/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Converteert MetaObjecten naar het operationele verantwoording model. Dit wordt gebruikt bij het inlezen van de BLOB.
 */
public final class VerantwoordingConverter {

    private VerantwoordingConverter() {
    }

    /**
     * Converteert de verantwoording blob record naar AdministratieveHandeling objecten.
     * @param verantwoordingBlobRoot lijst met BlobRoot objecten
     * @return een set met AdministratieveHandeling objecten
     */
    public static Set<AdministratieveHandeling> map(final List<BlobRoot> verantwoordingBlobRoot) {
        final Set<AdministratieveHandeling> handelingen = Sets.newHashSet();
        for (final BlobRoot root : verantwoordingBlobRoot) {
            final Collection<MetaObject.Builder> builderList = new BlobTerugConverter(root,
                    AttribuutMapper.INSTANCE).geefRootMetaObjectBuilders();
            for (final MetaObject.Builder builder : builderList) {
                final MetaObject metaObject = builder.build();
                final AdministratieveHandeling ah = AdministratieveHandeling.converter().converteer(metaObject);
                handelingen.add(ah);
            }
        }
        return handelingen;
    }
}
