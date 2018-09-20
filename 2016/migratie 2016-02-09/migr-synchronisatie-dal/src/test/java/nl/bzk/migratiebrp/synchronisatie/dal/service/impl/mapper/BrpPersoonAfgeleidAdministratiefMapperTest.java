/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonAfgeleidAdministratiefInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Assert;
import org.junit.Test;

public class BrpPersoonAfgeleidAdministratiefMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpPersoonAfgeleidAdministratiefMapper mapper;

    @Test
    public void testMapInhoud() {

        final Timestamp timestamp = new Timestamp(0L);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partijnaam", 42), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp, false);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);

        Assert.assertEquals(new BrpBoolean(false, null), result.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig());
    }

    @Test
    public void testMapInhoud1() {

        final Timestamp timestamp = new Timestamp(1000L);
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partijnaam", 42), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp, false);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);

        Assert.assertEquals(new BrpBoolean(false, null), result.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig());
    }

    @Test
    public void testMapInhoud2012() {

        final Timestamp timestamp = new Timestamp(BrpDatumTijd.fromDatumTijd(20121218131000L, null).getJavaDate().getTime());
        final AdministratieveHandeling administratieveHandeling =
                new AdministratieveHandeling(new Partij("partijnaam", 42), SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL);
        final PersoonAfgeleidAdministratiefHistorie historie =
                new PersoonAfgeleidAdministratiefHistorie((short) 1, new Persoon(SoortPersoon.INGESCHREVENE), administratieveHandeling, timestamp, false);

        final BrpPersoonAfgeleidAdministratiefInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);

        Assert.assertEquals(new BrpBoolean(false, null), result.getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig());
    }
}
