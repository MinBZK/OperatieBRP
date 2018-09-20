/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.autorisatie;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import org.junit.Assert;
import org.junit.Test;

public class BRLV0041Test {

    private static final SoortDienst [] RELEVANTE_DIENSTEN = {
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING,
            SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE
    };
    private BRLV0041 regel = new BRLV0041();

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0041, regel.getRegel());
    }

    @Test
    public final void invalideAlsDienstOntbreekt() {
        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(maakToegangLeveringautorisatie( new Dienst[0]), null);
        Assert.assertFalse(regel.valideer(regelContext));
    }

    @Test
    public final void invalideBijEnkelOnjuisteDienst() {
        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(maakToegangLeveringautorisatie(
                TestDienstBuilder.maker().metSoortDienst(SoortDienst.SYNCHRONISATIE_PERSOON).maak()), null);
        Assert.assertFalse(regel.valideer(regelContext));
    }

    @Test
    public final void invalideAlsRelevanteDienstOngeldigIs() {
        for (final SoortDienst soortDienst : RELEVANTE_DIENSTEN) {
            final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst)
                    .metDatumIngang(DatumAttribuut.morgen())
                    .maak();
            final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(maakToegangLeveringautorisatie(dienst), null);
            Assert.assertFalse(regel.valideer(regelContext));
        }
    }

    @Test
    public final void invalideAlsRelevanteDienstGeblokkeerdIs() {
        for (final SoortDienst soortDienst : RELEVANTE_DIENSTEN) {
            final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst)
                    .metIndicatieGeblokkeerd(true)
                    .maak();
            final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(maakToegangLeveringautorisatie(dienst), null);
            Assert.assertFalse(regel.valideer(regelContext));
        }
    }

    private ToegangLeveringsautorisatie maakToegangLeveringautorisatie(Dienst ...diensten) {
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(diensten).maak();
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metDienstbundels(dienstbundel).maak();
        return TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(leveringsautorisatie).maak();
    }
}
