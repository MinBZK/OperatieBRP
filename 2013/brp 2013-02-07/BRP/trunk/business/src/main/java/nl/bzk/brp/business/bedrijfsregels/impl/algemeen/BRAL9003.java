/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonOpschortingRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;


/**
 * Bedrijfsregel voor BRAL9003 - Als er een actuele reden opschorting is bij de persoon dan mogen over de periode na
 * opschorting geen gegevens worden bijgehouden over de persoon zelf, c.q. gegevens die direct aan het objecttype
 * Persoon hangen.
 *
 * @brp.bedrijfsregel BRAL9003
 */
public class BRAL9003 implements ActieBedrijfsRegel<Persoon> {

    @Inject
    private HistoriePersoonOpschortingRepository historiePersoonOpschortingRepository;

    @Override
    public String getCode() {
        return "BRAL9003";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
        final Actie actie)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (huidigeSituatie != null && huidigeSituatie.getOpschorting() != null
            && huidigeSituatie.getOpschorting().getRedenOpschortingBijhouding() != null)
        {
            Datum datumOpschorting =
                historiePersoonOpschortingRepository.haalOpActueleDatumOpschorting((PersoonModel) huidigeSituatie);

            if (datumOpschorting != null && actie.getDatumAanvangGeldigheid() != null
                && !datumOpschorting.na(actie.getDatumAanvangGeldigheid()))
            {
                // BRAL9003 is een generieke bedrijfsregel, van toepassing op allerlei bijhoudingen.
                // de fout wordt dus gezet op de persoon.
                meldingen.add(new Melding(SoortMelding.DEBLOKKEERBAAR, MeldingCode.BRAL9003,
                    (PersoonBericht) nieuweSituatie, "datumAanvangGeldigheid"));
            }
        }

        return meldingen;
    }
}
