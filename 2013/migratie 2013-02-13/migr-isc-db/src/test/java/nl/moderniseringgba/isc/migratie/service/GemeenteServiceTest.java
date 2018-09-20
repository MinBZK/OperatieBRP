/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.service;

import javax.inject.Inject;

import nl.moderniseringgba.isc.migratie.AbstractDbTest;

import org.junit.Assert;
import org.junit.Test;

public class GemeenteServiceTest extends AbstractDbTest {

    @Inject
    private GemeenteService gemeenteService;

    @Test
    public void test() {
        Assert.assertEquals(Stelsel.BRP, gemeenteService.geefStelselVoorGemeente(1));
        Assert.assertEquals(Stelsel.GBA, gemeenteService.geefStelselVoorGemeente(2));
        Assert.assertEquals(Stelsel.GBA, gemeenteService.geefStelselVoorGemeente(1905));
    }
}
