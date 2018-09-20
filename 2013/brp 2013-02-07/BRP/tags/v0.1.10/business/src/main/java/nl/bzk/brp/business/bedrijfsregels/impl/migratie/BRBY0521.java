/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;


/**
 * Aanvang adreshouding niet voor Inschrijving BRP.
 *
 * @brp.bedrijfsregel BRBY0521
 */
public class BRBY0521 implements ActieBedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0521";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (huidigeSituatie != null && huidigeSituatie.getInschrijving() != null
            && huidigeSituatie.getInschrijving().getDatumInschrijving() != null
            && nieuweSituatie != null && nieuweSituatie.getAdressen() != null)
        {
            Datum datumInschrijving = huidigeSituatie.getInschrijving().getDatumInschrijving();
            if (!nieuweSituatie.getAdressen().isEmpty()) {
                for (PersoonAdres adres : nieuweSituatie.getAdressen()) {
                    Datum datumAdresHouding = adres.getGegevens().getDatumAanvangAdreshouding();
                    if (datumAdresHouding != null && datumAdresHouding.voor(datumInschrijving)) {
                        meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521,
                            (Identificeerbaar) adres, "datumAanvangAdreshouding"));
                    }
                }
            }
        }
        return meldingen;
    }
}
