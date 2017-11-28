/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoonGeneriekVerzoek;

/**
 * Converteert GBA ZoekCriterium objecten naar BRP ZoekCriteria.
 */
public interface ZoekCriteriaMapper {
    /**
     * Converteer een ZoekCriterium object naar een ZoekCriteria.
     * @param criterium GBA zoek criterium object
     * @return BRP zoek criteria object
     */
    static ZoekPersoonGeneriekVerzoek.ZoekCriteria map(final ZoekCriterium criterium) {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        criteria.setElementNaam(criterium.getNaam());
        criteria.setZoekOptie(criterium.getZoekOptie(false));
        criteria.setWaarde(criterium.getWaarde());

        if (criterium.getOf() != null) {
            criteria.setOf(map(criterium.getOf()));
        }
        return criteria;
    }

    /**
     * Converteer een ZoekCriterium object naar een ZoekCriteria binnen een slim zoeken context.
     * @param criterium GBA zoek criterium object
     * @return BRP zoek criteria object
     */
    static ZoekPersoonGeneriekVerzoek.ZoekCriteria mapInSlimZoekenContext(final ZoekCriterium criterium) {
        ZoekPersoonGeneriekVerzoek.ZoekCriteria criteria = new ZoekPersoonGeneriekVerzoek.ZoekCriteria();
        criteria.setElementNaam(criterium.getNaam());
        criteria.setZoekOptie(criterium.getZoekOptie(true));
        criteria.setWaarde(criterium.getSlimZoekenWaarde());

        if (criterium.getOf() != null) {
            criteria.setOf(mapInSlimZoekenContext(criterium.getOf()));
        }
        return criteria;
    }

}
