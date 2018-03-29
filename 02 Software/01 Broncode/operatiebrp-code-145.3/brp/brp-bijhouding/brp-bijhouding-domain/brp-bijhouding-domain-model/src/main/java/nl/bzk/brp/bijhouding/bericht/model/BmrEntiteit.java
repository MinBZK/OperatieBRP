/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECT_SLEUTEL_ATTRIBUUT;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;

/**
 * Classes uit het bijhouding berichtenmodel die deze interface implementeren definieren elementen die mappen op een
 * entiteit in de brp database. Deze elementen kunnen een objectsleutel bevatten die gebruikt wordt om de entiteit in de
 * database te identificeren.
 * @param <T> het gewenste type
 */
public interface BmrEntiteit<T extends RootEntiteit> extends BmrGroep {

    /**
     * Geeft aan of de {@link BmrEntiteit} een object sleutel heeft.
     * @return true als de {@link BmrEntiteit} een object sleutel heeft, anders false.
     */
    default boolean heeftObjectSleutel(){
        return getObjectSleutel() != null;
    }

    /**
     * Geeft de waarde van het objectsleutel attribuut van dit element. Mag null zijn.
     *
     * @return de objectsleutel of null
     */
    default String getObjectSleutel() {
        return getAttributen().get(OBJECT_SLEUTEL_ATTRIBUUT.toString());
    }

    /**
     * Het type Root Entiteit dat hoort bij dit element en kan worden geidentificeerd door de objectsleutel.
     *
     * @return de entiteit die hoort bij dit element
     */
    Class<T> getEntiteitType();

    /**
     * Geeft het bijbehorende entiteit terug als heeftObjectSleutel true geeft.
     * @return de bijbehorende entiteit anders null.
     */
    default T getEntiteit(){
        return getVerzoekBericht().getEntiteitVoorObjectSleutel(getEntiteitType(), getObjectSleutel());
    }

    /**
     * Geeft aan of deze {@link BmrEntiteit} in de {@link ObjectSleutelIndex} voorkomt.
     * @return true als de {@link BmrEntiteit} in de {@link ObjectSleutelIndex} voorkomt
     */
    default boolean inObjectSleutelIndex() {
        return false;
    }
}
