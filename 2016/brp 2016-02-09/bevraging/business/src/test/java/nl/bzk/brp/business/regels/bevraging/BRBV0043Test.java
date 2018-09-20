/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.bevraging;

import java.util.List;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRBV0043Test {

    public static final DatumAttribuut VOLLEDIGE_DATUM   = new DatumAttribuut(20140101);
    public static final DatumAttribuut ONVOLLEDIGE_DATUM = new DatumAttribuut(20140101);

    private final BRBV0043 brbv0043 = new BRBV0043();

    @Before
    public final void init() {
        ReflectionTestUtils.setField(ONVOLLEDIGE_DATUM, "waarde", 20140000);
    }

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRBV0043, brbv0043.getRegel());
    }

    @Test
    public final void testGeenParameters() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon = maakBericht(false, null);
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0043.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testGeenPeilmoment() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon = maakBericht(true, null);
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0043.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testVolledigeDatum() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon = maakBericht(true, VOLLEDIGE_DATUM);
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0043.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testOnvolledigeDatum() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon = maakBericht(true, ONVOLLEDIGE_DATUM);
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0043.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getParameters(), overtreders.get(0));
    }

    private GeefDetailsPersoonBericht maakBericht(final boolean parameters, final DatumAttribuut peilmoment) {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        if (parameters) {
            final BerichtParametersGroepBericht parametersGroep = new BerichtParametersGroepBericht();
            if (peilmoment != null) {
                parametersGroep.setPeilmomentMaterieelResultaat(peilmoment);
            }
            bericht.setParameters(parametersGroep);
        }
        return bericht;
    }

}
