/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test zowel het indicatie gedeelte (overrides) als het specifieke verstrekkingsbeperking gedeelte (zie onderaan).
 */
public class RegistratieVerstrekkingsbeperkingUitvoerderTest
        extends AbstractRegistratieIndicatieUitvoerderTest<ActieRegistratieVerstrekkingsbeperkingBericht>
{

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING;
    }

    @Override
    protected AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder() {
        return new RegistratieVerstrekkingsbeperkingUitvoerder();
    }

    @Override
    protected ActieRegistratieVerstrekkingsbeperkingBericht maakNieuwActieBericht() {
        return new ActieRegistratieVerstrekkingsbeperkingBericht();
    }

    @Override
    protected void voegNieuweIndicatieHisVolledigToe() {
        getPersoon().setIndicatieVolledigeVerstrekkingsbeperking(
                new PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl(getPersoon()));
    }

    @Override
    protected void verwijderIndicatie() {
        getPersoon().setIndicatieVolledigeVerstrekkingsbeperking(null);
    }

    private static final int DUMMY_ID = 1234;
    private static final PartijAttribuut PARTIJ_1 = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_UTRECHT;
    private static final PartijAttribuut PARTIJ_2 = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA;
    private static final PartijAttribuut GEMEENTE_1 = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM;
    private static final PartijAttribuut GEMEENTE_2 = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_SGRAVENHAGE;
    private static final OmschrijvingEnumeratiewaardeAttribuut OMSCHRIJVING_1 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 1");
    private static final OmschrijvingEnumeratiewaardeAttribuut OMSCHRIJVING_2 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 2");
    private static final boolean NIEUWE_HIS_VOLLEDIG_IMPL = true;
    private static final boolean BESTAANDE_HIS_VOLLEDIG_IMPL = false;

    @Test
    public void testNieuweSpecifiekeVerstrekkingsBeperking() {
        assertUitvoerder(
                maakBericht(PARTIJ_1, null, null),
                maakPersoon(null, null, null),
                NIEUWE_HIS_VOLLEDIG_IMPL);
    }

    @Test
    public void testMatchPartijSpecifiekeVerstrekkingsBeperking() {
        assertUitvoerder(
                maakBericht(PARTIJ_1, null, null),
                maakPersoon(PARTIJ_1, null, null),
                BESTAANDE_HIS_VOLLEDIG_IMPL);
    }

    @Test
    public void testMatchOmschrijvingGemeenteVerordeningSpecifiekeVerstrekkingsBeperking() {
        assertUitvoerder(
                maakBericht(null, OMSCHRIJVING_1, GEMEENTE_1),
                maakPersoon(null, OMSCHRIJVING_1, GEMEENTE_1),
                BESTAANDE_HIS_VOLLEDIG_IMPL);
    }

    @Test
    // Coverage master! :)
    public void testMismatchSpecifiekeVerstrekkingsBeperking() {
        assertUitvoerder(
                maakBericht(PARTIJ_1, null, null),
                maakPersoon(null, OMSCHRIJVING_1, GEMEENTE_1),
                NIEUWE_HIS_VOLLEDIG_IMPL);

        assertUitvoerder(
                maakBericht(null, OMSCHRIJVING_1, GEMEENTE_1),
                maakPersoon(PARTIJ_1, null, null),
                NIEUWE_HIS_VOLLEDIG_IMPL);

        assertUitvoerder(
                maakBericht(PARTIJ_1, null, null),
                maakPersoon(PARTIJ_2, null, null),
                NIEUWE_HIS_VOLLEDIG_IMPL);

        assertUitvoerder(
                maakBericht(PARTIJ_1, null, null),
                maakPersoon(PARTIJ_2, null, null),
                NIEUWE_HIS_VOLLEDIG_IMPL);

        assertUitvoerder(
                maakBericht(null, OMSCHRIJVING_1, GEMEENTE_1),
                maakPersoon(null, OMSCHRIJVING_2, GEMEENTE_2),
                NIEUWE_HIS_VOLLEDIG_IMPL);

        assertUitvoerder(
                maakBericht(null, OMSCHRIJVING_1, GEMEENTE_1),
                maakPersoon(null, OMSCHRIJVING_1, GEMEENTE_2),
                NIEUWE_HIS_VOLLEDIG_IMPL);

    }

    private void assertUitvoerder(final PersoonBericht bericht, final PersoonHisVolledigImpl persoon, final boolean nieuweHisVolledigImpl) {
        RegistratieVerstrekkingsbeperkingUitvoerder uitvoerder = bereidUitvoerderVoor(bericht, persoon);
        uitvoerder.verzamelVerwerkingsregels();

        @SuppressWarnings("unchecked")
        List<Verwerkingsregel> verwerkingsregels =
                (List<Verwerkingsregel>) ReflectionTestUtils.getField(uitvoerder, "verwerkingsregels");

        Assert.assertEquals(1, verwerkingsregels.size());
        PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperkingHisVolledigImpl =
                (PersoonVerstrekkingsbeperkingHisVolledigImpl) ReflectionTestUtils.getField(verwerkingsregels.get(0), "model");

        if (nieuweHisVolledigImpl) {
            Assert.assertNull(persoonVerstrekkingsbeperkingHisVolledigImpl.getID());
        } else {
            Assert.assertNotNull(persoonVerstrekkingsbeperkingHisVolledigImpl.getID());
            Assert.assertEquals(DUMMY_ID, persoonVerstrekkingsbeperkingHisVolledigImpl.getID().intValue());
        }
    }

    private PersoonBericht maakBericht(final PartijAttribuut partij, final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde, final PartijAttribuut gemeenteVerordening) {
        PersoonBericht bericht = new PersoonBericht();
        if (partij == null && omschrijvingDerde == null && gemeenteVerordening == null) {
            // Alles null betekent geen verstr bepr in bericht (dus ook geen collectie).
        } else {
            bericht.setVerstrekkingsbeperkingen(new ArrayList<PersoonVerstrekkingsbeperkingBericht>());
            PersoonVerstrekkingsbeperkingBericht vestrbepr = new PersoonVerstrekkingsbeperkingBericht();
            vestrbepr.setPartij(partij);
            vestrbepr.setOmschrijvingDerde(omschrijvingDerde);
            vestrbepr.setGemeenteVerordening(gemeenteVerordening);
            bericht.getVerstrekkingsbeperkingen().add(vestrbepr);
        }
        return bericht;
    }

    private PersoonHisVolledigImpl maakPersoon(final PartijAttribuut partij, final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde, final PartijAttribuut gemeenteVerordening) {
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        if (partij == null && omschrijvingDerde == null && gemeenteVerordening == null) {
            // Alles null betekent geen huidige specifieke his volledig impl.
        } else {
            PersoonVerstrekkingsbeperkingHisVolledigImpl verstrbepr = new PersoonVerstrekkingsbeperkingHisVolledigImpl(
                    persoon, partij, omschrijvingDerde, gemeenteVerordening);
            ReflectionTestUtils.setField(verstrbepr, "iD", DUMMY_ID);
            persoon.getVerstrekkingsbeperkingen().add(verstrbepr);
        }
        return persoon;
    }

    private RegistratieVerstrekkingsbeperkingUitvoerder bereidUitvoerderVoor(final PersoonBericht persoonBericht,
            final PersoonHisVolledigImpl persoonHisVolledig)
    {
        RegistratieVerstrekkingsbeperkingUitvoerder uitvoerder = new RegistratieVerstrekkingsbeperkingUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", persoonHisVolledig);

        return uitvoerder;
    }
}
