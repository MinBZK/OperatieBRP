/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;

import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

/**
 * De implementatie van de Leveringsautorisatie rRepository.
 */
@Repository
public final class LeveringsautorisatieRepositoryImpl implements LeveringsautorisatieRepository {

    /**
     * Standaard fetch size voor query.
     */
    private static final Integer DEFAULT_FETCH_SIZE = Integer.valueOf(100);

    private static final String SELECT_BY_PARTIJ_QUERY = "SELECT autorisatie FROM Leveringsautorisatie autorisatie, "
            + "IN (autorisatie.toegangLeveringsautorisatieSet) toegang "
            + "WHERE toegang.geautoriseerde = :partijRol";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public List<Leveringsautorisatie> findLeveringsautorisatiesVoorPartij(final PartijRol partijRol) {
        final TypedQuery<Leveringsautorisatie> query = em.createQuery(SELECT_BY_PARTIJ_QUERY, Leveringsautorisatie.class);
        query.setParameter("partijRol", partijRol);

        return query.getResultList();
    }

    @Override
    public Leveringsautorisatie saveLeveringsautorisatie(Leveringsautorisatie leveringsautorisatie) {
        ALaagAfleidingsUtil.vulALaag(leveringsautorisatie);
        if (leveringsautorisatie.getId() == null) {
            em.persist(leveringsautorisatie);
            return leveringsautorisatie;
        } else {
            return em.merge(leveringsautorisatie);
        }
    }

    @Override
    public Collection<Leveringsautorisatie> geefAlleGbaLeveringsautorisaties() {
        final TypedQuery<Leveringsautorisatie> query =
                em.createQuery(
                        "select l from Leveringsautorisatie l where l.stelselId = :stelselId and (l.datumEinde is null or l.datumEinde >= :datumEinde)",
                        Leveringsautorisatie.class);
        query.setParameter("stelselId", Stelsel.GBA.getId());
        query.setParameter("datumEinde", vandaag());
        return query.setHint(QueryHints.FETCH_SIZE, DEFAULT_FETCH_SIZE).getResultList();
    }

    private Integer vandaag() {
        return Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    @Override
    public List<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieByPartijEnDatumIngang(PartijRol partijRol, Integer datumIngang) {
        final TypedQuery<ToegangLeveringsAutorisatie> query =
                em.createQuery(
                        "select t from ToegangLeveringsAutorisatie t where t.geautoriseerde = :geautoriseerde and t.datumIngang >= :datingang",
                        ToegangLeveringsAutorisatie.class);
        query.setParameter("geautoriseerde", partijRol);
        query.setParameter("datingang", datumIngang);
        return query.getResultList();
    }


}
