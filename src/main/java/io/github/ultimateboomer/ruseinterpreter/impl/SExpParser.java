package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class SExpParser {
    
    public static SExp parse(String str) {
        Deque<SList> context = new ArrayDeque<>();

        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == '(') {
                SList lst = new SList(new ArrayList<>());
                if (!context.isEmpty()) {
                    context.getLast().exps().add(lst);
                }
                context.add(lst);
            } else if (str.charAt(i) == ')') {
                SList result = context.removeLast();
                if (context.isEmpty()) {
                    return result;
                }
            } else {
                StringBuilder builder = new StringBuilder();
                while (i < str.length() && str.charAt(i) != '(' && str.charAt(i) != ')'
                        && !Character.isWhitespace(str.charAt(i))) {
                    builder.appendCodePoint(str.codePointAt(i));

                    ++i;
                }
                Atom result = new Atom(builder.toString());
                if (context.isEmpty()) {
                    return result;
                } else {
                    context.getLast().exps().add(result);
                }
            }
        }

        if (context.isEmpty()) return null;
        return context.getFirst();
    }

}
