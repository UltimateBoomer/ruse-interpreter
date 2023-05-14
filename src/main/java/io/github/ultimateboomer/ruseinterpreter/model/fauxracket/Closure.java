package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import java.util.Map;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record Closure(
    String var,
    Exp body,
    Map<String, Exp> env
) implements Exp {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("closure"), SList.of(new Atom(var)), body.toSExp());
    }
    
}
