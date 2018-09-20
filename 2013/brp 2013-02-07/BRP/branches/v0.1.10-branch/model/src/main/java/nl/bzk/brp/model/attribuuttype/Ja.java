/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.attribuuttype;

/**
 * Ja.
 */
public enum Ja {

    /** Ja. */
    Ja("J");

    private String xmlCode;

    /**
     * Constructor.
     * @param xmlCode De Xml representatie van deze enum.
     */
    Ja(final String xmlCode) {
        this.xmlCode = xmlCode;
    }

    /**
     * Retourneert de Xml representatie van deze enum.
     * @return De Xml representatie van deze enum.
     */
    public String getXmlCode() {
        return xmlCode;
    }
}
