/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;

/**
 * ESB LO3 Bericht.
 */
public interface Lo3Bericht extends Bericht {

    /**
     * Zet een header waarde.
     * @param veld header veld
     * @param waarde waarde
     */
    void setHeader(Lo3HeaderVeld veld, String waarde);

    /**
     * Retourneer een header waarde.
     * @param veld header veld
     * @return waarde
     */
    String getHeaderWaarde(Lo3HeaderVeld veld);

    /**
     * Retourneer een lijst van header waarden voor een variabele lengte veld.
     * @param veld variabele lengte veld
     * @return een lijst van waarden
     * @throws IllegalArgumentException voor een veld dat niet een variabele lengte heeft
     */
    List<String> getHeaderWaarden(Lo3HeaderVeld veld);

    /**
     * Geef de header definitie voor dit bericht.
     * @return header definitie
     */
    Lo3Header getHeader();

    /**
     * Bericht Originator (verzender).
     * @return Originator
     */
    String getBronPartijCode();

    /**
     * Bericht Originator (verzender).
     * @param originator Originator (verzender)
     */
    void setBronPartijCode(final String originator);

    /**
     * Bericht Recipient (ontvanger).
     * @return Recipient
     */
    String getDoelPartijCode();

    /**
     * Bericht Recipient (ontvanger).
     * @param recipient Recipient (ontvanger)
     */
    void setDoelPartijCode(final String recipient);

    /**
     * Parse van een lo3 bericht.
     * @param lo3Bericht lo3 bericht
     * @throws BerichtSyntaxException bij syntax fouten
     * @throws BerichtInhoudException bij inhoudelijke bericht fouten
     */
    void parse(String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException;
}
