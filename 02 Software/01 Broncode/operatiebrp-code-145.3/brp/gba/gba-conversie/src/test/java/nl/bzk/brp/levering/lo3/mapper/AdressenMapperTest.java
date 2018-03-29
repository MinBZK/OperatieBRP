/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;
import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class AdressenMapperTest extends AbstractMapperTestBasis {

    private final AdressenMapper mapper = new AdressenMapper(new AdresMapper());

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        final AdresMapper singleMapper = new AdresMapper();
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
        ReflectionTestUtils.setField(mapper, "adresMapper", singleMapper);
    }

    @Test
    public void testSucces() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonAdres(actie));
        final List<BrpStapel<BrpAdresInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertTrue(brpInhoud.get(0).size() == 1);
        Assert.assertEquals(Character.valueOf('I'), brpInhoud.get(0).get(0).getInhoud().getAangeverAdreshoudingCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpAdresInhoud>> brpInhoud = doMapping(mapper, maakIngeschrevene());

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test
    public void testGeenWaarde() {
        final MetaObject.Builder builder = maakIngeschrevene(MetaObjectUtil::maakPersoonAdresLeeg);
        final List<BrpStapel<BrpAdresInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertTrue(brpInhoud.get(0).size() == 1);
        Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getDatumAanvangAdreshouding());
    }
}
