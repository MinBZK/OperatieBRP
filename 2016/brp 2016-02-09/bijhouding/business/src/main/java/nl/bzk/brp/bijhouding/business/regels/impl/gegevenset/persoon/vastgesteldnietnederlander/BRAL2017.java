/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander;

import javax.inject.Named;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;


/**
 * Een Persoon met de indicatie VastgesteldNietNederlander,
 * mag op DatumAanvangGeldigheid daarvan niet Nederlandse Nationaliteit bezitten.
 *
 * @brp.bedrijfsregel BRAL2017
 */
@Named("BRAL2017")
public class BRAL2017 extends AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL2017;
    }

    @Override
    protected SoortIndicatie getIndicatie() {
        return SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER;
    }

    @Override
    protected boolean heeftTeCheckenNationaliteit(final NationaliteitcodeAttribuut nationaliteitCode) {
        return NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.equals(nationaliteitCode);
    }

}
