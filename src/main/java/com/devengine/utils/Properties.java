package com.devengine.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Properties {

    private static Environment environment;

    @Autowired
    public Properties(Environment env) {
        Properties.environment = env;
    }

    public static String getProperties(String key) {
        return environment.getProperty(key);
    }
}