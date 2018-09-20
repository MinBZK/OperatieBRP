/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public class BRBY0525Test {

    @Mock
    private PersoonRepository         persoonRepository;

    @Mock
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    private BRBY0525                       bedrijfsregel;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        bedrijfsregel = new BRBY0525();
        ReflectionTestUtils.setField(bedrijfsregel, "persoonRepository", persoonRepository);
        ReflectionTestUtils.setField(bedrijfsregel, "persoonAdresHistorieRepository", persoonAdresHistorieRepository);

        PersoonModel persoonModel = new PersoonModel(new PersoonBericht());

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.any(Burgerservicenummer.class)))
                .thenReturn(persoonModel);
    }

    @Test
    public void testHistorieBevatGeenIncosistenties() {
        BerichtContext berichtContext = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "ref");
        berichtContext.voegHoofdPersoonToe(PersoonBuilder.bouwPersoon("12345", null, null, null, null, null, null));

        Mockito.when(
                persoonAdresHistorieRepository.haalHistorieGewijzigdeRecordsOp(Matchers.any(PersoonModel.class),
                        Matchers.any(DatumTijd.class))).thenReturn(maakHistorie(false));

        List<Melding> meldingen = bedrijfsregel.executeer(berichtContext);
        Assert.assertEquals(0, meldingen.size());

        ArgumentCaptor<DatumTijd> dateArgument = ArgumentCaptor.forClass(DatumTijd.class);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                new Burgerservicenummer("12345"));

        Mockito.verify(persoonAdresHistorieRepository, Mockito.times(1)).haalHistorieGewijzigdeRecordsOp(
                Matchers.any(PersoonModel.class), dateArgument.capture());

        Assert.assertEquals(new DatumTijd(berichtContext.getTijdstipVerwerking()).getWaarde(), dateArgument.getValue()
                .getWaarde());
    }

    @Test
    public void testHistorieBevatIncosistenties() {
        BerichtContext berichtContext = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "ref");
        berichtContext.voegHoofdPersoonToe(PersoonBuilder.bouwPersoon("12345", null, null, null, null, null, null));

        Mockito.when(
                persoonAdresHistorieRepository.haalHistorieGewijzigdeRecordsOp(Matchers.any(PersoonModel.class),
                        Matchers.any(DatumTijd.class))).thenReturn(maakHistorie(true));

        List<Melding> meldingen = bedrijfsregel.executeer(berichtContext);
        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(meldingen.get(0).getCode(), MeldingCode.BRBY0525);

        ArgumentCaptor<DatumTijd> dateArgument = ArgumentCaptor.forClass(DatumTijd.class);

        Mockito.verify(persoonRepository, Mockito.times(1)).findByBurgerservicenummer(
                new Burgerservicenummer("12345"));

        Mockito.verify(persoonAdresHistorieRepository, Mockito.times(1)).haalHistorieGewijzigdeRecordsOp(
                Matchers.any(PersoonModel.class), dateArgument.capture());

        Assert.assertEquals(new DatumTijd(berichtContext.getTijdstipVerwerking()).getWaarde(), dateArgument.getValue()
                .getWaarde());
    }

    private List<PersoonAdresHisModel> maakHistorie(final boolean inconsistent) {
        int datum = 20120101;

        PersoonModel persoonModel = new PersoonModel(new PersoonBericht());

        PersoonAdresBericht bericht =
            PersoonAdresBuilder.bouwWoonadres("op", 1, "1000AA", new Plaats(), new Partij(), datum);
        PersoonAdresModel persoonAdresModel = new PersoonAdresModel(bericht, persoonModel);

        List<PersoonAdresHisModel> historie = new ArrayList<PersoonAdresHisModel>();

        PersoonAdresHisModel his1 = new PersoonAdresHisModel(persoonAdresModel.getGegevens(), persoonAdresModel);
        his1.setDatumAanvangGeldigheid(new Datum(datum));

        PersoonAdresHisModel his2 = new PersoonAdresHisModel(persoonAdresModel.getGegevens(), persoonAdresModel);
        if (inconsistent) {
            his2.setDatumAanvangGeldigheid(new Datum(20120501));
        } else {
            his2.setDatumAanvangGeldigheid(new Datum(datum));
        }

        PersoonAdresHisModel his3 = new PersoonAdresHisModel(persoonAdresModel.getGegevens(), persoonAdresModel);
        his3.setDatumAanvangGeldigheid(new Datum(datum));

        historie.add(his1);
        historie.add(his2);
        historie.add(his3);

        return historie;
    }
}
