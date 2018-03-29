/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging;

/**
 * Persoonslijst verwerker meldingen.
 */
public enum PlVerwerkerMelding {

    /**
     * Verwerker synchronisatie.
     */
    SERVICE("service", "Verwerk bericht."),

    /**
     * Verwerker initiele vulling.
     */
    SYNCHRONISATIE_VERWERKER_INITIELE_VULLING("verwerker-initiele-vulling", "Verwerk synchronisatie als 'initiele vulling'."),
    /**
     * Verwerker synchronisatie.
     */
    SYNCHRONISATIE_VERWERKER_SYNCHRONISATIE("verwerker-sync", "Verwerk synchronisatie."),
    /**
     * Verwerker beheerders keuze.
     */
    SYNCHRONISATIE_VERWERKER_BEHEERDERS_KEUZE("verwerker-beheerders-keuze", "Verwerk beheerders keuze."),

    /**
     * Verwerker beheerderskeuze nieuw.
     */
    PL_VERWERKER_KEUZE_NIEUW("verwerker-keuze-nieuw", "Verwerk aangeboden persoonslijst als geforceerd opnemen als nieuwe persoonslijst."),
    /**
     * Verwerker beheerderskeuze vervang.
     */
    PL_VERWERKER_KEUZE_VERVANG("verwerker-keuze-vervang",
            "Verwerk aangeboden persoonslijst als geforceerd opnemen als vervanging van bestaande persoonslijst."),
    /**
     * Verwerker synchronisatie.
     */
    PL_VERWERKER_SYNCHRONISATIE("verwerker-synchronisatie", "Verwerk aangeboden persoonslijst als reguliere synchronisatie.");

    private final String key;
    private final String omschrijving;

    PlVerwerkerMelding(final String key, final String omschrijving) {
        this.key = key;
        this.omschrijving = omschrijving;
    }

    /**
     * Geef de waarde van key.
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * Geef de waarde van omschrijving.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

}
