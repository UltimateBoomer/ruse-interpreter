package io.github.ultimateboomer.ruseinterpreter.model.sexp;

import java.util.List;
import java.util.StringJoiner;

public record SList(
    List<SExp> exps
) implements SExp {

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ", "(", ")");
        exps.forEach(e -> joiner.add(e.toString()));

        return joiner.toString();
    }

}
