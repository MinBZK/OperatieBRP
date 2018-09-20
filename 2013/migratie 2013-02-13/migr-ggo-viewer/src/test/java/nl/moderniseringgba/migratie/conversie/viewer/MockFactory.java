/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import nl.moderniseringgba.migratie.conversie.viewer.service.BcmService;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;

import org.mockito.Mockito;

/**
 * Voor de test-viewer-beans.xml . Ik kreeg dit helaas niet op geen generiekere manier voor elkaar.
 */
public class MockFactory {
    public DbService createDbService() {
        return Mockito.mock(DbService.class);
    }

    public BcmService createBcmService() {
        return Mockito.mock(BcmService.class);
    }
}
