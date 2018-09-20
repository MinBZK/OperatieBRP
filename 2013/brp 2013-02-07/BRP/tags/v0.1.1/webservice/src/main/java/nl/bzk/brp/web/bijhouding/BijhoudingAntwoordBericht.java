/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.web.AbstractAntwoordBericht;


/**
 * Deze klasse stelt een antwoord bericht voor in het kader van bijhoudingen. Het antwoord bericht bevat alle elementen
 * die terug gecommuniceerd worden via de webservice. Zo bevat het eventuele meldingen (fouten, waarschuwingen etc.)
 * die zijn opgetreden tijdens de verwerking van het bericht, maar ook een lijst van personen die tijdens de bijhouding
 * zijn aangepast/geraakt.
 */
public class BijhoudingAntwoordBericht extends AbstractAntwoordBericht<BijhoudingResultaat> {
    private final List<Persoon> bijgehoudenPersonen;

    /**
     * Standaard constructor die op basis van het opgegeven berichtresultaat uit de business laag de juiste waardes
     * bepaalt/zet in dit antwoord bericht.
     *
     * @param berichtResultaat het bericht resultaat uit de business laag op basis waarvan het antwoord bericht wordt
     *            gebouwd.
     */
    public BijhoudingAntwoordBericht(final BijhoudingResultaat berichtResultaat) {
        super(berichtResultaat);
        this.setTijdstipRegistratie();
        bijgehoudenPersonen = null;
    }

    /**
     * Retourneert een (onaanpasbare) lijst van de bijgehouden/aangepast personen.
     *
     * @return een (onaanpasbare) lijst van de bijgehouden/aangepast personen.
     */
    public List<Persoon> getBijgehoudenPersonen() {
        List<Persoon> resultaat;
        if (bijgehoudenPersonen == null) {
            resultaat = null;
        } else {
            resultaat = Collections.unmodifiableList(bijgehoudenPersonen);
        }
        return resultaat;
    }
}
