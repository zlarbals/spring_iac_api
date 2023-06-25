package com.example.spring_iac_api.util.time;

import java.util.Date;

public class DefaultTimeProvider implements TimeProvider{

    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
