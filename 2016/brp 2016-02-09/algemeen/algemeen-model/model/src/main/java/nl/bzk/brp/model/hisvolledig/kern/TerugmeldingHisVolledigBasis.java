/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.FormeleHistorieSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingContactpersoonModel;
import nl.bzk.brp.model.operationeel.kern.HisTerugmeldingModel;

/**
 * Interface voor Terugmelding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface TerugmeldingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert de historie van His Terugmelding.
     *
     * @return Historie met His Terugmelding
     */
    FormeleHistorieSet<HisTerugmeldingModel> getTerugmeldingHistorie();

    /**
     * Retourneert de historie van His Terugmelding Contactpersoon.
     *
     * @return Historie met His Terugmelding Contactpersoon
     */
    FormeleHistorieSet<HisTerugmeldingContactpersoonModel> getTerugmeldingContactpersoonHistorie();

    /**
     * Retourneert Terugmeldende partij van Terugmelding.
     *
     * @return Terugmeldende partij van Terugmelding
     */
    PartijAttribuut getTerugmeldendePartij();

    /**
     * Retourneert Persoon van Terugmelding.
     *
     * @return Persoon van Terugmelding
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Bijhoudingsgemeente van Terugmelding.
     *
     * @return Bijhoudingsgemeente van Terugmelding
     */
    PartijAttribuut getBijhoudingsgemeente();

    /**
     * Retourneert Tijdstip registratie van Terugmelding.
     *
     * @return Tijdstip registratie van Terugmelding
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert lijst van Gegeven in terugmelding.
     *
     * @return lijst van Gegeven in terugmelding
     */
    Set<? extends GegevenInTerugmeldingHisVolledig> getGegevens();

}
