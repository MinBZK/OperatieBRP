/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class MigratieMapperTest {

    private static final String REGEL_1 = "regel1";
    private static final String REGEL_2 = "regel2";
    private static final String REGEL_3 = "regel3";
    private static final String REGEL_4 = "regel4";
    private static final String REGEL_5 = "regel5";
    private static final String REGEL_6 = "regel6";
    private final MigratieMapper mapper = new MigratieMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(maak(builder).build(), null);
        final BrpStapel<BrpMigratieInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Character.valueOf('I'), brpInhoud.get(0).getInhoud().getAangeverMigratieCode().getWaarde());
        Assert.assertEquals(REGEL_1, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals(REGEL_2, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals(REGEL_3, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals(REGEL_4, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals(REGEL_5, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals(REGEL_6, brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals(Short.valueOf((short) 542), brpInhoud.get(0).getInhoud().getLandOfGebiedCode().getWaarde());
        Assert.assertEquals(Character.valueOf('R'), brpInhoud.get(0).getInhoud().getRedenWijzigingMigratieCode().getWaarde());
        Assert.assertEquals("E", brpInhoud.get(0).getInhoud().getSoortMigratieCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpMigratieInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        builder.nieuwMigratieRecord(MapperTestUtil.maakActieModel(20131212000000L, 20131212, null)).eindeRecord();

        final PersoonHisVolledigView persoonHisVolledig = new PersoonHisVolledigView(builder.build(), null);
        final BrpStapel<BrpMigratieInhoud> brpInhoud =
                mapper.map(persoonHisVolledig, new OnderzoekMapper(persoonHisVolledig), new TestActieHisVolledigLocator());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getAangeverMigratieCode());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel1());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel2());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel3());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel4());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel5());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel6());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getLandOfGebiedCode());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getRedenWijzigingMigratieCode());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getSoortMigratieCode());
    }

    public static PersoonHisVolledigImplBuilder maak(final PersoonHisVolledigImplBuilder builder) {
        // @formatter:off
        return builder.nieuwMigratieRecord(MapperTestUtil.maakActieModel(20131212000000L, 20131212, null))
                .aangeverMigratie("I")
                .buitenlandsAdresRegel1Migratie(REGEL_1)
                .buitenlandsAdresRegel2Migratie(REGEL_2)
                .buitenlandsAdresRegel3Migratie(REGEL_3)
                .buitenlandsAdresRegel4Migratie(REGEL_4)
                .buitenlandsAdresRegel5Migratie(REGEL_5)
                .buitenlandsAdresRegel6Migratie(REGEL_6)
                .landGebiedMigratie((short) 542)
                .redenWijzigingMigratie("R")
                .soortMigratie(SoortMigratie.EMIGRATIE)
                .eindeRecord();
        // @formatter:on
    }
}
