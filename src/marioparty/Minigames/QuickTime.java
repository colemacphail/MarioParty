/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

import ControllerInput.Controllers;
import ControllerInput.GamepadInput;
import ControllerInput.InputAction;
import java.util.Random;
import marioparty.Board;

/**
 *
 * @author arnav
 */
// People will have to press the sequence of buttons before anyone else.
class Button {

    private final Random rand = new Random();
    private InputAction buttonType;
    private Controllers controller;

    public Button() {
        controller = Controllers.getInstance();
        buttonType = InputAction.values()[rand.nextInt(6) + 5];
    }

    public boolean isPressed() {
        for (int i = 0; i < Board.getInstance().getNumOfPlayers(); i++){
            return controller.getControllerInput(i).actions().size() == 1 && controller.getControllerInput(i).actions().contains(buttonType);
        }
        return false;
    }
}

public class QuickTime extends Minigame {

    private Button[] buttonList = new Button[15];

    public QuickTime(MinigameType type, long timeout) {
        super(type, timeout);
    }

    @Override
    public void init() {
        for (int i = 0; i < buttonList.length; i++) {
            buttonList = new Button[i];
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int isDone() {//TODO: have actual finishing condition
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }
}
