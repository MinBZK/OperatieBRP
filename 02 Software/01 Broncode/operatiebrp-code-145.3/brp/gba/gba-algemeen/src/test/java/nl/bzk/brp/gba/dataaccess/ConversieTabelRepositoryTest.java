/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;
import nl.bzk.brp.gba.dataaccess.conversie.AanduidingInhoudingVermissingReisdocumentRepository;
import nl.bzk.brp.gba.dataaccess.conversie.AangifteAdreshoudingRepository;
import nl.bzk.brp.gba.dataaccess.conversie.AdellijkeTitelPredikaatRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RNIDeelnemerRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RedenOntbindingHuwelijkPartnerschapRepository;
import nl.bzk.brp.gba.dataaccess.conversie.RedenOpschortingRepository;
import nl.bzk.brp.gba.dataaccess.conversie.SoortNlReisdocumentRepository;
import nl.bzk.brp.gba.dataaccess.conversie.VoorvoegselConversieRepository;
import org.junit.Assert;
import org.junit.Test;

/**
 * Conversie tabel repository test
 */
public class ConversieTabelRepositoryTest extends AbstractIntegratieTest {

    @Inject
    private AanduidingInhoudingVermissingReisdocumentRepository aanduidingInhoudingVermissingReisdocumentRepository;
    @Inject
    private AangifteAdreshoudingRepository aangifteAdreshoudingRepository;
    @Inject
    private AdellijkeTitelPredikaatRepository adellijkeTitelPredikaatRepository;
    @Inject
    private RedenOntbindingHuwelijkPartnerschapRepository redenOntbindingHuwelijkPartnerschapRepository;
    @Inject
    private RedenOpschortingRepository redenOpschortingRepository;
    @Inject
    private RNIDeelnemerRepository rniDeelnemerRepository;
    @Inject
    private SoortNlReisdocumentRepository soortNlReisdocumentRepository;
    @Inject
    private VoorvoegselConversieRepository voorvoegselConversieRepository;

    @Test
    public final void findAllAangifteAdreshouding() {
        final List<AangifteAdreshouding> result = aangifteAdreshoudingRepository.findAll();
        Assert.assertEquals(12, result.size());
    }

    @Test
    public final void findAllAdellijkeTitelPredikaat() {
        final List<AdellijkeTitelPredikaat> result = adellijkeTitelPredikaatRepository.findAll();
        Assert.assertEquals(14, result.size());
    }

    @Test
    public final void findAllRedenInhoudingVermissingReisdocument() {
        final List<AanduidingInhoudingVermissingReisdocument> result = aanduidingInhoudingVermissingReisdocumentRepository.findAll();
        Assert.assertEquals(4, result.size());
    }

    @Test
    public final void findAllRedenOntbindingHuwelijkPartnerschap() {
        final List<RedenOntbindingHuwelijkPartnerschap> result = redenOntbindingHuwelijkPartnerschapRepository.findAll();
        Assert.assertEquals(8, result.size());
    }

    @Test
    public final void findAllRedenOpschorting() {
        final List<RedenOpschorting> result = redenOpschortingRepository.findAll();
        Assert.assertEquals(9, result.size());
    }

    @Test
    public final void findAllSoortNlReisdocument() {
        final List<SoortNlReisdocument> result = soortNlReisdocumentRepository.findAll();
        Assert.assertEquals(30, result.size());
    }

    @Test
    public final void findAllVoorvoegsel() {
        final List<VoorvoegselConversie> result = voorvoegselConversieRepository.findAll();
        Assert.assertEquals(333, result.size());
    }

    @Test
    public final void findAllRNIDeelnemer() {
        final List<RNIDeelnemer> result = rniDeelnemerRepository.findAll();
        Assert.assertEquals(7, result.size());
    }
}
