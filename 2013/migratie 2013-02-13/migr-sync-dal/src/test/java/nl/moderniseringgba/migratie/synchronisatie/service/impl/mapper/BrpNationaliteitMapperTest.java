/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;

import org.junit.Test;

public class BrpNationaliteitMapperTest extends BrpAbstractTest {

    @Inject
    private BrpNationaliteitMapper.BrpNationaliteitInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonNationaliteitHistorie historie = new PersoonNationaliteitHistorie();
        historie.setPersoonNationaliteit(new PersoonNationaliteit());
        historie.getPersoonNationaliteit().setNationaliteit(new Nationaliteit());
        historie.getPersoonNationaliteit().getNationaliteit().setNationaliteitcode(new BigDecimal("0032"));
        historie.setRedenVerkrijgingNLNationaliteit(new RedenVerkrijgingNLNationaliteit());
        historie.getRedenVerkrijgingNLNationaliteit().setNaam(new BigDecimal("0001"));
        historie.setRedenVerliesNLNationaliteit(new RedenVerliesNLNationaliteit());
        historie.getRedenVerliesNLNationaliteit().setNaam(new BigDecimal("0003"));

        final BrpNationaliteitInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpNationaliteitCode(Integer.valueOf("0032")), result.getNationaliteitCode());
        Assert.assertEquals(new BrpRedenVerkrijgingNederlandschapCode(new BigDecimal("0001")),
                result.getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(new BrpRedenVerliesNederlandschapCode(new BigDecimal("0003")),
                result.getRedenVerliesNederlandschapCode());
    }
}
