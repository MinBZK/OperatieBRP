/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;

/**
 * VR00009: Verwerken Groep Naamgebruik.
 */
public class NaamgebruikGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model   het model object
     * @param actie   de actie
     */
    public NaamgebruikGroepVerwerker(final PersoonBericht bericht,
                                     final PersoonHisVolledigImpl model,
                                     final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final PersoonNaamgebruikGroepBericht aanschrijving = getBericht().getNaamgebruik();
        getModel().getPersoonNaamgebruikHistorie().voegToe(new HisPersoonNaamgebruikModel(getModel(), aanschrijving, getActie()));
    }

    @Override
    public final void verzamelAfleidingsregels() {
        // Let op: We roepen hier de afleiding aan omdat in het bericht het vlaggetje indnaamgebruikafgeleid op "Ja",
        // kan staan, waardoor we dus toch de afleiding VR00009a moeten uitvoeren.
        voegAfleidingsregelToe(new NaamgebruikAfleiding(getModel(), getActie()));
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00009;
    }
}
