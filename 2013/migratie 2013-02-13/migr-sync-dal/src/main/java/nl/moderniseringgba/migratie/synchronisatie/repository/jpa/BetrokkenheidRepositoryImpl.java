/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.repository.jpa;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.repository.BetrokkenheidRepository;

import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent BRP-betrokkenheden.
 * 
 */
@Repository
public final class BetrokkenheidRepositoryImpl implements BetrokkenheidRepository {

    @PersistenceContext(name = "entityManagerFactory", unitName = "entityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeBetrokkenheidHistorie(final Betrokkenheid betrokkenheid) {
        checkPrecondities(betrokkenheid);
        final Set<BetrokkenheidOuderHistorie> teVerwijderenOuderHistorie =
                new HashSet<BetrokkenheidOuderHistorie>(betrokkenheid.getBetrokkenheidOuderHistorieSet());
        for (final BetrokkenheidOuderHistorie ouderHistorie : teVerwijderenOuderHistorie) {
            betrokkenheid.removeBetrokkenheidOuderHistorie(ouderHistorie);
            em.remove(ouderHistorie);
        }
        final Set<BetrokkenheidOuderlijkGezagHistorie> teVerwijderenOuderlijkGezogHistorie =
                new HashSet<BetrokkenheidOuderlijkGezagHistorie>(
                        betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet());
        for (final BetrokkenheidOuderlijkGezagHistorie ouderlijkGezagHistorie : teVerwijderenOuderlijkGezogHistorie) {
            betrokkenheid.removeBetrokkenheidOuderlijkGezagHistorie(ouderlijkGezagHistorie);
            em.remove(ouderlijkGezagHistorie);
        }
        betrokkenheid.setIndicatieOuder(null);
        betrokkenheid.setIndicatieOuderHeeftGezag(null);
        betrokkenheid.setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatusVoorBrp(betrokkenheid
                .getBetrokkenheidOuderHistorieSet()));
        betrokkenheid.setOuderlijkGezagStatusHistorie(HistorieStatus.bepaalHistorieStatusVoorBrp(betrokkenheid
                .getBetrokkenheidOuderlijkGezagHistorieSet()));
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
        doel.setIndicatieOuderHeeftGezag(bron.getIndicatieOuderHeeftGezag());
        doel.setOuderlijkGezagStatusHistorie(HistorieStatus.bepaalHistorieStatusVoorBrp(bron
                .getBetrokkenheidOuderlijkGezagHistorieSet()));
        doel.setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatusVoorBrp(bron
                .getBetrokkenheidOuderHistorieSet()));
        overschrijfBetrokkenheidOuderHistorie(doel, bron);
        overschrijfBetrokkenheidOuderlijkGezagHistorie(doel, bron);
    }

    private void overschrijfBetrokkenheidOuderHistorie(final Betrokkenheid doel, final Betrokkenheid bron) {
        final Iterator<BetrokkenheidOuderHistorie> doelOuderHistorieIterator =
                doel.getBetrokkenheidOuderHistorieSet().iterator();
        final Iterator<BetrokkenheidOuderHistorie> bronOuderHistorieIterator =
                bron.getBetrokkenheidOuderHistorieSet().iterator();
        while (doelOuderHistorieIterator.hasNext()) {
            final BetrokkenheidOuderHistorie doelBetrokkenheidOuderHistorie = doelOuderHistorieIterator.next();
            if (bronOuderHistorieIterator.hasNext()) {
                final BetrokkenheidOuderHistorie bronBetrokkenheidOuderHistorie = bronOuderHistorieIterator.next();
                doelBetrokkenheidOuderHistorie.setActieAanpassingGeldigheid(bronBetrokkenheidOuderHistorie
                        .getActieAanpassingGeldigheid());
                doelBetrokkenheidOuderHistorie.setActieInhoud(bronBetrokkenheidOuderHistorie.getActieInhoud());
                doelBetrokkenheidOuderHistorie.setActieVerval(bronBetrokkenheidOuderHistorie.getActieVerval());
                doelBetrokkenheidOuderHistorie.setDatumAanvangGeldigheid(bronBetrokkenheidOuderHistorie
                        .getDatumAanvangGeldigheid());
                doelBetrokkenheidOuderHistorie.setDatumEindeGeldigheid(bronBetrokkenheidOuderHistorie
                        .getDatumEindeGeldigheid());
                doelBetrokkenheidOuderHistorie.setDatumTijdRegistratie(bronBetrokkenheidOuderHistorie
                        .getDatumTijdRegistratie());
                doelBetrokkenheidOuderHistorie
                        .setDatumTijdVerval(bronBetrokkenheidOuderHistorie.getDatumTijdVerval());
                doelBetrokkenheidOuderHistorie.setIndicatieOuder(bronBetrokkenheidOuderHistorie.getIndicatieOuder());
            } else {
                doel.removeBetrokkenheidOuderHistorie(doelBetrokkenheidOuderHistorie);
                em.remove(doelBetrokkenheidOuderHistorie);
            }
        }
        while (bronOuderHistorieIterator.hasNext()) {
            final BetrokkenheidOuderHistorie bronBetrokkenheidOuderHistorie = bronOuderHistorieIterator.next();
            doel.addBetrokkenheidOuderHistorie(bronBetrokkenheidOuderHistorie);
        }
    }

    private void overschrijfBetrokkenheidOuderlijkGezagHistorie(final Betrokkenheid doel, final Betrokkenheid bron) {
        final Iterator<BetrokkenheidOuderlijkGezagHistorie> doelOuderlijkGezagHistorieIterator =
                doel.getBetrokkenheidOuderlijkGezagHistorieSet().iterator();
        final Iterator<BetrokkenheidOuderlijkGezagHistorie> bronOuderlijkGezagHistorieIterator =
                bron.getBetrokkenheidOuderlijkGezagHistorieSet().iterator();
        while (doelOuderlijkGezagHistorieIterator.hasNext()) {
            final BetrokkenheidOuderlijkGezagHistorie doelBetrokkenheidOuderlijkGezagHistorie =
                    doelOuderlijkGezagHistorieIterator.next();
            if (bronOuderlijkGezagHistorieIterator.hasNext()) {
                final BetrokkenheidOuderlijkGezagHistorie bronBetrokkenheidOuderlijkGezagHistorie =
                        bronOuderlijkGezagHistorieIterator.next();
                doelBetrokkenheidOuderlijkGezagHistorie
                        .setActieAanpassingGeldigheid(bronBetrokkenheidOuderlijkGezagHistorie
                                .getActieAanpassingGeldigheid());
                doelBetrokkenheidOuderlijkGezagHistorie.setActieInhoud(bronBetrokkenheidOuderlijkGezagHistorie
                        .getActieInhoud());
                doelBetrokkenheidOuderlijkGezagHistorie.setActieVerval(bronBetrokkenheidOuderlijkGezagHistorie
                        .getActieVerval());
                doelBetrokkenheidOuderlijkGezagHistorie
                        .setDatumAanvangGeldigheid(bronBetrokkenheidOuderlijkGezagHistorie
                                .getDatumAanvangGeldigheid());
                doelBetrokkenheidOuderlijkGezagHistorie
                        .setDatumEindeGeldigheid(bronBetrokkenheidOuderlijkGezagHistorie.getDatumEindeGeldigheid());
                doelBetrokkenheidOuderlijkGezagHistorie
                        .setDatumTijdRegistratie(bronBetrokkenheidOuderlijkGezagHistorie.getDatumTijdRegistratie());
                doelBetrokkenheidOuderlijkGezagHistorie.setDatumTijdVerval(bronBetrokkenheidOuderlijkGezagHistorie
                        .getDatumTijdVerval());
                doelBetrokkenheidOuderlijkGezagHistorie
                        .setIndicatieOuderHeeftGezag(bronBetrokkenheidOuderlijkGezagHistorie
                                .getIndicatieOuderHeeftGezag());
            } else {
                doel.removeBetrokkenheidOuderlijkGezagHistorie(doelBetrokkenheidOuderlijkGezagHistorie);
                em.remove(doelBetrokkenheidOuderlijkGezagHistorie);
            }
        }
        while (bronOuderlijkGezagHistorieIterator.hasNext()) {
            final BetrokkenheidOuderlijkGezagHistorie bronBetrokkenheidOuderlijkGezagHistorie =
                    bronOuderlijkGezagHistorieIterator.next();
            doel.addBetrokkenheidOuderlijkGezagHistorie(bronBetrokkenheidOuderlijkGezagHistorie);
        }
    }
}
