package com.phototravel.repositories;

import com.phototravel.entity.Destination;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PBezdienezhnykh on 026 26.7.2016.
 */
@Repository
public interface DestinationRepositoty extends CrudRepository<Destination, Integer> {

    @Query(value = "select destination_id from destination d where d.request_value= :requestValue"
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

}
