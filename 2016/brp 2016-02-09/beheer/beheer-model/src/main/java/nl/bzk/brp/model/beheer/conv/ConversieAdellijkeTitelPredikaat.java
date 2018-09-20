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
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AdellijkeTitelPredikaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.beheer.kern.AdellijkeTitel;
import nl.bzk.brp.model.beheer.kern.Predicaat;

/**
 * Conversietabel ten behoeve van de adellijke titel/predikaat (LO3) enerzijds, dan wel de adellijke titel of predikaat
 * (BRP) anderzijds.
 *
 * Bij de conversie wordt de waarde van de rubriek(LO3) omgezet in een waarde voor de adellijke titel of een waarde voor
 * het predikaat. Voor de terugconversie is het geslacht ook van belang: voor de meeste adellijke titels en predikaten
 * geldt dat daar waar de BRP één waarde kent, het LO3 stelsel er één kent voor de mannelijke en één voor de vrouwelijke
 * variant.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieAdellijkeTitelPredikaat")
@Table(schema = "Conv", name = "ConvAdellijkeTitelPredikaat")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieAdellijkeTitelPredikaat {

    @Id
    @SequenceGenerator(name = "CONVERSIEADELLIJKETITELPREDIKAAT", sequenceName = "Conv.seq_ConvAdellijkeTitelPredikaat")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEADELLIJKETITELPREDIKAAT")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3AdellijkeTitelPredikaatAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr0221AdellijkeTitelPredik"))
    private LO3AdellijkeTitelPredikaatAttribuut rubriek0221AdellijkeTitelPredikaat;

    @Column(name = "Geslachtsaand")
    @Enumerated
    private Geslachtsaanduiding geslachtsaanduiding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AdellijkeTitel")
    private AdellijkeTitel adellijkeTitel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Predicaat")
    private Predicaat predicaat;

    /**
     * Retourneert ID van Conversie adellijke titel predikaat.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 0221 Adellijke titel predikaat van Conversie adellijke titel predikaat.
     *
     * @return Rubriek 0221 Adellijke titel predikaat.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3AdellijkeTitelPredikaatAttribuut getRubriek0221AdellijkeTitelPredikaat() {
        return rubriek0221AdellijkeTitelPredikaat;
    }

    /**
     * Retourneert Geslachtsaanduiding van Conversie adellijke titel predikaat.
     *
     * @return Geslachtsaanduiding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Retourneert Adellijke titel van Conversie adellijke titel predikaat.
     *
     * @return Adellijke titel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * Retourneert Predicaat van Conversie adellijke titel predikaat.
     *
     * @return Predicaat.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Predicaat getPredicaat() {
        return predicaat;
    }

    /**
     * Zet ID van Conversie adellijke titel predikaat.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 0221 Adellijke titel predikaat van Conversie adellijke titel predikaat.
     *
     * @param pRubriek0221AdellijkeTitelPredikaat Rubriek 0221 Adellijke titel predikaat.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek0221AdellijkeTitelPredikaat(final LO3AdellijkeTitelPredikaatAttribuut pRubriek0221AdellijkeTitelPredikaat) {
        this.rubriek0221AdellijkeTitelPredikaat = pRubriek0221AdellijkeTitelPredikaat;
    }

    /**
     * Zet Geslachtsaanduiding van Conversie adellijke titel predikaat.
     *
     * @param pGeslachtsaanduiding Geslachtsaanduiding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setGeslachtsaanduiding(final Geslachtsaanduiding pGeslachtsaanduiding) {
        this.geslachtsaanduiding = pGeslachtsaanduiding;
    }

    /**
     * Zet Adellijke titel van Conversie adellijke titel predikaat.
     *
     * @param pAdellijkeTitel Adellijke titel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAdellijkeTitel(final AdellijkeTitel pAdellijkeTitel) {
        this.adellijkeTitel = pAdellijkeTitel;
    }

    /**
     * Zet Predicaat van Conversie adellijke titel predikaat.
     *
     * @param pPredicaat Predicaat.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setPredicaat(final Predicaat pPredicaat) {
        this.predicaat = pPredicaat;
    }

}
