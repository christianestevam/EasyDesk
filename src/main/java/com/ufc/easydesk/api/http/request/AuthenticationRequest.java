package com.ufc.easydesk.api.http.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {

    private String username;
    private String password;

}