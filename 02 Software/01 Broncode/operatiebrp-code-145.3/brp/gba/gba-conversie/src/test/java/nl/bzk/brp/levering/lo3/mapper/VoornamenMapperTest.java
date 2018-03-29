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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class VoornamenMapperTest extends AbstractMapperTestBasis {

    private final VoornaamMapper singleMapper = new VoornaamMapper();
    private final VoornamenMapper mapper = new VoornamenMapper(singleMapper);

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
    }

    @Test
    public void testSucces() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonVoornaam(1, "voornaam"));

        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen = doMapping(mapper, builder);

        Assert.assertNotNull(brpVoornamen);
        Assert.assertTrue(brpVoornamen.size() == 1);
        Assert.assertTrue(brpVoornamen.get(0).size() == 1);
        Assert.assertEquals("voornaam", brpVoornamen.get(0).get(0).getInhoud().getVoornaam().getWaarde());
    }

    @Test
    public void testTweeVoornamen() {
        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Arrays.asList(
                                () -> MetaObjectUtil.maakPersoonVoornaam(1, "Jan"),
                                () -> MetaObjectUtil.maakPersoonVoornaam(2, "Jaap"),
                                () -> MetaObjectUtil.maakPersoonVoornaam(3, "Pieter")),
                        java.util.Collections.emptyList());

        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen = doMapping(mapper, builder);

        Assert.assertNotNull(brpVoornamen);
        Assert.assertTrue(brpVoornamen.size() == 3);

        brpVoornamen.sort((o1, o2) -> o1.get(0).getInhoud().getVolgnummer().compareTo(o2.get(0).getInhoud().getVolgnummer()));

        Assert.assertTrue(brpVoornamen.get(0).size() == 1);
        Assert.assertEquals(Integer.valueOf(1), brpVoornamen.get(0).get(0).getInhoud().getVolgnummer().getWaarde());
        Assert.assertEquals("Jan", brpVoornamen.get(0).get(0).getInhoud().getVoornaam().getWaarde());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen = doMapping(mapper, maakIngeschrevene());
        Assert.assertTrue(brpVoornamen.size() == 0);
    }

    @Test
    public void testGeenVoornaam() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonVoornaam(1, null));

        final List<BrpStapel<BrpVoornaamInhoud>> brpVoornamen = doMapping(mapper, builder);

        Assert.assertTrue(brpVoornamen.size() == 1);
        Assert.assertTrue(brpVoornamen.get(0).size() == 1);
        Assert.assertNull(brpVoornamen.get(0).get(0).getInhoud().getVoornaam());
    }
}
