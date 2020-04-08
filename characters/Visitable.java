package characters;

public interface Visitable {
    void visit(Pyromancer character);
    void visit(Knight character);
    void visit(Wizard character);
    void visit(Rogue character);
}
