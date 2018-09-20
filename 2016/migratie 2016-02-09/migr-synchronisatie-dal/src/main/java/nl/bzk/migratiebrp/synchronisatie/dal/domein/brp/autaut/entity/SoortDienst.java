/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EnumParser;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie;

/**
 * Enumeratie van soorten diensten.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
 select format(E'/** %s. *\/\n%s ((short) %s, "%s"),',
 s.naam, regexp_replace(regexp_replace(upper(s.naam), ' |-', '_', 'g'), '\(|\)', '', 'g'), s.id, s.naam)
 from autaut.srtdienst s
 </code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 */
public enum SoortDienst implements Enumeratie {

    /** Mutatielevering op basis van doelbinding. */
    MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING((short) 1, "Mutatielevering op basis van doelbinding"),
    /** Mutatielevering op basis van afnemerindicatie. */
    MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE((short) 2, "Mutatielevering op basis van afnemerindicatie"),
    /** Plaatsen afnemerindicatie. */
    PLAATSEN_AFNEMERINDICATIE((short) 3, "Plaatsen afnemerindicatie"),
    /** Attendering. */
    ATTENDERING((short) 4, "Attendering"),
    /** Zoek persoon. */
    ZOEK_PERSOON((short) 5, "Zoek persoon"),
    /** Zoek persoon op adresgegevens. */
    ZOEK_PERSOON_OP_ADRESGEGEVENS((short) 6, "Zoek persoon op adresgegevens"),
    /** Geef medebewoners van persoon. */
    GEEF_MEDEBEWONERS_VAN_PERSOON((short) 7, "Geef medebewoners van persoon"),
    /** Geef details persoon. */
    GEEF_DETAILS_PERSOON((short) 8, "Geef details persoon"),
    /** Synchronisatie persoon. */
    SYNCHRONISATIE_PERSOON((short) 9, "Synchronisatie persoon"),
    /** Synchronisatie stamgegeven. */
    SYNCHRONISATIE_STAMGEGEVEN((short) 10, "Synchronisatie stamgegeven"),
    /** Mutatielevering stamgegeven. */
    MUTATIELEVERING_STAMGEGEVEN((short) 11, "Mutatielevering stamgegeven"),
    /** Selectie. */
    SELECTIE((short) 12, "Selectie"),
    /** Geef details persoon bulk. */
    GEEF_DETAILS_PERSOON_BULK((short) 13, "Geef details persoon bulk"),
    /** Geef synchroniciteitsgegevens persoon. */
    GEEF_SYNCHRONICITEITSGEGEVENS_PERSOON((short) 14, "Geef synchroniciteitsgegevens persoon"),
    /** Geef identificerende gegevens persoon bulk. */
    GEEF_IDENTIFICERENDE_GEGEVENS_PERSOON_BULK((short) 15, "Geef identificerende gegevens persoon bulk"),
    /** Geef details terugmelding. */
    GEEF_DETAILS_TERUGMELDING((short) 16, "Geef details terugmelding"),
    /** Opvragen aantal personen op adres. */
    OPVRAGEN_AANTAL_PERSONEN_OP_ADRES((short) 17, "Opvragen aantal personen op adres"),
    /** Aanmelding gerede twijfel. */
    AANMELDING_GEREDE_TWIJFEL((short) 18, "Aanmelding gerede twijfel"),
    /** Intrekking terugmelding. */
    INTREKKING_TERUGMELDING((short) 19, "Intrekking terugmelding"),
    /** Verwijderen afnemerindicatie. */
    VERWIJDEREN_AFNEMERINDICATIE((short) 20, "Verwijderen afnemerindicatie");

    private static final EnumParser<SoortDienst> PARSER = new EnumParser<>(SoortDienst.class);

    private final short id;
    private final String naam;

    /**
     * Maak een nieuwe soort relatie.
     *
     * @param id
     *            id
     * @param naam
     *            code
     */
    SoortDienst(final short id, final String naam) {
        this.id = id;
        this.naam = naam;
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
     *            id
     * @return soort relatie
     */
    public static SoortDienst parseId(final Short id) {
        return PARSER.parseId(id);
    }

    /**
     * Bepaal een voorkomen op basis van code.
     *
     * @param code
     *            code
     * @return soort relatie
     */
    public static SoortDienst parseCode(final String code) {
        return PARSER.parseCode(code);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie SoortDienst heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public String getNaam() {
        return naam;
    }
}
