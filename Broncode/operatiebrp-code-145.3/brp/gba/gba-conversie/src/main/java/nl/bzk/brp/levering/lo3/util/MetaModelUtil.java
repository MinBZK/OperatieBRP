/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Meta model utilities.
 */
public interface MetaModelUtil {

    /**
     * Geef het object van het gegeven type.
     * @param object meta object
     * @param objectElement object type
     * @return objecten (kan null zijn)
     */
    static MetaObject getObject(final MetaObject object, final ObjectElement objectElement) {
        final Optional<MetaObject> resultaat =
                object.getObjecten().stream().filter(metaObject -> metaObject.getObjectElement().equals(objectElement)).findFirst();
        return resultaat.isPresent() ? resultaat.get() : null;
    }

    /**
     * Geef de objecten van het gegeven type.
     * @param object meta object
     * @param objectElement object type
     * @return objecten (kan lege lijst zijn)
     */
    static Collection<MetaObject> getObjecten(final MetaObject object, final ObjectElement objectElement) {
        return object.getObjecten()
                .stream()
                .filter(metaObject -> metaObject.getObjectElement().equals(objectElement))
                .sorted((a, b) -> Long.compare(a.getObjectsleutel(), b.getObjectsleutel()))
                .collect(Collectors.toList());
    }

    /**
     * Geef het identiteitrecord van een object.
     * @param object object
     * @param identiteitGroepElement identiteit groep element
     * @return het identiteit record
     * @throws IllegalArgumentException als de identiteit groep niet gevonden kan worden.
     */
    static MetaRecord getIdentiteitRecord(final MetaObject object, final GroepElement identiteitGroepElement) {
        final MetaGroep groep = object.getGroep(identiteitGroepElement);
        if (groep == null) {
            throw new IllegalArgumentException("Identiteit groep (" + identiteitGroepElement + ") niet gevonden");
        }
        return groep.getRecords().stream().findFirst().orElse(null);
    }

    /**
     * Geef de record van het gegeven type.
     * @param object meta object
     * @param groepElement groep type
     * @return record (kan lege lijst zijn)
     */
    static Collection<MetaRecord> getRecords(final MetaObject object, final GroepElement groepElement) {
        final MetaGroep groep = object.getGroep(groepElement);

        return groep == null ? Collections.emptyList()
                : groep.getRecords().stream().sorted((a, b) -> Long.compare(a.getVoorkomensleutel(), b.getVoorkomensleutel())).collect(
                        Collectors.toList());
    }

    /**
     * Retourneert het het actuele record. Dit is het record wat niet is vervallen en dat geen datum einde geldigheid heeft.
     * @param records records
     * @return het record wat niet is vervallen en geen datum einde geldigheid heeft; null als er geen actueel record is
     */
    static MetaRecord getActueleRecord(final Collection<MetaRecord> records) {
        for (final MetaRecord record : records) {
            if (record.getActieVerval() == null && (record.getDatumEindeGeldigheid() == null)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Retourneert het het actuele record (convenience voor {link {@link #getActueleRecord(Collection)} obv meta object en groep element).
     * @param object meta object
     * @param groepElement groep van de records
     * @return het record wat niet is vervallen en geen datum einde geldigheid heeft; null als er geen actueel record is
     */
    static MetaRecord getActueleRecord(final MetaObject object, final GroepElement groepElement) {
        return getActueleRecord(getRecords(object, groepElement));
    }

}
