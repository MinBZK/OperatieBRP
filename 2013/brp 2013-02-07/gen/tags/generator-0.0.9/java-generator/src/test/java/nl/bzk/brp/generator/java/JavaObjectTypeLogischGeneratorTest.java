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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaObjectTypeLogischGeneratorTest extends AbstractBmrTest {

    @Autowired(required = true)
    private JavaObjectTypeLogischGenerator generator;

    @Autowired(required = true)
    private ObjectTypeDao                  objectTypeDao;

    GenerationReport                       report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        report.setObjectType("objecttypes");
    }

    @Test
    public void shouldGenerateObjectTypeLogisch() {
        StringWriter writer = new StringWriter();
        ObjectType enumeratie = objectTypeDao.getBySyncId(3010);
        generator.genereerElement(writer, report, enumeratie);
        Assert.assertEquals(getVerwachtResultaat("Persoon.jav"), toUnixString(writer));
    }

}
