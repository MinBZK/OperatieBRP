/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfiguratie.class, RepositoryConfiguratie.class, BlobifierConfiguratie.class, DummySecurityConfiguratie.class })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@Data(resources = {"classpath:/data/testdata.xml" }, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public abstract class AbstractControllerIntegratieTest extends AbstractDatabaseTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    /**
     * before.
     */
    @Before
    public final void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

}
