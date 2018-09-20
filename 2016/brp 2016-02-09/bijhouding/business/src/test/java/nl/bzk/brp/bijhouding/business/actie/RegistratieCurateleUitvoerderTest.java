/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieCurateleBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;

public class RegistratieCurateleUitvoerderTest
    extends AbstractRegistratieIndicatieUitvoerderTest<ActieRegistratieCurateleBericht>
{

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_ONDER_CURATELE;
    }

    @Override
    protected AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder() {
        return new RegistratieCurateleUitvoerder();
    }

    @Override
    protected ActieRegistratieCurateleBericht maakNieuwActieBericht() {
        return new ActieRegistratieCurateleBericht();
    }

    @Override
    protected void voegNieuweIndicatieHisVolledigToe() {
        getPersoon().setIndicatieOnderCuratele(new PersoonIndicatieOnderCurateleHisVolledigImpl(getPersoon()));
    }

    @Override
    protected void verwijderIndicatie() {
        getPersoon().setIndicatieOnderCuratele(null);
    }

}
