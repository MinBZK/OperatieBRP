/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * AutorisatiebundelCacheUtilTest.
 */
public class AutorisatiebundelCacheUtilTest {

    @Test
    public void testMutatieDienstGeldig() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertEquals(1, autorisatiebundels.size());
    }


    @Test
    public void testGeenMutatieDienstGeldig() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.GEEF_DETAILS_PERSOON, true, true, true,
                true, true);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void testMutatieDienstOngeldigeDienst() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, false, true,
                true, true);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void testMutatieDienstOngeldigeTla() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.GEEF_DETAILS_PERSOON, false, true, true,
                true, true);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }


    @Test
    public void testMutatieDienstOngeldigePartij() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                false, true);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void testMutatieDienstOngeldigePartijRol() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, false);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void geenAutorisatieBijStelselBrpMaarDatumOvergangLeeg() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        tla.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(null);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void geenAutorisatieBijStelselBrpMaarDatumOvergangMorgen() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        tla.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(-1));
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void geenAutorisatieBijStelselGbaMaarDatumOvergangGisteren() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        tla.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        tla.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1));
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        Assert.assertTrue(autorisatiebundels.isEmpty());
    }

    @Test
    public void autorisatieBijStelselGbaEnDatumOvergangLeeg() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        tla.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        tla.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(null);
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        assertThat(autorisatiebundels.size(), is(1));
    }

    @Test
    public void autorisatieBijStelselGbaEnDatumOvergangMorgen() {
        final ToegangLeveringsAutorisatie tla = maakToegangLeveringautorisatie(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING, true, true, true,
                true, true);
        tla.getLeveringsautorisatie().setStelsel(Stelsel.GBA);
        tla.getGeautoriseerde().getPartij().setDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(-1));
        final List<Autorisatiebundel>
                autorisatiebundels =
                AutorisatiebundelCacheUtil
                        .geefAutorisatiebundels(Lists.newArrayList(tla), DatumUtil.vandaag(), AutorisatiebundelCacheUtil.LEVEREN_MUTATIES_DIENST_SOORTEN);
        assertThat(autorisatiebundels.size(), is(1));
    }

    private ToegangLeveringsAutorisatie maakToegangLeveringautorisatie(final SoortDienst soortDienst, final boolean leveringautorisatieGeldig,
                                                                       final boolean dienstGeldig, final boolean dienstbundelGeldig, final boolean partijGeldig,
                                                                       final boolean partijRolGeldig) {
        final Integer datumEindeToegangLeveringautorisatie = leveringautorisatieGeldig ? DatumUtil.morgen() : DatumUtil.gisteren();
        final Integer datumEindeDienst = dienstGeldig ? DatumUtil.morgen() : DatumUtil.gisteren();
        final Integer datumEindeDienstbundel = dienstbundelGeldig ? DatumUtil.morgen() : DatumUtil.gisteren();
        final Integer datumEindePartij = partijGeldig ? DatumUtil.morgen() : DatumUtil.gisteren();
        final Integer datumEindePartijRol = partijRolGeldig ? DatumUtil.morgen() : DatumUtil.gisteren();

        //@formatter:off
        final Partij partij = TestPartijBuilder.maakBuilder()
            .metId(1)
            .metCode("000001")
            .metDatumAanvang(DatumUtil.gisteren())
            .metDatumEinde(datumEindePartij)
            .metDatumOvergangNaarBrp(DatumUtil.gisteren())
            .build();
        //@formatter:on

        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        partijRol.setDatumIngang(DatumUtil.gisteren());
        partijRol.setDatumEinde(datumEindePartijRol);

        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        leveringsautorisatie.setDatumIngang(DatumUtil.gisteren());
        leveringsautorisatie.setDatumEinde(datumEindeToegangLeveringautorisatie);
        leveringsautorisatie.setStelsel(Stelsel.BRP);

        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setDatumIngang(DatumUtil.gisteren());
        tla.setDatumEinde(datumEindeToegangLeveringautorisatie);

        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        dienst.setId(1);
        dienst.setDatumIngang(DatumUtil.gisteren());
        dienst.setDatumEinde(datumEindeDienst);
        dienst.getDienstbundel().setDatumIngang(DatumUtil.gisteren());
        dienst.getDienstbundel().setDatumEinde(datumEindeDienstbundel);

        return tla;
    }
}
