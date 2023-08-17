package com.o7planning.service.oauth2;

import com.o7planning.entity.Provider;
import com.o7planning.exception.BaseException;
import com.o7planning.service.oauth2.model.OAuth2FacebookUser;
import com.o7planning.service.oauth2.model.OAuth2GithubUser;
import com.o7planning.service.oauth2.model.OAuth2GoogleUser;

import java.util.Map;

// cau hinh kiem tra xem obj thuoc ben app nao quan ly
public class OAuth2UserDetailFactory {

    public static OAuth2UserDetail getOAuth2Detail(String registrationId, Map<String , Object> attributes) {
        if (registrationId.equals((Provider.google.name()))) {
            return new OAuth2GoogleUser(attributes);
        } else if (registrationId.equals((Provider.facebook.name()))) {
            return new OAuth2FacebookUser(attributes);
        } else if (registrationId.equals((Provider.github.name()))) {
            return new OAuth2GithubUser(attributes);
        } else {
            throw new BaseException("400 ", "Sorry ! Login with" + registrationId + " is not supported: ");
        }
    }
}
