/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import org.jibx.runtime.IUnmarshallingContext;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class GeneriekeAbstractBerichtIdentificeerbaarTest {

    private PersoonBericht persoonBericht = new PersoonBericht();

    @Test
    public void testVoegToeIdentificeerbaarObjectAanJixbUserContextNiksInContext() {
        final IUnmarshallingContext ctx = Mockito.mock(IUnmarshallingContext.class);
        Mockito.when(ctx.getStackObject(Mockito.anyInt())).thenReturn(
            new BerichtBericht(new SoortBerichtAttribuut(SoortBericht.DUMMY)) {

            });

        persoonBericht.jibxPostSet(ctx);

        Mockito.verify(ctx, Mockito.times(1)).getUserContext();
        Mockito.verify(ctx, Mockito.times(1)).setUserContext(Matchers.any(CommunicatieIdMap.class));
    }

    @Test
    public void testVoegToeIdentificeerbaarObjectAanJixbUserContextIetsInContext() {
        final IUnmarshallingContext ctx = Mockito.mock(IUnmarshallingContext.class);
        Mockito.when(ctx.getUserContext()).thenReturn(new CommunicatieIdMap());

        persoonBericht.jibxPostSet(ctx);
        Mockito.verify(ctx, Mockito.times(0)).setUserContext(Matchers.any(CommunicatieIdMap.class));
        Mockito.verify(ctx, Mockito.times(2)).getUserContext();
    }

}
