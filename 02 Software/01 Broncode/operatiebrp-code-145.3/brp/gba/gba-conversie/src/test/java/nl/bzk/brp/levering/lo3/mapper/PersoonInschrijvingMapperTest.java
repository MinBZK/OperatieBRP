/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;

public class PersoonInschrijvingMapperTest extends AbstractMapperTestBasis {

    private final PersoonInschrijvingMapper mapper = new PersoonInschrijvingMapper();

    @Before
    public void setup() {
        final VerConvRepository dummyVerConvRepository = Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS);
        ReflectionTestUtils.setField(mapper, "verConvRepository", dummyVerConvRepository);
    }

    @Test
    public void testSucces() {
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonInschrijvingGroepToe(b, 20131212, new Date(123), 1L)));

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertEquals(Integer.valueOf(20131212), brpInhoud.get(0).getInhoud().getDatumInschrijving().getWaarde());
        Assert.assertEquals(Long.valueOf(1L), brpInhoud.get(0).getInhoud().getVersienummer().getWaarde());
        Assert.assertEquals(123L, brpInhoud.get(0).getInhoud().getDatumtijdstempel().getJavaDate().getTime());
        // Vorig en Volgend Persoon wordt op dit moment niet gevuld in het BRP model, zie {@link
        // AbstractHisPersoonInschrijvingModel}
        // Assert.assertEquals(1234567890L, brpInhoud.get(0).getInhoud().getVolgendAdministratienummer().longValue());
        // Assert.assertEquals(9876543210L, brpInhoud.get(0).getInhoud().getVorigAdministratienummer().longValue());
    }

    @Test
    public void testLeeg() {
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud = doMapping(mapper, MetaObjectUtil.maakIngeschrevene());
        Assert.assertNull(brpInhoud);
    }

    /**
     * Verwacht NPE vanwege hardgecodeerde controle op conversie model
     */
    @Test(expected = NullPointerException.class)
    public void testGeenWaarde() {
        final BrpStapel<BrpInschrijvingInhoud> brpInhoud =
                doMapping(mapper, MetaObjectUtil.maakIngeschrevene(b -> MetaObjectUtil.voegPersoonInschrijvingGroepToe(b, null, null, null)));

        Assert.assertNull(brpInhoud);
    }
}
