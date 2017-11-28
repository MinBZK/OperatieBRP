/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.expressie.Expressie;


/**
 * nw versie tbv efficienter kunnen inladen in cache.
 */
public interface LeveringsAutorisatieCache {

    /**
     * (Her)laad de cache.
     * @param partijData partijData
     * @return de cache entry
     */
    CacheEntry herlaad(PartijCacheImpl.Data partijData);

    /**
     * Geef alle toegangLeveringsautorisaties uit cache.
     * @return leveringsautorisaties
     */
    List<ToegangLeveringsAutorisatie> geefAlleToegangleveringsautorisaties();

    /**
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param dienst dienst
     * @return expressie
     */
    Expressie geefPopulatiebeperking(ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, Dienst dienst);

    /**
     * @param dienst dienst
     * @return expressie
     */
    Expressie geefAttenderingExpressie(Dienst dienst);

    /**
     * @param toegangLeveringsAutorisatie toegangLeveringsAutorisatie
     * @param dienst dienst
     * @return geldige elementen
     */
    List<AttribuutElement> geefGeldigeElementen(ToegangLeveringsAutorisatie toegangLeveringsAutorisatie, Dienst dienst);

    /**
     * Geef het leveringsautorisatie met naam.
     * @param leveringautorisatieId de leveringsautorisatie naam
     * @return abonnnement het leveringsautorisatie met de gegeven naam
     */
    Leveringsautorisatie geefLeveringsautorisatie(final int leveringautorisatieId);

    /**
     * Geef toegangLeveringsautorisaties voor geautoriseerde partij.
     * @param partijCode de partij code
     * @return een lijst met toegangleveringsautorisatie voor geautoriseerde partij
     */
    List<ToegangLeveringsAutorisatie> geefToegangleveringautorisatiesVoorGeautoriseerdePartij(String partijCode);

    /**
     * Geef toegangLeveringsautorisaties met leveringsautorisatie naam en partijcode.
     * @param leveringautorisatieId het leveringautorisatieId
     * @param partijCode de partij code
     * @return het toegang leveringsautorisatie
     */
    ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(final int leveringautorisatieId, final String partijCode);

    /**
     * Geef toegangLeveringsautorisatie voor id.
     * @param toegangLeveringsaugtorisatieId id van toegangLeveringsautorisatie
     * @return het toegang leveringsautorisatie
     */
    ToegangLeveringsAutorisatie geefToegangLeveringsautorisatie(Integer toegangLeveringsaugtorisatieId);

    /**
     * @return autorisatiebundels
     */
    List<Autorisatiebundel> geefAutorisatieBundelsVoorMutatielevering();

    /**
     * Geef de dienst op basis van dienstId.
     * @param dienstId id van de dienst
     * @return de dienst
     */
    Dienst geefDienst(final int dienstId);
}
