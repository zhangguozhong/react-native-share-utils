
#import "RNShareUtils.h"
#import "BStringUtil.h"
#import <UMSocialCore/UMSocialCore.h>

@implementation RNShareUtils

RCT_EXPORT_MODULE();

/**
 *  设置Umeng key
 *
 *  @param key          key
 */
RCT_EXPORT_METHOD(setUmengAppKey:(NSString *)key){
    [[UMSocialManager defaultManager] setUmSocialAppkey:key];
}
/**
 *  设置Umeng key
 *
 *  @param key          key
 */
RCT_EXPORT_METHOD(openLog:(BOOL)isOpen){
    [[UMSocialManager defaultManager] openLog:isOpen];
}
/***
 * 设置平台账号
 * @param type     类别0,新浪 1,微信 2,朋友圈  4,qq  5,Qzone    12,短信
 * @param appkey   key
 * @param appSecret secret
 * @param redirectURL   redirectURL
 */
RCT_EXPORT_METHOD(setPlatform:(NSInteger)type appkey:(NSString *)appkey appSecret:(NSString *)appSecret redirectURL:(NSString *)redirectURL){
    [[UMSocialManager defaultManager] setPlaform:type appKey:appkey appSecret:appSecret redirectURL:redirectURL];
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
RCT_EXPORT_METHOD(share:(NSString *)title description:(NSString *)description url:(NSString *)url imageUrl:(NSString *)imageUrl type:(NSInteger)type callback:(RCTResponseSenderBlock)callback){
    
    UIViewController *presentingViewController = [[[UIApplication sharedApplication] delegate] window].rootViewController;
    while (presentingViewController.presentedViewController != nil) {
        presentingViewController = presentingViewController.presentedViewController;
    }
    [RNShareUtils shareWithTitle:title description:description url:url image:imageUrl type:type fromScene:presentingViewController complate:^(id data, NSError *error){
        NSString *message = nil;
        if (!error) {
            message = [NSString stringWithFormat:@"分享成功"];
            callback(@[[NSNull null],@{@"result":message}]);
        } else {
            message = [NSString stringWithFormat:@"失败原因Code: %d\n",(int)error.code];
            callback(@[[NSNumber numberWithInteger:error.code],@{@"result":message}]);
        }
        
    }];
}
/**
 *  获取第三方的用户信息
 *
 *  @param type           分享的类型 0,新浪 1,微信 2,朋友圈  4,qq
 *  @param callback       分享成功的回调
 */
RCT_EXPORT_METHOD(authWithPlatform:(NSInteger)type callback:(RCTResponseSenderBlock)callback{
    [RNShareUtils authWithPlatform:type callback:callback];
})

/**
 *  获取第三方的软件是否登录
 *
 *  @param type           分享的类型 0,新浪 1,微信 2,朋友圈  4,qq
 */
RCT_EXPORT_METHOD(isInstallWithPlatform:(NSInteger)type resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject{
    resolve(@([[UMSocialManager defaultManager] isInstall:type]));
})

+(void)shareWithTitle:(NSString *)title description:(NSString *)description url:(NSString *)url image:(NSString *)imageUrl type:(UMSocialPlatformType)type fromScene:(UIViewController *)scene complate:(UMSocialRequestCompletionHandler)completion{
    
    NSString *shareTitle = title;
    NSString *shareContent =description;
    NSString *shareUrl =url;
    if ([BStringUtil emptyOrNull:url]) {
        shareUrl=@"http://www.bnq.com.cn";
    }
    id shareImage = imageUrl;
    if ([BStringUtil emptyOrNull:imageUrl]) {
        shareImage=[UIImage imageNamed:@"share"];
    }
    UMSocialMessageObject *messageObject = [UMSocialMessageObject messageObject];
    messageObject.text=shareContent;
    
    messageObject.shareObject=[UMShareWebpageObject shareObjectWithTitle:shareTitle descr:shareContent thumImage:shareImage];
    ((UMShareWebpageObject *)(messageObject.shareObject)).webpageUrl=shareUrl;
    [[UMSocialManager defaultManager] shareToPlatform:type messageObject:messageObject currentViewController:scene completion:completion];
    
    
}
+(void)authWithPlatform:(NSInteger)type callback:(RCTResponseSenderBlock)callback{
    
    UIViewController *presentingViewController = [[[UIApplication sharedApplication] delegate] window].rootViewController;
    while (presentingViewController.presentedViewController != nil) {
        presentingViewController = presentingViewController.presentedViewController;
    }
    [[UMSocialManager defaultManager] authWithPlatform:type currentViewController:presentingViewController completion:^(id result, NSError *error) {
        
        if (error) {
            callback(@[[NSNumber numberWithInteger:1],@{}]);
        } else {
            UMSocialAuthResponse *resp = result;
            NSLog(@"----------------------------");
            NSLog(@"%@",resp.originalResponse);
            
            NSMutableDictionary *dic= [[NSMutableDictionary alloc] init];
            [dic setObject:resp.openid forKey:@"openId"];
            [dic setObject:resp.accessToken forKey:@"accessToken"];
            if(type==4){
                [dic setObject:[resp.originalResponse objectForKey:@"pf"] forKey:@"pf"];
                [dic setObject:[resp.originalResponse objectForKey:@"pfkey"] forKey:@"pfkey"];
            }
            callback(@[[NSNumber numberWithInteger:0],dic]);
        }
    }];
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@end
  
