package service;

import domain.UserInfo;

public class UserRatingServiceImpl implements UserRatingService {
    public void updateRating(float currentRating, float newRating, int numRatings, UserInfo user){
        user.setUserRating(((currentRating * numRatings) + newRating) / (numRatings + 1));
    }

}
