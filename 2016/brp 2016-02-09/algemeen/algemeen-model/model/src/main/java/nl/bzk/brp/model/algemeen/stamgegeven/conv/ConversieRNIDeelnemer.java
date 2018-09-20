/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RNIDeelnemerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de RNI deelnemer (LO3) enerzijds, en de partij (BRP) anderzijds.
 */
@Entity
@Table(schema = "Conv", name = "ConvRNIDeelnemer")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieRNIDeelnemer extends AbstractConversieRNIDeelnemer {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieRNIDeelnemer() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek8811CodeRNIDeelnemer rubriek8811CodeRNIDeelnemer van ConversieRNIDeelnemer.
     * @param partij                      partij van ConversieRNIDeelnemer.
     */
    protected ConversieRNIDeelnemer(final LO3RNIDeelnemerAttribuut rubriek8811CodeRNIDeelnemer, final Partij partij) {
        super(rubriek8811CodeRNIDeelnemer, partij);
    }

}
