package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record ArithBinExp(
    ArithBinOp op,
    Exp left,
    Exp right
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom(op.toString()), left.toSExp(), right.toSExp());
    }
    
}
