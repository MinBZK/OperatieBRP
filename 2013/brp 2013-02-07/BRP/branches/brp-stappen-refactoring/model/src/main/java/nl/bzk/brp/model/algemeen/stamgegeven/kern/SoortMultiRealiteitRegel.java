/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

/**
 * De Soort multirealiteitsregel.
 *
 * Met Multirealiteitregel kan mutirealiteit worden vastgelegd voor persoonsgegevens (doordat een 'alternatieve persoon'
 * wordt vastgelegd), doordat een Relatie vanuit ��n van de twee partners wordt ontkend, of doordat - in geval van een
 * Familierechtelijke betrekking - de Betrokkenheid van ��n van de ouders wordt ontkend. Deze verschillende soorten MR
 * wordt met Soort MR aangegeven.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public enum SoortMultiRealiteitRegel {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens..
     */
    PERSOON("Persoon", "Multirealiteit op persoonsgegevens, zoals naam en geboortegegevens."),
    /**
     * Multirealiteit op Relatie..
     */
    RELATIE("Relatie", "Multirealiteit op Relatie."),
    /**
     * Multirealiteit op Betrokkenheid..
     */
    BETROKKENHEID("Betrokkenheid", "Multirealiteit op Betrokkenheid.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortMultiRealiteitRegel
     * @param omschrijving Omschrijving voor SoortMultiRealiteitRegel
     */
    private SoortMultiRealiteitRegel(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort multi-realiteit regel.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort multi-realiteit regel.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
