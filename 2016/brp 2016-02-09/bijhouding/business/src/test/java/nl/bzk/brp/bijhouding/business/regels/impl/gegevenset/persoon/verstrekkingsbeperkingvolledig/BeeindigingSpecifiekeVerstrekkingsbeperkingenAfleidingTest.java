/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingvolledig;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BeeindigingSpecifiekeVerstrekkingsbeperkingenAfleidingTest {

    private BeeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding beeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding;
    private PersoonHisVolledigImpl                                 persoonHisVolledig;

    @Before
    public void init() {
        persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        PersoonVerstrekkingsbeperkingHisVolledigImpl verstrekkingsbeperking = new PersoonVerstrekkingsbeperkingHisVolledigImpl(persoonHisVolledig, null,
            null, null);
        persoonHisVolledig.getVerstrekkingsbeperkingen().add(verstrekkingsbeperking);
        ActieBericht actieBericht = new ActieRegistratieVerstrekkingsbeperkingBericht();
        actieBericht.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2014, 04, 20));
        ActieModel actie = new ActieModel(actieBericht, null);

        verstrekkingsbeperking.getPersoonVerstrekkingsbeperkingHistorie()
            .voegToe(new HisPersoonVerstrekkingsbeperkingModel(verstrekkingsbeperking, actie));
        beeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding = new BeeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding(persoonHisVolledig, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00034a, beeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding.getRegel());
    }

    @Test
    public void testLeidAf() {
        Assert.assertFalse(persoonHisVolledig.getVerstrekkingsbeperkingen().iterator().next().getPersoonVerstrekkingsbeperkingHistorie().isVervallen());
        beeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding.leidAf();
        Assert.assertTrue(persoonHisVolledig.getVerstrekkingsbeperkingen().iterator().next().getPersoonVerstrekkingsbeperkingHistorie().isVervallen());
    }

}
