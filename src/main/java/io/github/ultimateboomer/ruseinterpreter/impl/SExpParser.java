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

        for (int i = 0; i < str.length();) {
            if (str.charAt(i) == '(') {
                SList list = new SList(new ArrayList<>());
                if (!context.isEmpty()) {
                    context.getLast().exps().add(list);
                }
                context.add(list);
                ++i;
            } else if (str.charAt(i) == ')') {                
                SList result = context.removeLast();
                if (context.isEmpty()) return result;
                ++i;
            } else if (Character.isWhitespace(str.charAt(i))) {
                // skip whitespace
                do ++i;
                while (i < str.length() && Character.isWhitespace(str.charAt(i)));
            } else {
                // get token
                StringBuilder builder = new StringBuilder();
                while (i < str.length() && isTokenChar(str.charAt(i))) {
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

    private static boolean isTokenChar(char c) {
        return !Character.isWhitespace(c) && c != '(' && c != ')';
    }

    // private static Atom flushBuilder(Deque<SList> context, StringBuilder builder) {
    //     Atom result = new Atom(builder.toString());
    //     builder.setLength(0);
    //     if (!context.isEmpty()) {
    //         context.getLast().exps().add(result);
    //     }
    //     return result;
    // }

}
