/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;

/**
 * Interface voor Persoon \ Verstrekkingsbeperking.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface PersoonVerstrekkingsbeperkingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Persoon \ Verstrekkingsbeperking.
     *
     * @return Historie met His Persoon \ Verstrekkingsbeperking
     */
    FormeleHistorieSet<HisPersoonVerstrekkingsbeperkingModel> getPersoonVerstrekkingsbeperkingHistorie();

    /**
     * Retourneert Persoon van Persoon \ Verstrekkingsbeperking.
     *
     * @return Persoon van Persoon \ Verstrekkingsbeperking
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Partij van Persoon \ Verstrekkingsbeperking.
     *
     * @return Partij van Persoon \ Verstrekkingsbeperking
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Omschrijving derde van Persoon \ Verstrekkingsbeperking.
     *
     * @return Omschrijving derde van Persoon \ Verstrekkingsbeperking
     */
    OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde();

    /**
     * Retourneert Gemeente verordening van Persoon \ Verstrekkingsbeperking.
     *
     * @return Gemeente verordening van Persoon \ Verstrekkingsbeperking
     */
    PartijAttribuut getGemeenteVerordening();

}
