/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;

/**
 * Authenticatiegegevens welke middels het certificaat offloading proces verkregen worden.
 */
public class AutorisatieOffloadGegevens {

    public static final String OIN_ONDERTEKENAAR = "oin-ondertekenaar";
    public static final String OIN_TRANSPORTEUR = "oin-transporteur";

    private Partij ondertekenaar;
    private Partij transporteur;

    public AutorisatieOffloadGegevens(Partij ondertekenaar, Partij transporteur) {
       this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;
    }

    public Partij getOndertekenaar() {
        return ondertekenaar;
    }

    public Partij getTransporteur() {
        return transporteur;
    }

}
