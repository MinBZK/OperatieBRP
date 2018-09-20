/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service.impl;

import java.lang.reflect.Constructor;

import nl.bzk.brp.bijhouding.domein.brm.Regel;
import org.springframework.test.util.ReflectionTestUtils;


public final class BedrijfsregelTestUtils {

    /**
     * Constructor private gemaakt.
     */
    private BedrijfsregelTestUtils() {
    }

    public static Regel creeerRegel(final String code) {
        Regel resultaat;
        try {
            Constructor<Regel> constructor = Regel.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            resultaat = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ReflectionTestUtils.setField(resultaat, "code", code);
        return resultaat;
    }

}
