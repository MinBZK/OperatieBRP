/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RNIDeelnemerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de RNI deelnemer (LO3) enerzijds, en de partij (BRP) anderzijds.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieRNIDeelnemer {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RNIDeelnemerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8811CodeRNIDeelnemer"))
    @JsonProperty
    private LO3RNIDeelnemerAttribuut rubriek8811CodeRNIDeelnemer;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij partij;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieRNIDeelnemer() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek8811CodeRNIDeelnemer rubriek8811CodeRNIDeelnemer van ConversieRNIDeelnemer.
     * @param partij partij van ConversieRNIDeelnemer.
     */
    protected AbstractConversieRNIDeelnemer(final LO3RNIDeelnemerAttribuut rubriek8811CodeRNIDeelnemer, final Partij partij) {
        this.rubriek8811CodeRNIDeelnemer = rubriek8811CodeRNIDeelnemer;
        this.partij = partij;

    }

    /**
     * Retourneert ID van Conversie RNI-deelnemer.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 8811 Code RNI-deelnemer van Conversie RNI-deelnemer.
     *
     * @return Rubriek 8811 Code RNI-deelnemer.
     */
    public final LO3RNIDeelnemerAttribuut getRubriek8811CodeRNIDeelnemer() {
        return rubriek8811CodeRNIDeelnemer;
    }

    /**
     * Retourneert Partij van Conversie RNI-deelnemer.
     *
     * @return Partij.
     */
    public final Partij getPartij() {
        return partij;
    }

}
