/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

/**
 * Enumeratie van de types die we onderscheiden bij de vergelijker van deltabepaling.
 */
public enum VerschilType {

    /** Rij verwijderd van PL. */
    RIJ_VERWIJDERD,
    /** Element binnen een rij verwijderd. */
    ELEMENT_VERWIJDERD,
    /** Rij toegevoegd aan PL. */
    RIJ_TOEGEVOEGD,
    /** Element nieuw binnen een rij. */
    ELEMENT_NIEUW,
    /** Element binnen een rij aangepast. */
    ELEMENT_AANGEPAST,
    /** Element binnen een nieuw toegevoegde rij aangepast. */
    NIEUWE_RIJ_ELEMENT_AANGEPAST,
    /** Onderzoek moet worden gekopieerd van de bestaande rij naar nieuwe rij. */
    KOPIEER_ONDERZOEK_NAAR_NIEUWE_RIJ
}
