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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonIdentificatienummersMapperTest extends AbstractMapperTestBasis {

    private final PersoonIdentificatienummersMapper mapper = new PersoonIdentificatienummersMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpIdentificatienummersInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonIdentificatienummersGroepToe(b, "1234567890", "123456789")));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals("1234567890", brpInhoud.get(0).getInhoud().getAdministratienummer().getWaarde());
        Assert.assertEquals("123456789", brpInhoud.get(0).getInhoud().getBurgerservicenummer().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpIdentificatienummersInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpIdentificatienummersInhoud> brpInhoud =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonIdentificatienummersGroepToe(b, null, null)));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getAdministratienummer());
    }
}
