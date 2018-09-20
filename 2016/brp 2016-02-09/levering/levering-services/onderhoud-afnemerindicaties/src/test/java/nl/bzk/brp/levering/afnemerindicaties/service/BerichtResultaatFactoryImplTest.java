/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import static org.junit.Assert.assertNotNull;

import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;

public class BerichtResultaatFactoryImplTest {

    private BerichtResultaatFactoryImpl berichtResultaatFactory = new BerichtResultaatFactoryImpl();

    @Test
    public void testCreeerBerichtResultaat() {
        final OnderhoudAfnemerindicatiesResultaat resultaat = berichtResultaatFactory.creeerBerichtResultaat(
            new RegistreerAfnemerindicatieBericht(), new OnderhoudAfnemerindicatiesBerichtContextImpl(new BerichtenIds(0L, 1L), null, null, null));

        assertNotNull(resultaat);
    }

}
