/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.isc.esb.message.brp.generated.MvGeboorteVerzoekType;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;

public class MvGeboorteVerzoekBerichtTest {
    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("mvGeboorteVerzoekBericht.xml"));
        final BrpBericht bericht = factory.getBericht(berichtOrigineel);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = (MvGeboorteVerzoekBericht) bericht;
        assertEquals("MvGeboorteVerzoek", mvGeboorteVerzoekBericht.getBerichtType());
        assertEquals(null, mvGeboorteVerzoekBericht.getStartCyclus());
        System.out.println("Bericht: " + bericht);

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final BrpBericht parsed = factory.getBericht(formatted);
        System.out.println("Bericht.parsed: " + parsed);
        controleerFormatEnParse(bericht, parsed);
    }

    private void controleerFormatEnParse(final BrpBericht bericht, final BrpBericht parsed) throws IOException,
            ClassNotFoundException {
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());

        Assert.assertEquals(bericht, parsed);

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((BrpBericht) deserialized).getMessageId());
    }

    @Test
    public void testEquals() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("mvGeboorteVerzoekBericht.xml"));

        final BrpBericht mvGeboorteVerzoekBerichtOrigineel = factory.getBericht(berichtOrigineel);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBerichtKopie =
                (MvGeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBerichtObjectKopie =
                (MvGeboorteVerzoekBericht) mvGeboorteVerzoekBerichtOrigineel;
        final MvGeboorteAntwoordBericht mvGeboorteAntwoordBericht = new MvGeboorteAntwoordBericht();

        mvGeboorteVerzoekBerichtKopie.setMessageId(mvGeboorteVerzoekBerichtOrigineel.getMessageId());
        mvGeboorteVerzoekBerichtKopie.setCorrelationId(mvGeboorteVerzoekBerichtOrigineel.getCorrelationId());

        assertEquals(true, mvGeboorteVerzoekBerichtObjectKopie.equals(mvGeboorteVerzoekBerichtOrigineel));
        assertEquals(false, mvGeboorteVerzoekBerichtOrigineel.equals(mvGeboorteAntwoordBericht));
        assertEquals(true, mvGeboorteVerzoekBerichtKopie.equals(mvGeboorteVerzoekBerichtOrigineel));
        assertEquals(mvGeboorteVerzoekBerichtObjectKopie.hashCode(), mvGeboorteVerzoekBerichtOrigineel.hashCode());
        assertEquals(mvGeboorteVerzoekBerichtKopie.hashCode(), mvGeboorteVerzoekBerichtOrigineel.hashCode());
        assertEquals(mvGeboorteVerzoekBerichtKopie.toString(), mvGeboorteVerzoekBerichtOrigineel.toString());
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParse() throws Exception {

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        final SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        // maak een resolver aan zodat alle gerefereerde xsd's ook gevonden kunnen worden.
        schemaFactory.setResourceResolver(new LSResourceResolver());

        factory.setSchema(schemaFactory.newSchema(new Source[] { new StreamSource(BrpBerichtFactory.class
                .getResourceAsStream("/xsd/BRP_Berichten.xsd")), }));

        final Document document =
                factory.newDocumentBuilder().parse(
                        MvGeboorteVerzoekBerichtTest.class
                                .getResourceAsStream("mvGeboorteVerzoekBerichtSyntaxExceptionBericht.xml"));

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht();

        try {
            mvGeboorteVerzoekBericht.parse(document);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSettersEnGetters() throws Exception {
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht();
        final String BRP_GEMEENTE_CODE = "1904";
        final String LO3_GEMEENTE_CODE = "1903";
        final BrpPersoonslijst brpPL = new BrpPersoonslijstBuilder().build();
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal(BRP_GEMEENTE_CODE)));
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal(LO3_GEMEENTE_CODE)));
        mvGeboorteVerzoekBericht.setKindPersoonslijst(brpPL);
        mvGeboorteVerzoekBericht.setMoederPersoonslijst(brpPL);

        assertEquals(BRP_GEMEENTE_CODE, mvGeboorteVerzoekBericht.getBrpGemeente().getFormattedStringCode());
        assertEquals(LO3_GEMEENTE_CODE, mvGeboorteVerzoekBericht.getLo3Gemeente().getFormattedStringCode());
        assertEquals(brpPL, mvGeboorteVerzoekBericht.getKindPersoonslijst());
        assertEquals(brpPL, mvGeboorteVerzoekBericht.getMoederPersoonslijst());
    }

    @Test
    public void testConvenientConstructors() throws Exception {

        final BrpPersoonslijst kindPL = new BrpPersoonslijstBuilder().build();
        final BrpPersoonslijst moederPL = new BrpPersoonslijstBuilder().build();

        MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, moederPL);

        assertEquals(kindPL, mvGeboorteVerzoekBericht.getKindPersoonslijst());
        assertEquals(moederPL, mvGeboorteVerzoekBericht.getMoederPersoonslijst());

        final MvGeboorteVerzoekType mvGeboorteVerzoekType = new MvGeboorteVerzoekType();
        mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(mvGeboorteVerzoekType, kindPL, moederPL);

        assertEquals(kindPL, mvGeboorteVerzoekBericht.getKindPersoonslijst());
        assertEquals(moederPL, mvGeboorteVerzoekBericht.getMoederPersoonslijst());
    }

    @Test
    public void testBerichtSyntaxException() throws Exception {
        final String berichtOrigineel =
                IOUtils.toString(MvGeboorteVerzoekBerichtTest.class
                        .getResourceAsStream("mvGeboorteVerzoekBerichtSyntaxExceptionBericht.xml"));

        final BrpBericht brpBericht = factory.getBericht(berichtOrigineel);
        Assert.assertTrue(brpBericht instanceof OngeldigBericht);
    }

    @Test(expected = BerichtInhoudException.class)
    public void testKindPersoonslijstNullPointerException() throws Exception {

        final BrpPersoonslijst kindPL = new BrpPersoonslijstBuilder().build();

        final MvGeboorteVerzoekType mvGeboorteVerzoekType = new MvGeboorteVerzoekType();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht =
                new MvGeboorteVerzoekBericht(mvGeboorteVerzoekType, kindPL, null);

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    public void testKindPersoonslijstVoornamenNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        kindPL.getVoornaamStapels().remove(0);
        final BrpStapel<BrpVoornaamInhoud> voornamenStapel = maakVoornamenStapel(null);

        kindPL.getVoornaamStapels().add(voornamenStapel);

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, null);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    public void testKindPersoonslijstGeslachtsaanduidingNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);

        final BrpPersoonslijst kindPLOrigineel = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPLOrigineel);

        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingStapel = maakGeslachtsaanduidingStapel(null);

        builder.geslachtsaanduidingStapel(geslachtsaanduidingStapel);

        final BrpPersoonslijst kindPL = builder.build();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, null);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    public void testKindPersoonslijstGeslachtscomponentenNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);

        final BrpPersoonslijst kindPLOrigineel = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();
        kindPLOrigineel.getGeslachtsnaamcomponentStapels().remove(0);
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPLOrigineel);

        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsaanduidingStapel =
                maakGeslachtscomponentenStapel("Janssen");

        builder.geslachtsnaamcomponentStapel(geslachtsaanduidingStapel);

        final BrpPersoonslijst kindPL = builder.build();
        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, null);
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testBerichtMetKindPersoonslijst() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, null);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        final String geformatteerdMvGeboorteVerzoekVerzoekbericht = mvGeboorteVerzoekBericht.format();
        System.out.println(geformatteerdMvGeboorteVerzoekVerzoekbericht);
    }

    @Test(expected = BerichtInhoudException.class)
    public void testMoederPersoonslijstNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();
        final BrpPersoonslijst moederPL = new BrpPersoonslijstBuilder().build();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        mvGeboorteVerzoekBericht.format();
    }

    @Test(expected = BerichtInhoudException.class)
    public void testMoederPersoonslijstIdentificatienummersNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPL);
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                maakIdentificatienummersStapel(null, null);

        builder.identificatienummersStapel(identificatienummersStapel);
        final BrpPersoonslijst moederPL = builder.build();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    public void testMoederPersoonslijstGeboorteNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPL);
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = maakGeboorteStapel(null, null, null, null);
        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                maakIdentificatienummersStapel(null, Long.valueOf("123456789"));

        builder.geboorteStapel(geboorteStapel);
        builder.identificatienummersStapel(identificatienummersStapel);
        final BrpPersoonslijst moederPL = builder.build();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    @Ignore
    public void testMoederPersoonslijstSamengesteldenaamNullPointerException() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPLOrigineel = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();
        final BrpPersoonslijst kindPL = new BrpPersoonslijstBuilder(kindPLOrigineel).build();

        kindPL.getGeslachtsnaamcomponentStapels().remove(0);

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPL);
        final BrpStapel<BrpGeslachtsnaamcomponentInhoud> geslachtsaanduidingStapel =
                maakGeslachtscomponentenStapel("Janssen");

        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                maakIdentificatienummersStapel(null, Long.valueOf("123456789"));

        builder.geslachtsnaamcomponentStapel(geslachtsaanduidingStapel);
        builder.identificatienummersStapel(identificatienummersStapel);
        final BrpPersoonslijst moederPL = builder.build();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht =
                new MvGeboorteVerzoekBericht(kindPLOrigineel, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        try {
            mvGeboorteVerzoekBericht.format();
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testBerichtMetMoederPersoonslijst() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst moederPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(null, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        final String geformatteerdMvGeboorteVerzoekVerzoekbericht = mvGeboorteVerzoekBericht.format();
        System.out.println(geformatteerdMvGeboorteVerzoekVerzoekbericht);
    }

    @Test
    public void testBerichtMetKindEnMoederPersoonslijst() throws Exception {

        final String berichtOrigineel =
                IOUtils.toString(NotificatieVerzoekBerichtTest.class
                        .getResourceAsStream("geboorteVerzoekBericht.xml"));

        final GeboorteVerzoekBericht geboorteVerzoekBerichtOrigineel =
                (GeboorteVerzoekBericht) factory.getBericht(berichtOrigineel);
        final BrpPersoonslijst kindPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();
        BrpPersoonslijst moederPL = geboorteVerzoekBerichtOrigineel.getBrpPersoonslijst();

        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(kindPL);

        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepLijst =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();

        final BrpIdentificatienummersInhoud identificatienummersInhoud =
                new BrpIdentificatienummersInhoud(null, Long.valueOf("123456789"));

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpIdentificatienummersInhoud> identificatienummersGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(identificatienummersInhoud, historie, null, null, null);

        identificatienummersGroepLijst.add(identificatienummersGroep);

        final BrpStapel<BrpIdentificatienummersInhoud> identificatienummersStapel =
                new BrpStapel<BrpIdentificatienummersInhoud>(identificatienummersGroepLijst);

        builder.identificatienummersStapel(identificatienummersStapel);
        moederPL = builder.build();

        final MvGeboorteVerzoekBericht mvGeboorteVerzoekBericht = new MvGeboorteVerzoekBericht(kindPL, moederPL);
        mvGeboorteVerzoekBericht.setLo3Gemeente(new BrpGemeenteCode(new BigDecimal("1903")));
        mvGeboorteVerzoekBericht.setBrpGemeente(new BrpGemeenteCode(new BigDecimal("1904")));

        final String geformatteerdMvGeboorteVerzoekVerzoekbericht = mvGeboorteVerzoekBericht.format();
        System.out.println(geformatteerdMvGeboorteVerzoekVerzoekbericht);
    }

    private BrpStapel<BrpIdentificatienummersInhoud>
            maakIdentificatienummersStapel(final Long aNummer, final Long bsn) {
        final List<BrpGroep<BrpIdentificatienummersInhoud>> identificatienummersGroepLijst =
                new ArrayList<BrpGroep<BrpIdentificatienummersInhoud>>();

        final BrpIdentificatienummersInhoud identificatienummersInhoud =
                new BrpIdentificatienummersInhoud(aNummer, bsn);

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpIdentificatienummersInhoud> identificatienummersGroep =
                new BrpGroep<BrpIdentificatienummersInhoud>(identificatienummersInhoud, historie, null, null, null);

        identificatienummersGroepLijst.add(identificatienummersGroep);

        return new BrpStapel<BrpIdentificatienummersInhoud>(identificatienummersGroepLijst);
    }

    private BrpStapel<BrpGeboorteInhoud> maakGeboorteStapel(
            final BrpDatum geboorteDatum,
            final BrpGemeenteCode gemeenteCode,
            final BrpPlaatsCode plaatsCode,
            final BrpLandCode landCode) {
        final List<BrpGroep<BrpGeboorteInhoud>> geboorteGroepLijst = new ArrayList<BrpGroep<BrpGeboorteInhoud>>();

        final BrpGeboorteInhoud geboorteInhoud =
                new BrpGeboorteInhoud(geboorteDatum, gemeenteCode, plaatsCode, null, null, landCode, null);

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpGeboorteInhoud> geboorteGroep =
                new BrpGroep<BrpGeboorteInhoud>(geboorteInhoud, historie, null, null, null);

        geboorteGroepLijst.add(geboorteGroep);

        return new BrpStapel<BrpGeboorteInhoud>(geboorteGroepLijst);
    }

    private BrpStapel<BrpVoornaamInhoud> maakVoornamenStapel(final String voornamen) {
        final List<BrpGroep<BrpVoornaamInhoud>> voornamenGroepLijst = new ArrayList<BrpGroep<BrpVoornaamInhoud>>();

        final BrpVoornaamInhoud voornamenInhoud = new BrpVoornaamInhoud(voornamen, 1);

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpVoornaamInhoud> voornamenGroep =
                new BrpGroep<BrpVoornaamInhoud>(voornamenInhoud, historie, null, null, null);

        voornamenGroepLijst.add(voornamenGroep);

        return new BrpStapel<BrpVoornaamInhoud>(voornamenGroepLijst);
    }

    private BrpStapel<BrpGeslachtsaanduidingInhoud> maakGeslachtsaanduidingStapel(
            final BrpGeslachtsaanduidingCode geslachtsaanduidingCode) {
        final List<BrpGroep<BrpGeslachtsaanduidingInhoud>> geslachtsaanduidingGroepLijst =
                new ArrayList<BrpGroep<BrpGeslachtsaanduidingInhoud>>();

        final BrpGeslachtsaanduidingInhoud geslachtsaanduidingInhoud =
                new BrpGeslachtsaanduidingInhoud(geslachtsaanduidingCode);

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpGeslachtsaanduidingInhoud> geslachtsaanduidingGroep =
                new BrpGroep<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingInhoud, historie, null, null, null);

        geslachtsaanduidingGroepLijst.add(geslachtsaanduidingGroep);

        return new BrpStapel<BrpGeslachtsaanduidingInhoud>(geslachtsaanduidingGroepLijst);
    }

    private BrpStapel<BrpGeslachtsnaamcomponentInhoud> maakGeslachtscomponentenStapel(final String geslachtsnaam) {
        final List<BrpGroep<BrpGeslachtsnaamcomponentInhoud>> geslachtsaanduidingGroepLijst =
                new ArrayList<BrpGroep<BrpGeslachtsnaamcomponentInhoud>>();

        final BrpGeslachtsnaamcomponentInhoud geslachtsaanduidingInhoud =
                new BrpGeslachtsnaamcomponentInhoud(null, null, geslachtsnaam, null, null, 1);

        final BrpHistorie historie = new BrpHistorie(null, null, new BrpDatumTijd(new Date()), null);

        final BrpGroep<BrpGeslachtsnaamcomponentInhoud> geslachtsaanduidingGroep =
                new BrpGroep<BrpGeslachtsnaamcomponentInhoud>(geslachtsaanduidingInhoud, historie, null, null, null);

        geslachtsaanduidingGroepLijst.add(geslachtsaanduidingGroep);

        return new BrpStapel<BrpGeslachtsnaamcomponentInhoud>(geslachtsaanduidingGroepLijst);
    }
}
