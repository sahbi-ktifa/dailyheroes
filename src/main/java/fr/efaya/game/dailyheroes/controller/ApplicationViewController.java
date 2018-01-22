package fr.efaya.game.dailyheroes.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sahbi Ktifa
 * created on 21/12/2017
 */
@Controller
public class ApplicationViewController {
    @Value("${server.context-path:}")
    private String context;

    @RequestMapping("/login")
    public String login(Map<String, Object> model, @RequestParam(required = false) String error, HttpServletRequest request) {
        addContext(model);
        model.put("error", error);
        model.put("language", request.getLocale().getLanguage());
        return "login";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public ModelAndView getIndexPage(Principal principal, HttpServletRequest request) {
        return retrieveHomePageMav(principal, request);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage(Principal principal, HttpServletRequest request) {
        return retrieveHomePageMav(principal, request);
    }

    @RequestMapping(path = "/partials/{partial}.html", method = RequestMethod.GET)
    public String retrievePartial(@PathVariable String partial) {
        return "partials/" + partial;
    }

    private ModelAndView retrieveHomePageMav(Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("index", "principal", principal.getName());
        addContext(mav.getModel());
        mav.getModel().put("language", request.getLocale().getLanguage());
        return mav;
    }

    private void addContext(Map<String, Object> model) {
        model.put("path", context);
    }
}
