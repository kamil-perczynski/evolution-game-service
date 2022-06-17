package perczynski.kamil.evolution.gameservice.domain;

import lombok.Builder;
import lombok.Value;
import perczynski.kamil.evolution.gameservice.libs.Money;

@Value
@Builder(toBuilder = true)
public class Player {

    String playerId;
    Money balance;
    boolean freeRoundAvailable;

}
