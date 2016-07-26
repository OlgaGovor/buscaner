package com.phototravel.repository;

import com.phototravel.entity.Destination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface DestinationRepositoty extends CrudRepository<Destination, Long> {


}
