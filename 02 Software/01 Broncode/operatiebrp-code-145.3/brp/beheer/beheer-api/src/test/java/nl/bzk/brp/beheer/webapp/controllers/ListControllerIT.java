/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.WebConfiguratie;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.test.Datas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RepositoryConfiguratie.class, WebConfiguratie.class, DummySecurityConfiguratie.class})
@Datas({@Data(resources = "classpath:/data/testdata.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER),
        @Data(resources = "classpath:/data/ber.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_ARCHIEVERING)})
public class ListControllerIT extends AbstractIntegratieTest {

    @Inject
    private ApplicationContext applicationContext;

    @Test
    public void list() throws Exception {
        for (final String beanDefName : applicationContext.getBeanDefinitionNames()) {
            if (beanDefName.equals("vrijBerichtClient.proxyFactory")) {
                // Skip de web service client maar even...
                continue;
            }
            final Object bean = applicationContext.getBean(beanDefName);
            System.out.println("Bean def: " + beanDefName + " ->" + bean.getClass().getName());
        }
    }
}
