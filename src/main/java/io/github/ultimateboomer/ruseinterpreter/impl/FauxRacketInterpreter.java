package io.github.ultimateboomer.ruseinterpreter.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.App;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBinExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithBinOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithCmpExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithCmpOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.ArithExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Bool;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.BoolBinExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.BoolBinOp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.BoolExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Closure;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Exp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Fun;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.IfExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.NotExp;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Num;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.Var;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.Atom;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SList;

public class FauxRacketInterpreter {

    private static final Map<String, ArithBinOp> arithOpMap = Map.of(
        "+", ArithBinOp.ADD,
        "-", ArithBinOp.SUB,
        "*", ArithBinOp.MUL,
        "/", ArithBinOp.DIV,
        "div", ArithBinOp.DIV,
        "%", ArithBinOp.MOD,
        "mod", ArithBinOp.MOD
    );

    private static final Map<String, BoolBinOp> boolOpMap = Map.of(
        "and", BoolBinOp.AND,
        "or", BoolBinOp.OR
    );

    private static final Map<String, ArithCmpOp> arithCmpOpMap = Map.of(
        "=", ArithCmpOp.EQ,
        "<", ArithCmpOp.LT,
        "<=", ArithCmpOp.LE,
        ">", ArithCmpOp.GT,
        ">=", ArithCmpOp.GE
    );

    public static Exp parse(SExp exp) {
        if (exp instanceof Atom) {
            String value = ((Atom) exp).value();
            if (RuseCommon.numPattern.matcher(value).matches()) {
                return new Num(Integer.parseInt(value));
            } else if (RuseCommon.boolPattern.matcher(value).matches()) {
                return new Bool(Boolean.parseBoolean(value));
            } else {
                return new Var(value);
            }
        } else {
            return parseSList((SList) exp);
        }
    }

    private static Exp parseSList(SList list) {
        if (list.exps().isEmpty()) {
            throw new InterpException("SList is empty");
        }

        SExp first = list.exps().get(0);

        if (first instanceof Atom) {
            if (arithOpMap.containsKey(((Atom) first).value())) {
                Exp left = (ArithExp) parse(list.exps().get(1));
                Exp right = (ArithExp) parse(list.exps().get(2));
                return new ArithBinExp(arithOpMap.get(((Atom) first).value()), left, right);
            } else if (arithCmpOpMap.containsKey(((Atom) first).value())) {
                Exp left = (ArithExp) parse(list.exps().get(1));
                Exp right = (ArithExp) parse(list.exps().get(2));
                return new ArithCmpExp(arithCmpOpMap.get(((Atom) first).value()), left, right);
            } else if (boolOpMap.containsKey(((Atom) first).value())) {
                Exp left = (BoolExp) parse(list.exps().get(1));
                Exp right = (BoolExp) parse(list.exps().get(2));
                return new BoolBinExp(boolOpMap.get(((Atom) first).value()), left, right);
            } else if (((Atom) first).value().equals("not")) {
                BoolExp exp = (BoolExp) parse(list.exps().get(1));
                return new NotExp(exp);
            } else if (((Atom) first).value().equals("if")) {
                BoolExp bexp = (BoolExp) parse(list.exps().get(1));
                Exp trueExp = (Exp) parse(list.exps().get(2));
                Exp falseExp = (Exp) parse(list.exps().get(3));
                return new IfExp(bexp, trueExp, falseExp);
            } else if (((Atom) first).value().equals("fun")) {
                String var = ((Atom) ((SList) list.exps().get(1)).exps().get(0)).value();
                Exp body = parse(list.exps().get(2));
                return new Fun(var, body);
            } else if (((Atom) first).value().equals("with")) {
                List<SExp> defs = ((SList) list.exps().get(1)).exps();
                Exp result = parse(list.exps().get(2));
                for (SExp d : defs) {
                    String var = ((Atom) ((SList) d).exps().get(0)).value();
                    Exp args = parse(((SList) d).exps().get(1));
                    result = new App(new Fun(var, result), args);
                }
                return result;
            }
        } else if (list.exps().size() == 2) {
            Exp fun = parse(list.exps().get(0));
            Exp args = parse(list.exps().get(1));
            return new App(fun, args);
        }

        throw new InterpException(String.format("Invalid Faux Racket syntax: %s", list));
    }

    public static Exp interp(Exp exp) {
        return interp(exp, new HashMap<>());
    }

    static Exp interp(Exp exp, Map<String, Exp> env) {
        if (exp instanceof Num || exp instanceof Bool) {
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
        } else if (exp instanceof ArithBinExp) {
            Num leftRes = (Num) interp(((ArithBinExp) exp).left(), env);
            Num rightRes = (Num) interp(((ArithBinExp) exp).right(), env);
            return new Num(((ArithBinExp) exp).op().apply(leftRes.value(), rightRes.value()));
        } else if (exp instanceof ArithCmpExp) {
            Num leftRes = (Num) interp(((ArithCmpExp) exp).left(), env);
            Num rightRes = (Num) interp(((ArithCmpExp) exp).right(), env);
            return new Bool(((ArithCmpExp) exp).op().apply(leftRes.value(), rightRes.value()));
        } else if (exp instanceof BoolBinExp) {
            Bool leftRes = (Bool) interp(((BoolBinExp) exp).left(), env);
            Bool rightRes = (Bool) interp(((BoolBinExp) exp).right(), env);
            return new Bool(((BoolBinExp) exp).op().apply(leftRes.value(), rightRes.value()));
        } else if (exp instanceof NotExp) {
            Bool res = (Bool) interp(((NotExp) exp).exp(), env);
            return new Bool(!res.value());
        } else if (exp instanceof IfExp) {
            Bool res = (Bool) interp(((IfExp) exp).bexp());
            return interp(res.value() ? ((IfExp) exp).trueExp() : ((IfExp) exp).falseExp(), env);
        } else if (exp instanceof App) {
            Closure closure = (Closure) interp(((App) exp).fun(), env);
            Exp arg = interp(((App) exp).arg(), env);
            Map<String, Exp> newEnv = new HashMap<>(env);
            newEnv.put(closure.var(), arg);
            return interp(closure.body(), newEnv);
        } else {
            throw new InterpException("Invalid abstract syntax");
        }
    }

}
