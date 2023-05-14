package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Exp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record SetStmt(
    String var,
    Exp value
)  implements Stmt {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("set"), new Atom(var), value.toSExp());
    }
    
}
