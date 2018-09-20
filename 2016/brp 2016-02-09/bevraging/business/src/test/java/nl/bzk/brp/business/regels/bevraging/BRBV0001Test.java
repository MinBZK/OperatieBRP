/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.bevraging;

import java.util.List;
import nl.bzk.brp.business.regels.context.BerichtRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardetekstAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import org.junit.Assert;
import org.junit.Test;


public class BRBV0001Test {

    public static final boolean BSN_AANWEZIG                = true;
    public static final boolean BSN_NIET_AANWEZIG           = false;
    public static final boolean ANR_AANWEZIG                = true;
    public static final boolean ANR_NIET_AANWEZIG           = false;
    public static final boolean OBJECTSLEUTEL_AANWEZIG      = true;
    public static final boolean OBJECTSLEUTEL_NIET_AANWEZIG = false;

    private final BRBV0001      brbv0001                    = new BRBV0001();

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRBV0001, brbv0001.getRegel());
    }

    @Test
    public final void testGeenZoekcriteria() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon =
            maakBericht(BSN_NIET_AANWEZIG, ANR_NIET_AANWEZIG, OBJECTSLEUTEL_NIET_AANWEZIG);
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getZoekcriteriaPersoon(), overtreders.get(0));
    }

    @Test
    public final void testBsnAlsZoekcriteria() {
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        maakBericht(BSN_AANWEZIG, ANR_NIET_AANWEZIG, OBJECTSLEUTEL_NIET_AANWEZIG));

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testAnrAlsZoekcriteria() {
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        maakBericht(BSN_NIET_AANWEZIG, ANR_AANWEZIG, OBJECTSLEUTEL_NIET_AANWEZIG));

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testObjectSleutelAlsZoekcriteria() {
        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                                        maakBericht(BSN_NIET_AANWEZIG, ANR_NIET_AANWEZIG, OBJECTSLEUTEL_AANWEZIG));

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public final void testBsnEnAnrAlsZoekcriteria() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon =
                maakBericht(BSN_AANWEZIG, ANR_AANWEZIG, OBJECTSLEUTEL_NIET_AANWEZIG);

        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON, vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getZoekcriteriaPersoon(), overtreders.get(0));
    }

    @Test
    public final void testBsnEnObjectSleutelAlsZoekcriteria() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon =
            maakBericht(BSN_AANWEZIG, ANR_NIET_AANWEZIG, OBJECTSLEUTEL_AANWEZIG);

        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON, vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getZoekcriteriaPersoon(), overtreders.get(0));
    }

    @Test
    public final void testAnrEnObjectSleutelAlsZoekcriteria() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon =
            maakBericht(BSN_AANWEZIG, ANR_AANWEZIG, OBJECTSLEUTEL_NIET_AANWEZIG);

        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON, vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getZoekcriteriaPersoon(), overtreders.get(0));
    }

    @Test
    public final void testBsnEnAnrEnObjectSleutelAlsZoekcriteria() {
        final GeefDetailsPersoonBericht vraagDetailsPersoon = maakBericht(BSN_AANWEZIG, ANR_AANWEZIG,
                                                                          OBJECTSLEUTEL_AANWEZIG);

        final BerichtRegelContext regelContext =
                new BerichtRegelContext(null, SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON, vraagDetailsPersoon);

        final List<BerichtIdentificeerbaar> overtreders = brbv0001.valideer(regelContext);
        Assert.assertEquals(1, overtreders.size());
        Assert.assertEquals(vraagDetailsPersoon.getZoekcriteriaPersoon(), overtreders.get(0));
    }

    /**
     * Maakt een bericht.
     *
     * @param isBsnAanwezig is bsn aanwezig
     * @param isAnrAanwezig is anr aanwezig
     * @param isObjectSleutelAanwezig is object sleutel aanwezig
     * @return geef details persoon bericht.
     */
    private GeefDetailsPersoonBericht maakBericht(final boolean isBsnAanwezig, final boolean isAnrAanwezig,
            final boolean isObjectSleutelAanwezig)
    {
        final GeefDetailsPersoonBericht bericht = new GeefDetailsPersoonBericht();
        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteriaPersoon = new BerichtZoekcriteriaPersoonGroepBericht();
        if (isBsnAanwezig) {
            zoekcriteriaPersoon.setBurgerservicenummer(new BurgerservicenummerAttribuut(111));
        }
        if (isAnrAanwezig) {
            zoekcriteriaPersoon.setAdministratienummer(new AdministratienummerAttribuut(222L));
        }
        if (isObjectSleutelAanwezig) {
            zoekcriteriaPersoon.setObjectSleutel(new SleutelwaardetekstAttribuut("333"));
        }
        bericht.setZoekcriteriaPersoon(zoekcriteriaPersoon);
        return bericht;
    }

}
