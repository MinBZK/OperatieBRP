/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;

/**
 * Interface voor de repository die met conversie heeft te maken.
 */
//
/*
 * squid:S1609 @FunctionalInterface annotation should be used to flag Single Abstract Method
 * interfaces
 *
 * False positive, dit hoort niet een functional interface te zijn. Dit is een gewone interface met
 * slechts 1 methode.
 */
public interface VerConvRepository {

    /**
     * Zoek de LO3 Voorkomen (Lo3Herkomst in migratie) voor een actie.
     *
     * @param actieId actie id
     * @return lo3 voorkomen
     */
    Lo3Voorkomen zoekLo3VoorkomenVoorActie(final Long actieId);
}
