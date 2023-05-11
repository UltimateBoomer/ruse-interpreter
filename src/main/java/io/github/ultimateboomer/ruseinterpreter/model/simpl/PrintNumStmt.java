package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record PrintNumStmt(
    ArithExp exp
) implements Stmt {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("print"), exp.toSExp());
    }
    
}
