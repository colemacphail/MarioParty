/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames.MinigameList;

import ControllerInput.Controllers;
import ControllerInput.InputAction;
import DLibX.DConsole;
import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import marioparty.Characters;
import marioparty.Console;
import marioparty.Constants;
import marioparty.Minigames.Minigame;
import marioparty.Minigames.MinigameType;
import marioparty.Players;

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
        this.controller = Controllers.getInstance();
        this.buttonType = InputAction.values()[this.rand.nextInt(6) + 5];
    }

    public boolean isPressed(int i) {
        return this.controller.getControllerInput(i).actions().size() == 1
                && this.controller.getControllerInput(i).actions().contains(this.buttonType);
    }

    public boolean nonePressed(int i) {
        return this.controller.getControllerInput(i).actions().isEmpty();
    }

    public InputAction getButtonType() {
        return this.buttonType;
    }

}

public class QuickTime extends Minigame {

    private Button[] buttonList = new Button[15];
    private Characters characters;
    private DConsole cons;
    boolean wasPressed = false;

    public QuickTime() {
        super(MinigameType.FFA, 15000);
    }

    @Override
    public void init() {
        this.cons = Console.getInstance();
        this.characters = Characters.getInstance();
        for (int i = 0; i < this.buttonList.length; i++) {
            this.buttonList[i] = new Button();
        }
    }

    @Override
    public void run() {
        this.cons.setPaint(Color.BLACK);
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            this.cons.drawString(this.buttonList[this.characters.getCharacter(i).getMinigameScore()].getButtonType(),
                    this.cons.getWidth() / 4 * (i % 2 == 1 ? 3 : 1),
                    this.cons.getHeight() / 4 * (i > 1 ? 3 : 1));
            if (this.buttonList[this.characters.getCharacter(i).getMinigameScore()].isPressed(i) && !this.wasPressed) {
                this.characters.getCharacter(i).changeMinigameScore(1);
                this.wasPressed = true;
            } else if (this.buttonList[this.characters.getCharacter(i).getMinigameScore()].nonePressed(i)) {
                this.wasPressed = false;
            }
        }
        this.displayMinigameScoreCornerSplitscreen();
        this.drawCornerSplitscreen();
    }

    @Override
    public Set isDone() {
        Set<Players> winningPlayers = new HashSet<>();
        for (int i = 0; i < Constants.NUM_OF_PLAYERS; i++) {
            if (Characters.characters[i].getMinigameScore() > 14) {
                winningPlayers.add(Players.values()[i]);
            }
        }
        return winningPlayers;
    }
}
