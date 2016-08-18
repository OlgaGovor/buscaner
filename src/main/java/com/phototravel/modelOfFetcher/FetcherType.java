package com.phototravel.modelOfFetcher;

/**
 * Created by Olga_Govor on 8/16/2016.
 */
public enum FetcherType {
    POLSKI_BUS("PolskiBus"),
    LUX_EXPRESS("Luxexpress");

    private String value;

    private FetcherType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
