/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.util.Random;


/**
 * Genereert 'random' technische sleutels.
 */
public final class RandomTechnischeSleutelService {

    private RandomTechnischeSleutelService() {
    }

    public static int randomTechSleutel() {
        return Math.abs(new Random().nextInt());
    }

    public static long randomLongTechSleutel() {
        return Math.abs(new Random().nextLong());
    }
}
