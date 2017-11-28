/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Collection;
import java.util.Map;

/**
 * Interface voor a-laag sub-entiteiten van Persoon die een eigen verzameling historie hebben. Wordt
 * gebruikt bij de verschil-bepaling om bij verwijdering van deze entiteit de historie netjes naar
 * 'M'-rijen om te zetten.
 */
public interface ALaagHistorieVerzameling extends Entiteit {

    /**
     * Geef alle verzamelde historie rijen van deze entiteit.
     *
     * @return de verzamelde historie rijen
     */
    Map<String, Collection<FormeleHistorie>> verzamelHistorie();
}
