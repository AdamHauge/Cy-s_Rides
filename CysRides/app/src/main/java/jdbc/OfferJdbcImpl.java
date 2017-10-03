package jdbc;

import com.microsoft.sqlserver.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Offer;
import domain.UserType;
import mapper.DriverOfferMapper;

public class OfferJdbcImpl implements OfferJdbc {

    private JdbcTemplate jdbcTemplate;

    protected Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "dbu309sab5");
        connectionProps.put("password", "BRCc@3V2");
        conn = DriverManager.getConnection(
                "jdbc:" + "mysql" + "://" +
                        "mysql.cs.iastate.edu" +
                        ":" + 3306 + "/",
                connectionProps);
        return conn;
    }

    @Override
    public List<Offer> getDriverOffers() {
        String sqlQuery = "select * from DRIVER_OFFERS_TABLE";

        List<Offer> offers = this.jdbcTemplate.query(
                "select first_name, last_name from t_actor",
                new DriverOfferMapper());


//        List<Offer> dummyList = new ArrayList<>();
//        for(int i=0 ; i<25 ; i++) {
//            Offer offer = new Offer(UserType.PASSENGER, 53180.08, null, "Des Moines", "Take me to Des Moines Yo", null);
//            dummyList.add(offer);
//        }
//        return dummyList;
    }

    @Override
    public List<Offer> getPassengerOffers() {
        String sqlQuery = "select * from PASSENGER_REQUESTS_TABLE";
        List<Offer> dummyList = new ArrayList<>();
        for(int i=0 ; i<5 ; i++) {
            Offer offer = new Offer(UserType.DRIVER, 53180.08, null, "Des Moines", "I'm going to Des Moines Yo", null);
            dummyList.add(offer);
        }
        return dummyList;
    }

}
