/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.impl.algemeen.BRAL9003;
import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonOpschortingGroepModel;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BRAL9003Test {

    @Mock
    private PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    private BRAL9003                             bral9003;

    private PersoonModel                         persoon1;

    private PersoonModel                         persoon0;

    @Before
    public void init() {

        persoon1 = new PersoonModel(new AbstractPersoonModel() {
        });

        persoon0 = new PersoonModel(new AbstractPersoonModel() {
        });

        bral9003 = new BRAL9003();
        ReflectionTestUtils.setField(bral9003, "persoonOpschortingHistorieRepository",
                persoonOpschortingHistorieRepository);

        Mockito.when(persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(persoon1)).thenReturn(
                new Datum(20120505));
        Mockito.when(persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(persoon0)).thenReturn(null);
    }

    @Test
    public void testHuidigeSituatieGeenOpschorting() {
        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository, Mockito.times(0)).haalOpActueleDatumOpschorting(
                (PersoonModel) Matchers.anyObject());

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuidigeSituatieLeeg() {
        List<Melding> meldingen = bral9003.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository, Mockito.times(0)).haalOpActueleDatumOpschorting(
                (PersoonModel) Matchers.anyObject());

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuidigeSituatieWelOpschortingMaarGeenOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.MINISTER);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon0, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon0, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository,
                Mockito.times(1)).haalOpActueleDatumOpschorting(persoon0);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWijzigingDatumVoorOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.MINISTER);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120504)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWijzigingDatumOpOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.MINISTER);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120505)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, meldingen.get(0).getCode());
    }

    @Test
    public void testWijzigingDatumNaOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.MINISTER);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120506)).getActie());

        Mockito.verify(persoonOpschortingHistorieRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, meldingen.get(0).getCode());
    }

}
