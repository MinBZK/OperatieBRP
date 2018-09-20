/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
import nl.bzk.brp.beheer.webapp.configuratie.json.BrpJsonObjectMapper;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonHisVolledigRepository;
import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
import nl.bzk.brp.beheer.webapp.test.Data;
import nl.bzk.brp.beheer.webapp.view.PersoonDetailView;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test voor de PersoonDetailSerializerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguratie.class, BlobifierConfiguratie.class}, loader = AnnotationConfigContextLoader.class)
@Data(resources = "classpath:/data/actieviewtest.xml", dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
public class PersoonDetailSerializerTest extends AbstractDatabaseTest {

    private final BrpJsonObjectMapper subject = new BrpJsonObjectMapper();
    @Autowired
    private PersoonHisVolledigRepository persoonRepository;

    @Test
    @Transactional
    public void testPersoonSeriazen() throws Exception {
        final PersoonHisVolledig persoon = persoonRepository.findOne(1);
        Assert.assertNotNull("Persoon mag niet null zijn", persoon);
        String result = subject.writeValueAsString(new PersoonDetailView(persoon));
        Assert.assertNotNull("Resultaat mag niet null zijn", result);
        Assert.assertTrue(result.indexOf("In het ziekenhuis") > -1);
        Assert.assertTrue(result.indexOf("Buitenlands adres 1") > -1);
    }

}
