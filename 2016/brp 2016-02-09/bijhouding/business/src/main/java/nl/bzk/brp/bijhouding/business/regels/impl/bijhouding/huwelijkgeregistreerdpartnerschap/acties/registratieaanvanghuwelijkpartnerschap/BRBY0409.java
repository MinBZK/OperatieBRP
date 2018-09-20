/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0001;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.HuwelijkGeregistreerdPartnerschapView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Geen verwantschap tussen partners bij voltrekking H/P in Nederland.
 *
 * @brp.bedrijfsregel BRBY0409
 */
@Named("BRBY0409")
public class BRBY0409 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{

    @Inject
    private BRBY0001 brby0001;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0409;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
        final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie, final Actie actie,
        final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final List<Persoon> personen = RelatieUtils.haalPartnersUitRelatie(nieuweSituatie);

        PersoonView persoon1;
        PersoonView persoon2;

        final PersoonBericht persoonBericht1 = (PersoonBericht) personen.get(0);
        final PersoonBericht persoonBericht2 = (PersoonBericht) personen.get(1);

        if (StringUtils.isNotBlank(persoonBericht1.getIdentificerendeSleutel())
            && StringUtils.isNotBlank(persoonBericht2.getIdentificerendeSleutel()))
        {
            persoon1 = bestaandeBetrokkenen.get(persoonBericht1.getIdentificerendeSleutel());
            persoon2 = bestaandeBetrokkenen.get(persoonBericht2.getIdentificerendeSleutel());

            if (zijnBeideIngeschrevenen(persoon1, persoon2) && isLandAanvangNederland(nieuweSituatie)
                && brby0001.isErVerwantschap(persoon1, persoon2))
            {
                objectenDieDeRegelOvertreden.add(nieuweSituatie);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of beide personen ingeschreven zijn.
     *
     * @param persoon1 de eerste persoon
     * @param persoon2 de tweede persoon
     * @return of beide personen ingeschreven zijn.
     */
    private boolean zijnBeideIngeschrevenen(final PersoonView persoon1, final PersoonView persoon2) {
        return persoon1.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE
                && persoon2.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE;
    }

    /**
     * Controleert of de verbintenis als land aanvang Nederland heeft.
     *
     * @param nieuweSituatie de verbintenis uit het bericht
     * @return of de verbintenis als land aanvang Nederland heeft.
     */
    private boolean isLandAanvangNederland(final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie) {
        return nieuweSituatie.getStandaard().getLandGebiedAanvang() != null
                && nieuweSituatie.getStandaard().getLandGebiedAanvang().getWaarde().getCode()
                .equals(LandGebiedCodeAttribuut.NEDERLAND);
    }

}
