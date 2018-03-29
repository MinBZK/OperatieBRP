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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonNummerverwijzingMapperTest extends AbstractMapperTestBasis {

    private final PersoonNummerverwijzingMapper mapper = new PersoonNummerverwijzingMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpNummerverwijzingInhoud> brpInhoud =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonNummerverwijzingGroepToe(b, "1234567890", "9876543210")));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals("1234567890", brpInhoud.get(0).getInhoud().getVolgendeAdministratienummer().getWaarde());
        Assert.assertEquals("9876543210", brpInhoud.get(0).getInhoud().getVorigeAdministratienummer().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpNummerverwijzingInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpNummerverwijzingInhoud> brpInhoud =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonNummerverwijzingGroepToe(b, null, null)));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getVolgendeAdministratienummer());
        Assert.assertNull(brpInhoud.get(0).getInhoud().getVorigeAdministratienummer());
    }
}
