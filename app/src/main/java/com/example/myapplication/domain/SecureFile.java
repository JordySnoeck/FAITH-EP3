package com.example.myapplication.domain;

import java.lang.reflect.Type;

public interface SecureFile {

    public boolean isValid();

    public String getFileName();

    public Type getType();
}