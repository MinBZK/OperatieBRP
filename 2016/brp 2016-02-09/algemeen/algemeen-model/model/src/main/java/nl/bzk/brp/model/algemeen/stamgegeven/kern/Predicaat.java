/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * De mogelijke predicaat van een Persoon.
 */
@Entity
@Table(schema = "Kern", name = "Predicaat")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Predicaat extends AbstractPredicaat {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Predicaat() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code           code van Predicaat.
     * @param naamMannelijk  naamMannelijk van Predicaat.
     * @param naamVrouwelijk naamVrouwelijk van Predicaat.
     */
    public Predicaat(final PredicaatCodeAttribuut code, final NaamEnumeratiewaardeAttribuut naamMannelijk,
        final NaamEnumeratiewaardeAttribuut naamVrouwelijk)
    {
        super(code, naamMannelijk, naamVrouwelijk);
    }

}
