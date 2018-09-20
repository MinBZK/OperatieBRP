/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.Collection;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;

/**
 * Stamtabel service.
 */
public interface StamTabelRepository {

    /**
     * Geef een lijst van alle geldige gemeentes.
     *
     * @return lijst van alle geldige gemeentes
     */
    Collection<Gemeente> findAllGemeente();

    /**
     * Geef een lijst van alle geldige gemeente codes.
     *
     * @return lijst van alle geldige gemeente codes
     */
    Collection<Short> findAllGemeenteCodes();

    /**
     * Geef een lijst van alle geldige land codes.
     *
     * @return lijst van alle geldige land codes
     */
    Collection<Short> findAllLandCodes();

    /**
     * Geef een lijst van alle geldige nationaliteit codes.
     *
     * @return lijst van alle geldige nationaliteit codes
     */
    Collection<Short> findAllNationaliteitCodes();

    /**
     * Geeft een lijst van alle verblijfstitels.
     *
     * @return de lijst met alle verblijfstitels
     */
    List<AanduidingVerblijfsrecht> findAllAanduidingVerblijfsrecht();

    /**
     * Geeft een lijst met alle verkrijging nl schap codes.
     *
     * @return de lijst met alle verkrijging nl schap codes.
     */
    List<RedenVerkrijgingNLNationaliteit> findAllVerkrijgingNLNationaliteit();

    /**
     * Geeft een lijst met alle verlies nl schap codes.
     *
     * @return de lijst met alle verlies nl schap codes.
     */
    List<RedenVerliesNLNationaliteit> findAllVerliesNLNationaliteit();

    /**
     * Geeft een lijst met alle plaatsen.
     *
     * @return de lijst met alle plaatsen.
     */
    List<Plaats> findAllPlaats();
}
