/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieBericht;

import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test klasse voor het testen van de methodes in de {@link AbstractMaterieleHistorieGroepBericht} klasse.
 */
public class GeneriekeAbstractMaterieleHistorieGroepBerichtTest {

    @Test
    public void testMissendeContext() {
        final AbstractMaterieleHistorieGroepBericht groep = bouwGroep();

        groep.zetMaterieleHistorieDatums(null);
        Assert.assertNull(groep.getDatumAanvangGeldigheid());
        Assert.assertNull(groep.getDatumEindeGeldigheid());
    }

    @Test
    public void testContextMetAlleenObjectOpStack() {
        final AbstractMaterieleHistorieGroepBericht groep = bouwGroep();

        groep.zetMaterieleHistorieDatums(bouwContext(groep));
        Assert.assertNull(groep.getDatumAanvangGeldigheid());
        Assert.assertNull(groep.getDatumEindeGeldigheid());
    }

    @Test
    public void testContextZonderActieInStack() {
        final AbstractMaterieleHistorieGroepBericht groep = bouwGroep();

        groep.zetMaterieleHistorieDatums(bouwContext(groep, new Object(), new Object()));
        Assert.assertNull(groep.getDatumAanvangGeldigheid());
        Assert.assertNull(groep.getDatumEindeGeldigheid());
    }

    @Test
    public void testContextMetActieInStack() {
        final AbstractMaterieleHistorieGroepBericht groep = bouwGroep();

        final ActieBericht actie = new ActieBericht(null) {

        };
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130312));
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20490728));
        groep.zetMaterieleHistorieDatums(bouwContext(groep, new Object(), actie, new Object()));
        Assert.assertEquals(Integer.valueOf(20130312), groep.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(Integer.valueOf(20490728), groep.getDatumEindeGeldigheid().getWaarde());
    }

    /**
     * Bouwt een nieuwe instantie van de {@link AbstractMaterieleHistorieGroepBericht} klasse en zet daarop de twee
     * datums naar <code>null</code>.
     *
     * @return een nieuwe {@link AbstractMaterieleHistorieGroepBericht} instantie met lege datums.
     */
    private AbstractMaterieleHistorieGroepBericht bouwGroep() {
        final AbstractMaterieleHistorieGroepBericht groep = new AbstractMaterieleHistorieGroepBericht() {

            @Override
            public List<Attribuut> getAttributen() {
                return new ArrayList<>();
            }

            @Override
            public Integer getMetaId() {
                return null;
            }

            @Override
            public boolean bevatElementMetMetaId(final Integer id) {
                return false;
            }

        };
        groep.setDatumAanvangGeldigheid(null);
        groep.setDatumEindeGeldigheid(null);
        return groep;
    }

    /**
     * Bouwt een {@link IUnmarshallingContext} instantie op met de opgegeven objecten in de stack.
     *
     * @param stackObjecten de objecten waarvan een stack wordt gebouwd en die door de context wordt gebruikt.
     * @return een nieuwe {@link IUnmarshallingContext} instantie met een stack.
     */
    private IUnmarshallingContext bouwContext(final Object... stackObjecten) {
        final IUnmarshallingContext context = new UnmarshallingContext();

        for (Object obj : stackObjecten) {
            context.pushObject(obj);
        }
        return context;
    }
}
