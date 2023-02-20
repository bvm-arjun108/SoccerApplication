package com.app.LeagueApplication.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.app.LeagueApplication.model.League;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableScan
public interface LeaguesRepository extends CrudRepository<League, String> {
    /*@Autowired
    private DynamoDBMapper dynamoDBMapper;

    public League save(League league) {
        dynamoDBMapper.save(league);
        return league;
    }

    public League findById(String id) {
        return dynamoDBMapper.load(League.class, id);
    }

    public String deleteById(String id) {
        League league = dynamoDBMapper.load(League.class, id);
        dynamoDBMapper.delete(league);
        return "League Deleted";
    }

    public String update(String id, League league) {
        dynamoDBMapper.save(league,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id",
                                new ExpectedAttributeValue(
                                        new AttributeValue(id))));
        return "Updated " + id;

    }
*/
}
