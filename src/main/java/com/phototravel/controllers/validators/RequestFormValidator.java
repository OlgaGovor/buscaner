package com.phototravel.controllers.validators;

import com.phototravel.controllers.entity.RequestForm;
import com.phototravel.services.FindBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestFormValidator {

    @Autowired
    FindBusService findBusService;

    public String validateRequestForm(RequestForm requestForm) {
        String defaultErrorMessage = "requestFormValidation.invalidRequestForm";
        if (requestForm.getFromCity() < 0)
            return defaultErrorMessage;
        if (requestForm.getToCity() < 0)
            return defaultErrorMessage;

        if (requestForm.getDepartureDate() == null || requestForm.getDepartureDate().isEmpty())
            return defaultErrorMessage;
        if (requestForm.isScanForPeriod() && (requestForm.getDepartureDateEnd() == null || requestForm.getDepartureDateEnd().isEmpty()))
            return defaultErrorMessage;
        if (requestForm.isScanForPeriod() && (
                (requestForm.getDepartureDate() == null || requestForm.getDepartureDate().isEmpty())
                        ||
                        (requestForm.getDepartureDateEnd() == null || requestForm.getDepartureDateEnd().isEmpty())
        ))
            return defaultErrorMessage;

        if (!findBusService.checkIfRouteExists(requestForm.getFromCity(), requestForm.getToCity(), false)) {
            return "requestFormValidation.noRouteExists";
        }

        return null;
    }

}
