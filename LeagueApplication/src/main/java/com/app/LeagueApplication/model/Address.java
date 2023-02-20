package com.app.LeagueApplication.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Address {
    @DynamoDBAttribute
    private String city;
    @DynamoDBAttribute
    private String country;
}
