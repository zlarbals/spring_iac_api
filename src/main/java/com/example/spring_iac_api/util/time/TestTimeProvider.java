package com.example.spring_iac_api.util.time;

import java.util.Date;

public class TestTimeProvider implements TimeProvider{

    private long minusMinutes;

    public TestTimeProvider(int minusMinutes){
        this.minusMinutes = minusMinutes * 60 * 1000L;
    }

    @Override
    public Date getCurrentTime() {
        Date now = new Date();
        Date dateBeforeMinutes = new Date(now.getTime() - minusMinutes);
        return dateBeforeMinutes;
    }
}
