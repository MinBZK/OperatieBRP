/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.repository.HisAfnemerindicatieTabelRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public final class HisAfnemerindicatieTabelJpaRepository implements HisAfnemerindicatieTabelRepository {


    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager emLezenSchrijven;


    @Override
    public PersoonAfnemerindicatieHisVolledigImpl maakNieuweAfnemerIndicatie(final Integer persoonId, final PartijAttribuut partijAttribuut,
                                                                             final LeveringsautorisatieAttribuut laAttribuut)
    {
        //haal een proxy op voor de bidirectionele relatie
        final PersoonHisVolledigImpl persoonhisVolledig = emLezenSchrijven.getReference(PersoonHisVolledigImpl.class, persoonId);
        final PersoonAfnemerindicatieHisVolledigImpl nieuweAfnemerIndicatie = new PersoonAfnemerindicatieHisVolledigImpl(persoonhisVolledig, partijAttribuut,
                laAttribuut);
        //sla nieuwe afnemerindicatie op
        emLezenSchrijven.persist(nieuweAfnemerIndicatie);
        return nieuweAfnemerIndicatie;
    }


}
