/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.migratie;

import java.util.Comparator;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroepInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.simpleframework.xml.Element;

/**
 * Deze abstract class is de super class voor alle Migratie Groep typen. Een Migratie groep bestaat mogelijk uit:
 * <ul>
 * <li>BrpGroepInhoud</li>
 * <li>Lo3Historie</li>
 * </ul>
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt. Dit betekend dat als de instantie
 * voor inhoud immutable is dat de gehele categorie immutable is (aangezien ook lo3historie en lo3documentatie immutable
 * zijn).
 * 
 * 
 * 
 * @param <T>
 *            de BRP groep inhoud
 */
public final class MigratieGroep<T extends BrpGroepInhoud> {

    private final T inhoud;
    private final Lo3Historie historie;
    private final Lo3Documentatie documentatie;
    private final Lo3Herkomst lo3Herkomst;

    /**
     * Maakt een MigratieGroep object.
     * 
     * @param inhoud
     *            de BRP groep inhoud, mag niet null zijn
     * @param historie
     *            de LO3 historie, mag niet null zijn
     * @param documentatie
     *            de LO3 documentatie, mag null zijn
     * @param lo3Herkomst
     *            de herkomst, mag null zijn
     * @throws NullPointerException
     *             als inhoud of historie null is
     */
    public MigratieGroep(
            @Element(name = "inhoud") final T inhoud,
            @Element(name = "historie") final Lo3Historie historie,
            @Element(name = "documentatie", required = false) final Lo3Documentatie documentatie,
            @Element(name = "lo3Herkomst", required = false) final Lo3Herkomst lo3Herkomst) {
        if (inhoud == null) {
            throw new NullPointerException("inhoud mag niet null zijn");
        }
        if (historie == null) {
            throw new NullPointerException("historie mag niet null zijn");
        }
        this.inhoud = inhoud;
        this.historie = historie;
        this.documentatie = documentatie;
        this.lo3Herkomst = lo3Herkomst;
    }

    /**
     * @return de BRP inhoud van deze migratie groep
     */
    @Element(name = "inhoud")
    public T getInhoud() {
        return inhoud;
    }

    /**
     * @return de LO3 historie van deze migratie groep
     */
    @Element(name = "historie")
    public Lo3Historie getHistorie() {
        return historie;
    }

    /**
     * @return de LO3 documentatie (akte/document) van deze migratie groep
     */
    @Element(name = "documentatie", required = false)
    public Lo3Documentatie getDocumentatie() {
        return documentatie;
    }

    /**
     * @return de herkomst van deze migratie groep
     */
    @Element(name = "lo3Herkomst", required = false)
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * @return true als deze MigratieGroep semantisch leeg is.
     */
    public boolean isInhoudelijkLeeg() {
        return getInhoud().isLeeg();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MigratieGroep)) {
            return false;
        }
        final MigratieGroep<?> castOther = (MigratieGroep<?>) other;
        return new EqualsBuilder().append(inhoud, castOther.inhoud).append(historie, castOther.historie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inhoud).append(historie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("inhoud", inhoud).append("historie", historie).append("documentatie", documentatie)
                .append("lo3Herkomst", lo3Herkomst).toString();
    }

    /**
     * Sorteer alle rijen (juist en onjuist) op basis van datum van opneming en daarbinnen op datum geldigheid. Deze
     * worden doorlopen van oud naar nieuw. NB: Als er meerder rijen zijn die dezelfde datum van opnemening en datum
     * geldigheid hebben, dan moeten eerst de onjuist doorlopen worden, en daarna de niet-onjuiste.
     */
    public static final class MigratieGroepComparator implements Comparator<MigratieGroep<?>> {

        @Override
        public int compare(final MigratieGroep<?> arg0, final MigratieGroep<?> arg1) {
            int result = arg0.getHistorie().getDatumVanOpneming().compareTo(arg1.getHistorie().getDatumVanOpneming());

            if (result == 0) {
                result =
                        arg0.getHistorie().getIngangsdatumGeldigheid()
                                .compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());
            }

            if (result == 0) {
                final Lo3IndicatieOnjuist onjuist0 = arg0.getHistorie().getIndicatieOnjuist();
                final Lo3IndicatieOnjuist onjuist1 = arg1.getHistorie().getIndicatieOnjuist();

                if (onjuist0 != null && onjuist1 == null) {
                    result = -1;
                } else if (onjuist0 == null && onjuist1 != null) {
                    result = 1;
                }
            }

            return result;
        }
    }
}
