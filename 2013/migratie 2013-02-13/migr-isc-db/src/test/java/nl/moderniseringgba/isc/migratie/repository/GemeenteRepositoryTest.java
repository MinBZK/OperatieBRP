/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.repository;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.migratie.AbstractDbTest;
import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;

import org.junit.Assert;
import org.junit.Test;

public class GemeenteRepositoryTest extends AbstractDbTest {

    @Inject
    private GemeenteRepository gemeenteRepository;

    @Test
    public void testFindGemeente() {
        final Gemeente gemeente = gemeenteRepository.findGemeente(1);
        Assert.assertNotNull(gemeente);
        Assert.assertEquals(Integer.valueOf(1), gemeente.getGemeenteCode());
        Assert.assertEquals(Integer.valueOf(20120101), gemeente.getDatumBrp());
    }

    @Test
    public void testGetBrpActiveGemeente() {
        final List<Gemeente> gemeenten = gemeenteRepository.getBrpActiveGemeente();
        Assert.assertNotNull(gemeenten);
        Assert.assertEquals(4, gemeenten.size());
    }
}
