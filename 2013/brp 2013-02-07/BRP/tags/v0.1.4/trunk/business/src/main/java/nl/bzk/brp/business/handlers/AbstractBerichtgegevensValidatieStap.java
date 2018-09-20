/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 * @param <T> Een object dat je kunt valideren, m.a.w. geannoteerd met validatie annotaties.
 * @param <X> Een BRP bericht. (bevraging of bijhouding)
 * @param <Y> Een berichtresultaat.
 */
public abstract class AbstractBerichtgegevensValidatieStap<T, X extends BRPBericht, Y extends BerichtResultaat>
        extends AbstractBerichtVerwerkingsStap<X, Y>
{

    private static final String CONSTRAINT_CODE  = "code";
    private static final String CONSTRAINT_SOORT = "soortMelding";

    /**
     * Voert de validatie uit.
     * @param t Een object dat te valideren is, m.a.w. het object heeft validatie annotaties.
     * @param resultaat Resultaat waar validatie fouten eventueel in opgeslagen moeten worden.
     */
    protected void valideer(final T t, final BerichtResultaat resultaat) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> overtredingen = validator.validate(t, Default.class);

        for (ConstraintViolation<T> v : overtredingen) {
            MeldingCode meldingCode =
                    (MeldingCode) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_CODE);
            SoortMelding soortMelding =
                    (SoortMelding) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_SOORT);

            // TODO er wordt hier nog niet gebruik gemaakt van i18n berichten, dit zou gerefactored kunnen
            // TODO worden
            resultaat.voegMeldingToe(new Melding(soortMelding, meldingCode));
        }
    }
}
