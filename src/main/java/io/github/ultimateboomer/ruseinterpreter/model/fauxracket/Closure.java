package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.List;
import java.util.Map;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record Closure(
    String var,
    FauxRacketAbstractSyntax body,
    Map<String, FauxRacketAbstractSyntax> env
) implements FauxRacketAbstractSyntax {

    @Override
    public SExp toSExp() {
        return new SList(List.of(new Atom("closure"), new SList(List.of(new Atom(var))), body.toSExp()));
    }
    
}
