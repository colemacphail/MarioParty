/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import ControllerInput.InputAction;
import java.util.Random;

/**
 *
 * @author arnav
 */
class Button {

    private final Random rand = new Random();
    private InputAction buttonType;

    public Button() {
        int i = rand.nextInt(6) + 5;
        buttonType = InputAction.values()[i];
    }
}

public class QuickTime extends Minigame {

    public QuickTime(MinigameType type, long timeout) {
        super(type, timeout);
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDone() {
        return false;
    }

}
