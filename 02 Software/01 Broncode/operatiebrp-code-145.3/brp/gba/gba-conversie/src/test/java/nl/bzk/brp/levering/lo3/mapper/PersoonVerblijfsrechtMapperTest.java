/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonVerblijfsrechtMapperTest extends AbstractMapperTestBasis {

    private final PersoonVerblijfsrechtMapper mapper = new PersoonVerblijfsrechtMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpVerblijfsrechtInhoud> brpVerblijfstitel =
                doMapping(mapper, maakIngeschrevene(b -> MetaObjectUtil.voegPersoonVerblijfsrechtGroepToe(b, "01", 20131211, 20141211)));

        Assert.assertNotNull(brpVerblijfstitel);
        Assert.assertTrue(brpVerblijfstitel.size() == 1);
        Assert.assertEquals("01", brpVerblijfstitel.get(0).getInhoud().getAanduidingVerblijfsrechtCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpVerblijfsrechtInhoud> brpVerblijfstitel = doMapping(mapper, maakIngeschrevene());
        Assert.assertNull(brpVerblijfstitel);
    }

    @Test
    public void testGeenTitel() {
        final BrpStapel<BrpVerblijfsrechtInhoud> brpVerblijfstitel =
                doMapping(mapper, maakIngeschrevene(b -> MetaObjectUtil.voegPersoonVerblijfsrechtGroepToe(b, null, null, null)));

        Assert.assertNotNull(brpVerblijfstitel);
        Assert.assertTrue(brpVerblijfstitel.size() == 1);
        Assert.assertNull(brpVerblijfstitel.get(0).getInhoud().getAanduidingVerblijfsrechtCode());
    }
}
