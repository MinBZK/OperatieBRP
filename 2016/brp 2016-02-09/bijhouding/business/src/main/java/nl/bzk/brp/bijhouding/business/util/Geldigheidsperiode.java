/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Value object voor de combinatie datum aanvang/einde geldigheid.
 * Maakt het mogelijk om deze combinatie te sorteren.
 */
public class Geldigheidsperiode implements Comparable<Geldigheidsperiode> {

    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Maak een nieuwe dag deg aan met behulp van een actie.
     *
     * @param actie de actie
     */
    public Geldigheidsperiode(final Actie actie) {
        this.datumAanvangGeldigheid = actie.getDatumAanvangGeldigheid();
        this.datumEindeGeldigheid = actie.getDatumEindeGeldigheid();
    }

    /**
     * Constructor waarmee je de twee datums los kunt opgeven.
     *
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     */
    public Geldigheidsperiode(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
    }

    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public int compareTo(final Geldigheidsperiode that) {
        int compare;
        if (this.datumAanvangGeldigheid.voor(that.datumAanvangGeldigheid)) {
            compare = -1;
        } else if (this.datumAanvangGeldigheid.na(that.datumAanvangGeldigheid)) {
            compare = 1;
        } else {
            //Voorlopig doen we niks met de einde geldigheid. (Moet nog bepaald worden)
            //Let op: pas ook equals en hashcode aan, mocht dit weer wijzigen.
            compare = 0;
        }
        return compare;
    }

    @Override
    public boolean equals(final Object that) {
        boolean equals = false;
        if (that instanceof Geldigheidsperiode) {
            equals = new EqualsBuilder().
                    append(this.datumAanvangGeldigheid, ((Geldigheidsperiode) that).datumAanvangGeldigheid).isEquals();
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(datumAanvangGeldigheid).toHashCode();
    }

}
