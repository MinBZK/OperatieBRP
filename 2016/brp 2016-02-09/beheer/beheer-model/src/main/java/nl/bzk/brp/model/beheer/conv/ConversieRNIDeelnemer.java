/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RNIDeelnemerAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;

/**
 * Conversietabel ten behoeve van de RNI deelnemer (LO3) enerzijds, en de partij (BRP) anderzijds.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieRNIDeelnemer")
@Table(schema = "Conv", name = "ConvRNIDeelnemer")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieRNIDeelnemer {

    @Id
    @SequenceGenerator(name = "CONVERSIERNIDEELNEMER", sequenceName = "Conv.seq_ConvRNIDeelnemer")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIERNIDEELNEMER")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3RNIDeelnemerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr8811CodeRNIDeelnemer"))
    private LO3RNIDeelnemerAttribuut rubriek8811CodeRNIDeelnemer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Partij")
    private Partij partij;

    /**
     * Retourneert ID van Conversie RNI-deelnemer.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 8811 Code RNI-deelnemer van Conversie RNI-deelnemer.
     *
     * @return Rubriek 8811 Code RNI-deelnemer.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3RNIDeelnemerAttribuut getRubriek8811CodeRNIDeelnemer() {
        return rubriek8811CodeRNIDeelnemer;
    }

    /**
     * Retourneert Partij van Conversie RNI-deelnemer.
     *
     * @return Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet ID van Conversie RNI-deelnemer.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 8811 Code RNI-deelnemer van Conversie RNI-deelnemer.
     *
     * @param pRubriek8811CodeRNIDeelnemer Rubriek 8811 Code RNI-deelnemer.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek8811CodeRNIDeelnemer(final LO3RNIDeelnemerAttribuut pRubriek8811CodeRNIDeelnemer) {
        this.rubriek8811CodeRNIDeelnemer = pRubriek8811CodeRNIDeelnemer;
    }

    /**
     * Zet Partij van Conversie RNI-deelnemer.
     *
     * @param pPartij Partij.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPartij(final Partij pPartij) {
        this.partij = pPartij;
    }

}
