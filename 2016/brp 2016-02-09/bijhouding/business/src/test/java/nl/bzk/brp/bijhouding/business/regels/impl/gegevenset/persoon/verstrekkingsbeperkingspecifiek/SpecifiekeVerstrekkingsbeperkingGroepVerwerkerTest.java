/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class SpecifiekeVerstrekkingsbeperkingGroepVerwerkerTest {

    @Test
    public void testGetRegel() {
        final SpecifiekeVerstrekkingsbeperkingGroepVerwerker verwerker =
            new SpecifiekeVerstrekkingsbeperkingGroepVerwerker(null, null, null);
        Assert.assertEquals(Regel.VR00034, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final PersoonVerstrekkingsbeperkingBericht verstrekkingsbeperkingBericht =
            new PersoonVerstrekkingsbeperkingBericht();

        final PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperkingHisVolledigImpl =
            new PersoonVerstrekkingsbeperkingHisVolledigImpl(null, null, null, null);

        final SpecifiekeVerstrekkingsbeperkingGroepVerwerker verwerker =
            new SpecifiekeVerstrekkingsbeperkingGroepVerwerker(verstrekkingsbeperkingBericht,
                    persoonVerstrekkingsbeperkingHisVolledigImpl, creeerActie());

        Assert.assertEquals(0, persoonVerstrekkingsbeperkingHisVolledigImpl.getPersoonVerstrekkingsbeperkingHistorie().getAantal());
        verwerker.neemBerichtDataOverInModel();
        Assert.assertEquals(1, persoonVerstrekkingsbeperkingHisVolledigImpl.getPersoonVerstrekkingsbeperkingHistorie().getAantal());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final SpecifiekeVerstrekkingsbeperkingGroepVerwerker verwerker =
            new SpecifiekeVerstrekkingsbeperkingGroepVerwerker(null, Mockito.mock(PersoonVerstrekkingsbeperkingHisVolledigImpl.class), creeerActie());
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
        Assert.assertEquals(BeeindigingVolledigeVerstrekkingsbeperkingAfleiding.class,
                verwerker.getAfleidingsregels().iterator().next().getClass());
    }

    /**
     * Creeert een standaard actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @return actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.DUMMY), null, null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130710), null,
                DatumTijdAttribuut.bouwDatumTijd(2013, 7, 9), null);
    }
}
