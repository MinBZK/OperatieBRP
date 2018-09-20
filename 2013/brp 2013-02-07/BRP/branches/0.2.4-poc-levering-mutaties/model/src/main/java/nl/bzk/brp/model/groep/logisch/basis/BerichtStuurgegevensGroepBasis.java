/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.logisch.basis;

import nl.bzk.brp.model.attribuuttype.Applicatienaam;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.attribuuttype.Organisatienaam;
import nl.bzk.brp.model.attribuuttype.Sleutelwaardetekst;
import nl.bzk.brp.model.basis.Groep;

/**
 * Stuurgegevens groep van bericht.
 */
public interface BerichtStuurgegevensGroepBasis extends Groep {

    /**
     * Organisatie.
     * @return Organisatie.
     */
    Organisatienaam getOrganisatie();

    /**
     * Applicatie.
     * @return Applicatie.
     */
    Applicatienaam getApplicatie();

    /**
     * Referentienummer.
     * @return Referentienummer.
     */
    Sleutelwaardetekst getReferentienummer();

    /**
     * Cross referentienummer.
     * @return Cross referentienummer.
     */
    Sleutelwaardetekst getCrossReferentienummer();

    /**
     * Indicatie prevalidatie.
     * @return Indicatie prevalidatie.
     */
    Ja getIndPrevalidatie();
}
