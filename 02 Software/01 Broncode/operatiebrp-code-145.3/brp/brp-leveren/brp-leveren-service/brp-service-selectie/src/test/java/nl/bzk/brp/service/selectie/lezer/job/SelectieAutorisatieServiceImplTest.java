/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test voor {@link SelectieAutorisatieServiceImpl}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SelectieAutorisatieServiceImplTest {

    private final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = maakToegangLeveringsAutorisatie(DatumUtil.gisteren());
    private final Dienst dienst = maakDienst(toegangLeveringsAutorisatie);

    @Mock
    private LeveringsautorisatieService leveringsautorisatieService;

    @InjectMocks
    private SelectieAutorisatieServiceImpl service;


    @Before
    public void before() {
        Mockito.when(leveringsautorisatieService.bestaatDienst(Mockito.anyInt())).thenReturn(true);
        BrpNu.set();
    }

    private static Dienst maakDienst(final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie) {
        final Dienstbundel dienstbundel = new Dienstbundel(toegangLeveringsAutorisatie.getLeveringsautorisatie());
        dienstbundel.setActueelEnGeldig(true);
        dienstbundel.setDatumIngang(20100101);
        final Dienst dienst = new Dienst(dienstbundel, SoortDienst.SELECTIE);
        dienst.setId(1);
        dienst.setActueelEnGeldig(true);
        dienst.setDatumIngang(20100101);
        return dienst;
    }

    private static ToegangLeveringsAutorisatie maakToegangLeveringsAutorisatie(final int datumIngang) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setActueelEnGeldig(true);
        leveringsautorisatie.setDatumIngang(datumIngang);
        leveringsautorisatie.setDatumEinde(null);
        final Partij partij = new Partij("test", "000123");
        partij.setActueelEnGeldig(true);
        partij.setDatumIngang(datumIngang);
        partij.setDatumEinde(null);
        partij.setDatumOvergangNaarBrp(datumIngang);
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setActueelEnGeldig(true);
        partijRol.setDatumIngang(datumIngang);
        partijRol.setDatumEinde(null);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegangLeveringsAutorisatie.setActueelEnGeldig(true);
        toegangLeveringsAutorisatie.setDatumIngang(datumIngang);
        toegangLeveringsAutorisatie.setDatumEinde(null);
        return toegangLeveringsAutorisatie;
    }

    @Test
    public void dienstNietInCache(){
        Mockito.when(leveringsautorisatieService.bestaatDienst(Mockito.anyInt())).thenReturn(false);
        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieGeldig() throws Exception {
        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertTrue(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstGeblokkeerd() throws Exception {
        dienst.setIndicatieGeblokkeerd(true);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstbundelGeblokkeerd() throws Exception {
        dienst.getDienstbundel().setIndicatieGeblokkeerd(true);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigToegangLeveringsautorisatieGeblokkeerd() throws Exception {
        toegangLeveringsAutorisatie.setIndicatieGeblokkeerd(true);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigLeveringsautorisatieGeblokkeerd() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setIndicatieGeblokkeerd(true);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstDatumAanvangInToekomst() throws Exception {
        dienst.setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstDatumEindeInVerleden() throws Exception {
        dienst.setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstbundelDatumAanvangInToekomst() throws Exception {
        dienst.getDienstbundel().setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigDienstbundelDatumEindeInVerleden() throws Exception {
        dienst.getDienstbundel().setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigToegangLeveringsautorisatieDatumAanvangInToekomst() throws Exception {
        toegangLeveringsAutorisatie.setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigToegangLeveringsautorisatieDatumEindeInVerleden() throws Exception {
        toegangLeveringsAutorisatie.setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigLeveringsautorisatieDatumAanvangInToekomst() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigLeveringsautorisatieDatumEindeInVerleden() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigPartijRolDatumAanvangInToekomst() throws Exception {
        toegangLeveringsAutorisatie.getGeautoriseerde().setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigPartijRolDatumEindeInVerleden() throws Exception {
        toegangLeveringsAutorisatie.getGeautoriseerde().setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigGeautoriseerdePartijDatumAanvangInToekomst() throws Exception {
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumIngang(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigGeautoriseerdePartijDatumEindeInVerleden() throws Exception {
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumEinde(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigStelselBrpDatumOvergangMorgen() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setStelsel(Stelsel.BRP);
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieOngeldigStelselBrpDatumOvergangLeeg() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setStelsel(Stelsel.BRP);
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(null);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

    @Test
    public void autorisatieGeldigStelselGbaDatumOvergangLeeg() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(null);

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertTrue(isGeldig);
    }

    @Test
    public void autorisatieGeldigStelselGbaDatumOvergangGisteren() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.morgen());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertTrue(isGeldig);
    }

    @Test
    public void autorisatieOngeldigStelselGbaDatumOvergangGisteren() throws Exception {
        toegangLeveringsAutorisatie.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        toegangLeveringsAutorisatie.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.gisteren());

        final boolean isGeldig = service.isAutorisatieGeldig(toegangLeveringsAutorisatie, dienst);

        assertFalse(isGeldig);
    }

}
