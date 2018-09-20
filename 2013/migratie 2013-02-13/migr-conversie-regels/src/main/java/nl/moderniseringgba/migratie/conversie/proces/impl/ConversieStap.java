/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.impl;

/**
 * De conversie stappen.
 */
public enum ConversieStap {
    /** LO3 naar BRP: Stap 0: Valideer model. */
    LO3_NAAR_BRP_VALIDEER,
    /** LO3 naar BRP: Stap 1a: Extra documentatie toevoegen. */
    LO3_NAAR_BRP_EXTRA_DOCUMENTATIE,
    /** LO3 naar BRP: Stap 1b: Splitsen (verbintenissen). */
    LO3_NAAR_BRP_SPLITSEN_VERBINTENISSEN,
    /** LO3 naar BRP: Stap 1c: Splitsen (ouders). */
    LO3_NAAR_BRP_SPLITSEN_OUDERS,
    /** LO3 naar BRP: Stap 2a: Conversie inhoud. */
    LO3_NAAR_BRP_CONVERSIE_INHOUD,
    /** LO3 naar BRP: Stap 2b: Verwijder redundantie. */
    LO3_NAAR_BRP_VERWIJDER_REDUNDANTIE,
    /** LO3 naar BRP: Stap 3: Conversie historie. */
    LO3_NAAR_BRP_CONVERSIE_HISTORIE,
    /** LO3 naar BRP: Stap 4: Afgeleide gegevens. */
    LO3_NAAR_BRP_AFGELEIDE_GEGEVENS,

    /** BRP naar LO3: Stap 0a: Valideren BRP persoonslijst. */
    BRP_NAAR_LO3_VALIDEER,
    /** BRP naar LO3: Stap 0b: Opschonen verbintenissen. */
    BRP_NAAR_LO3_OPSCHONEN_VERBINTENISSEN,
    /** BRP naar LO3: Stap 1: Bepalen gegevens in gegevens set. */
    BRP_NAAR_LO3_BEPALEN_GEGEVENS_SET,
    /** BRP naar LO3: Stap 2: Bepalen materiele historie. */
    BRP_NAAR_LO3_BEPALEN_MATERIELE_HISTORIE,
    /** BRP naar LO3: Stap 3: Converteer. */
    BRP_NAAR_LO3_CONVERTEREN,
    /** BRP naar LO3: Stap 4: Ouders samenvoegen. */
    BRP_NAAR_LO3_OUDERS_SAMENVOEGEN,
    /** BRP naar LO3: Stap 5: Opschonen relaties. */
    BRP_NAAR_LO3_OPSCHONEN_RELATIES,
    /** BRP naar LO3: Stap 6: juridisch geen ouder toevoegen. */
    BRP_NAAR_LO3_JURIDISCH_GEEN_OUDER_TOEVOEGEN,
    /** BRP naar LO3: Stap 7: Adellijke titel / Predikaat bepalen. */
    BRP_NAAR_LO3_ADELLIJKE_TITEL_PREDIKAAT_BEPALEN,
    /** BRP naar LO3: Stap 8: Opschorten in geval van emigratie. */
    BRP_NAAR_LO3_OPSCHORTEN_VOOR_EMIGRATIE,
    /** BRP naar LO3: Stap 9: Sorteren. */
    BRP_NAAR_LO3_SORTEREN;
}
