package marioparty.Minigames;

/**
 *
 * @author Cole
 */
public class Masher extends Minigame {

    public Masher(MinigameType type, long timeout) {
        super(type, timeout);
    }

    @Override
    public void init() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int isDone() {//TODO: have actual finishing condition
        if (this.dc.isKeyPressed(' ')) {
            return 1;
        }
        return -1;
    }

}
