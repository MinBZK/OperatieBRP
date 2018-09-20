/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * Indien in de BRP op DatumAanvangGeldigheid van een Adres dat door de Actie RegistratieAdres wordt geregistreerd,
 * reeds een andere Persoon staat geregistreerd met hetzelfde Adres , dan moet de DatumAanvangGeldigheid van beide
 * Adressen gelijk zijn.
 *
 * @brp.bedrijfsregel BRBY0502
 */
@Named("BRBY0502")
public class BRBY0502 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0502;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView huidigeSituatie, final PersoonBericht nieuweSituatie,
        final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        if (nieuweSituatie.getAdressen() != null && !nieuweSituatie.getAdressen().isEmpty()) {
            final PersoonAdresBericht nieuwAdres = nieuweSituatie.getAdressen().get(0);
            if (persoonAdresRepository.isIemandIngeschrevenOpAdres(nieuwAdres)) {
                objectenDieDeRegelOvertreden.add(nieuwAdres);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

}
