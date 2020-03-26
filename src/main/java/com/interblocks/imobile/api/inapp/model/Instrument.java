package com.interblocks.imobile.api.inapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Instrument
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-05-09T08:34:42.156Z")
public class Instrument {
    @JsonProperty("type")
    private String type = null;

    @JsonProperty("instrumentName")
    private String instrumentName = null;

    @JsonProperty("instrumentNumber")
    private String instrumentNumber = null;

    @JsonProperty("instrumentRef")
    private String instrumentRef = null;

    @JsonProperty("maskInstrumentNumber")
    private String maskInstrumentNumber = null;

    @JsonProperty("expYear")
    private String expYear = null;

    @JsonProperty("expMonth")
    private String expMonth = null;

    @JsonProperty("balances")
    private List<Balance> balances = null;


    /**
     * Get type
     *
     * @return type
     **/
    @JsonProperty("type")
    @ApiModelProperty(example = "SAVINGS ACCOUNT", value = "")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instrument instrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
        return this;
    }

    /**
     * Get instrumentName
     *
     * @return instrumentName
     **/
    @JsonProperty("instrumentName")
    @ApiModelProperty(example = "Gandalf", value = "")
    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public Instrument instrumentRef(String instrumentRef) {
        this.instrumentRef = instrumentRef;
        return this;
    }

    /**
     * Get instrumentRef
     *
     * @return instrumentRef
     **/
    @JsonProperty("instrumentRef")
    @ApiModelProperty(example = "string", value = "")
    public String getInstrumentRef() {
        return instrumentRef;
    }

    public void setInstrumentRef(String instrumentRef) {
        this.instrumentRef = instrumentRef;
    }

    public Instrument instrumentNumber(String instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
        return this;
    }

    /**
     * Get instrumentNumber
     *
     * @return instrumentNumber
     **/
    @JsonProperty("instrumentNumber")
    @ApiModelProperty(example = "42722a06025bb493295e496535396d45", value = "")
    public String getInstrumentNumber() {
        return instrumentNumber;
    }

    public void setInstrumentNumber(String instrumentNumber) {
        this.instrumentNumber = instrumentNumber;
    }


    public Instrument maskInstrumentNumber(String maskInstrumentNumber) {
        this.maskInstrumentNumber = maskInstrumentNumber;
        return this;
    }

    /**
     * Get maskInstrumentNumber
     *
     * @return maskInstrumentNumber
     **/
    @JsonProperty("maskInstrumentNumber")
    @ApiModelProperty(example = "431XX....44", value = "")
    public String getMaskInstrumentNumber() {
        return maskInstrumentNumber;
    }

    public void setMaskInstrumentNumber(String maskInstrumentNumber) {
        this.maskInstrumentNumber = maskInstrumentNumber;
    }

    public Instrument expYear(String expYear) {
        this.expYear = expYear;
        return this;
    }

    /**
     * Get expYear
     *
     * @return expYear
     **/
    @JsonProperty("expYear")
    @ApiModelProperty(example = "18", value = "")
    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public Instrument expMonth(String expMonth) {
        this.expMonth = expMonth;
        return this;
    }

    /**
     * Get expMonth
     *
     * @return expMonth
     **/
    @JsonProperty("expMonth")
    @ApiModelProperty(example = "11", value = "")
    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public Instrument balances(List<Balance> balances) {
        this.balances = balances;
        return this;
    }

    public Instrument addBalancesItem(Balance balancesItem) {
        if (this.balances == null) {
            this.balances = new ArrayList<Balance>();
        }
        this.balances.add(balancesItem);
        return this;
    }

    /**
     * Get balances
     *
     * @return balances
     **/
    @JsonProperty("balances")
    @ApiModelProperty(value = "")
    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instrument instrument = (Instrument) o;
        return Objects.equals(this.type, instrument.type) &&
                Objects.equals(this.instrumentName, instrument.instrumentName) &&
                Objects.equals(this.instrumentRef, instrument.instrumentRef) &&
                Objects.equals(this.instrumentNumber, instrument.instrumentNumber) &&
                Objects.equals(this.maskInstrumentNumber, instrument.maskInstrumentNumber) &&
                Objects.equals(this.expYear, instrument.expYear) &&
                Objects.equals(this.expMonth, instrument.expMonth) &&
                Objects.equals(this.balances, instrument.balances);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, instrumentName, instrumentRef, instrumentNumber, maskInstrumentNumber, expYear, expMonth, balances);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Instrument {\n");

        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    instrumentName: ").append(toIndentedString(instrumentName)).append("\n");
        sb.append("    instrumentRef: ").append(toIndentedString(instrumentRef)).append("\n");
        sb.append("    instrumentNumber: ").append(toIndentedString(instrumentNumber)).append("\n");
        sb.append("    maskInstrumentNumber: ").append(toIndentedString(maskInstrumentNumber)).append("\n");
        sb.append("    expYear: ").append(toIndentedString(expYear)).append("\n");
        sb.append("    expMonth: ").append(toIndentedString(expMonth)).append("\n");
        sb.append("    balances: ").append(toIndentedString(balances)).append("\n");
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

