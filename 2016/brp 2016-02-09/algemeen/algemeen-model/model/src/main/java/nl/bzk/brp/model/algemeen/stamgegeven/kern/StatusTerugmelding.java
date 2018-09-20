/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Categorisatie van de status terugmelding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum StatusTerugmelding implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy", "Dummy"),
    /**
     * Aangemeld en doorgemeld.
     */
    GEMELD("Gemeld", "Aangemeld en doorgemeld"),
    /**
     * De terugmelding heeft geen wijziging veroorzaakt, ook geen onderzoek.
     */
    GESLOTEN_ZONDER_WIJZIGING("Gesloten zonder wijziging", "De terugmelding heeft geen wijziging veroorzaakt, ook geen onderzoek"),
    /**
     * De terugmelding heeft geleid tot mutaties, zonder de tussenkomst van een onderzoek.
     */
    GESLOTEN_MET_WIJZIGING("Gesloten met wijziging", "De terugmelding heeft geleid tot mutaties, zonder de tussenkomst van een onderzoek"),
    /**
     * De terugmelding is ingetrokken.
     */
    INGETROKKEN("Ingetrokken", "De terugmelding is ingetrokken"),
    /**
     * Er is een onderzoek waaraan deze terugmelding is toegevoegd.
     */
    IN_ONDERZOEK("In onderzoek", "Er is een onderzoek waaraan deze terugmelding is toegevoegd");

    private final String naam;
    private final String omschrijving;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor StatusTerugmelding
     * @param omschrijving Omschrijving voor StatusTerugmelding
     */
    private StatusTerugmelding(final String naam, final String omschrijving) {
        this.naam = naam;
        this.omschrijving = omschrijving;
    }

    /**
     * Retourneert Naam van Status terugmelding.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Status terugmelding.
     *
     * @return Omschrijving.
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.STATUSTERUGMELDING;
    }

}
