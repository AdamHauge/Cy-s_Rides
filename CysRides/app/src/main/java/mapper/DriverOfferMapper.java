package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Offer;
import domain.UserType;

public class DriverOfferMapper {

    public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setUserType(UserType.valueOf(rs.getString("user_type")));
        offer.setCost(rs.getInt("last_name"));
        offer.setEmail(rs.getString("email"));
        offer.setDestination(rs.getString("destination"));
        offer.setDescription(rs.getString("description"));
        offer.setDate(rs.getString("date"));
        return offer;
    }

}
