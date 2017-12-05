package service;

import android.content.Context;

import domain.UserInfo;

public interface UserRatingService {
    void updateRating(Context context, float currentRating, float newRating, int numRatings, UserInfo user);
}
