/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.gba;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.delivery.selectie.gba.berichten.Ag11BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sf01BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sv01BerichtFactory;
import nl.bzk.brp.delivery.selectie.gba.berichten.Sv11BerichtFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.service.algemeen.MaakPersoonBerichtService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.dalapi.BlokkeringRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MaakGbaPersoonBerichtServiceImplTest {

    private static final String AG_11_BERICHT_ALS_STRING = "00000000Ag11A0000000000066010610110010212510940901200095019249050210005Aafje0240009Staartjes";
    private static final String SF_01_BERICHT_ALS_STRING = "00000000Sf01000220101701100103450924321";
    private static final String SV_01_BERICHT_ALS_STRING = "00000000Sv01O2013111400077010720110010843242473701200093066860410210005Cindy0240005Loper"
            + "031000819660821";
    private static final String SV_11_BERICHT_ALS_STRING = "00000000Sv11000000000";
    private static final String A_NUMMER = "3450924321";

    private BerichtFactory berichtFactory = Mockito.mock(BerichtFactory.class);
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository = Mockito.mock(Lo3FilterRubriekRepository.class);
    private BlokkeringRepository blokkeringRepository = Mockito.mock(BlokkeringRepository.class);
    private Ag11BerichtFactory ag11BerichtFactory = new Ag11BerichtFactory(berichtFactory, lo3FilterRubriekRepository);
    private Sf01BerichtFactory sf01BerichtFactory = new Sf01BerichtFactory(berichtFactory);
    private Sv01BerichtFactory sv01BerichtFactory = new Sv01BerichtFactory(berichtFactory, lo3FilterRubriekRepository);
    private Sv11BerichtFactory sv11BerichtFactory = new Sv11BerichtFactory(berichtFactory);
    private Dienst dienst = Mockito.mock(Dienst.class);
    private Persoonslijst persoonslijst;
    private static final Bericht AG11_BERICHT = Mockito.mock(Bericht.class);
    private static final Bericht SF01_BERICHT = Mockito.mock(Bericht.class);
    private static final Bericht SV01_BERICHT = Mockito.mock(Bericht.class);
    private static final Bericht SV11_BERICHT = Mockito.mock(Bericht.class);

    MaakPersoonBerichtService
            subject =
            new MaakGbaPersoonBerichtServiceImpl(ag11BerichtFactory, blokkeringRepository, sf01BerichtFactory, sv01BerichtFactory, sv11BerichtFactory);

    @Before
    public void setup() {
        Mockito.when(AG11_BERICHT.maakUitgaandBericht()).thenReturn(AG_11_BERICHT_ALS_STRING);
        Mockito.when(SF01_BERICHT.maakUitgaandBericht()).thenReturn(SF_01_BERICHT_ALS_STRING);
        Mockito.when(SV01_BERICHT.maakUitgaandBericht()).thenReturn(SV_01_BERICHT_ALS_STRING);
        Mockito.when(SV11_BERICHT.maakUitgaandBericht()).thenReturn(SV_11_BERICHT_ALS_STRING);
        Mockito.when(berichtFactory.maakAg11Bericht(Matchers.any(Persoonslijst.class))).thenReturn(AG11_BERICHT);
        Mockito.when(berichtFactory.maakSf01Bericht(Matchers.any(Persoonslijst.class))).thenReturn(SF01_BERICHT);
        Mockito.when(berichtFactory.maakSv01Bericht(Matchers.any(Persoonslijst.class))).thenReturn(SV01_BERICHT);
        Mockito.when(berichtFactory.maakSv11Bericht()).thenReturn(SV11_BERICHT);
    }

    @Test
    public void testAg11Bericht() throws StapException {
        Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs(A_NUMMER, "987654321");
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(
                null,
                maakAutorisatiebundel(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE),
                Collections.singletonList(new BijgehoudenPersoon.Builder(persoonslijst, null).build()));
        String resultaat = subject.maakPersoonBericht(bericht);
        Assert.assertEquals("Ag11 bericht verwacht", AG_11_BERICHT_ALS_STRING, resultaat);
    }

    @Test
    public void testSf01Bericht() throws StapException {
        Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs(A_NUMMER, "987654321");
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(
                null,
                maakAutorisatiebundel(SoortSelectie.STANDAARD_SELECTIE),
                Collections.singletonList(new BijgehoudenPersoon.Builder(persoonslijst, null).build())
        );
        Blokkering blokkering = new Blokkering(A_NUMMER, Timestamp.from(ZonedDateTime.now().toInstant()));
        blokkering.setRegistratieGemeente("0599");
        Mockito.when(blokkeringRepository.getBlokkeringOpANummer(Matchers.anyString())).thenReturn(blokkering);
        String resultaat = subject.maakPersoonBericht(bericht);
        Assert.assertEquals("Sf01 bericht verwacht", SF_01_BERICHT_ALS_STRING, resultaat);
    }

    @Test
    public void testSv01Bericht() throws StapException {
        Persoonslijst persoonslijst = TestBuilders.maakPersoonMetIdentificatieNrs(A_NUMMER, "987654321");
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(
                null,
                maakAutorisatiebundel(SoortSelectie.STANDAARD_SELECTIE),
                Collections.singletonList(new BijgehoudenPersoon.Builder(persoonslijst, null).build())
        );
        String resultaat = subject.maakPersoonBericht(bericht);
        Assert.assertEquals("Sv01 bericht verwacht", SV_01_BERICHT_ALS_STRING, resultaat);
    }

    @Test
    public void testSv11Bericht() throws StapException {
        VerwerkPersoonBericht bericht = new VerwerkPersoonBericht(
                null,
                maakAutorisatiebundel(SoortSelectie.STANDAARD_SELECTIE),
                Collections.emptyList()
        );
        String resultaat = subject.maakPersoonBericht(bericht);
        Assert.assertEquals("Sv11 bericht verwacht", SV_11_BERICHT_ALS_STRING, resultaat);
    }

    private Autorisatiebundel maakAutorisatiebundel(final SoortSelectie soortSelectie) {
        final PartijRol partijRol = new PartijRol(new Partij("Testpartij", "123401"), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.SELECTIE;
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setId(1);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setActueelEnGeldig(true);
        dienstbundel.setDatumIngang(DatumUtil.gisteren());
        dienstbundel.setId(1);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        leveringsautorisatie.setDatumIngang(DatumUtil.gisteren());
        leveringsautorisatie.setActueelEnGeldig(true);
        final Set<Dienst> diensten = new HashSet<>(1);
        final Dienst dienst = new Dienst(dienstbundel, soortDienst);
        dienst.setId(1);
        dienst.setLeverwijzeSelectie(LeverwijzeSelectie.BERICHT.getId());
        dienst.setSoortSelectie(soortSelectie.getId());
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setActueelEnGeldig(true);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        diensten.add(dienst);
        final Dienst dienstSpontaan = new Dienst(dienstbundel, SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setId(2);
        diensten.add(dienstSpontaan);
        dienstbundel.setDienstSet(diensten);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
