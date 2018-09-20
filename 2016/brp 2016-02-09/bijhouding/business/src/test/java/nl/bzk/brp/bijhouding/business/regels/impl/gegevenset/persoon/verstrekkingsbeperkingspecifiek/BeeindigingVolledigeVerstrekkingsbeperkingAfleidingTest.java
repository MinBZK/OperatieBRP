/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;

import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeeindigingVolledigeVerstrekkingsbeperkingAfleidingTest {

    private BeeindigingVolledigeVerstrekkingsbeperkingAfleiding beeindigingVolledigeVerstrekkingsbeperkingAfleiding;
    private PersoonHisVolledigImpl                              persoonHisVolledig;

    @Before
    public void init() {
        persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl indicatie = new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(
            persoonHisVolledig);
        persoonHisVolledig.setIndicatieVolledigeVerstrekkingsbeperking(indicatie);
        ActieBericht actieBericht = new ActieRegistratieVerstrekkingsbeperkingBericht();
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2014, 04, 20));
        ActieModel actie = new ActieModel(actieBericht, null);
        MaterieleHistorie materieleHistorie = new MaterieleHistorie() {
            @Override
            public DatumTijdAttribuut getTijdstipRegistratie() {
                return DatumTijdAttribuut.bouwDatumTijd(2014, 04, 20);
            }

            @Override
            public DatumTijdAttribuut getDatumTijdVerval() {
                return null;
            }

            @Override
            public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
                return null;
            }

            @Override
            public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
                return new DatumEvtDeelsOnbekendAttribuut(20140420);
            }
        };

        indicatie.getPersoonIndicatieHistorie().voegToe(new HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(indicatie, new JaAttribuut(Ja.J), actie, materieleHistorie));
        beeindigingVolledigeVerstrekkingsbeperkingAfleiding = new BeeindigingVolledigeVerstrekkingsbeperkingAfleiding(persoonHisVolledig, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00021b, beeindigingVolledigeVerstrekkingsbeperkingAfleiding.getRegel());
    }

    @Test
    public void testLeidAf() {
        Assert.assertFalse(persoonHisVolledig.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie().isVervallen());
        beeindigingVolledigeVerstrekkingsbeperkingAfleiding.leidAf();
        Assert.assertTrue(persoonHisVolledig.getIndicatieVolledigeVerstrekkingsbeperking().getPersoonIndicatieHistorie().isVervallen());
    }

}
