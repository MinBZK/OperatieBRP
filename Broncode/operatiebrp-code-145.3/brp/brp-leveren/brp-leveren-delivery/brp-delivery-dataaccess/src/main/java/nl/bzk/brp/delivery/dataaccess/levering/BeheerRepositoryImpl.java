/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van de {@link BeheerRepository} interface.
 */
@Repository
public class BeheerRepositoryImpl implements BeheerRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    /**
     * Slaat een nieuw {@link VrijBericht} op
     * @param vrijBericht het nieuwe {@link VrijBericht}
     */
    @Override
    public void opslaanNieuwVrijBericht(VrijBericht vrijBericht) {
        em.persist(vrijBericht);
    }

    /**
     * Haalt een {@link SoortVrijBericht} op
     * @param naam van het {@link SoortVrijBericht}
     */
    @Override
    public SoortVrijBericht haalSoortVrijBerichtOp(final String naam) {
        final String query = "select svb from SoortVrijBericht  svb where svb.naam = :soortNaam";
        final List<SoortVrijBericht> soortVrijberichten =
                em.createQuery(query, SoortVrijBericht.class)
                        .setParameter("soortNaam", naam).getResultList();
        if (!soortVrijberichten.isEmpty()) {
            return soortVrijberichten.get(0);
        }
        return null;
    }
}
