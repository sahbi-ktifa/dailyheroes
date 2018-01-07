package fr.efaya.game.dailyheroes.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sahbi Ktifa
 * created on 07/01/2018
 */
@RestController
@RequestMapping("version")
public class VersionWebServiceController {

    @Value("${info.build.version:SNAPSHOT}")
    private String version;

    @GetMapping(produces = "text/plain")
    public String version() {
        return version;
    }
}
