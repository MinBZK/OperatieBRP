/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.element;

import com.google.common.collect.Lists;
import java.util.Collection;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ModelIndex;

/**
 * Zoekt een attribuut op variabele x, waar x een MetaRecord is.
 * <p>
 * bv:
 * <p>
 * MAP(x.burgerservicenummer
 */
final class AttribuutOpMetaRecordResolver implements Resolver {

    private final AttribuutElement attribuutElement;

    AttribuutOpMetaRecordResolver(final AttribuutElement attribuutElement) {
        this.attribuutElement = attribuutElement;
    }

    @Override
    public Collection<? extends MetaModel> apply(final ModelIndex modelIndex, final Object contextObject) {
        final MetaRecord metaRecord = (MetaRecord) contextObject;
        return Lists.newArrayList(metaRecord.getAttribuut(attribuutElement));
    }

    @Override
    public boolean matchContext(final Object contextObject) {
        return contextObject instanceof MetaRecord
                && ((MetaRecord) contextObject).getParentGroep().getGroepElement() == attribuutElement.getGroep();
    }

    @Override
    public Prioriteit prioriteit() {
        return Prioriteit.MIDDEL;
    }

    @Override
    public boolean postFilter() {
        //voorkomt dat attributen wegvallen omdat ze op een niet-actueel record zitten
        return false;
    }
}
