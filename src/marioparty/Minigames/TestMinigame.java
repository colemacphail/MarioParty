package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import marioparty.Constants;

/**
 *
 * @author Cole
 */
public class TestMinigame extends Minigame {

    public TestMinigame() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {
        this.dc.drawString("Press A", 450, 300);
    }

    @Override
    public int isDone() {
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {

            if (Controllers.getInstance().getControllerInput(i).actions().contains(InputAction.A)) {
                System.out.println(i);
                return i;
            }
        }
        return -1;
    }

}
