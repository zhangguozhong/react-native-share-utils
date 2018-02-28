
package com.bnq.share;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

public class RNShareUtilsModule extends ReactContextBaseJavaModule {
  private String mTitle;
  private String mDescription;
  private String mImage;
  private String mUrl;
  private SHARE_MEDIA mPlaform;
  private Callback mCallback;
  public RNShareUtilsModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }
  @Override
  public String getName() {
    return "RNShareUtils";
  }

  @ReactMethod
  public void openLog(boolean isopen) {}

  @ReactMethod
  public void setUmengAppKey(String key){
    //UMShareAPI.get(getCurrentActivity().getApplicationContext());
    Config.DEBUG = true;
  }
  @ReactMethod
  public void setPlatform(Integer type,String appkey,String appSecret,String redirectURL){
    Log.i("share","type:"+type+"  appkey:"+appkey+"  appSecret:"+appSecret+"  redirectURL:"+redirectURL);
    switch (type){
      case 0:
        PlatformConfig.setSinaWeibo(appkey,appSecret,redirectURL);
        break;
      case 1:
      case 2:
        PlatformConfig.setWeixin(appkey,appSecret);
        break;
      case 4:
      case 5:
        PlatformConfig.setQQZone(appkey,appSecret);
        break;
      case 12:
        break;
    }
  }

  /***
   * 分享
   * @param title        分享出去的名称
   * @param description  描述
   * @param imageUrl        图片的本地地址
   * @param url          H5链接
   * @param type         分享的类型 0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
   * @param callback     回调
   */
  @ReactMethod
  public void share(String title,String description,String url,String imageUrl,Integer type,Callback callback) {

    mTitle = title;
    mDescription = description;
    mImage = imageUrl;
    mUrl = url;
    System.out.println("sadfasdfasdf" + title + "  " + description + "  " + imageUrl + "  " + url + "  " + type + "  ");
    mCallback = callback;


    switch (type){
      case 0:
        mPlaform= SHARE_MEDIA.SINA;
        break;
      case 1:
        mPlaform= SHARE_MEDIA.WEIXIN;
        break;
      case 2:
        mPlaform= SHARE_MEDIA.WEIXIN_CIRCLE;
        break;
      case 4:
        mPlaform= SHARE_MEDIA.QQ;
        break;
      case 5:
        mPlaform= SHARE_MEDIA.QZONE;
        break;
      case 12:
        mPlaform= SHARE_MEDIA.SMS;
        break;
    }


    getCurrentActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Resources res = getCurrentActivity().getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.share);

        UMImage image = null;
        if (mImage!=null&&mImage.length()>0){
          image=new UMImage(getCurrentActivity(), mImage);
        }else{
          image=new UMImage(getCurrentActivity(), bmp);
        }

        UMWeb  web = new UMWeb(mUrl);
        web.setTitle(mTitle);//标题
        web.setThumb(image);  //缩略图
        web.setDescription(mDescription);//描述
        new ShareAction(getCurrentActivity())
                .setPlatform(mPlaform)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                  @Override
                  public void onStart(SHARE_MEDIA share_media) {
                    Log.i("share","分享start");
                  }

                  @Override
                  public void onResult(SHARE_MEDIA platform) {//分享成功啦
                    try {
                      WritableMap map = Arguments.createMap();
                      map.putString("result", "success");
                      WritableArray array = Arguments.createArray();
                      array.pushNull();
                      array.pushMap(map);
                      mCallback.invoke(array);
                    } catch (IllegalViewOperationException e) {
                      mCallback.invoke(e.getMessage());
                    }
                  }

                  @Override
                  public void onError(SHARE_MEDIA platform, Throwable t) {//分享失败啦
                    Log.i("share","分享失败"+platform+""+t);
                  }

                  @Override
                  public void onCancel(SHARE_MEDIA platform) {//分享取消
                    Log.i("share","分享取消"+platform);
                  }
                })//回调监听器
                .share();
      }
    });


  }
  /***
   * 第三方软件是否登录
   * @param type         分享的类型 0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
   */
  @ReactMethod
  public void isInstallWithPlatform(Integer type, final Promise promise){
    switch (type){
      case 0:
        mPlaform= SHARE_MEDIA.SINA;
        break;
      case 1:
        mPlaform= SHARE_MEDIA.WEIXIN;
        break;
      case 2:
        mPlaform= SHARE_MEDIA.WEIXIN_CIRCLE;
        break;
      case 4:
        mPlaform= SHARE_MEDIA.QQ;
        break;
      case 5:
        mPlaform= SHARE_MEDIA.QZONE;
        break;
      case 12:
        mPlaform= SHARE_MEDIA.SMS;
        break;
    }
    promise.resolve(UMShareAPI.get(getCurrentActivity().getApplicationContext()).isInstall(getCurrentActivity(),mPlaform));
  }
  /***
   * 获取社交平台的用户信息（用于第三方登录）
   * @param type         分享的类型 0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
   * @param callback     回调
   */
  @ReactMethod
  public void authWithPlatform(Integer type,Callback callback) {

    mCallback = callback;


    switch (type){
      case 0:
        mPlaform= SHARE_MEDIA.SINA;
        break;
      case 1:
        mPlaform= SHARE_MEDIA.WEIXIN;
        break;
      case 2:
        mPlaform= SHARE_MEDIA.WEIXIN_CIRCLE;
        break;
      case 4:
        mPlaform= SHARE_MEDIA.QQ;
        break;
      case 5:
        mPlaform= SHARE_MEDIA.QZONE;
        break;
      case 12:
        mPlaform= SHARE_MEDIA.SMS;
        break;
    }


    getCurrentActivity().runOnUiThread(new Runnable() {
      @Override
      public void run() {
        UMShareAPI.get(getCurrentActivity())
                .doOauthVerify(getCurrentActivity(), mPlaform, new UMAuthListener() {
                  @Override
                  public void onStart(SHARE_MEDIA share_media) {
                    Log.i("getUserInfoWithPlatform","start");
                  }

                  @Override
                  public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    Log.i("getUserInfoWithPlatform",map.toString());

                    try {
                      WritableMap newMap = Arguments.createMap();
                      newMap.putString("openId", map.get("openid"));
                      newMap.putString("accessToken", map.get("access_token"));
                      if(mPlaform== SHARE_MEDIA.QQ){
                        newMap.putString("pf", map.get("pf"));
                        newMap.putString("pfkey", map.get("pfkey"));
                      }
                      mCallback.invoke(0,newMap);
                    } catch (IllegalViewOperationException e) {
                      mCallback.invoke(1);
                    }


                  }

                  @Override
                  public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    Log.i("getUserInfoWithPlatform","获取失败");
                    mCallback.invoke(1);
                  }

                  @Override
                  public void onCancel(SHARE_MEDIA share_media, int i) {
                    Log.i("getUserInfoWithPlatform","取消");
                    mCallback.invoke(1);
                  }

                });
      }
    });


  }
}