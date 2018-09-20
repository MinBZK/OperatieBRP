/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3BerichtFactory;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Builder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;

import org.junit.Assert;
import org.junit.Test;

public class Tb02TransseksualiteitBerichtTest {

    private static final String DOEL_GEMEENTE = "1904";
    private static final String BRON_GEMEENTE = "1905";
    private static final String AANTAL = "001";
    private static final String AKTENUMMER = "A130433";
    private static final Lo3Datum INGANGSDATUM = new Lo3Datum(20130115);

    private final Lo3BerichtFactory factory = new Lo3BerichtFactory();

    @Test
    public void testParseEnFormat() throws Exception {
        final Lo3Persoonslijst pl = maakLo3Persoonslijst();
        final Tb02Bericht tb02Bericht = maakTb02Bericht(pl);
        assertEquals("Tb02", tb02Bericht.getBerichtType());
        assertEquals("uc309", tb02Bericht.getStartCyclus());

        System.out.println("Bericht: " + tb02Bericht);

        final String formatted = tb02Bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final Lo3Bericht parsed = factory.getBericht(formatted);
        controleerFormatEnParse(tb02Bericht, parsed);
    }

    @Test(expected = IllegalStateException.class)
    public void testFormatZonderPersoonslijst() throws Exception {
        final Tb02Bericht tb02Bericht = maakTb02Bericht(null);
        assertEquals("Tb02", tb02Bericht.getBerichtType());
        assertEquals("uc309", tb02Bericht.getStartCyclus());

        System.out.println("Bericht: " + tb02Bericht);

        try {
            tb02Bericht.format();
        } catch (final IllegalStateException exception) {
            throw exception;
        }
    }

    @Test(expected = BerichtInhoudException.class)
    public void testParseZonderPersoonslijst() throws Exception {
        final Tb02Bericht tb02Bericht = maakTb02Bericht(null);
        assertEquals("Tb02", tb02Bericht.getBerichtType());
        assertEquals("uc309", tb02Bericht.getStartCyclus());

        System.out.println("Bericht: " + tb02Bericht);

        try {
            tb02Bericht.parseInhoud(null);
        } catch (final BerichtInhoudException exception) {
            throw exception;
        }
    }

    @Test
    public void testSerialisatieZonderPersoonslijst() throws Exception {
        final Tb02Bericht tb02Bericht = maakTb02Bericht(null);
        assertEquals("Tb02", tb02Bericht.getBerichtType());
        assertEquals("uc309", tb02Bericht.getStartCyclus());

        System.out.println("Bericht: " + tb02Bericht);

        final Object deserialized = testSerialisatie(tb02Bericht);
        Assert.assertEquals(tb02Bericht.getMessageId(), ((Lo3Bericht) deserialized).getMessageId());
    }

    private Tb02Bericht maakTb02Bericht(final Lo3Persoonslijst lo3PersoonsLijst) {
        final Tb02Bericht tb02Bericht =
                new Tb02TransseksualiteitBericht(BRON_GEMEENTE, AKTENUMMER, new Lo3Datum(20130115));
        tb02Bericht.setLo3Persoonslijst(lo3PersoonsLijst);
        tb02Bericht.setHeader(Lo3HeaderVeld.AANTAL, AANTAL);
        tb02Bericht.setHeader(Lo3HeaderVeld.AKTENUMMER, AKTENUMMER);
        tb02Bericht.setBronGemeente(BRON_GEMEENTE);
        tb02Bericht.setDoelGemeente(DOEL_GEMEENTE);
        return tb02Bericht;
    }

    @Test
    public void testGettersEnSetters() throws Exception {
        final Lo3Persoonslijst pl = maakLo3Persoonslijst();
        final Tb02Bericht tb02Bericht = maakTb02Bericht(pl);
        ((Tb02TransseksualiteitBericht) tb02Bericht).setAktenummer(AKTENUMMER);
        ((Tb02TransseksualiteitBericht) tb02Bericht).setIngangsdatumGeldigheid(INGANGSDATUM);
        ((Tb02TransseksualiteitBericht) tb02Bericht).setRegistratieGemeente(BRON_GEMEENTE);
        assertEquals("Bericht type onjuist.", "Tb02", tb02Bericht.getBerichtType());
        assertEquals("Start cyclus onjuist.", "uc309", tb02Bericht.getStartCyclus());
        assertEquals("Persoonslijst ongelijk.", pl, tb02Bericht.getLo3Persoonslijst());
        assertEquals("Header aantal ongelijk.", AANTAL, tb02Bericht.getHeader(Lo3HeaderVeld.AANTAL));
        assertEquals("Header Aktenummer ongelijk.", AKTENUMMER, tb02Bericht.getHeader(Lo3HeaderVeld.AKTENUMMER));
        assertEquals("Registratiegemeente ongelijk.", BRON_GEMEENTE,
                ((Tb02TransseksualiteitBericht) tb02Bericht).getRegistratieGemeente());
        assertEquals("Aktenummer ongelijk.", AKTENUMMER, ((Tb02TransseksualiteitBericht) tb02Bericht).getAktenummer());
        assertEquals("Aktenummer ongelijk.", INGANGSDATUM,
                ((Tb02TransseksualiteitBericht) tb02Bericht).getIngangsdatumGeldigheid());
        assertEquals("Brongemeente ongelijk", BRON_GEMEENTE, tb02Bericht.getBronGemeente());
        assertEquals("Doelgemeente ongelijk", DOEL_GEMEENTE, tb02Bericht.getDoelGemeente());
    }

