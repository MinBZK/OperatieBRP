/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * De burgerzaken module.
 *
 * Bijhoudingsberichten valleen uiteen in verschillende modulen, die overeenkomen met een opdeling die gebruikelijk is
 * in burgerzaken modules.
 *
 * Om de enumeratie te re-gegereren aan de hand van de database tabel, gebruik het volgende postgreSQL statement: <code>
select format(E'/** %s. *\/\n%s ((short) %s, "%s", "%s"),',
naam, regexp_replace(replace(upper(naam), ' ', '_'), '\(|\)', '', 'g'), id, naam, oms)
from kern.burgerzakenmodule;
</code>
 *
 * Gebruik in psql het commando '\a' om de formatting van de output te beperken.
 *
 * Gebruik in psql het commando '\o <bestandsnaam>' om de uitvoer in een bestand op te slaan.
 *
 * Voer daarna bovenstaand SQL statement uit.
 *
 */
public enum BurgerzakenModule implements Enumeratie {

    /** Afstamming. */
    AFSTAMMING((short) 1, "Afstamming",
            "Module rondom de familierechtelijke betrekking tussen kind en ouder(s), alsmede de relaties in kader van het ongeboren vrucht."),
    /** HuwelijkGeregistreerd partnerschap. */
    HUWELIJKGEREGISTREERD_PARTNERSCHAP((short) 2, "HuwelijkGeregistreerd partnerschap", "Module rondom het huwelijk en het geregistreerd partnerschap."),
    /** Verblijf en adres. */
    VERBLIJF_EN_ADRES((short) 3, "Verblijf en adres", "Module rondom verblijf binnen of buiten Nederland, ook wel aangeduid met de term Migratie."),
    /** Overlijden. */
    OVERLIJDEN((short) 4, "Overlijden", "Module rondom overlijden."),
    /** Bevraging. */
    BEVRAGING_BIJHOUDING((short) 5, "Bevraging (bijhouding)", "Module rondom bevraging in het kader van koppelvlak Bijhouding."),
    /** Nationaliteit. */
    NATIONALITEIT((short) 6, "Nationaliteit", "Module rondom Nationaliteit."),
    /** Naam geslacht. */
    NAAM_GESLACHT((short) 7, "Naam geslacht", "Module rondom Naam en Geslacht."),
    /** Synchronisatie. */
    SYNCHRONISATIE((short) 8, "Synchronisatie", "Module rondom het synchroniseren van gegevens BRP-gegevens met afnemers."),
    /** Document verzoek mededeling. */
    DOCUMENT_VERZOEK_MEDEDELING((short) 9, "Document verzoek mededeling", "Module rondom afhandeling Documenten, Verzoeken en Mededelingen."),
    /** Reisdocumenten. */
    REISDOCUMENTEN((short) 10, "Reisdocumenten", "Module rondom Reisdocumenten."),
    /** Verkiezingen. */
    VERKIEZINGEN((short) 11, "Verkiezingen", "Module rondom Verkiezingen."),
    /** Bevraging. */
    BEVRAGING((short) 12, "Bevraging", "Module rondom bevraging in het kader van koppelvlak Levering."),
    /** Afnemerindicatie. */
    AFNEMERINDICATIE((short) 13, "Afnemerindicatie", "Module rondom plaatsen en verwijderen van afnemerindicaties."),
    /** Onderzoek. */
    ONDERZOEK((short) 14, "Onderzoek", "Module rondom registreren en afhandelen van onderzoeken."),
    /** Selectie. */
    SELECTIE((short) 15, "Selectie", "Module ten behoeve van selectie binnen koppelvlak Levering."),
    /** Migratievoorzieningen. */
    MIGRATIEVOORZIENINGEN((short) 16, "Migratievoorzieningen", "Module ten behoeve van migratievoorzieningen."),
    /** Bijzondere bijhouding. */
    BIJZONDERE_BIJHOUDING((short) 17, "Bijzondere bijhouding", "Module ten behoeve van complexe bijhouding."),
    /** Fiattering. */
    FIATTERING((short) 18, "Fiattering", "Module rondom fiattering."),
    /** Attendering. */
    ATTENDERING((short) 19, "Attendering", "Module rondom attendering."),
    /** Vrije berichten. */
    VRIJE_BERICHTEN((short) 20, "Vrije berichten", "Module ten behoeve van het versturen van vrije berichten.");

    /**
     *
     */
    private static final EnumParser<BurgerzakenModule> PARSER = new EnumParser<>(BurgerzakenModule.class);

    /**
     *
     */
    private final short id;
    /**
     *
     */
    private final String naam;
    /**
     *
     */
    private final String omschrijving;

    /**
     * Maak een nieuwe burgerzaken module.
     *
     * @param id
     *            id
     * @param naam
     *            naam
     * @param omschrijving
     *            omschrijving
     */
    BurgerzakenModule(final short id, final String naam, final String omschrijving) {
        this.id = id;
        this.naam = naam;
        this.omschrijving = omschrijving;
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
     * @return burgerzaken module
     */
    public static BurgerzakenModule parseId(final Short id) {
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

    /**
     * Geef de waarde van omschrijving.
     *
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Enumeratie#getCode()
     */
    @Override
    public String getCode() {
        throw new UnsupportedOperationException("De enumeratie BurgerzakenModule heeft geen code");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean heeftCode() {
        return false;
    }
}
