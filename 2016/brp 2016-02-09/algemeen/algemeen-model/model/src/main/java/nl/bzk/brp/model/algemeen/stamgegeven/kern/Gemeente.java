/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * De gemeente als geografisch object.
 * <p/>
 * De gemeente komt in twee hoedanigheden voor in de BRP: enerzijds als partij die berichten uitwisselt met de BRP, en anderzijds als geografisch object
 * dat de opdeling van (het Europese deel van) Nederland bepaalt. Hier wordt de tweede hoedanigheid bedoeld.
 */
@Entity
@Table(schema = "Kern", name = "Gem")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Gemeente extends AbstractGemeente {

    private static final int HASHCODE_GETAL1 = 27;
    private static final int HASHCODE_GETAL2 = 23;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Gemeente() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam                   naam van Gemeente.
     * @param code                   code van Gemeente.
     * @param partij                 partij van Gemeente.
     * @param voortzettendeGemeente  voortzettendeGemeente van Gemeente.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Gemeente.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van Gemeente.
     */
    public Gemeente(final NaamEnumeratiewaardeAttribuut naam, final GemeenteCodeAttribuut code, final Partij partij,
        final Gemeente voortzettendeGemeente, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(naam, code, partij, voortzettendeGemeente, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof Gemeente)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                final Gemeente that = (Gemeente) obj;
                resultaat = new EqualsBuilder().append(this.getCode(), that.getCode()).isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getCode()).hashCode();
    }
}
