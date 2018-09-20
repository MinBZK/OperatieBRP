/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class MarshallingContextFactoryImplTest {

    @Test
    public final void testNieuweMarshallingContext() throws JiBXException {
        final MarshallingContextFactoryImpl marshallingContextFactory = new MarshallingContextFactoryImpl();
        final IMarshallingContext iMarshallingContext =
                marshallingContextFactory.nieuweMarshallingContext(AbstractSynchronisatieBericht.class);
        Assert.assertNotNull(iMarshallingContext);
    }
}
