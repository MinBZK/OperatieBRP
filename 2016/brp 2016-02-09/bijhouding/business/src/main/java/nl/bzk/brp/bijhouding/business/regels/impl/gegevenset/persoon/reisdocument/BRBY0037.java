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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * BRBY0037: Geldigheidsduur van reisdocument is maximaal is 10 jaar.
 *
 * @brp.bedrijfsregel BRBY0037
 */
@Named("BRBY0037")
public class BRBY0037 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    private static final int MAX_JAREN_GELDIGHEID = 10;

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (nieuweSituatie.getReisdocumenten() != null && !nieuweSituatie.getReisdocumenten().isEmpty()) {
            for (PersoonReisdocumentBericht persoonReisdocument : nieuweSituatie.getReisdocumenten()) {
                if (isPersoonReisdocumentTeLangGeldig(persoonReisdocument)) {
                    objectenDieDeRegelOvertreden.add(persoonReisdocument);
                }
            }
        }
        return objectenDieDeRegelOvertreden;
    }

    private boolean isPersoonReisdocumentTeLangGeldig(final PersoonReisdocumentBericht persoonReisdocument) {
        if (persoonReisdocument.getStandaard() != null) {
            final DatumEvtDeelsOnbekendAttribuut datumUitgifte = persoonReisdocument.getStandaard().getDatumUitgifte();
            final DatumEvtDeelsOnbekendAttribuut datumVoorzieneEindeGeldigheid = persoonReisdocument.getStandaard().getDatumEindeDocument();

            // Kunnen mogelijk null zijn ivm onttrekking reisdocument.
            return datumUitgifte != null
                && datumVoorzieneEindeGeldigheid != null
                && datumVoorzieneEindeGeldigheid.na(DatumAttribuut.verhoogMetJaren(new DatumAttribuut(datumUitgifte), MAX_JAREN_GELDIGHEID));
        }

        return false;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0037;
    }
}
