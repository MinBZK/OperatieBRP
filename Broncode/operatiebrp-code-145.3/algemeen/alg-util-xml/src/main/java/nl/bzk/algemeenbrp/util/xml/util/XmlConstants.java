/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.xml.util;

/**
 * Constant XML values.
 */
public final class XmlConstants {

    /**
     * Element start (&lt;).
     */
    public static final String START_ELEMENT = "<";
    /**
     * Element end (&gt;).
     */
    public static final String ELEMENT_END = ">";
    /**
     * Element close (/&gt;).
     */
    public static final String ELEMENT_CLOSE = "/>";
    /**
     * End element start (&lt;/).
     */
    public static final String END_ELEMENT = "</";

    private XmlConstants() {
        // Niet instantieerbaar
    }
}
