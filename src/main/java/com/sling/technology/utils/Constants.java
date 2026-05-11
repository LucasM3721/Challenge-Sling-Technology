package com.sling.technology.utils;

public final class Constants {
    public static final String HOTEL_SEARCH_KAFKA_TOPIC = "hotel_availability_searches";
    public static final String HOTEL_SEARCH_KAFKA_GROUP = "hotel_search_group";
    public static final String HOTEL_SEARCH_TABLE_NAME = "hotel_searches";

    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_HOTEL_ID = "hotelId cannot be null or empty";
    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN = "checkIn cannot be null";
    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_OUT = "checkOut cannot be null";
    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_CHECK_IN_BEFORE_CHECK_OUT = "checkIn must be before checkOut";
    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_AGES = "ages cannot be null or empty";
    public static final String DOMAIN_EXCEPTION_HOTEL_SEARCH_AGE_VALUE = "ages must contain valid numbers >= 0";

    public static final String DOMAIN_EXCEPTION_COUNT_SERVICE_SEARCH_ID = "Search ID not found";

    public static final String INVALID_PAYLOAD_FORMAT = "Invalid payload format. Make sure dates are in dd/MM/yyyy format and fields are correct.";

}
