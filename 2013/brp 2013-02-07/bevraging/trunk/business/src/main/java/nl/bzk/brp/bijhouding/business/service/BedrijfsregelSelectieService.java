/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import java.util.List;

import nl.bzk.brp.domein.ber.SoortBericht;
import nl.bzk.brp.domein.brm.Regelimplementatiesituatie;
import nl.bzk.brp.domein.kern.RedenOpschorting;
import nl.bzk.brp.domein.kern.Verantwoordelijke;


/**
 * Service om te bepalen welke regels gevalideerd moeten worden.
 */
public interface BedrijfsregelSelectieService {

    /**
     * Zoekt voor elke bedrijfsregel het meest specifieke actieve gedrag dat voldoet aan de opgegeven criteria. Zie
     * {@link nl.bzk.brp.bijhouding.domein.repository.RegelGedragRepository} voor uitleg wanneer een gedrag
     * voldoet aan de criteria. Als er meerdere actieve gedrag objecten zijn die voldoen aan de criteria dan wordt de
     * meest specifieke als volgt bepaald:<br>
     *
     * 1) Een gedrag waabij het stelsel exact overeenkomt met het filter is specifieker dan een die
     * niet exact overeenkomt.<br>
     *
     * 3) Bij gelijke verantwoordelijke is een gedrag waabij de opschortingsvlag exact
     * overeenkomt met het filter specifieker dan een die niet exact overeenkomt.<br>
     *
     * 2) Bij gelijke verantwoordelijke en opschortingsvlag is een gedrag waabij de reden opschorting exact overeenkomt
     * met het filter specifieker dan een die niet exact overeenkomt.<br>
     *
     * 3) Bij gelijke verantwoordelijke, opschortingsvlag en reden opschorting is het gedrag even specifiek. Het is dus
     * mogelijk dat er meer dan 1 gedrag per bedrijfsregel wordt teruggegeven.
     *
     * @param soortBericht de berichtsoort waarop gefilterd moet worden
     * @param verantwoordelijke de verantwoordelijke waarop gefilterd moet worden, of null indien niet bekend
     * @param isOpschorting opschortingsvlag waarop gefilterd moet worden, of null indien niet bekend
     * @param redenOpschorting reden van de opschorting waarop gefilterd moet worden, of null indien niet bekend
     * @return voor elke bedrijfsregel met actief gedrag dat voldoet aan de criteria, het meest specifieke actieve
     *         gedrag dat voldoet aan de criteria
     */
    List<Regelimplementatiesituatie> zoekMeestSpecifiekBedrijfsregelGedrag(SoortBericht soortBericht,
            Verantwoordelijke verantwoordelijke, Boolean isOpschorting, RedenOpschorting redenOpschorting);

}
