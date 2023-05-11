package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record Fun(
    String var,
    AbstractSyntax body
) implements AbstractSyntax {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("fun"), SList.of(new Atom(var)), body.toSExp());
    }
    
}
