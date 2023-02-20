package com.cricket.User.Application.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private BigInteger executionTimeInMillis;
    private String timestamp;
    private String message;

    public static BigInteger calculateExecutionTimeInMillis(Instant startInstant, Clock clock) {
        return BigInteger.valueOf(Duration.between(startInstant, Instant.now(clock)).toMillis());

    }
}
