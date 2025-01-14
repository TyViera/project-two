package com.travelport.projecttwo;

import com.intuit.karate.junit5.Karate;

public class KarateControllers {

    @Karate.Test
    Karate testControllers() {
        return Karate.run("features/classpath:checkControllers.feature");
    }
}
