package com.phototravel.services.oneTimeServices.outerRequests;

import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.MultivaluedMap;

/**
 * Created by Olga_Govor on 7/20/2016.
 */
public class SendRequestPolskiBus extends BaseSendRequest {

    public String getCookie (ClientResponse response)
    {
        String headerStr = "";
        if (response.getClientResponseStatus() == ClientResponse.Status.OK)
        {
            MultivaluedMap<String, String> headers = response.getHeaders();
            headerStr = headers.get("Set-Cookie").toString();
        }

        String cookieValue = headerStr.substring(19,43);
        return cookieValue;
    }
}
