/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BetrokkenheidRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-betrokkenheden.
 * 
 */
@Repository
public final class BetrokkenheidRepositoryImpl implements BetrokkenheidRepository {

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBetrokkenheidHistorie(final Betrokkenheid betrokkenheid) {
        checkPrecondities(betrokkenheid);
        final Set<BetrokkenheidOuderHistorie> teVerwijderenOuderHistorie = new HashSet<>(betrokkenheid.getBetrokkenheidOuderHistorieSet());
        for (final BetrokkenheidOuderHistorie ouderHistorie : teVerwijderenOuderHistorie) {
            betrokkenheid.removeBetrokkenheidOuderHistorie(ouderHistorie);
            em.remove(ouderHistorie);
        }
        final Set<BetrokkenheidOuderlijkGezagHistorie> teVerwijderenOuderlijkGezogHistorie;
        teVerwijderenOuderlijkGezogHistorie = new HashSet<>(betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());
        for (final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagHistorie : teVerwijderenOuderlijkGezogHistorie) {
            betrokkenheid.removeBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagHistorie);
            em.remove(ouderlijkGezagHistorie);
        }
        betrokkenheid.setIndicatieOuder(null);
        betrokkenheid.setIndicatieOuderHeeftGezag(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(final Betrokkenheid betrokkenheid) {
        checkPrecondities(betrokkenheid);
        em.remove(betrokkenheid);
    }

    private void checkPrecondities(final Betrokkenheid betrokkenheid) {
        if (betrokkenheid == null) {
            throw new NullPointerException("betrokkenheid mag niet null zijn");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void overschrijfBetrokkenheid(final Betrokkenheid doel, final Betrokkenheid bron) {
        doel.setIndicatieOuder(bron.getIndicatieOuder());
        doel.setIndicatieOuderUitWieKindIsGeboren(bron.getIndicatieOuderUitWieKindIsGeboren());
        doel.setIndicatieOuderHeeftGezag(bron.getIndicatieOuderHeeftGezag());

        overschrijfBetrokkenheidOuderHistorie(doel, bron);
        overschrijfBetrokkenheidOuderlijkGezagHistorie(doel, bron);
    }

    private void overschrijfBetrokkenheidOuderHistorie(final Betrokkenheid doel, final Betrokkenheid bron) {
        final Iterator<BetrokkenheidOuderHistorie> doelHistorieIterator = doel.getBetrokkenheidOuderHistorieSet().iterator();
        final Iterator<BetrokkenheidOuderHistorie> bronHistorieIterator = bron.getBetrokkenheidOuderHistorieSet().iterator();

        // Voorkom een CuncurrentModificationException door pas na itereren de set te wijzigen
        final List<BetrokkenheidOuderHistorie> teVerwijderen = new ArrayList<>();

        while (doelHistorieIterator.hasNext()) {
            final BetrokkenheidOuderHistorie doelHistorie = doelHistorieIterator.next();
            if (bronHistorieIterator.hasNext()) {
                final BetrokkenheidOuderHistorie bronHistorie = bronHistorieIterator.next();
                doelHistorie.setActieAanpassingGeldigheid(bronHistorie.getActieAanpassingGeldigheid());
                doelHistorie.setActieInhoud(bronHistorie.getActieInhoud());
                doelHistorie.setActieVerval(bronHistorie.getActieVerval());
                doelHistorie.setDatumAanvangGeldigheid(bronHistorie.getDatumAanvangGeldigheid());
                doelHistorie.setDatumEindeGeldigheid(bronHistorie.getDatumEindeGeldigheid());
                doelHistorie.setDatumTijdRegistratie(bronHistorie.getDatumTijdRegistratie());
                doelHistorie.setDatumTijdVerval(bronHistorie.getDatumTijdVerval());
                doelHistorie.setIndicatieOuder(bronHistorie.getIndicatieOuder());
                doelHistorie.setIndicatieOuderUitWieKindIsGeboren(bronHistorie.getIndicatieOuderUitWieKindIsGeboren());
            } else {
                teVerwijderen.add(doelHistorie);
            }
        }

        for (final BetrokkenheidOuderHistorie doelHistorie : teVerwijderen) {
            doel.removeBetrokkenheidOuderHistorie(doelHistorie);
            em.remove(doelHistorie);
        }

        while (bronHistorieIterator.hasNext()) {
            final BetrokkenheidOuderHistorie bronBetrokkenheidOuderHistorie = bronHistorieIterator.next();
            doel.addBetrokkenheidOuderHistorie(bronBetrokkenheidOuderHistorie);
        }
    }

    private void overschrijfBetrokkenheidOuderlijkGezagHistorie(final Betrokkenheid doel, final Betrokkenheid bron) {
        final Iterator<BetrokkenheidOuderlijkGezagHistorie> doelHistorieIterator =
                doel.getBetrokkenheidOuderlijkGezagHistorieSet().iterator();
        final Iterator<BetrokkenheidOuderlijkGezagHistorie> bronHistorieIterator =
                bron.getBetrokkenheidOuderlijkGezagHistorieSet().iterator();

        // Voorkom een CuncurrentModificationException door pas na itereren de set te wijzigen
        final List<BetrokkenheidOuderlijkGezagHistorie> teVerwijderen = new ArrayList<>();

        while (doelHistorieIterator.hasNext()) {
            final BetrokkenheidOuderlijkGezagHistorie doelHistorie = doelHistorieIterator.next();
            if (bronHistorieIterator.hasNext()) {
                final BetrokkenheidOuderlijkGezagHistorie bronHistorie = bronHistorieIterator.next();
                doelHistorie.setActieAanpassingGeldigheid(bronHistorie.getActieAanpassingGeldigheid());
                doelHistorie.setActieInhoud(bronHistorie.getActieInhoud());
                doelHistorie.setActieVerval(bronHistorie.getActieVerval());
                doelHistorie.setDatumAanvangGeldigheid(bronHistorie.getDatumAanvangGeldigheid());
                doelHistorie.setDatumEindeGeldigheid(bronHistorie.getDatumEindeGeldigheid());
                doelHistorie.setDatumTijdRegistratie(bronHistorie.getDatumTijdRegistratie());
                doelHistorie.setDatumTijdVerval(bronHistorie.getDatumTijdVerval());
                doelHistorie.setIndicatieOuderHeeftGezag(bronHistorie.getIndicatieOuderHeeftGezag());
            } else {
                teVerwijderen.add(doelHistorie);
            }
        }

        for (final BetrokkenheidOuderlijkGezagHistorie doelHistorie : teVerwijderen) {
            doel.removeBetrokkenheidOuderlijkGezagHistorie(doelHistorie);
            em.remove(doelHistorie);
        }

        while (bronHistorieIterator.hasNext()) {
            final BetrokkenheidOuderlijkGezagHistorie bronBetrokkenheidOuderlijkGezagHistorie = bronHistorieIterator.next();
            doel.addBetrokkenheidOuderlijkGezagHistorie(bronBetrokkenheidOuderlijkGezagHistorie);
        }
    }
}
