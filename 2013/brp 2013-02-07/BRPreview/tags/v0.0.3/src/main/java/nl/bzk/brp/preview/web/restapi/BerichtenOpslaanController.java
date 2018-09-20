/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.web.restapi;

import javax.servlet.http.HttpServletResponse;

import nl.bzk.brp.preview.model.Bericht;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


/**
 * De Class BerichtenOpslaanController.
 *
 */
@Controller
public class BerichtenOpslaanController {

    /**
     * Opslaan van een bericht van de BRP in de database.
     *
     * @param bericht het inkomende bericht van de BRP service
     * @param response de http response
     * @return het bericht opslaan response object dat we teruggeven (is dit nodig?)
     */
    @RequestMapping(value = "/berichten/opslaan", method = RequestMethod.POST)
    public ModelAndView opslaan(@RequestBody final Bericht bericht, final HttpServletResponse response) {
        //TODO implementeer hier het opslaan in de database
        return null;
    }

    /**
     * Handling of client errors.
     *
     * Returns HTTP status code 400 (bad request).
     *
     * @param ex The client error.
     * @return The error message.
     */
    // These exceptions denote client errors.
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleClientErrors(final Exception ex) {
        return ex.getMessage();
    }

    /**
     * Handling of server errors.
     *
     * Returns HTTP status code 500 (internal server error).
     *
     * @param ex The server error.
     * @return The error message.
     */
    // All other exceptions are server errors.
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleServerErrors(final Exception ex) {
        return ex.getMessage();
    }

}
