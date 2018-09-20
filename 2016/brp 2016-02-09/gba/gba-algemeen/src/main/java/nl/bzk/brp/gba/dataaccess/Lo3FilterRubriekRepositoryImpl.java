/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van de expressies repositorie.
 */
@Repository("gbaLo3FilterRubriekRepository")
public class Lo3FilterRubriekRepositoryImpl implements Lo3FilterRubriekRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Override
    public final List<String> haalLo3FilterRubriekenVoorDienstbundel(final Dienstbundel dienstbundel) {
        final TypedQuery<String> query =
                entityManager.createQuery(
                    "select rubrieken.rubriek.naam.waarde from DienstbundelLO3Rubriek rubrieken where rubrieken.dienstbundel = :dienstbundel",
                    String.class);
        query.setParameter("dienstbundel", dienstbundel);
        return query.getResultList();
    }
}
