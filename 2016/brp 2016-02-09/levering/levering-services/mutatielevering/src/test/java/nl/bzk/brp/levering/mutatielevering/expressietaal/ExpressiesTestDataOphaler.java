/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.expressietaal;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Expressies test data ophaler, haalt zaken uit database voor het etsten van expressies.
 */
public interface ExpressiesTestDataOphaler {

    /**
     * Haalt alle expressies uit database.
     *
     * @return lijst van alle expressies uit database
     */
    List<String> getAlleExpressiesUitDatabase();

    /**
     * Haalt alle personen uit database.
     *
     * @return lijst van alle personen uit database
     */
    List<PersoonHisVolledigImpl> getAllePersonenUitDatabase();

}
