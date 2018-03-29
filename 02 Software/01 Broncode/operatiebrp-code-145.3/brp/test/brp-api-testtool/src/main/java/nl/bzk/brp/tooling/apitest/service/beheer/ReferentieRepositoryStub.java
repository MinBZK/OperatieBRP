/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.beheer.service.dal.ReferentieRepository;
import org.springframework.stereotype.Component;

/**
 * Stub voor {@link ReferentieRepository}.
 */
@Component
final class ReferentieRepositoryStub implements ReferentieRepository {

    @Inject
    private BeheerAutorisatieStub beheerAutorisatieStub;

    @Override
    public <T> T getReferentie(Class<T> clazz, Object key) {
        T referentie = null;
        if (clazz == Dienst.class) {
            referentie = clazz.cast(beheerAutorisatieStub.findDienstById((Integer) key));
        } else if (clazz == ToegangLeveringsAutorisatie.class) {
            referentie = clazz.cast(beheerAutorisatieStub.getToegangLeveringsautorisatieById((Integer) key));
        }
        return referentie;
    }
}
