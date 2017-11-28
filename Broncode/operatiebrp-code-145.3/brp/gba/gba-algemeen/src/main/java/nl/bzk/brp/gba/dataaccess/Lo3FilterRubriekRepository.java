/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;

/**
 * Interface van de repositorie voor het ophalen van expressies.
 */
//
/*
 * squid:S1609 @FunctionalInterface annotation should be used to flag Single Abstract Method
 * interfaces
 *
 * False positive, dit hoort niet een functional interface te zijn. Dit is een gewone interface met
 * slechts 1 methode.
 */
public interface Lo3FilterRubriekRepository {

    /**
     * Haalt alle LO3 filter rubrieken op die aan een dienstbundel gekoppeld zijn.
     * @param dienstbundelId De dienstbundel id
     * @return De lijst van lo3 filter rubrieken.
     */
    List<String> haalLo3FilterRubriekenVoorDienstbundel(final Integer dienstbundelId);

}
