package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record PrintStrStmt(
    String str
) implements Stmt {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("print"), new Atom(String.format("\"%s\"", str)));
    }
    
}
