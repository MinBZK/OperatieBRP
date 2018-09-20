/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3;

import java.util.regex.Pattern;

/**
 * Enumeratie van alle LO3 elementen.
 * 
 * 
 * 
 */
public enum Lo3ElementEnum {

    /**
     * A-Nummer.
     */
    ELEMENT_0110("0110", Type.NUMERIEK, 10),
    /**
     * Burgerservicenummer.
     */
    ELEMENT_0120("0120", Type.NUMERIEK, 9),
    /**
     * Voornamen.
     */
    ELEMENT_0210("0210", Type.ALPHANUMERIEK, 1, 200, true),
    /**
     * Adellijke titel/predikaat.
     */
    ELEMENT_0220("0220", Type.ALPHANUMERIEK, 1, 2, false),
    /**
     * Voorvoegsel geslachtsnaam.
     */
    ELEMENT_0230("0230", Type.ALPHANUMERIEK, 1, 10, false),
    /**
     * Geslachtsnaam.
     */
    ELEMENT_0240("0240", Type.ALPHANUMERIEK, 1, 200, true),
    /**
     * Geboortedatum.
     */
    ELEMENT_0310("0310", Type.NUMERIEK, 8),
    /**
     * Geboorteplaats.
     */
    ELEMENT_0320("0320", Type.ALPHANUMERIEK, 1, 40, true),
    /**
     * Geboorteland.
     */
    ELEMENT_0330("0330", Type.NUMERIEK, 4),
    /**
     * Geslachtsaanduiding.
     */
    ELEMENT_0410("0410", Type.ALPHANUMERIEK, 1),
    /**
     * Nationaliteit.
     */
    ELEMENT_0510("0510", Type.NUMERIEK, 4),
    /**
     * Datum huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0610("0610", Type.NUMERIEK, 8),
    /**
     * Plaats huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0620("0620", Type.ALPHANUMERIEK, 1, 40, true),
    /**
     * Land huwelijkssluiting/aangaan geregistreerd partnerschap.
     */
    ELEMENT_0630("0630", Type.NUMERIEK, 4),
    /**
     * Datum ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0710("0710", Type.NUMERIEK, 8),
    /**
     * Plaats ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0720("0720", Type.ALPHANUMERIEK, 1, 40, true),
    /**
     * Land ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0730("0730", Type.NUMERIEK, 4),
    /**
     * Reden ontbinding huwelijkssluiting/geregistreerd partnerschap.
     */
    ELEMENT_0740("0740", Type.ALPHANUMERIEK, 1),
    /**
     * Datum overlijden.
     */
    ELEMENT_0810("0810", Type.NUMERIEK, 8),
    /**
     * Plaats overlijden.
     */
    ELEMENT_0820("0820", Type.ALPHANUMERIEK, 1, 40, true),
    /**
     * Land overlijden.
     */
    ELEMENT_0830("0830", Type.NUMERIEK, 4),
    /**
     * Gemeente van inschrijving.
     */
    ELEMENT_0910("0910", Type.NUMERIEK, 4),
    /**
     * Datum inschrijving.
     */
    ELEMENT_0920("0920", Type.NUMERIEK, 8),
    /**
     * Functie adres.
     */
    ELEMENT_1010("1010", Type.ALPHANUMERIEK, 1),
    /**
     * Gemeentedeel.
     */
    ELEMENT_1020("1020", Type.ALPHANUMERIEK, 1, 24, true),
    /**
     * Datum aanvang adreshouding.
     */
    ELEMENT_1030("1030", Type.NUMERIEK, 8),
    /**
     * Straatnaam.
     */
    ELEMENT_1110("1110", Type.ALPHANUMERIEK, 1, 24, true),
    /**
     * Naam Openbare ruimte.
     */
    ELEMENT_1115("1115", Type.ALPHANUMERIEK, 1, 80, true),
    /**
     * Huisnummer.
     */
    ELEMENT_1120("1120", Type.NUMERIEK, 1, 5, false),
    /**
     * Huisletter.
     */
    ELEMENT_1130("1130", Type.ALPHANUMERIEK, 1),
    /**
     * Huisnummertoevoeging.
     */
    ELEMENT_1140("1140", Type.ALPHANUMERIEK, 1, 4, false),
    /**
     * Aanduiding bij huisnummer.
     */
    ELEMENT_1150("1150", Type.ALPHANUMERIEK, 2),
    /**
     * Postcode.
     */
    ELEMENT_1160("1160", Type.ALPHANUMERIEK, 6),
    /**
     * Woonplaatsnaam.
     */
    ELEMENT_1170("1170", Type.ALPHANUMERIEK, 1, 80, true),
    /**
     * Identificatiecode verblijfplaats.
     */
    ELEMENT_1180("1180", Type.ALPHANUMERIEK, 16),
    /**
     * Identificatiecode nummeraanduiding.
     */
    ELEMENT_1190("1190", Type.ALPHANUMERIEK, 16),
    /**
     * Locatiebeschrijving.
     */
    ELEMENT_1210("1210", Type.ALPHANUMERIEK, 1, 35, true),
    /**
     * Land adres buitenland.
     */
    ELEMENT_1310("1310", Type.NUMERIEK, 4),
    /**
     * Datum aanvang adres buitenland.
     */
    ELEMENT_1320("1320", Type.NUMERIEK, 8),
    /**
     * Regel 1 adres buitenland.
     */
    ELEMENT_1330("1330", Type.ALPHANUMERIEK, 1, 35, true),
    /**
     * Regel 2 adres buitenland.
     */
    ELEMENT_1340("1340", Type.ALPHANUMERIEK, 1, 35, true),
    /**
     * Regel 3 adres buitenland.
     */
    ELEMENT_1350("1350", Type.ALPHANUMERIEK, 1, 35, true),
    /**
     * Land vanwaar ingeschreven.
     */
    ELEMENT_1410("1410", Type.NUMERIEK, 4),
    /**
     * Datum vestiging in Nederland.
     */
    ELEMENT_1420("1420", Type.NUMERIEK, 8),
    /**
     * Soort verbintenis.
     */
    ELEMENT_1510("1510", Type.ALPHANUMERIEK, 1),
    /**
     * Vorig A-nummer.
     */
    ELEMENT_2010("2010", Type.NUMERIEK, 10),
    /**
     * Volgend A-nummer.
     */
    ELEMENT_2020("2020", Type.NUMERIEK, 10),
    /**
     * Aanduiding Europees kiesrecht.
     */
    ELEMENT_3110("3110", Type.NUMERIEK, 1),
    /**
     * Datum verzoek of mededeling Europees kiesrecht.
     */
    ELEMENT_3120("3120", Type.NUMERIEK, 8),
    /**
     * Einddatum uitsluiting Europees kiesrecht.
     */
    ELEMENT_3130("3130", Type.NUMERIEK, 8),
    /**
     * Indicatie gezag minderjarige.
     */
    ELEMENT_3210("3210", Type.ALPHANUMERIEK, 1, 2, false),
    /**
     * Indicate curateleregister.
     */
    ELEMENT_3310("3310", Type.NUMERIEK, 1),
    /**
     * Soort Nederlands reisdocument.
     */
    ELEMENT_3510("3510", Type.ALPHANUMERIEK, 2),
    /**
     * Nummer Nederlands reisdocument.
     */
    ELEMENT_3520("3520", Type.ALPHANUMERIEK, 9),
    /**
     * Datum uitgifte Nederlands reisdocument.
     */
    ELEMENT_3530("3530", Type.NUMERIEK, 8),
    /**
     * Autoriteit van afgifte Nederlands reisdocument.
     */
    ELEMENT_3540("3540", Type.ALPHANUMERIEK, 2, 6, false),
    /**
     * Datum einde geldigheid Nederlands reisdocument.
     */
    ELEMENT_3550("3550", Type.NUMERIEK, 8),
    /**
     * Datum inhouding dan wel vermissing Nederlands reisdocument.
     */
    ELEMENT_3560("3560", Type.NUMERIEK, 8),
    /**
     * Aanduiding inhouding dan wel vermissing Nederlands reisdocument.
     */
    ELEMENT_3570("3570", Type.ALPHANUMERIEK, 1),
    /**
     * Lengte houder.
     */
    ELEMENT_3580("3580", Type.NUMERIEK, 3),
    /**
     * Signalering met betrekking tot het verstrekken van een Nederlands reisdocument.
     */
    ELEMENT_3610("3610", Type.NUMERIEK, 1),
    /**
     * Aanduiding bezit buitenlands reisdocument.
     */
    ELEMENT_3710("3710", Type.NUMERIEK, 1),
    /**
     * Aanduiding uitgesloten kiesrecht.
     */
    ELEMENT_3810("3810", Type.ALPHANUMERIEK, 1),
    /**
     * Einddatum uitsluiting kiesrecht.
     */
    ELEMENT_3820("3820", Type.NUMERIEK, 8),
    /**
     * Aanduiding verblijfstitel.
     */
    ELEMENT_3910("3910", Type.NUMERIEK, 2),
    /**
     * Datum einde verblijfstitel.
     */
    ELEMENT_3920("3920", Type.NUMERIEK, 8),
    /**
     * Ingangsdatum verblijfstitel.
     */
    ELEMENT_3930("3930", Type.NUMERIEK, 8),
    /**
     * Afnemersindicatie.
     */
    ELEMENT_4010("4010", Type.NUMERIEK, 6),
    /**
     * Aantekening.
     */
    ELEMENT_4210("4210", Type.ALPHANUMERIEK, 1, 24, true),
    /**
     * Aanduiding naamgebruik.
     */
    ELEMENT_6110("6110", Type.ALPHANUMERIEK, 1),
    /**
     * Datum ingang familierechtelijke betrekking.
     */
    ELEMENT_6210("6210", Type.NUMERIEK, 8),
    /**
     * Reden verkrijging Nederlandse nationaliteit.
     */
    ELEMENT_6310("6310", Type.NUMERIEK, 3),
    /**
     * Reden verlies Nederlandse nationaliteit.
     */
    ELEMENT_6410("6410", Type.NUMERIEK, 3),
    /**
     * Aanduiding bijzonder Nederlanderschap.
     */
    ELEMENT_6510("6510", Type.ALPHANUMERIEK, 1),
    /**
     * Datum ingang blokkering PL.
     */
    ELEMENT_6620("6620", Type.NUMERIEK, 8),
    /**
     * Datum opschorting bijhouding.
     */
    ELEMENT_6710("6710", Type.NUMERIEK, 8),
    /**
     * Omschrijving reden opschorting bijhouding.
     */
    ELEMENT_6720("6720", Type.ALPHANUMERIEK, 1),
    /**
     * Datum eerste inschrijving GBA/RNI.
     */
    ELEMENT_6810("6810", Type.NUMERIEK, 8),
    /**
     * Gemeente waar de PK zich bevindt.
     */
    ELEMENT_6910("6910", Type.NUMERIEK, 4),
    /**
     * Indicatie geheim.
     */
    ELEMENT_7010("7010", Type.NUMERIEK, 1),
    /**
     * Datum verificatie (RNI element).
     */
    ELEMENT_7110("7110", Type.NUMERIEK, 8),
    /**
     * Omschrijving verificatie (RNI element).
     */
    ELEMENT_7120("7120", Type.ALPHANUMERIEK, 1, 50, true),
    /**
     * Omschrijving van de aangifte adreshouding.
     */
    ELEMENT_7210("7210", Type.ALPHANUMERIEK, 1),
    /**
     * Indicatie document.
     */
    ELEMENT_7510("7510", Type.NUMERIEK, 1),
    /**
     * Versienummer.
     */
    ELEMENT_8010("8010", Type.NUMERIEK, 4),
    /**
     * Datumtijdstempel.
     */
    ELEMENT_8020("8020", Type.NUMERIEK, 17),
    /**
     * Registergemeente akte.
     */
    ELEMENT_8110("8110", Type.NUMERIEK, 4),
    /**
     * Aktenummer.
     */
    ELEMENT_8120("8120", Type.ALPHANUMERIEK, 7),
    /**
     * Gemeente document.
     */
    ELEMENT_8210("8210", Type.NUMERIEK, 4),
    /**
     * Datum document.
     */
    ELEMENT_8220("8220", Type.NUMERIEK, 8),
    /**
     * Beschrijving document.
     */
    ELEMENT_8230("8230", Type.ALPHANUMERIEK, 1, 40, true),
    /**
     * Aanduiding gegevens in onderzoek.
     */
    ELEMENT_8310("8310", Type.NUMERIEK, 6),
    /**
     * Datum ingang onderzoek.
     */
    ELEMENT_8320("8320", Type.NUMERIEK, 8),
    /**
     * Datum einde onderzoek.
     */
    ELEMENT_8330("8330", Type.NUMERIEK, 8),
    /**
     * Indicatie onjuist, dan wel strijdigheid met de openbare orde.
     */
    ELEMENT_8410("8410", Type.ALPHANUMERIEK, 1),
    /**
     * Ingangsdatum geldigheid.
     */
    ELEMENT_8510("8510", Type.NUMERIEK, 8),
    /**
     * Datum van opneming.
     */
    ELEMENT_8610("8610", Type.NUMERIEK, 8),
    /**
     * PK-gegevens volledig meegeconverteerd.
     */
    ELEMENT_8710("8710", Type.ALPHANUMERIEK, 1),
    /**
     * RNI Deelnemer (RNI element).
     */
    ELEMENT_8810("8810", Type.NUMERIEK, 4),
    /**
     * Omschrijving verdrag (RNI element).
     */
    ELEMENT_8820("8820", Type.ALPHANUMERIEK, 1, 50, true);

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* *** LEESBARE NAMEN *************************************************************************************** */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /** 01.10 A-nummer. */
    public static final Lo3ElementEnum ANUMMER = ELEMENT_0110;
    /** 01.20 Burgerservicenummer. */
    public static final Lo3ElementEnum BURGERSERVICENUMMER = ELEMENT_0120;
    /** 02.10 Voornamen. */
    public static final Lo3ElementEnum VOORNAMEN = ELEMENT_0210;
    /** 02.30 Voorvoegsel. */
    public static final Lo3ElementEnum VOORVOEGSEL = ELEMENT_0230;
    /** 02.40 Geslachtsnaam */
    public static final Lo3ElementEnum GESLACHTSNAAM = ELEMENT_0240;
    /** 03.10 Geboortedatum */
    public static final Lo3ElementEnum GEBOORTEDATUM = ELEMENT_0310;
    /** 03.20 Geboorteplaats */
    public static final Lo3ElementEnum GEBOORTEPLAATS = ELEMENT_0320;
    /** 03.30 Geboorteland */
    public static final Lo3ElementEnum GEBOORTELAND = ELEMENT_0330;
    /** 04.10 Geslachtsaanduiding */
    public static final Lo3ElementEnum GESLACHTSAANDUIDING = ELEMENT_0410;

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private final String elementNummer;
    private final Type type;
    private final int minimumLengte;
    private final int maximumLengte;
    private final boolean afkappen;

