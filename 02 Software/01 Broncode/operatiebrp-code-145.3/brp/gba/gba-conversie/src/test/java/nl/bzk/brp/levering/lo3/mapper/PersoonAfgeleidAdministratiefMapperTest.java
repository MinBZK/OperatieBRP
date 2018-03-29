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
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonAfgeleidAdministratiefMapperTest extends AbstractMapperTestBasis {

    private final PersoonAfgeleidAdministratiefMapper mapper = new PersoonAfgeleidAdministratiefMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final MetaObject.Builder builder = MetaObjectUtil.maakIngeschrevene(MetaObjectUtil::voegPersoonAfgeleidAdministratiefGroepToe);
        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    /**
     * Vanwege primitive gebruik in BrpPersoonAfgeleidAdministratiefInhoud
     */
    @Test
    public void testGeenWaarde() {
        final MetaObject.Builder builder = MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonAfgeleidAdministratiefGroepToe(b, null));
        final BrpStapel<BrpPersoonAfgeleidAdministratiefInhoud> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
    }
}
