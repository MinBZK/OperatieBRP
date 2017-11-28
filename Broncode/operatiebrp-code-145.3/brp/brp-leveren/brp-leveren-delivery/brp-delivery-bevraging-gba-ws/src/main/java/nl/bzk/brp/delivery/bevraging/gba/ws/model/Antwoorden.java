/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.model;

import java.util.List;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.builder.WebserviceAntwoordBuilder;

/**
 * Deze interface bevat statische factory methods om Antwoord object te construeren.
 */
public interface Antwoorden {
    /**
     * Maakt een foutief antwoord aan de hand van het meegegeven AntwoordBerichtResultaat. Eventueel kunnen de
     * meegegeven args gebruikt worden om placeholders in de resultaat omschrijving te vervangen.
     * @param resultaat AntwoordBerichtResultaat
     * @param args kunnen gebruikt worden om placeholders in de resultaat omschrijving te vervangen
     * @return antwoord object
     */
    static Antwoord foutief(final AntwoordBerichtResultaat resultaat, final String... args) {
        Antwoord antwoord = new Antwoord();
        antwoord.setResultaat(Resultaten.of(resultaat, args));
        return antwoord;
    }

    /**
     * Maakt een succes antwoord object.
     * @param builder builder om een WebserviceAntwoord te creeëren adhv een persoonslijsten
     * @param persoonslijsten persoonslijsten die in het antwoord verwerkt moeten worden
     * @param rubrieken lijst van te leveren rubrieken
     * @return antwoord object
     */
    static Antwoord ok(final WebserviceAntwoordBuilder builder, final List<Persoonslijst> persoonslijsten, final List<String> rubrieken) {
        Antwoord antwoord = new Antwoord();
        antwoord.setResultaat(Resultaten.of(AntwoordBerichtResultaat.OK));
        antwoord.setPersoonslijsten(WebserviceAntwoordMapper.map(builder.build(persoonslijsten).rubrieken(rubrieken)));
        return antwoord;
    }

}
