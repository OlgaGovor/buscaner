package com.phototravel.dao;

import com.phototravel.dataCollectors.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TestDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SAVE_ROUTE = "insert into routes values(?,?,?,?,?)";
    public static final String SELECT_ALL_ROUTES = "select * from routes";

    public void saveRoute(Route route){
        Object[] args = new Object[5];
        args[0] = route.getTo();
        args[1] = route.getFrom();
        args[2] = route.getMinPrice();
        args[3] = route.getDateOfTrip();
        args[4] = route.getLastUpdateDate();
        System.out.println("requestPrams="+args);
        jdbcTemplate.update(SAVE_ROUTE, args);
    }


    public List<Route> readAllRoutes(){

        List<Route> routeList = jdbcTemplate.query(SELECT_ALL_ROUTES, new RowMapper<Route>() {
            @Override
            public Route mapRow(ResultSet resultSet, int i) throws SQLException {
                Route route = new Route();
                route.setTo(resultSet.getString(1));
                route.setFrom(resultSet.getString(2));
                route.setMinPrice(resultSet.getDouble(3));
                route.setDateOfTrip(resultSet.getDate(4));
                route.setLastUpdateDate(resultSet.getDate(5));
                return route;
            }
        });

        return routeList;

    }


}