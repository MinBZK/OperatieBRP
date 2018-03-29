/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Assert;
import org.junit.Test;

public class BrpPersoonAfgeleidAdministratiefMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<>());

    @Inject
    private BrpPersoonAfgeleidAdministratiefMapper mapper;

    @Test
    public void testMapInhoud() {

        final Timestamp timestamp = new Timestamp(0L);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(
                        new Partij("partijnaam", "000042"),
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                        new Timestamp(System.currentTimeMillis()));
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
    }

    @Test
    public void testMapInhoud1() {

        final Timestamp timestamp = new Timestamp(1000L);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(
                        new Partij("partijnaam", "000042"),
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                        new Timestamp(System.currentTimeMillis()));
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
    }

    @Test
    public void testMapInhoud2012() {

        final Timestamp timestamp = new Timestamp(BrpDatumTijd.fromDatumTijd(20121218131000L, null).getJavaDate().getTime());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(
                        new Partij("partijnaam", "000042"),
                        SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL,
                        new Timestamp(System.currentTimeMillis()));
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
    }
}
