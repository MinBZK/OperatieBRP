/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Actie.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface ActieHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Soort van Actie.
     *
     * @return Soort van Actie
     */
    SoortActieAttribuut getSoort();

    /**
     * Retourneert Administratieve handeling van Actie.
     *
     * @return Administratieve handeling van Actie
     */
    AdministratieveHandelingHisVolledig getAdministratieveHandeling();

    /**
     * Retourneert Partij van Actie.
     *
     * @return Partij van Actie
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Datum aanvang geldigheid van Actie.
     *
     * @return Datum aanvang geldigheid van Actie
     */
    DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid();

    /**
     * Retourneert Datum einde geldigheid van Actie.
     *
     * @return Datum einde geldigheid van Actie
     */
    DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid();

    /**
     * Retourneert Tijdstip registratie van Actie.
     *
     * @return Tijdstip registratie van Actie
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert Datum ontlening van Actie.
     *
     * @return Datum ontlening van Actie
     */
    DatumEvtDeelsOnbekendAttribuut getDatumOntlening();

    /**
     * Retourneert lijst van Actie \ Bron.
     *
     * @return lijst van Actie \ Bron
     */
    Set<? extends ActieBronHisVolledig> getBronnen();

}
