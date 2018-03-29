/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.actie;
import static nl.bzk.brp.levering.lo3.support.MetaObjectUtil.maakIngeschrevene;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.levering.lo3.support.MetaObjectUtil;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class VerificatiesMapperTest extends AbstractMapperTestBasis {

    private final VerificatieMapper singleMapper = new VerificatieMapper();
    private final VerificatiesMapper mapper = new VerificatiesMapper(singleMapper);

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
    }

    @Test
    public void testSucces() throws ReflectiveOperationException {
        final ZonedDateTime tsReg = ZonedDateTime.of(2013, 12, 11, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(1, "998555", tsReg, SoortAdministratieveHandeling
                        .GBA_INITIELE_VULLING)
                .metObject(TestVerantwoording.maakActieBuilder(10, SoortActie.CONVERSIE_GBA, tsReg, "000001", 20131212)
                ).build());
        final Actie actie2 = handeling.getActies().iterator().next();

        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Arrays.asList(
                                () -> MetaObjectUtil.maakPersoonVerificatie(MetaObjectUtil.actie, "998556", "Verificatie1", 20130513),
                                () -> MetaObjectUtil.maakPersoonVerificatie(actie2, "998555", "Verificatie2", 20130514)),
                        Collections.emptyList());

        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertEquals(2, brpInhoud.size());
        Assert.assertEquals(1, brpInhoud.get(0).size());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud = doMapping(mapper, maakIngeschrevene());
        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test
    public void testGeenWaarde() throws ReflectiveOperationException {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonVerificatie(actie, "998555", "Verificatie2", null));
        final List<BrpStapel<BrpVerificatieInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getDatum());
    }
}
