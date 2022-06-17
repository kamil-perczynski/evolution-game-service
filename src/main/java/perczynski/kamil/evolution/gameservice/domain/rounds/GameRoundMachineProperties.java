package perczynski.kamil.evolution.gameservice.domain.rounds;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Value
@Validated
@ConstructorBinding
@ConfigurationProperties("game-round")
public class GameRoundMachineProperties {

    @NotNull
    Integer smallWinMultiplier;
    @NotNull
    Integer mediumWinMultiplier;
    @NotNull
    Integer bigWinMultiplier;

}
