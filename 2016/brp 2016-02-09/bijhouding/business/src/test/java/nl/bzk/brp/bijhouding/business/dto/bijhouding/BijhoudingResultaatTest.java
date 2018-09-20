/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.dto.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.DocumentModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de {@link BijhoudingResultaat} klasse. */
public class BijhoudingResultaatTest {

    /**
     * Standaard dient de lijst met meldingen nooit <code>null</code> te zijn, dient de lijst met bijgehouden personen
     * ook niet <code>null</code> te zijn en dient de resultaat code initieel op "GOED" te staan.
     */
    @Test
    public void testConstructor() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        Assert.assertFalse(resultaat.bevatStoppendeFouten());
        Assert.assertNotNull(resultaat.getMeldingen());
        Assert.assertNotNull(resultaat.getBijgehoudenPersonen());
    }

    @Test
    public void testToevoegenEnkeleBijgehoudenPersoonBericht() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersoonToe(new PersoonBericht());
        Assert.assertEquals(1, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersoonToe(new PersoonBericht());
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenPersonen() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersonenToe(Arrays.<Persoon>asList(new PersoonBericht(), new PersoonBericht()));
        Assert.assertEquals(2, resultaat.getBijgehoudenPersonen().size());

        resultaat.voegPersonenToe(Arrays.<Persoon>asList(new PersoonBericht(), new PersoonBericht()));
        Assert.assertEquals(4, resultaat.getBijgehoudenPersonen().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenPersonenSortering() {
        // Zou eigenlijk een persoonModel in moeten
        PersoonBericht persoon1 = new PersoonBericht();
        persoon1.setCommunicatieID("Persoon1");
        PersoonIdentificatienummersGroepBericht pid1 = new PersoonIdentificatienummersGroepBericht();
        pid1.setBurgerservicenummer(new BurgerservicenummerAttribuut("1234"));
        persoon1.setIdentificatienummers(pid1);

        PersoonBericht persoon2 = new PersoonBericht();
        persoon2.setCommunicatieID("Persoon2");
        PersoonIdentificatienummersGroepBericht pid2 = new PersoonIdentificatienummersGroepBericht();
        pid2.setBurgerservicenummer(new BurgerservicenummerAttribuut("1230"));
        persoon2.setIdentificatienummers(pid2);

        PersoonBericht persoon3 = new PersoonBericht();
        persoon3.setCommunicatieID("Persoon3");
        PersoonIdentificatienummersGroepBericht pid3 = new PersoonIdentificatienummersGroepBericht();
        persoon3.setIdentificatienummers(pid3);

        PersoonBericht persoon4 = new PersoonBericht();
        persoon4.setCommunicatieID("Persoon4");


        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        // Zou eigenlijk een persoonModel in moeten
        resultaat.voegPersonenToe(
                Arrays.<Persoon>asList(persoon4, persoon3, persoon1, persoon2));


        Object[] personen = resultaat.getBijgehoudenPersonen().toArray();
        Assert.assertEquals("Persoon2", ((PersoonBericht) personen[0]).getCommunicatieID());
        Assert.assertEquals("Persoon1", ((PersoonBericht) personen[1]).getCommunicatieID());
        Assert.assertEquals("Persoon3", ((PersoonBericht) personen[2]).getCommunicatieID());
        Assert.assertEquals("Persoon4", ((PersoonBericht) personen[3]).getCommunicatieID());
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienPersonenLijstWordtAangepastVanBuiten() {
        List<Persoon> personen = new ArrayList<>();
        personen.add(new PersoonBericht());
        personen.add(new PersoonBericht());

        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        resultaat.voegPersonenToe(personen);
        resultaat.getBijgehoudenPersonen().add(new PersoonBericht());
    }

    @Test
    public void testGettersEnSetters() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Calendar now = Calendar.getInstance();
        resultaat.setTijdstipRegistratie(now.getTime());
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());

        now.add(Calendar.MONTH, 2);
        resultaat.setTijdstipRegistratie(now.getTime());
        Assert.assertEquals(now.getTime(), resultaat.getTijdstipRegistratie());

        resultaat.setTijdstipRegistratie(null);
        Assert.assertNull(resultaat.getTijdstipRegistratie());
    }

    @Test
    public void testToevoegenEnkeleBijgehoudenDocumentBerichten() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        SoortDocumentAttribuut soortDocument = StatischeObjecttypeBuilder.bouwSoortDocument("iets");
        DocumentModel document1 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document1, "iD", (long) 10);
        final Object bijgehoudenDocumenten = ReflectionTestUtils.getField(resultaat, "bijgehoudenDocumenten");
        ReflectionTestUtils.invokeMethod(bijgehoudenDocumenten, "put", "document1", document1);
        Assert.assertEquals(1, resultaat.getBijgehoudenDocumenten().size());

        DocumentModel document2 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document2, "iD", (long) 20);
        ReflectionTestUtils.invokeMethod(bijgehoudenDocumenten, "put", "document2", document2);
        Assert.assertEquals(2, resultaat.getBijgehoudenDocumenten().size());
    }

    @Test
    public void testToevoegenMeerdereBijgehoudenDocumenten() {
        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);

        Map<String, DocumentModel> documenten1 = new HashMap<>();
        SoortDocumentAttribuut soortDocument = StatischeObjecttypeBuilder.bouwSoortDocument("iets");
        DocumentModel document1 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document1, "iD", (long) 10);
        documenten1.put("document1", document1);

        DocumentModel document2 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document2, "iD", (long) 20);
        documenten1.put("document2", document2);

        resultaat.voegDocumentenToe(documenten1);
        Assert.assertEquals(2, resultaat.getBijgehoudenDocumenten().size());

        Map<String, DocumentModel> documenten2 = new HashMap<>();
        DocumentModel document3 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document3, "iD", (long) 30);
        documenten2.put("document3", document3);

        DocumentModel document4 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document4, "iD", (long) 40);
        documenten2.put("document4", document4);

        resultaat.voegDocumentenToe(documenten2);
        Assert.assertEquals(4, resultaat.getBijgehoudenDocumenten().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFoutIndienDocumentenLijstWordtAangepastVanBuiten() {
        Map<String, DocumentModel> documenten = new HashMap<>();
        SoortDocumentAttribuut soortDocument = StatischeObjecttypeBuilder.bouwSoortDocument("iets");
        DocumentModel document1 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document1, "iD", (long) 10);
        documenten.put("document1", document1);

        DocumentModel document2 = new DocumentModel(soortDocument);
        ReflectionTestUtils.setField(document2, "iD", (long) 20);
        documenten.put("document2", document2);

        BijhoudingResultaat resultaat = new BijhoudingResultaat(null);
        resultaat.voegDocumentenToe(documenten);
        resultaat.getBijgehoudenDocumenten().put("document3", new DocumentModel(soortDocument));
    }

}
