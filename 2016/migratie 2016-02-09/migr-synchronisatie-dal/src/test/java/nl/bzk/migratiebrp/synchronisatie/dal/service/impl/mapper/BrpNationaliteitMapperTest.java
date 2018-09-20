/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Nationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNationaliteitHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
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
                new PersoonNationaliteitHistorie(new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), new Nationaliteit(
                    "naam",
                    (short) 32)));
        historie.setRedenVerkrijgingNLNationaliteit(new RedenVerkrijgingNLNationaliteit((short) 1, "oms"));
        historie.setRedenVerliesNLNationaliteit(new RedenVerliesNLNationaliteit((short) 3, "oms"));

        final BrpNationaliteitInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpNationaliteitCode(Short.parseShort("0032")), result.getNationaliteitCode());
        Assert.assertEquals(
            new BrpRedenVerkrijgingNederlandschapCode(Short.parseShort("0001")),
            result.getRedenVerkrijgingNederlandschapCode());
        Assert.assertEquals(new BrpRedenVerliesNederlandschapCode(Short.parseShort("0003")), result.getRedenVerliesNederlandschapCode());
    }
}
