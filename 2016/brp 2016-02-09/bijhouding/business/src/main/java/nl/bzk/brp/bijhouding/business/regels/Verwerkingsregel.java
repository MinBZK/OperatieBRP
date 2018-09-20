/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import java.util.List;
import nl.bzk.brp.business.regels.RegelInterface;

/**
 * Een verwerkingsregel komt overeen met de verwerking van de data van een bepaalde
 * groep uit het model. In de functionele specificaties is ook sprake van verwerkingsregels (VR.....).
 * Elke implementerende klasse representeert één bepaalde verwerkingsregel uit de specs.
 * Implementerende klasses wordt geadviseerd gebruik te maken van AbstractVerwerkingsregel als basis klasse.
 */
public interface Verwerkingsregel extends RegelInterface {

    /**
     * Verrijk het bericht met eventuele ontbrekende data die afleidbaar is.
     * Het is de bedoeling om dit te minimaliseren! Als stelregel: alleen
     * attributen aanvullen in bestaande groepen in het bericht en liefst
     * alleen stamgegevens. Zie tevens in de specs welke verrijking nodig is.
     * Dit is dus expliciet geen plek om nieuwe, afgeleide groepen in het bericht toe te voegen!
     */
    void verrijkBericht();

    /**
     * Neem de informatie voor de betreffende groep over uit het bericht
     * in de betreffende plek van de model.
     */
    void neemBerichtDataOverInModel();

    /**
     * Verzamel alle afleidingsregels die op deze verwerkingsregel van toepassing zijn.
     * Elke regel instantie dient toegevoegd te worden door een aanroep aan voegAfleidingsregelToe.
     * Let op: deze methode maar 1 keer aanroepen per life cycle van een Verwerkingsregel object.
     */
    void verzamelAfleidingsregels();

    /**
     * Voeg een afleidingsregel toe.
     *
     * @param afleidingsregel de regel
     */
    void voegAfleidingsregelToe(final Afleidingsregel afleidingsregel);

    /**
     * Geef alle afleidingsregel instanties terug die van toepassing zijn om uit te voeren
     * na het overnemen van de data uit het bericht. Let op: roep eerst verzamelAfleidingsregels
     * aan, voordat deze methode aangeroepen wordt.
     *
     * @return een lijst met afleidingsregels (mag niet null zijn)
     */
    List<Afleidingsregel> getAfleidingsregels();

}
