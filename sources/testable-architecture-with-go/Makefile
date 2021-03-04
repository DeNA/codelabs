.PHONY: build
build:
	go build -o bin/api .

.PHONY: build/answer
build/answer:
	cd answer && go build -o bin/api .

.PHONY: clean
clean:
	rm bin/api tools/bin/wait

test: test/e2e

.PHONY: test/e2e
test/e2e: build compose/up/db
	go test -count=1 ./e2e/...

.PHONY: test/e2e/answer
test/e2e/answer: build/answer compose/up/db
	cd answer && go test -count=1 ./e2e/...

.PHONY: compose/up
compose/up: compose/up/db
	docker-compose up -d api

.PHONY: compose/up/db
compose/up/db: compose/down tools/bin/wait
	docker-compose up -d db
	tools/bin/wait

.PHONY: compose/down
compose/down:
	docker-compose down

tools/bin/wait:
	go build -o tools/bin/wait tools/wait/main.go
