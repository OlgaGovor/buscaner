package com.phototravel.services;

import com.phototravel.controllers.entity.PriceCalendar;
import com.phototravel.entity.Price;
import com.phototravel.repositories.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by Olga_Govor on 7/26/2016.
 */
@Service
public class PriceService {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Autowired
    PriceRepository priceRepository;

    private static final String SELECT_PRICES_FOR_CALENDAR_VIEW = "SELECT " +
            "  departure_date, " +
            "  min(p.price) AS price, " +
            "  currency " +
            "FROM price p " +
            "  JOIN route r on r.route_id = p.route_id " +
            "WHERE r.from_city_id = :fromCity " +
            "      AND r.to_city_id = :toCity " +
            "      AND p.departure_date >= date(:departureDate) " +
            "      AND p.departure_date <= date(:departureDateEnd) " +
            "GROUP BY p.departure_date " +
            "ORDER BY departure_date ASC;";

    public static String date_format = "date(:departureDate)";

    public static final String MOVE_PRICE_TO_ARCHIVE = "insert into price_archive " +
            "select route_id,\n" +
            "      departure_date,\n" +
            "      departure_time,\n" +
            "      price,\n" +
            "      last_update,\n" +
            "      arrival_time,\n" +
            "      currency, " +
            "      duration " +

            "from price " +
            "where route_id=:routeId " +
            "and departure_date= date(:departureDate)";

    public static final String DELETE_PRICE = "delete from price where route_id=:routeId and departure_date=date(:departureDate)";


    public List<PriceCalendar> pricesForCalendarView(int fromCityId, int toCityId,
                                                     Date departureDate, Date departureDateEnd) {


        MapSqlParameterSource namedParameters = new MapSqlParameterSource("fromCity", fromCityId);
        namedParameters.addValue("toCity", toCityId);
        namedParameters.addValue("departureDate", departureDate);
        namedParameters.addValue("departureDateEnd", departureDateEnd);


        List<PriceCalendar> result = jdbcTemplate.query(SELECT_PRICES_FOR_CALENDAR_VIEW, namedParameters, new RowMapper<PriceCalendar>() {
            @Override
            public PriceCalendar mapRow(ResultSet resultSet, int i) throws SQLException {
                return new PriceCalendar(resultSet.getDate(1), resultSet.getDouble(2), resultSet.getString(3));
            }
        });
        return result;

    }

    public void movePriceToArchive(int routId, Date departureDate) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("routeId", routId);
        namedParameters.addValue("departureDate", departureDate);

        int rowCount = 0;// jdbcTemplate.update(MOVE_PRICE_TO_ARCHIVE, namedParameters);
        logger.info(routId + " " + departureDate + " moved " + rowCount + " rows");

        rowCount = jdbcTemplate.update(DELETE_PRICE, namedParameters);
        logger.info(routId + " " + departureDate + " deleted " + rowCount + " rows");
    }

    public boolean save(Price price) {
        logger.info("save " + price);
        if (priceRepository.save(price) != null)
            return true;

        return false;
    }


}
