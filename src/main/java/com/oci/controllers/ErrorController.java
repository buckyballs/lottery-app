package com.oci.controllers;

import com.oci.services.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Ishtiaq on 1/27/2017.
 */
@Controller
public class ErrorController {

    @Autowired
    private ErrorService errorService;

    @RequestMapping(value = "/errors", method = RequestMethod.GET)
    public String renderErrorPage(final Model model, final HttpServletRequest request) {

        //Get the http error code
        final int error_code = getHttpErrorCode(request);

        //Generate error message for corresponding Http Error Code
        final String error_message = errorService.generateErrorMessage(error_code);

        model.addAttribute("errorMsg", error_message);
        return "error";
    }

    private int getHttpErrorCode(final HttpServletRequest request) {
        return (int) request.getAttribute("javax.servlet.error.status_code");
    }
}
