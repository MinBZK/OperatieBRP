/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository voor entity PersistentActie.
 */
@Repository
public interface ActieRepository {
    /**
     * .
     * @param model .
     * @return .
     */
    ActieModel opslaanNieuwActie(ActieModel model);

//    public PersoonModel opslaanNieuwPersoon(final PersoonModel persoon, final ActieModel actie,
//            final Datum datumAanvangGeldigheid)
}
