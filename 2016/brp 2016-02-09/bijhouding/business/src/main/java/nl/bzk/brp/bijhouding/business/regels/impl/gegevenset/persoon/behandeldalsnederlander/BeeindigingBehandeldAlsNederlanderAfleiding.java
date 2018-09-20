/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractBeeindigingIndicatieAfleiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Deze afleidingsregel beëindigt de behandeld als nederlander indicatie op de overlappende delen
 * van de geregistreerde nationaliteit, volgens het bekende historie patroon.
 */
public class BeeindigingBehandeldAlsNederlanderAfleiding extends AbstractBeeindigingIndicatieAfleiding {

    /**
     * Forwarding constructor.
     *
     * @param persoon het model
     * @param actie de actie
     * @param toegevoegdHisNationaliteitRecord het his record dat is toegevoegd
     */
    public BeeindigingBehandeldAlsNederlanderAfleiding(final PersoonHisVolledig persoon, final ActieModel actie,
            final HisPersoonNationaliteitModel toegevoegdHisNationaliteitRecord)
    {
        super(persoon, actie, toegevoegdHisNationaliteitRecord);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00007a;
    }

    @Override
    protected final SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER;
    }

    @Override
    protected final boolean isNationaliteitVanToepassing(final Nationaliteit nationaliteit) {
        // Alleen van toepassing bij NL nationaliteit.
        return nationaliteit.getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE);
    }

}
