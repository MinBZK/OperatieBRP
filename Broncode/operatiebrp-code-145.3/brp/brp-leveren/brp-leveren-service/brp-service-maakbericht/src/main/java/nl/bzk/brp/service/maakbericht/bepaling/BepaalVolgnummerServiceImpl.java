/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.bepaling;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.stereotype.Service;

/**
 * BepaalVolgnummerServiceImpl.
 */
@Service
public final class BepaalVolgnummerServiceImpl implements BepaalVolgnummerService {
    @Override
    public Map<MetaObject, Integer> bepaalVolgnummers(final Persoonslijst persoonslijst) {
        final Map<MetaObject, Integer> volgnummerMap = new HashMap<>();

        new ParentFirstModelVisitor() {
            @Override
            protected void doVisit(final MetaObject metaObject) {
                volgnummerMap.put(metaObject, bepaalVolgorde(metaObject));
            }
        }.visit(persoonslijst.getMetaObject());

        return volgnummerMap;
    }

    /**
     * @param metaObject metaObject
     * @return volgnummer
     */
    private static Integer bepaalVolgorde(final MetaObject metaObject) {
        Integer volgorde = null;
        if (metaObject.getParentObject() != null) {
            final Collection<GroepElement> groepen = metaObject.getParentObject().getObjectElement()
                    .getTypeObjectElement().getGroepen();
            volgorde = bepaalVanGroepen(metaObject, groepen);
        }
        if (volgorde == null) {
            volgorde = metaObject.getObjectElement().getTypeObjectElement().getVolgnummer();
        }
        return volgorde;

    }

    private static Integer bepaalVanGroepen(final MetaObject metaObject, final Collection<GroepElement> groepen) {
        for (final GroepElement berichtGroep : groepen) {
            final Integer attribuutElement = bepaalVanGroep(metaObject, berichtGroep);
            if (attribuutElement != null) {
                return attribuutElement;
            }
        }
        return null;
    }

    private static Integer bepaalVanGroep(final MetaObject metaObject, final GroepElement berichtGroep) {
        if (!berichtGroep.isIdentiteitGroep()) {
            return null;
        }

        for (final AttribuutElement attribuutElement : berichtGroep.getAttributenInGroep()) {
            if (attribuutElement.getType() != null) {
                //betrokken persoon in betrokkenheid
                final boolean matchAlias = metaObject.getObjectElement().getAliasVan() != null
                        && attribuutElement.getType().equals(metaObject.getObjectElement().getAliasVan()
                        .getId());

                //relatie binnen betrokkenheid
                final boolean matchType = attribuutElement.getType().equals(metaObject.getObjectElement()
                        .getType());

                if (matchAlias || matchType) {
                    return attribuutElement.getVolgnummer();
                }
            }
        }
        return null;
    }

}
