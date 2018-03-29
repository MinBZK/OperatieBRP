/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonSamengesteldeNaamMapperTest extends AbstractMapperTestBasis {

    private static final String H = "H";

    private final PersoonSamengesteldeNaamMapper mapper = new PersoonSamengesteldeNaamMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void test() {
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonSamengesteldeNaamGroepToe(
                        b,
                        H,
                        "geslachtsnaam",
                        false,
                        true,
                        H,
                        " ",
                        "Voornaam1 Voornaam2",
                        "de")));

        Assert.assertNotNull(samengesteldeNaam);
        Assert.assertTrue(samengesteldeNaam.size() == 1);
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(samengesteldeNaam);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaam =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonSamengesteldeNaamGroepToe(b, null, null, null, null, null, null, null, null)));

        Assert.assertNotNull(samengesteldeNaam);
        Assert.assertTrue(samengesteldeNaam.size() == 1);
        Assert.assertNull(samengesteldeNaam.get(0).getInhoud().getGeslachtsnaamstam());
    }
}
