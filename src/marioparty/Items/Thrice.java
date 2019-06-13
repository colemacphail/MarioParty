package marioparty.Items;

import java.awt.Color;
import marioparty.Board;
import marioparty.RollState;

/**
 *
 * @author colem
 */
//STRUCTURE FOR A CANDY
public class Thrice extends Item {

    @Override
    public void triggerEvent() {
        Board.getInstance().setRollState(RollState.THRICE);
    }

    @Override
    public void draw(int x, int y) {
        this.dc.setPaint(Color.BLACK);
        this.dc.drawString('3', x, y);
    }

}
