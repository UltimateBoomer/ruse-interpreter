package io.github.ultimateboomer.ruseinterpreter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ultimateboomer.ruseinterpreter.impl.RuseInterpreter;
import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.model.InterpRequest;
import io.github.ultimateboomer.ruseinterpreter.model.InterpResponse;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.RuseAbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

@RestController
@RequestMapping("/api/interp")
public class RuseInterpController {

    @PostMapping("/ruse")
    @ResponseStatus(HttpStatus.OK)
    public InterpResponse interpRuse(@RequestBody InterpRequest data) {
        SExp exp = SExpParser.parse(data.exp());
        RuseAbstractSyntax result = RuseInterpreter.parse(exp);
        result = RuseInterpreter.interp(result);
        return new InterpResponse(result.toSExp().toString());
    }

}
