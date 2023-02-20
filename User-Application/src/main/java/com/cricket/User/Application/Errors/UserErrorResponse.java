package com.cricket.User.Application.Errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserErrorResponse {
    private BigInteger executionTimeInMillis;
    private String timestamp;
    private String message;
    private List<String> errorMessage;
    public UserErrorResponse(BigInteger executionTimeInMillis, String timestamp, String message) {
        this.executionTimeInMillis = executionTimeInMillis;
        this.timestamp = timestamp;
        this.message = message;
    }
    public UserErrorResponse(String message) {
        this.message = message;
    }
    public static BigInteger calculateExecutionTimeInMillis(Instant startInstant, Clock clock) {
        return BigInteger.valueOf(Duration.between(startInstant, Instant.now(clock)).toMillis());
    }
}
