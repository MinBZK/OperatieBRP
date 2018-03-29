/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.brp.service.dalapi.BeheerRepository;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;

/**
 * BeheerRepositoryStub.
 */
public class BeheerRepositoryStub implements BeheerRepository, Stateful {

    private final List<VrijBericht> persistedVrijeBerichten = new ArrayList<>();

    /**
     * Slaat een nieuw {@link VrijBericht} op
     * @param vrijBericht het nieuwe {@link VrijBericht}
     */
    @Override
    public void opslaanNieuwVrijBericht(VrijBericht vrijBericht) {
        persistedVrijeBerichten.add(vrijBericht);
    }

    /**
     * Haalt een {@link SoortVrijBericht} op
     * @param naam van het {@link SoortVrijBericht}
     * @return het soort vrij bericht of null
     */
    @Override
    public SoortVrijBericht haalSoortVrijBerichtOp(String naam) {
        return new SoortVrijBericht("Beheermelding");
    }

    /**
     * Geeft aan of er een vrij bericht gepersisteerd is.
     * @return true indien vrij bericht is gepresisteerd
     */
    public boolean isVrijBerichtGepersisteerd() {
        return !persistedVrijeBerichten.isEmpty();
    }

    /**
     * Geeft de opgeslagen vrije berichten.
     * @return de opgeslagen vrije berichten
     */
    public List<VrijBericht> geefVrijeBerichten() {
        return persistedVrijeBerichten;
    }

    @Override
    public void reset() {
        persistedVrijeBerichten.clear();
    }
}
