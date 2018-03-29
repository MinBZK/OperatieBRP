/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonNaamgebruikMapperTest extends AbstractMapperTestBasis {

    private final PersoonNaamgebruikMapper mapper = new PersoonNaamgebruikMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpNaamgebruikInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonNaamgebruikGroepToe(b, "B", "E", "Baronnetje", false, "J", "-", "Piet", "van der")));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals("Baronnetje", brpInhoud.get(0).getInhoud().getGeslachtsnaamstam().getWaarde());
        Assert.assertEquals(new BrpNaamgebruikCode("E"), brpInhoud.get(0).getInhoud().getNaamgebruikCode());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpNaamgebruikInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpNaamgebruikInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonNaamgebruikGroepToe(b, null, null, null, null, null, null, null, null)));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getGeslachtsnaamstam());
    }
}
