/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.preview.service;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nl.bzk.brp.model.data.kern.Pers;
import nl.bzk.brp.model.data.kern.Persnation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 *
 *
 */
@Service
public class BevragingViaBsnServiceImpl implements BevragingViaBsnService {

    private static Logger logger = LoggerFactory.getLogger(BevragingViaBsnServiceImpl.class);

    @Override
    public Pers findPersonByBsn(final String bsn) {
        TypedQuery<Pers> result = null;
        Pers persoon = null;
        result = Pers.findPersesByBsnEquals(bsn);
        List<Pers> personen = result.getResultList();
        logger.debug("Aantal personen voor BSN " + bsn + " = " + personen.size());
        if (personen.size() > 1) {
            throw new IllegalStateException("Meerdere personen gevonden voor deze BSN");
        } else if (personen.size() == 1) {
            persoon = personen.get(0);
            logger.debug("Gevonden persoon:" + persoon.getGeslnaam() + persoon.getVoornamen());
        }
        return persoon;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Persnation> zoekNationaliteitenOpPersoon(final Pers persoon) {
        List<Persnation> persnations;
        if (persoon == null) {
            persnations = null;
        } else {
            Query query = Persnation.entityManager().createQuery("from Persnation pn where pn.pers.id = :persid");
            query.setParameter("persid", persoon.getId());
            persnations = query.getResultList();
        }
        return persnations;
    }

}
