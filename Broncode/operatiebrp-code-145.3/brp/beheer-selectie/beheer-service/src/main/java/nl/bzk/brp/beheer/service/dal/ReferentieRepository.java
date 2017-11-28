/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.dal;

/**
 * Repository om referentie objecten mee op te halen.
 */
public interface ReferentieRepository {

    /**
     * Geef een referentie naar een object van type.
     * @param clazz de klasse
     * @param key de primaire sleutel
     * @param <T> het type
     * @return het referentieobject
     */
    <T> T getReferentie(Class<T> clazz, Object key);
}
