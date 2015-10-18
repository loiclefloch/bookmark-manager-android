package bm.bookmark_manager.common.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import retrofit.RetrofitError;

/**
 * RestError represent an error from the API.
 */
public class RestError
{
    private static final Integer DEFAULT_ERROR_CODE = -1;
    /**
     * The api error code or the http status if the json can't be unserialize.
     */
    @SerializedName("code")
    private Integer code = DEFAULT_ERROR_CODE;

    /**
     * The api error message
     */
    @SerializedName("message")
    private String strMessage = "";

    /**
     * The retrofit error
     */
    @Expose
    private RetrofitError error;

    /**
     * Return if the code contains an api error code or and http error code
     * @return boolean
     */
    public boolean isApiError() {
        return this.code != null && !Objects.equals(this.code, DEFAULT_ERROR_CODE);
    }

    public retrofit.client.Response getResponse() {
        return error.getResponse();
    }

    public int getStatusCode() {
        return error.getResponse().getStatus();
    }

    @Override
    public String toString() {
        return "ResError\n"
                + "[" + code + "] " + strMessage + "\n"
                + error;
    }

    // -- getters and setters

    public String getStrMessage() {
        return strMessage;
    }

    public void setStrMessage(String strMessage) {
        this.strMessage = strMessage;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public RetrofitError getError() {
        return error;
    }

    public void setError(RetrofitError error) {
        this.error = error;
    }
}