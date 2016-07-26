package com.phototravel.repository;

import com.phototravel.entity.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
}