    private final Pattern elementPattern = Pattern.compile("^[0-9]{4}$");

    private Lo3ElementEnum(final String elementNummer, final Type type, final int lengte) {
        this(elementNummer, type, lengte, lengte, false);
    }

    private Lo3ElementEnum(
            final String elementNummer,
            final Type type,
            final int minimumLengte,
            final int maximumLengte,
            final boolean afkappen) {
        if (!elementPattern.matcher(elementNummer).matches()) {
            throw new IllegalArgumentException("Element(nummer) moet bestaan uit vier cijfers");
        }
        this.elementNummer = elementNummer;
        this.type = type;
        this.minimumLengte = minimumLengte;
        this.maximumLengte = maximumLengte;
        this.afkappen = afkappen;
    }

    /**
     * @return het elementnummer
     */
    public String getElementNummer() {
        return elementNummer;
    }

    public Type getType() {
        return type;
    }

    public int getMinimumLengte() {
        return minimumLengte;
    }

    public int getMaximumLengte() {
        return maximumLengte;
    }

    public boolean getAfkappen() {
        return afkappen;
    }

    /**
     * @param element
     *            het element nummer
     * @return de corresponderende Lo3ElementEnum: ELEMENT_[element]
     */
    public static Lo3ElementEnum getLO3Element(final String element) {
        return Lo3ElementEnum.valueOf("ELEMENT_" + element);
    }

    public static enum Type {
        NUMERIEK, ALPHANUMERIEK;
    }
}
