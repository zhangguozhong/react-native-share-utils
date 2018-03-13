
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


## Usage
```javascript
import RNShareUtils from 'react-native-share-utils';

// TODO: What to do with the module?
RNShareUtils;
```
  
