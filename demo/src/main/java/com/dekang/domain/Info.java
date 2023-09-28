package com.dekang.domain;

import lombok.Data;

@Data
public class Info {

//    @DecryptField
//    @EncryptField
    private String name;

    private String sex;

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Info() {
    }

    public Info(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
}
