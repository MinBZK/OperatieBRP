/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrGroep;
import nl.bzk.brp.bijhouding.bericht.model.AbstractBmrObjecttype;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.model.StringElement;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;

/**
 * Testen voor ValideerGroepTest.
 */
public class ValideerGroepTest extends AbstractParserTest {

    public static final String CI_TESTGROEP = "CI_testgroep";

    @Test
    public void testValideer() throws ParseException {
        final Map<String, String> groepAttributen = new LinkedHashMap<>();
        groepAttributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), CI_TESTGROEP);
        final Map<String, String> objecttypeAttributen = new LinkedHashMap<>();
        objecttypeAttributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "TestObjecttype");
        objecttypeAttributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
        final StringElement waarde = new StringElement("waarde");
        final TestGroep groep = new TestGroep(groepAttributen, waarde);
        final TestGroepLijstElement groepLijstElement = new TestGroepLijstElement(groepAttributen, waarde);
        final List<TestGroepLijstElement> groepen = Collections.singletonList(groepLijstElement);
        final TestObjecttype objecttype = new TestObjecttype(objecttypeAttributen, groep, groepen);
        final List<MeldingElement> meldingen = objecttype.valideer();
        Assert.assertNotNull(meldingen);
        Assert.assertEquals(6, meldingen.size());
        final MeldingElement melding1 = meldingen.get(0);
        final MeldingElement melding2 = meldingen.get(1);
        final MeldingElement melding3 = meldingen.get(2);
        final MeldingElement melding4 = meldingen.get(3);
        final MeldingElement melding5 = meldingen.get(4);
        final MeldingElement melding6 = meldingen.get(5);
        Assert.assertEquals(CI_TESTGROEP, melding1.getReferentieId());
        Assert.assertEquals(CI_TESTGROEP, melding2.getReferentieId());
        Assert.assertEquals(CI_TESTGROEP, melding3.getReferentieId());
        Assert.assertEquals(CI_TESTGROEP, melding4.getReferentieId());
        assertEqualStringElement("R1260", melding1.getRegelCode());
        assertEqualStringElement("R1261", melding2.getRegelCode());
        assertEqualStringElement("R1263", melding3.getRegelCode());
        assertEqualStringElement("R1264", melding4.getRegelCode());
        assertEqualStringElement("R1257", melding5.getRegelCode());
        assertEqualStringElement("R1258", melding6.getRegelCode());
    }

    @XmlElement("testObjecttype")
    public static class TestObjecttype extends AbstractBmrObjecttype {

        private final TestGroep groep;
        @XmlChildList(listElementType = TestGroepLijstElement.class)
        private final List<TestGroepLijstElement> groepen;

        public TestObjecttype(final Map<String, String> attributen, final TestGroep groep, final List<TestGroepLijstElement> groepen) {
            super(attributen);
            this.groep = groep;
            this.groepen = initArrayList(groepen);
        }

        public TestGroep getGroep() {
            return groep;
        }

        public List<TestGroepLijstElement> getGroepen() {
            return Collections.unmodifiableList(groepen);
        }

        @Override
        protected List<MeldingElement> valideerInhoud() {
            final List<MeldingElement> result = new ArrayList<>();
            result.add(MeldingElement.getInstance(Regel.R1257, this));
            result.add(MeldingElement.getInstance(Regel.R1258, this));
            return result;
        }
    }

    @XmlElement("testGroep")
    public static class TestGroep extends AbstractBmrGroep {

        private final StringElement waarde;

        public TestGroep(final Map<String, String> attributen, final StringElement waarde) {
            super(attributen);
            this.waarde = waarde;
        }

        public StringElement getWaarde() {
            return waarde;
        }

        /**/
        @Override
        protected List<MeldingElement> valideerInhoud() {
            final List<MeldingElement> result = new ArrayList<>();
            result.add(MeldingElement.getInstance(Regel.R1260, this));
            result.add(MeldingElement.getInstance(Regel.R1261, this));
            return result;
        }
    }

    @XmlElement("testGroepLijstElement")
    public static class TestGroepLijstElement extends AbstractBmrGroep {

        private final StringElement waarde;

        public TestGroepLijstElement(final Map<String, String> attributen, final StringElement waarde) {
            super(attributen);
            this.waarde = waarde;
        }

        public StringElement getWaarde() {
            return waarde;
        }

        /**/
        @Override
        protected List<MeldingElement> valideerInhoud() {
            final List<MeldingElement> result = new ArrayList<>();
            result.add(MeldingElement.getInstance(Regel.R1263, this));
            result.add(MeldingElement.getInstance(Regel.R1264, this));
            return result;
        }
    }

    private BijhoudingVerzoekBericht maakDummyBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        return parser.parse(EenvoudigVoltrekkingHuwelijkNederlandParserTest.class.getResourceAsStream(EenvoudigVoltrekkingHuwelijkNederlandParserTest.EENVOUDIG_BIJHOUDING_BERICHT));
    }
}
