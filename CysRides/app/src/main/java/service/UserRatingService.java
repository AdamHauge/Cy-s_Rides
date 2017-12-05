package service;

import domain.UserInfo;

public interface UserRatingService {
    void updateRating(float currentRating, float newRating, int numRatings, UserInfo user);
}
