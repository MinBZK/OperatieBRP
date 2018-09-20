/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;


/**
 * Basis object van alle berichten.
 */
public class Bericht {

    private String         berichtId;
    private Date           tijdstipVerzonden;
    private List<BRPActie> bijhoudingen;

    public Bericht() {
        this.bijhoudingen = new ArrayList<BRPActie>();
    }

    /**
     * Retourneert het id van het verzonden bericht.
     *
     * @return het id van het verzonden bericht.
     */
    public String getBerichtId() {
        return berichtId;
    }

    /**
     * Zet het id van het verzonden bericht.
     *
     * @param berichtId het id van het verzonden bericht.
     */
    public void setBerichtId(String berichtId) {
        this.berichtId = berichtId;
    }

    /**
     * Retourneert de datum en tijdstip waarop het bericht is verzonden.
     *
     * @return de datum en tijdstip waarop het bericht is verzonden.
     */
    public Date getTijdstipVerzonden() {
        return tijdstipVerzonden;
    }

    /**
     * Zet de datum en tijdstip waarop het bericht is verzonden.
     *
     * @param tijdstipVerzonden de datum en tijdstip waarop het bericht is verzonden.
     */
    public void setTijdstipVerzonden(Date tijdstipVerzonden) {
        this.tijdstipVerzonden = tijdstipVerzonden;
    }

    /**
     * Retourneert de lijst van bijhoudingen die middels dit bericht worden doorgegeven en dus doorgevoerd moeten
     * worden.
     *
     * @return de lijst van door te voeren bijhoudingen.
     */
    public List<BRPActie> getBijhoudingen() {
        return bijhoudingen;
    }

    /**
     * Zet  de lijst van door te voeren bijhoudingen.
     *
     * @param bijhoudingen de lijst van door te voeren bijhoudingen.
     */
    public void setBijhoudingen(final List<BRPActie> bijhoudingen) {
        this.bijhoudingen = bijhoudingen;
    }

}
