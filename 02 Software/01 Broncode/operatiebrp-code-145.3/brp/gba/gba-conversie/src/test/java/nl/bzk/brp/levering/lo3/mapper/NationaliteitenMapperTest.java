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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class NationaliteitenMapperTest extends AbstractMapperTestBasis {

    private final NationaliteitMapper singleMapper = new NationaliteitMapper();
    private final NationaliteitenMapper mapper = new NationaliteitenMapper(singleMapper);

    @Before
    public void setupSingleMapper() throws ReflectiveOperationException {
        ReflectionTestUtils.setField(singleMapper, "verConvRepository", Mockito.mock(VerConvRepository.class, Mockito.RETURNS_DEFAULTS));
    }

    @Test
    public void testSucces() {

        final ZonedDateTime tsReg = ZonedDateTime.of(2013, 12, 11, 0, 0, 0, 0, DatumUtil.BRP_ZONE_ID);
        final AdministratieveHandeling handelingInhoud = AdministratieveHandeling.converter().converteer(TestVerantwoording
                .maakAdministratieveHandeling(111, "000034", tsReg, SoortAdministratieveHandeling
                        .GBA_AFVOEREN_PL)
                .metObject(TestVerantwoording.maakActieBuilder(10, SoortActie.CONVERSIE_GBA, tsReg, "000001", 20131212)
                ).build());
        final Actie actie2 = handelingInhoud.getActies().iterator().next();

        final MetaObject.Builder builder =
                maakIngeschrevene(
                        Arrays.asList(
                                () -> MetaObjectUtil.maakPersoonNationaliteit(actie, "0001", "016", null),
                                () -> MetaObjectUtil.maakPersoonNationaliteit(actie2, "0077" , null, "066")),
                        java.util.Collections.emptyList());

        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 2);
        Assert.assertTrue(brpInhoud.get(0).size() == 1);

         Assert.assertEquals("0001", brpInhoud.get(0).get(0).getInhoud().getNationaliteitCode().getWaarde());
         Assert.assertEquals("016", brpInhoud.get(0).get(0).getInhoud().getRedenVerkrijgingNederlandschapCode().getWaarde());
         Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getRedenVerliesNederlandschapCode());

         Assert.assertEquals("0077" , brpInhoud.get(1).get(0).getInhoud().getNationaliteitCode().getWaarde());
         Assert.assertEquals("066", brpInhoud.get(1).get(0).getInhoud().getRedenVerliesNederlandschapCode().getWaarde());
         Assert.assertNull(brpInhoud.get(1).get(0).getInhoud().getRedenVerkrijgingNederlandschapCode());
    }

    @Test
    public void testLeeg() {
        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud = doMapping(mapper, maakIngeschrevene());
        Assert.assertTrue(brpInhoud.size() == 0);
    }

    @Test
    public void testGeenWaarde() {
        final MetaObject.Builder builder = maakIngeschrevene(() -> MetaObjectUtil.maakPersoonNationaliteit(actie, null, "016", null));
        final List<BrpStapel<BrpNationaliteitInhoud>> brpInhoud = doMapping(mapper, builder);

        Assert.assertNotNull(brpInhoud);
        Assert.assertTrue(brpInhoud.size() == 1);
        Assert.assertNull(brpInhoud.get(0).get(0).getInhoud().getNationaliteitCode());
    }
}
