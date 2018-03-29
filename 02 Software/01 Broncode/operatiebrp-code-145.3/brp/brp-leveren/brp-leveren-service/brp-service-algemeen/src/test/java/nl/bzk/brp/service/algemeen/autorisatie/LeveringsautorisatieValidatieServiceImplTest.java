/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

import static java.util.Collections.singletonList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.request.OIN;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link LeveringsautorisatieValidatieServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class LeveringsautorisatieValidatieServiceImplTest {

    private static final int DIENST_ID = 1234;
    private static final int LEVERINGAUTORISATIE_ID = 1;

    private static final String ZENDENDE_PARTIJ_OIN = "123";
    private static final String ONDERTEKENAAR_OIN = "124";
    private static final String TRANSPORTEUR_OIN = "125";
    private static final String GEAUTORISEERDE_TOEGANGLEV_AUT_OIN = "125";
    private static final String GEAUTORISEERDE_PARTIJ_OIN = "321";
    private static final String ONGELDIGE_PARTIJ_OIN = "654";

    private static final SoortDienst SOORT_DIENST_ATTENDERING = SoortDienst.ATTENDERING;
    private static final SoortDienst SOORT_DIENST_PLAATS_AFNIND = SoortDienst.PLAATSING_AFNEMERINDICATIE;

    private Partij zendendePartij;
    private Partij geautoriseerdePartij;
    private Partij ongeldigePartij;

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;
    @Mock
    private PartijService partijService;

    @InjectMocks
    private LeveringsautorisatieValidatieServiceImpl service;

    @Before
    public void before() {
        BrpNu.set();
    }

    private AutorisatieParams.Builder autorisatieParams;

    private static ToegangLeveringsAutorisatie getToegangLeveringsAutorisatie() {
        //@formatter:off
        final Partij partij = TestPartijBuilder.maakBuilder()
                .metId(1)
                .metCode("000001")
                .metDatumAanvang(DatumUtil.gisteren())
                .metDatumEinde(DatumUtil.morgen())
                .metOin(GEAUTORISEERDE_TOEGANGLEV_AUT_OIN)
                .build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setDatumIngang(DatumUtil.gisteren());
        partijRol.setDatumEinde(DatumUtil.morgen());
        partijRol.setActueelEnGeldig(true);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, true);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setDatumIngang(DatumUtil.gisteren());
        dienstbundel.setDatumEinde(DatumUtil.morgen());
        dienstbundel.setActueelEnGeldig(true);
        final Dienst dienst = new Dienst(dienstbundel, SOORT_DIENST_ATTENDERING);
        dienst.setId(DIENST_ID);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        dienst.setActueelEnGeldig(true);
        dienstbundel.addDienstSet(dienst);
        leveringsautorisatie.getDienstbundelSet().add(dienstbundel);
        final Integer vandaag = DatumUtil.gisteren();
        leveringsautorisatie.setDatumIngang(vandaag);
        leveringsautorisatie.setActueelEnGeldig(true);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setDatumIngang(vandaag);
        tla.setActueelEnGeldig(true);
        tla.setId(LEVERINGAUTORISATIE_ID);
        leveringsautorisatie.setId(LEVERINGAUTORISATIE_ID);
        return tla;
    }

    private static ToegangLeveringsAutorisatie getToegangLeveringsAutorisatieGba() {
        //@formatter:off
        final Partij partij = TestPartijBuilder.maakBuilder()
                .metId(1)
                .metCode("000001")
                .metDatumAanvang(DatumUtil.gisteren())
                .metDatumEinde(DatumUtil.morgen())
                .build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setDatumIngang(DatumUtil.gisteren());
        partijRol.setDatumEinde(DatumUtil.morgen());
        partijRol.setActueelEnGeldig(true);
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, true);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setDatumIngang(DatumUtil.gisteren());
        dienstbundel.setDatumEinde(DatumUtil.morgen());
        dienstbundel.setActueelEnGeldig(true);
        final Dienst dienst = new Dienst(dienstbundel, SOORT_DIENST_PLAATS_AFNIND);
        dienst.setId(DIENST_ID);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(DatumUtil.morgen());
        dienst.setActueelEnGeldig(true);
        dienstbundel.addDienstSet(dienst);
        leveringsautorisatie.getDienstbundelSet().add(dienstbundel);
        final Integer vandaag = DatumUtil.gisteren();
        leveringsautorisatie.setDatumIngang(vandaag);
        leveringsautorisatie.setActueelEnGeldig(true);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setDatumIngang(vandaag);
        tla.setActueelEnGeldig(true);
        tla.setId(LEVERINGAUTORISATIE_ID);
        leveringsautorisatie.setId(LEVERINGAUTORISATIE_ID);
        return tla;
    }

    @Before
    public void voorTest() {

        zendendePartij = TestPartijBuilder.maakBuilder()
                .metId(1)
                .metCode("000001")
                .metNaam("ZendendePartij")
                .metDatumAanvang(DatumUtil.gisteren())
                .metDatumEinde(DatumUtil.morgen())
                .metDatumOvergangNaarBrp(DatumUtil.vandaag())
                .metOin(ZENDENDE_PARTIJ_OIN).build();

        geautoriseerdePartij = TestPartijBuilder.maakBuilder()
                .metId(2)
                .metCode("000004")
                .metNaam("GeautoriseerdeGeldigePartij")
                .metDatumAanvang(DatumUtil.gisteren())
                .metDatumEinde(DatumUtil.morgen())
                .metOin(GEAUTORISEERDE_PARTIJ_OIN).build();

        ongeldigePartij = TestPartijBuilder.maakBuilder()
                .metId(3)
                .metCode("000005")
                .metNaam("OngeldigePartij")
                .metDatumAanvang(DatumUtil.morgen())
                .metDatumEinde(DatumUtil.morgen())
                .metOin(ONGELDIGE_PARTIJ_OIN).build();

        when(partijService.vindPartijOpCode(zendendePartij.getCode())).thenReturn(zendendePartij);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID))
                .thenReturn(getToegangLeveringsAutorisatie().getLeveringsautorisatie());

        autorisatieParams = AutorisatieParams.maakBuilder()
                .metZendendePartijCode(zendendePartij.getCode())
                .metLeveringautorisatieId(LEVERINGAUTORISATIE_ID)
                .metSoortDienst(SOORT_DIENST_ATTENDERING);
    }

    @Test
    public void testZendendePartijNietBestaand() {
        when(partijService.vindPartijOpCode(zendendePartij.getCode())).thenReturn(null);

        final AutorisatieParams params = autorisatieParams.metZendendePartijCode(ongeldigePartij.getCode()).build();
        controleerAutorisatie(params, Regel.R2120);
    }

    @Test
    public void testZendendePartijNietGeldig() {
        final AutorisatieParams params = autorisatieParams.metZendendePartijCode(ongeldigePartij.getCode()).build();
        when(partijService.vindPartijOpCode(ongeldigePartij.getCode())).thenReturn(ongeldigePartij);

        controleerAutorisatie(params, Regel.R2242);
    }

    @Test
    public void testOinVanOndertekenaarMatchedNietOpPartij() {
        when(partijService.vindPartijOpOin(ONDERTEKENAAR_OIN)).thenReturn(null);
        final AutorisatieParams params = autorisatieParams.metOIN(new OIN(ONDERTEKENAAR_OIN, null)).build();
        controleerAutorisatie(params, Regel.R2120);
    }

    @Test
    public void testPartijOndertekenaarIsOngeldig() {
        when(partijService.vindPartijOpOin(ONGELDIGE_PARTIJ_OIN)).thenReturn(ongeldigePartij);
        final AutorisatieParams params = autorisatieParams.metOIN(new OIN(ONGELDIGE_PARTIJ_OIN, null)).build();
        controleerAutorisatie(params, Regel.R2243);
    }

    @Test
    public void testGbaPartijZonderOin() {
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID))
                .thenReturn(getToegangLeveringsAutorisatieGba().getLeveringsautorisatie());
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(Collections.singletonList(getToegangLeveringsAutorisatieGba()));
        zendendePartij.setOin(null);
        zendendePartij.setDatumOvergangNaarBrp(null);
        final AutorisatieParams params = autorisatieParams
                .metZendendePartijCode(zendendePartij.getCode())
                .metLeveringautorisatieId(LEVERINGAUTORISATIE_ID)
                .metSoortDienst(SOORT_DIENST_PLAATS_AFNIND).build();
        controleerAutorisatie(params, null);
    }

    @Test
    public void testOinVanTransporteurMatchedNietOpPartij() {
        when(partijService.vindPartijOpOin(TRANSPORTEUR_OIN)).thenReturn(null);
        final AutorisatieParams params = autorisatieParams.metOIN(new OIN(null, TRANSPORTEUR_OIN)).build();
        controleerAutorisatie(params, Regel.R2120);
    }

    @Test
    public void testPartijTransporteurIsOngeldig() {
        when(partijService.vindPartijOpOin(ONGELDIGE_PARTIJ_OIN)).thenReturn(ongeldigePartij);
        final AutorisatieParams params = autorisatieParams.metOIN(new OIN(null, ONGELDIGE_PARTIJ_OIN)).build();
        controleerAutorisatie(params, Regel.R2244);
    }

    @Test
    public void testLeveringsautorisatieBestaatNiet() {
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(getToegangLeveringsAutorisatie()));

        final int nietBestaandeLeveringsautorisatieId = 21212121;
        final AutorisatieParams params = autorisatieParams.metLeveringautorisatieId(nietBestaandeLeveringsautorisatieId).build();
        controleerAutorisatie(params, Regel.R2053);
    }

    @Test
    public void testLeveringsautorisatieMatchedNietOpLeveringsautorisatieId() {
        final ToegangLeveringsAutorisatie tla1 = getToegangLeveringsAutorisatie();
        tla1.getLeveringsautorisatie().setId(LEVERINGAUTORISATIE_ID + 1);
        final ToegangLeveringsAutorisatie tla2 = getToegangLeveringsAutorisatie();
        tla2.getLeveringsautorisatie().setId(LEVERINGAUTORISATIE_ID + 2);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(Lists.newArrayList(
                        tla1,
                        tla2
                ));
        final AutorisatieParams params = autorisatieParams.build();
        controleerAutorisatie(params, Regel.R2053);
    }

    @Test
    public void testLeveringsautorisatieNietGeldig() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.getLeveringsautorisatie().setDatumEinde(DatumUtil.vandaag());
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(tla.getLeveringsautorisatie());

        final AutorisatieParams params = autorisatieParams.build();
        controleerAutorisatie(params, Regel.R1261);
    }

    @Test
    public void testLeveringsautorisatieGeblokkeerd() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.getLeveringsautorisatie().setIndicatieGeblokkeerd(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(tla.getLeveringsautorisatie());

        final AutorisatieParams params = autorisatieParams.build();
        controleerAutorisatie(params, Regel.R1263);
    }

    @Test
    public void testDienstBestaatNiet() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(la, SOORT_DIENST_ATTENDERING);
        dienst.setSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);

        controleerAutorisatie(autorisatieParams.build(), Regel.R2130);
    }

    @Test
    public void testDienstIdBestaat() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        when(this.leveringsautorisatieService.bestaatDienst(DIENST_ID)).thenReturn(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));
        final AutorisatieParams params = autorisatieParams.metDienstId(DIENST_ID).build();
        controleerAutorisatie(params, null);
    }

    @Test
    public void testDienstIdBestaatGlobaalNiet() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        final int nietBestaandeDienst = 999;
        when(this.leveringsautorisatieService.bestaatDienst(nietBestaandeDienst)).thenReturn(false);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);

        final AutorisatieParams params = autorisatieParams.metDienstId(nietBestaandeDienst).build();
        controleerAutorisatie(params, Regel.R2055);
    }

    @Test
    public void testDienstIdBestaatNietInLeveringsautorisatie() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        final int andereDienst = 999;
        when(this.leveringsautorisatieService.bestaatDienst(andereDienst)).thenReturn(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);

        final AutorisatieParams params = autorisatieParams.metDienstId(andereDienst).build();
        controleerAutorisatie(params, Regel.R2130);
    }

    @Test
    public void testDienstIdMetVerkeerdeSoortDienst() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        when(this.leveringsautorisatieService.bestaatDienst(DIENST_ID)).thenReturn(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);

        final AutorisatieParams params = autorisatieParams.metDienstId(DIENST_ID).metSoortDienst(SoortDienst.MUTATIELEVERING_STAMGEGEVEN).build();
        controleerAutorisatie(params, Regel.R2054);
    }

    @Test
    public void testDienstOngeldig() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(la, SOORT_DIENST_ATTENDERING);
        dienst.setDatumEinde(DatumUtil.vandaag());
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        controleerAutorisatie(autorisatieParams.build(), Regel.R1262);
    }

    @Test
    public void testDienstGeblokkeerd() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(la, SOORT_DIENST_ATTENDERING);
        dienst.setIndicatieGeblokkeerd(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        controleerAutorisatie(autorisatieParams.build(), Regel.R1264);
    }

    @Test
    public void testDienstbundelOngeldig() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(la, SOORT_DIENST_ATTENDERING);
        dienst.getDienstbundel().setDatumEinde(DatumUtil.vandaag());
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        controleerAutorisatie(autorisatieParams.build(), Regel.R2239);
    }

    @Test
    public void testDienstbundelGeblokkeerd() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();
        final Dienst dienst = AutAutUtil.zoekDienst(la, SOORT_DIENST_ATTENDERING);
        dienst.getDienstbundel().setIndicatieGeblokkeerd(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        controleerAutorisatie(autorisatieParams.build(), Regel.R2056);
    }

    @Test
    public void testGeenToegangLeveringautorisatiesVoorPartij() {
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij)).thenReturn(
                Collections.emptyList());
        controleerAutorisatie(autorisatieParams.build(), Regel.R2120);
    }

    @Test
    public void testToegangleveringautorisatieOngeldig() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setDatumEinde(DatumUtil.vandaag());
        tla.setActueelEnGeldig(false);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij)).thenReturn(Collections.singletonList(tla));
        controleerAutorisatie(autorisatieParams.build(), Regel.R1258);
    }

    @Test
    public void testToegangleveringsautorisatieGeblokkeerd() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setIndicatieGeblokkeerd(true);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij)).thenReturn(Collections.singletonList(tla));
        controleerAutorisatie(autorisatieParams.build(), Regel.R2052);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsZelfOndertekenaarEnTransporteur() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(zendendePartij);
        tla.setTransporteur(zendendePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));
        controleerAutorisatie(autorisatieParams.build(), null);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsNietOndertekenaarEnNietTransporteur() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(geautoriseerdePartij);
        tla.setTransporteur(geautoriseerdePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, null);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsZelfOndertekenaarEnNietTransporteur() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(zendendePartij);
        tla.setTransporteur(geautoriseerdePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(partijService.vindPartijOpOin(ZENDENDE_PARTIJ_OIN)).thenReturn(zendendePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(ZENDENDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, null);
    }

    @Test
    public void isGeautoriseerdAlsZendendePartijIsZelfTransporteurEnNietOndertekenaar() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(geautoriseerdePartij);
        tla.setTransporteur(zendendePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(partijService.vindPartijOpOin(ZENDENDE_PARTIJ_OIN)).thenReturn(zendendePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, ZENDENDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, null);
    }

    //de ondertekenaar van toegangleveringsautorisatie is leeg..
    @Test
    public void geeftR2121AlsOndertekenaarNietGeautoriseerd() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(null);
        tla.setTransporteur(zendendePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, null))
                .build();
        controleerAutorisatie(params, Regel.R2121);
    }

    @Test
    public void geeftR2121AlsOndertekenaarNietGeautoriseerd_zendendePartijOinIsNull() {

        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setTransporteur(geautoriseerdePartij);
        zendendePartij.setOin(null);
        when(partijService.vindPartijOpCode(zendendePartij.getCode())).thenReturn(zendendePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN)).metZendendePartijCode(zendendePartij.getCode())
                .build();
        controleerAutorisatie(params, Regel.R2121);
    }

    @Test
    public void geeftR2122AlsTransporteurNietGeautoriseerd() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
       tla.setOndertekenaar(geautoriseerdePartij);
        tla.setTransporteur(null);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, Regel.R2122);
    }

    @Test
    public void geeftR2122AlsTransporteurNietGeautoriseerd_zendendePartijOinIsNull() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(geautoriseerdePartij);
        tla.setTransporteur(null);
        zendendePartij.setOin(null);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, Regel.R2122);
    }

    @Test
    public void geeftR1257AlsOndertekenaarEnTransporteurGeautoriseerdInVerschillendeAutorisaties() {
        final ToegangLeveringsAutorisatie tla1 = getToegangLeveringsAutorisatie();
        final ToegangLeveringsAutorisatie tla2 = getToegangLeveringsAutorisatie();
        tla1.setOndertekenaar(geautoriseerdePartij);
        tla2.setTransporteur(geautoriseerdePartij);
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(geautoriseerdePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(Arrays.asList(tla1, tla2));

        final AutorisatieParams params = autorisatieParams
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();
        controleerAutorisatie(params, Regel.R1257);
    }

    @Test
    public void partijRolOngeldig() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        when(this.leveringsautorisatieService.bestaatDienst(DIENST_ID)).thenReturn(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        tla.getGeautoriseerde().setDatumEinde(DatumUtil.vandaag());
        tla.getGeautoriseerde().setActueelEnGeldig(false);
        final AutorisatieParams params = autorisatieParams.build();
        controleerAutorisatie(params, Regel.R2245);
    }

    @Test
    public void partijRolVerkeerd() {
        final Leveringsautorisatie la = getToegangLeveringsAutorisatie().getLeveringsautorisatie();

        when(this.leveringsautorisatieService.bestaatDienst(DIENST_ID)).thenReturn(true);
        when(leveringsautorisatieService.geefLeveringautorisatie(LEVERINGAUTORISATIE_ID)).thenReturn(la);
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));

        final AutorisatieParams params = autorisatieParams.metRol(Rol.BIJHOUDINGSORGAAN_MINISTER.getNaam()).build();
        controleerAutorisatie(params, Regel.R2120);
    }

    @Test
    public void testR2524BijStelselBrpOvergangMorgen() {
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(zendendePartij);
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        when(leveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenReturn(tla.getLeveringsautorisatie());
        zendendePartij.setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(-1));
        final AutorisatieParams params = autorisatieParams
                .metZendendePartijCode(zendendePartij.getCode())
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();

        controleerAutorisatie(params, Regel.R2524);
    }

    @Test
    public void testR2524BijStelselBrpOvergangLeeg() {
        when(partijService.vindPartijOpOin(GEAUTORISEERDE_PARTIJ_OIN)).thenReturn(zendendePartij);
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        when(leveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenReturn(tla.getLeveringsautorisatie());
        zendendePartij.setDatumOvergangNaarBrp(null);
        final AutorisatieParams params = autorisatieParams
                .metZendendePartijCode(zendendePartij.getCode())
                .metOIN(new OIN(GEAUTORISEERDE_PARTIJ_OIN, GEAUTORISEERDE_PARTIJ_OIN))
                .build();

        controleerAutorisatie(params, Regel.R2524);
    }

    @Test
    public void controleerVerzoekViaKoppelvlakDoorGbaPartij() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        when(leveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenReturn(tla.getLeveringsautorisatie());
        final AutorisatieParams params = autorisatieParams
                .metZendendePartijCode(zendendePartij.getCode())
                .metVerzoekViaKoppelvlak(true)
                .build();

        controleerAutorisatie(params, Regel.R2585);
    }

    @Test
    public void controleerVerzoekViaKoppelvlakDoorBrpPartij() {
        final ToegangLeveringsAutorisatie tla = getToegangLeveringsAutorisatie();
        tla.setOndertekenaar(zendendePartij);
        tla.setTransporteur(zendendePartij);
        when(leveringsautorisatieService.geefToegangLeveringAutorisaties(zendendePartij))
                .thenReturn(singletonList(tla));
        tla.getLeveringsautorisatie().setStelsel(Stelsel.BRP);
        when(leveringsautorisatieService.geefLeveringautorisatie(anyInt())).thenReturn(tla.getLeveringsautorisatie());
        final AutorisatieParams params = autorisatieParams
                .metZendendePartijCode(zendendePartij.getCode())
                .metVerzoekViaKoppelvlak(true)
                .build();

        controleerAutorisatie(params, null);
    }

    private void controleerAutorisatie(AutorisatieParams autorisatieParams, Regel meldingFout) {
        Autorisatiebundel autorisatiebundel = null;
        AutorisatieException autorisatieException = null;
        try {
            autorisatiebundel = service.controleerAutorisatie(autorisatieParams);
        } catch (AutorisatieException e) {
            e.printStackTrace();
            autorisatieException = e;
        }

        if (meldingFout != null) {
            Assert.assertNull(autorisatiebundel);
            Assert.assertNotNull(autorisatieException);
            Assert.assertEquals(meldingFout, autorisatieException.getMelding().getRegel());
        } else {
            Assert.assertNotNull(autorisatiebundel);
        }
    }
}
