/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * Simple utility class die een random String genereeert van 4 cijfer.
 *
 */
public final class PrefixBuilder {

    private PrefixBuilder() {
    }

    private static Random rand = new Random(4 * 1024);

    /**
     * genereert een string met 4 cijfers gevolgd door een punt.
     * @return de string.
     */
    public static String getPrefix() {
        return StringUtils.leftPad("" + rand.nextInt(1024), 4, "0") + ".";
    }
}
