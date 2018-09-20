/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.ber.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Applicatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Organisatienaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.Groep;


/**
 *
 *
 */
public interface BerichtStuurgegevensGroepBasis extends Groep {

    /**
     * Retourneert Organisatie van Stuurgegevens.
     *
     * @return Organisatie.
     */
    Organisatienaam getOrganisatie();

    /**
     * Retourneert Applicatie van Stuurgegevens.
     *
     * @return Applicatie.
     */
    Applicatienaam getApplicatie();

    /**
     * Retourneert Referentienummer van Stuurgegevens.
     *
     * @return Referentienummer.
     */
    Sleutelwaardetekst getReferentienummer();

    /**
     * Retourneert Cross referentienummer van Stuurgegevens.
     *
     * @return Cross referentienummer.
     */
    Sleutelwaardetekst getCrossReferentienummer();

}
