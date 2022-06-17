package perczynski.kamil.evolution.gameservice.domain.bets;

import lombok.Builder;
import lombok.Value;
import perczynski.kamil.evolution.gameservice.libs.Money;

@Value
@Builder
public class Bet {

    BetMode mode;
    Money stake;

}
