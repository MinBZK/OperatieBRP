/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.sql.Timestamp;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAfgeleidAdministratiefInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

import org.junit.Assert;
import org.junit.Test;

public class BrpAfgeleidAdministratiefMapperTest extends BrpAbstractTest {

    @Inject
    private BrpAfgeleidAdministratiefMapper mapper;

    @Test
    public void testMapInhoud() {

        final Persoon persoon = new Persoon();
        final Timestamp timestamp = new Timestamp(0L);
        persoon.setTijdstipLaatsteWijziging(timestamp);

        final BrpStapel<BrpAfgeleidAdministratiefInhoud> result = mapper.map(persoon);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        final BrpAfgeleidAdministratiefInhoud inhoud = result.get(0).getInhoud();

        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(19700101000000L), inhoud.getLaatsteWijziging());
        Assert.assertNull(inhoud.getInOnderzoek());
    }

    @Test
    public void testMapInhoud1() {

        final Persoon persoon = new Persoon();
        final Timestamp timestamp = new Timestamp(1000L);
        persoon.setTijdstipLaatsteWijziging(timestamp);

        final BrpStapel<BrpAfgeleidAdministratiefInhoud> result = mapper.map(persoon);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        final BrpAfgeleidAdministratiefInhoud inhoud = result.get(0).getInhoud();

        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(19700101000001L), inhoud.getLaatsteWijziging());
        Assert.assertNull(inhoud.getInOnderzoek());
    }

    @Test
    public void testMapInhoud2012() {

        final Persoon persoon = new Persoon();
        final Timestamp timestamp = new Timestamp(BrpDatumTijd.fromDatumTijd(20121218131000L).getJavaDate().getTime());
        persoon.setTijdstipLaatsteWijziging(timestamp);

        final BrpStapel<BrpAfgeleidAdministratiefInhoud> result = mapper.map(persoon);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        final BrpAfgeleidAdministratiefInhoud inhoud = result.get(0).getInhoud();

        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20121218131000L), inhoud.getLaatsteWijziging());
        Assert.assertNull(inhoud.getInOnderzoek());
    }
}
