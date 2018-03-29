/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonGeboorteMapperTest extends AbstractMapperTestBasis {

    private final PersoonGeboorteMapper mapper = new PersoonGeboorteMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, 20130101, "0518", "6030")));

        Assert.assertNotNull(brpGeboorte);
        Assert.assertTrue(brpGeboorte.size() == 1);
        Assert.assertEquals(Integer.valueOf(20130101), brpGeboorte.get(0).getInhoud().getGeboortedatum().getWaarde());
        Assert.assertEquals("0518", brpGeboorte.get(0).getInhoud().getGemeenteCode().getWaarde());
        Assert.assertEquals("6030", brpGeboorte.get(0).getInhoud().getLandOfGebiedCode().getWaarde());

        Assert.assertNotNull(brpGeboorte.get(0).getActieInhoud());
        Assert.assertNull(brpGeboorte.get(0).getActieGeldigheid());
        Assert.assertNull(brpGeboorte.get(0).getActieVerval());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpGeboorte);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpGeboorteInhoud> brpGeboorte =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonGeboorteGroepToe(b, actie, null, null, null)));

        Assert.assertNotNull(brpGeboorte);
        Assert.assertTrue(brpGeboorte.size() == 1);
        Assert.assertNull(brpGeboorte.get(0).getInhoud().getGeboortedatum());
    }
}
