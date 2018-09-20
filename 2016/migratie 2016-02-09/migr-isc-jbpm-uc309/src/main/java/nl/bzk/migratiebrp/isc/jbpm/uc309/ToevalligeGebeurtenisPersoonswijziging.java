/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGeslachtType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

/**
 * Maakt VerwerkToevalligeGebeurtenisVerzoekBericht voor akte 1H, 1M en 1S.
 */
public class ToevalligeGebeurtenisPersoonswijziging extends AbstractBerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> {

    @Override
    public final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkInput(final Tb02Bericht input) {
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        bericht.setDoelGemeente(input.getDoelGemeente());

        bericht.setPersoon(maakPersoonType(input, Lo3CategorieEnum.CATEGORIE_51));
        bericht.getPersoon().setIdentificatienummers(maakGroep01(input, Lo3CategorieEnum.CATEGORIE_01));

        bericht.setAkte(maakGroep81(input, Lo3CategorieEnum.CATEGORIE_01));
        bericht.setGeldigheid(maakGroep85(input, Lo3CategorieEnum.CATEGORIE_01));

        final NaamGeslachtType naamGeslachtType = new NaamGeslachtType();
        naamGeslachtType.setNaam(maakGroep02(input, Lo3CategorieEnum.CATEGORIE_01));
        naamGeslachtType.setGeslacht(maakGroep04(input, Lo3CategorieEnum.CATEGORIE_01));

        bericht.setUpdatePersoon(naamGeslachtType);
        return bericht;
    }
}
