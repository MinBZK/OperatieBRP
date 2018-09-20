/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.beheer.webapp.repository;
//
//import javax.inject.Named;
//
//import nl.bzk.brp.beheer.webapp.configuratie.BlobifierConfiguratie;
//import nl.bzk.brp.beheer.webapp.configuratie.RepositoryConfiguratie;
//import nl.bzk.brp.beheer.webapp.test.AbstractDatabaseTest;
//import nl.bzk.brp.beheer.webapp.test.Data;
//import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Kanaal;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {RepositoryConfiguratie.class, BlobifierConfiguratie.class }, loader = AnnotationConfigContextLoader.class)
//@Data(resources = {"classpath:/data/testdata.xml" }, dataSourceRef = RepositoryConfiguratie.DATA_SOURCE_MASTER)
//@TransactionConfiguration
//@Transactional
//public class KanaalRepositoryTest extends AbstractDatabaseTest {
//
//    @Autowired
//    @Named("kanaalRepository")
//    private ReadonlyRepository<Kanaal, Integer> subject;
//
//    @Test
//    public void findAll() throws JsonProcessingException {
//        final Pageable pageable = new PageRequest(0, 10);
//        final Page<Kanaal> page = subject.findAll(null, pageable);
//        Assert.assertNotNull(page);
//        Assert.assertEquals(3L, page.getTotalElements());
//    }
//
//    @Test
//    public void findOne() throws JsonProcessingException {
//        final Kanaal item = subject.findOne(1);
//        Assert.assertNotNull(item);
//    }
//}
