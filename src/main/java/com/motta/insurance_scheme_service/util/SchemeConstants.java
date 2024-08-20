package com.motta.insurance_scheme_service.util;

public class SchemeConstants {
    public static final String LOG_MESSAGE_SCHEME_NOT_FOUND = "Scheme id not found. Please enter different id";
    public static final String LOG_MESSAGE_SCHEME_PERSISTED = "Scheme id = {} persisted";
    public static final String LOG_MESSAGE_SCHEME_UPDATE_FAILED = "Updating scheme id = {} has failed.";
    public static final String LOG_FETCHING_ASSOCIATIONS_FAILED = "Failed fetching associations for scheme Id.";

    public static final String EXCEPTION_MESSAGE_SCHEME_NOT_FOUND = "Scheme id = {} already Exists!";
    public static final String EXCEPTION_MESSAGE_INVALID_FROM_DATE = "From Date should be less than To Date";
    public static final String EXCEPTION_MESSAGE_SCHEME_ID_IS_MANDATORY = "Scheme Id is mandatory";
    public static final String EXCEPTION_MESSAGE_SCHEME_NAME_IS_MANDATORY = "Scheme Name is mandatory";
    public static final String EXCEPTION_MESSAGE_SCHEME_ID_LESS_THAN_INITIAL_VALUE = "Scheme Id must not be less than the initial value of: ";
    public static final String EXCEPTION_MESSAGE_TO_DATE_IS_MANDATORY = "Valid To Date is mandatory";
    public static final String EXCEPTION_MESSAGE_SCHEME_TYPE_IS_MANDATORY = "Scheme Type is mandatory";
    public static final String EXCEPTION_MESSAGE_SCHEME_AMOUNT_IS_MANDATORY = "Scheme Amount is mandatory";
    public static final String EXCEPTION_MESSAGE_SHARE_IS_MANDATORY = "Share  is mandatory";
    public static final String EXCEPTION_MESSAGE_COMMISSION_IS_MANDATORY = "Commission  is mandatory";
    public static final String EXCEPTION_MESSAGE_ASSOCIATIONS_NOT_FOUND = "Associations not found";

    public static final String URL_GET_ASSOCIATIONS_BY_SCHEME_ID = "http://localhost:8900/getassociationsbyschemeid/";


}
