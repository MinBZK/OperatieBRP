/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc309;

import java.math.BigInteger;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Tb02Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.OverlijdenType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;

/**
 * Maakt VerwerkToevalligeGebeurtenisVerzoekBericht voor akte 2A en 2G.
 */
public class ToevalligeGebeurtenisOverlijden extends AbstractBerichtVerwerker<VerwerkToevalligeGebeurtenisVerzoekBericht, Tb02Bericht> {

    @Override
    public final VerwerkToevalligeGebeurtenisVerzoekBericht verwerkInput(final Tb02Bericht input) {
        final VerwerkToevalligeGebeurtenisVerzoekBericht bericht = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        bericht.setDoelGemeente(input.getDoelGemeente());

        bericht.setPersoon(maakPersoonType(input, Lo3CategorieEnum.CATEGORIE_01));

        final OverlijdenType overlijden = new OverlijdenType();
        final OverlijdenGroepType overlijdenGroepType = new OverlijdenGroepType();
        overlijdenGroepType.setDatum(new BigInteger(input.getWaarde(Lo3CategorieEnum.CATEGORIE_06, Lo3ElementEnum.ELEMENT_0810)));
        overlijdenGroepType.setPlaats(input.getWaarde(Lo3CategorieEnum.CATEGORIE_06, Lo3ElementEnum.ELEMENT_0820));
        overlijdenGroepType.setPlaats(input.getWaarde(Lo3CategorieEnum.CATEGORIE_06, Lo3ElementEnum.ELEMENT_0830));
        overlijden.setOverlijden(overlijdenGroepType);
        bericht.setOverlijden(overlijden);

        bericht.setAkte(maakGroep81(input, Lo3CategorieEnum.CATEGORIE_06));
        bericht.setGeldigheid(maakGroep85(input, Lo3CategorieEnum.CATEGORIE_06));

        return bericht;
    }
}
