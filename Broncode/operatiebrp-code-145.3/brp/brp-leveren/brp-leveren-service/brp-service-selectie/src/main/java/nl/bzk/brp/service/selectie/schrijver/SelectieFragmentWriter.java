/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.service.selectie.algemeen.SelectieException;

/**
 * Schrijft een reeks XML berichten welke behoren tot een selectietaak naar bestand (een part).
 * Concat eventueel deze parts tot een groter geheel (fragment) om zodoende het aantal files te beperken.
 * <p>
 * Deze writer is impliciet threadsafe omdat het deel uit maakt van een JMS messagegroup.
 * Alle writes mbt een gegeven selectietaak zijn serieel.
 */
public interface SelectieFragmentWriter {
    /**
     * Schrijf tussenresultaat persoon fragment.
     * @param selectieBerichtSchrijfTaak selectieResultaat
     * @throws SelectieException SelectieException
     */
    void verwerk(SelectieFragmentSchrijfBericht selectieBerichtSchrijfTaak) throws SelectieException;
}
