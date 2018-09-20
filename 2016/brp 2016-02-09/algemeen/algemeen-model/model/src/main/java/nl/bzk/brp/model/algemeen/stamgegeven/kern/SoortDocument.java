/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * Categorisatie van Documenten.
 */
@Entity
@Table(schema = "Kern", name = "SrtDoc")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SoortDocument extends AbstractSoortDocument {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected SoortDocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam         naam van SoortDocument.
     * @param omschrijving omschrijving van SoortDocument.
     * @param rangorde     rangorde van SoortDocument.
     */
    public SoortDocument(final NaamEnumeratiewaardeAttribuut naam,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving, final VolgnummerAttribuut rangorde)
    {
        super(naam, omschrijving, rangorde);
    }

}
