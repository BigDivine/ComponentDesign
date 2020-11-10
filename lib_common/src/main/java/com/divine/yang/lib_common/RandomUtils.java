package com.divine.yang.lib_common;

import java.security.SecureRandom;

/**
 * author: Divine
 * 获取随机数
 * <p>
 * date: 2018/12/10
 */
public class RandomUtils {
    private static RandomUtils mRandomUtils = null;
    private SecureRandom mRandom;

    private RandomUtils() {
        mRandom = new SecureRandom();
    }

    public static RandomUtils instance() {
        if (null == mRandomUtils) {
            mRandomUtils = new RandomUtils();
        }
        return mRandomUtils;
    }

    public int getRandomInt(int end) {
        return mRandom.nextInt(end);
    }
}
