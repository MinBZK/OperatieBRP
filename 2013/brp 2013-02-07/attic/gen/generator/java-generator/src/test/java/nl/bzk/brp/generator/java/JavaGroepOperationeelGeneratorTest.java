/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.StringWriter;

import junit.framework.Assert;
import nl.bzk.brp.generator.GenerationReport;
import nl.bzk.brp.metaregister.dataaccess.GroepDao;
import nl.bzk.brp.metaregister.model.Groep;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class JavaGroepOperationeelGeneratorTest extends AbstractBmrTest {

    @Autowired(required = true)
    private JavaGroepOperationeelGenerator generator;

    @Autowired(required = true)
    private GroepDao groepDao;

    GenerationReport report = new GenerationReport();

    @Before
    public void setUp() throws Exception {
        report.setObjectType("Groepen Operationeel");
    }

    @Test
    public void genereerGroepNormaal() {
        StringWriter writer = new StringWriter();
        Groep groep = groepDao.getBySyncId(3211);
        generator.genereerElement(writer, report, groep);
        Assert.assertEquals(getVerwachtResultaat("AbstractBetrokkenheidOuderlijkGezagGroep.jav"), toUnixString(writer));
    }

    @Test
    public void genereerGroepStandaard() {
        StringWriter writer = new StringWriter();
        Groep groep = groepDao.getBySyncId(6063);
        generator.genereerElement(writer, report, groep);
        Assert.assertEquals(getVerwachtResultaat("AbstractPersoonAdresStandaardGroep.jav"), toUnixString(writer));
    }

    @Test
    public void genereerGroepMetDynamischeAttributen() {
        StringWriter writer = new StringWriter();
        Groep groep = groepDao.getBySyncId(3521);
        generator.genereerElement(writer, report, groep);
        Assert.assertEquals(getVerwachtResultaat("AbstractPersoonInschrijvingGroep.jav"), toUnixString(writer));
    }

}
