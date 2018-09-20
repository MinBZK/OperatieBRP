/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

/**
 * Constanten binnen de ESB.
 */
public final class EsbConstants {

    /** ESB message property waarin het message id (String) wordt bewaard. */
    public static final String PROPERTY_MESSAGE_ID = "iscBerichtReferentie";

    /** ESB message property waarin het correlatieid (String) wordt bewaard. */
    public static final String PROPERTY_CORRELATIE_ID = "iscCorrelatieReferentie";

    /** ESB message property waarin de bron gemeente (String) wordt bewaard. */
    public static final String PROPERTY_BRON_GEMEENTE = "voaOriginator";

    /** ESB message property waarin de doel gemeente (String) wordt bewaard. */
    public static final String PROPERTY_DOEL_GEMEENTE = "voaRecipient";

    /** ESB message property waarin de herhaling (Integer) wordt bewaard. */
    public static final String PROPERTY_HERHALING = "iscHerhaling";

    /** ESB message property waarin de indicatie herhaling (Boolean) wordt bewaard. */
    public static final String PROPERTY_INDICATIE_HERHALING = "iscIndicatieHerhaling";

    /** ESB message property waarin de bericht naam (String) wordt bewaard. */
    public static final String PROPERTY_NAAM = "iscNaam";

    /** ESB message property waarin het mig_berichten.id (Long) wordt bewaard. */
    public static final String PROPERTY_BERICHT = "iscBerichtId";

    private EsbConstants() {
    }
}
