/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap;

import javax.inject.Named;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * Minimumleeftijd NL partner bij voltrekking H/P in Nederland.
 *
 *
 * @brp.bedrijfsregel BRBY0401
 */
@Named("BRBY0401")
public class BRBY0401 extends AbstractMinimaleLeeftijdsRegelVerbintenis {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0401;
    }

    @Override
    protected Integer getMinimaleLeeftijd() {
        return DatumEvtDeelsOnbekendAttribuut.ACHTTIEN_JAAR;
    }

    @Override
    protected boolean overtreedtExtraVoorwaarden(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
            final PersoonView persoon)
    {
        return LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT.equals(nieuweSituatie.getStandaard().getLandGebiedAanvang()
                .getWaarde().getCode().getWaarde())
            && persoon.heeftNederlandseNationaliteit();
    }
}
