package com.app.TeamsApplication.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamsErrorResponse {
    private BigInteger executionTimeInMillis;
    private String timestamp;
    private String message;
    private List<String> errorMessage;

    public TeamsErrorResponse(BigInteger executionTimeInMillis, String timestamp, String message) {
        this.executionTimeInMillis = executionTimeInMillis;
        this.timestamp = timestamp;
        this.message = message;
    }

    public TeamsErrorResponse(String message) {
        this.message = message;
    }

    public static BigInteger calculateExecutionTimeInMillis(Instant startInstant, Clock clock) {
        return BigInteger.valueOf(Duration.between(startInstant, Instant.now(clock)).toMillis());
    }
}


