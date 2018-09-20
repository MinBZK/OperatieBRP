/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocument;

/**
 * BRBY0042: Hoogstens één reisdocument van een zekere soort.
 *
 * Op DatumUitgifte van een Reisdocument mag de Persoon niet reeds een ander exemplaar zonder RedenOnttrekking van
 * dezelfde Soort hebben.
 *
 * @brp.bedrijfsregel BRBY0042
 */
@Named("BRBY0042")
public class BRBY0042 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        for (PersoonReisdocumentBericht persoonReisdocument : nieuweSituatie.getReisdocumenten()) {
            if (heeftNietVervallenReisdocumentVanSoort(persoonReisdocument.getSoort().getWaarde(), huidigeSituatie)) {
                objectenDieDeRegelOvertreden.add(persoonReisdocument);
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of persoon al een reisdocument heeft van een bepaalde soort dat niet vervallen is.
     *
     * @param teRegistrerenSoort de soort uit bericht dat geregistreerd wordt
     * @param huidigeSituatie de huidige persoon
     * @return true indien persoon al een reisdocument heeft van de te registreren soort, anders false
     */
    private boolean heeftNietVervallenReisdocumentVanSoort(final SoortNederlandsReisdocument teRegistrerenSoort,
                                                           final Persoon huidigeSituatie)
    {
        boolean resultaat = false;
        for (PersoonReisdocument persoonReisdocument : huidigeSituatie.getReisdocumenten()) {
            if (persoonReisdocument.getStandaard().getAanduidingInhoudingVermissing() == null
                    && persoonReisdocument.getSoort().getWaarde().getCode().equals(teRegistrerenSoort.getCode()))
            {
                resultaat = true;
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0042;
    }
}
