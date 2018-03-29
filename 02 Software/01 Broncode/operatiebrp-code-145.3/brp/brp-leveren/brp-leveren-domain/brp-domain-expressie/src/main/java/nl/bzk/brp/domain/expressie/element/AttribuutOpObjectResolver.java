/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import java.util.Collection;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Zoekt een attribuut op variabele x, waar x een object is.
 */
final class AttribuutOpObjectResolver implements Resolver {

    private final AttribuutElement attribuutElement;
    private final Prioriteit prioriteit;

    AttribuutOpObjectResolver(final AttribuutElement attribuutElement, final Prioriteit prioriteit) {
        this.attribuutElement = attribuutElement;
        this.prioriteit = prioriteit;
    }

    @Override
    public Collection<? extends MetaModel> apply(final ModelIndex modelIndex, final Object contextObject) {
        return modelIndex.geefAttributen(attribuutElement).stream()
                .filter(metaAttribuut -> metaAttribuut.getParentRecord().getParentGroep().getParentObject() == contextObject)
                .collect(Collectors.toList());
    }

    @Override
    public boolean matchContext(final Object contextObject) {
        return contextObject instanceof MetaObject
                && ((MetaObject) contextObject).getObjectElement() == attribuutElement.getGroep().getObjectElement();
    }

    @Override
    public Prioriteit prioriteit() {
        return prioriteit;
    }
}
