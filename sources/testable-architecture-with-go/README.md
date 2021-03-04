(ハンズオン)Testabilityの高いGoのAPIサーバを開発しよう
====

# はじめに

testable-architecture-with-goのcodelabで使用するサンプルコードです。

- [part1](https://dena.github.io/codelabs/testable-architecture-with-go-part1/#1)
- [part2](https://dena.github.io/codelabs/testable-architecture-with-go-part2/#1)
- [part3](https://dena.github.io/codelabs/testable-architecture-with-go-part3/#1)


# 起動方法

## 環境

- Go (1.13~)
- Docker (19~)

## build方法

```
$ make
```

## Application起動方法

```
$ make compose/up
```

## Test実行方法

```
$ make test
```

# Endpointの仕様

## user create API

- POST `/users`
- first_name, last_name, email, passwordをbodyに含めたjsonをpostする
- validation rule
    - first_name/last_name: 256文字以内
    - email: RFC5322
    - password: 16文字以上文字種全部含むなどのルールを決める
- emailは重複を禁止する
    - 重複していた場合は重複を知らせるエラーを返す
- passwordはbcryptでhash化して保存

# ディレクトリ構成


```
.
├── Dockerfile
├── Makefile
├── README.md
├── answer               # 正解のコードが設置されているディレクトリ
├── bin                  # build成果物が設置されるディレクトリ
├── docker
├── docker-compose.yaml
├── e2e                  # E2Eを設置するディレクトリ
├── go.mod
├── go.sum
├── main.go              # アプリケーションのentry point
├── internal             # アプリケーションの実際の処理を行うパッケージを設置する
│   ├── apierr           # アプリケーションロジック固有のエラー
│   ├── config           # configのパッケージ
│   ├── handler          # httpのhandlerを設置するパッケージ
│   ├── logging          # loggingのパッケージ
│   ├── model            # data binding用の構造体を設置するパッケージ
│   └── validator        # http requestのvalidatorのパッケージ
├── spec.md              # アプリケーションのEndpointの仕様
└── tools                # アプリケーションには関係ないutility tool
```
