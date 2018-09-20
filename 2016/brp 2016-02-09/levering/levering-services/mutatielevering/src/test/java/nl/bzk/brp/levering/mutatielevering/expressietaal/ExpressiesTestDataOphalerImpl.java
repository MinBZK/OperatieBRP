/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.expressietaal;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.springframework.stereotype.Repository;

@Repository
public class ExpressiesTestDataOphalerImpl implements ExpressiesTestDataOphaler {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager entityManager;

    public List<String> getAlleExpressiesUitDatabase() {
        final List<String> resultaat = new ArrayList<>();
        final List<ExpressietekstAttribuut> expressiesInDatabase =
            entityManager.createQuery("SELECT expressie FROM Element WHERE expressie != null and autorisatie NOT IN (nl.bzk.brp.model.algemeen"
                + ".stamgegeven.kern.SoortElementAutorisatie.NIET_VERSTREKKEN, nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie"
                + ".BIJHOUDINGSGEGEVENS)", ExpressietekstAttribuut.class).getResultList();

        for (final ExpressietekstAttribuut expressie : expressiesInDatabase) {
            resultaat.add(expressie.getWaarde());
        }
        return resultaat;
    }

    public List<PersoonHisVolledigImpl> getAllePersonenUitDatabase() {
        final List<PersoonHisVolledigImpl> personenInDatabase = new ArrayList<>();
        personenInDatabase.addAll(entityManager.createQuery("from PersoonHisVolledigImpl p", PersoonHisVolledigImpl.class).getResultList());

        return personenInDatabase;
    }

}
