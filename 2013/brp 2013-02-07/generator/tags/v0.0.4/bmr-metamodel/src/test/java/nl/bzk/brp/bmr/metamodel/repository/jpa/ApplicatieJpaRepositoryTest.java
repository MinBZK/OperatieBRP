/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.bmr.metamodel.AbstractRepositoryTest;
import nl.bzk.brp.bmr.metamodel.Attribuut;
import nl.bzk.brp.bmr.metamodel.Element;
import nl.bzk.brp.bmr.metamodel.repository.ApplicatieRepository;
import nl.bzk.brp.bmr.metamodel.ui.Applicatie;
import nl.bzk.brp.bmr.metamodel.ui.Bron;
import nl.bzk.brp.bmr.metamodel.ui.BronAttribuut;
import nl.bzk.brp.bmr.metamodel.ui.Formulier;
import nl.bzk.brp.bmr.metamodel.ui.Frame;
import nl.bzk.brp.bmr.metamodel.ui.FrameVeld;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class ApplicatieJpaRepositoryTest extends AbstractRepositoryTest {

    private static final String  AANTAL_FRAMES         = "select count(*) from FRAME where formulier = ?";
    private static final String  AANTAL_BRONNEN        = "select count(*) from bron where formulier = ?";
    private static final String  AANTAL_BRONATTRIBUTEN = "select count(*) from BRONATTRIBUUT where BRON = ?";
    private static final String  AANTAL_FRAMEVELDEN    = "select count(*) from frameveld where frame = ?";

    @Inject
    private ApplicatieRepository repository;

    @Test
    @Ignore("Niet iedereen, ook Jenkins niet,  heeft een Firebird installatie met geldige BMR inhoud")
    public void test() {
        Applicatie applicatie = (Applicatie) repository.findByNaam("Beheerapplicatie");
        Assert.assertEquals("Beheerapplicatie", applicatie.getNaam());

        List<Formulier> formulieren = applicatie.getFormulieren();
        Assert.assertEquals(2, formulieren.size());
        Formulier formulier = formulieren.get(0);
        Assert.assertEquals("Partij", formulier.getNaam());
        Assert.assertSame(applicatie, formulier.getApplicatie());

        List<Frame> frames = formulier.getFrames();
        int aantalFrames = simpleJdbcTemplate.queryForInt(AANTAL_FRAMES, formulier.getId());
        Assert.assertEquals(aantalFrames, frames.size());
        Frame frame = frames.get(0);
        Assert.assertNotNull(frame.getNaam());
        Assert.assertEquals("Partij", frame.getNaam());
        Assert.assertSame(formulier, frame.getFormulier());

        List<Bron> bronnen = formulier.getBronnen();
        int aantalBronnen = simpleJdbcTemplate.queryForInt(AANTAL_BRONNEN, formulier.getId());
        Assert.assertEquals(aantalBronnen, bronnen.size());
        Bron bron = bronnen.get(0);
        Assert.assertEquals("Partij", bron.getNaam());
        Assert.assertSame(formulier, bron.getFormulier());

        Assert.assertSame(bron, frame.getBron());

        Element objectType = bron.getObjectType();
        Assert.assertNotNull(objectType);
        Assert.assertEquals("Partij", objectType.getNaam());

        List<BronAttribuut> bronAttributen = bron.getBronAttibuten();
        int aantalBronAttributen = simpleJdbcTemplate.queryForInt(AANTAL_BRONATTRIBUTEN, bron.getId());
        Assert.assertEquals(aantalBronAttributen, bronAttributen.size());
        BronAttribuut bronVeld = bronAttributen.get(0);
        Assert.assertEquals("Test Sector", bronVeld.getNaam());
        Assert.assertSame(bron, bronVeld.getBron());

        bron = bronnen.get(1);
        Assert.assertEquals("Rol", bron.getNaam());
        Assert.assertEquals("Rollen", bron.getMeervoudsNaam());
        Assert.assertNull(bron.getIdentifier());
        Attribuut attribuut = bron.getLink();
        Assert.assertNotNull(attribuut);
        Assert.assertEquals("Partij", attribuut.getNaam());
        Assert.assertSame(bron.getObjectType(), attribuut.getObjectType());

        Attribuut bronVeldAttribuut = bronVeld.getAttribuut();
        Assert.assertNotNull(bronVeldAttribuut);
        Assert.assertEquals("Sector", bronVeldAttribuut.getNaam());

        List<FrameVeld> frameVelden = frame.getVelden();
        int aantalFrameVelden = simpleJdbcTemplate.queryForInt(AANTAL_FRAMEVELDEN, frame.getId());
        Assert.assertEquals(aantalFrameVelden, frameVelden.size());
        FrameVeld frameVeld = frameVelden.get(0);
        Assert.assertSame(frame, frameVeld.getFrame());
        Attribuut frameVeldAttribuut = frameVeld.getAttribuut();
        Assert.assertNotNull(frameVeldAttribuut);
        Assert.assertEquals("ID", frameVeldAttribuut.getNaam());
    }
}
