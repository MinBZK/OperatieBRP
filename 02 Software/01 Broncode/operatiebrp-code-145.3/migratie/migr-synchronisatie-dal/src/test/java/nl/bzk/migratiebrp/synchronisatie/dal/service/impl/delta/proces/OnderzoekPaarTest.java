/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
public class OnderzoekPaarTest {

    private static final Partij PARTIJ = new Partij("partij", "000001");
    private static final Persoon PERSOON = new Persoon(SoortPersoon.INGESCHREVENE);
    private Onderzoek onderzoek;


    @BeforeClass
    public static void init() {
        SynchronisatieLogging.init();
    }


    @Before
    public void setup() {
        onderzoek = new Onderzoek(PARTIJ, PERSOON);
        onderzoek.setDatumAanvang(19990101);
        onderzoek.setOmschrijving("onderzoek 1");
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, Element.AANGEVER_CODE);
        onderzoek.addGegevenInOnderzoek(gio);
    }

    @Test
    public void testConstructor() {
        Onderzoek onderzoek = new Onderzoek(PARTIJ, PERSOON);
        Onderzoek onderzoek2 = new Onderzoek(PARTIJ, PERSOON);

        final OnderzoekHistorie onderzoekHistorie = new OnderzoekHistorie(19990101, StatusOnderzoek.IN_UITVOERING, onderzoek);
        final OnderzoekHistorie onderzoekHistorie2 = new OnderzoekHistorie(20010101, StatusOnderzoek.IN_UITVOERING, onderzoek2);
        onderzoekHistorie.setOmschrijving("onderzoek 1");
        onderzoekHistorie2.setOmschrijving("onderzoek 2");

        onderzoek.addOnderzoekHistorie(onderzoekHistorie);
        onderzoek2.addOnderzoekHistorie(onderzoekHistorie2);

        OnderzoekPaar paar = new OnderzoekPaar(null, null);
        assertNull(paar.getBestaandOnderzoek());
        assertNull(paar.getNieuwOnderzoek());
        assertEquals(OnderzoekDeltaStatus.NIEUW, paar.getStatus());
        paar = new OnderzoekPaar(onderzoek, null);
        assertNotNull(paar.getBestaandOnderzoek());
        assertNull(paar.getNieuwOnderzoek());
        assertEquals(OnderzoekDeltaStatus.VERWIJDERD, paar.getStatus());
        paar = new OnderzoekPaar(onderzoek, onderzoek);
        assertEquals(OnderzoekDeltaStatus.ONGEWIJZIGD, paar.getStatus());
        paar = new OnderzoekPaar(onderzoek, onderzoek2);
        assertEquals(OnderzoekDeltaStatus.GEWIJZIGD, paar.getStatus());
        assertEquals("Onderzoek GEWIJZIGD", paar.toString());
        assertNotNull(paar.getBestaandOnderzoek());
        assertNotNull(paar.getNieuwOnderzoek());

    }

    @Test
    public void testIsBijhoudingActueel_bestaand_actueel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek, null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertTrue((boolean) testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_administratief() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_IDENTITEIT);
        gio.setEntiteitOfVoorkomen(new Document(new SoortDocument("admin", "admin"), PARTIJ));
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek, null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean) testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_FormeleHistorie_actueel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PartijHistorie ph = new PartijHistorie(new Partij("1", "000001"), new Timestamp(Calendar.getInstance().getTimeInMillis()), 20010101, Boolean.FALSE, "naam");
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gio.setEntiteitOfVoorkomen(ph);
        assertTrue(ph.isActueel());
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek, null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertTrue((boolean) testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_FormeleHistorie_vervallen() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        PartijHistorie ph = new PartijHistorie(new Partij("1", "000001"), timestamp, 20010101, Boolean.FALSE, "naam");
        ph.setDatumTijdVerval(timestamp);
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gio.setEntiteitOfVoorkomen(ph);
        assertFalse(ph.isActueel());
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek, null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean) testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_Lo3Historie() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        onderzoek.setVoortgekomenUitNietActueelVoorkomen(true);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek, null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean) testMethod.invoke(paar));
    }

}
