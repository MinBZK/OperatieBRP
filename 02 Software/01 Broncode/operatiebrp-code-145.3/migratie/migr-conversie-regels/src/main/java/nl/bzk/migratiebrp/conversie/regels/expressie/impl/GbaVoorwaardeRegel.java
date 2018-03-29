/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.expressie.impl;

import nl.bzk.migratiebrp.conversie.regels.expressie.impl.criteria.Expressie;

/**
 * Interface voor gba voorwaarde regels.
 */
public interface GbaVoorwaardeRegel {

    /**
     * Geeft de BRP expressie van de GBA voorwaarderegel.
     * @param voorwaardeRegel De te vertalen voorwaarderegel
     * @return de BRP expressie
     * @throws GbaVoorwaardeOnvertaalbaarExceptie Indien de regel niet te vertalen is
     */
    Expressie getBrpExpressie(RubriekWaarde voorwaardeRegel) throws GbaVoorwaardeOnvertaalbaarExceptie;

    /**
     * Geeft de volgorde aan waarin deze regel moet worden uitgevoerd. Hoe hoger hoe later. De standaard voorwaardeRegel
     * heeft een volgorde van 999.
     * @return de volgorde
     */
    int volgorde();

    /**
     * Het filter om te bepalen of een voorwaarderegel door de implementatie moet worden verwerkt.
     * @param voorwaarde de voorwaarde die verwerkt moet worden.
     * @return true indien het een geschikte implementatie is
     */
    boolean filter(String voorwaarde);

}
