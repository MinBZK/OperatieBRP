/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import java.util.Collections;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSoortGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Sluiting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;

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
public class SoortVerbintenisOngelijkControleTest {

    @Mock
    private Persoon persoon;

    @Mock
    private Relatie relatie;

    @Mock
    private ConversietabelFactory conversieTabellen;

    @Mock
    private Lo3AttribuutConverteerder converteerder;

    @Mock
    private Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> soortVerbintenisConversietabel;

    @InjectMocks
    private final SoortVerbintenisOngelijkControle subject = new SoortVerbintenisOngelijkControle();

    @Test
    public void testFalse() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrue() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueGeenRelaties() {
        Mockito.when(persoon.getRelaties()).thenReturn(null);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueLegeRelatieSet() {
        final Set<Relatie> relatieSet = Collections.<Relatie>emptySet();
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueGeenRelatieSoort() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(null);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueGeenRelatieVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueGeenSluitingVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final Sluiting sluiting = new Sluiting();
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueGeenSoortVerbintenisVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieType relatieType = new RelatieType();
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueLegeSoortVerbintenisVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Before
    public void setup() {
        Mockito.when(soortVerbintenisConversietabel.converteerNaarBrp(Matchers.any(Lo3SoortVerbintenis.class))).thenReturn(BrpSoortRelatieCode.HUWELIJK);
        Mockito.when(conversieTabellen.createSoortRelatieConversietabel()).thenReturn(soortVerbintenisConversietabel);
        ReflectionTestUtils.setField(converteerder, "conversietabellen", conversieTabellen);
    }
}
