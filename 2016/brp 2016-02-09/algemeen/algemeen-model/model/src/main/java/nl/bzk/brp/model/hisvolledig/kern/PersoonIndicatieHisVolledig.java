/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import nl.bzk.brp.model.HistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;


/**
 * Interface voor Persoon \ Indicatie.
 */
public interface PersoonIndicatieHisVolledig<T extends HisPersoonIndicatieModel> extends ModelPeriode, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Indicatie.
     *
     * @return Historie met His Persoon \ Indicatie
     */
    HistorieSet<T> getPersoonIndicatieHistorie();

    /**
     * Retourneert ID van Persoon \ Indicatie.
     *
     * @return ID van Persoon \ Indicatie
     */
    Integer getID();

    /**
     * Retourneert Persoon van Persoon \ Indicatie.
     *
     * @return Persoon van Persoon \ Indicatie
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Soort van Persoon \ Indicatie.
     *
     * @return Soort van Persoon \ Indicatie
     */
    SoortIndicatieAttribuut getSoort();

}
