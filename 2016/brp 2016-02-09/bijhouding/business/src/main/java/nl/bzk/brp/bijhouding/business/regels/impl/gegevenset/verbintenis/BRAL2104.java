/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;

/**
 * H/P mag alleen door betrokken gemeente worden geregistreerd.
 *
 * @brp.bedrijfsregel BRAL2104
 */
@Named("BRAL2104")
public class BRAL2104 implements VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView,
        HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRAL2104;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Partij bijhoudendePartij = actie.getAdministratieveHandeling().getPartij().getWaarde();

        final List<Partij> gerechtigdePartijen = bepaalGerechtigdePartijen(
                huidigeSituatie, nieuweSituatie, bestaandeBetrokkenen);

        if (!isPartijGerechtigd(bijhoudendePartij, gerechtigdePartijen)) {
            objectenDieDeRegelOvertreden.add(((ActieBericht) actie).getAdministratieveHandeling());
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Bepaalt of de partij gerechtigd is, dat wil zeggen: of hij in de lijst met gerechtigde partijen voorkomt.
     *
     * @param bijhoudendePartij de bijhoudende partij
     * @param gerechtigdePartijen de gerechtigde partijen
     * @return of de partij gerechtigd is
     */
    private boolean isPartijGerechtigd(final Partij bijhoudendePartij, final List<Partij> gerechtigdePartijen) {
        boolean gerechtigdePartijGevonden = false;
        for (final Partij gerechtigdePartij : gerechtigdePartijen) {
            if (gerechtigdePartij.getCode().getWaarde().equals(bijhoudendePartij.getCode().getWaarde())) {
                gerechtigdePartijGevonden = true;
                break;
            }
        }
        return gerechtigdePartijGevonden;
    }

    /**
     * Bepaal de gerechtigde partijen voor de bijhouding op een H/P:
     * - De gemeente aanvang.
     * - De gemeente einde.
     * - De bijhoudingsgemeente van een van de partners.
     *
     * @param huidigeSituatie het H/P in de db
     * @param nieuweSituatie het H/P in het bericht
     * @param bestaandeBetrokkenen de bestaande partners
     * @return de lijst met gerechtigde partijen
     */
    private List<Partij> bepaalGerechtigdePartijen(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
            final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
            final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<Partij> gerechtigdePartijen = new ArrayList<>();
        if (huidigeSituatie != null) {
            if (huidigeSituatie.getStandaard().getGemeenteAanvang() != null) {
                gerechtigdePartijen.add(huidigeSituatie.getStandaard().getGemeenteAanvang().getWaarde().getPartij());
            }
            if (huidigeSituatie.getStandaard().getGemeenteEinde() != null) {
                gerechtigdePartijen.add(huidigeSituatie.getStandaard().getGemeenteEinde().getWaarde().getPartij());
            }
        }
        if (nieuweSituatie != null) {
            if (nieuweSituatie.getStandaard().getGemeenteAanvang() != null) {
                gerechtigdePartijen.add(nieuweSituatie.getStandaard().getGemeenteAanvang().getWaarde().getPartij());
            }
            if (nieuweSituatie.getStandaard().getGemeenteEinde() != null) {
                gerechtigdePartijen.add(nieuweSituatie.getStandaard().getGemeenteEinde().getWaarde().getPartij());
            }
        }
        for (final PersoonView partner : bestaandeBetrokkenen.values()) {
            if (partner.getBijhouding() != null && partner.getBijhouding().getBijhoudingspartij() != null) {
                gerechtigdePartijen.add(partner.getBijhouding().getBijhoudingspartij().getWaarde());
            }
        }
        return gerechtigdePartijen;
    }

}
