/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.erkenning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0136Test {

    private static final String  IDENTIFICERENDE_SLEUTEL_OUDER              = "123456";
    private static final String  IDENTIFICERENDE_SLEUTEL_NIET_INGESCHREVENE = "654321";

    private static final boolean NIET_OVERLEDEN                             = false;
    private static final boolean OVERLEDEN                                  = true;

    private BRBY0136             brby0136;

    @Before
    public void init() {
        brby0136 = new BRBY0136();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0136, brby0136.getRegel());
    }

    @Test
    public void testOuderNietOverleden() {
        final List<BerichtEntiteit> overtreders =
            brby0136.voerRegelUit(null, maakNieuweSituatie(NIET_OVERLEDEN, IDENTIFICERENDE_SLEUTEL_OUDER), null,
                    maakBestaandeBetrokkenen(NIET_OVERLEDEN, IDENTIFICERENDE_SLEUTEL_OUDER));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderOverleden() {
        final List<BerichtEntiteit> overtreders =
            brby0136.voerRegelUit(null, maakNieuweSituatie(OVERLEDEN, IDENTIFICERENDE_SLEUTEL_OUDER), null,
                    maakBestaandeBetrokkenen(OVERLEDEN, IDENTIFICERENDE_SLEUTEL_OUDER));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNietIngeschreveneOuder() {
        final List<BerichtEntiteit> overtreders =
            brby0136.voerRegelUit(null, maakNieuweSituatie(NIET_OVERLEDEN, IDENTIFICERENDE_SLEUTEL_NIET_INGESCHREVENE), null,
                    maakBestaandeBetrokkenen(NIET_OVERLEDEN, IDENTIFICERENDE_SLEUTEL_OUDER));
        Assert.assertEquals(0, overtreders.size());
    }

    private FamilierechtelijkeBetrekkingBericht maakNieuweSituatie(final boolean ouderOverleden,
            final String identificerendeSleutelOuder)
    {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();

        final PersoonBericht ouderPersoon = new PersoonBericht();
        ouderPersoon.setIdentificerendeSleutel(identificerendeSleutelOuder);
        if (ouderOverleden) {
            final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
            ouderPersoon.setOverlijden(overlijden);
        }
        final OuderBericht ouder = new OuderBericht();
        ouder.setPersoon(ouderPersoon);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(ouder);

        return familie;
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final boolean ouderOverleden,
            final String identificerendeSleutelOuder)
    {
        final Map<String, PersoonView> betrokkenheden = new HashMap<>();

        final PersoonHisVolledigImplBuilder persoonBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        if (ouderOverleden) {
            persoonBuilder.nieuwOverlijdenRecord(20130101).datumOverlijden(20130101).eindeRecord();
        }
        final PersoonView persoon = new PersoonView(persoonBuilder.build());

        betrokkenheden.put(identificerendeSleutelOuder, persoon);

        return betrokkenheden;
    }

}
