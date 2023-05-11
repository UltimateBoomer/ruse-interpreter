package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record NotExp(
    BoolExp exp
) implements BoolExp {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("not"), exp.toSExp());
    }
    
}
