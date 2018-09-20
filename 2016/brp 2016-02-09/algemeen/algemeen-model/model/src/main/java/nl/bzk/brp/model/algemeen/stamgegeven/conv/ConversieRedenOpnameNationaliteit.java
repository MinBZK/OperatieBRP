/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de conversie tussen Reden opnamen nationaliteit (LO3) en de Reden verkrijging van
 * Persoon \ Nationaliteit (BRP).
 *
 * Zie OT:"Conversie reden be�indigen nationaliteit" voor nadere toelichting.
 *
 *
 *
 */
@Table(schema = "Conv", name = "ConvRdnOpnameNation")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieRedenOpnameNationaliteit extends AbstractConversieRedenOpnameNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected ConversieRedenOpnameNationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6310RedenOpnameNationaliteit rubriek6310RedenOpnameNationaliteit van
     *            ConversieRedenOpnameNationaliteit.
     * @param redenVerkrijging redenVerkrijging van ConversieRedenOpnameNationaliteit.
     */
    protected ConversieRedenOpnameNationaliteit(
        final LO3RedenOpnameNationaliteitAttribuut rubriek6310RedenOpnameNationaliteit,
        final RedenVerkrijgingNLNationaliteit redenVerkrijging)
    {
        super(rubriek6310RedenOpnameNationaliteit, redenVerkrijging);
    }

}
