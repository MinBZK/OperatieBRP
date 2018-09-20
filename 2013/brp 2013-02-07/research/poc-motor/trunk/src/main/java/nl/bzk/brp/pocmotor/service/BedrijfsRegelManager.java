/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.service;

import java.util.List;

import nl.bzk.brp.pocmotor.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;

/**
 * Interface voor de bedrijfsregel manager. Deze service bepaald, op basis van een bericht, welke bedrijfsregels er
 * geexecuteerd dienen te worden.
 */
public interface BedrijfsRegelManager {

    /**
     * Retourneert een voor de actie in een bericht geldende lijst van bedrijfsregels.
     *
     * @param actie de actie in een bericht waarvoor de bedrijfsregels moeten worden bepaald.
     * @return de lijst van bedrijfsregels die moeten worden gecontroleerd voor de actie in een bericht.
     */
    List<? extends BedrijfsRegel> getLijstVanBedrijfsRegels(BRPActie actie);

}
