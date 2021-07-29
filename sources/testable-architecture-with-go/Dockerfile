FROM golang:1.16.6-alpine3.14 as builder

RUN apk add --no-cache git make gcc musl-dev
WORKDIR /app
COPY go.mod go.sum ./
RUN go mod download
COPY . .
RUN make

FROM alpine:3.16.6
COPY --from=builder /app/bin/api /app/api
EXPOSE 8080
ENTRYPOINT ["/app/api"]
