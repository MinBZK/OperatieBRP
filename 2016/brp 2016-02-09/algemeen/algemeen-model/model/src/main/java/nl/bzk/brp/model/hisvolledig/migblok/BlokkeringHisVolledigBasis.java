/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.migblok;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Blokkering.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface BlokkeringHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert Administratienummer van Blokkering.
     *
     * @return Administratienummer van Blokkering
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Reden blokkering van Blokkering.
     *
     * @return Reden blokkering van Blokkering
     */
    RedenBlokkeringAttribuut getRedenBlokkering();

    /**
     * Retourneert Process instantie ID van Blokkering.
     *
     * @return Process instantie ID van Blokkering
     */
    ProcessInstantieIDAttribuut getProcessInstantieID();

    /**
     * Retourneert LO3 gemeente vestiging van Blokkering.
     *
     * @return LO3 gemeente vestiging van Blokkering
     */
    LO3GemeenteCodeAttribuut getLO3GemeenteVestiging();

    /**
     * Retourneert LO3 gemeente registratie van Blokkering.
     *
     * @return LO3 gemeente registratie van Blokkering
     */
    LO3GemeenteCodeAttribuut getLO3GemeenteRegistratie();

    /**
     * Retourneert Tijdstip registratie van Blokkering.
     *
     * @return Tijdstip registratie van Blokkering
     */
    DatumTijdAttribuut getTijdstipRegistratie();

}
