/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import nl.bzk.algemeenbrp.util.xml.annotation.Element;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Deze abstract class is de super class voor alle Migratie Groep typen. Een Migratie groep bestaat mogelijk uit:
 * <ul>
 * <li>BrpGroepInhoud</li>
 * <li>Lo3Historie</li>
 * </ul>
 * Deze klasse staat geen wijzigingen toe op de referenties die deze klasse vasthoudt. Dit betekend dat als de instantie
 * voor inhoud immutable is dat de gehele categorie immutable is (aangezien ook lo3historie en lo3documentatie immutable
 * zijn).
 * @param <T> de BRP groep inhoud
 */
public final class TussenGroep<T extends BrpGroepInhoud> {

    @Element(name = "inhoud", required = false)
    private final T inhoud;
    @Element(name = "historie", required = false)
    private final Lo3Historie historie;
    @Element(name = "documentatie", required = false)
    private final Lo3Documentatie documentatie;
    @Element(name = "lo3Herkomst", required = false)
    private final Lo3Herkomst lo3Herkomst;
    private final boolean afsluitendeGroep;
    /**
     * oorsprongVoorkomenLeeg is true als het voorkomen waaruit deze groep is ontstaan, leeg is.
     */
    private final boolean oorsprongVoorkomenLeeg;

    /**
     * Maakt een TussenGroep object.
     * @param inhoud de BRP groep inhoud, mag niet null zijn
     * @param historie de LO3 historie, mag niet null zijn
     * @param documentatie de LO3 documentatie, mag null zijn
     * @param lo3Herkomst de herkomst, mag null zijn
     * @throws NullPointerException als inhoud of historie null is
     */
    public TussenGroep(
            @Element(name = "inhoud", required = false) final T inhoud,
            @Element(name = "historie", required = false) final Lo3Historie historie,
            @Element(name = "documentatie", required = false) final Lo3Documentatie documentatie,
            @Element(name = "lo3Herkomst", required = false) final Lo3Herkomst lo3Herkomst) {
        this(inhoud, historie, documentatie, lo3Herkomst, false, false);
    }

    /**
     * Maakt een TussenGroep object.
     * @param inhoud de BRP groep inhoud, mag niet null zijn
     * @param historie de LO3 historie, mag niet null zijn
     * @param documentatie de LO3 documentatie, mag null zijn
     * @param lo3Herkomst de herkomst, mag null zijn
     * @param afsluitendGroep als deze groep alleen gebruikt wordt om een eerdere groep af te sluiten (bv bij het splitsen van relaties)
     * @param oorsprongVoorkomenLeeg true als het voorkomen waaruit deze groep ontstaat, inhoudelijk leeg is.
     * @throws NullPointerException als inhoud of historie null is
     */
    public TussenGroep(
            final T inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Herkomst lo3Herkomst,
            final boolean afsluitendGroep,
            final boolean oorsprongVoorkomenLeeg) {
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
        this.afsluitendeGroep = afsluitendGroep;
        this.oorsprongVoorkomenLeeg = oorsprongVoorkomenLeeg;
    }

    /**
     * Geef de waarde van inhoud van TussenGroep.
     * @return de waarde van inhoud van TussenGroep
     */
    public T getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van historie van TussenGroep.
     * @return de waarde van historie van TussenGroep
     */
    public Lo3Historie getHistorie() {
        return historie;
    }

    /**
     * Geef de waarde van documentatie van TussenGroep.
     * @return de waarde van documentatie van TussenGroep
     */
    public Lo3Documentatie getDocumentatie() {
        return documentatie;
    }

    /**
     * Geef de waarde van lo3 herkomst van TussenGroep.
     * @return de waarde van lo3 herkomst van TussenGroep
     */
    public Lo3Herkomst getLo3Herkomst() {
        return lo3Herkomst;
    }

    /**
     * Geef de waarde van inhoudelijk leeg van TussenGroep.
     * @return de waarde van inhoudelijk leeg van TussenGroep
     */
    public boolean isInhoudelijkLeeg() {
        return getInhoud().isLeeg();
    }

    /**
     * Geef de waarde van afsluitende groep van TussenGroep.
     * @return de waarde van afsluitende groep van TussenGroep
     */
    public boolean isAfsluitendeGroep() {
        return afsluitendeGroep;
    }

    /**
     * Geef de waarde van oorsprong voorkomen leeg van TussenGroep.
     * @return de waarde van oorsprong voorkomen leeg van TussenGroep
     */
    public boolean isOorsprongVoorkomenLeeg() {
        return oorsprongVoorkomenLeeg;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TussenGroep)) {
            return false;
        }
        final TussenGroep<?> castOther = (TussenGroep<?>) other;
        return new EqualsBuilder().append(inhoud, castOther.inhoud).append(historie, castOther.historie).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(inhoud).append(historie).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("inhoud", inhoud)
                .append("historie", historie)
                .append("documentatie", documentatie)
                .append("lo3Herkomst", lo3Herkomst)
                .toString();
    }
}
