package com.phototravel.repository;

import com.phototravel.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
}
