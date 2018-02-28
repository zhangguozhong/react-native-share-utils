
import { NativeModules } from 'react-native';

const RNShareUtils = NativeModules.RNShareUtils;

export default class ShareUtils {
    /**
     *  设置Umeng key
     *  @param key
     */
    static setUmengAppKey(key: string) {
        RNShareUtils.setUmengAppKey(key);
    }

    static openLog(isOpen: boolean) {
        RNShareUtils.openLog(isOpen);
    }

    /***
     * 设置平台账号
     * @param type     类别0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
     * @param appkey   key
     * @param appSecret secret
     * @param redirectURL   redirectURL
     */
    static setPlatform(type: number, appkey: string, appSecret: string, redirectURL: string) {
        RNShareUtils.setPlatform(type, appkey, appSecret, redirectURL);
    }

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

    /**
     *  获取社交平台的用户信息（用于第三方登录）
     *
     *  @param type           分享的类型 0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
     *  @param callback       信息获取成功的回调
     */
    static authWithPlatform(type: number, callback: Function) {
        RNShareUtils.authWithPlatform(type, (error, events) => {
            console.log('share,result', error, events);
            if (callback) {
                callback(error, events);
            }
        })
    }

    static isInstallWithPlatform(type) {
        return RNShareUtils.isInstallWithPlatform(type);
    }
}
