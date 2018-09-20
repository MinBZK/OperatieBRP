/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.configuratie;

import static junit.framework.Assert.assertEquals;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;


public class BrpConfiguratieTest {

    @Test
    public void testDatabaseTimeOutStandaardWaardeIndienGeenWaarde() throws ConfigurationException {
        BrpConfiguratie brpConfiguratie = new BrpConfiguratie("leeg.brp.properties", 10);
        assertEquals(5, brpConfiguratie.getDatabaseTimeOutProperty());
    }

    @Test
    public void testDatabaseTimeOutStandaardWaardeIndienFoutieveWaarde() throws ConfigurationException {
        BrpConfiguratie brpConfiguratie = new BrpConfiguratie("fout.brp.properties", 10);
        assertEquals(5, brpConfiguratie.getDatabaseTimeOutProperty());
    }

    @Test
    public void testDatabaseTimeOutCorrecteIngesteldeWaarde() throws ConfigurationException {
        BrpConfiguratie brpConfiguratie = new BrpConfiguratie("brp.properties", 10);
        assertEquals(2, brpConfiguratie.getDatabaseTimeOutProperty());
    }
}
