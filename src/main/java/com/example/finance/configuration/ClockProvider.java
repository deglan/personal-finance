package com.example.finance.configuration;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;

@Component
public class ClockProvider {

    public Instant getDate() {
        return Clock.systemUTC().instant();
    }
}
