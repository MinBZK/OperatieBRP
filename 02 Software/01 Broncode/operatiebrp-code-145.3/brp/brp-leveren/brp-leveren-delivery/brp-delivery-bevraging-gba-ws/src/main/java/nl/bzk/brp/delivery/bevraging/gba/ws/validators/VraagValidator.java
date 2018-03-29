/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;

/**
 * Valideert AdHocWebserviceVraagBericht objecten aan de hand van de geconfigureerde Validators.
 * @param <T> subtype van WebserviceBericht dat gevalideerd dient te worden
 */
public class VraagValidator<T extends WebserviceBericht> {
    private final T vraag;
    private List<Validator<T>> validators = Collections.emptyList();

    /**
     * Constructor.
     * @param vraag te valideren AdHocWebserviceVraagBericht
     */
    public VraagValidator(final T vraag) {
        this.vraag = vraag;
    }

    /**
     * Configureert de validators.
     * @param validators lijst van validators
     * @return het huidige object
     */
    public final VraagValidator<T> setValidators(List<Validator<T>> validators) {
        this.validators = new ArrayList<>(validators);
        return this;
    }

    /**
     * Checkt de validators één voor één of er een validatie fout gevonden is. Bij de eerst gevonden fout
     * wordt het antwoord gegenereerd door de validator terug gegeven. Bij geen fouten wordt de meegegeven
     * functie uitgevoerd en het resultaat terug gegeven.
     * @param f functie die uitgevoerd wordt bij geen validatie fouten
     * @return antwoord
     */
    public final Antwoord ifValid(final Function<T, Antwoord> f) {
        return validationError().orElseGet(() -> f.apply(vraag));
    }

    private Optional<Antwoord> validationError() {
        return validators.stream()
                .map(validator -> validator.apply(vraag))
                .filter(Optional::isPresent)
                .findFirst()
                .map(Optional::get);
    }
}
