/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.gba.dataaccess;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAangifteAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRNIDeelnemer;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieRedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieSoortNLReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.conv.ConversieVoorvoegsel;


/**
 * Conversie tabel repository test
 */
public class ConversieTabelRepositoryTest extends AbstractIntegratieTest {

    @Inject
    private ConversieTabelRepository conversieTabelRepository;

    @Test
    public final void findAllAangifteAdreshouding() {
        final List<ConversieAangifteAdreshouding> result = conversieTabelRepository.findAllAangifteAdreshouding();
        Assert.assertEquals(12, result.size());
    }

    @Test
    public final void findAllAdellijkeTitelPredikaat() {
        final List<ConversieAdellijkeTitelPredikaat> result = conversieTabelRepository.findAllAdellijkeTitelPredikaat();
        Assert.assertEquals(14, result.size());
    }

    @Test
    public final void findAllRedenInhoudingVermissingReisdocument() {
        final List<ConversieAanduidingInhoudingVermissingReisdocument> result =
                conversieTabelRepository.findAllAanduidingInhoudingVermissingReisdocument();
        Assert.assertEquals(3, result.size());
    }

    @Test
    public final void findAllRedenOntbindingHuwelijkPartnerschap() {
        final List<ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap> result =
                conversieTabelRepository.findAllRedenOntbindingHuwelijkPartnerschap();
        Assert.assertEquals(8, result.size());
    }

    @Test
    public final void findAllRedenOpschorting() {
        final List<ConversieRedenOpschorting> result = conversieTabelRepository.findAllRedenOpschorting();
        Assert.assertEquals(8, result.size());
    }

    @Test
    public final void findAllSoortNlReisdocument() {
        final List<ConversieSoortNLReisdocument> result = conversieTabelRepository.findAllSoortNlReisdocument();
        Assert.assertEquals(30, result.size());
    }

    @Test
    public final void findAllVoorvoegsel() {
        final List<ConversieVoorvoegsel> result = conversieTabelRepository.findAllVoorvoegsel();
        Assert.assertEquals(333, result.size());
    }

    @Test
    public final void findAllRNIDeelnemer() {
        final List<ConversieRNIDeelnemer> result = conversieTabelRepository.findAllRNIDeelnemer();
        Assert.assertEquals(7, result.size());
    }
}
