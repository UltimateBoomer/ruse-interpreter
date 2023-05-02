package io.github.ultimateboomer.ruseinterpreter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.ultimateboomer.ruseinterpreter.impl.RuseInterpreter;
import io.github.ultimateboomer.ruseinterpreter.model.ruse.RuseAbstractSyntax;

@RestController
@RequestMapping("/api/interp")
public class RuseInterpController {

    private RuseInterpreter ruseInterpreter = new RuseInterpreter();
    
    @PostMapping("/ruse")
    @ResponseStatus(HttpStatus.OK)
    public RuseAbstractSyntax interpRuse(@RequestBody RuseAbstractSyntax as) {
        RuseAbstractSyntax result = ruseInterpreter.interp(as);
        return result;
    }

}
