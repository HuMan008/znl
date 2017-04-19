/*
 * Copyright (C) 2017.  Iusworks, Inc - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Dolphin cn.gotoil.dolphin.model.enums.OS
 *
 * cluries <cluries@me.com>,  February 2017
 *
 * LastModified: 2/14/17 9:36 AM
 *
 */

package com.znl.model.enums;


public enum OS {

    Android((byte) 1, "Android"),
    iOS((byte) 2, "iOS"),
    Unknow((byte) 100, "Unknow");


    OS(Byte value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OS fromName(String name) {

        if (iOS.getName().equalsIgnoreCase(name)) {
            return iOS;
        }

        if (Android.getName().equalsIgnoreCase(name)) {
            return Android;
        }

        return Unknow;
    }

    public static OS fromValue(byte value) {
        if (iOS.getValue().intValue() == value) {
            return iOS;
        }

        if (Android.getValue().intValue() == value) {
            return Android;
        }

        return Unknow;
    }

    public Byte getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "OS{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }

    private Byte value;
    private String name;
}
