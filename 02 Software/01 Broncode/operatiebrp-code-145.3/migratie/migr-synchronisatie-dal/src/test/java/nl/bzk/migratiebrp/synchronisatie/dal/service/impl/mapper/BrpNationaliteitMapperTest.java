/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Test;

public class BrpNationaliteitMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpNationaliteitMapper.BrpNationaliteitInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonNationaliteitHistorie historie =
                new PersoonNationaliteitHistorie(new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), new Nationaliteit("naam", "0032")));
        historie.setRedenVerkrijgingNLNationaliteit(new RedenVerkrijgingNLNationaliteit("001", "oms"));
        historie.setRedenVerliesNLNationaliteit(new RedenVerliesNLNationaliteit("003", "oms"));

        final BrpNationaliteitInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpNationaliteitCode("0032"), result.getNationaliteitCode());
        Assert.assertEquals(new BrpRedenVerkrijgingNederlandschapCode("001"), result.getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(new BrpRedenVerliesNederlandschapCode("003"), result.getRedenVerliesNederlandschapCode());
    }
}
