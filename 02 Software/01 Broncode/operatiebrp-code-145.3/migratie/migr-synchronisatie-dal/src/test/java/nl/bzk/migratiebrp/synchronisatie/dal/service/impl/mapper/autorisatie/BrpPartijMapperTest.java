/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.inject.Inject;

import org.junit.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../synchronisatie-brp-mapper-beans.xml")
public class BrpPartijMapperTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpPartijMapper mapper;

    @Test
    public void testMapInhoud() {
        final Partij partij = new Partij("Partijnaam", "000001");
        final Timestamp datumTijdRegistratie = new Timestamp(System.currentTimeMillis());
        final PartijHistorie historie = new PartijHistorie(partij, datumTijdRegistratie, 2013_01_01, false, partij.getNaam());
        historie.setDatumEinde(2013_02_02);

        final BrpPartijInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(Integer.valueOf(2013_02_02), result.getDatumEinde().getWaarde());
        Assert.assertEquals(Integer.valueOf(2013_01_01), result.getDatumIngang().getWaarde());
    }
}
