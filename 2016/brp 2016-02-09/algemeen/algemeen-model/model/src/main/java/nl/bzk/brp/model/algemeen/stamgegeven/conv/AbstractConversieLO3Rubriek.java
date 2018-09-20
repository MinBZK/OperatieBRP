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
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieLO3RubriekNaamAttribuut;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieLO3Rubriek {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = ConversieLO3RubriekNaamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    @JsonProperty
    private ConversieLO3RubriekNaamAttribuut naam;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieLO3Rubriek() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van ConversieLO3Rubriek.
     */
    protected AbstractConversieLO3Rubriek(final ConversieLO3RubriekNaamAttribuut naam) {
        this.naam = naam;

    }

    /**
     * Retourneert ID van Conversie LO3 Rubriek.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Conversie LO3 Rubriek.
     *
     * @return Naam.
     */
    public final ConversieLO3RubriekNaamAttribuut getNaam() {
        return naam;
    }

}
