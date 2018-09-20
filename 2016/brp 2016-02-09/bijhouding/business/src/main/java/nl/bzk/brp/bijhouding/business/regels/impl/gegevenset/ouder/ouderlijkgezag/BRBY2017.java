/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Kind;
import nl.bzk.brp.model.logisch.kern.Ouder;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Op DatumAanvangGeldigheid van de groep OuderlijkGezag, mag het Kind
 * - hoogstens één andere Ouder met geldig Ouderschap én OuderlijkGezag hebben als GezagDerde ontbreekt,
 * - geen andere Ouder met geldig Ouderschap én OuderlijkGezag hebben als GezagDerde bestaat.
 *
 * @brp.bedrijfsregel BRBY2017
 */
@Named("BRBY2017")
public class BRBY2017 implements NaActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    private static final int MAX_AANTAL_PERSONEN_MET_GEZAG = 2;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY2017;
    }

    @Override
    public final List<BerichtEntiteit> voerRegelUit(final PersoonView persoon, final PersoonBericht persoonBericht) {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        int aantalPersonenMetGezag = 0;

        // Bepalen of er gezagDerde bestaat
        final PersoonIndicatie derdeHeeftGezag = haalIndicatieDerdeHeeftGezagOp(persoon.getIndicaties());
        if (derdeHeeftGezag != null) {
            aantalPersonenMetGezag++;
        }

        // haal alle ouders uit het bericht (betrokkenheden met de juiste relatie en rol)
        // controleer of deze ouders ook ouderschap EN ouderlijk gezag hebben
        final Kind kindBetr = RelatieUtils.haalKindBetrokkenheidUitPersoon(persoon);
        final List<Ouder> ouders = RelatieUtils.haalOuderBetrokkenhedenUitRelatie(kindBetr.getRelatie());
        for (final Ouder ouder : ouders) {
            if (ouder.getOuderschap() != null
                    && ouder.getOuderlijkGezag() != null
                && ouder.getOuderlijkGezag().getIndicatieOuderHeeftGezag().equals(JaNeeAttribuut.JA))
            {
                aantalPersonenMetGezag++;
            }
        }

        // Hoogstens twee personen kunnen gezag hebben over kind
        if (aantalPersonenMetGezag > MAX_AANTAL_PERSONEN_MET_GEZAG) {
            objectenDieDeRegelOvertreden.add(persoonBericht);
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Haalt de indicatie derde heeft gezag uit de indicatie lijst.
     *
     * @param indicaties de indicatie lijst.
     * @return de indicatie derde heeft gezag of null als die er niet is.
     */
    private PersoonIndicatie haalIndicatieDerdeHeeftGezagOp(final Collection<? extends PersoonIndicatie> indicaties) {
        for (final PersoonIndicatie indicatie : indicaties) {
            if (SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG == indicatie.getSoort().getWaarde()) {
                return indicatie;
            }
        }
        return null;
    }
}
