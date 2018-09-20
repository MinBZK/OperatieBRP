/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;

/**
 * VR01002a: Afleiding/verwerking van de ouderschap groep. Als een Betrokkenheid met de Rol Ouder wordt
 * geregistreerd vanwege een zekere Actie, dan wordt als volgt een groep Ouderschap afgeleid geregistreerd.
 */
public class OuderschapGroepVerwerker extends AbstractVerwerkingsregel<OuderBericht, OuderHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public OuderschapGroepVerwerker(final OuderBericht bericht, final OuderHisVolledigImpl model, final ActieModel actie) {
        super(bericht, model, actie);
    }


    @Override
    public final Regel getRegel() {
        return Regel.VR01002a;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final JaNeeAttribuut indicatieOuderUitWieKindIsGeboren;
        if (getBericht().getOuderschap() != null) {
            indicatieOuderUitWieKindIsGeboren = getBericht().getOuderschap().getIndicatieOuderUitWieKindIsGeboren();
        } else {
            indicatieOuderUitWieKindIsGeboren = null;
        }
        final HisOuderOuderschapModel hisOuderOuderschap =
            new HisOuderOuderschapModel(getModel(), new JaAttribuut(Ja.J), indicatieOuderUitWieKindIsGeboren, getActie(), getActie());
        getModel().getOuderOuderschapHistorie().voegToe(hisOuderOuderschap);
    }
}
