/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.afnemerindicatie;

import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRLV0011Test {

    private BRLV0011 brlv0011;

    @Before
    public void init() {
        brlv0011 = new BRLV0011();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0011, brlv0011.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AfnemerindicatieRegelContext.class, brlv0011.getContextType());
    }

    @Test
    public void nieuweSituatieHeeftAanvangMaterielePeriodeInVerleden() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        null, null,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null,
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null);

        final boolean resultaat = brlv0011.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftAanvangMaterielePeriodeVandaag() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        null, null,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null,
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null);

        final boolean resultaat = brlv0011.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftAanvangMaterielePeriodeMorgen() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        null, null,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null,
                        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.morgen()), null);

        final boolean resultaat = brlv0011.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftGeenAanvangMaterielePeriode() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        null, null,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0011.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);

    }

    @Test
    public void contextZonderSoortAdministratieveHandeling() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null,
                        SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0011.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

}
