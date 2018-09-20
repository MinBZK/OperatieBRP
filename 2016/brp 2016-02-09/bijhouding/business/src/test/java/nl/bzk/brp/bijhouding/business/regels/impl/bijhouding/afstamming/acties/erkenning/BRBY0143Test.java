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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOuderBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0143Test {

    private static final String IDENTIFICERENDE_SLEUTEL_OUDER              = "123456";
    private static final String IDENTIFICERENDE_SLEUTEL_NIET_INGESCHREVENE = "654321";

    private final DatumEvtDeelsOnbekendAttribuut ouderDanZestien  = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
    private final DatumEvtDeelsOnbekendAttribuut jongerDanZestien = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());

    private BRBY0143 brby0143;

    @Before
    public void init() {
        brby0143 = new BRBY0143();
        ouderDanZestien.voegJaarToe(-20);
        jongerDanZestien.voegJaarToe(-5);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0143, brby0143.getRegel());
    }

    @Test
    public void testOuderOudGenoeg() {
        final List<BerichtEntiteit> overtreders =
            brby0143.voerRegelUit(null, maakNieuweSituatie(IDENTIFICERENDE_SLEUTEL_OUDER, ouderDanZestien),
                maakActie(), maakBestaandeBetrokkenen(IDENTIFICERENDE_SLEUTEL_OUDER, ouderDanZestien));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testOuderTeJong() {
        final List<BerichtEntiteit> overtreders =
            brby0143.voerRegelUit(null, maakNieuweSituatie(IDENTIFICERENDE_SLEUTEL_OUDER, jongerDanZestien),
                maakActie(), maakBestaandeBetrokkenen(IDENTIFICERENDE_SLEUTEL_OUDER, jongerDanZestien));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testNietIngeschreveneOuderOudGenoeg() {
        final List<BerichtEntiteit> overtreders =
            brby0143.voerRegelUit(null,
                maakNieuweSituatie(IDENTIFICERENDE_SLEUTEL_NIET_INGESCHREVENE, ouderDanZestien), maakActie(),
                    maakBestaandeBetrokkenen(IDENTIFICERENDE_SLEUTEL_OUDER, ouderDanZestien));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNietIngeschreveneOuderTeJong() {
        final List<BerichtEntiteit> overtreders =
            brby0143.voerRegelUit(null,
                    maakNieuweSituatie(IDENTIFICERENDE_SLEUTEL_NIET_INGESCHREVENE, jongerDanZestien), maakActie(),
                    maakBestaandeBetrokkenen(IDENTIFICERENDE_SLEUTEL_OUDER, jongerDanZestien));
        Assert.assertEquals(1, overtreders.size());
    }

    private FamilierechtelijkeBetrekkingBericht maakNieuweSituatie(final String identificerendeSleutelOuder,
            final DatumEvtDeelsOnbekendAttribuut geboorteDatumOuder)
    {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();

        final PersoonBericht ouderPersoon = new PersoonBericht();
        ouderPersoon.setIdentificerendeSleutel(identificerendeSleutelOuder);
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        geboorte.setDatumGeboorte(geboorteDatumOuder);
        ouderPersoon.setGeboorte(geboorte);
        final OuderBericht ouder = new OuderBericht();
        ouder.setPersoon(ouderPersoon);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(ouder);

        return familie;
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final String identificerendeSleutelOuder,
            final DatumEvtDeelsOnbekendAttribuut geboorteDatumOuder)
    {
        final Map<String, PersoonView> betrokkenheden = new HashMap<>();

        final PersoonView persoon =
            new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwGeboorteRecord(geboorteDatumOuder.getWaarde()).datumGeboorte(geboorteDatumOuder.getWaarde())
                    .eindeRecord().build());

        betrokkenheden.put(identificerendeSleutelOuder, persoon);

        return betrokkenheden;
    }

    private Actie maakActie() {
        final ActieBericht actie = new ActieRegistratieOuderBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()));
        return actie;
    }

}
