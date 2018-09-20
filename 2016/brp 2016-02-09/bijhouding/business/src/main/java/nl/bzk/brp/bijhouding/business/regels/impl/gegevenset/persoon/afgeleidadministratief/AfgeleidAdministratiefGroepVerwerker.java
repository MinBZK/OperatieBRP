/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.afgeleidadministratief;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;

/**
 * VR00027a: Afleiden Groep Afgeleid administratief.
 */
public class AfgeleidAdministratiefGroepVerwerker extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Boolean die aangeeft of de groep afgeleid wordt voor een hoofdpersoon (BRBY0009).
     * Voor bepaling van SorteerVolgorde.
     */
    private final boolean isHoofdPersoonInBijhouding;

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     * @param isHoofdPersoonInBijhouding is de persoon een hoofdpersoon in de bijhouding
     */
    public AfgeleidAdministratiefGroepVerwerker(final PersoonHisVolledig model,
                                                final ActieModel actie,
                                                final boolean isHoofdPersoonInBijhouding)
    {
        super(model, actie);
        this.isHoofdPersoonInBijhouding = isHoofdPersoonInBijhouding;
    }

    @Override
    public final AfleidingResultaat leidAf() {
        final byte sorteervolgorde;
        if (isHoofdPersoonInBijhouding) {
            sorteervolgorde = (byte) 1;
        } else {
            sorteervolgorde = (byte) 2;
        }
        final HisPersoonAfgeleidAdministratiefModel hisPersoonAfgeleidAdministratiefModel =
                new HisPersoonAfgeleidAdministratiefModel(getModel(),
                                                          getActie().getAdministratieveHandeling(),
                                                          getActie().getTijdstipRegistratie(),
                                                          new SorteervolgordeAttribuut(sorteervolgorde),
                                                          JaNeeAttribuut.NEE, null, getActie());
        getModel().getPersoonAfgeleidAdministratiefHistorie().voegToe(hisPersoonAfgeleidAdministratiefModel);
        return GEEN_VERDERE_AFLEIDINGEN;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00027a;
    }
}
