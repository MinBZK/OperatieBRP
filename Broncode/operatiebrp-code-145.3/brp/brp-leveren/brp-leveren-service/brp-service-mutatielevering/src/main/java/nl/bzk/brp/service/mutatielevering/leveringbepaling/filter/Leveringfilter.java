/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.mutatielevering.leveringbepaling.filter;

import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Container voor leveringfilters.
 */
interface Leveringfilter {

    /**
     * Bepaalt of de levering van een persoon kan doorgaan, gegeven de parameters en de functionele omschrijving in de UC.
     * @param persoon De persoon
     * @param populatie De positie van de persoon t.o.v. de populatieBeperking
     * @param leveringAutorisatie De leveringAutorisatie waarvoor de bepaling plaats vindt
     * @return als de levering om logische redenen mag doorgaan
     */
    boolean magLeveren(Persoonslijst persoon, Populatie populatie, Autorisatiebundel leveringAutorisatie) ;

}
