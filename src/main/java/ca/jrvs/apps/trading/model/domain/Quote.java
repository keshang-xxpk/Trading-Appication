package ca.jrvs.apps.trading.model.domain;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "askPrice",
        "askSize",
        "bidPrice",
        "bidSize",
        "id",
        "lastPrice",
        "ticker"
})
public class Quote implements Entity<String> {

    @JsonProperty("askPrice")
    private Integer askPrice;
    @JsonProperty("askSize")
    private Integer askSize;
    @JsonProperty("bidPrice")
    private Integer bidPrice;
    @JsonProperty("bidSize")
    private Integer bidSize;
    @JsonProperty("lastPrice")
    private Integer lastPrice;
    @JsonProperty("ticker")
    private String ticker;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("askPrice")
    public Integer getAskPrice() {
        return askPrice;
    }

    @JsonProperty("askPrice")
    public void setAskPrice(Integer askPrice) {
        this.askPrice = askPrice;
    }

    @JsonProperty("askSize")
    public Integer getAskSize() {
        return askSize;
    }

    @JsonProperty("askSize")
    public void setAskSize(Integer askSize) {
        this.askSize = askSize;
    }

    @JsonProperty("bidPrice")
    public Integer getBidPrice() {
        return bidPrice;
    }

    @JsonProperty("bidPrice")
    public void setBidPrice(Integer bidPrice) {
        this.bidPrice = bidPrice;
    }

    @JsonProperty("bidSize")
    public Integer getBidSize() {
        return bidSize;
    }

    @JsonProperty("bidSize")
    public void setBidSize(Integer bidSize) {
        this.bidSize = bidSize;
    }

    @JsonProperty("id")
    @Override
    public String getId() {
        return ticker;
    }

    @JsonProperty("id")
    @Override
    public void setId(String id) {
        this.ticker = id;
    }

    @JsonProperty("lastPrice")
    public Integer getLastPrice() {
        return lastPrice;
    }

    @JsonProperty("lastPrice")
    public void setLastPrice(Integer lastPrice) {
        this.lastPrice = lastPrice;
    }

    @JsonProperty("ticker")
    public String getTicker() {
        return ticker;
    }

    @JsonProperty("ticker")
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }


}
