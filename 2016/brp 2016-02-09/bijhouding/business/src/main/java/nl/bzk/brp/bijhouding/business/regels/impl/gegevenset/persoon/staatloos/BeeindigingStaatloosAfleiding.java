/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractBeeindigingIndicatieAfleiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Deze afleidingsregel beëindigt de staatloos indicatie op de overlappende delen
 * van de geregistreerde nationaliteit, volgens het bekende historie patroon.
 */
public class BeeindigingStaatloosAfleiding extends AbstractBeeindigingIndicatieAfleiding {

    /**
     * Forwarding constructor.
     *
     * @param persoon het model
     * @param actie de actie
     * @param toegevoegdHisNationaliteitRecord het his record dat is toegevoegd
     */
    public BeeindigingStaatloosAfleiding(final PersoonHisVolledig persoon, final ActieModel actie,
            final HisPersoonNationaliteitModel toegevoegdHisNationaliteitRecord)
    {
        super(persoon, actie, toegevoegdHisNationaliteitRecord);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00005a;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_STAATLOOS;
    }

    @Override
    protected boolean isNationaliteitVanToepassing(final Nationaliteit nationaliteit) {
        // De specifieke nationaliteit maakt niet uit: staatloos wordt altijd beëindigd.
        return true;
    }

}
