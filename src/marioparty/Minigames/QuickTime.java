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
import marioparty.Characters;
import marioparty.Constants;

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

    public boolean isPressed(int i) {
        return controller.getControllerInput(i).actions().size() == 1 && controller.getControllerInput(i).actions().contains(buttonType);
    }
}

public class QuickTime extends Minigame {

    private Button[] buttonList = new Button[15];
    private Characters characters;

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
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (buttonList[characters.characterAtI(i).getMinigameScore()].isPressed(i)) {
                characters.characterAtI(i).setMinigameScore(i);
            }
        }
    }

    @Override
    public int isDone() {//TODO: have actual finishing condition
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (Characters.characters[i].getMinigameScore() > 14) {
                return i;
            }
        }
        return -1;
    }
}
