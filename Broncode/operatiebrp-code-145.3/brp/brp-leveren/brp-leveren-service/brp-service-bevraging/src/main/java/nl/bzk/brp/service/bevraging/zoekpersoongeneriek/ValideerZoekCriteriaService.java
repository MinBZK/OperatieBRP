/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;

import java.util.Set;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;

/**
 * ValideerZoekCriteriaService.
 * @param <T> specifiek verzoek type dat gevalideerd dient te worden
 */
@FunctionalInterface
public interface ValideerZoekCriteriaService<T extends ZoekPersoonGeneriekVerzoek> {
    /**
     * @param bevragingVerzoek bevragingVerzoek
     * @param autorisatiebundel autorisatiebundel
     * @return eventuele meldinden
     */
    Set<Melding> valideerZoekCriteria(T bevragingVerzoek, Autorisatiebundel autorisatiebundel);
}
