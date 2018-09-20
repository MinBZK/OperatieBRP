/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3NederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Conversietabel ten behoeve van de conversie tussen Nederlands reisdocument (LO3) enerzijds, en soort Nederlands reisdocument (BRP) anderzijds.
 */
@Entity
@Table(schema = "Conv", name = "ConvSrtNLReisdoc")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieSoortNLReisdocument extends AbstractConversieSoortNLReisdocument {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ConversieSoortNLReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek3511NederlandsReisdocument
     *                                    rubriek3511NederlandsReisdocument van ConversieSoortNLReisdocument.
     * @param soortNederlandsReisdocument soortNederlandsReisdocument van ConversieSoortNLReisdocument.
     */
    protected ConversieSoortNLReisdocument(final LO3NederlandsReisdocumentAttribuut rubriek3511NederlandsReisdocument,
        final SoortNederlandsReisdocument soortNederlandsReisdocument)
    {
        super(rubriek3511NederlandsReisdocument, soortNederlandsReisdocument);
    }

}
