/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.util.List;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonIndicatiesVastgesteldNietNederlanderMapperTest extends AbstractMapperTestBasis {

    private final PersoonIndicatiesVastgesteldNietNederlanderMapper mapper;

    public PersoonIndicatiesVastgesteldNietNederlanderMapperTest() {
        final PersoonIndicatieVastgesteldNietNederlanderMapper singleMapper = new PersoonIndicatieVastgesteldNietNederlanderMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        mapper = new PersoonIndicatiesVastgesteldNietNederlanderMapper(singleMapper);
    }

    @Test
    public void testSucces() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonIndicatieVastgesteldNietNederlander(true));

        final List<BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>> stapels = doMapping(mapper, builder);

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 1);
        Assert.assertEquals(Boolean.TRUE, stapels.get(0).get(0).getInhoud().getIndicatie().getWaarde());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>> stapels = doMapping(mapper, maakIngeschrevene());

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 0);
    }

    @Test
    public void testGeenWaarde() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonIndicatieVastgesteldNietNederlander(null));

        final List<BrpStapel<BrpVastgesteldNietNederlanderIndicatieInhoud>> stapels = doMapping(mapper, builder);

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 1);
        Assert.assertTrue(stapels.get(0).size() == 1);
        Assert.assertEquals(null, stapels.get(0).get(0).getInhoud().getIndicatie());
    }
}
