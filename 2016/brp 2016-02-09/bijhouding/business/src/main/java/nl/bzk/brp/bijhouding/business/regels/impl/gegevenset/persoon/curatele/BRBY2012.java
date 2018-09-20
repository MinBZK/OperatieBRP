/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Geen rechtsfeiten geregistreerd na onder curatelestelling.
 *
 * Onder Curatele mag niet worden geregistreerd als de Persoon is bijgehouden met een Handeling met een
 * TijdstipRegistratie (24.00u) na DatumAanvangGeldigheid Curatele,
 *
 * Opmerking:
 * Net als in BRBY0907: Geen rechtsfeiten geregistreerd na datum overlijden, is de vergelijking met TijdstipRegistratie
 * is eigenlijk iets te "streng".
 *
 * @brp.bedrijfsregel BRBY2012
 */
@Named("BRBY2012")
public class BRBY2012 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie,
                                              final PersoonBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (null != nieuweSituatie.getIndicaties()) {
            final PersoonIndicatieBericht onderCuratele = haalIndicatieOp(nieuweSituatie.getIndicaties());
            if (onderCuratele != null) {
                final DatumEvtDeelsOnbekendAttribuut datumOnderCuratele = actie.getDatumAanvangGeldigheid();
                final DatumAttribuut laatsteWijzigingDatum =
                    new DatumAttribuut(huidigeSituatie.getAfgeleidAdministratief().getTijdstipLaatsteWijziging().getWaarde());
                if (datumOnderCuratele.voor(laatsteWijzigingDatum)) {
                    objectenDieDeRegelOvertreden.add(nieuweSituatie);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Haalt de indicatie onder curatele uit de indicatie lijst.
     * @param indicaties de indicatie lijst.
     * @return de indicatie onder curatele of null als dat er niet is.
     */
    private PersoonIndicatieBericht haalIndicatieOp(final List<PersoonIndicatieBericht> indicaties) {
        for (final PersoonIndicatieBericht indicatie : indicaties) {
            if (SoortIndicatie.INDICATIE_ONDER_CURATELE == indicatie.getSoort().getWaarde()) {
                return indicatie;
            }
        }
        return null;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2012;
    }
}
