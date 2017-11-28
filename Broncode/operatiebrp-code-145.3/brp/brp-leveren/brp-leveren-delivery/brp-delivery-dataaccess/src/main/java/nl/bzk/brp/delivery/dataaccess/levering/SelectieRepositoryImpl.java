/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van {@link SelectieRepository}
 */
@Repository
public final class SelectieRepositoryImpl implements SelectieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    private SelectieRepositoryImpl() {
    }


    @Override
    public List<Selectietaak> getTakenGeplandVoorVandaag() {
        return em.createQuery("select taak from Selectietaak taak "
                        + "where "
                        + "taak.datumPlanning = :datumPlanning "
                        + "and taak.status = :status "
                        + "and taak.datumUitvoer is null "
                        + "and taak.isActueelEnGeldig = true",
                Selectietaak.class)
                .setParameter("datumPlanning", DatumUtil.vandaag())
                .setParameter("status", (short) SelectietaakStatus.UITVOERBAAR.getId())
                .getResultList();
    }

    @Override
    public Selectietaak haalSelectietaakOp(final int selectietaakId) {
        return em.find(Selectietaak.class, selectietaakId);
    }

    @Override
    public void slaSelectieOp(final Selectierun selectierun) {
        em.persist(selectierun);
    }

    @Override
    public void werkSelectieBij(final Selectierun selectierun) {
        em.merge(selectierun);
    }

    @Override
    public void slaSelectietaakOp(final Selectietaak selectietaak) {
        em.persist(selectietaak);
    }

    @Override
    public List<Selectietaak> getSelectietakenMetStatusTeProtocolleren() {
        return em.createQuery("select taak from Selectietaak taak where taak.status = :status and taak.isActueelEnGeldig = true",
                Selectietaak.class)
                .setParameter("status", (short) SelectietaakStatus.TE_PROTOCOLLEREN.getId())
                .getResultList();
    }
}
