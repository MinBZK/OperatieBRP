/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingGedeblokkeerdeMelding;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Het door middel van een bericht deblokkeren of gedeblokkkerd hebben van een (deblokkeerbare) fout.
 * <p/>
 * Een bijhoudingsbericht kan aanleiding zijn tot ��n of meer deblokkeerbare fouten. Een deblokkeerbare fout kan worden gedeblokkeerd door in een
 * bijhoudingsbericht expliciet de (deblokkeerbare) fout te de-blokkeren. Een gedeblokkeerde fout wordt twee keer gekoppeld aan een bericht: enerzijds door
 * een koppeling tussen het inkomende bijhoudingsbericht en de deblokkage; anderzijds door het uitgaand bericht waarin wordt medegedeeld welke
 * deblokkeringen zijn verwerkt.
 */
public final class AdministratieveHandelingGedeblokkeerdeMeldingBericht extends
    AbstractAdministratieveHandelingGedeblokkeerdeMeldingBericht implements BrpObject,
    AdministratieveHandelingGedeblokkeerdeMelding, Comparable<AdministratieveHandelingGedeblokkeerdeMeldingBericht>
{

    private static final int HASHCODE_GETAL1 = 1234;
    private static final int HASHCODE_GETAL2 = 5674;

    @Override
    public int compareTo(final AdministratieveHandelingGedeblokkeerdeMeldingBericht that) {
        final int isGelijk;
        if (null == that || null == that.getGedeblokkeerdeMelding()
            || null == that.getGedeblokkeerdeMelding().getRegel()
            || null == that.getGedeblokkeerdeMelding().getRegel().getWaarde().getCode())
        {
            isGelijk = -1;
        } else {
            if (null == getGedeblokkeerdeMelding() || null == getGedeblokkeerdeMelding().getRegel()
                || null == getGedeblokkeerdeMelding().getRegel().getWaarde())
            {
                isGelijk = -1;
            } else {
                isGelijk = getGedeblokkeerdeMelding().getRegel().compareTo(that.getGedeblokkeerdeMelding().getRegel());
            }
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final AdministratieveHandelingGedeblokkeerdeMeldingBericht gedeblokkeerdeMeldingBericht =
                (AdministratieveHandelingGedeblokkeerdeMeldingBericht) obj;

            if (null == this.getGedeblokkeerdeMelding()
                || null == gedeblokkeerdeMeldingBericht.getGedeblokkeerdeMelding())
            {
                isGelijk = false;
            } else {
                isGelijk =
                    new EqualsBuilder().append(this.getGedeblokkeerdeMelding().getRegel(),
                        gedeblokkeerdeMeldingBericht.getGedeblokkeerdeMelding().getRegel()).isEquals();
            }
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getGedeblokkeerdeMelding().getRegel())
            .hashCode();
    }

}
