/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.Optional;
import java.util.function.Function;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;

/**
 * Functionele interface voor het valideren van een AdHocWebserviceVraagBericht.
 * @param <T> subtype van AbstractWebserviceVraagBericht die gevalideerd dienen te worden
 */
@FunctionalInterface
public interface Validator<T extends WebserviceBericht> extends Function<T, Optional<Antwoord>> {
}
