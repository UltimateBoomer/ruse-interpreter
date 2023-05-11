package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.App;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBin;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Closure;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Var;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.FauxRacketAbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Fun;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class FauxRacketInterpreter {

    private static final Map<String, ArithOp> arithOpMap = Map.of(
        "+", ArithOp.ADD,
        "-", ArithOp.SUB,
        "*", ArithOp.MUL,
        "/", ArithOp.DIV);
    
    private static final Pattern integerPattern = Pattern.compile("-?[0-9]+");

    public static FauxRacketAbstractSyntax parse(SExp exp) {
        if (exp instanceof Atom) {
            if (integerPattern.matcher(((Atom) exp).value()).matches()) {
                return new Num(Integer.parseInt(((Atom) exp).value()));
            } else {
                return new Var(((Atom) exp).value());
            }
        } else if (exp instanceof SList) {
            return parseSList((SList) exp);
        } else {
            throw new InterpException("Invalid syntax");
        }
    }

    private static FauxRacketAbstractSyntax parseSList(SList list) {
        if (list.exps().isEmpty()) {
            throw new InterpException("SList is empty");
        }

        SExp first = list.exps().get(0);
        if (list.exps().size() == 3 && first instanceof Atom && arithOpMap.containsKey(((Atom) first).value())) {
            ArithExp left = (ArithExp) parse(list.exps().get(1));
            ArithExp right = (ArithExp) parse(list.exps().get(2));
            return new ArithBin(arithOpMap.get(((Atom) first).value()), left, right);
        } else if (list.exps().size() == 3 && first instanceof Atom && ((Atom) first).value().equals("fun")) {
            String var = ((Atom) ((SList) list.exps().get(1)).exps().get(0)).value();
            FauxRacketAbstractSyntax body = parse(list.exps().get(2));
            return new Fun(var, body);
        } else if (list.exps().size() == 3 && first instanceof Atom && ((Atom) first).value().equals("with")) {
            List<SExp> defs = ((SList) list.exps().get(1)).exps();
            FauxRacketAbstractSyntax result = parse(list.exps().get(2));
            for (SExp d : defs) {
                String var = ((Atom) ((SList) d).exps().get(0)).value();
                FauxRacketAbstractSyntax args = parse(((SList) d).exps().get(1));
                result = new App(new Fun(var, result), args);
            }
            return result;
        } else if (list.exps().size() == 2) {
            FauxRacketAbstractSyntax fun = parse(list.exps().get(0));
            FauxRacketAbstractSyntax args = parse(list.exps().get(1));
            return new App(fun, args);
        } else {
            throw new InterpException("Invalid SList syntax");
        }
    }

    public static FauxRacketAbstractSyntax interp(FauxRacketAbstractSyntax exp) {
        return interp(exp, new HashMap<>());
    }

    public static FauxRacketAbstractSyntax interp(FauxRacketAbstractSyntax exp, Map<String, FauxRacketAbstractSyntax> env) {
        if (exp instanceof Num) {
            return exp;
        } else if (exp instanceof Var) {
            String name = ((Var) exp).name();
            if (env.containsKey(name)) {
                return env.get(name);
            } else {
                throw new InterpException("Variable not found: %s".formatted(name));
            }
        } else if (exp instanceof Fun) {
            return new Closure(((Fun) exp).var(), ((Fun) exp).body(), new HashMap<>(env));
        } else if (exp instanceof ArithBin) {
            Num leftRes = (Num) interp(((ArithBin) exp).left(), env);
            Num rightRes = (Num) interp(((ArithBin) exp).right(), env);
            return ((ArithBin) exp).op().apply(leftRes, rightRes);
        } else if (exp instanceof App) {
            Closure closure = (Closure) interp(((App) exp).fun(), env);
            FauxRacketAbstractSyntax arg = interp(((App) exp).arg(), env);
            Map<String, FauxRacketAbstractSyntax> newEnv = new HashMap<>(env);
            newEnv.put(closure.var(), arg);
            return interp(closure.body(), newEnv);
        } else {
            throw new InterpException("Invalid abstract syntax");
        }
    }

}
