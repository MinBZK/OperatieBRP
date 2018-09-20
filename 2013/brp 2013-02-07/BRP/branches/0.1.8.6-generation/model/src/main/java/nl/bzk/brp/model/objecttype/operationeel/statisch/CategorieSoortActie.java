/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Deze code is gegenereerd vanuit het metaregister van het BRP, versie 1.0.18.
 *
 */
package nl.bzk.brp.model.objecttype.operationeel.statisch;

/**
 * De categorisatie van de soorten BRP acties.
 * @version 1.0.18.
 */
public enum CategorieSoortActie {

    /** Dummy value. Echte values beginnen in de database bij 1 ipv 0. */
    DUMMY("", ""),
    /** Conversie. */
    CONVERSIE("Conversie", "Alle soorten acties voortvloeiend uit conversie"),
    /** Familierechtelijke betrekking. */
    FAMILIERECHTELIJKE_BETREKKING("Familierechtelijke betrekking", "Alle soorten acties met betrekking tot leggen van familierechtelijke betrekking tussen ouder(s) en kind"),
    /** Verhuizing. */
    VERHUIZING("Verhuizing", "Alle soorten acties met betrekking tot verhuizingen"),
    //MANUALY ADDED:
    /** Huwelijk / Geregistreerd partnerschap. */
    HUWELIJK_GEREGISTREERD_PARTNERSCHAP("Huwelijk / Geregistreerd partnerschap", "Alle soorten acties met betrekking tot huwelijk/geregistreerd partnerschap"),
    /** Nationaliteit. */
    NATIONALITEIT("Nationaliteit", "Alle soorten acties met betrekking tot Nationaliteit");

    /** De naam waarmee de categorie soort actie wordt omschreven. */
    private final String naam;
    /** De omschrijving voor de categorie soort actie. */
    private final String omschrijving;

    /**
     * Constructor.
     *
     * @param naam De naam waarmee de categorie soort actie wordt omschreven.
     * @param omschrijving De omschrijving voor de categorie soort actie.
     *
     */
    private CategorieSoortActie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * @return De naam waarmee de categorie soort actie wordt omschreven.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return De omschrijving voor de categorie soort actie.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
