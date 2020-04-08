package angels;

import characters.Rogue;
import characters.Wizard;
import characters.Pyromancer;
import characters.Knight;
import characters.Pair;
import greatmagician.Observable;
import java.util.ArrayList;

public abstract class Angel {
    private Pair<Integer, Integer> angelCoordonates;
    private String name;
    private ArrayList<Observable> observators;
    /** */
    public String getAngelName() {

        return name;
    }
    /** */
    public Pair<Integer, Integer> getAngelCoordonates() {

        return angelCoordonates;
    }

    protected Angel(final Pair<Integer, Integer> angelCoordonates, final String name) {
        this.angelCoordonates = angelCoordonates;
        this.name = name;
        observators = new ArrayList<>();
    }
    /**adugare de observatori. */
    public void addobserver(final Observable observer) {

        observators.add(observer);
    }
    /**notificarea observatorilor. */
    public void notifyAllObservers(final StringBuilder information) {
        for (Observable observator : observators) {
            observator.update(information);
        }
    }
    /**mesajul care indica aparitia ingerului. */
    public StringBuilder apperanceMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Angel ");
        message.append(this.getAngelName());
        message.append(" was spawned at ");
        message.append(this.getAngelCoordonates().getFirst());
        message.append(" ");
        message.append(this.getAngelCoordonates().getSecond());
        return message;
    }

    public abstract void visitBYAngels(Pyromancer character);
    public  abstract void visitBYAngels(Knight character);
    public abstract void visitBYAngels(Wizard character);
    public  abstract void visitBYAngels(Rogue character);
}
