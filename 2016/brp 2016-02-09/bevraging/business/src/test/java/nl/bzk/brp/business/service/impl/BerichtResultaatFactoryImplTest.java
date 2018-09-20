/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service.impl;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.service.BerichtResultaatFactoryImpl;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContext;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.model.bevraging.bijhouding.BepaalKandidaatVaderBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.bijhouding.GeefPersonenOpAdresMetBetrokkenhedenBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.service.BerichtResultaatFactory;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Test;


public class BerichtResultaatFactoryImplTest {

    private final BerichtResultaatFactory berichtResultaatFactoryImpl = new BerichtResultaatFactoryImpl();

    @Test
    public final void testCreeerBerichtResultaatVraagDetailsPersoonBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(new GeefDetailsPersoonBericht(),
                bouwBerichtContext()) instanceof BevragingResultaat);
    }

    @Test
    public final void testCreeerBerichtResultaatVraagPersonenOpAdresInclusiefBetrokkenhedenBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl.creeerBerichtResultaat(
            new GeefPersonenOpAdresMetBetrokkenhedenBericht(),
            bouwBerichtContext()) instanceof BevragingResultaat);
    }

    @Test
    public final void testCreeerBerichtResultaatVraagKandidaatVaderBericht() {
        Assert.assertTrue(berichtResultaatFactoryImpl
            .creeerBerichtResultaat(new BepaalKandidaatVaderBericht(),
                bouwBerichtContext()) instanceof BevragingResultaat);
    }

    private BevragingBerichtContext bouwBerichtContext() {
        return new BevragingBerichtContextBasis(new BerichtenIds(1L, 2L), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "refnr", null);
    }
}
