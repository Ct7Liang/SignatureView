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