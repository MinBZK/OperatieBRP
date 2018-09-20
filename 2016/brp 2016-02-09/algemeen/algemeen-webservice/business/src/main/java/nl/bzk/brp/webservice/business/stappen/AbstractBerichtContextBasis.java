/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;


/**
 * De context rond een BRP bericht. Deze context bevat additionele (niet bericht-type specifieke) informatie zoals
 * afzender, bericht id, authenticatiemiddel id etc.
 * Voor elke webservice wordt een aparte verwerker met zijn eigen context gemaakt, echter als er onderdelen zijn die
 * gedeeld kunnen worden omdat elke context dat gebruikt, dan kan dit in deze context worden geplaatst.
 */
public abstract class AbstractBerichtContextBasis extends AbstractBerichtContext {


    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param afzender            de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public AbstractBerichtContextBasis(final BerichtenIds berichtIds, final Partij afzender,
                               final String berichtReferentieNr, final CommunicatieIdMap identificeerbareObj)
    {
        super(berichtIds, new PartijAttribuut(afzender), berichtReferentieNr, identificeerbareObj);
    }
}
