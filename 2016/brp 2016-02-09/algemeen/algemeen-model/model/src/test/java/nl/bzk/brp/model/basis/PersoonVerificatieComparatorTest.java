/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerificatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerificatieComparator;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVerificatieHisVolledigImplBuilder;
import org.junit.Test;

public class PersoonVerificatieComparatorTest {

    @Test
    public void sorteringIsVolgensSpecificatie() {
        final PersoonVerificatieHisVolledigImpl verificatieAmsterdamAlfa = new PersoonVerificatieHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                new NaamEnumeratiewaardeAttribuut("Alfa"))
                .nieuwStandaardRecord(20140430).datum(20140430).eindeRecord(1L).build();

        final PersoonVerificatieHisVolledigImpl verificatieAmsterdamBeta = new PersoonVerificatieHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde(),
                new NaamEnumeratiewaardeAttribuut("Beta"))
                .nieuwStandaardRecord(20140101).eindeRecord(2L).build();

        final PersoonVerificatieHisVolledigImpl verificatieBzkDelta = new PersoonVerificatieHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK.getWaarde(),
                new NaamEnumeratiewaardeAttribuut("Delta"))
                .nieuwStandaardRecord(20140101).eindeRecord(3L).build();

        final PersoonVerificatieHisVolledigImpl verificatieBzkTango = new PersoonVerificatieHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK.getWaarde(),
                new NaamEnumeratiewaardeAttribuut("Tango"))
                .nieuwStandaardRecord(20140101).eindeRecord(4L).build();

        final PersoonVerificatieHisVolledigImpl verificatieBzkTangoNieuw = new PersoonVerificatieHisVolledigImplBuilder(
                StatischeObjecttypeBuilder.PARTIJ_MINISTERIE_BZK.getWaarde(),
                new NaamEnumeratiewaardeAttribuut("Tango"))
                .nieuwStandaardRecord(20131001).eindeRecord(5L).build();

        final Set<PersoonVerificatieHisVolledigImpl> set = new TreeSet<>(new PersoonVerificatieComparator());
        set.add(verificatieBzkDelta);
        set.add(verificatieAmsterdamAlfa);
        set.add(verificatieAmsterdamBeta);
        set.add(verificatieBzkTangoNieuw);
        set.add(verificatieBzkTango);

        assertThat(set, contains(verificatieBzkDelta, verificatieBzkTango, verificatieBzkTangoNieuw,
                                 verificatieAmsterdamAlfa, verificatieAmsterdamBeta));
    }
}
