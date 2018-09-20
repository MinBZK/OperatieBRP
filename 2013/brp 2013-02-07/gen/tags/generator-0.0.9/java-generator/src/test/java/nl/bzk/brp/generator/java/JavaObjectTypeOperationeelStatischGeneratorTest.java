/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.StringWriter;

import junit.framework.Assert;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaObjectTypeOperationeelStatischGeneratorTest extends AbstractBmrTest {

    @Autowired(required = true)
    private JavaObjectTypeStatischGenerator generator;

    @Autowired(required = true)
    private ObjectTypeDao                   objectTypeDao;

    GenerationReport                        report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        report.setObjectType("objecttypes");
    }

    @Test
    @Ignore
    public void shouldGenerateStatischObjectTypePartij() {
        StringWriter writer = new StringWriter();
        ObjectType object = objectTypeDao.getBySyncId(3141);
        generator.genereerElement(writer, report, object);
        Assert.assertEquals(getVerwachtResultaat("Partij.jav"), toUnixString(writer));
    }

    @Test
    @Ignore
    public void shouldGenerateStatischObjectTypeAutorisatiebesluit() {
        StringWriter writer = new StringWriter();
        ObjectType object = objectTypeDao.getBySyncId(4852);
        generator.genereerElement(writer, report, object);
        Assert.assertEquals(getVerwachtResultaat("Autorisatiebesluit.jav"), toUnixString(writer));
    }

    @Test
    @Ignore
    public void shouldGenerateStatischObjectTypeLand() {
        StringWriter writer = new StringWriter();
        ObjectType object = objectTypeDao.getBySyncId(3041);
        generator.genereerElement(writer, report, object);
        Assert.assertEquals(getVerwachtResultaat("Land.jav"), toUnixString(writer));
    }

}
