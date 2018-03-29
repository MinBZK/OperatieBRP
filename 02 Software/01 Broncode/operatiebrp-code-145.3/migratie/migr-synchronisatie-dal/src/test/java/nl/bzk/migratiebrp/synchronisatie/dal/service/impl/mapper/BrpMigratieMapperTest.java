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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

public class BrpMigratieMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpMigratieMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonMigratieHistorie historie =
                new PersoonMigratieHistorie(new Persoon(SoortPersoon.INGESCHREVENE), SoortMigratie.IMMIGRATIE);
        historie.setLandOfGebied(new LandOfGebied("4022", "naam"));
        historie.setAangeverMigratie(new Aangever('V', "Verzorger", "Verzorger omschrijving"));
        historie.setRedenWijzigingMigratie(new RedenWijzigingVerblijf('A', "Ambtshalve"));

        final BrpMigratieInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpLandOfGebiedCode("4022"), result.getLandOfGebiedCode());
        Assert.assertEquals(BrpSoortMigratieCode.IMMIGRATIE, result.getSoortMigratieCode());
        Assert.assertEquals(new BrpAangeverCode('V'), result.getAangeverMigratieCode());
        Assert.assertEquals(new BrpRedenWijzigingVerblijfCode('A'), result.getRedenWijzigingMigratieCode());
    }
}
