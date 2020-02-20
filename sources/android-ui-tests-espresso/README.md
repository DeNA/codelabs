# Espresso APIを使いこなしてUIテストを書いてみよう

Codelab「Espresso APIを使いこなしてUIテストを書いてみよう」の演習アプリです。

GoogleがAndroid Jetpackのサンプルアプリとして公開している[Android Sunflower](https://github.com/android/sunflower)の2019年5月18日時点のソースコードをベースに、少し改修を加えたものを題材として使用します。

Android Sunflower付属の、オリジナルのREADMEは[README.orig.md](./README.orig.md)を参照してください。


## オリジナルのAndroid Sunflowerとの違い

- Android Studio 3.5+でビルドできるように、ビルドスクリプトを修正しています。
- AndroidX Test関連のライブラリを依存関係に追加したり、バージョンを最新化しています。
- UIテスト演習用の画面をメニューに追加しています
- `app/src/androidTest`配下のテストコードを全て削除しています。 代わりに、本Codelabで使用するテストコードとその回答を追加しています。
- `app/src/sharedTest`ディレクトリを追加し、配下に本Codelabで使用するテストコードとその回答を追加しています。
- `app/src/sharedTest`配下のテストコードを実行できるようにビルドスクリプトを修正しています。
- オリジナルのREADME.mdのファイル名をREADME.orig.mdにリネームしています。

## ライセンス
Original Copyright 2018 Google, Inc. See [README.orig.md](./README.orig.md) for details.

Modifications Copyright 2019 DeNA Co., Ltd.

Licensed under the Apache License, Version 2.0.

### Third Party Content
Select text used for describing the plants (in plants.json) are used from Wikipedia via CC BY-SA 3.0 US (license in ASSETS_LICENSE).