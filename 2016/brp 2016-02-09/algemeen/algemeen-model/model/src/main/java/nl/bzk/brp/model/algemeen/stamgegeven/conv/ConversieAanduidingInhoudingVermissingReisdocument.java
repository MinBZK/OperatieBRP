/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocument;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de reden inhouding/vermissing reisdocument (LO3) enerzijds, en de reden vervallen reisdocument (BRP) anderzijds.
 */
@Entity
@Table(schema = "Conv", name = "ConvAandInhingVermissingReis")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieAanduidingInhoudingVermissingReisdocument extends
    AbstractConversieAanduidingInhoudingVermissingReisdocument
{

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieAanduidingInhoudingVermissingReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument
     *         rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument van ConversieAanduidingInhoudingVermissingReisdocument.
     * @param aanduidingInhoudingVermissingReisdocument
     *         aanduidingInhoudingVermissingReisdocument van ConversieAanduidingInhoudingVermissingReisdocument.
     */
    protected ConversieAanduidingInhoudingVermissingReisdocument(
        final LO3AanduidingInhoudingDanWelVermissingNLReisdocumentAttribuut rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument,
        final AanduidingInhoudingVermissingReisdocument aanduidingInhoudingVermissingReisdocument)
    {
        super(rubriek3570AanduidingInhoudingDanWelVermissingNederlandsReisdocument,
            aanduidingInhoudingVermissingReisdocument);
    }

}
