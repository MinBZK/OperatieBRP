/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.vraagPLVraag;
import static org.junit.Assert.assertEquals;

import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import org.junit.Test;

public class VraagPLIT extends AbstractIT {

    @Test
    public void happyFlow() {
        VraagPL vraagPL = vraagPLVraag(param(10110, "1234567890"), param(10120, "003000020"));
        Antwoord antwoord = request(vraagPL, "001801");
        assertSuccess(antwoord);
        assertEquals("Antwoord moet 1 persoonslijst bevatten", 1, antwoord.getPersoonslijsten().size());
        assertEquals("ANummer moet correct zijn", "1234567890",
                antwoord.getPersoonslijsten().get(0).getCategoriestapels().get(0).getCategorievoorkomens().get(0).getElementen().get(0).getWaarde());
    }

    @Test
    public void slimZoeken() {
        VraagPL vraagPL = vraagPLVraag(param(10110, "1234*"));
        Antwoord antwoord = request(vraagPL, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_032.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_032.getCode());
        assertEquals(AntwoordBerichtResultaat.TECHNISCHE_FOUT_032.getOmschrijving(), antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void badFlowGeenBijhouder() {
        VraagPL vraagPL = vraagPLVraag(param(10110, "1234567890"), param(10120, "003000020"));
        Antwoord antwoord = request(vraagPL, "002101");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_027.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_027.getCode());
        assertEquals(AntwoordBerichtResultaat.TECHNISCHE_FOUT_027.getOmschrijving(), antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void numeriekElementBevatGeenGetal() {
        VraagPL vraag = vraagPLVraag(param(10120, "Aap"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_022.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_022.getCode());
        assertEquals("Numeriek zoekcriterium 10120 bevat geen numerieke waarde", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void zoekwaardeLangerDanToegestaan() {
        VraagPL vraag = vraagPLVraag(param(81010, "1234567890987654321"));
        Antwoord antwoord = request(vraag, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_026.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_026.getCode());
        assertEquals("Onjuiste lengte voor rubriek: 81010=1234567890987654321", antwoord.getResultaat().getOmschrijving());
    }

    @Test
    public void foutX19() {
        VraagPL vraagPL = vraagPLVraag(param(10240, "Janssen"));
        Antwoord antwoord = request(vraagPL, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_019.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_019.getCode());
    }

    @Test
    public void foutX18() {
        VraagPL vraagPL = vraagPLVraag(param(10110, "1234567890"), param(77010, "0"));
        Antwoord antwoord = request(vraagPL, "001801");
        assertAntwoord(antwoord, AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getLetter(), AntwoordBerichtResultaat.TECHNISCHE_FOUT_018.getCode());
    }
}
