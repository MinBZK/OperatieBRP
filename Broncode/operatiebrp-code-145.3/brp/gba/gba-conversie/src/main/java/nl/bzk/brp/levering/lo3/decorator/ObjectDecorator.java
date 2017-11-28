/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.decorator;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Object decorator.
 */
public class ObjectDecorator {

    private final MetaObject metaObject;

    /**
     * Constructor.
     * @param metaObject te decoreren object
     */
    protected ObjectDecorator(final MetaObject metaObject) {
        this.metaObject = metaObject;
    }

    /**
     * Geef de objectsleutel.
     * @return objectsleutel
     */
    protected final long getObjectsleutel() {
        return metaObject.getObjectsleutel();
    }

    /**
     * Geef child objecten van een bepaald object element gewrapped in een object decorator.
     * @param element object element van de gezochte children.
     * @param decorator decorator voor de child objecten
     * @param <T> type waari nde child objecten gewrapped worden.
     * @return lijst van child objecten
     */
    protected final <T> Collection<T> getObjecten(final ObjectElement element, final Function<? super MetaObject, T> decorator) {
        return metaObject.getObjecten()
                .stream()
                .filter(childMetaObject -> childMetaObject.getObjectElement().equals(element))
                .map(decorator)
                .collect(Collectors.toList());
    }

    /**
     * Geef de record van een bepaalde groep element gewrapped in een record decorator.
     * @param element groep element van de gezochte records
     * @param decorator decorator voor de record
     * @param <T> type waarin de record gewrapped worden.
     * @return lijst van records
     */
    protected final <T> Collection<T> getRecords(final GroepElement element, final Function<? super MetaRecord, T> decorator) {
        return metaObject.getGroep(element).getRecords().stream().map(decorator).collect(Collectors.toList());
    }

}
