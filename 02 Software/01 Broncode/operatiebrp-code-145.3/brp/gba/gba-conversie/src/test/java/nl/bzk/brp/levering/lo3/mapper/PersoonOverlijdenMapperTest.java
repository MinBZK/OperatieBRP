/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonOverlijdenMapperTest extends AbstractMapperTestBasis {

    private final PersoonOverlijdenMapper mapper = new PersoonOverlijdenMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpOverlijdenInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonOverlijdenGroepToe(
                        b,
                        actie,
                        "BuitenlandsePlaats",
                        "BuitenlandseRegio",
                        20131212,
                        "0518",
                        "6030",
                        "Achter de bosjes",
                        "Rotterdam")));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Integer.valueOf(20131212), brpInhoud.get(0).getInhoud().getDatum().getWaarde());
        Assert.assertEquals("0518", brpInhoud.get(0).getInhoud().getGemeenteCode().getWaarde());
        Assert.assertEquals("6030", brpInhoud.get(0).getInhoud().getLandOfGebiedCode().getWaarde());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpOverlijdenInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    @Test
    public void testGeenWaarde() {
        final BrpStapel<BrpOverlijdenInhoud> brpInhoud =
                doMapping(
                    mapper,
                    MetaObjectUtil.maakIngeschrevene(
                        b -> MetaObjectUtil.voegPersoonOverlijdenGroepToe(b, actie, null, null, null, null, null, null, null)));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).getInhoud().getDatum());
    }
}
