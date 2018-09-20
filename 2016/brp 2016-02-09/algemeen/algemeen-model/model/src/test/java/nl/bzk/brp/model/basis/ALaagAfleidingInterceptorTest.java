/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test klasse voor de {@link ALaagAfleidingInterceptor} en de daarin opgenomen methodes.
 */
public class ALaagAfleidingInterceptorTest {

    private final ALaagAfleidingInterceptor interceptor = new ALaagAfleidingInterceptor();

    @Test
    public void testPreFlushZonderEntities() {
        // Test dat er geen problemen optreden zonder entities
        interceptor.preFlush(null);
        interceptor.preFlush(Arrays.asList().iterator());
    }

    @Test
    public void testPreFlushMetEntities() {
        Object entity1 = Mockito.mock(ALaagAfleidbaar.class);
        Object entity2 = new Object();
        Object entity3 = Mockito.mock(ALaagAfleidbaar.class);
        Object entity4 = new Object();

        interceptor.preFlush(Arrays.asList(entity1, entity2, entity3, entity4).iterator());

        Mockito.verify(((ALaagAfleidbaar) entity1), Mockito.times(1)).leidALaagAf();
        Mockito.verify(((ALaagAfleidbaar) entity3), Mockito.times(1)).leidALaagAf();
    }

}
