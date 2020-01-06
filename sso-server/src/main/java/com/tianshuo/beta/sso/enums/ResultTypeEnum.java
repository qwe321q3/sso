package com.tianshuo.beta.sso.enums;

import lombok.Getter;

@Getter
public enum ResultTypeEnum {

    SUCCESS(true, "操作成功"),
    ERROR(false, "操作失败");

    private boolean code;
    private String desc;

    ResultTypeEnum(boolean code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
