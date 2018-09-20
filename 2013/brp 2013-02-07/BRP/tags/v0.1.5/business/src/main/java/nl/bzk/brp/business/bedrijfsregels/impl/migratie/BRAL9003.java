/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.historie.PersoonOpschortingHistorieRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;


/**
 * Bedrijfsregel voor BRAL9003 - Als er een actuele reden opschorting is bij de persoon dan mogen over de periode na
 * opschorting geen gegevens worden bijgehouden over de persoon zelf, c.q. gegevens die direct aan het objecttype
 * Persoon hangen.
 *
 * @brp.bedrijfsregel BRAL9003
 *
 */
public class BRAL9003 implements BedrijfsRegel<PersistentPersoon, Persoon> {

    @Inject
    private PersoonOpschortingHistorieRepository persoonOpschortingHistorieRepository;

    @Override
    public String getCode() {
        return "BRAL9003";
    }

    @Override
    public Melding executeer(final PersistentPersoon huidigeSituatie, final Persoon nieuweSituatie,
            final Integer datumAanvangGeldigheid)
    {
        Melding melding = null;

        if (huidigeSituatie != null && huidigeSituatie.getRedenOpschortingBijhouding() != null) {
            Integer datumOpschorting =
                persoonOpschortingHistorieRepository.haalOpActueleDatumOpschorting(huidigeSituatie.getId());

            if (datumOpschorting != null && datumAanvangGeldigheid >= datumOpschorting) {
                melding = new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRAL9003);
            }
        }

        return melding;
    }
}
