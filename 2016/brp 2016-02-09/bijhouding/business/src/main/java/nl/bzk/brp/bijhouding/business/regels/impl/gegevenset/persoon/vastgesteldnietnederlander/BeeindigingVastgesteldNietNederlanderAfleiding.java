/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractBeeindigingIndicatieAfleiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Deze afleidingsregel beëindigt de vastgesteld niet nederlander indicatie op de overlappende delen
 * van de geregistreerde nationaliteit, volgens het bekende historie patroon.
 */
public class BeeindigingVastgesteldNietNederlanderAfleiding extends AbstractBeeindigingIndicatieAfleiding {

    /**
     * Forwarding constructor.
     *
     * @param persoon het model
     * @param actie de actie
     * @param toegevoegdHisNationaliteitRecord het his record dat is toegevoegd
     */
    public BeeindigingVastgesteldNietNederlanderAfleiding(final PersoonHisVolledig persoon, final ActieModel actie,
            final HisPersoonNationaliteitModel toegevoegdHisNationaliteitRecord)
    {
        super(persoon, actie, toegevoegdHisNationaliteitRecord);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00006a;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER;
    }

    @Override
    protected boolean isNationaliteitVanToepassing(final Nationaliteit nationaliteit) {
        // Alleen van toepassing bij NL nationaliteit.
        return nationaliteit.getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);
    }

}
