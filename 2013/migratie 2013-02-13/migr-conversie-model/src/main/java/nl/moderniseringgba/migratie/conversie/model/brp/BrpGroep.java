/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze abstracte class is de super class voor alle BRP groepen. Een BRP groep bestaat mogelijk uit:
 * <ul>
 * <li>BrpGroepInhoud</li>
 * <li>BrpHistorie</li>
 * <li>actieInhoud</li>
 * <li>actieVerval</li>
 * <li>actieGeldigheid</li>
 * </ul>
 * 
 * <p>
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt. Dit betekend dat als de instantie
 * voor inhoud immutable is dat de gehele groep immutable is (aangezien ook BrpHistorie en BrpActie immutable zijn).
 * </p>
 * 
 * @param <T>
 *            de BRP groep inhoud type
 * 
 */
public final class BrpGroep<T extends BrpGroepInhoud> {

    private final T inhoud;
    private final BrpHistorie historie;
    private final BrpActie actieInhoud;
    private final BrpActie actieVerval;
    private final BrpActie actieGeldigheid;

    /**
     * Maakt een BrpGroep object.
     * 
     * @param inhoud
     *            de BRP inhoud voor deze BRP groep, mag niet null zijn
     * @param historie
     *            de BRP historie voor deze BRP groep, mag niet null zijn
     * @param actieInhoud
     *            de BRP actie voor het opnemen van deze inhoud
     * @param actieVerval
     *            de BRP actie voor het vervallen van deze inhoud
     * @param actieGeldigheid
     *            de BRP actie voor het aanpassen van de geldigheid van deze inhoud
     * @throws NullPointerException
     *             als inhoud of historie null is
     */
    public BrpGroep(
            @Element(name = "inhoud", required = false) final T inhoud,
            @Element(name = "historie", required = false) final BrpHistorie historie,
            @Element(name = "actieInhoud", required = false) final BrpActie actieInhoud,
            @Element(name = "actieVerval", required = false) final BrpActie actieVerval,
            @Element(name = "actieGeldigheid", required = false) final BrpActie actieGeldigheid) {
        if (inhoud == null) {
            throw new NullPointerException("inhoud mag niet null zijn");
        }
        if (historie == null) {
            throw new NullPointerException("historie mag niet null zijn");
        }
        this.inhoud = inhoud;
        this.historie = historie;
        this.actieInhoud = actieInhoud;
        this.actieVerval = actieVerval;
        this.actieGeldigheid = actieGeldigheid;
    }

    /**
     * @return de BRP inhoud van deze BRP groep
     */
    @Element(name = "inhoud", required = false)
    public T getInhoud() {
        return inhoud;
    }

    /**
     * @return de BRP historie van deze BRP groep
     */
    @Element(name = "historie", required = false)
    public BrpHistorie getHistorie() {
        return historie;
    }

    @Element(name = "actieInhoud", required = false)
    public BrpActie getActieInhoud() {
        return actieInhoud;
    }

    @Element(name = "actieVerval", required = false)
    public BrpActie getActieVerval() {
        return actieVerval;
    }

    @Element(name = "actieGeldigheid", required = false)
    public BrpActie getActieGeldigheid() {
        return actieGeldigheid;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BrpGroep)) {
            return false;
        }
        final BrpGroep<?> castOther = (BrpGroep<?>) other;
        return new EqualsBuilder().append(inhoud, castOther.inhoud).append(historie, castOther.historie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inhoud).append(historie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("inhoud", inhoud).append("historie", historie).append("actieInhoud", actieInhoud)
                .append("actieVerval", actieVerval).append("actieGeldigheid", actieGeldigheid).toString();
    }

}
