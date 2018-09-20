/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatieverwerker.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.levering.mutatieverwerker.repository.AdministratieveHandelingVerwerkerRepository;
import org.springframework.stereotype.Repository;

/**
 * De Class AdministratieveHandelingVerwerkerRepositoryImpl, de implementatie voor de interface
 * AdministratieveHandelingVerwerkerRepository. Deze klasse haalt Administratieve Handelingen op uit de data-laag.
 */
@Repository
public class AdministratieveHandelingVerwerkerRepositoryImpl implements AdministratieveHandelingVerwerkerRepository {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    /*
     * (non-Javadoc)
     * @see nl.bzk.brp.levering.mutatieverwerker.repository.
     * AdministratieveHandelingVerwerkerRepository#haalAdministratieveHandelingPersoonIds(java.lang.Long)
     */
    @Override
    public List<Integer> haalAdministratieveHandelingPersoonIds(final Long administratieveHandelingId) {
        /*  TODO: als functionaliteit klaar is, deze query gebruiken
        TypedQuery<Integer> tQuery =
                em.createQuery("SELECT ahbpm.persoon.id FROM AdministratieveHandelingBijgehoudenPersoonModel ahbpm"
                        + " WHERE ahbpm.administratieveHandeling.id = :id",
                        Integer.class);
        tQuery.setParameter("id", administratieveHandelingId);
          */

        TypedQuery<Integer> tQuery =
                em.createQuery("SELECT pers.id FROM PersoonModel pers, ActieModel act"
                        + " WHERE act.tijdstipRegistratie = pers.afgeleidAdministratief.tijdstipLaatsteWijziging"
                        + " AND act.administratieveHandeling.id = :id",
                        Integer.class);
        tQuery.setParameter("id", administratieveHandelingId);

        return tQuery.getResultList();
    }

    /*
     * (non-Javadoc)
     * @see nl.bzk.brp.levering.mutatieverwerker.repository.
     * AdministratieveHandelingVerwerkerRepository#haalAdministratieveHandelingPersoonBsns(java.lang.Long)
     */
    @Override
    public List<Integer> haalAdministratieveHandelingPersoonBsns(final Long administratieveHandelingId) {
        //TODO: als functionaliteit klaar is, niet meer via acties ophalen

        TypedQuery<Integer> tQuery =
                em.createQuery(
                        "SELECT pers.identificatienummers.burgerservicenummer.waarde "
                            + " FROM PersoonModel pers, ActieModel act"
                            + " WHERE act.tijdstipRegistratie = pers.afgeleidAdministratief.tijdstipLaatsteWijziging"
                            + " AND act.administratieveHandeling.id = :id",
                        Integer.class);
        tQuery.setParameter("id", administratieveHandelingId);

        return tQuery.getResultList();
    }

}
