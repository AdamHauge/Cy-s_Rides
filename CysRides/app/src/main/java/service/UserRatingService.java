package service;

import android.content.Context;

import java.util.ArrayList;

import domain.Group;
import domain.UserInfo;

public interface UserRatingService {
    void updateRating(Context context, float currentRating, float newRating, int numRatings, UserInfo user);
    ArrayList<Group> getGroupsByUser(UserInfo userInfo, ArrayList<Group> groups);
    ArrayList<String> getMembersFromGroups(ArrayList<Group> groups);
}
