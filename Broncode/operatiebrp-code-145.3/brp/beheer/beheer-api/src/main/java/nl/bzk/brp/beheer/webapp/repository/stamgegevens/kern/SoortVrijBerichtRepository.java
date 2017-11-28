/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * SoortVrijBericht repository.
 */
@Master
public interface SoortVrijBerichtRepository extends ReadWriteRepository<SoortVrijBericht, Short> {

    /**
     * Haal alle geldige soort vrij bericht stamdata op.
     * @param peildatum peildatum
     * @return de lijst met soort vrij bericht
     */
    @Query("select svb from SoortVrijBericht svb where "
            + "(svb.datumAanvangGeldigheid <= :peildatum or svb.datumAanvangGeldigheid is null) "
            + "and (svb.datumEindeGeldigheid > :peildatum or svb.datumEindeGeldigheid is null)")
    List<SoortVrijBericht> getGeldigeSoortVrijBericht(@Param("peildatum") Integer peildatum);
}
