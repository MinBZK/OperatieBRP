/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel;

/**
 * Interface voor Partij \ Onderzoek.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PartijOnderzoekHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Partij \ Onderzoek.
     *
     * @return Historie met His Partij \ Onderzoek
     */
    FormeleHistorieSet<HisPartijOnderzoekModel> getPartijOnderzoekHistorie();

    /**
     * Retourneert Partij van Partij \ Onderzoek.
     *
     * @return Partij van Partij \ Onderzoek
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Onderzoek van Partij \ Onderzoek.
     *
     * @return Onderzoek van Partij \ Onderzoek
     */
    OnderzoekHisVolledig getOnderzoek();

}
