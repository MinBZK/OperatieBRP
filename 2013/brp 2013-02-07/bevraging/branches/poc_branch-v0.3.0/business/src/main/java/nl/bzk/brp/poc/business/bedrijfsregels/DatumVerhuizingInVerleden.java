/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.bedrijfsregels;

import java.util.Date;

import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwing;
import nl.bzk.brp.poc.business.dto.antwoord.BijhoudingWaarschuwingNiveau;
import nl.bzk.brp.poc.business.util.DatumUtility;
import nl.bzk.brp.poc.domein.PocPersoon;

/**
 * Bedrijfsregel die stelt dat de datum van aanvang van een adres in het verleden moet liggen.
 */
public class DatumVerhuizingInVerleden implements BedrijfsRegel<PocPersoon> {

    @Override
    public BijhoudingWaarschuwing voerUit(final PocPersoon nieuweSituatie, final PocPersoon huidigeSituatie) {
        BijhoudingWaarschuwing resultaat = null;

        if (nieuweSituatie.getAdressen().iterator().next().getDatumAanvangAdresHouding() >= DatumUtility
                .zetDatumOmNaarInteger(new Date()))
        {
            resultaat = new BijhoudingWaarschuwing(BijhoudingWaarschuwingNiveau.ZACHTE_FOUT, "bdvh001.01",
                    "Datum aanvang nieuw adrs moet in het verleden liggen.");
        }
        return resultaat;
    }

}
