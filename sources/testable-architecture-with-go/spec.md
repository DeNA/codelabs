アプリケーション仕様
===

# user create API

## 要求仕様

- POSTされたJSONの情報を元に、DBにレコードを作成する
- passwordはbcryptによりハッシュ化し、DBに保存する 
- 同じemailでの重複登録はできない
    - 重複していた場合、400のstatus codeを返す
- request bodyは下記のJSONの仕様を満たしていなければならない
    - 満たしていない場合は400のstatus codeを返す

## API仕様

- Request Path: `POST /users`
- Request BodyのJSONの仕様

|  property名  |  type  |必須  | validation rule |
| ----        | ----    | ---   | ---             |
|  first_name  |  string  |  ○   | 256文字以内 |
|  last_name  |  string  | ○  | 256文字以内 |
|  email  |  string  | ○  | 256文字以内, 正しいformatであること |
|  password |  string  | ○  | 12文字以上256文字以内, 小文字英字・大文字英字・数字・記号（!_$@/-+）から2種類以上の文字を使用していること |

- Response BodyのJSONの仕様

> 201(成功時)

|  property名  |  type  |必須  |
| ----        | ----    | ---   |
|  id  |  int  |  ○   |
|  first_name  |  string  |  ○   |
|  last_name  |  string  | ○  |
|  email  |  string  | ○  |

> 失敗時

|  property名  |  type  |必須  |
| ----        | ----    | ---   |
|  message |  string  | ○  |