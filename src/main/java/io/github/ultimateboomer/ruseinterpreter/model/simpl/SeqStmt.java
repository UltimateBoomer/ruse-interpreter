package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import java.util.List;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record SeqStmt(
    List<Stmt> stmts
) implements Stmt {

    @Override
    public SExp toSExp() {
        return SList.of(new Atom("seq"), new SList(stmts.stream().map(s -> s.toSExp()).toList()));
    }
    
}
