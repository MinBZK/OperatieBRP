/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

public class PopulatieBepalingFilterImplTest {

    private static final long PERSOON_ID = 123L;

    private final PersoonPopulatieFilterImpl populatieBepalingFilterImpl = new PersoonPopulatieFilterImpl();
    private Persoonslijst testPersoon = TestBuilders.PERSOON_MET_HANDELINGEN;

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBetreedPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BETREEDT;
        final boolean resultaat = populatieBepalingFilterImpl.magLeveren(null, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBinnenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingVerlaatPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.VERLAAT;
        final boolean resultaat = populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorDoelbindingBuitenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertFalse(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBetreedPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BETREEDT;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBinnenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieVerlaatPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.VERLAAT;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAfnemerindicatieBuitenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBetreedPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BETREEDT;
        populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBinnenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BINNEN;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertTrue(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorAttenderingVerlaatPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.VERLAAT;
        populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);
    }

    @Test
    public final void testFilterNietTeLeverenPersonenVoorAttenderingBuitenPopulatie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.ATTENDERING);

        final Populatie populatie = Populatie.BUITEN;
        final boolean resultaat = populatieBepalingFilterImpl
                .magLeveren(testPersoon, populatie, leveringAutorisatie);

        Assert.assertFalse(resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public final void testFilterNietTeLeverenPersonenVoorOnbekendeCatalogusOptie() throws Exception {
        final Autorisatiebundel leveringAutorisatie = maakAutorisatiebudel(SoortDienst.SYNCHRONISATIE_PERSOON);
        final Populatie populatie = Populatie.BINNEN;
        populatieBepalingFilterImpl.magLeveren(testPersoon, populatie, leveringAutorisatie);
    }

    private Autorisatiebundel maakAutorisatiebudel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(soortDienst);
        final Partij partij = TestPartijBuilder.maakBuilder().metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, Rol.AFNEMER);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        final Dienst dienst = AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst);
        return new Autorisatiebundel(tla, dienst);
    }
}
