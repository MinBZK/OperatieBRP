/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de voorvoegseltabel (LO3) enerzijds, en de waarden voor het voorvoegsel en scheidingsteken (BRP) anderzijds.
 * <p/>
 * Bij de conversie wordt de waarde van de voorvoegseltabel (LO3) vertaalt naar een waarde van voorvoegsel en scheidingsteken (BRP).
 */
@Entity
@Table(schema = "Conv", name = "ConvVoorvoegsel")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieVoorvoegsel extends AbstractConversieVoorvoegsel {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieVoorvoegsel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek0231Voorvoegsel rubriek0231Voorvoegsel van ConversieVoorvoegsel.
     * @param voorvoegsel            voorvoegsel van ConversieVoorvoegsel.
     * @param scheidingsteken        scheidingsteken van ConversieVoorvoegsel.
     */
    protected ConversieVoorvoegsel(final LO3VoorvoegselAttribuut rubriek0231Voorvoegsel,
        final NaamEnumeratiewaardeAttribuut voorvoegsel, final ScheidingstekenAttribuut scheidingsteken)
    {
        super(rubriek0231Voorvoegsel, voorvoegsel, scheidingsteken);
    }

}
