/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import nl.bzk.brp.model.data.kern.Betr;
import nl.bzk.brp.model.data.kern.HisPersadres;
import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persadres;
import nl.bzk.brp.model.data.kern.Persnation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 */
@Service
@Transactional
public class BevragingViaBsnServiceImpl implements BevragingViaBsnService {

    private static Logger logger = LoggerFactory.getLogger(BevragingViaBsnServiceImpl.class);

    @Override
    public Pers findPersonByBsn(final String bsn) {
        TypedQuery<Pers> query = null;
        Pers persoon = null;
        query = Pers.findPersesByBsnEquals(bsn);
        List<Pers> personen = query.getResultList();
        logger.debug("Aantal personen voor BSN " + bsn + " = " + personen.size());
        if (personen.size() > 1) {
            throw new IllegalStateException("Meerdere personen gevonden voor deze BSN, dit wordt niet ondersteund.");
        } else if (personen.size() == 1) {
            persoon = personen.get(0);
            logger.debug("Gevonden persoon:" + persoon.getGeslnaam() + persoon.getVoornamen());
            if (persoon.getSrt().getId().compareTo((short) 1) != 0) {
                logger.warn("Persoon is niet van type 1!");
                persoon = null;
            }
        }
        return persoon;
    }

    @Override
    public List<Persnation> zoekNationaliteitenOpPersoon(final Pers persoon) {
        List<Persnation> persnations;
        if (persoon == null) {
            persnations = null;
        } else {
            TypedQuery<Persnation> query = Persnation.findPersnationsByPers(persoon);
            persnations = query.getResultList();
        }
        return persnations;
    }

    @Override
    public Persadres findAdresByPers(final Pers persoon) {
        Persadres adres = null;
        if (persoon == null) {
            adres = null;
        } else {
            try {
                TypedQuery<Persadres> query = Persadres.findPersadresesByPers(persoon);
                adres = query.getSingleResult();
            } catch (NonUniqueResultException e) {
                logger.warn("Meer dan 1 adres gevonden voor persoon " + persoon);
            }
        }
        return adres;
    }

    @Override
    public List<HisPersadres> findHistorischeAdressenByPers(final Persadres huidigAdres) {
        List<HisPersadres> adressen = null;
        if (huidigAdres == null) {
            adressen = null;
        } else {

            String sql =
                "select hpa from HisPersadres hpa where hpa.persadres.id = :persadresid"
                    + " and hpa.dateindegel is not null and hpa.tsverval is null order by hpa.dateindegel desc";

            TypedQuery<HisPersadres> query = HisPersadres.entityManager().createQuery(sql, HisPersadres.class);
            query.setParameter("persadresid", huidigAdres.getId());
            adressen = query.getResultList();
            logger.debug("Aantal historische adressen:" + adressen.size());
        }
        return adressen;
    }

    @Override
    public List<Betr> findOuders(final Pers persoon) {
        List<Betr> ouders = new ArrayList<Betr>();
        if (persoon != null) {
            List<Betr> betrokkenhedenKind = Betr.findBetrsByBetrokkene(persoon).getResultList();
            for (Betr betrokkenheid : betrokkenhedenKind) {
                if ("K".equals(betrokkenheid.getRol().getCode())) {
                    List<Betr> familiebetrekkingen =
                        Betr.findBetrsByRelatie(betrokkenheid.getRelatie()).getResultList();
                    for (Betr familiebetrekking : familiebetrekkingen) {
                        if ("O".equals(familiebetrekking.getRol().getCode())) {
                            ouders.add(familiebetrekking);
                        }
                    }
                }
            }
        }
        return ouders;
    }

    @Override
    public List<Betr> findKinderen(final Pers persoon) {
        List<Betr> kinderen = new ArrayList<Betr>();
        if (persoon != null) {
            List<Betr> betrokkenheden = Betr.findBetrsByBetrokkene(persoon).getResultList();
            for (Betr betrokkenheid : betrokkenheden) {
                if ("O".equals(betrokkenheid.getRol().getCode())) {
                    List<Betr> familiebetrekkingen =
                        Betr.findBetrsByRelatie(betrokkenheid.getRelatie()).getResultList();
                    for (Betr familiebetrekking : familiebetrekkingen) {
                        if ("K".equals(familiebetrekking.getRol().getCode())) {
                            kinderen.add(familiebetrekking);
                        }
                    }
                }
            }
        }
        return kinderen;
    }

    @Override
    public Betr findPartner(final Pers persoon) {
        Betr partner = null;
        if (persoon != null) {
            List<Betr> betrokkenheden = Betr.findBetrsByBetrokkene(persoon).getResultList();
            for (Betr betrokkenheid : betrokkenheden) {
                if ("P".equals(betrokkenheid.getRol().getCode())) {
                    List<Betr> familiebetrekkingen =
                        Betr.findBetrsByRelatie(betrokkenheid.getRelatie()).getResultList();
                    for (Betr familiebetrekking : familiebetrekkingen) {
                        if ("P".equals(familiebetrekking.getRol().getCode())
                            && !familiebetrekking.getBetrokkene().getBsn().equalsIgnoreCase(persoon.getBsn()))
                        {
                            logger.debug("Partner:" + familiebetrekking.getBetrokkene().getBsn());
                            logger.debug("Partner:" + familiebetrekking.getBetrokkene().getVoornamen());
                            partner = familiebetrekking;
                        }
                    }
                }
            }
        }
        return partner;
    }

}
