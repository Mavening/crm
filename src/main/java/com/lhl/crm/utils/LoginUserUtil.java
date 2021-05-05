package com.lhl.crm.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 */
public class LoginUserUtil {

    /**
     * 从cookie中获取userId
     *
     *
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @param request
     * @return int
     */
    public static int releaseUserIdFromCookie(HttpServletRequest request) {
        String userIdString = CookieUtil.getCookieValue(request, "userId");
        if (StringUtils.isBlank(userIdString)) {
            return 0;
        }
        return UserIDBase64.decoderUserID(userIdString);
    }
}
