package service;

import android.content.Context;

import domain.UserInfo;
import volley.UserRatingVolley;
import volley.UserRatingVolleyImpl;

public class UserRatingServiceImpl implements UserRatingService {
    public void updateRating(Context context, float currentRating, float newRating, int numRatings, UserInfo user){
        UserRatingVolley userRatingVolley = new UserRatingVolleyImpl();
        user.setUserRating(((currentRating * numRatings) + newRating) / (numRatings + 1));
        userRatingVolley.addRating(context, user.getNetID(), user.getUserRating(), (numRatings + 1));
    }

}
