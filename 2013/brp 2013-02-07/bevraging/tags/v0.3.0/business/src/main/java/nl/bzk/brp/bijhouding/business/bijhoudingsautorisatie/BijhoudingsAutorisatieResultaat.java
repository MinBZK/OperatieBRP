/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.bijhoudingsautorisatie;

/**
 * Een aantal vaste waarden die gebruikt worden om te bepalen of een bijhouding geautoriseerd is en
 * zo ja, op basis waarvan de bijhouding geautoriseerd is.
 */
public enum BijhoudingsAutorisatieResultaat {

    /**
     * De bijhouding is niet toegestaan.
     */
    BIJHOUDING_NIET_TOEGESTAAN,

    /**
     * De bijhouding is op basis van de wet BRP toegestaan.
     */
    BIJHOUDING_TOEGESTAAN_OP_BASIS_WET_BRP,

    /**
     * Een partij heeft een andere partij expliciet geautoriseerd.
     */
    BIJHOUDING_TOEGESTAAN_OP_BASIS_VERLEENDE_AUTORISATIE,

    /**
     * De bijhouding is toegestaan omdat de gemeente hiervoor autorisatie heeft verleend aan de bijhoudende partij.
     */
    BIJHOUDING_TOEGESTAAN_OP_BASIS_AUT_FIATTERING_MANDAAT,

    /**
     * Slechts een bijhoudingsvoorstel mag gedaan worden.
     */
    BIJHOUDING_VOORSTEL_TOEGESTAAN
}
