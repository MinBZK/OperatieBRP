/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Interface voor LO3 Bericht.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LO3BerichtHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Berichtsoort onderdeel LO3 stelsel? van LO3 Bericht.
     *
     * @return Berichtsoort onderdeel LO3 stelsel? van LO3 Bericht
     */
    JaNeeAttribuut getIndicatieBerichtsoortOnderdeelLO3Stelsel();

    /**
     * Retourneert Referentie van LO3 Bericht.
     *
     * @return Referentie van LO3 Bericht
     */
    LO3ReferentieAttribuut getReferentie();

    /**
     * Retourneert Bron van LO3 Bericht.
     *
     * @return Bron van LO3 Bericht
     */
    LO3BerichtenBronAttribuut getBron();

    /**
     * Retourneert Administratienummer van LO3 Bericht.
     *
     * @return Administratienummer van LO3 Bericht
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Persoon van LO3 Bericht.
     *
     * @return Persoon van LO3 Bericht
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Berichtdata van LO3 Bericht.
     *
     * @return Berichtdata van LO3 Bericht
     */
    ByteaopslagAttribuut getBerichtdata();

    /**
     * Retourneert Checksum van LO3 Bericht.
     *
     * @return Checksum van LO3 Bericht
     */
    ChecksumAttribuut getChecksum();

}
