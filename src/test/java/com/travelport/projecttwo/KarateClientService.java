package com.travelport.projecttwo;

import com.intuit.karate.junit5.Karate;

public class KarateClientService {

    @Karate.Test
    Karate testClientService() {
        return Karate.run("classpath:features/checkClientService.feature").relativeTo(getClass());
    }
}
