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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieLO3RubriekNaamAttribuut;

/**
 * De in LO3 gedefinieerde LO3 rubrieken
 *
 * Er is vooralsnog voor gekozen om de rubriek niet te splitsen in Categorie, Groep en Rubriek omdat momenteel alleen de
 * volledige Rubriek-string relevant is.
 *
 * De locatie van deze tabel in het Conv schema is dubieus. Feitelijk is deze tabel alleen relevant tijdens de duale
 * periode, daarom is een plaats in de overige schemas niet terecht. Omdat dit inhoud eigenlijk alleen relevant is voor
 * conversie doeleinden tussen BRP en GBA is het Conv schema gekozen.
 *
 *
 *
 */
@Entity(name = "beheer.ConversieLO3Rubriek")
@Table(schema = "Conv", name = "ConvLO3Rubriek")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieLO3Rubriek {

    @Id
    @SequenceGenerator(name = "CONVERSIELO3RUBRIEK", sequenceName = "Conv.seq_ConvLO3Rubriek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIELO3RUBRIEK")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = ConversieLO3RubriekNaamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private ConversieLO3RubriekNaamAttribuut naam;

    /**
     * Retourneert ID van Conversie LO3 Rubriek.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Conversie LO3 Rubriek.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ConversieLO3RubriekNaamAttribuut getNaam() {
        return naam;
    }

    /**
     * Zet ID van Conversie LO3 Rubriek.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Naam van Conversie LO3 Rubriek.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final ConversieLO3RubriekNaamAttribuut pNaam) {
        this.naam = pNaam;
    }

}
