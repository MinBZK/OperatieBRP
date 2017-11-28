/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.ResourceBundle;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.beheer.webapp.model.ExceptionResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Rest service error handler.
 */
@ControllerAdvice
public final class ErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final ResourceBundle resources;

    /**
     * Constructor.
     * @param resources resource bundle voor foutmeldingen
     */
    @Inject
    public ErrorHandler(@Qualifier("foutmeldingen") final ResourceBundle resources) {
        this.resources = resources;
    }

    /**
     * Geef HTTP code 404 bij NotFoundException.
     * @param ex exceptie
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(final NotFoundException ex) {
        LOG.warn("Ongeldige pagina opgevraagd.");
    }

    /**
     * Geef HTTP code 500 bij JPA fouten.
     * @param ex exception
     * @return fout omschrijving
     */
    @ExceptionHandler(JpaSystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleOtherExceptions(final JpaSystemException ex) {
        LOG.warn("JPA exceptie opgetreden", ex);
        return new ExceptionResponse(
                "Databaseregels geschonden",
                "Ingevoerde gegevens komen al voor in de database of schenden integriteitsregels van de database");
    }

    /**
     * Geef HTTP code 409 bij validatie fouten.
     * @param ex exception
     * @return fout omschrijving
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleValidationExceptions(final MethodArgumentNotValidException ex) {

        return new ExceptionResponse("Bedrijfsregels geschonden", buildMessage(ex));
    }

    private String buildMessage(final MethodArgumentNotValidException ex) {
        final StringBuilder result = new StringBuilder();
        for (final ObjectError error : ex.getBindingResult().getAllErrors()) {
            result.append(" - ").append(String.format(resources.getString(error.getCode()), error.getArguments()));
        }
        return result.toString();
    }

    /**
     * Geef HTTP code 500 bij fouten.
     * @param ex exception
     * @return fout omschrijving
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleOtherExceptions(final Exception ex) {
        LOG.warn("Onverwachte exceptie opgetreden: " + ExceptionUtils.getFullStackTrace(ex));
        return new ExceptionResponse(ex);
    }

    /**
     * Exceptie als een gegeven niet is gevonden.
     */
    public static final class NotFoundException extends Exception {

        private static final long serialVersionUID = 1L;
    }

}
