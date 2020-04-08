package angels;

import characters.Knight;
import characters.Pyromancer;
import characters.Rogue;
import characters.Wizard;

public interface VisitableByAngels {
    void visitBYAngels(Pyromancer character);
    void visitBYAngels(Knight character);
    void visitBYAngels(Wizard character);
    void visitBYAngels(Rogue character);
}
