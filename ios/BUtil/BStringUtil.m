//
//  StringUtil.m
//  Staff
//
//  Created by 雷刚 on 14/12/19.
//  Copyright (c) 2014年 员工宝. All rights reserved.
//

#import "BStringUtil.h"
#import <CommonCrypto/CommonDigest.h>

@implementation BStringUtil

/**
 * 判断字串是否为空
 * @param str
 * @return
 */
+(bool) emptyOrNull:(NSString *)str
{
    return str == nil || (NSNull *)str == [NSNull null] || str.length == 0||[str isKindOfClass:[NSNull class]];
}

@end
