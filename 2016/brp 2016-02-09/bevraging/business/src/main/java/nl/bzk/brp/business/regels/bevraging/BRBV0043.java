/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.bevraging;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.BerichtBedrijfsregel;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;

/**
 * Implementatie van bedrijfsregel BRBV0043
 * <p/>
 * Peilmoment mag geen onbekende datum zijn.
 * <p/>
 *
 * @brp.bedrijfsregel BRBV0043
 */
@Named("BRBV0043")
public class BRBV0043 implements BerichtBedrijfsregel<BerichtRegelContext> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBV0043;
    }

    @Override
    public List<BerichtIdentificeerbaar> valideer(final BerichtRegelContext regelContext) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();

        final BerichtParametersGroepBericht parameters = regelContext.getBericht().getParameters();
        if (parameters != null) {
            final DatumAttribuut peildatumMaterieel = parameters.getPeilmomentMaterieelResultaat();
            // De parameters zijn in overtreding als het peilmoment niet volledig is.
            // Peilmoment formeel wordt niet gecheckt, omdat dit al nooit tot een Java Date zou kunnen komen.
            if (peildatumMaterieel != null && !peildatumMaterieel.isVolledigDatumWaarde()) {
                objectenDieDeRegelOvertreden.add(parameters);
            }
        }

        return objectenDieDeRegelOvertreden;
    }

    @Override
    public Class<BerichtRegelContext> getContextType() {
        return BerichtRegelContext.class;
    }
}
