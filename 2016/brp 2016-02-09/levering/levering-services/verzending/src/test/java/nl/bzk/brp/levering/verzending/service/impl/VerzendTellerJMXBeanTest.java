/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.service.impl;

import java.util.Collections;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VerzendTellerJMXBeanTest {

    private final VerzendTellerJMXBean tellerBean = new VerzendTellerJMXBean();

    @Test
    public void telOp() {

        final VerwerkContext context = new VerwerkContext(0);
        tellerBean.setVerwerkContextList(Collections.singletonList(context));

        Assert.assertEquals(0, tellerBean.getAantalGeleverdeBerichten());
        context.addSucces(1000);
        Assert.assertEquals(1, tellerBean.getAantalGeleverdeBerichten());

    }

    @Test
    public void telErrorOp() {
        final VerwerkContext context = new VerwerkContext(0);
        tellerBean.setVerwerkContextList(Collections.singletonList(context));
        Assert.assertEquals(0, tellerBean.getAantalErrorGeleverdeBerichten());
        context.addError();
        Assert.assertEquals(1, tellerBean.getAantalErrorGeleverdeBerichten());

    }
}
