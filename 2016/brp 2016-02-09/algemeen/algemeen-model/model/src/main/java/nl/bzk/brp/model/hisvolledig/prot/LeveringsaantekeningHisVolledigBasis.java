/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.prot;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Leveringsaantekening.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LeveringsaantekeningHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Toegang leveringsautorisatie van Leveringsaantekening.
     *
     * @return Toegang leveringsautorisatie van Leveringsaantekening
     */
    Integer getToegangLeveringsautorisatieId();

    /**
     * Retourneert Dienst van Leveringsaantekening.
     *
     * @return Dienst van Leveringsaantekening
     */
    Integer getDienstId();

    /**
     * Retourneert Datum/tijd klaarzetten levering van Leveringsaantekening.
     *
     * @return Datum/tijd klaarzetten levering van Leveringsaantekening
     */
    DatumTijdAttribuut getDatumTijdKlaarzettenLevering();

    /**
     * Retourneert Datum materieel selectie van Leveringsaantekening.
     *
     * @return Datum materieel selectie van Leveringsaantekening
     */
    DatumAttribuut getDatumMaterieelSelectie();

    /**
     * Retourneert Datum aanvang materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum aanvang materiële periode resultaat van Leveringsaantekening
     */
    DatumAttribuut getDatumAanvangMaterielePeriodeResultaat();

    /**
     * Retourneert Datum einde materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum einde materiële periode resultaat van Leveringsaantekening
     */
    DatumAttribuut getDatumEindeMaterielePeriodeResultaat();

    /**
     * Retourneert Datum/tijd aanvang formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd aanvang formele periode resultaat van Leveringsaantekening
     */
    DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat();

    /**
     * Retourneert Datum/tijd einde formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd einde formele periode resultaat van Leveringsaantekening
     */
    DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat();

    /**
     * Retourneert Administratieve handeling van Leveringsaantekening.
     *
     * @return Administratieve handeling van Leveringsaantekening
     */
    Long getAdministratieveHandelingId();

    /**
     * Retourneert Soort synchronisatie van Leveringsaantekening.
     *
     * @return Soort synchronisatie van Leveringsaantekening
     */
    SoortSynchronisatieAttribuut getSoortSynchronisatie();

}
