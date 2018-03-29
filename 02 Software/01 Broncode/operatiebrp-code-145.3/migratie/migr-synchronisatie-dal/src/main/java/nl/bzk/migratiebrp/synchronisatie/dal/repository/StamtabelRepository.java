/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository;

import java.util.Collection;
import java.util.List;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpnameNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;

/**
 * Stamtabel service.
 */
public interface StamtabelRepository {

    /**
     * Geef een lijst van alle geldige gemeentes.
     * @return lijst van alle geldige gemeentes
     */
    Collection<Gemeente> findAllGemeentes();

    /**
     * Geef een lijst van alle geldige gemeentes.
     * @return lijst van alle geldige gemeentes
     */
    Collection<Partij> findAllPartijen();

    /**
     * Geef de partij behorende bij de code.
     * @param partijCode De code van de benodigde partij
     * @return gevonden Partij
     */
    Partij findPartijByCode(String partijCode);

    /**
     * Geef een lijst van alle geldige gemeente codes.
     * @return lijst van alle geldige gemeente codes
     */
    Collection<String> findAllGemeenteCodes();

    /**
     * Geef een lijst van alle geldige land codes.
     * @return lijst van alle geldige land codes
     */
    Collection<String> findAllLandOfGebiedCodes();

    /**
     * Geef een lijst van alle geldige nationaliteit codes.
     * @return lijst van alle geldige nationaliteit codes
     */
    Collection<String> findAllNationaliteitCodes();

    /**
     * Geef een lijst van alle geldige plaatsnamen.
     * @return lijst van alle geldige plaatsnamen
     */
    Collection<String> findAllPlaatsnamen();

    /**
     * Geef een lijst van alle geldige namen openbare ruimte.
     * @return lijst van alle geldige namen openbare ruimte
     */
    Collection<String> findAllNamenOpenbareRuimte();

    /**
     * Geeft een lijst van alle verblijfstitels.
     * @return de lijst met alle verblijfstitels
     */
    List<Verblijfsrecht> findAllVerblijfsrecht();

    /**
     * Geeft een lijst met alle voorvoegsels.
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
     * @param naam de naam die het soort document beschrijft
     * @return het soort document dat gebruikt wordt voor conversie.
     */
    SoortDocument findSoortDocumentConversie(String naam);

    /**
     * @return een collectie met alle autorisaties van afgifte buitenlands persoonsnummer.
     */
    Collection<String> findAllAutorisatiesVanAfgifteBuitenlandsPersoonsnummer();
}
