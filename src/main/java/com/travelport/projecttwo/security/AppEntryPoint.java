package com.travelport.projecttwo.security;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

public class AppEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void afterPropertiesSet() {
        setRealmName("travelport");
        super.afterPropertiesSet();
    }
}
