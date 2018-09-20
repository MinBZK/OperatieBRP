/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev;

/**
 * Categorisering van expressie
 * <p/>
 * Expressies worden voor verschillende doeleinden gebruikt: bepalen van of een gegeven geleverd mag worden, en bepalen of er geleverd moet worden. Deze
 * verschillende soorten expressies worden onderscheiden door de soort.
 * <p/>
 * Het is denkbaar dat ��n expressie voor beide doeleinden (filtering EN triggering) kan worden gebruikt, en zelfs dat dit generiek zo is. Zeker is het
 * niet. Een alternatieve modellering van het geheel rondom expressies is het verwijderen van 'soort' bij expressie, en het bij de koppeltabel
 * Abonnement/Expressie te positioneren. In dat geval zou soort expressie eerder een 'doel expressie' of 'nut expressie' zijn. Vooralsnog is niet voor die
 * alternatieve modellering gekozen (omdat nu niet zeker is dat die werkt), en kent bijv. de conversietabel gbarubriek=>brp twee expressies, zijnde een
 * filterexpressie en een triggerexpressie, en is expressie dus te typeren door een soort. RvdP 17 september 2013.
 */
public enum SoortExpressie {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Expressie ten behoeve van bepalen van de te leveren gegevens. Corresponderend met LO3 filterrubrieken..
     */
    FILTER("Filter",
        "Expressie ten behoeve van bepalen van de te leveren gegevens. Corresponderend met LO3 filterrubrieken."),
    /**
     * Expressie ten behoeve van het triggeren van de levering. Corresponderend met LO sleutelrubrieken..
     */
    TRIGGER("Trigger",
        "Expressie ten behoeve van het triggeren van de levering. Corresponderend met LO sleutelrubrieken.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam         Naam voor SoortExpressie
     * @param omschrijving Omschrijving voor SoortExpressie
     */
    private SoortExpressie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Soort expressie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort expressie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
