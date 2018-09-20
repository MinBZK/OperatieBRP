/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;

/**
 * Interface voor de repository die met conversie heeft te maken.
 */
public interface VerConvRepository {

    /**
     * Zoek de LO3 Voorkomen (Lo3Herkomst in migratie) voor een actie.
     *
     * @param actie actie
     * @return lo3 voorkomen
     */
    LO3Voorkomen zoekLo3VoorkomenVoorActie(Actie actie);
}
