/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.dataaccess;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.bevraging.app.support.PersoonsLijst;
import nl.bzk.copy.dataaccess.repository.PersoonRepository;
import nl.bzk.copy.model.attribuuttype.Burgerservicenummer;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonIdentificatienummersGroepModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Service
public class PersoonsLijstJpaServiceImpl implements PersoonsLijstJpaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonsLijstJpaServiceImpl.class);

    @Inject
    private PersoonRepository persoonRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public PersoonsLijst getPersoonsLijstJpa(final Integer bsn) {

        try {
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(
                    new Burgerservicenummer(String.valueOf(bsn)));
            PersoonsLijst pl = new PersoonsLijst(persoon);
            LOGGER.info("Persoonslijst: {}", pl.toString());

            return pl;
        } catch (Exception e) {
            LOGGER.error("BSN '" + bsn + "': " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Transactional
    public PersoonsLijst getPersoonsLijstMetHistorieJpa(final Integer bsn) {
        try {
            PersoonModel persoon = persoonRepository.findByBurgerservicenummer(
                    new Burgerservicenummer(String.valueOf(bsn)));
            persoonRepository.vulaanAdresMetHistorie(persoon, true);

            PersoonsLijst pl = new PersoonsLijst(persoon);
            LOGGER.info("Persoonslijst: {}", pl.toString());

            return pl;
        } catch (NonUniqueResultException e) {
            LOGGER.error("BSN '{}': {}", bsn, e.getMessage());
        }
        return null;
    }

    @Override
    @Transactional
    public PersoonsLijst getPersoonsLijstJoinedJpa(final Integer bsn) {
        try {

            TypedQuery<PersoonModel> tQuery = em.createQuery(
//                    Alles meegefetchd
//                    "SELECT DISTINCT persoon FROM PersoonModel persoon" +
//                            " LEFT JOIN FETCH persoon.betrokkenheden as bet1" +
//                            " LEFT JOIN FETCH bet1.relatie as rel" +
//                            " LEFT JOIN FETCH rel.betrokkenheden as bet2" +
//                            " LEFT JOIN FETCH bet2.betrokkene as start" +
////                            " LEFT JOIN FETCH persoon.aanschrijving.predikaat" +
//                            " LEFT JOIN FETCH persoon.geboorte.landGeboorte" +
//                            " LEFT JOIN FETCH persoon.immigratie.landVanwaarGevestigd" +
//                            " LEFT JOIN FETCH persoon.adressen as adres" +
//                            " LEFT JOIN FETCH adres.gegevens.land" +
//                            " LEFT JOIN FETCH adres.gegevens.redenwijziging" +
//                            " LEFT JOIN FETCH adres.gegevens.gemeente" +
//                            " LEFT JOIN FETCH adres.gegevens.woonplaats" +
//                            " LEFT JOIN FETCH persoon.nationaliteiten" +
//                            " LEFT JOIN FETCH persoon.indicaties" +
//                            " LEFT JOIN FETCH persoon.euVerkiezingen" +
//                            " LEFT JOIN FETCH persoon.geslachtsnaamcomponenten" +
//                            " WHERE start.identificatienummers.burgerservicenummer.waardeAlsInteger = :burgerservicenummer",

//                    Relaties e.d. meegefetchd
                    "SELECT DISTINCT persoon FROM PersoonModel persoon" +
                            " LEFT JOIN FETCH persoon.betrokkenheden as bet1" +
                            " LEFT JOIN FETCH bet1.relatie as rel" +
                            " LEFT JOIN FETCH rel.betrokkenheden as bet2" +
                            " LEFT JOIN FETCH bet2.betrokkene as start" +
                            " WHERE start.identificatienummers.burgerservicenummer.waardeAlsInteger = :burgerservicenummer",

//                    Triviale variant
//                    "SELECT DISTINCT persoon FROM PersoonModel persoon" +
//                    " WHERE persoon.betrokkenheden.relatie.betrokkenheden.betrokkene.burgerservicenummer = :burgerservicenummer",

                    PersoonModel.class);
            tQuery.setParameter("burgerservicenummer", bsn);

            List<PersoonModel> personen = tQuery.getResultList();
            final Burgerservicenummer burgerservicenummer = new Burgerservicenummer(String.valueOf(bsn));
            try {
                for (PersoonModel persoon : personen) {
                    final PersoonIdentificatienummersGroepModel identificatienummers =
                            persoon.getIdentificatienummers();
                    if (identificatienummers != null && burgerservicenummer.equals(
                            identificatienummers.getBurgerservicenummer()))
                    {
                        return new PersoonsLijst(persoon);
                    }
                }

            } catch (NoResultException e) {
                personen = null;
            }

//            personen.get(0).getAdressen().iterator().next().getGegevens().

//            return personen;
        } catch (NonUniqueResultException e) {
            LOGGER.error("BSN '{}': {}", bsn, e.getMessage());
        }
        return null;
    }
}
