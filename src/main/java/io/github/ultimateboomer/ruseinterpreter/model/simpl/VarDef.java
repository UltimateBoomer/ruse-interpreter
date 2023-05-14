package io.github.ultimateboomer.ruseinterpreter.model.simpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Exp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public record VarDef(
    Map<String, Exp> vars,
    List<Stmt> stmts
) implements Stmt {

    @Override
    public SExp toSExp() {
        List<SExp> exps = Stream.concat(
            List.of(new Atom("vars"), new SList(vars.values().stream().map(s -> s.toSExp()).toList())).stream(),
            stmts.stream().map(s -> s.toSExp()))
            .toList();
        return new SList(exps);
    }
    
}
