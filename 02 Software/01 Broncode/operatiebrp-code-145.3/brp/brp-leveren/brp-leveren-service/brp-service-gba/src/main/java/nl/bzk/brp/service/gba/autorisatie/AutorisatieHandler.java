/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.gba.autorisatie;

import java.util.function.Function;
import java.util.function.Supplier;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;

/**
 * Helperklasse voor doorlopen autorisatie in een autorisatiebundel en opstellen antwoord bericht
 * @param <U> Subklasse van Antwoord
 */
public final class AutorisatieHandler<U> {

    private final GbaAutorisaties gbaAutorisaties;

    /**
     * Public constructor.
     * @param gbaAutorisaties GbaAutorisaties helperklasse voor ophalen/bepalen autorisaties
     */
    public AutorisatieHandler(final GbaAutorisaties gbaAutorisaties) {
        this.gbaAutorisaties = gbaAutorisaties;
    }

    /**
     * Voer de meegegeven functies uit.
     * @param partijCode De partij waarvoor de autorisatie wordt bepaald
     * @param rol De rol van de partij
     * @param soortDienst De soort dienst waarvoor de autorisatie wordt opgehaald
     * @param success De functie indien gevonden
     * @param failure De functie indien niet gevonden
     * @return antwoord bericht samengesteld door de betreffende functie
     */
    public U verwerkMetAutorisatie(final String partijCode, final Rol rol, final SoortDienst soortDienst, final Function<Autorisatiebundel, U> success,
                                   final Supplier<U> failure) {
        return gbaAutorisaties.bepaalAutorisatie(partijCode, rol, soortDienst)
                .map(success)
                .orElseGet(failure);
    }
}
