/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * De (mogelijke) reden van het definitief aan het verkeer onttrokken zijn van het Nederlands reisdocument.
 */
@Entity
@Table(schema = "Kern", name = "AandInhingVermissingReisdoc")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class AanduidingInhoudingVermissingReisdocument extends AbstractAanduidingInhoudingVermissingReisdocument {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected AanduidingInhoudingVermissingReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AanduidingInhoudingVermissingReisdocument.
     * @param naam naam van AanduidingInhoudingVermissingReisdocument.
     */
    public AanduidingInhoudingVermissingReisdocument(final AanduidingInhoudingVermissingReisdocumentCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam)
    {
        super(code, naam);
    }

}
