package com.rachitgoyal.testperpule.exceptions;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Rachit Goyal on 16/01/19.
 */
public class PerpuleException extends Throwable {

    private static final String KEY_ERROR_LIST = "errorList";
    private String mErrorMessage;
    private int mErrorCode;
    private ServiceError mServiceError;

    public PerpuleException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

    public PerpuleException(int code, String message, String errorBody) {
        this("Error Code : " + code + " Message : " + message + getErrorDetailMessage(errorBody));
        mErrorCode = code;
        mErrorMessage = message;
        if (errorBody != null) {
            try {
                JSONObject jsonObject = new JSONObject(errorBody);
                if (!jsonObject.isNull(KEY_ERROR_LIST)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(KEY_ERROR_LIST);
                    mServiceError = new Gson().fromJson(jsonArray.getJSONObject(0).toString(), ServiceError.class);
                }
            } catch (JSONException e) {
            }
        }
    }

    private PerpuleException(String errorMessage) {
        super(errorMessage);
    }

    public PerpuleException(int code, String message, List<ServiceError> errorList) {
        this("Error Code : " + code + " Message : " + message + getErrorDetailMessage(errorList));
        mErrorCode = code;
        mErrorMessage = message;
        if (errorList != null && !errorList.isEmpty()) {
            mServiceError = errorList.get(0);
        }
    }

    private static String getErrorDetailMessage(List<ServiceError> errorList) {
        ServiceError serviceError;
        if (errorList != null && !errorList.isEmpty()) {
            serviceError = errorList.get(0);
            if (serviceError != null) {
                return " Error Message : " + serviceError.getErrorMessage() + " Cause " + serviceError.getCause();
            }
        }
        return "";
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    private static String getErrorDetailMessage(String errorBody) {
        ServiceError serviceError = null;
        if (errorBody != null) {
            try {
                JSONObject jsonObject = new JSONObject(errorBody);
                if (!jsonObject.isNull(KEY_ERROR_LIST)) {
                    JSONArray jsonArray = jsonObject.getJSONArray(KEY_ERROR_LIST);
                    serviceError = new Gson().fromJson(jsonArray.getJSONObject(0).toString(), ServiceError.class);
                }
            } catch (JSONException e) {
            }
            if (serviceError != null) {
                return " Error Message : " + serviceError.getErrorMessage() + " Cause " + serviceError.getCause();
            }
        }
        return "";
    }
}
