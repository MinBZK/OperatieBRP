/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message;

/**
 * Basis voor berichten.
 */
public interface Bericht {
    /**
     * Bericht ID.
     * 
     * @return bericht ID
     */
    String getMessageId();

    /**
     * Bericht ID.
     * 
     * @param messageId
     *            bericht ID
     */
    void setMessageId(final String messageId);

    /**
     * Correlatie ID.
     * 
     * @return correlatie ID
     */
    String getCorrelationId();

    /**
     * Correlatie ID.
     * 
     * @param correlationId
     *            correlatie ID
     */
    void setCorrelationId(final String correlationId);

    /**
     * Bericht type.
     * 
     * @return bericht type
     */
    String getBerichtType();

    /**
     * Welke cyclus wordt gestart door dit bericht?
     * 
     * @return proces identificatie
     */
    String getStartCyclus();

    /**
     * Format naar een string bericht.
     * 
     * @return String representatie van het bericht
     * @throws BerichtInhoudException
     *             In het gaval de inhoud niet conformeert aan de XSD.
     */
    String format() throws BerichtInhoudException;
}
