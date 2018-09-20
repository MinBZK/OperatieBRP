/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;


/**
 * Een Persoon met de indicatie Staatloos,
 * mag op DatumAanvangGeldigheid daarvan geen enkele Nationaliteit bezitten.
 *
 * @brp.bedrijfsregel BRAL2013
 */
@Named("BRAL2013")
public class BRAL2013 extends AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2013;
    }

    @Override
    protected SoortIndicatie getIndicatie() {
        return SoortIndicatie.INDICATIE_STAATLOOS;
    }

    @Override
    protected boolean heeftTeCheckenNationaliteit(final NationaliteitcodeAttribuut nationaliteitCode) {
        // Elke nationaliteit is een reden om deze regel af te laten gaan, de indicatie heet immers staatLOOS. :)
        return true;
    }

}
