package com.interblocks.imobile.api.inapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Balance
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-05-09T08:34:42.156Z")
public class Balance {
    @JsonProperty("account")
    private String account = null;

    @JsonProperty("currency")
    private String currency = null;

    @JsonProperty("balance")
    private String balance = null;

    public Balance account(String account) {
        this.account = account;
        return this;
    }

    /**
     * Get account
     *
     * @return account
     **/
    @JsonProperty("account")
    @ApiModelProperty(example = "42722a06025bb493295e496535396d45", value = "")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Balance currency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Get currency
     *
     * @return currency
     **/
    @JsonProperty("currency")
    @ApiModelProperty(example = "USD", value = "")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Balance balance(String balance) {
        this.balance = balance;
        return this;
    }

    /**
     * Get balance
     *
     * @return balance
     **/
    @JsonProperty("balance")
    @ApiModelProperty(example = "14.32", value = "")
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Balance balance = (Balance) o;
        return Objects.equals(this.account, balance.account) &&
                Objects.equals(this.currency, balance.currency) &&
                Objects.equals(this.balance, balance.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, currency, balance);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Balance {\n");

        sb.append("    account: ").append(toIndentedString(account)).append("\n");
        sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
        sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
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

