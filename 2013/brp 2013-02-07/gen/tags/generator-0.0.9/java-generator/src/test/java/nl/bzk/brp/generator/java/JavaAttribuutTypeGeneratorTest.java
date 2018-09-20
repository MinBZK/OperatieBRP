/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.StringWriter;

import junit.framework.Assert;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.metaregister.dataaccess.AttribuutTypeDao;
import nl.bzk.brp.metaregister.model.AttribuutType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaAttribuutTypeGeneratorTest extends AbstractBmrTest {


    @Autowired(required = true)
    private JavaAttribuutTypeGenerator generator;

    @Autowired(required = true)
    private AttribuutTypeDao attribuutTypeDao;

    GenerationReport report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        report.setObjectType("attribuuttypes");
    }

    @Test
    public void genereerAttribuutTypeDatum() {
        StringWriter writer = new StringWriter();
        AttribuutType attribuutType = attribuutTypeDao.getBySyncId(3040);
        generator.genereerElement(writer, report, attribuutType);
        Assert.assertEquals(getVerwachtResultaat("Datum.jav"), toUnixString(writer));
    }

    @Test
    public void genereerAttribuutTypeIpAdres() {
        StringWriter writer = new StringWriter();
        AttribuutType attribuutType = attribuutTypeDao.getBySyncId(4669);
        generator.genereerElement(writer, report, attribuutType);
        Assert.assertEquals(getVerwachtResultaat("IPAdres.jav"), toUnixString(writer));
    }

}
