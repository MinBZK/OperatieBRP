/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCodeAttribuut;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * De mogelijke soorten voor een Nederlandse reisdocument.
 */
@Entity
@Table(schema = "Kern", name = "SrtNLReisdoc")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class SoortNederlandsReisdocument extends AbstractSoortNederlandsReisdocument implements
    Comparable<SoortNederlandsReisdocument>
{

    private static final int INITIEEL_ONEVEN_NUMMER          = 9;
    private static final int VERMENIGVULDIGING_ONEVEN_NUMMER = 19;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected SoortNederlandsReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van SoortNederlandsReisdocument.
     * @param omschrijving           omschrijving van SoortNederlandsReisdocument.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van SoortNederlandsReisdocument.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van SoortNederlandsReisdocument.
     */
    public SoortNederlandsReisdocument(final SoortNederlandsReisdocumentCodeAttribuut code,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public int compareTo(final SoortNederlandsReisdocument o) {
        return new CompareToBuilder().append(getCode(), o.getCode()).toComparison();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(INITIEEL_ONEVEN_NUMMER, VERMENIGVULDIGING_ONEVEN_NUMMER).append(this.getCode())
            .toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean equals;
        if (obj == null) {
            equals = false;
        } else if (obj == this) {
            equals = true;
        } else if (obj instanceof SoortNederlandsReisdocument) {
            final SoortNederlandsReisdocument that = (SoortNederlandsReisdocument) obj;
            equals = new EqualsBuilder().append(this.getCode(), that.getCode()).isEquals();
        } else {
            equals = false;
        }
        return equals;
    }
}
