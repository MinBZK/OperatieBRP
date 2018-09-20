/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.ber;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Situaties die zich kunnen voordoen tijdens het verwerken van een OT:Bijhoudingsplan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum Bijhoudingssituatie implements SynchroniseerbaarStamgegeven {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * De persoon valt onder de bijhoudingsverantwoordelijkheid van de partij die het bijhoudingsvoorstel heeft
     * ingezonden..
     */
    INDIENER_IS_BIJHOUDINGSPARTIJ("Indiener is bijhoudingspartij",
            "De persoon valt onder de bijhoudingsverantwoordelijkheid van de partij die het bijhoudingsvoorstel heeft ingezonden."),
    /**
     * De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel automatisch wenst te
     * fiatteren..
     */
    AUTOMATISCHE_FIAT("Automatische fiat",
            "De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel automatisch wenst te fiatteren."),
    /**
     * Het bijhoudingsvoorstel is incompleet (ontbreken Anr/BSN) en kan daardoor niet verwerkt worden. De ontvanger
     * dient het voorstel te completeren en opnieuw in te dienen..
     */
    AANVULLEN_EN_OPNIEUW_INDIENEN(
            "Aanvullen en opnieuw indienen",
            "Het bijhoudingsvoorstel is incompleet (ontbreken Anr/BSN) en kan daardoor niet verwerkt worden. De ontvanger dient het voorstel te completeren en opnieuw in te dienen."),
    /**
     * De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel handmatig wenst te
     * fiatteren of de partij wenst automatisch te fiatteren maar er is een fiatteringsuitzondering die van toepassing
     * is op het bijhoudingsvoorstel..
     */
    OPNIEUW_INDIENEN(
            "Opnieuw indienen",
            "De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die het voorstel handmatig wenst te fiatteren of de partij wenst automatisch te fiatteren maar er is een fiatteringsuitzondering die van toepassing is op het bijhoudingsvoorstel."),
    /**
     * De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die valt onder het GBA-regime..
     */
    GBA("GBA", "De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die valt onder het GBA-regime.");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor Bijhoudingssituatie
     * @param omschrijving Omschrijving voor Bijhoudingssituatie
     */
    private Bijhoudingssituatie(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Bijhoudingssituatie.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Bijhoudingssituatie.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
