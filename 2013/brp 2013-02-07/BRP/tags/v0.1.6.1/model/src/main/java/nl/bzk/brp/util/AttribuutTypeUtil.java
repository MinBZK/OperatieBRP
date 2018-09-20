/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import nl.bzk.brp.model.basis.AbstractAttribuutType;
import org.apache.commons.lang.StringUtils;


/**
 * Utility class voor het {@link AttribuutType}.
 *
 */
public final class AttribuutTypeUtil {

    /** default constructor. */
    private AttribuutTypeUtil() {

    }

    /**
     * Controlleert of de AttribuutType geen lege waarde heeft.
     *
     * Voor attribuuType van het type String wordt StringUtils.isNotBlank gebruikt,
     * voor overige type wordt er alleen gekeken of waarde niet null is.
     *
     * @param attribuut AttribuutType
     * @return true als attribuutType waarde gevuld is of voor String type voldoen de voorwaarde van
     *         StringUtils.isNotBlank
     */
    public static boolean isNotBlank(final AbstractAttribuutType<? extends Object> attribuut) {
        boolean isNotBlank;

        if (attribuut != null) {
            if (attribuut.getWaarde() == null) {
                isNotBlank = false;
            } else if (attribuut.getWaarde() instanceof String) {
                isNotBlank = StringUtils.isNotBlank((String) attribuut.getWaarde());
            } else {
                isNotBlank = true;
            }
        } else {
            isNotBlank = false;
        }

        return isNotBlank;
    }

}
