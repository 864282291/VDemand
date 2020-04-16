package com.bigdata.base.request;

import android.content.Context;


import java.io.Serializable;

/**
 * Author: 通子
 * Created by: ModelGenerator on 2017/9/18
 */
public class UserInfoBean implements Serializable {
    private String user_id;
    private String user_name;
    private String user_phone;
    private String user_password;
    private String user_sex;
    private String user_avatar;
    private String user_role;
    private String user_birthday;
    private String user_city;
    private String user_funscount;
    private String user_autograph;
    private String user_video;
    private String user_apple_val;
    private String user_available_money;
    private String user_code;
    private String user_conference_room;
    private String user_token;
    private String user_sig;
    private String user_sigexpire;
    private String user_vip_valid_time;

    public String getUser_vip_valid_time() {
        return user_vip_valid_time;
    }

    public void setUser_vip_valid_time(String user_vip_valid_time) {
        this.user_vip_valid_time = user_vip_valid_time;
    }

    private static UserInfoBean ourInstance = new UserInfoBean();

    public static UserInfoBean getInstance() {
        return ourInstance;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_funscount() {
        return user_funscount;
    }

    public void setUser_funscount(String user_funscount) {
        this.user_funscount = user_funscount;
    }

    public String getUser_autograph() {
        return user_autograph;
    }

    public void setUser_autograph(String user_autograph) {
        this.user_autograph = user_autograph;
    }

    public String getUser_video() {
        return user_video;
    }

    public void setUser_video(String user_video) {
        this.user_video = user_video;
    }

    public String getUser_apple_val() {
        return user_apple_val;
    }

    public void setUser_apple_val(String user_apple_val) {
        this.user_apple_val = user_apple_val;
    }

    public String getUser_available_money() {
        return user_available_money;
    }

    public void setUser_available_money(String user_available_money) {
        this.user_available_money = user_available_money;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getUser_conference_room() {
        return user_conference_room;
    }

    public void setUser_conference_room(String user_conference_room) {
        this.user_conference_room = user_conference_room;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getUser_sig() {
        return user_sig;
    }

    public void setUser_sig(String user_sig) {
        this.user_sig = user_sig;
    }

    public String getUser_sigexpire() {
        return user_sigexpire;
    }

    public void setUser_sigexpire(String user_sigexpire) {
        this.user_sigexpire = user_sigexpire;
    }
}