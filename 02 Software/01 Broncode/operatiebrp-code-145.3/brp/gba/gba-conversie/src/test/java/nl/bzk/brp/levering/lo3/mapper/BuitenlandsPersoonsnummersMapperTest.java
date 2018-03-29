/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.util.List;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class BuitenlandsPersoonsnummersMapperTest extends AbstractMapperTestBasis {

    private final BuitenlandsPersoonsnummerMapper singleMapper = new BuitenlandsPersoonsnummerMapper();
    private final BuitenlandsPersoonsnummersMapper mapper = new BuitenlandsPersoonsnummersMapper(singleMapper);

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
    }

    @Test
    public void test() {
        final MetaObject.Builder builder = maakIngeschrevene(MetaObjectUtil::maakPersoonBuitenlandsPersoonsnummer);

        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = doMapping(mapper, builder);

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 1);
        Assert.assertNotNull(stapels.get(0).get(0).getInhoud().getNummer());
    }

    @Test
    public void testGeenWaarde() {
        final MetaObject.Builder builder = MetaObjectUtil.maakIngeschrevene(() -> MetaObjectUtil.maakPersoonBuitenlandsPersoonsnummer(null, null));

        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = doMapping(mapper, builder);

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 1);
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> stapels = doMapping(mapper, maakIngeschrevene());

        Assert.assertNotNull(stapels);
        Assert.assertTrue(stapels.size() == 0);
    }
}
