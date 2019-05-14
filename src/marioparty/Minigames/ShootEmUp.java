/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marioparty.Minigames;

/**
 *
 * @author Jacob
 */
class Target {

}


public class ShootEmUp extends Minigame {

    public ShootEmUp() {
        super(MinigameType.FFA, 10000);
    }

    @Override
    public void init() {

    }

    @Override
    public void run() {

        this.dc.drawString("SHOOT EM UP", 450, 20);

    }

    @Override
    public int isDone() {//TODO: have actual finishing condition
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }
}
