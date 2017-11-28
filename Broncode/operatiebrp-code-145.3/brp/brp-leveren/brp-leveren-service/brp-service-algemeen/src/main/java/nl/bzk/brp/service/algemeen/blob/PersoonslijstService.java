/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.util.List;
import java.util.Set;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;

/**
 * Service voor het inlezen van de persoon uit de database.
 */
public interface PersoonslijstService {

    /**
     * Lees de 'afdruk' van een persoon in obv het technisch id.
     * @param persId Het technisch id van de persoon.
     * @return volledig Persoon
     */
    Persoonslijst getById(long persId);

    /**
     * Maak een persoonlijst obv de gegeven data.
     * @param persoonData persoon Blob data
     * @param afnemerindicatieData afnemerindicatieData Blob data
     * @param afnemerindicatieLockVersie afnemerindicatieData lock versie
     * @return de persoonslijst
     */
    Persoonslijst maak(byte[] persoonData, byte[] afnemerindicatieData, long afnemerindicatieLockVersie);

    /**
     * Levert een lijst van persoongegevens. Als er geen blob aanwezig dan exception. Ook bevat deze persoonsgegevens geen
     * afnemerindicaties
     * @param persoonIds persoonIds
     * @return de personen
     * @throws StapException fout bij ophalen personen voor zoeken
     */
    List<Persoonslijst> getByIdsVoorZoeken(Set<Long> persoonIds) throws StapException;
}
