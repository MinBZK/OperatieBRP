/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Collection;
import java.util.List;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Voorvoegsel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenBeeindigingNationaliteit;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RedenOpnameNationaliteit;

/**
 * Stamtabel service.
 */
public interface StamtabelRepository {

    /**
     * Geef een lijst van alle geldige gemeentes.
     * 
     * @return lijst van alle geldige gemeentes
     */
    Collection<Gemeente> findAllGemeentes();

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
    Collection<Short> findAllLandOfGebiedCodes();

    /**
     * Geef een lijst van alle geldige nationaliteit codes.
     * 
     * @return lijst van alle geldige nationaliteit codes
     */
    Collection<Short> findAllNationaliteitCodes();

    /**
     * Geef een lijst van alle geldige plaatsnamen.
     * 
     * @return lijst van alle geldige plaatsnamen
     */
    Collection<String> findAllPlaatsnamen();

    /**
     * Geef een lijst van alle geldige namen openbare ruimte.
     * 
     * @return lijst van alle geldige namen openbare ruimte
     */
    Collection<String> findAllNamenOpenbareRuimte();

    /**
     * Geeft een lijst van alle verblijfstitels.
     * 
     * @return de lijst met alle verblijfstitels
     */
    List<Verblijfsrecht> findAllVerblijfsrecht();

    /**
     * Geeft een lijst met alle voorvoegsels.
     * 
     * @return de lijst met alle voorvoegsels
     */
    List<Voorvoegsel> findAllVoorvoegsels();

    /**
     * @return de lijst met alle redenen opename nationaliteit..
     */
    List<RedenOpnameNationaliteit> findAllRedenOpnameNationaliteit();

    /**
     * @return de lijst met alle redenen beeindiging nationaliteit.
     */
    List<RedenBeeindigingNationaliteit> findAllRedenBeeindigingNationaliteit();

    /**
     * @return het soort document dat gebruikt wordt voor conversie.
     * @param naam de naam die het soort document beschrijft
     */
    SoortDocument findSoortDocumentConversie(final String naam);
}
