/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRolAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.autaut.HisToegangBijhoudingsautorisatieModel;

/**
 * Interface voor Toegang bijhoudingsautorisatie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface ToegangBijhoudingsautorisatieHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Toegang bijhoudingsautorisatie.
     *
     * @return Historie met His Toegang bijhoudingsautorisatie
     */
    FormeleHistorieSet<HisToegangBijhoudingsautorisatieModel> getToegangBijhoudingsautorisatieHistorie();

    /**
     * Retourneert Geautoriseerde van Toegang bijhoudingsautorisatie.
     *
     * @return Geautoriseerde van Toegang bijhoudingsautorisatie
     */
    PartijRolAttribuut getGeautoriseerde();

    /**
     * Retourneert Ondertekenaar van Toegang bijhoudingsautorisatie.
     *
     * @return Ondertekenaar van Toegang bijhoudingsautorisatie
     */
    PartijAttribuut getOndertekenaar();

    /**
     * Retourneert Transporteur van Toegang bijhoudingsautorisatie.
     *
     * @return Transporteur van Toegang bijhoudingsautorisatie
     */
    PartijAttribuut getTransporteur();

}
