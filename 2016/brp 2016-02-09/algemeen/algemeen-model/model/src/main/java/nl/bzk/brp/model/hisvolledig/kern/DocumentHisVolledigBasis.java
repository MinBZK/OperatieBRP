/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;

/**
 * Interface voor Document.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface DocumentHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert de historie van His Document.
     *
     * @return Historie met His Document
     */
    FormeleHistorieSet<HisDocumentModel> getDocumentHistorie();

    /**
     * Retourneert Soort van Document.
     *
     * @return Soort van Document
     */
    SoortDocumentAttribuut getSoort();

}
