/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

/**
 * Implementatie van de expressies repositorie.
 */
@Repository("gbaLo3FilterRubriekRepository")
public class Lo3FilterRubriekRepositoryImpl implements Lo3FilterRubriekRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public final List<String> haalLo3FilterRubriekenVoorDienstbundel(final Integer dienstbundelId) {
        final TypedQuery<String> query = entityManager.createQuery(
                "select rubrieken.lo3Rubriek.naam from DienstbundelLo3Rubriek rubrieken where rubrieken.dienstbundel.id = :dienstbundelId", String.class);
        query.setParameter("dienstbundelId", dienstbundelId);
        return query.getResultList();
    }

}
