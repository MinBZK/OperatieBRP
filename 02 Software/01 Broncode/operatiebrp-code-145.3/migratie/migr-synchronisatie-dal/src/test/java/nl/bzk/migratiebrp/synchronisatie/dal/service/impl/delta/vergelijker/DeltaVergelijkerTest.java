/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.vergelijker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractDeltaTest;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VergelijkerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.Verschil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.VerschilType;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util.DeltaRootEntiteitModus;

import org.junit.Before;
import org.junit.Test;

/**
 * Unittest voor de {@link AbstractDeltaVergelijker}.
 */
public class DeltaVergelijkerTest extends AbstractDeltaTest {

    private static final SoortNederlandsReisdocument PASPOORT = new SoortNederlandsReisdocument("PD", "paspoort");
    private static final SoortNederlandsReisdocument ID_KAART = new SoortNederlandsReisdocument("NI", "ID-bewijs");
    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;
    private DeltaVergelijker vergelijker;
    private DeltaBepalingContext context;

    @Before
    public void setUp() {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(true);
        vergelijker = new DeltaRootEntiteitVergelijker(DeltaRootEntiteitModus.PERSOON);
        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, null, false);
    }

    @Test
    public void testNieuwReisdocument() {
        maakReisdocument(nieuwPersoon, false);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(1, resultaat.getVerschillen().size());
        final Verschil verschil = resultaat.getVerschillen().iterator().next();
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil.getVerschilType());
        assertEquals(-1L, verschil.getSleutel().getDelen().get(PersoonReisdocument.TECHNISCH_ID));
    }

    @Test
    public void testZelfdeVersieReisdocument() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, false);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertTrue(resultaat.isLeeg());
    }

    @Test
    public void testNieuw2xExactZelfdeReisdocument() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, false);
        maakReisdocument(nieuwPersoon, false);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(1, resultaat.getVerschillen().size());
        final Verschil verschil = resultaat.getVerschillen().iterator().next();
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil.getVerschilType());
        assertEquals(-1L, verschil.getSleutel().getDelen().get(PersoonReisdocument.TECHNISCH_ID));
    }

    @Test
    public void testBestaand2xExactZelfdeReisdocument1xVerwijderd() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, false);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(1, resultaat.getVerschillen().size());
        final Verschil verschil = resultaat.getVerschillen().iterator().next();
        assertEquals(VerschilType.RIJ_VERWIJDERD, verschil.getVerschilType());
        assertNull(verschil.getSleutel().getDelen().get(PersoonReisdocument.TECHNISCH_ID));
    }

    @Test
    public void testReisdocumentPartieleMatch() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, false);

        FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(
                nieuwPersoon.getPersoonReisdocumentSet().iterator().next().getPersoonReisdocumentHistorieSet()).setDatumIngangDocument(20151231);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(1, resultaat.getVerschillen().size());

        final Verschil verschil = resultaat.getVerschillen().iterator().next();
        assertEquals(VerschilType.ELEMENT_AANGEPAST, verschil.getVerschilType());
    }

    @Test
    public void testBestaandReisdocumentNieuwToegevoegd1xBestaand() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, false);
        maakReisdocument(nieuwPersoon, true);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(1, resultaat.getVerschillen().size());

        final Verschil verschil = resultaat.getVerschillen().iterator().next();
        assertEquals(VerschilType.RIJ_TOEGEVOEGD, verschil.getVerschilType());
        assertEquals(-1L, verschil.getSleutel().getDelen().get(PersoonReisdocument.TECHNISCH_ID));
    }

    @Test
    public void testBestaandReisdocumentNieuwToegevoegd() {
        maakReisdocument(bestaandPersoon, false);
        maakReisdocument(nieuwPersoon, true);

        final VergelijkerResultaat resultaat = vergelijker.vergelijk(context, bestaandPersoon, nieuwPersoon);
        assertFalse(resultaat.isLeeg());
        assertEquals(2, resultaat.getVerschillen().size());

        boolean isRijToegevoegd = false;
        boolean isRijVerwijderd = false;

        for (final Verschil verschil : resultaat.getVerschillen()) {
            if (VerschilType.RIJ_TOEGEVOEGD.equals(verschil.getVerschilType())) {
                isRijToegevoegd = true;
            }
            if (VerschilType.RIJ_VERWIJDERD.equals(verschil.getVerschilType())) {
                isRijVerwijderd = true;
            }
        }
        assertTrue(isRijToegevoegd && isRijVerwijderd);
    }

    private void maakReisdocument(final Persoon persoon, final boolean anderDocument) {
        final int datumIngang = anderDocument ? 20150101 : 20160101;
        final int datumUitgifte = anderDocument ? 20150102 : 20160102;
        final int datumEinde = anderDocument ? 20250101 : 20260101;
        final String nummer = anderDocument ? "123456890" : "0987654321";
        final String autoriteitVanAfgifte = anderDocument ? "0518" : "0017";
        final SoortNederlandsReisdocument soortNederlandsReisdocument = anderDocument ? PASPOORT : ID_KAART;

        final PersoonReisdocument reisdocument = new PersoonReisdocument(persoon, soortNederlandsReisdocument);
        final PersoonReisdocumentHistorie historie =
                new PersoonReisdocumentHistorie(datumIngang, datumUitgifte, datumEinde, nummer, autoriteitVanAfgifte, reisdocument);

        reisdocument.addPersoonReisdocumentHistorieSet(historie);
        persoon.addPersoonReisdocument(reisdocument);
    }
}
