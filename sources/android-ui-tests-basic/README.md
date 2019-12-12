# Espressoの知識ゼロでも書ける！Android UIテストはじめの一歩

Codelab「Espressoの知識ゼロでも書ける！Android UIテストはじめの一歩」の演習アプリです。

GoogleがAndroid Jetpackのサンプルアプリとして公開している[Android Sunflower](https://github.com/android/sunflower)の2019年5月18日時点のソースコードをベースに、少し改修を加えたものを題材として使用します。

Android Sunflower付属の、オリジナルのREADMEは[README.orig.md](./README.orig.md)を参照してください。


## オリジナルのAndroid Sunflowerとの違い

- Android Studio 3.3+でビルドできるように、ビルドスクリプトを修正しています。
- AndroidX Test関連のライブラリを依存関係に追加したり、バージョンを最新化しています。
- `app/src/androidTest`配下のテストコードを全て削除しています。 代わりに、本Codelabで使用するテストコードを追加しています。
- `app/src/exampleAndroidTest`配下に回答のテストコードを追加しています。
- `app/src/exampleAndroidTest`配下のテストコードをInstrument Testとして実行できるようにビルドスクリプトを修正しています。
- Espresso Test Recorderでテストコードを記録しやすいようにUIを変更しています。
- オリジナルのREADME.mdのファイル名をREADME.orig.mdにリネームしています。

## ライセンス
Original Copyright 2018 Google, Inc. See [README.orig.md](./README.orig.md) for details.

Modifications Copyright 2019 DeNA Co., Ltd.

Licensed under the Apache License, Version 2.0.

### Third Party Content
Select text used for describing the plants (in plants.json) are used from Wikipedia via CC BY-SA 3.0 US (license in ASSETS_LICENSE).