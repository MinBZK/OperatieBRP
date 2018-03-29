/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.writer;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import nl.bzk.brp.bijhouding.bericht.annotation.XmlChild;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;

/**
 * Test de reflectie utils voor het maken van bijhouding objecten uit het berichten model.
 */
public class BmrGroepMetaInfoTest {

    @Test
    public void testVolgordeXmlFields() throws WriteException {
        final TestGroep testGroep = new TestGroep(Collections.emptyMap(), new StringElement("waarde1"), new StringElement("waarde2"),
                new StringElement("waarde3"), new StringElement("waarde4"), new StringElement("waarde5"));
        final ObjectWriter objectWriter = new ObjectWriter(TestGroep.class);
        final StringWriter stringWriter = new StringWriter();
        objectWriter.write(testGroep, stringWriter);
        final String result = stringWriter.toString();
        assertEquals(getExpectedXmlString().replaceAll("\\r\\n", "\n"), result.replaceAll("\\r\\n", "\n"));
    }

    private String getExpectedXmlString() {
        return "<brp:TestGroep xmlns:brp=\"http://www.bzk.nl/brp/brp0200\">\n" + "    <brp:waarde1>waarde1</brp:waarde1>\n"
                + "    <brp:waarde2>waarde2</brp:waarde2>\n" + "    <brp:waarde3>waarde3</brp:waarde3>\n" + "    <brp:waarde4>waarde4</brp:waarde4>\n"
                + "    <brp:waarde5>waarde5</brp:waarde5>\n" + "</brp:TestGroep>";
    }

    public static abstract class AbstractTestGroep extends AbstractBmrGroep {
        @XmlChild(volgorde = 10)
        private final StringElement waarde1;
        @XmlChild(volgorde = 20)
        private final StringElement waarde5;

        public AbstractTestGroep(final Map<String, String> attributen, final StringElement waarde1, final StringElement waarde5) {
            super(attributen);
            this.waarde1 = waarde1;
            this.waarde5 = waarde5;
        }

        public StringElement getWaarde1() {
            return waarde1;
        }

        public StringElement getWaarde5() {
            return waarde5;
        }
    }

    @XmlElement("TestGroep")
    public static class TestGroep extends AbstractTestGroep {

        @XmlChild(volgorde = 11)
        private final StringElement waarde2;
        // bewust in andere volgorde dan constructor
        @XmlChild(volgorde = 13)
        private final StringElement waarde4;
        @XmlChild(volgorde = 12)
        private final StringElement waarde3;

        public TestGroep(final Map<String, String> attributen, final StringElement waarde1, final StringElement waarde2, final StringElement waarde3,
                final StringElement waarde4, final StringElement waarde5) {
            super(attributen, waarde1, waarde5);
            this.waarde2 = waarde2;
            this.waarde3 = waarde3;
            this.waarde4 = waarde4;
        }

        public StringElement getWaarde2() {
            return waarde2;
        }

        public StringElement getWaarde3() {
            return waarde3;
        }

        public StringElement getWaarde4() {
            return waarde4;
        }

        @Override
        protected List<MeldingElement> valideerInhoud() {
            return Collections.emptyList();
        }
    }
}
