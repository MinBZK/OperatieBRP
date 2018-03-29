/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonMigratieMapperTest extends AbstractMapperTestBasis {

    private final PersoonMigratieMapper mapper = new PersoonMigratieMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpMigratieInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonMigratieGroepToe(
                            b,
                            "I",
                            "regel1",
                            "regel2",
                            "regel3",
                            "regel4",
                            "regel5",
                            "regel6",
                            "0542",
                            "R",
                            "E")));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Character.valueOf('I'), brpInhoud.get(0).getInhoud().getAangeverMigratieCode().getWaarde());
        Assert.assertEquals("regel1", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel1().getWaarde());
        Assert.assertEquals("regel2", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel2().getWaarde());
        Assert.assertEquals("regel3", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel3().getWaarde());
        Assert.assertEquals("regel4", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel4().getWaarde());
        Assert.assertEquals("regel5", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel5().getWaarde());
        Assert.assertEquals("regel6", brpInhoud.get(0).getInhoud().getBuitenlandsAdresRegel6().getWaarde());
        Assert.assertEquals("0542", brpInhoud.get(0).getInhoud().getLandOfGebiedCode().getWaarde());
        Assert.assertEquals(Character.valueOf('R'), brpInhoud.get(0).getInhoud().getRedenWijzigingMigratieCode().getWaarde());
        Assert.assertEquals("E", brpInhoud.get(0).getInhoud().getSoortMigratieCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpMigratieInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpMigratieInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonMigratieGroepToe(b, null, null, null, null, null, null, null, null, null, null)));

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
}
