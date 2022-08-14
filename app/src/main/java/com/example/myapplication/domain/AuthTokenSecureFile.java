package com.example.myapplication.domain;

import android.util.Log;

import com.auth0.android.result.Credentials;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthTokenSecureFile implements SecureFile {


    private final  String _tokenName ="jwt_tokens_v3.json";


    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    private String email;

    public String getEmail() {
        return email;
    }


    public AuthTokenSecureFile(){

    }

    public void fill(Credentials credentials, String userEmail) {



        setUserEmail(userEmail);

    }

    @Override
    public boolean isValid() {
        //check if token is valid
        return false;
    }
    @Override
    public String getFileName() {
        return _tokenName;
    }

    @Override
    public Type getType() {
        return AuthTokenSecureFile.class;
    }


}