package com.o7planning.service.oauth2.model;

import com.o7planning.service.oauth2.OAuth2UserDetail;

import java.util.Map;


// cau hinh de lay ra thuoc tinh thong tin login cua ben thu 3
public class OAuth2FacebookUser extends OAuth2UserDetail {

    public OAuth2FacebookUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
