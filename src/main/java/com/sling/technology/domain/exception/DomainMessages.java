package com.sling.technology.domain.exception;

/**
 * Domain validation error messages.
 */
public final class DomainMessages {

    private DomainMessages() {}

    public static final String HOTEL_ID_REQUIRED = "hotelId cannot be null or empty";
    public static final String CHECK_IN_REQUIRED = "checkIn cannot be null";
    public static final String CHECK_OUT_REQUIRED = "checkOut cannot be null";
    public static final String CHECK_IN_PAST = "checkIn cannot be in the past";
    public static final String CHECK_IN_BEFORE_CHECK_OUT = "checkIn must be before checkOut";
    public static final String AGES_REQUIRED = "ages cannot be null or empty";
    public static final String AGE_INVALID = "ages must contain valid numbers >= 0";
    public static final String SEARCH_NOT_FOUND = "Search ID not found";
}
