/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming.BRBY0105M;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.util.RelatieUtils;


/**
 * Implementatie van bedrijfsregel BRBY0189. Erkend kind moet Nederlander worden vanwege Oma/opa artikel.
 *
 * @brp.bedrijfsregel BRBY0189
 */
@Named("BRBY0189")
public class BRBY0189 implements
        NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView, FamilierechtelijkeBetrekkingBericht>
{

    @Inject
    private BRBY0105M brby0105M;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0189;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking,
            final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        final Persoon kind = RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekking);

        // Als BRBY0105M 'afgaat' en het kind is jonger dan 7, dan is deze regel 'afgegaan'.
        if (brby0105M.voerRegelUit(familierechtelijkeBetrekking, familierechtelijkeBetrekkingBericht).size() > 0
            && isKindJongerDan7(kind))
        {
            objectenDieDeRegelOvertreden.add((BerichtEntiteit) RelatieUtils
                    .haalKindUitRelatie(familierechtelijkeBetrekkingBericht));
        }

        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of kind jonger dan 7 is.
     *
     * @param kind kind
     * @return true als kind jonger is dan zeven jaar.
     */
    private boolean isKindJongerDan7(final Persoon kind) {
        final DatumEvtDeelsOnbekendAttribuut datumGeboorteKind = kind.getGeboorte().getDatumGeboorte();
        final DatumAttribuut datumZevenJaarKind = new DatumAttribuut(datumGeboorteKind);
        datumZevenJaarKind.voegJaarToe(DatumEvtDeelsOnbekendAttribuut.ZEVEN_JAAR);
        return DatumAttribuut.vandaag().voor(datumZevenJaarKind);
    }
}
