package com.o7planning.service.oauth2;

import java.util.Map;


// class nay de chua du lieu username va email cua ben thu 3
public abstract class OAuth2UserDetail {

    protected Map<String, Object> attributes;

    public OAuth2UserDetail(Map<String,Object> attributes){
        this.attributes = attributes;
    }

    public abstract String getName();
    public abstract String getEmail();

}
