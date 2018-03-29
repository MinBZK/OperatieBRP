/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.alaag.ALaagAfleidingsUtil;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PartijRolRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO (Gegevens toegangs punt) voor alles omtrent BRP partijrol.
 */
@Repository
public final class PartijRolRepositoryImpl implements PartijRolRepository {

    private static final String COL_PARTIJ = "partij";
    private static final String COL_ROL = "rol";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public List<PartijRol> getPartijRollenByPartij(Partij partij) {
        final TypedQuery<PartijRol> query = em.createQuery("select p from PartijRol p where p.partij = :partij", PartijRol.class);
        query.setParameter(COL_PARTIJ, partij);
        return query.getResultList();
    }

    @Override
    public PartijRol getPartijRolByPartij(Partij partij, Rol rol) {
        final TypedQuery<PartijRol> query = em.createQuery("select p from PartijRol p where p.partij = :partij and p.rolId = :rol", PartijRol.class);
        query.setParameter(COL_PARTIJ, partij);
        query.setParameter(COL_ROL, rol.getId());
        final List<PartijRol> result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Override
    public PartijRol savePartijRol(PartijRol partijRol) {
        ALaagAfleidingsUtil.vulALaag(partijRol);
        if (partijRol.getId() == null) {
            em.persist(partijRol);
            return partijRol;
        } else {
            return em.merge(partijRol);
        }
    }
}
