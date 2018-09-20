/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.DatumUtil;


/** Datum aanvang huwelijk mag niet in de toekomst liggen. */
public class BRBY0438 implements ActieBedrijfsRegel<Relatie> {

    @Override
    public String getCode() {
        return "BRBY0438";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie huwelijk,
        final Actie actie)
    {
        final List<Melding> meldingen = new ArrayList<Melding>();
        if (huwelijk.getGegevens() != null && huwelijk.getGegevens().getDatumAanvang() != null
            && huwelijk.getGegevens().getDatumAanvang().na(DatumUtil.vandaag()))
        {
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBY0438,
                (Identificeerbaar) huwelijk, "datumAanvang"));
        }
        return meldingen;
    }
}