    @Test
    public void testEquals() throws Exception {
        final Lo3Persoonslijst pl = maakLo3Persoonslijst();
        final Tb02Bericht tb02BerichtOrigineel = maakTb02Bericht(pl);
        final Tb02Bericht tb02BerichtAfwijkend = maakTb02Bericht(maakLo3Persoonslijst());
        final Tb02Bericht tb02BerichtKopie = tb02BerichtOrigineel;

        tb02BerichtKopie.setMessageId(tb02BerichtOrigineel.getMessageId());
        tb02BerichtKopie.setCorrelationId(tb02BerichtOrigineel.getCorrelationId());

        assertEquals(true, tb02BerichtKopie.equals(tb02BerichtOrigineel));
        assertEquals(false, tb02BerichtOrigineel.equals(tb02BerichtAfwijkend));
        assertEquals(false, tb02BerichtOrigineel.equals(new Tb01Bericht()));
        assertEquals(true, tb02BerichtKopie.equals(tb02BerichtOrigineel));
        assertEquals(tb02BerichtKopie.hashCode(), tb02BerichtOrigineel.hashCode());
        assertEquals(tb02BerichtKopie.hashCode(), tb02BerichtOrigineel.hashCode());
        assertEquals(tb02BerichtKopie.toString(), tb02BerichtOrigineel.toString());
    }

    private void controleerFormatEnParse(final Lo3Bericht bericht, final Lo3Bericht parsed) throws IOException,
            ClassNotFoundException {
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());
        parsed.setBronGemeente(bericht.getBronGemeente());
        parsed.setDoelGemeente(bericht.getDoelGemeente());

        Assert.assertEquals(bericht, parsed);

        final Object deserialized = testSerialisatie(parsed);

        Assert.assertEquals(bericht.getMessageId(), ((Lo3Bericht) deserialized).getMessageId());
    }

    private Object testSerialisatie(final Lo3Bericht parsed) throws IOException, ClassNotFoundException {
        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);
        return deserialized;
    }

    private static Lo3Persoonslijst maakLo3Persoonslijst() {
        final Lo3PersoonInhoud lo3PersoonInhoud = maakLo3PersoonInhoud();
        final Lo3Historie historie = Lo3Builder.createLo3Historie("S", 20121101, 20121103);
        final Lo3Categorie<Lo3PersoonInhoud> persoon1 =
                new Lo3Categorie<Lo3PersoonInhoud>(lo3PersoonInhoud, null, historie, new Lo3Herkomst(
                        Lo3CategorieEnum.CATEGORIE_01, 0, 0));
        @SuppressWarnings("unchecked")
        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel = new Lo3Stapel<Lo3PersoonInhoud>(Arrays.asList(persoon1));
        return new Lo3PersoonslijstBuilder().persoonStapel(lo3PersoonStapel).ouder1Stapel(null).ouder2Stapel(null)
                .build();
    }

    private static Lo3PersoonInhoud maakLo3PersoonInhoud() {
        final Long aNummer = 2349326344L;
        final Long burgerservicenummer = 123456789L;
        final String voornamen = "Henk Jan";
        final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode = null;
        final String voorvoegselGeslachtsnaam = "van";
        final String geslachtsnaam = "Dalen";
        final Lo3Datum geboortedatum = new Lo3Datum(20121024);
        final Lo3GemeenteCode geboorteGemeenteCode = new Lo3GemeenteCode("1234");
        final Lo3LandCode geboorteLandCode = new Lo3LandCode("6030");
        final Lo3Geslachtsaanduiding geslachtsaanduiding = new Lo3Geslachtsaanduiding("M");
        final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode = new Lo3AanduidingNaamgebruikCode("E");
        final Long vorigANummer = null;
        final Long volgendANummer = null;

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(aNummer, burgerservicenummer, voornamen, adellijkeTitelPredikaatCode,
                        voorvoegselGeslachtsnaam, geslachtsnaam, geboortedatum, geboorteGemeenteCode,
                        geboorteLandCode, geslachtsaanduiding, aanduidingNaamgebruikCode, vorigANummer,
                        volgendANummer);
        return persoonInhoud;
    }
}
