/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Administratieve handeling.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface AdministratieveHandelingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Soort van Administratieve handeling.
     *
     * @return Soort van Administratieve handeling
     */
    SoortAdministratieveHandelingAttribuut getSoort();

    /**
     * Retourneert Partij van Administratieve handeling.
     *
     * @return Partij van Administratieve handeling
     */
    PartijAttribuut getPartij();

    /**
     * Retourneert Toelichting ontlening van Administratieve handeling.
     *
     * @return Toelichting ontlening van Administratieve handeling
     */
    OntleningstoelichtingAttribuut getToelichtingOntlening();

    /**
     * Retourneert Tijdstip registratie van Administratieve handeling.
     *
     * @return Tijdstip registratie van Administratieve handeling
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert lijst van Administratieve handeling \ Gedeblokkeerde melding.
     *
     * @return lijst van Administratieve handeling \ Gedeblokkeerde melding
     */
    Set<? extends AdministratieveHandelingGedeblokkeerdeMeldingHisVolledig> getGedeblokkeerdeMeldingen();

    /**
     * Retourneert lijst van Actie.
     *
     * @return lijst van Actie
     */
    Set<? extends ActieHisVolledig> getActies();

}
