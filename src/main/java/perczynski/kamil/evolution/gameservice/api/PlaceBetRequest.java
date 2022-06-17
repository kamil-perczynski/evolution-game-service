package perczynski.kamil.evolution.gameservice.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import perczynski.kamil.evolution.gameservice.domain.bets.BetMode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@Builder
@JsonDeserialize(builder = PlaceBetRequest.PlaceBetRequestBuilder.class)
public class PlaceBetRequest {

    @Min(100)
    @Max(1000)
    int stake;

    @NotNull
    BetMode mode;

    @JsonPOJOBuilder(withPrefix = "")
    public static class PlaceBetRequestBuilder {

    }
}
