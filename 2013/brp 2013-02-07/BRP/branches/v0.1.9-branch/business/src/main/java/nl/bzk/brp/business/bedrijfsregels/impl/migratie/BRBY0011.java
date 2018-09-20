/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.DatumUtil;


/**
 * Datum aanvang geldigheid mag niet groter zijn dan actuele datum.
 *
 * @brp.bedrijfsregel BRBY0011
 *
 */
public class BRBY0011 implements BedrijfsRegel<Persoon> {

    @Override
    public String getCode() {
        return "BRBY0011";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (datumAanvangGeldigheid != null && DatumUtil.vandaag().voor(datumAanvangGeldigheid)) {
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.BRBY0011,
                    (PersoonBericht) nieuweSituatie, "datumAanvangGeldigheid"));
        }

        return meldingen;
    }
}
