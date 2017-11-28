/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.BiFunction;
import nl.bzk.brp.domain.element.ElementConstants;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Interface voor het vinden van {@link MetaModel} instanties binnen de gegeven parent context.
 */
@FunctionalInterface
interface Resolver extends BiFunction<ModelIndex, Object, Collection> {

    /**
     * Bepaalt of deze resolver geschikt is voor het zoeken van kindobjecten binnen
     * het gegeven contextObject. Potentieel zijn er meerdere {@link Resolver}s geschikt,
     * dit probleem wordt opgelost met {@link Prioriteit}.
     *
     * @param contextObject het object waarbinnen gezocht moet worden
     * @return boolean indicatie of deze {@link Resolver} geschikt is o
     */
    default boolean matchContext(Object contextObject) {
        return contextObject instanceof MetaObject && ((MetaObject) contextObject).getObjectElement().isVanType(ElementConstants.PERSOON);
    }

    /**
     * Definieert de proriteit van de {@link Resolver}
     * @return een {@link Prioriteit}
     */
    default Prioriteit prioriteit() {
        return Prioriteit.HOOG;
    }

    /**
     * @return indicatie of het resultaat gefilterd dient te worden (actueel/ historisch).
     */
    default boolean postFilter() {
        return true;
    }

    /**
     * @return een comparator voor het sorteren val {@link Resolver}s obv prioriteit
     */
    static Comparator<Resolver> prioriteitComparator() {
        return (o1, o2) -> o2.prioriteit().getPrioriteitWaarde() - o1.prioriteit().getPrioriteitWaarde();
    }
}
