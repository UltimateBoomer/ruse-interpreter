package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.BoolExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record IifStmt(
    BoolExp bexp,
    Stmt trueStmt,
    Stmt falseStmt
) implements Stmt {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("iif"), trueStmt.toSExp(), falseStmt.toSExp());
    }
    
}
