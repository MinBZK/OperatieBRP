/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;


/**
 * Interface voor de repository die met IST heeft te maken.
 */
public interface IstTabelRepository {

    /**
     * Haalt alle IST stapels op voor de meegegeven persoon.
     *
     * @param persoon De persoon waarvoor we de IST stapels ophalen.
     * @return de lijst van stapels.
     */
    List<Stapel> leesIstStapels(final PersoonHisVolledig persoon);
}
