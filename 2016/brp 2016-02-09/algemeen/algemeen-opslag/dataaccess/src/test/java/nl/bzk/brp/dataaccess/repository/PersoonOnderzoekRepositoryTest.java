/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import org.junit.Assert;
import org.junit.Test;

public class PersoonOnderzoekRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonOnderzoekRepository persoonOnderzoekRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void opslaanPersoonOnderzoek() {

        // Koppel een persoon aan een onderzoek, in dit geval persoon 1001 met een bestaand onderzoek.
        final PersoonOnderzoekHisVolledigImpl opgeslagenPersoonOnderzoek = persoonOnderzoekRepository.opslaanNieuwPersoonOnderzoek(
            new PersoonOnderzoekHisVolledigImpl(
                em.createQuery("select persoon from PersoonHisVolledigImpl persoon where id = 1001", PersoonHisVolledigImpl.class).getResultList().get(0),
                em.createQuery("select onderzoek from OnderzoekHisVolledigImpl onderzoek", OnderzoekHisVolledigImpl.class).getResultList().get(0)));

                    // Controle op opslag van het persoonOnderzoek
        final List<PersoonOnderzoekHisVolledigImpl> persoonOnderzoekHisVolledigList =
            em.createQuery("select persoonOnderzoek FROM PersoonOnderzoekHisVolledigImpl persoonOnderzoek WHERE persoonOnderzoek.id=:persoonOnderzoekId",
                PersoonOnderzoekHisVolledigImpl.class).setParameter("persoonOnderzoekId", opgeslagenPersoonOnderzoek.getID()).getResultList();

        Assert.assertEquals(1, persoonOnderzoekHisVolledigList.size());
    }

}
