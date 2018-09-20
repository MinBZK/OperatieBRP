/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import nl.bzk.brp.dataaccess.repository.PersoonCacheRepository;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.dataaccess.repository.jpa.PersoonHisVolledigJpaRepository;
import nl.bzk.brp.expressietaal.expressies.BRPExpressies;
import nl.bzk.brp.expressietaal.expressies.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSerializer;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BRPExpressies.class)
public class PersoonLeveringServiceImplTest {

    @Mock
    private BestandRepository bestandRepository;

    @Mock
    private PersoonHisVolledigJpaRepository persoonHisVolledigRepository;

    @Mock
    private PersoonCacheRepository persoonCacheRepository;

    @Mock
    private PersoonHisVolledigSerializer persoonHisVolledigSerializer;

    @InjectMocks
    private PersoonLeveringServiceImpl persoonLeveringService;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(persoonLeveringService, "populatieBeperking", "geboorte.datum > 1950/01/01");
        persoonLeveringService.postConstruct();

        PowerMockito.mockStatic(BRPExpressies.class);
    }

    @Test
    public void kanPersoonLeverenOpBasisVanBytes() {
        // given
        when(persoonHisVolledigSerializer.deserializeer(any(byte[].class))).thenReturn(maakTestPersoonJohnnyDonnyJordaan());
        when(BRPExpressies.evalueer(any(Expressie.class), any(PersoonView.class))).thenReturn(
                new BooleanLiteralExpressie(true));

        // when
        persoonLeveringService.leverPersoon(new byte[]{});
    }

    @Test
    public void kanPersoonLeverenOpBasisVanId() {
        // given
        PersoonCacheModel mockCache = mock(PersoonCacheModel.class, RETURNS_DEEP_STUBS);
        when(mockCache.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde()).thenReturn(new byte[]{});

        when(persoonHisVolledigRepository.leesGeserializeerdModel(anyInt())).thenReturn(maakTestPersoonJohnnyDonnyJordaan());
        when(BRPExpressies.evalueer(any(Expressie.class), any(PersoonView.class))).thenReturn(
                new BooleanLiteralExpressie(true));

        // when
        persoonLeveringService.leverPersoon(1);
    }



    public static PersoonHisVolledigImpl maakTestPersoonJohnnyDonnyJordaan(){
        PersoonVoornaamHisVolledigImplBuilder persoonVoornaamHisVolledigImplBuilder =
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1));
        persoonVoornaamHisVolledigImplBuilder
                .nieuwStandaardRecord(20130101, null, 20130101).naam("Sjonnie")
                .eindeRecord();

        PersoonVoornaamHisVolledigImplBuilder persoonVoornaamHisVolledigImplBuilder2 =
                new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(2));
        persoonVoornaamHisVolledigImplBuilder2
                .nieuwStandaardRecord(20120101, 20130101, 20130101).naam("Gonny")
                .eindeRecord()
                .nieuwStandaardRecord(20130101, null, 20130101).naam("Donny")
                .eindeRecord();

        PersoonGeslachtsnaamcomponentHisVolledigImplBuilder persoonGeslachtsnaamcomponentHisVolledigImplBuilder =
                new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1));
        persoonGeslachtsnaamcomponentHisVolledigImplBuilder
                .nieuwStandaardRecord(20130101, null, 20130101).naam("Jordaan")
                .eindeRecord();

        PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        PersoonHisVolledigImpl persoon = persoonHisVolledigImplBuilder
                .voegPersoonVoornaamToe(persoonVoornaamHisVolledigImplBuilder.build())
                .voegPersoonVoornaamToe(persoonVoornaamHisVolledigImplBuilder2.build())
                .voegPersoonGeslachtsnaamcomponentToe(persoonGeslachtsnaamcomponentHisVolledigImplBuilder.build())
                .nieuwBijhoudingsgemeenteRecord(20130101, null, 20130101)
                .bijhoudingsgemeente(12345).datumInschrijvingInGemeente(20130101).eindeRecord()
                .nieuwIdentificatienummersRecord(20120101, 20130101, 20120101)
                .burgerservicenummer(1234567890).eindeRecord()
                .nieuwIdentificatienummersRecord(20130101, null, 20130101)
                .burgerservicenummer(987654321).eindeRecord()
                .build();

        ReflectionTestUtils.setField(persoon, "iD", 1);

        return persoon;
    }
}
