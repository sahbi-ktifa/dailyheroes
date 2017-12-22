package fr.efaya.game.todorpg.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
    public String login(Map<String, Object> model, @RequestParam(required = false) String error) {
        addContext(model);
        model.put("error", error);
        return "login";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public ModelAndView getIndexPage(Principal principal) {
        return retrieveHomePageMav(principal);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage(Principal principal) {
        return retrieveHomePageMav(principal);
    }

    private ModelAndView retrieveHomePageMav(Principal principal) {
        ModelAndView mav = new ModelAndView("index", "principal", principal.getName());
        addContext(mav.getModel());
        return mav;
    }

    private void addContext(Map<String, Object> model) {
        model.put("path", context);
    }
}
