package com.phototravel.repositories;

import com.phototravel.entity.Destination;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface DestinationRepository extends CrudRepository<Destination, Integer> {

    @Override
    @CacheEvict("destination")
    Destination save(Destination s);

    @Override
    @Cacheable("destination")
    Iterable<Destination> findAll();

   /* @Query(value = "select destination_id from destination d where d.request_value= :requestValue"
            , nativeQuery = true
    )
    Integer getDestIdByRequestValue(@Param("requestValue") String requestValue);


    @Query(value = "select city_id from destination d " +
            " where d.request_value= :requestValue"
            , nativeQuery = true
    )
    Integer getCityIdByRequestValue(@Param("requestValue") String requestValue);

    @Query(value = "select request_value from destination d where d.company_id= :companyId"
            , nativeQuery = true
    )
    List<String> getRequestValuesByCompanyId(@Param("companyId") int companyId);

    @Query(value = "select request_value from destination d where d.company_id= :companyId" +
                    " and d.city_id= :cityId"
                    , nativeQuery = true
    )
    List<String> getRequestValuesByCompanyIdAndCityId(@Param("companyId") int companyId,
                                                      @Param("cityId") int cityId);

    @Query(value = "select d.destination_name from destination d where d.destination_id= :destinationId"
            , nativeQuery = true
    )
    String getDestinationNameByDestinationId(@Param("destinationId") int destinationId);*/
}
