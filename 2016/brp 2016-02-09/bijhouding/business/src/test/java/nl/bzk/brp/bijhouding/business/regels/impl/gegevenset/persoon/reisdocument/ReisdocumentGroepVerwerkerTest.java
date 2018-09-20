/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;


import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonReisdocumentHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ReisdocumentGroepVerwerkerTest {

    @Test
    public void testGetRegel() {
        final ReisdocumentGroepVerwerker verwerker = new ReisdocumentGroepVerwerker(null, null, null);
        Assert.assertEquals(Regel.VR00020, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final PersoonReisdocumentBericht reisdocumentBericht = new PersoonReisdocumentBericht();
        reisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());

        final PersoonReisdocumentHisVolledigImpl reisdocumentHisVolledig =
                new PersoonReisdocumentHisVolledigImplBuilder(null).build();

        final ReisdocumentGroepVerwerker
                verwerker = new ReisdocumentGroepVerwerker(reisdocumentBericht, reisdocumentHisVolledig,
                        creeerActie(SoortAdministratieveHandeling.VERKRIJGING_REISDOCUMENT));

        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(reisdocumentHisVolledig.getPersoonReisdocumentHistorie().getActueleRecord());
    }

    @Test
    public void testNeemBerichtDataOverInModelOnttrekking() {
        AanduidingInhoudingVermissingReisdocument dummyReden = Mockito.mock(AanduidingInhoudingVermissingReisdocument.class);

        final PersoonReisdocumentBericht reisdocumentBericht = new PersoonReisdocumentBericht();
        reisdocumentBericht.setStandaard(new PersoonReisdocumentStandaardGroepBericht());
        reisdocumentBericht.getStandaard().setAanduidingInhoudingVermissing(new AanduidingInhoudingVermissingReisdocumentAttribuut(dummyReden));

        final PersoonReisdocumentHisVolledigImpl reisdocumentHisVolledig =
                new PersoonReisdocumentHisVolledigImplBuilder(null).nieuwStandaardRecord(20130610).eindeRecord().build();

        final ReisdocumentGroepVerwerker verwerker = new ReisdocumentGroepVerwerker(reisdocumentBericht, reisdocumentHisVolledig,
                                                                                    creeerActie(SoortAdministratieveHandeling.ONTTREKKING_REISDOCUMENT));

        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(dummyReden,
                            reisdocumentHisVolledig.getPersoonReisdocumentHistorie().getActueleRecord().getAanduidingInhoudingVermissing().getWaarde());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final ReisdocumentGroepVerwerker verwerker = new ReisdocumentGroepVerwerker(null, null,
                                                                                    creeerActie(SoortAdministratieveHandeling.VERKRIJGING_REISDOCUMENT));
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());
    }

    /**
     * Creeert een standaard actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @return actie model
     */
    private ActieModel creeerActie(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_REISDOCUMENT),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(soortAdministratieveHandeling), null,
                                                                null, null),
                              null, new DatumEvtDeelsOnbekendAttribuut(20130710), null, DatumTijdAttribuut.bouwDatumTijd(2013, 7, 9), null);
    }
}
