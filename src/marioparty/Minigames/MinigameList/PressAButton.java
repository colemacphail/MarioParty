package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import marioparty.Constants;
import marioparty.Minigames.Minigame;
import marioparty.Minigames.MinigameType;
import marioparty.Players;

/**
 *
 * @author Cole
 */
public class PressAButton extends Minigame {

    private boolean[] buttonWasPressed = new boolean[Constants.NUM_OF_PLAYERS];
    private final Controllers controllers = Controllers.getInstance();
    private final Random rangen = new Random();
    private InputAction desiredAction;
    private Players winningPlayer;

    public PressAButton() {
        super(MinigameType.FFA, 5000);
    }

    @Override
    public void init() {
        this.desiredAction = InputAction.values()[rangen.nextInt(InputAction.values().length - 5) + 5];
    }

    @Override
    public void run() {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawString("Press " + this.desiredAction, this.dc.getWidth() / 2, this.dc.getHeight() / 2);
    }

    @Override
    public Set isDone() {
        Set<Players> winningPlayers = new HashSet<>();

        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {

            if (Controllers.getInstance().getControllerInput(i).actions().contains(this.desiredAction) && this.winningPlayer == null) {
                this.winningPlayer = Players.values()[i];
            }
        }

        if (this.winningPlayer != null) {
            winningPlayers.add(winningPlayer);
        }

        if (this.dc.getKeyPress(' ')) {
            winningPlayers.add(Players.values()[0]);
        }

        return winningPlayers;
    }

}
