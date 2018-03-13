
# react-native-share-utils

## Getting started

`$ npm install react-native-share-utils --save`

### Mostly automatic installation

`$ react-native link react-native-share-utils`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-share-utils` and add `RNShareUtils.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNShareUtils.a`、`UMSorcialCore.framework`、`UMSorcialNetwork.framework` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. In XCode, in the project navigator, select your project. Add `$(SRCROOT)/../node_modules/react-native-share-utils/ios/Core/3rd` to your project's `Build Settings` ➜ `Framework Search Paths`
5. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.bnq.share.RNShareUtilsPackage;` to the imports at the top of the file
  - Add `new RNShareUtilsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-share-utils'
  	project(':react-native-share-utils').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-share-utils/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-share-utils')
  	```


### 配置友盟key和设置分享平台（android配置友盟key请参考友盟官方文档）
```javascript
/**
     * 用于配置友盟分享的信息（微信、QQ等三方平台）
     */
    static configShareKey(){
        if (Platform.OS === 'ios'){
            ShareUtils.setUmengAppKey(umengKey);
        }
        ShareUtils.setPlatform('平台',key,appSecret,'http://mobile.umeng.com/social');
    }
```


### 分享到那个平台
```javascript
/**
     *  分享到社交平台
     *
     *  @param title          分享的title
     *  @param description    分享的描述信息
     *  @param image          分享的图片路径（本地路径）
     *  @param url            分享的链接URL
     *  @param type           分享的类型 0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
     *  @param callback       分享成功的回调
     */
    static share(title: string, discription: string, url: string, imageUrl: string, type: number, callback: Function) {
        RNShareUtils.share(title, discription, url, imageUrl.replace('https', 'http'), type, (error, events) => {
            console.log('share,result', error, events);
            if (callback) {
                callback(error, events);
            }
        })
    }
```


### 如何使用
```javascript
import RNShareUtils from 'react-native-share-utils';

ShareUtils.share(
                'title',
                'description',
                '分享的url',
                '分享图片的url',
                '平台'
            );
```
