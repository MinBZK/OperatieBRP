/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.overlijden.acties.registratieoverlijden;

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
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Er mogen geen rechtsfeiten zijn geregistreerd die na de datum overlijden hebben plaatsgevonden.
 * Opmerking:
 * Eenvoudige doch brute implementatie is: datum overlijden > tijdstip laatste wijziging
 * (groep Afgeleid administratief). Het is dan echter niet zeker of daadwerkelijk een later rechtsfeit is geregistreerd
 * (denk bijvoorbeeld een historische correctie?).
 * <p/>
 * Idealiter zouden gebruikers een lijst van rechtsfeiten willen zien die na overlijden hebben plaatsgevonden
 *
 * @brp.bedrijfsregel BRBY0907
 */
@Named("BRBY0907")
public class BRBY0907 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0907;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
            final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (null != nieuweSituatie.getOverlijden()
            && null != nieuweSituatie.getOverlijden().getDatumOverlijden()
            && null != huidigeSituatie.getAfgeleidAdministratief()
            && null != huidigeSituatie.getAfgeleidAdministratief().getTijdstipLaatsteWijziging())
        {
            final DatumEvtDeelsOnbekendAttribuut datumOverLijden = nieuweSituatie.getOverlijden().getDatumOverlijden();
            final DatumAttribuut laatsteWijzigingDatum =
                new DatumAttribuut(huidigeSituatie.getAfgeleidAdministratief().getTijdstipLaatsteWijziging().getWaarde());
            if (datumOverLijden.voor(laatsteWijzigingDatum)) {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
