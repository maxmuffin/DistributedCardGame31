package distributedLogic.net.messages;

import distributedLogic.game.Move;

import java.util.Date;

public class GameMessage extends Message implements Cloneable {

    private int id;
    private Move move;
    private String message;// TODO provissorio per inviare il messaggio
    private int nodeCrashedId;
    private int howManyCrash;

    // TODO provvisorio
    public GameMessage(int originId, int id, String message, int howManyCrash) {
        super(originId, id);
        this.id = id;
        this.message = message;
        this.nodeCrashedId = -1;
        this.howManyCrash = howManyCrash;
    }

    /**
     * Metodo che inizializza un GameMessage classico
     *
     * @param originId
     * @param id
     * @param howManyCrash
     */
    public GameMessage(int originId, int id, Move move, int howManyCrash) {
        super(originId, id);
        this.id = id;
        this.move = move;
        this.nodeCrashedId = -1;
        this.howManyCrash = howManyCrash;
    }

    /**
     * Metodo che inizializza un GameMessage utilizzato per informare
     * la rete del crash di un dato nodo.
     *
     * @param origId
     * @param id
     * @param nodeCrashedId
     * @param howManyCrash
     */
    public GameMessage(int origId, int id, int nodeCrashedId, int howManyCrash) {
        super(origId, id);
        this.id = id;
        this.nodeCrashedId = nodeCrashedId;
        this.move = null;
        this.message = "nodo crash: " + nodeCrashedId;// TODO provvisorio
        this.howManyCrash = howManyCrash;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "# " + id + "[ " + this.message + " ]" + super.toString();
    }

    /**
     * Metodo utilizzato per clonare un istanza della classe
     *
     * @return
     */
    public Object clone() {
        GameMessage m;
        if (nodeCrashedId == -1) {
            // m = new GameMessage(getOriginId(), id, move, howManyCrash);
            // TODO prova
            m = new GameMessage(getOriginId(), id, message, howManyCrash);
        } else {
            m = new GameMessage(getOriginId(), id, nodeCrashedId, howManyCrash);
        }
        m.setFromId(getFromId());
        return m;
    }

    public Move getMove() {
        return move;
    }

    public boolean getBusso() {
        return move.isBusso();
    }

    public int getNodeCrashed() {
        return nodeCrashedId;
    }

    public void incrementCrash() {
        howManyCrash = howManyCrash + 1;
    }

    public int getHowManyCrash() {
        return howManyCrash;
    }

    // TODO provvisorio
    public String getMessage() {
        return message;
    }
}