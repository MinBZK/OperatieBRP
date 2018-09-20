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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;

/**
 * Conversietabel ten behoeve van de voorvoegseltabel (LO3) enerzijds, en de waarden voor het voorvoegsel en
 * scheidingsteken (BRP) anderzijds.
 *
 * Bij de conversie wordt de waarde van de voorvoegseltabel (LO3) vertaald naar een waarde van voorvoegsel en
 * scheidingsteken (BRP).
 *
 *
 *
 */
@Entity(name = "beheer.ConversieVoorvoegsel")
@Table(schema = "Conv", name = "ConvVoorvoegsel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieVoorvoegsel {

    @Id
    @SequenceGenerator(name = "CONVERSIEVOORVOEGSEL", sequenceName = "Conv.seq_ConvVoorvoegsel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEVOORVOEGSEL")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr0231Voorvoegsel"))
    private LO3VoorvoegselAttribuut rubriek0231Voorvoegsel;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    private NaamEnumeratiewaardeAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    private ScheidingstekenAttribuut scheidingsteken;

    /**
     * Retourneert ID van Conversie voorvoegsel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 0231 Voorvoegsel van Conversie voorvoegsel.
     *
     * @return Rubriek 0231 Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3VoorvoegselAttribuut getRubriek0231Voorvoegsel() {
        return rubriek0231Voorvoegsel;
    }

    /**
     * Retourneert Voorvoegsel van Conversie voorvoegsel.
     *
     * @return Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van Conversie voorvoegsel.
     *
     * @return Scheidingsteken.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet ID van Conversie voorvoegsel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 0231 Voorvoegsel van Conversie voorvoegsel.
     *
     * @param pRubriek0231Voorvoegsel Rubriek 0231 Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek0231Voorvoegsel(final LO3VoorvoegselAttribuut pRubriek0231Voorvoegsel) {
        this.rubriek0231Voorvoegsel = pRubriek0231Voorvoegsel;
    }

    /**
     * Zet Voorvoegsel van Conversie voorvoegsel.
     *
     * @param pVoorvoegsel Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setVoorvoegsel(final NaamEnumeratiewaardeAttribuut pVoorvoegsel) {
        this.voorvoegsel = pVoorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Conversie voorvoegsel.
     *
     * @param pScheidingsteken Scheidingsteken.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setScheidingsteken(final ScheidingstekenAttribuut pScheidingsteken) {
        this.scheidingsteken = pScheidingsteken;
    }

}
