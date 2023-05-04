package io.github.ultimateboomer.ruseinterpreter.model.ruse;

import java.util.List;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record ArithBin(
    ArithOp op,
    ArithExp left,
    ArithExp right
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return new SList(List.of(new Atom(op.toString()), left.toSExp(), right.toSExp()));
    }
    
}
