/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Calendar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 */
public class OnderzoekPaarTest {

    private Onderzoek onderzoek;
    private Onderzoek onderzoek2;



    @BeforeClass
    public static void init(){
        SynchronisatieLogging.init();
    }


    @Before
    public void setup(){

        onderzoek = new Onderzoek();
        onderzoek.setDatumAanvang(19990101);
        onderzoek.setOmschrijving("onderzoek 1");
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek, Element.AANGEVER_CODE);
        onderzoek.addGegevenInOnderzoek(gio);
        onderzoek2 = new Onderzoek();
        onderzoek2.setDatumAanvang(20010101);
        onderzoek.setOmschrijving("onderzoek 2");
        GegevenInOnderzoek gio2 = new GegevenInOnderzoek(onderzoek2, Element.AANGEVER_CODE);
        onderzoek.addGegevenInOnderzoek(gio2);

    }

    @Test
    public void testConstructor(){
        Onderzoek onderzoek = new Onderzoek();
        onderzoek.setDatumAanvang(19990101);
        onderzoek.setOmschrijving("onderzoek 1");
        Onderzoek onderzoek2 = new Onderzoek();
        onderzoek2.setDatumAanvang(20010101);
        onderzoek.setOmschrijving("onderzoek 2");
        OnderzoekPaar paar = new OnderzoekPaar(null,null);
        assertNull(paar.getBestaandOnderzoek());
        assertNull(paar.getNieuwOnderzoek());
        assertEquals(OnderzoekDeltaStatus.NIEUW,paar.getStatus());
        paar = new OnderzoekPaar(onderzoek,null);
        assertNotNull(paar.getBestaandOnderzoek());
        assertNull(paar.getNieuwOnderzoek());
        assertEquals(OnderzoekDeltaStatus.VERWIJDERD,paar.getStatus());
        paar = new OnderzoekPaar(onderzoek,onderzoek);
        assertEquals(OnderzoekDeltaStatus.ONGEWIJZIGD,paar.getStatus());
        paar = new OnderzoekPaar(onderzoek,onderzoek2);
        assertEquals(OnderzoekDeltaStatus.GEWIJZIGD,paar.getStatus());
        assertEquals("Onderzoek GEWIJZIGD",paar.toString());
        assertNotNull(paar.getBestaandOnderzoek());
        assertNotNull(paar.getNieuwOnderzoek());

    }

    @Test
    public void testIsBijhoudingActueel_bestaand_actueel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek,null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertTrue((boolean)testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_administratief() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek,Element.DOCUMENT_DOCUMENT);
        gio.setObject(new Document(new SoortDocument("admin","admin")));
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek,null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean)testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_FormeleHistorie_actueel() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PartijHistorie ph = new PartijHistorie(new Partij("1",1), new Timestamp(Calendar.getInstance().getTimeInMillis()),20010101,Boolean.FALSE,"naam");
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek,Element.DOCUMENT_DOCUMENT);
        gio.setObject(ph);
        assertTrue(ph.isActueel());
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek,null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertTrue((boolean)testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_FormeleHistorie_vervallen() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        PartijHistorie ph = new PartijHistorie(new Partij("1",1), timestamp,20010101,Boolean.FALSE,"naam");
        ph.setDatumTijdVerval(timestamp);
        GegevenInOnderzoek gio = new GegevenInOnderzoek(onderzoek,Element.DOCUMENT_DOCUMENT);
        gio.setObject(ph);
        assertFalse(ph.isActueel());
        onderzoek.addGegevenInOnderzoek(gio);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek,null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean)testMethod.invoke(paar));
    }

    @Test
    public void testIsBijhoudingActueel_Lo3Historie() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        onderzoek.setVoortgekomenUitNietActueelVoorkomen(true);
        OnderzoekPaar paar = new OnderzoekPaar(onderzoek,null);
        final Method testMethod = OnderzoekPaar.class.getDeclaredMethod("isBijhoudingActueel");
        testMethod.setAccessible(true);
        assertFalse((boolean)testMethod.invoke(paar));
    }

}