/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieLO3RubriekNaamAttribuut;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * De in LO3 gedefinieerde LO3 rubrieken
 *
 * Er is vooralsnog voor gekozen om de rubriek niet te splitsen in Categorie, Groep en Rubriek omdat momenteel alleen de
 * volledige Rubriek-string relevant is.
 *
 * De locatie van deze tabel in het Conv schema is discutabel. Deze tabel is alleen relevant tijdens de duale
 * periode, daarom is een plaats in de overige schemas niet terecht. Omdat de inhoud alleen relevant is voor
 * conversie tussen BRP en GBA is het Conv schema gekozen.
 */
@Table(schema = "Conv", name = "ConvLO3Rubriek")
@Access(value = AccessType.FIELD)
@Entity
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieLO3Rubriek extends AbstractConversieLO3Rubriek {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected ConversieLO3Rubriek() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van ConversieLO3Rubriek.
     */
    protected ConversieLO3Rubriek(final ConversieLO3RubriekNaamAttribuut naam) {
        super(naam);
    }

}
