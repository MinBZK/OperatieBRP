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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonDeelnameEuVerkiezingenMapperTest extends AbstractMapperTestBasis {

    private final PersoonDeelnameEuVerkiezingenMapper mapper = new PersoonDeelnameEuVerkiezingenMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> brpEUVerkiezingen =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(MetaObjectUtil::voegPersoonDeelnameEUVerkiezingenGroepToe));

        Assert.assertNotNull(brpEUVerkiezingen);
        Assert.assertTrue(brpEUVerkiezingen.size() == 1);
        Assert.assertEquals(Boolean.TRUE, brpEUVerkiezingen.get(0).getInhoud().getIndicatieDeelnameEuVerkiezingen().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> brpEUVerkiezingen = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpEUVerkiezingen);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpDeelnameEuVerkiezingenInhoud> brpEUVerkiezingen =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene((b) -> MetaObjectUtil.voegPersoonDeelnameEUVerkiezingenGroepToe(b, null, null)));

        Assert.assertNotNull(brpEUVerkiezingen);
        Assert.assertTrue(brpEUVerkiezingen.size() == 1);
        Assert.assertEquals(null, brpEUVerkiezingen.get(0).getInhoud().getIndicatieDeelnameEuVerkiezingen());
    }
}
