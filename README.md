### 自定义签名控件

##### Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

##### Add the dependency:
```
dependencies {
    implementation 'com.github.Ct7Liang:SignatureView:1.0'
}
```

##### Use:
```
<com.android.ct7liang.signaturelib.view.SignatureView
    android:id="@+id/sign_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>

SignatureView signatureView = findViewById(R.id.sign_view);
signatureView.clear(); //清理
signatureView.save(); //保存
```