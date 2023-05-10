package io.github.ultimateboomer.ruseinterpreter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ultimateboomer.ruseinterpreter.impl.FauxRacketInterpreter;
import io.github.ultimateboomer.ruseinterpreter.impl.InterpException;
import io.github.ultimateboomer.ruseinterpreter.impl.SExpParser;
import io.github.ultimateboomer.ruseinterpreter.model.InterpExceptionResponse;
import io.github.ultimateboomer.ruseinterpreter.model.InterpRequest;
import io.github.ultimateboomer.ruseinterpreter.model.InterpResponse;
import io.github.ultimateboomer.ruseinterpreter.model.RuseLanguage;
import io.github.ultimateboomer.ruseinterpreter.model.fauxracket.FauxRacketAbstractSyntax;
import io.github.ultimateboomer.ruseinterpreter.model.sexp.SExp;

@RestController
@RequestMapping("/api/ruse")
public class RuseInterpController {

    @GetMapping("/langs")
    public RuseLanguage[] getLanguages() {
        return RuseLanguage.values();
    }

    @PostMapping("/interp")
    @ResponseStatus(HttpStatus.OK)
    public InterpResponse interpRuse(@RequestBody InterpRequest data) {
        SExp exp = SExpParser.parse(data.exp());
        if (data.lang() == RuseLanguage.FAUX_RACKET) {
            FauxRacketAbstractSyntax result = FauxRacketInterpreter.parse(exp);
            result = FauxRacketInterpreter.interp(result);
            return new InterpResponse(result.toSExp().toString());
        } else {
            throw new InterpException("Invalid language");
        }
    }

    @ExceptionHandler(InterpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InterpExceptionResponse handleInterpException(InterpException e) {
        return new InterpExceptionResponse(e.getMessage());
    }

}
