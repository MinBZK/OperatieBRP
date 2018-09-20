/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.WoonplaatscodeAttribuut;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * De woonplaatsen, zoals onderhouden vanuit de BAG.
 * <p/>
 * De inhoud van de woonplaatsentabel wordt overgenomen vanuit de BAG. Qua vorm wijkt deze wel af, zo wordt er apart bijgehouden welke gemeenten er zijn,
 * terwijl de BAG deze in ��n en dezelfde tabel heeft gestopt.
 */
@Entity
@Table(schema = "Kern", name = "Plaats")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Plaats extends AbstractPlaats {

    private static final int HASHCODE_GETAL1 = 27;
    private static final int HASHCODE_GETAL2 = 23;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Plaats() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van Plaats.
     * @param naam                   naam van Plaats.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Plaats.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van Plaats.
     */
    public Plaats(final WoonplaatscodeAttribuut code, final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof Plaats)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                final Plaats that = (Plaats) obj;
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
