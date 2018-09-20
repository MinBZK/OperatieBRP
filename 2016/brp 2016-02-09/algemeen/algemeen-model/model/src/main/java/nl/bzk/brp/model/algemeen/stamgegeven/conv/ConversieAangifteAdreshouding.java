/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de aangifte adreshouding (LO3) enerzijds, en de aangever adreshouding en reden wijziging (BRP) anderzijds.
 * <p/>
 * Bij de conversie wordt de waarde van de rubriek (LO3) vertaalt naar een waarde van de aangever adreshouding en/of een waarde van reden wijziging (BRP).
 */
@Entity
@Table(schema = "Conv", name = "ConvAangifteAdresh")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieAangifteAdreshouding extends AbstractConversieAangifteAdreshouding {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieAangifteAdreshouding() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek7210OmschrijvingVanDeAangifteAdreshouding
     *                            rubriek7210OmschrijvingVanDeAangifteAdreshouding van ConversieAangifteAdreshouding.
     * @param aangever            aangever van ConversieAangifteAdreshouding.
     * @param redenWijzigingAdres redenWijzigingAdres van ConversieAangifteAdreshouding.
     */
    protected ConversieAangifteAdreshouding(
        final LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut rubriek7210OmschrijvingVanDeAangifteAdreshouding,
        final Aangever aangever, final RedenWijzigingVerblijf redenWijzigingAdres)
    {
        super(rubriek7210OmschrijvingVanDeAangifteAdreshouding, aangever, redenWijzigingAdres);
    }

}
