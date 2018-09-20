/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.toegangsbewaking.test.model.Functieadres;
import nl.bzk.brp.toegangsbewaking.test.model.Gem;
import nl.bzk.brp.toegangsbewaking.test.model.Geslachtsaand;
import nl.bzk.brp.toegangsbewaking.test.model.Land;
import nl.bzk.brp.toegangsbewaking.test.model.Pers;
import nl.bzk.brp.toegangsbewaking.test.model.Persadres;
import nl.bzk.brp.toegangsbewaking.test.model.Plaats;
import nl.bzk.brp.toegangsbewaking.test.model.Verantwoordelijke;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public class PersoonDAO {

    // Injected database connection:
    @PersistenceContext
    private EntityManager em;

    /**
     * Retourneert alle personen
     * 
     * @return alle personen
     */
    public List<Pers> getAllePersonen() {
        TypedQuery<Pers> query = em.createQuery("SELECT p FROM Pers p ORDER BY p.id", Pers.class);
        return query.getResultList();
    }

    /**
     * Retourneert alle personen
     * 
     * @return alle personen
     */
    public List<Pers> getAllePersonen(final String filter) {
        String clause = "";
        if (!filter.isEmpty()) {
            clause = "WHERE " + filter;
        }
        TypedQuery<Pers> query =
                em.createQuery(String.format("SELECT p FROM Pers p %s ORDER BY p.id", clause), Pers.class);
        return query.getResultList();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Pers opslaan(final Pers pers) {
        return em.merge(pers);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public Persadres opslaan(final Persadres adres) {
        return em.merge(adres);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void verwijderen(final Pers pers) {
        em.remove(em.merge(pers));
    }

    public Pers vindPersMiddelsBSN(final Integer bsn) {
        TypedQuery<Pers> query = em.createQuery("SELECT p FROM Pers p where p.bsn = :bsn", Pers.class);
        query.setParameter("bsn", bsn);
        return query.getSingleResult();
    }

    public Pers vindPersMiddelsId(final Long id) {
        System.out.println("PERSOON ZOEKEN OP ID: " + id);
        return em.find(Pers.class, id);
    }

    public List<Geslachtsaand> getAlleGeslachtsAanduidingen() {
        TypedQuery<Geslachtsaand> query =
                em.createQuery("SELECT g FROM Geslachtsaand g ORDER BY g.id", Geslachtsaand.class);
        return query.getResultList();
    }

    public List<Gem> getAlleGemeentes() {
        TypedQuery<Gem> query = em.createQuery("SELECT g FROM Gem g ORDER BY g.naam", Gem.class);
        return query.getResultList();
    }

    public List<Plaats> getAllePlaatsen() {
        TypedQuery<Plaats> query = em.createQuery("SELECT p FROM Plaats p ORDER BY p.naam", Plaats.class);
        return query.getResultList();
    }

    public List<Land> getAlleLanden() {
        TypedQuery<Land> query = em.createQuery("SELECT l FROM Land l ORDER BY l.naam", Land.class);
        return query.getResultList();
    }

    public List<Verantwoordelijke> getAlleVerantwoordelijken() {
        TypedQuery<Verantwoordelijke> query =
                em.createQuery("SELECT v FROM Verantwoordelijke v ORDER BY v.naam", Verantwoordelijke.class);
        return query.getResultList();
    }

    public List<Functieadres> getAlleAdresFuncties() {
        TypedQuery<Functieadres> query =
                em.createQuery("SELECT fa FROM Functieadres fa ORDER BY fa.naam", Functieadres.class);
        return query.getResultList();
    }
}
