package ControllerInput;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GamepadInput {
    //VARIABLES
    private final ControllerManager controllers;
    private int controllerInUse = 0;
    
    //CONSTRUCTORS
    public GamepadInput(int controller) {
        this.controllerInUse = controller;
        this.controllers = new ControllerManager();
        this.controllers.initSDLGamepad();
    }

    
    //INPUTS
    public Set<InputAction> actions() {
        ControllerState currState = this.controllers.getState(this.controllerInUse);
        if (!currState.isConnected) {
            System.out.println("NOT CONNECTED");
            return Collections.emptySet();
        }

        Set<InputAction> actions = new HashSet<>();
        //Get what buttons are being pressed, then add them to the set
        if (currState.dpadLeft) {
            actions.add(InputAction.MOVE_LEFT);
            System.out.println("LEFT");
        }
        if (currState.dpadRight) {
            actions.add(InputAction.MOVE_RIGHT);
            System.out.println("right");
        }
        if (currState.dpadUp) {
            actions.add(InputAction.MOVE_UP);
        }
        if (currState.dpadDown) {
            actions.add(InputAction.MOVE_DOWN);
        }
        if (currState.a) {
            actions.add(InputAction.A);
            System.out.println("a");
        }
        if (currState.b) {
            actions.add(InputAction.B);
        }
        if (currState.x) {
            actions.add(InputAction.X);
        }
        if (currState.y) {
            actions.add(InputAction.Y);
        }
        if (currState.lb) {
            actions.add(InputAction.L);
        }
        if (currState.rb) {
            actions.add(InputAction.R);
        }
        return actions;
    }
}
