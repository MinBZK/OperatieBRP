/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import nl.bzk.migratiebrp.bericht.model.sync.generated.AdellijkeTitelPredicaatType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.NaamGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AdellijkeTitelPredikaatPaar;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class GeslachtsnaamComponentenControleTest {

    private static final String VOORNAMEN = "Jan-Pieter";
    private static final String VOORVOEGSELS = "op den";
    private static final String GESLACHTSNAAM = "Kamp van Oost naar West";
    private static final AdellijkeTitel ADELLIJKTITEL = AdellijkeTitel.H;
    private static final Predicaat PREDICAAT = Predicaat.J;

    @Mock
    private Persoon persoon;

    @Mock
    private ConversietabelFactory conversieTabellen;

    @Mock
    private Lo3AttribuutConverteerder converteerder;

    @Mock
    private Conversietabel<Lo3AdellijkeTitelPredikaatCode, AdellijkeTitelPredikaatPaar> adellijkeTitelPredikaatConversietabel;

    @InjectMocks
    private final GeslachtsnaamComponentenControle subject = new GeslachtsnaamComponentenControle();

    @Test
    public void testTrue() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.JH);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseVerkeerdeVoornamen() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen("Henk");
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.JH);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseVerkeerdeVoorvoegsel() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel("den");
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.JH);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseVerkeerdeGeslachtsnaam() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam("Besten");
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.JH);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseZonderAdellijkeTitelPredicaat() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseFoutieveAdellijkeTitel() {
        final AdellijkeTitelPredikaatPaar predicaatPaar =
                new AdellijkeTitelPredikaatPaar(new BrpCharacter('B'), new BrpCharacter('J'), BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(adellijkeTitelPredikaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class)))
               .thenReturn(predicaatPaar);

        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.B);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseFoutievePredicaat() {
        final AdellijkeTitelPredikaatPaar predicaatPaar =
                new AdellijkeTitelPredikaatPaar(new BrpCharacter('H'), new BrpCharacter('H'), BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(adellijkeTitelPredikaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class)))
               .thenReturn(predicaatPaar);

        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.B);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegePredicaat() {
        final AdellijkeTitelPredikaatPaar predicaatPaar = new AdellijkeTitelPredikaatPaar(new BrpCharacter('H'), null, BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(adellijkeTitelPredikaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class)))
               .thenReturn(predicaatPaar);

        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.B);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeAdellijkeTitel() {
        final AdellijkeTitelPredikaatPaar predicaatPaar = new AdellijkeTitelPredikaatPaar(null, new BrpCharacter('H'), BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(adellijkeTitelPredikaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class)))
               .thenReturn(predicaatPaar);

        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final NaamGroepType naamGroepType = new NaamGroepType();
        naamGroepType.setVoornamen(VOORNAMEN);
        naamGroepType.setVoorvoegsel(VOORVOEGSELS);
        naamGroepType.setGeslachtsnaam(GESLACHTSNAAM);
        naamGroepType.setAdellijkeTitelPredicaat(AdellijkeTitelPredicaatType.B);

        final PersoonType persoonType = new PersoonType();
        persoonType.setNaam(naamGroepType);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalse() {
        Mockito.when(persoon.getVoornamen()).thenReturn(VOORNAMEN);
        Mockito.when(persoon.getVoorvoegsel()).thenReturn(VOORVOEGSELS);
        Mockito.when(persoon.getGeslachtsnaamstam()).thenReturn(GESLACHTSNAAM);
        Mockito.when(persoon.getAdellijkeTitel()).thenReturn(ADELLIJKTITEL);
        Mockito.when(persoon.getPredicaat()).thenReturn(PREDICAAT);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Before
    public void setup() {
        final AdellijkeTitelPredikaatPaar predicaatPaar =
                new AdellijkeTitelPredikaatPaar(new BrpCharacter('H'), new BrpCharacter('J'), BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(adellijkeTitelPredikaatConversietabel.converteerNaarBrp(Matchers.any(Lo3AdellijkeTitelPredikaatCode.class)))
               .thenReturn(predicaatPaar);
        Mockito.when(conversieTabellen.createAdellijkeTitelPredikaatConversietabel()).thenReturn(adellijkeTitelPredikaatConversietabel);
        ReflectionTestUtils.setField(converteerder, "conversietabellen", conversieTabellen);
    }
}
