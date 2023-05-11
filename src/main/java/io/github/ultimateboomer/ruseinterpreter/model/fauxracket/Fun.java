package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.List;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record Fun(
    String var,
    FauxRacketAbstractSyntax body
) implements FauxRacketAbstractSyntax {

    @Override
    public SExp toSExp() {
        return new SList(List.of(new Atom("fun"), new SList(List.of(new Atom(var))), body.toSExp()));
    }
    
}
