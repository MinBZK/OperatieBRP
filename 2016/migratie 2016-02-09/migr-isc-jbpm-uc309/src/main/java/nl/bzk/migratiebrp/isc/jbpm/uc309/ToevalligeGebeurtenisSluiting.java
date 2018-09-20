/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Maakt VerwerkToevalligeGebeurtenisVerzoekBericht voor akte 3A en 5A.
 */
class ToevalligeGebeurtenisSluiting extends AbstractBerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> {

    @Override
    public final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkInput(final Tb02Bericht input) {

        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        bericht.setDoelGemeente(input.getDoelGemeente());

        bericht.setPersoon(maakPersoonType(input, Lo3CategorieEnum.CATEGORIE_01));

        bericht.setRelatie(new RelatieType());
        bericht.getRelatie().setPersoon(maakPersoonType(input, Lo3CategorieEnum.CATEGORIE_05));
        bericht.setAkte(maakGroep81(input, Lo3CategorieEnum.CATEGORIE_05));
        bericht.setGeldigheid(maakGroep85(input, Lo3CategorieEnum.CATEGORIE_05));

        final RelatieType.Sluiting sluiting = new RelatieType.Sluiting();
        sluiting.setSluiting(maakGroep06(input, Lo3CategorieEnum.CATEGORIE_05));
        sluiting.setSoort(maakGroep15(input, Lo3CategorieEnum.CATEGORIE_05));
        bericht.getRelatie().setSluiting(sluiting);
        return bericht;
    }
}
