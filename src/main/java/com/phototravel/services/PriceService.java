package com.phototravel.services;

import com.phototravel.controllers.entity.PriceCalendar;
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

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final String SELECT_PRICES_FOR_CALENDAR_VIEW = "SELECT " +
            "  departure_date, " +
            "  min(p.price) AS price " +
            "FROM price p " +
            "  JOIN route r on r.route_id = p.route_id " +
            "WHERE r.from_city_id = :fromCity " +
            "      AND r.to_city_id = :toCity " +
            "      AND p.departure_date >= date(:departureDate) " +
            "      AND p.departure_date <= date(:departureDateEnd) " +
            "GROUP BY p.departure_date " +
            "ORDER BY departure_date ASC;";

    public List<PriceCalendar> pricesForCalendarView(int fromCityId, int toCityId,
                                                     Date departureDate, Date departureDateEnd) {


        MapSqlParameterSource namedParameters = new MapSqlParameterSource("fromCity", fromCityId);
        namedParameters.addValue("toCity", toCityId);
        namedParameters.addValue("departureDate", departureDate);
        namedParameters.addValue("departureDateEnd", departureDateEnd);


        List<PriceCalendar> result = jdbcTemplate.query(SELECT_PRICES_FOR_CALENDAR_VIEW, namedParameters, new RowMapper<PriceCalendar>() {
            @Override
            public PriceCalendar mapRow(ResultSet resultSet, int i) throws SQLException {
                return new PriceCalendar(resultSet.getDate(1), resultSet.getDouble(2));
            }
        });
        return result;

    }

}
