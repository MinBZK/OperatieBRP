/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GeslachtsaanduidingType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PersoonType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;

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
public class GeslachtsaanduidingControleTest {

    @Mock
    private Persoon persoon;

    @Mock
    private ConversietabelFactory conversieTabellen;

    @Mock
    private Lo3AttribuutConverteerder converteerder;

    @Mock
    private Conversietabel<Lo3Geslachtsaanduiding, BrpGeslachtsaanduidingCode> geslachtsaanduidingConversietabel;

    @InjectMocks
    private final GeslachtsaanduidingControle subject = new GeslachtsaanduidingControle();

    @Test
    public void testTrue() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.MAN);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
        geslachtGroep.setGeslachtsaanduiding(GeslachtsaanduidingType.M);
        final PersoonType persoonType = new PersoonType();
        persoonType.setGeslacht(geslachtGroep);
        verzoek.setPersoon(persoonType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalse() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
        geslachtGroep.setGeslachtsaanduiding(GeslachtsaanduidingType.V);
        final PersoonType persoonType = new PersoonType();
        persoonType.setGeslacht(geslachtGroep);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenPersoonVerzoek() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenGeslachtGroepVerzoek() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final PersoonType persoonType = new PersoonType();
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeGeslachtsaanduidingVerzoek() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(Geslachtsaanduiding.VROUW);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
        final PersoonType persoonType = new PersoonType();
        persoonType.setGeslacht(geslachtGroep);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeGeslachtsaanduidingBrp() {
        Mockito.when(persoon.getGeslachtsaanduiding()).thenReturn(null);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final GeslachtGroepType geslachtGroep = new GeslachtGroepType();
        geslachtGroep.setGeslachtsaanduiding(GeslachtsaanduidingType.V);
        final PersoonType persoonType = new PersoonType();
        persoonType.setGeslacht(geslachtGroep);
        verzoek.setPersoon(persoonType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Before
    public void setup() {
        Mockito.when(geslachtsaanduidingConversietabel.converteerNaarBrp(Matchers.any(Lo3Geslachtsaanduiding.class))).thenReturn(
            BrpGeslachtsaanduidingCode.MAN);
        Mockito.when(conversieTabellen.createGeslachtsaanduidingConversietabel()).thenReturn(geslachtsaanduidingConversietabel);
        ReflectionTestUtils.setField(converteerder, "conversietabellen", conversieTabellen);
    }
}
