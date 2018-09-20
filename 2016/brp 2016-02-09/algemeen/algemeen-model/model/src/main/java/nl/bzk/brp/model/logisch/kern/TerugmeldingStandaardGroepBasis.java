/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.KenmerkTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TekstTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmeldingAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface TerugmeldingStandaardGroepBasis extends Groep {

    /**
     * Retourneert Onderzoek van Standaard.
     *
     * @return Onderzoek.
     */
    Onderzoek getOnderzoek();

    /**
     * Retourneert Status van Standaard.
     *
     * @return Status.
     */
    StatusTerugmeldingAttribuut getStatus();

    /**
     * Retourneert Toelichting van Standaard.
     *
     * @return Toelichting.
     */
    TekstTerugmeldingAttribuut getToelichting();

    /**
     * Retourneert Kenmerk meldende partij van Standaard.
     *
     * @return Kenmerk meldende partij.
     */
    KenmerkTerugmeldingAttribuut getKenmerkMeldendePartij();

}
