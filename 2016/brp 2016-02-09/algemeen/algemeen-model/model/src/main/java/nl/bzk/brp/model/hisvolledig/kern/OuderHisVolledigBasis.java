/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.MaterieleHistorieSet;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;

/**
 * Interface voor Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface OuderHisVolledigBasis extends ModelPeriode, BetrokkenheidHisVolledig, BrpObject {

    /**
     * Retourneert de historie van His Ouder Ouderschap.
     *
     * @return Historie met His Ouder Ouderschap
     */
    MaterieleHistorieSet<HisOuderOuderschapModel> getOuderOuderschapHistorie();

    /**
     * Retourneert de historie van His Ouder Ouderlijk gezag.
     *
     * @return Historie met His Ouder Ouderlijk gezag
     */
    MaterieleHistorieSet<HisOuderOuderlijkGezagModel> getOuderOuderlijkGezagHistorie();

}
