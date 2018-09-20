/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen;

/**
 * Diverse constanten nodig in de diverse levering projecten.
 */
public final class LeveringConstanten {

    /**
     * Constante ten behoeve van berichtinhoud-property in JMS bericht.
     */
    public static final String JMS_PROPERTY_KEY_XML_BERICHT = "afnemerXmlBericht";

    /**
     * Constante ten behoeve van JSON berichtinhoud-property in JMS bericht.
     */
    public static final String JMS_PROPERTY_KEY_BERICHT_GEGEVENS     = "gegevens";
    /**
     * Constante tbv Code van de ontvangende partij in JMS bericht.
     */
    public static final String JMS_PROPERTY_PARTIJ_CODE              = "partijCode";
    /**
     * Constante tbv Naam van leveringsautorisatie in JMS bericht.
     */
    public static final String JMS_PROPERTY_LEVERINGS_AUTORISATIE_ID = "leveringsautorisatieId";
    /**
     * Constante tbv Aflever URI in JMS bericht.
     */
    public static final String JMS_PROPERTY_BRP_AFLEVER_URI          = "brpAfleverURI";

    /**
     * Constante tbv van protocolleringniveau in JMS bericht.
     */
    public static final String JMS_PROPERTY_PROTOCOLLERINGNIVEAU = "protocolleringNiveau";

    /**
     * Prive constructor voor deze constante klasse, zodat hij niet geinitieerd kan worden.
     */
    private LeveringConstanten() {

    }

}
