package com.example.finance.configuration;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
public class ClockProvider {

    public Instant getDate() {
        Clock clock = Clock.systemUTC();
        return clock.instant();
    }
}
