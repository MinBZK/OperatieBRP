/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractSoortDocument;


/**
 * Categorisatie van Documenten.
 *
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "SrtDoc")
public class SoortDocument extends AbstractSoortDocument {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected SoortDocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van SoortDocument.
     * @param omschrijving omschrijving van SoortDocument.
     * @param rangorde rangorde van SoortDocument.
     */
    protected SoortDocument(final NaamEnumeratiewaarde naam, final OmschrijvingEnumeratiewaarde omschrijving,
            final Volgnummer rangorde)
    {
        super(naam, omschrijving, rangorde);
    }

}
