/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geboorte;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.AdresAfleidingDoorGeboorte;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;

/**
 * Verwerkingsregel van de geboorte groep.
 * Verwerkingsregel VR00011.
 */
public class GeboorteGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /** Ouwkiv benodigd om het adres te kunnen afleiden.*/
    private final PersoonHisVolledig adresgevendeOuder;

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     * @param adresgevendeOuder ouder uit wie kind is voortgekomen voor de adres afleiding
     */
    public GeboorteGroepVerwerker(final PersoonBericht bericht, final PersoonHisVolledigImpl model,
                                  final ActieModel actie, final PersoonHisVolledig adresgevendeOuder)
    {
        super(bericht, model, actie);
        this.adresgevendeOuder = adresgevendeOuder;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00011;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonGeboorteModel model = new HisPersoonGeboorteModel(getModel(), getBericht().getGeboorte(), getActie());
        getModel().getPersoonGeboorteHistorie().voegToe(model);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        voegAfleidingsregelToe(new AdresAfleidingDoorGeboorte(getModel(), adresgevendeOuder, getActie()));
    }

}
