package io.github.jerryofouc.time;

import java.util.Date;

/**
 * 功能介绍：
 *
 * @author zhangxiaojie
 *         Date:  9/28/14
 *         Time: 17:52
 */
public class UnixTime {
    private final int value;

    public UnixTime() {
        this((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    }

    public UnixTime(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}
