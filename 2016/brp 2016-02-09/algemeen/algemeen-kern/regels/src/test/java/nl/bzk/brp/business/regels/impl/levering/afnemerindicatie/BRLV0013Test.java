/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.afnemerindicatie;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.brp.business.regels.context.AfnemerindicatieRegelContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRLV0013Test {

    private BRLV0013 brlv0013;

    @Before
    public void init() {
        brlv0013 = new BRLV0013();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0013, brlv0013.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AfnemerindicatieRegelContext.class, brlv0013.getContextType());
    }

    @Test
    public void nieuweSituatieHeeftEindeVolgenInVerleden() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(null, null, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null,
                                                 new DatumAttribuut(20120101));

        final boolean resultaat = brlv0013.valideer(context);
        Assert.assertEquals(BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftEindeVolgenVandaag() {
        final Calendar vandaagPlusMinuutOokNogVandaag = Calendar.getInstance();
        vandaagPlusMinuutOokNogVandaag.set(Calendar.MINUTE, 0);
        final Date vandaagOverMinuut = DateUtils.addMinutes(vandaagPlusMinuutOokNogVandaag.getTime(), 1);

        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(
                        null, null,
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null,
                        new DatumAttribuut(vandaagOverMinuut));

        final boolean resultaat = brlv0013.valideer(context);
        Assert.assertEquals("Datum einde volgen mag niet vandaag zijn.", BRLV0001.INVALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftEindevolgenMorgen() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(null, null, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null, DatumAttribuut.morgen());
        final boolean resultaat = brlv0013.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void nieuweSituatieHeeftGeenEindevolgen() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(null, null, SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE, null, null, null);
        final boolean resultaat = brlv0013.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

    @Test
    public void contextZonderSoortAdministratieveHandeling() {
        final AfnemerindicatieRegelContext context =
                new AfnemerindicatieRegelContext(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(), null,
                                                 SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE, null, null, null);

        final boolean resultaat = brlv0013.valideer(context);
        Assert.assertEquals(BRLV0001.VALIDE, resultaat);
    }

}
