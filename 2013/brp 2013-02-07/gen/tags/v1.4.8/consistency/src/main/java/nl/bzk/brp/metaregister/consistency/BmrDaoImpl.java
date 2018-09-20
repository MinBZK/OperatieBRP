/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.metaregister.consistency;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import nl.bzk.brp.metaregister.model.Attribuut;
import nl.bzk.brp.metaregister.model.AttribuutType;
import nl.bzk.brp.metaregister.model.GeneriekElement;
import nl.bzk.brp.metaregister.model.Groep;
import nl.bzk.brp.metaregister.model.ObjectType;

import org.springframework.stereotype.Repository;


/** Dit is het Data Access Object voor het BMR. Met dit object kan informatie worden opgehaald uit het BMR. */
@Repository
public class BmrDaoImpl implements BmrDao {

    //TODO: test code heeft veel overlap met generatoren algemeen, mergen in metaregister dataaccess project?

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager em;

    /** {@inheritDoc} */
    @Override
    public List<ObjectType> getObjectTypen() {
        final String sql = "SELECT objectType FROM ObjectType objectType";
        return em.createQuery(sql, ObjectType.class).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<AttribuutType> getAttribuutTypen() {
        final String sql = "SELECT attibuutType FROM AttribuutType attibuutType";
        return em.createQuery(sql, AttribuutType.class).getResultList();
    }
    
    /** {@inheritDoc} */
    @Override
    public List<Groep> getGroepenVoorObjectType(final ObjectType objectType) {
        final String sql = "SELECT groep FROM Groep groep WHERE groep.objectType = :objectType "
            + "ORDER BY groep.volgnummerG ASC";
        return em.createQuery(sql, Groep.class).setParameter("objectType", objectType).getResultList();
    }

    /** {@inheritDoc} */
    @Override
    public List<Attribuut> getAttributenVanGroep(final Groep groep) {
        final String sql = "SELECT attr FROM Attribuut attr WHERE attr.groep = :groep "
            + "ORDER BY attr.volgnummer ASC";
        return em.createQuery(sql, Attribuut.class).setParameter("groep", groep).getResultList();
    }
    
    /** {@inheritDoc} */
    @Override
    public <T extends GeneriekElement> T getElement(final Integer id, final Class<T> clazz) {
        final String sql = "SELECT element FROM " + clazz.getName() + " element WHERE element.id = :id";
        return em.createQuery(sql, clazz).setParameter("id", id).getSingleResult();
    }


}
