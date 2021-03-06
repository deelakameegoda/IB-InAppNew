package com.interblocks.imobile.api.inapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BalanceList
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-05-09T08:34:42.156Z")
public class BalanceList {
    @JsonProperty("walletId")
    private String walletId = null;

    @JsonProperty("instruments")
    private List<Instrument> instruments = null;

    @JsonProperty("statusCode")
    private String statusCode = null;

    @JsonProperty("extErrorCode")
    private String extErrorCode = null;

    @JsonProperty("statusDescription")
    private String statusDescription = null;

    @JsonProperty("failReason")
    private String failReason = null;

    public BalanceList walletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    /**
     * Get walletId
     *
     * @return walletId
     **/
    @JsonProperty("walletId")
    @ApiModelProperty(example = "1231456", value = "")
    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public BalanceList instruments(List<Instrument> instruments) {
        this.instruments = instruments;
        return this;
    }

    public BalanceList addInstrumentsItem(Instrument instrumentsItem) {
        if (this.instruments == null) {
            this.instruments = new ArrayList<Instrument>();
        }
        this.instruments.add(instrumentsItem);
        return this;
    }

    /**
     * Get instruments
     *
     * @return instruments
     **/
    @JsonProperty("instruments")
    @ApiModelProperty(value = "")
    public List<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instrument> instruments) {
        this.instruments = instruments;
    }

    public BalanceList statusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * Get statusCode
     *
     * @return statusCode
     **/
    @JsonProperty("statusCode")
    @ApiModelProperty(value = "")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public BalanceList extErrorCode(String extErrorCode) {
        this.extErrorCode = extErrorCode;
        return this;
    }

    /**
     * Get extErrorCode
     *
     * @return extErrorCode
     **/
    @JsonProperty("extErrorCode")
    @ApiModelProperty(value = "")
    public String getExtErrorCode() {
        return extErrorCode;
    }

    public void setExtErrorCode(String extErrorCode) {
        this.extErrorCode = extErrorCode;
    }

    public BalanceList statusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
        return this;
    }

    /**
     * Get statusDescription
     *
     * @return statusDescription
     **/
    @JsonProperty("statusDescription")
    @ApiModelProperty(value = "")
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public BalanceList failReason(String failReason) {
        this.failReason = failReason;
        return this;
    }

    /**
     * Get failReason
     *
     * @return failReason
     **/
    @JsonProperty("failReason")
    @ApiModelProperty(value = "")
    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BalanceList balanceList = (BalanceList) o;
        return Objects.equals(this.walletId, balanceList.walletId) &&
                Objects.equals(this.instruments, balanceList.instruments) &&
                Objects.equals(this.statusCode, balanceList.statusCode) &&
                Objects.equals(this.extErrorCode, balanceList.extErrorCode) &&
                Objects.equals(this.statusDescription, balanceList.statusDescription) &&
                Objects.equals(this.failReason, balanceList.failReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(walletId, instruments, statusCode, extErrorCode, statusDescription, failReason);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class BalanceList {\n");

        sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n");
        sb.append("    instruments: ").append(toIndentedString(instruments)).append("\n");
        sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
        sb.append("    extErrorCode: ").append(toIndentedString(extErrorCode)).append("\n");
        sb.append("    statusDescription: ").append(toIndentedString(statusDescription)).append("\n");
        sb.append("    failReason: ").append(toIndentedString(failReason)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

