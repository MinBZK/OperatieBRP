/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
select format(E'/** %s. *\/\n%s ((short) %s, "%s"),',
naam, regexp_replace(replace(upper(naam), ' ', '_'), '\(|\)', '', 'g'), id, naam)
from kern.srtactie;
</code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 */
public enum SoortActie implements Enumeratie {

    /** Registratie geboorte. */
    REGISTRATIE_GEBOORTE((short) 1, "Registratie geboorte"),
    /** Beeindiging vastgesteld niet Nederlander. */
    BEEINDIGING_VASTGESTELD_NIET_NEDERLANDER((short) 2, "Beeindiging vastgesteld niet Nederlander"),
    /** Beeindiging bijzondere verblijfsrechtelijke positie. */
    BEEINDIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE((short) 3, "Beeindiging bijzondere verblijfsrechtelijke positie"),
    /** Registratie naamgebruik. */
    REGISTRATIE_NAAMGEBRUIK((short) 4, "Registratie naamgebruik"),
    /** Registratie nationaliteit. */
    REGISTRATIE_NATIONALITEIT((short) 5, "Registratie nationaliteit"),
    /** Registratie behandeld als Nederlander. */
    REGISTRATIE_BEHANDELD_ALS_NEDERLANDER((short) 6, "Registratie behandeld als Nederlander"),
    /** Conversie GBA. */
    CONVERSIE_GBA((short) 7, "Conversie GBA"),
    /** Registratie signalering reisdocument. */
    REGISTRATIE_SIGNALERING_REISDOCUMENT((short) 8, "Registratie signalering reisdocument"),
    /** Registratie gezag. */
    REGISTRATIE_GEZAG((short) 9, "Registratie gezag"),
    /** Registratie curatele. */
    REGISTRATIE_CURATELE((short) 10, "Registratie curatele"),
    /** Registratie staatloos. */
    REGISTRATIE_STAATLOOS((short) 11, "Registratie staatloos"),
    /** Registratie vastgesteld niet Nederlander. */
    REGISTRATIE_VASTGESTELD_NIET_NEDERLANDER((short) 12, "Registratie vastgesteld niet Nederlander"),
    /** Registratie verstrekkingsbeperking. */
    REGISTRATIE_VERSTREKKINGSBEPERKING((short) 13, "Registratie verstrekkingsbeperking"),
    /** Registratie afstamming. */
    REGISTRATIE_AFSTAMMING((short) 14, "Registratie afstamming"),
    /** Correctie afstamming. */
    CORRECTIE_AFSTAMMING((short) 15, "Correctie afstamming"),
    /** Registratie overlijden. */
    REGISTRATIE_OVERLIJDEN((short) 16, "Registratie overlijden"),
    /** Correctie overlijden. */
    CORRECTIE_OVERLIJDEN((short) 17, "Correctie overlijden"),
    /** Registratie adres. */
    REGISTRATIE_ADRES((short) 18, "Registratie adres"),
    /** Correctie adres. */
    CORRECTIE_ADRES((short) 19, "Correctie adres"),
    /** Correctie huwelijk. */
    CORRECTIE_HUWELIJK((short) 20, "Correctie huwelijk"),
    /** Correctie geregistreerd partnerschap. */
    CORRECTIE_GEREGISTREERD_PARTNERSCHAP((short) 21, "Correctie geregistreerd partnerschap"),
    /** Registratie onderzoek. */
    REGISTRATIE_ONDERZOEK((short) 22, "Registratie onderzoek"),
    /** Registratie ouder. */
    REGISTRATIE_OUDER((short) 23, "Registratie ouder"),
    /** Beeindiging nationaliteit. */
    BEEINDIGING_NATIONALITEIT((short) 24, "Beeindiging nationaliteit"),
    /** Beeindiging ouderschap. */
    BEEINDIGING_OUDERSCHAP((short) 25, "Beeindiging ouderschap"),
    /** Beeindiging behandeld als Nederlander. */
    BEEINDIGING_BEHANDELD_ALS_NEDERLANDER((short) 26, "Beeindiging behandeld als Nederlander"),
    /** Beeindiging verstrekkingsbeperking. */
    BEEINDIGING_VERSTREKKINGSBEPERKING((short) 27, "Beeindiging verstrekkingsbeperking"),
    /** Registratie voornaam. */
    REGISTRATIE_VOORNAAM((short) 28, "Registratie voornaam"),
    /** Beeindiging voornaam. */
    BEEINDIGING_VOORNAAM((short) 29, "Beeindiging voornaam"),
    /** Registratie geslachtsnaam. */
    REGISTRATIE_GESLACHTSNAAM((short) 30, "Registratie geslachtsnaam"),
    /** Registratie bijhouding. */
    REGISTRATIE_BIJHOUDING((short) 31, "Registratie bijhouding"),
    /** Registratie aanvang huwelijk geregistreerd partnerschap. */
    REGISTRATIE_AANVANG_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 32, "Registratie aanvang huwelijk geregistreerd partnerschap"),
    /** Registratie einde huwelijk geregistreerd partnerschap. */
    REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 33, "Registratie einde huwelijk geregistreerd partnerschap"),
    /** Registratie huwelijk geregistreerd partnerschap. */
    REGISTRATIE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 34, "Registratie huwelijk geregistreerd partnerschap"),
    /** Registratie afnemerindicatie. */
    REGISTRATIE_AFNEMERINDICATIE((short) 35, "Registratie afnemerindicatie"),
    /** Verval afnemerindicatie. */
    VERVAL_AFNEMERINDICATIE((short) 36, "Verval afnemerindicatie"),
    /** Verval overlijden. */
    VERVAL_OVERLIJDEN((short) 37, "Verval overlijden"),
    /** Verval huwelijk geregistreerd partnerschap. */
    VERVAL_HUWELIJK_GEREGISTREERD_PARTNERSCHAP((short) 38, "Verval huwelijk geregistreerd partnerschap"),
    /** Registratie naam geslacht. */
    REGISTRATIE_NAAM_GESLACHT((short) 39, "Registratie naam geslacht"),
    /** Registratie geslachtsaanduiding. */
    REGISTRATIE_GESLACHTSAANDUIDING((short) 40, "Registratie geslachtsaanduiding"),
    /** Beeindiging staatloos. */
    BEEINDIGING_STAATLOOS((short) 41, "Beeindiging staatloos"),
    /** Registratie nationaliteit naam. */
    REGISTRATIE_NATIONALITEIT_NAAM((short) 42, "Registratie nationaliteit naam"),
    /** Registratie bijzondere verblijfsrechtelijke positie. */
    REGISTRATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE((short) 43, "Registratie bijzondere verblijfsrechtelijke positie"),
    /** Registratie identificatienummers. */
    REGISTRATIE_IDENTIFICATIENUMMERS((short) 44, "Registratie identificatienummers"),
    /** Registratie reisdocument. */
    REGISTRATIE_REISDOCUMENT((short) 45, "Registratie reisdocument"),
    /** Registratie uitsluiting kiesrecht. */
    REGISTRATIE_UITSLUITING_KIESRECHT((short) 46, "Registratie uitsluiting kiesrecht"),
    /** Registratie deelname EU verkiezingen. */
    REGISTRATIE_DEELNAME_EU_VERKIEZINGEN((short) 47, "Registratie deelname EU verkiezingen"),
    /** Conversie GBA Materiële historie. */
    CONVERSIE_GBA_MATERIELE_HISTORIE((short) 48, "Conversie GBA Materiële historie"),
    /** Registratie migratie. */
    REGISTRATIE_MIGRATIE((short) 49, "Registratie migratie"),
    /** Registratie kind. */
    REGISTRATIE_KIND((short) 50, "Registratie kind"),
    /** Wijziging onderzoek. */
    WIJZIGING_ONDERZOEK((short) 51, "Wijziging onderzoek"),
    /** Conversie GBA Lege onjuist categorie. */
    CONVERSIE_GBA_LEGE_ONJUIST_CATEGORIE((short) 52, "Conversie GBA Lege onjuist categorie"),
    /** Registratie ouderschap. */
    REGISTRATIE_OUDERSCHAP((short) 53, "Registratie ouderschap"),
    /** Registratie naam voornaam. */
    REGISTRATIE_NAAM_VOORNAAM((short) 54, "Registratie naam voornaam"),
    /** Registratie verblijfsrecht. */
    REGISTRATIE_VERBLIJFSRECHT((short) 55, "Registratie verblijfsrecht"),
    /** Verval verblijfsrecht. */
    VERVAL_VERBLIJFSRECHT((short) 56, "Verval verblijfsrecht"),
    /** Correctie nationaliteit. */
    CORRECTIE_NATIONALITEIT((short) 57, "Correctie nationaliteit");

    private static final EnumParser<SoortActie> PARSER = new EnumParser<>(SoortActie.class);

    private final short id;
    private final String naam;

    /**
     * Maak een nieuwe soort actie.
     *
     * @param ident
     *            id van SoortActie
     * @param omschrijving
     *            omschrijving van SoortActie
     */
    SoortActie(final short ident, final String omschrijving) {
        id = ident;
        naam = omschrijving;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getId()
     */
    @Override
    public short getId() {
        return id;
    }

    /**
     * Bepaal een voorkomen op basis van id.
     *
     * @param id
     *            id van SoortActie
     * @return de SoortActie die bij het id hoort.
     */
    public static SoortActie parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortActie heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
