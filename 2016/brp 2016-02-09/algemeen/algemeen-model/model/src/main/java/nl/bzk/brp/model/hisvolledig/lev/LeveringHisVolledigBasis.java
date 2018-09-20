/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.lev;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Levering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LeveringHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Toegang abonnement van Levering.
     *
     * @return Toegang abonnement van Levering
     */
    Integer getToegangAbonnementId();

    /**
     * Retourneert Dienst van Levering.
     *
     * @return Dienst van Levering
     */
    Integer getDienstId();

    /**
     * Retourneert Datum/tijd klaarzetten levering van Levering.
     *
     * @return Datum/tijd klaarzetten levering van Levering
     */
    DatumTijdAttribuut getDatumTijdKlaarzettenLevering();

    /**
     * Retourneert Datum materieel selectie van Levering.
     *
     * @return Datum materieel selectie van Levering
     */
    DatumAttribuut getDatumMaterieelSelectie();

    /**
     * Retourneert Datum aanvang materi�le periode resultaat van Levering.
     *
     * @return Datum aanvang materi�le periode resultaat van Levering
     */
    DatumAttribuut getDatumAanvangMaterielePeriodeResultaat();

    /**
     * Retourneert Datum einde materi�le periode resultaat van Levering.
     *
     * @return Datum einde materi�le periode resultaat van Levering
     */
    DatumAttribuut getDatumEindeMaterielePeriodeResultaat();

    /**
     * Retourneert Datum/tijd aanvang formele periode resultaat van Levering.
     *
     * @return Datum/tijd aanvang formele periode resultaat van Levering
     */
    DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat();

    /**
     * Retourneert Datum/tijd einde formele periode resultaat van Levering.
     *
     * @return Datum/tijd einde formele periode resultaat van Levering
     */
    DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat();

    /**
     * Retourneert Administratieve handeling van Levering.
     *
     * @return Administratieve handeling van Levering
     */
    Long getAdministratieveHandelingId();

    /**
     * Retourneert Soort synchronisatie van Levering.
     *
     * @return Soort synchronisatie van Levering
     */
    SoortSynchronisatieAttribuut getSoortSynchronisatie();

}
