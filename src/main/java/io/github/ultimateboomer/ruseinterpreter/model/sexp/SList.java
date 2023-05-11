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

    public static SList of(SExp... exps) {
        return new SList(List.of(exps));
    }

}
