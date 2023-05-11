package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.List;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record App(
    FauxRacketAbstractSyntax fun,
    ArithExp arg
) implements ArithExp {

    @Override
    public SExp toSExp() {
        return new SList(List.of(fun.toSExp(), arg.toSExp()));
    }
    
}
