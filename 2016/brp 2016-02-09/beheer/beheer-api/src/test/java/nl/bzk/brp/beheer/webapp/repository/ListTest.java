/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository;

import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, BlobifierConfiguratie.class }, loader = AnnotationConfigContextLoader.class)
@Data(resources = {"classpath:/data/testdata.xml" }, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class ListTest extends AbstractDatabaseTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void list() {
        for (final String beanDefName : applicationContext.getBeanDefinitionNames()) {
            final Object bean = applicationContext.getBean(beanDefName);
            System.out.println("Bean def: " + beanDefName + " ->" + bean.getClass().getName());
        }
    }
}
