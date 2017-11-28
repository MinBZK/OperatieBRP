/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.request;

/**
 * DTO voor het OIN gegevens uit het request.
 */
public final class OIN {

    /**
     * Request attribuutnaam voor de OIN van de ondertekenaar.
     */
    public static final String OIN_ONDERTEKENAAR = "oin-ondertekenaar";

    /**
     * Request attribuutnaam voor de OIN van de transporteur.
     */
    public static final String OIN_TRANSPORTEUR = "oin-transporteur";

    private final String oinWaardeOndertekenaar;
    private final String oinWaardeTransporteur;

    /**
     * Constructor.
     * @param oinWaardeOndertekenaar de OIN van de ondertekenende partij
     * @param oinWaardeTransporteur de OIN van de transporterende partij
     */
    public OIN(final String oinWaardeOndertekenaar, final String oinWaardeTransporteur) {
        this.oinWaardeOndertekenaar = oinWaardeOndertekenaar;
        this.oinWaardeTransporteur = oinWaardeTransporteur;
    }

    /**
     * @return de OIN-waarde van de ondertekenaar
     */
    public String getOinWaardeOndertekenaar() {
        return oinWaardeOndertekenaar;
    }

    /**
     * @return de OIN-waarde van de transporteur
     */
    public String getOinWaardeTransporteur() {
        return oinWaardeTransporteur;
    }
}
