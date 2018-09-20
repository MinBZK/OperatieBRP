/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties
        .registratiehuwelijkpartnerschap;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
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
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import org.apache.commons.collections.CollectionUtils;


/**
 * Geslachtsnaamwijziging bij H/P in Nederland alleen voor niet-Nederlanders.
 *
 * Met de Actie waarmee een HuwelijkPartnerschap met LandAanvang gelijk aan "Nederland" wordt geregistreerd of
 * beëindigd, mag daarbij de Geslachtsnaamcomponent van een Ingeschrevene die er als Partner bij is betrokken alleen
 * worden gewijzigd als die Ingeschrevene op DatumAanvangGeldigheid van die Actie niet de Nederlandse
 * Nationaliteit heeft.
 *
 * @brp.bedrijfsregel BRBY0437
 */
@Named("BRBY0437")
public class BRBY0437 implements
        VoorActieRegelMetMomentopname<HuwelijkGeregistreerdPartnerschapView, HuwelijkGeregistreerdPartnerschapBericht>
{
    @Override
    public final Regel getRegel() {
        return Regel.BRBY0437;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final HuwelijkGeregistreerdPartnerschapView huidigeSituatie,
                                              final HuwelijkGeregistreerdPartnerschapBericht nieuweSituatie,
                                              final Actie actie,
                                              final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final HuwelijkGeregistreerdPartnerschap teControlerenSituatie;
        if (huidigeSituatie != null) {
            teControlerenSituatie = huidigeSituatie;
        } else {
            teControlerenSituatie = nieuweSituatie;
        }

        if (teControlerenSituatie.getStandaard().getLandGebiedAanvang() != null
            && LandGebiedCodeAttribuut.NEDERLAND.equals(
                teControlerenSituatie.getStandaard().getLandGebiedAanvang().getWaarde().getCode()))
        {
            for (final Betrokkenheid betrokkenheid : nieuweSituatie.getPartnerBetrokkenheden()) {
                final PersoonBericht persoonBericht = (PersoonBericht) betrokkenheid.getPersoon();
                final PersoonView persoon = bestaandeBetrokkenen.get(persoonBericht.getIdentificerendeSleutel());

                if (persoon != null && persoon.getSoort().getWaarde() == SoortPersoon.INGESCHREVENE
                    && persoon.heeftNederlandseNationaliteit()
                    && CollectionUtils.isNotEmpty(persoonBericht.getGeslachtsnaamcomponenten()))
                {
                    objectenDieDeRegelOvertreden.add(persoonBericht);
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }


}

