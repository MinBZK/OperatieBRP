/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

/**
 * De conversie stappen.
 */
public enum ConversieStap {
    /**
     * LO3 naar BRP: Stap 0: Valideer model.
     */
    LO3_NAAR_BRP_VALIDEER,
    /**
     * LO3 naar BRP: Stap 1a: Extra documentatie toevoegen.
     */
    LO3_NAAR_BRP_EXTRA_DOCUMENTATIE,
    /**
     * LO3 naar BRP: Stap 1b: Splitsen (verbintenissen).
     */
    LO3_NAAR_BRP_SPLITSEN_VERBINTENISSEN,
    /**
     * LO3 naar BRP: Stap 1c: Splitsen (ouders).
     */
    LO3_NAAR_BRP_SPLITSEN_OUDERS,
    /**
     * LO3 naar BRP: Stap 2a: Conversie inhoud.
     */
    LO3_NAAR_BRP_CONVERSIE_INHOUD,
    /**
     * LO3 naar BRP: Stap 2b: Verwijder redundantie.
     */
    LO3_NAAR_BRP_VERWIJDER_REDUNDANTIE,
    /**
     * LO3 naar BRP: Stap 3: Conversie historie.
     */
    LO3_NAAR_BRP_CONVERSIE_HISTORIE,
    /**
     * LO3 naar BRP: Stap 4: Afgeleide gegevens.
     */
    LO3_NAAR_BRP_AFGELEIDE_GEGEVENS,
    /**
     * LO3 naar BRP: Stap 5: Vullen IST gegevens.
     */
    LO3_NAAR_BRP_VULLEN_IST,

    /**
     * BRP naar LO3: Stap 0: Valideren BRP persoonslijst.
     */
    BRP_NAAR_LO3_VALIDEER,
    /**
     * BRP naar LO3: Stap 1: Bepalen materiele historie.
     */
    BRP_NAAR_LO3_BEPALEN_MATERIELE_HISTORIE,
    /**
     * BRP naar LO3: Stap 2: Converteer.
     */
    BRP_NAAR_LO3_CONVERTEREN,
    /**
     * BRP naar LO3: Stap 3: Bepalen adellijke titel of predikaat adhv geslacht.
     */
    BRP_NAAR_LO3_ADELLIJKE_TITEL_PREDIKAAT_BEPALEN,
    /**
     * BRP naar LO3: Stap 4: Onderzoek.
     */
    BRP_NAAR_LO3_ONDERZOEK,
    /**
     * BRP naar LO3: Stap 5: Sorteren.
     */
    BRP_NAAR_LO3_SORTEREN,
}
