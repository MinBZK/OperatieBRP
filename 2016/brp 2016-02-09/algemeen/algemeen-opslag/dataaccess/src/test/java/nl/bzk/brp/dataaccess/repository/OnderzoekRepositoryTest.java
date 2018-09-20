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
import org.junit.Assert;
import org.junit.Test;

/** Unit test klasse ten behoeve van de {@link OnderzoekRepository} klasse. */
public class OnderzoekRepositoryTest extends AbstractRepositoryTestCase {

    @Inject
    private OnderzoekRepository                onderzoekRepository;

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void opslaanOnderzoek() {
        final OnderzoekHisVolledigImpl onderzoekHisVolledig = new OnderzoekHisVolledigImpl();

        // Opslag onderzoek
        final OnderzoekHisVolledigImpl opgeslagenOnderzoek = onderzoekRepository.slaNieuwOnderzoekOp(onderzoekHisVolledig);

        // Controle op opslag van het onderzoek
        final List<OnderzoekHisVolledigImpl> onderzoekHisVolledigList =
            em.createQuery("SELECT onderzoek FROM OnderzoekHisVolledigImpl onderzoek WHERE onderzoek.id=:onderzoekId", OnderzoekHisVolledigImpl.class)
                .setParameter("onderzoekId", opgeslagenOnderzoek.getID()).getResultList();

        Assert.assertEquals(1, onderzoekHisVolledigList.size());
    }

}
