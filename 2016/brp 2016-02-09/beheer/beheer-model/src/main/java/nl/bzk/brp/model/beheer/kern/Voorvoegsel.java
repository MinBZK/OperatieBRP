/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;

/**
 * De in de BRP en GBA onderkende voorvoegsels, die voor de geslachtsnaam worden geplaatst bij aanschrijving.
 *
 * Ten behoeve van de migratieperiode onderkend de BRP een vaste lijst met voorvoegsels, deze worden gebruikt in de
 * conversie tussen GBA_V en de BRP, en ten behoeve van een bedrijfsregel.
 *
 * De voorvoegseltabel is toegevoegd ten behoeve van onder andere de bedrijfsregel; er wordt NIET naar verwezen (in die
 * zin is het géén echte stamtabel); dat is omdat voorzien is dat op den duur de limitatie tot bepaalde voorvoegsels
 * wordt losgelaten.
 *
 *
 *
 */
@Entity(name = "beheer.Voorvoegsel")
@Table(schema = "Kern", name = "Voorvoegsel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Voorvoegsel {

    @Id
    @SequenceGenerator(name = "VOORVOEGSEL", sequenceName = "Kern.seq_Voorvoegsel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "VOORVOEGSEL")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    private ScheidingstekenAttribuut scheidingsteken;

    /**
     * Retourneert ID van Voorvoegsel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Voorvoegsel van Voorvoegsel.
     *
     * @return Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van Voorvoegsel.
     *
     * @return Scheidingsteken.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Zet ID van Voorvoegsel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Voorvoegsel van Voorvoegsel.
     *
     * @param pVoorvoegsel Voorvoegsel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setVoorvoegsel(final VoorvoegselAttribuut pVoorvoegsel) {
        this.voorvoegsel = pVoorvoegsel;
    }

    /**
     * Zet Scheidingsteken van Voorvoegsel.
     *
     * @param pScheidingsteken Scheidingsteken.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setScheidingsteken(final ScheidingstekenAttribuut pScheidingsteken) {
        this.scheidingsteken = pScheidingsteken;
    }

}
