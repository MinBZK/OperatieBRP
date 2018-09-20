/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ist;

import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ist.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.BrpObject;


/**
 * Indicatie dat de persoon (tijdelijk) geblokkeerd is voor mutaties omdat deze aan het verhuizen is van een GBA naar BRP gemeente (of vice versa).
 * <p/>
 * De blokkering tabel heeft bestaansrecht totdat alle gemeenten over zijn naar de BRP, daarna kan deze vervallen.
 * <p/>
 * Deze tabel was reeds gemaakt door migratie en is achteraf opgenomen in BMR. Om het aantal wijzigingen te beperken in de implementatie (mede gezien de
 * tijdelijkheid van de tabel), is de gemeente geen Partij maar een vrije code.
 */
public interface BlokkeringBasis extends BrpObject {

    /**
     * Retourneert Administratienummer van Blokkering.
     *
     * @return Administratienummer.
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Reden blokkering van Blokkering.
     *
     * @return Reden blokkering.
     */
    RedenBlokkeringAttribuut getRedenBlokkering();

    /**
     * Retourneert Process instantie ID van Blokkering.
     *
     * @return Process instantie ID.
     */
    ProcessInstantieIDAttribuut getProcessInstantieID();

    /**
     * Retourneert LO3 gemeente vestiging van Blokkering.
     *
     * @return LO3 gemeente vestiging.
     */
    LO3GemeenteCodeAttribuut getLO3GemeenteVestiging();

    /**
     * Retourneert LO3 gemeente registratie van Blokkering.
     *
     * @return LO3 gemeente registratie.
     */
    LO3GemeenteCodeAttribuut getLO3GemeenteRegistratie();

    /**
     * Retourneert Tijdstip registratie van Blokkering.
     *
     * @return Tijdstip registratie.
     */
    DatumTijdAttribuut getTijdstipRegistratie();

}
