/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;


/**
 * Deze klasse stelt een antwoord bericht voor in het kader van bijhoudingen. Het antwoord bericht bevat alle elementen die terug gecommuniceerd worden via
 * de webservice. Zo bevat het eventuele meldingen (fouten, waarschuwingen etc.) die zijn opgetreden tijdens de verwerking van het bericht, maar ook een
 * lijst van personen die tijdens de bijhouding zijn aangepast/geraakt.
 */
public class BijhoudingAntwoordBericht extends AntwoordBericht {

    private Long tijdstipRegistratie;

    /**
     * Standaard constructor die de soort van het bericht zet.
     *
     * @param soort de soort van het bericht.
     */
    protected BijhoudingAntwoordBericht(final SoortBericht soort) {
        super(soort);
    }

    /**
     * Retourneert het tijdstip van registratie waarop de bijhouding is geregistreerd.
     *
     * @return het tijdstip van registratie waarop de bijhouding is geregistreerd.
     */
    public final Long getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Zet het tijdstip van registratie waarop de bijhouding is registreerd.
     *
     * @param tijdstipRegistratie het tijdstip van registratie waarop de bijhouding is geregistreerd.
     */
    public final void setTijdstipRegistratie(final Long tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Test of de lijst van bijgehoudenPersonen getooned dient te worden. Dit is een method voor Jibx om de lijst op te maken ja of nee. (Bij fout
     * verwerking de lijst dus niet tonen).
     *
     * @return true als de lijst terugggeven moet worden
     */
    public final boolean tonenBijgehoudenPersonen() {
        return getResultaat().getVerwerking().getWaarde() == Verwerkingsresultaat.GESLAAGD;
    }
}
