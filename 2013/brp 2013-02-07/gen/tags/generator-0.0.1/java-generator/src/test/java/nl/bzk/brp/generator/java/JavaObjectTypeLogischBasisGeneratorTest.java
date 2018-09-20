/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.StringWriter;

import junit.framework.Assert;
import nl.bzk.brp.generator.java.domein.GenerationReport;
import nl.bzk.brp.metaregister.dataaccess.ObjectTypeDao;
import nl.bzk.brp.metaregister.model.ObjectType;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaObjectTypeLogischBasisGeneratorTest extends AbstractBmrTest {

    @Autowired(required = true)
    private JavaObjectTypeLogischBasisGenerator generator;

    @Autowired(required = true)
    private JavaObjectTypeLogischGenerator generator2;

    @Autowired(required = true)
    private ObjectTypeDao objectTypeDao;

    GenerationReport report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        report.setObjectType("objecttypes");
    }

    @Test
    @Ignore //TODO: Friso er mist nog Soort en weet niet waar dit vandaan moet komen. De rest lijkt OK.
    public void shouldGenerateObjectTypeLogischBasis() {
        StringWriter writer = new StringWriter();
        ObjectType enumeratie = objectTypeDao.getBySyncId(3010);
        generator.genereerElement(writer, report, enumeratie);
        Assert.assertEquals(getVerwachtResultaat("PersoonBasis.jav"), toUnixString(writer));
    }

}
