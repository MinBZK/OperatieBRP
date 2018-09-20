/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess.impl.jpa;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.poc.berichtenverwerker.dataaccess.PersoonsNationaliteitDAO;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Hispersnation;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;


/**
 * JPA/Hibernate specifieke implementatie van de {@link PersoonsNationaliteitDAO} interface.
 */
public class PersoonsNationaliteitDAOImpl implements PersoonsNationaliteitDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public final Persnation vindPersoonsNationaliteitOpBasisVanId(final long id) {
        return em.find(Persnation.class, id);
    }

    @Override
    public final void voegToePersoonsNationaliteit(final Persnation persoonsNationaliteit) {
        Hispersnation historischPersoonsNationaliteit = bouwPersoonsNationaliteitMetHistorie(persoonsNationaliteit);
        em.persist(persoonsNationaliteit);
        em.persist(historischPersoonsNationaliteit);
        em.flush();
        // em.refresh(historischPersoonsNationaliteit);
    }

    /**
     * Bouwt een nieuwe {@link Hispersnation} instantie op basis van de opgegeven persoons nationaliteit.
     *
     * @param persoonsNationaliteit persoons nationaliteit die als basis dient voor de creatie van de nieuwe instantie.
     * @return de nieuw gecreeerde historie persoons nationaliteit.
     */
    private Hispersnation bouwPersoonsNationaliteitMetHistorie(final Persnation persoonsNationaliteit) {
        Hispersnation result = new Hispersnation();
        result.setActieByActiebegin(persoonsNationaliteit.getActie());
        result.setDataanvgel(persoonsNationaliteit.getDataanvgel());
        result.setDattijdreg(persoonsNationaliteit.getDattijdreg());
        result.setPersnation(persoonsNationaliteit);
        result.setRdnverknlnation(persoonsNationaliteit.getRdnverknlnation());

        // Gaan er van uit dat volgende velden niet direct bij aanmaken worden ingevuld
        result.setDattijdverval(null);
        result.setActieByActieeinde(null);
        result.setRdnverliesnlnation(null);
        result.setDateindegel(null);

        return result;
    }

    @Override
    public final void verwijderPersoonsNationaliteit(final Actie actie, final Persnation persoonsNationaliteit) {
        TypedQuery<Hispersnation> query =
                em.createQuery("from Hispersnation as hpn where hpn.persnation.id = :pnid "
                        + "and hpn.dataanvgel = :dag and hpn.dattijdverval is null", Hispersnation.class);
        query.setParameter("pnid", persoonsNationaliteit.getId());
        query.setParameter("dag", persoonsNationaliteit.getDataanvgel());

        Hispersnation historischPersoonsNationaliteit = query.getSingleResult();
        historischPersoonsNationaliteit.setDattijdverval(new Date());
        historischPersoonsNationaliteit.setPersnation(null);
        historischPersoonsNationaliteit.setActieByActieeinde(actie);

        em.remove(persoonsNationaliteit);
        em.persist(historischPersoonsNationaliteit);
        em.flush();
    }

}
