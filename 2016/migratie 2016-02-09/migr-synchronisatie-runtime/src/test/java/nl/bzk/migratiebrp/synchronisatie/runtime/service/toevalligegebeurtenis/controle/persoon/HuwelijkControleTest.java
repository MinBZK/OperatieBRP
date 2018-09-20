/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.persoon;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Set;

import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSluitingGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieSoortGroepType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RelatieType.Sluiting;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortRelatieType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkToevalligeGebeurtenisVerzoekBericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Gemeente;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.LandOfGebied;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Relatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HuwelijkControleTest {

    private static final Integer DATUM_AANVANG = Integer.valueOf("20150901");
    private static final Gemeente GEMEENTE_AANVANG = new Gemeente(Short.valueOf("599"), "Amsterdam", Short.valueOf("0599"), new Partij("Amsterdam", 599));
    private static final String BUITENLANDSE_PLAATS_AANVANG = "Rome";
    private static final String BUITENLANDSE_PLAATS_AANVANG_LANG = "New York";
    private static final LandOfGebied LAND_AANVANG = new LandOfGebied(Short.valueOf("0630"), "Nederland");

    @Mock
    private Persoon persoon;

    @Mock
    private Relatie relatie;

    @InjectMocks
    private final HuwelijkControle subject = new HuwelijkControle();

    @Test
    public void testFalseLandOngelijk() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(GEMEENTE_AANVANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand("Verenigde Staten");
        relatieSluiting.setPlaats(String.valueOf(GEMEENTE_AANVANG.getCode()));
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalsePlaatsOngelijk() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(GEMEENTE_AANVANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand("Verenigde Staten");
        relatieSluiting.setPlaats(BUITENLANDSE_PLAATS_AANVANG_LANG);
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseBuitenlandsePlaatsOngelijk() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(null);
        Mockito.when(relatie.getBuitenlandsePlaatsAanvang()).thenReturn(BUITENLANDSE_PLAATS_AANVANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(BUITENLANDSE_PLAATS_AANVANG_LANG);
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrue() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(GEMEENTE_AANVANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(String.valueOf(GEMEENTE_AANVANG.getCode()));
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalse() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        Mockito.when(relatie.getBuitenlandsePlaatsAanvang()).thenReturn(BUITENLANDSE_PLAATS_AANVANG);
        Mockito.when(relatie.getBuitenlandsePlaatsAanvang()).thenReturn(BUITENLANDSE_PLAATS_AANVANG_LANG);

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
    public void testFalseGeenRelaties() {
        Mockito.when(persoon.getRelaties()).thenReturn(null);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(String.valueOf(GEMEENTE_AANVANG.getCode()));
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeRelatieSet() {
        final Set<Relatie> relatieSet = Collections.<Relatie>emptySet();
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(String.valueOf(GEMEENTE_AANVANG.getCode()));
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueBuitenlandKort() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(null);
        Mockito.when(relatie.getBuitenlandsePlaatsAanvang()).thenReturn(BUITENLANDSE_PLAATS_AANVANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(BUITENLANDSE_PLAATS_AANVANG);
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testTrueBuitenlandLang() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getDatumAanvang()).thenReturn(DATUM_AANVANG);
        Mockito.when(relatie.getGemeenteAanvang()).thenReturn(null);
        Mockito.when(relatie.getBuitenlandsePlaatsAanvang()).thenReturn(BUITENLANDSE_PLAATS_AANVANG_LANG);
        Mockito.when(relatie.getLandOfGebiedAanvang()).thenReturn(LAND_AANVANG);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieSoortGroepType relatieSoortGroep = new RelatieSoortGroepType();
        relatieSoortGroep.setSoort(SoortRelatieType.H);
        final Sluiting sluiting = new Sluiting();
        sluiting.setSoort(relatieSoortGroep);
        final RelatieSluitingGroepType relatieSluiting = new RelatieSluitingGroepType();
        relatieSluiting.setDatum(BigInteger.valueOf(DATUM_AANVANG));
        relatieSluiting.setLand(String.valueOf(LAND_AANVANG.getCode()));
        relatieSluiting.setPlaats(BUITENLANDSE_PLAATS_AANVANG_LANG);
        sluiting.setSluiting(relatieSluiting);
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertTrue(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenRelatieSoort() {
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
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenRelatieVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenSluitingVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final Sluiting sluiting = new Sluiting();
        final RelatieType relatieType = new RelatieType();
        relatieType.setSluiting(sluiting);
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseGeenSoortVerbintenisVerzoek() {
        final Set<Relatie> relatieSet = Collections.singleton(relatie);
        Mockito.when(persoon.getRelaties()).thenReturn(relatieSet);
        Mockito.when(relatie.getSoortRelatie()).thenReturn(SoortRelatie.HUWELIJK);

        final VerwerkToevalligeGebeurtenisVerzoekBericht verzoek = new VerwerkToevalligeGebeurtenisVerzoekBericht();
        final RelatieType relatieType = new RelatieType();
        verzoek.setRelatie(relatieType);
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

    @Test
    public void testFalseLegeSoortVerbintenisVerzoek() {
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
        Assert.assertFalse(subject.controleer(persoon, verzoek));
    }

}
