/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.statisch;

/**
 * Statisch object type Soort bericht.
 */
public enum Soortbericht {
    /**
     * Dummy waarde ivm ordinal 0.*
     */
    DUMMY("DUMMY"),
    /**
     * Migratie Verhuizing Bijhouding. *
     */
    MIGRATIE_VERHUIZING_BIJHOUDING("Migratie Verhuizing Bijhouding"),
    /**
     * Migratie Verhuizing BijhoudingAntwoord.*
     */
    MIGRATIE_VERHUIZING_BIJHOUDING_ANTWOORD("Migratie Verhuizing BijhoudingAntwoord"),
    /**
     * Migratie CorrectieAdresBinnenNL Bijhouding.*
     */
    MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING("Migratie CorrectieAdresBinnenNL Bijhouding"),
    /**
     * Migratie CorrectieAdresBinnenNL BijhoudingAntwoord.*
     */
    MIGRATIE_CORRECTIEADRESBINNENNL_BIJHOUDING_ANTWOORD("Migratie CorrectieAdresBinnenNL BijhoudingAntwoord"),
    /**
     * Afstamming InschrijvingAangifteGeboorte Bijhouding.*
     */
    AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING("Afstamming InschrijvingAangifteGeboorte Bijhouding"),
    /**
     * Afstamming InschrijvingAangifteGeboorte BijhoudingAntwoord.*
     */
    AFSTAMMING_INSCHRIJVINGAANGIFTEGEBOORTE_BIJHOUDING_ANTWOORD(
            "Afstamming InschrijvingAangifteGeboorte BijhoudingAntwoord"),
    /**
     * HuwelijkPartnerschap RegistratieHuwelijk Bijhouding.*
     */
    HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING("HuwelijkPartnerschap RegistratieHuwelijk Bijhouding"),
    /**
     * HuwelijkPartnerschap RegistratieHuwelijk BijhoudingAntwoord.*
     */
    HUWELIJKPARTNERSCHAP_REGISTRATIEHUWELIJK_BIJHOUDING_ANTWOORD(
            "HuwelijkPartnerschap RegistratieHuwelijk BijhoudingAntwoord"),
    /**
     * HuwelijkPartnerschap RegistratiePartnerschap Bijhouding.*
     */
    HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING("HuwelijkPartnerschap RegistratiePartnerschap Bijhouding"),
    /**
     * HuwelijkPartnerschap RegistratiePartnerschap BijhoudingAntwoord.*
     */
    HUWELIJKPARTNERSCHAP_REGISTRATIEPARTNERSCHAP_BIJHOUDING_ANTWOORD(
            "HuwelijkPartnerschap RegistratiePartnerschap BijhoudingAntwoord"),
    /**
     * Vraag DetailsPersoon Bevragen.*
     */
    VRAAG_DETAILSPERSOON_BEVRAGEN("Vraag DetailsPersoon Bevragen"),
    /**
     * Vraag DetailsPersoon BevragenAntwoord.*
     */
    VRAAG_DETAILSPERSOON_BEVRAGEN_ANTWOORD("Vraag DetailsPersoon BevragenAntwoord"),
    /**
     * Vraag PersonenOpAdresInclusiefBetrokkenheden Bevragen.*
     */
    VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN("Vraag PersonenOpAdresInclusiefBetrokkenheden Bevragen"),
    /**
     * Vraag PersonenOpAdresInclusiefBetrokkenheden BevragenAntwoord.*
     */
    VRAAG_PERSONENOPADRESINCLUSIEFBETROKKENHEDEN_BEVRAGEN_ANTWOORD(
            "Vraag PersonenOpAdresInclusiefBetrokkenheden BevragenAntwoord"),
    /**
     * Vraag KandidaatVader Bevragen.*
     */
    VRAAG_KANDIDAATVADER_BEVRAGEN("Vraag KandidaatVader Bevragen"),
    /**
     * Vraag KandidaatVader BevragenAntwoord.*
     */
    VRAAG_KANDIDAATVADER_BEVRAGEN_ANTWOORD("Vraag KandidaatVader BevragenAntwoord");

    /**
     * Constructor.
     *
     * @param naam Naam.
     */
    Soortbericht(final String naam) {
        this.naam = naam;
    }

    private String naam;

    public String getNaam() {
        return naam;
    }
}
