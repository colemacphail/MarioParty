package ControllerInput;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GamepadInput {

    private final ControllerManager controllers;

    private int controllerInUse = 0;

    public GamepadInput(int controller) {
        this.controllerInUse = controller;
        controllers = new ControllerManager();
        controllers.initSDLGamepad();
    }

    public Set<InputAction> actions() {
        ControllerState currState = controllers.getState(this.controllerInUse);
        if (!currState.isConnected) {
            return Collections.emptySet();
        }

        Set<InputAction> actions = new HashSet<>();
        if (currState.dpadLeft) {
            actions.add(InputAction.MOVE_LEFT);
        }
        if (currState.dpadRight) {
            actions.add(InputAction.MOVE_RIGHT);
        }
        if (currState.dpadUp) {
            actions.add(InputAction.MOVE_UP);
        }
        if (currState.dpadDown) {
            actions.add(InputAction.MOVE_DOWN);
        }
        return actions;
    }
}
