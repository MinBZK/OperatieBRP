/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Test;

import org.junit.Assert;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

public class BrpUitsluitingKiesrechtMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpUitsluitingKiesrechtMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonUitsluitingKiesrechtHistorie historie =
                new PersoonUitsluitingKiesrechtHistorie(new Persoon(SoortPersoon.INGESCHREVENE), true);
        historie.setDatumVoorzienEindeUitsluitingKiesrecht(Integer.valueOf("20110505"));

        final BrpUitsluitingKiesrechtInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(Boolean.TRUE, BrpBoolean.unwrap(result.getIndicatieUitsluitingKiesrecht()));
        Assert.assertEquals(new BrpDatum(20110505, null), result.getDatumVoorzienEindeUitsluitingKiesrecht());
    }
}
