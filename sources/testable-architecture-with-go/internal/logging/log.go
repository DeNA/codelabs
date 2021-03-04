package logging

import (
	"net/http"
	"os"
	"sync"
	"time"

	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
)

var (
	logger  *logrus.Logger
	logonce sync.Once
)

func Logger() *logrus.Logger {
	logonce.Do(func() {
		logger = logrus.New()
		logger.SetOutput(os.Stdout)
	})

	return logger
}

type loggingResponseWriter struct {
	inner http.ResponseWriter
	code  int
}

func (l *loggingResponseWriter) Header() http.Header {
	return l.inner.Header()
}

func (l *loggingResponseWriter) Write(payload []byte) (int, error) {
	return l.inner.Write(payload)
}

func (l *loggingResponseWriter) WriteHeader(statusCode int) {
	l.code = statusCode
	l.inner.WriteHeader(statusCode)
}

func Middleware(logger *logrus.Logger) mux.MiddlewareFunc {

	return func(next http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			start := time.Now()

			ip := r.RemoteAddr
			method := r.Method
			uri := r.RequestURI

			writer := &loggingResponseWriter{inner: w}

			next.ServeHTTP(writer, r)

			latency := time.Since(start)

			entry := logrus.NewEntry(logger)

			entry = entry.WithFields(logrus.Fields{
				"ip":         ip,
				"method":     method,
				"uri":        uri,
				"statusCode": writer.code,
				"latency":    latency,
			})
			entry.Info("completed request")
		})
	}
}
