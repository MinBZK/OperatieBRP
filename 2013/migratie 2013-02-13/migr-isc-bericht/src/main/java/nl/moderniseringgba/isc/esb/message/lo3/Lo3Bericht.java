/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;

/**
 * ESB LO3 Bericht.
 */
public interface Lo3Bericht extends Bericht {

    /**
     * Zet een header waarde.
     * 
     * @param veld
     *            header veld
     * @param waarde
     *            waarde
     */
    void setHeader(Lo3HeaderVeld veld, String waarde);

    /**
     * Retourneer een header waarde.
     * 
     * @param veld
     *            header veld
     * @return waarde
     */
    String getHeader(Lo3HeaderVeld veld);

    /**
     * Bericht Originator (verzender).
     * 
     * @return Originator
     */
    String getBronGemeente();

    /**
     * Bericht Originator (verzender).
     * 
     * @param originator
     *            Originator (verzender)
     */
    void setBronGemeente(final String originator);

    /**
     * Bericht Recipient (ontvanger).
     * 
     * @return Recipient
     */
    String getDoelGemeente();

    /**
     * Bericht Recipient (ontvanger).
     * 
     * @param recipient
     *            Recipient (ontvanger)
     */
    void setDoelGemeente(final String recipient);

    /**
     * Parse van een lo3 bericht.
     * 
     * @param lo3Bericht
     *            lo3 bericht
     * @throws BerichtSyntaxException
     *             bij syntax fouten
     * @throws BerichtInhoudException
     *             bij inhoudelijke bericht fouten
     */
    void parse(String lo3Bericht) throws BerichtSyntaxException, BerichtInhoudException;
}
