/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.dto;

/**
 * XmlVerzoek bevat de gegevens om een XmlVerzoek te doen.
 */
public final class XmlVerzoek {

    private static final String NULL = "null";
    private String xmlPath;
    private String ondertekenaar;
    private String transporteur;

    /**
     * Constructor.
     *
     * @param xmlPath    file path naar het XML verzoek
     * @param ondertekenaar de partijCode van de ondertekenaar
     * @param transporteur  de partijdCode van de transporteur
     */
    public XmlVerzoek(final String xmlPath, final String ondertekenaar, final String transporteur) {
        this.xmlPath = xmlPath;

        if (!NULL.equals(ondertekenaar)) {
            this.ondertekenaar = ondertekenaar;
        }
        if (!NULL.equals(transporteur)) {
            this.transporteur = transporteur;
        }
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public String getOndertekenaar() {
        return ondertekenaar;
    }

    public String getTransporteur() {
        return transporteur;
    }
}
