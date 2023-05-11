package io.github.ultimateboomer.ruseinterpreter.model.fauxracket;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record IfExp(
    BoolExp bexp,
    Exp trueExp,
    Exp falseExp
) implements AbstractSyntax {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("if"), trueExp.toSExp(), falseExp.toSExp());
    }
    
}
