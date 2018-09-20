/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.util.ActieBerichtBuilder;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOpschortingGroepModel;
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
    private HistoriePersoonOpschortingRepository historiePersoonOpschortingRepository;

    private BRAL9003 bral9003;

    private PersoonModel persoon1;

    private PersoonModel persoon0;

    @Before
    public void init() {

        persoon1 = new PersoonModel(SoortPersoon.INGESCHREVENE);

        persoon0 = new PersoonModel(SoortPersoon.INGESCHREVENE);

        bral9003 = new BRAL9003();
        ReflectionTestUtils.setField(bral9003, "historiePersoonOpschortingRepository",
                                     historiePersoonOpschortingRepository);

        Mockito.when(historiePersoonOpschortingRepository.haalOpActueleDatumOpschorting(persoon1)).thenReturn(
            new Datum(20120505));
        Mockito.when(historiePersoonOpschortingRepository.haalOpActueleDatumOpschorting(persoon0)).thenReturn(null);
    }

    @Test
    public void testHuidigeSituatieGeenOpschorting() {
        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository, Mockito.times(0)).haalOpActueleDatumOpschorting(
            (PersoonModel) Matchers.anyObject());

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuidigeSituatieLeeg() {
        List<Melding> meldingen = bral9003.executeer(null, null, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository, Mockito.times(0)).haalOpActueleDatumOpschorting(
            (PersoonModel) Matchers.anyObject());

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuidigeSituatieWelOpschortingMaarGeenOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.MINISTERIEEL_BESLUIT);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon0, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon0, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20150101)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository,
            Mockito.times(1)).haalOpActueleDatumOpschorting(persoon0);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWijzigingDatumVoorOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.MINISTERIEEL_BESLUIT);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120504)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(0, meldingen.size());
    }

    @Test
    public void testWijzigingDatumOpOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.MINISTERIEEL_BESLUIT);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120505)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, meldingen.get(0).getCode());
    }

    @Test
    public void testWijzigingDatumNaOpschortingDatum() {
        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.MINISTERIEEL_BESLUIT);

        PersoonOpschortingGroepModel opschortingModel = new PersoonOpschortingGroepModel(opschorting);

        ReflectionTestUtils.setField(persoon1, "opschorting", opschortingModel);

        PersoonBericht nieuweSituatie = new PersoonBericht();

        List<Melding> meldingen = bral9003.executeer(persoon1, nieuweSituatie, ActieBerichtBuilder.bouwNieuweActie(
            SoortActie.REGISTRATIE_NATIONALITEIT).setDatumAanvang(new Datum(20120506)).getActie());

        Mockito.verify(historiePersoonOpschortingRepository, Mockito.times(1)).haalOpActueleDatumOpschorting(persoon1);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(SoortMelding.DEBLOKKEERBAAR, meldingen.get(0).getSoort());
        Assert.assertEquals(MeldingCode.BRAL9003, meldingen.get(0).getCode());
    }

}
