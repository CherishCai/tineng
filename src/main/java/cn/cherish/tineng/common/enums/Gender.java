package cn.cherish.tineng.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举类
 */
@Getter
@AllArgsConstructor
public enum Gender {
    M(1, "男"),
    F(0, "女"),
    N(-1, "未知");

    private int code;

    private String desc;

    public static String desc(Integer code) {
        if (code == null) {
            return N.desc;
        }
        for (Gender gender : Gender.values()) {
            if (gender.code == code) {
                return gender.desc;
            }
        }
        return N.desc;
    }


}
