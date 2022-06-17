package perczynski.kamil.evolution.gameservice.domain.rounds;

import lombok.Value;
import perczynski.kamil.evolution.gameservice.libs.Money;

@Value
public class Win {

    Money payout;
    boolean freeRound;

}
