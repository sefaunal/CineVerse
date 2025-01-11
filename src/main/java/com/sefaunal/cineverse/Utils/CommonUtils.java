package com.sefaunal.cineverse.Utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author github.com/sefaunal
 * @since 2025-01-11
 */
public class CommonUtils {

    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String getUserEnvironment(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    public static String getUserInfo() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}