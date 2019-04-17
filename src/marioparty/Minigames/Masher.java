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
    public boolean isDone() {
        return System.currentTimeMillis() > this.startTime + this.timeout;
    }

}
