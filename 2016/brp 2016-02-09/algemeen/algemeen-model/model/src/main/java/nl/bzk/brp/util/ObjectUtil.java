/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import org.apache.commons.lang.StringUtils;


/**
 * Object gerelateerde helper util.
 */
public final class ObjectUtil {

    /**
     * default constructor.
     */
    private ObjectUtil() {

    }

    /**
     * Test of alle doorgegeven objecten null (of empty string) zijn.
     *
     * @param args komma spearated objecten.
     * @return true als alle objecten null (of empty string), false anders
     */
    public static boolean isAllEmpty(final Object... args) {
        boolean isEmpty = true;
        for (Object o : args) {
            if (null != o) {
                if (o instanceof String) {
                    if (StringUtils.isNotBlank((String) o)) {
                        isEmpty = false;
                        break;
                    }
                } else {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    /**
     * Converteert een string naar "" als het null is en anders geeft de waarde terug die opgegeven is.
     *
     * @param waarde waarde dat omgezet moet worden naar "" als het een null is.
     * @return "" of waarde
     */
    public static String converteerNaarEmpty(final String waarde) {
        final String resultaat;
        if (waarde == null) {
            resultaat = "";
        } else {
            resultaat = waarde;
        }

        return resultaat;
    }

    /**
     * Converteer waarde naar false als het null is en anders geeft de waarde terug die opgegeven is.
     *
     * @param waarde waarde op dat omgezet moet worden wanneer het een null is.
     * @return false of waarde
     */
    public static Boolean converteerNaarFalse(final Boolean waarde) {
        final Boolean resultaat;
        if (waarde == null) {
            resultaat = false;
        } else {
            resultaat = waarde;
        }

        return resultaat;
    }
}
