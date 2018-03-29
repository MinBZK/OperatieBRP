/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.brp.beheer.webapp.configuratie.DatabaseConfiguration;
import nl.bzk.brp.beheer.webapp.configuratie.JsonConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.view.PersoonViewFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test voor de PersoonDetailSerializerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class, RepositoryConfiguratie.class, JsonConfiguratie.class},
        loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class PersoonDetailSerializerTest extends AbstractDatabaseTest {

    @Inject
    private BrpJsonObjectMapper subject;
    @Inject
    private PersoonRepository persoonRepository;
    @Inject
    private PersoonViewFactory persoonViewFactory;

    @Test
    @Transactional
    public void testPersoonSerializen() throws Exception {
        final Persoon persoon = persoonRepository.findOne(1L);
        Assert.assertNotNull("Persoon mag niet null zijn", persoon);
        final String result = subject.writeValueAsString(persoonViewFactory.mapPersoonDetailView(persoon));
        System.out.println(result);
        Assert.assertNotNull("Resultaat mag niet null zijn", result);
        Assert.assertTrue(result.indexOf("In het ziekenhuis") > -1);
        Assert.assertTrue(result.indexOf("Buitenlands adres 1") > -1);
    }

}
