/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;


/**
 * Als land aanvang huwelijk (of geregistreerd partnerschap) Nederland is dan moet datum aanvang een
 * geldige kalenderdatum zijn.
 */
public class BRAL2102 implements ActieBedrijfsRegel<HuwelijkGeregistreerdPartnerschap> {

    @Override
    public String getCode() {
        return "BRAL2102";
    }

    @Override
    public List<Melding> executeer(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschap huwelijk, final Actie actie)
    {
        final List<Melding> meldingen = new ArrayList<Melding>();

        if (huwelijk.getStandaard() != null
            && huwelijk.getStandaard().getDatumAanvang() != null
            && huwelijk.isPartnerschapVoltrokkenInNederland()
            && !DatumUtil.isVolledigDatum(huwelijk.getStandaard().getDatumAanvang()))
        {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRAL2102,
                (Identificeerbaar) huwelijk, "datumAanvang"));
        }

        return meldingen;
    }
}
