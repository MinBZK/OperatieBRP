/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * BRBY0032: Positieve duur geldigheid.
 * <p/>
 * Datum einde geldigheid van de Actie moet, indien gevuld, liggen na Datum aanvang geldigheid van de Actie.
 *
 * @brp.bedrijfsregel BRBY0032
 */
public class BRBY0032 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0032";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (actie.getDatumAanvangGeldigheid() != null && actie.getDatumEindeGeldigheid() != null
            && !actie.getDatumEindeGeldigheid().na(actie.getDatumAanvangGeldigheid()))
        {
            meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0032,
                (Identificeerbaar) actie, "datumEindeGeldigheid"));
        }

        return meldingen;
    }
}
