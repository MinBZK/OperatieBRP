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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3AdellijkeTitelPredikaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predicaat;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieAdellijkeTitelPredikaat {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3AdellijkeTitelPredikaatAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr0221AdellijkeTitelPredik"))
    @JsonProperty
    private LO3AdellijkeTitelPredikaatAttribuut rubriek0221AdellijkeTitelPredikaat;

    @Column(name = "Geslachtsaand")
    private Geslachtsaanduiding geslachtsaanduiding;

    @ManyToOne
    @JoinColumn(name = "AdellijkeTitel")
    @Fetch(value = FetchMode.JOIN)
    private AdellijkeTitel adellijkeTitel;

    @ManyToOne
    @JoinColumn(name = "Predicaat")
    @Fetch(value = FetchMode.JOIN)
    private Predicaat predicaat;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieAdellijkeTitelPredikaat() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek0221AdellijkeTitelPredikaat rubriek0221AdellijkeTitelPredikaat van
     *            ConversieAdellijkeTitelPredikaat.
     * @param geslachtsaanduiding geslachtsaanduiding van ConversieAdellijkeTitelPredikaat.
     * @param adellijkeTitel adellijkeTitel van ConversieAdellijkeTitelPredikaat.
     * @param predicaat predicaat van ConversieAdellijkeTitelPredikaat.
     */
    protected AbstractConversieAdellijkeTitelPredikaat(
        final LO3AdellijkeTitelPredikaatAttribuut rubriek0221AdellijkeTitelPredikaat,
        final Geslachtsaanduiding geslachtsaanduiding,
        final AdellijkeTitel adellijkeTitel,
        final Predicaat predicaat)
    {
        this.rubriek0221AdellijkeTitelPredikaat = rubriek0221AdellijkeTitelPredikaat;
        this.geslachtsaanduiding = geslachtsaanduiding;
        this.adellijkeTitel = adellijkeTitel;
        this.predicaat = predicaat;

    }

    /**
     * Retourneert ID van Conversie adellijke titel predikaat.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 0221 Adellijke titel predikaat van Conversie adellijke titel predikaat.
     *
     * @return Rubriek 0221 Adellijke titel predikaat.
     */
    public final LO3AdellijkeTitelPredikaatAttribuut getRubriek0221AdellijkeTitelPredikaat() {
        return rubriek0221AdellijkeTitelPredikaat;
    }

    /**
     * Retourneert Geslachtsaanduiding van Conversie adellijke titel predikaat.
     *
     * @return Geslachtsaanduiding.
     */
    public final Geslachtsaanduiding getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    /**
     * Retourneert Adellijke titel van Conversie adellijke titel predikaat.
     *
     * @return Adellijke titel.
     */
    public final AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    /**
     * Retourneert Predicaat van Conversie adellijke titel predikaat.
     *
     * @return Predicaat.
     */
    public final Predicaat getPredicaat() {
        return predicaat;
    }

}
