/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class GeslachtsnaamcomponentenMapperTest extends AbstractMapperTestBasis {

    private final GeslachtsnaamcomponentMapper singleMapper = new GeslachtsnaamcomponentMapper();
    private final GeslachtsnaamcomponentenMapper mapper = new GeslachtsnaamcomponentenMapper(singleMapper);

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
    }

    @Test
    public void testSucces() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(1, "Baronnetje"));
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Integer.valueOf(1), brpInhoud.get(0).get(0).getInhoud().getVolgnummer().getWaarde());
        Assert.assertEquals("Baronnetje", brpInhoud.get(0).get(0).getInhoud().getStam().getWaarde());
        Assert.assertTrue(brpInhoud.get(0).size() == 1);
    }

    @Test
    public void testTweeComponenten() {
        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Arrays.asList(
                                () -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(1, "Bastaard"),
                                () -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(2, "Pekela")),
                        java.util.Collections.emptyList());

        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 2);

        brpInhoud.sort((o1, o2) -> o1.get(0).getInhoud().getVolgnummer().compareTo(o2.get(0).getInhoud().getVolgnummer()));

        Assert.assertTrue(brpInhoud.get(0).size() == 1);
        Assert.assertEquals(Integer.valueOf(1), brpInhoud.get(0).get(0).getInhoud().getVolgnummer().getWaarde());
        Assert.assertEquals("Bastaard", brpInhoud.get(0).get(0).getInhoud().getStam().getWaarde());

        Assert.assertTrue(brpInhoud.get(1).size() == 1);
        Assert.assertEquals(Integer.valueOf(2), brpInhoud.get(1).get(0).getInhoud().getVolgnummer().getWaarde());
        Assert.assertEquals("Pekela", brpInhoud.get(1).get(0).getInhoud().getStam().getWaarde());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> brpInhoud = doMapping(mapper, maakIngeschrevene());
        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testGeenWaarde() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonGeslachtsnaamcomponent(1, null));
        doMapping(mapper, builder);
    }
}
