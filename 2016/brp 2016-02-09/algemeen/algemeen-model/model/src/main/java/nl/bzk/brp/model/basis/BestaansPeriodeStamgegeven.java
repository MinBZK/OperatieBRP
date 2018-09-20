/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Een object dat de interface GeldigheidsPeriode ondersteund, moet een datumaang, datumEinde kunnen terug geven.
 */
public interface BestaansPeriodeStamgegeven {

    /**
     * Geeft de aanvangs datum.
     *
     * @return de datum
     */
    DatumBasisAttribuut getDatumAanvangGeldigheid();

    /**
     * Geeft de einde datum.
     *
     * @return de datum
     */
    DatumBasisAttribuut getDatumEindeGeldigheid();
}
