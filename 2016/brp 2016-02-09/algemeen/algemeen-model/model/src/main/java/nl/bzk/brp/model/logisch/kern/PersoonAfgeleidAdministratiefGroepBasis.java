/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonAfgeleidAdministratiefGroepBasis extends Groep {

    /**
     * Retourneert Administratieve handeling van Afgeleid administratief.
     *
     * @return Administratieve handeling.
     */
    AdministratieveHandeling getAdministratieveHandeling();

    /**
     * Retourneert Tijdstip laatste wijziging van Afgeleid administratief.
     *
     * @return Tijdstip laatste wijziging.
     */
    DatumTijdAttribuut getTijdstipLaatsteWijziging();

    /**
     * Retourneert Sorteervolgorde van Afgeleid administratief.
     *
     * @return Sorteervolgorde.
     */
    SorteervolgordeAttribuut getSorteervolgorde();

    /**
     * Retourneert Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig? van Afgeleid administratief.
     *
     * @return Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?.
     */
    JaNeeAttribuut getIndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig();

    /**
     * Retourneert Tijdstip laatste wijziging GBA-systematiek van Afgeleid administratief.
     *
     * @return Tijdstip laatste wijziging GBA-systematiek.
     */
    DatumTijdAttribuut getTijdstipLaatsteWijzigingGBASystematiek();

}
