/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatie;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de reden ontbinding/nietigverklaring huwelijk/geregistreerd partnerschap (LO3) enerzijds, en de reden einde relatie (BRP)
 * anderzijds.
 */
@Entity
@Table(schema = "Conv", name = "ConvRdnOntbindingHuwelijkGer")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap extends
    AbstractConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap
{

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap
     *                          rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap van ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.
     * @param redenEindeRelatie redenEindeRelatie van ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.
     */
    protected ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap(
        final LO3RedenOntbindingNietigverklaringHuwelijkGeregistreerdPartnerschapAttribuut rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap,
        final RedenEindeRelatie redenEindeRelatie)
    {
        super(rubriek0741RedenOntbindingHuwelijkGeregistreerdPartnerschap, redenEindeRelatie);
    }

}
