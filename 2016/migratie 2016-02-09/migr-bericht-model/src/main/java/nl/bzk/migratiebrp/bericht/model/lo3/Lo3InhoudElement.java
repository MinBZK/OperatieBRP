/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.util.gbav.GBACharacterSet;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * LO3 Inhoud element.
 */
public final class Lo3InhoudElement {

    private static final int ELEMENT_LENGTE_LENGTE = 3;

    private static final int ELEMENTNUMMER_LENGTE = 4;

    private final String element;
    private final String inhoud;
    private final byte[] data;

    /**
     * Maak een element.
     *
     * @param element
     *            elementnummer (moet 4 cijfers zijn)
     * @param inhoud
     *            inhoud (unicode)
     * @throws IllegalArgumentException
     *             als het elementnummer niet uit vier cijfers bestaat
     */
    Lo3InhoudElement(final Lo3ElementEnum element, final String inhoud) {
        this.element = element.getElementNummer();
        this.inhoud = padInhoud(element, inhoud);

        final byte[] teletexInhoud = asTeletex(this.inhoud);

        data = new byte[ELEMENTNUMMER_LENGTE + ELEMENT_LENGTE_LENGTE + teletexInhoud.length];
        System.arraycopy(this.element.getBytes(), 0, data, 0, ELEMENTNUMMER_LENGTE);
        System.arraycopy(String.format("%1$03d", teletexInhoud.length).getBytes(), 0, data, ELEMENTNUMMER_LENGTE, ELEMENT_LENGTE_LENGTE);
        System.arraycopy(teletexInhoud, 0, data, ELEMENTNUMMER_LENGTE + ELEMENT_LENGTE_LENGTE, teletexInhoud.length);
    }

    /**
     * Geef de waarde van element.
     *
     * @return element
     */
    public String getElement() {
        return element;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Geef de byte weergave van dit element.
     *
     * @return teletex encoded byte weergave van dit element
     */
    public byte[] getBytes() {
        return ArrayUtils.clone(data);
    }

    private static byte[] asTeletex(final String inhoud) {
        if (inhoud == null || "".equals(inhoud)) {
            return new byte[] {};
        } else {
            return GBACharacterSet.convertTeletexStringToByteArray(GBACharacterSet.converteerUnicodeNaarTeletex(inhoud));
        }
    }

    /* ************************************************************************************************************* */

    private String padInhoud(final Lo3ElementEnum elementEnum, final String elementInhoud) {
        final String result;
        if ("".equals(elementInhoud)) {
            result = "";
        } else {
            switch (elementEnum.getType()) {
                case NUMERIEK:
                    result = StringUtils.leftPad(elementInhoud, elementEnum.getMinimumLengte(), '0');
                    break;
                case ALPHANUMERIEK:
                    result = StringUtils.leftPad(elementInhoud, elementEnum.getMinimumLengte(), ' ');
                    break;
                default:
                    result = elementInhoud;
            }
        }

        return result;
    }
}
