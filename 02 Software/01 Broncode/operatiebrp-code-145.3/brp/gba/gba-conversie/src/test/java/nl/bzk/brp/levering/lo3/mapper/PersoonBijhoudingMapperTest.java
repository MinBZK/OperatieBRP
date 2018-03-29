/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonBijhoudingMapperTest extends AbstractMapperTestBasis {

    private final PersoonBijhoudingMapper mapper = new PersoonBijhoudingMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(MetaObjectUtil::voegPersoonBijhoudingGroepToe));

        Assert.assertNotNull(brpBijhoudingsaard);
        Assert.assertTrue(brpBijhoudingsaard.size() == 1);
        Assert.assertEquals(Bijhoudingsaard.INGEZETENE.getCode(), brpBijhoudingsaard.get(0).getInhoud().getBijhoudingsaardCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpBijhoudingsaard);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpBijhoudingInhoud> brpBijhoudingsaard =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene((b) -> MetaObjectUtil.voegPersoonBijhoudingGroepToe(b, null)));

        Assert.assertNotNull(brpBijhoudingsaard);
        Assert.assertTrue(brpBijhoudingsaard.size() == 1);
        Assert.assertNull(brpBijhoudingsaard.get(0).getInhoud().getBijhoudingsaardCode());
    }
}
