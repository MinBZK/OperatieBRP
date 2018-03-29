/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.lang.reflect.Method;
import org.junit.Test;

/**
 * Testen voor {@link AbstractDelegateBetrokkenheid}.
 */
public class DelegateBetrokkenheidTest {

    @Test
    public void testDelegateMethodesAanwezig() throws NoSuchMethodException {
        for (final Method methodeVanBetrokkenheid : Betrokkenheid.class.getMethods()) {
            if (!ExcludedMethodList.EXCLUDED_METHODS.contains(methodeVanBetrokkenheid.getName())) {
                AbstractDelegateBetrokkenheid.class.getDeclaredMethod(methodeVanBetrokkenheid.getName(), methodeVanBetrokkenheid.getParameterTypes());
            }
        }
    }
}
