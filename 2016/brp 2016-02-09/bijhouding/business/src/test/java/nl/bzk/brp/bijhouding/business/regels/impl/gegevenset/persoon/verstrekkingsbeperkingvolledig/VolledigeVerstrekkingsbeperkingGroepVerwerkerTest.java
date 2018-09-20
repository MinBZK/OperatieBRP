/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingvolledig;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class VolledigeVerstrekkingsbeperkingGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel, PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00021;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel,
            PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> model,
            final ActieModel actie)
    {
        return new VolledigeVerstrekkingsbeperkingGroepVerwerker(bericht,
                                         (PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(getPersoonHisVolledigImpl());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final VolledigeVerstrekkingsbeperkingGroepVerwerker verwerker =
            new VolledigeVerstrekkingsbeperkingGroepVerwerker(null, Mockito.mock(PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl.class), null);
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
        Assert.assertEquals(BeeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding.class,
                verwerker.getAfleidingsregels().iterator().next().getClass());
    }

}
