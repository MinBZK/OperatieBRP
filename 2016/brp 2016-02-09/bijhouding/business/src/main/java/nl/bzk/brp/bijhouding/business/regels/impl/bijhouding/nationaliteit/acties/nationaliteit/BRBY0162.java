/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Naturalisatiebesluit moet tijdig verwerkt worden.
 *
 * @brp.bedrijfsregel BRBY0162
 */
@Named("BRBY0162")
public class BRBY0162 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0162;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (NationaliteitRegelUtil.verkrijgtNederlandseNationaliteit(nieuweSituatie)
                && NationaliteitRegelUtil.heeftKoninklijkbesluitBron(actie))
        {
            final DatumAttribuut eenJaarNaAanvangGeldigheid = new DatumAttribuut(actie.getDatumAanvangGeldigheid());
            eenJaarNaAanvangGeldigheid.voegJaarToe(1);
            // Alleen de datum van de registratie is van toepassing, niet het tijdstip.
            final DatumAttribuut datumRegistratie = actie.getTijdstipRegistratie().naarDatum();
            // Als de aanvang geldigheid langer dan een jaar geleden is, is de aanvraag verlopen.
            if (!datumRegistratie.voorOfOp(eenJaarNaAanvangGeldigheid)) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
