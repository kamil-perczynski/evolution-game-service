package perczynski.kamil.evolution.gameservice.domain.bets;

import lombok.Builder;
import lombok.Value;
import perczynski.kamil.evolution.gameservice.domain.rounds.Win;
import perczynski.kamil.evolution.gameservice.libs.Money;

@Value
@Builder
public class GameRound {

    String id;
    Bet bet;
    Win win;
    Money nextBalance;
    String playerId;

}
