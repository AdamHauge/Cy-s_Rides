package volley;

import android.content.Context;

public interface UserRatingVolley {
    void addRating(Context context, final String netID, final float userRating, final float numberRatings);
}
