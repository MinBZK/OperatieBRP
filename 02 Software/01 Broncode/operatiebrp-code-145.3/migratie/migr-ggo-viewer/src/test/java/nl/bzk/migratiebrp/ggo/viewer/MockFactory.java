/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.ggo.viewer.service.BcmService;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;

import org.mockito.Mockito;

/**
 * Voor de test-viewer-beans.xml . Ik kreeg dit helaas niet op een generiekere manier voor elkaar.
 */
public class MockFactory {
    public DbService createDbService() {
        return Mockito.mock(DbService.class);
    }

    public BcmService createBcmService() {
        return Mockito.mock(BcmService.class);
    }

    public BrpDalService createBrpDalService() {
        return Mockito.mock(BrpDalService.class);
    }

    public DynamischeStamtabelRepository createDynamischeStamtabelRepository() {
        return Mockito.mock(DynamischeStamtabelRepository.class);
    }

    public PersoonRepository createPersoonRepository() {
        return Mockito.mock(PersoonRepository.class);
    }
}
