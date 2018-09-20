/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.bericht.BerichtBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;

/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is.
 * Hierbij wordt gecontroleerd of de benodigde parameters aanwezig zijn, er geen tegenstrijdigheden in zitten
 * en of er geen ongeldige waardes tussen zitten. Eventueel geconstateerde invalide waardes worden, inclusief
 * bericht melding en zwaarte, toegevoegd aan de lijst van fouten binnen het antwoord.
 * @param <T> Een object dat je kunt valideren, m.a.w. geannoteerd met validatie annotaties.
 * @param <X> Een BRP bericht. (bevraging of bijhouding)
 * @param <Y> Een berichtresultaat.
 */
public abstract class AbstractBerichtgegevensValidatieStap<T, X extends BerichtBericht,
        Y extends BerichtVerwerkingsResultaat>
        extends AbstractBerichtVerwerkingsStap<X, Y>
{

    private static final String CONSTRAINT_CODE  = "code";
    private static final String CONSTRAINT_SOORT = "soortMelding";

    /**
     * Voert de validatie uit.
     * @param t Een object dat te valideren is, m.a.w. het object heeft validatie annotaties.
     * @param resultaat Resultaat waar validatie fouten eventueel in opgeslagen moeten worden.
     */
    protected void valideer(final T t, final BerichtVerwerkingsResultaat resultaat) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> overtredingen = validator.validate(t, Default.class);

        for (ConstraintViolation<T> v : overtredingen) {
            MeldingCode meldingCode =
                    (MeldingCode) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_CODE);
            Soortmelding soortMelding =
                    (Soortmelding) v.getConstraintDescriptor().getAttributes().get(CONSTRAINT_SOORT);

            // TODO er wordt hier nog niet gebruik gemaakt van i18n berichten, dit zou gerefactored kunnen
            // TODO worden
            Melding melding = new Melding(soortMelding, meldingCode, null, null);
            // zet het verzendendId en attribuutNaam apart, we kunnne geen groep automatisch opzetten.
            melding.setVerzendendId(getVerzendendId(v));
            melding.setAttribuutNaam(getAttribuutNaam(v));
            resultaat.voegMeldingToe(melding);
        }

    }

    /**
     * .
     * @param violation .
     * @return .
     */
    private String getAttribuutNaam(final ConstraintViolation<T> violation) {
        String attribuutNaam = "";
        Iterator<Node> it = violation.getPropertyPath().iterator();
        Node lastNode = null;
        while (it.hasNext()) {
            Node node = it.next();
            lastNode = node;
        }
        if (lastNode != null) {
            if (!lastNode.isInIterable()) {
                // dit is een echt eindpunt. geet de attribuutnaam terug
                attribuutNaam = lastNode.getName();
            } else {
                if (lastNode.getIndex() == null) {
                    attribuutNaam = lastNode.getName();
                } else {
                    // blijkt als de validator op een class staat ipv. op een proprty, de lastnode
                    // wijst naar het object, met index is niet null. Maar we hebben niets aan deze informatie
                    // dus laten we het weg.

                    // Jammer genoeg, veel van deze groepen zien we niet terug in de xml bericht structuur, dus
                    // de attribuutnaam 'gegevens' zegt de gebruiker helemaal niets.
                    // TODO we moeten hier nog steeds iets op verzinnen om java propertynamen te mappen naar xml tags.
                    attribuutNaam = lastNode.getName();
                }
            }
        }
        return attribuutNaam;
    }

    /**
     * .
     * @param violation .
     * @return .
     */
    private String getVerzendendId(final ConstraintViolation<T> violation) {
        String verzendendId = null;
        Object o = violation.getLeafBean();
        if (o instanceof Identificeerbaar) {
            verzendendId = ((Identificeerbaar) o).getVerzendendId();
        }
        return verzendendId;
    }

}
