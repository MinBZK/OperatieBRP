/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * Categorisatie van de soorten document.
 *
 * Documenten worden getypeerd met 'soort document'. Onderkend is dat deze soort document weer nader getypeerd kan
 * worden, denk aan 'Nederlandse akten' voor een soort document 'Nederlandse Huwelijksakte'.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.2.7.
 * Gegenereerd op: Tue Dec 04 11:24:58 CET 2012.
 */
public enum CategorieSoortDocument {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Nederlandse Akten van de burgerlijke stand..
     */
    NEDERLANDSE_AKTE("Nederlandse Akte", "Nederlandse Akten van de burgerlijke stand.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor CategorieSoortDocument
     * @param omschrijving Omschrijving voor CategorieSoortDocument
     */
    private CategorieSoortDocument(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Categorie soort document.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Categorie soort document.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
