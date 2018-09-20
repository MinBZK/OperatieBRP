/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.dataaccess;

import nl.bzk.brp.metaregister.model.Laag;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * Abstracte superclass voor repository (persistence) testcases.
 */
@ContextConfiguration
public abstract class AbstractRepositoryTestCase extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void init() {
        AbstractElementDao.setLaag(Laag.LOGISCH);
    }

}
