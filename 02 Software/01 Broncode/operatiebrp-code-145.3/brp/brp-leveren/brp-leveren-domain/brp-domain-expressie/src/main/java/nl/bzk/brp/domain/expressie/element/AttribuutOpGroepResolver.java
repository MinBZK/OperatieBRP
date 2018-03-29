/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import java.util.Collection;
import java.util.stream.Collectors;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Zoekt een attribuut op variabele x, waar x een groep is.
 * <p>
 * bv:
 * <p>
 * MAP(x.burgerservicenummer
 */
final class AttribuutOpGroepResolver implements Resolver {

    private final AttribuutElement attribuutElement;

    AttribuutOpGroepResolver(final AttribuutElement attribuutElement) {
        this.attribuutElement = attribuutElement;
    }

    @Override
    public Collection<? extends MetaModel> apply(final ModelIndex modelIndex, final Object contextObject) {
        final MetaGroep metaGroep = (MetaGroep) contextObject;
        return metaGroep.getRecords().stream()
                .map(MetaRecord::getAttributen)
                .flatMap(attribuutElementMetaAttribuutMap -> attribuutElementMetaAttribuutMap.values().stream())
                .filter(metaAttribuut -> metaAttribuut.getAttribuutElement() == attribuutElement)
                .collect(Collectors.toList())
        ;
    }

    @Override
    public boolean matchContext(final Object contextObject) {
        return contextObject instanceof MetaGroep
                && ((MetaGroep) contextObject).getGroepElement() == attribuutElement.getGroep();
    }

    @Override
    public Prioriteit prioriteit() {
        return Prioriteit.MIDDEL;
    }
}
