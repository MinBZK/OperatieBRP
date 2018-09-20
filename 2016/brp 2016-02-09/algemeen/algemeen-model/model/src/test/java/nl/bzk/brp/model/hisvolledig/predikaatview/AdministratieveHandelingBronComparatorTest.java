/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.impl.kern.ActieBronHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.DocumentHisVolledigImpl;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class AdministratieveHandelingBronComparatorTest {

    private ActieBronHisVolledigImpl actieBronModel1 = new ActieBronHisVolledigImpl(null, null, null, null);
    private ActieBronHisVolledigImpl actieBronModel2 = new ActieBronHisVolledigImpl(null, null, null, null);

    private AdministratieveHandelingBronComparator administratieveHandelingBronComparator = new AdministratieveHandelingBronComparator();

    @Test
    public void testDocumentBovenRechtsgrond() {
        final DocumentHisVolledigImpl document = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        final RechtsgrondAttribuut rechtsgrond = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));

        zetDocumentOpActieBron(actieBronModel1, document);
        zetRechtsgrondOpActieBron(actieBronModel2, rechtsgrond);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testRechtsgrondOnderDocument() {
        final DocumentHisVolledigImpl document = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        final RechtsgrondAttribuut rechtsgrond = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));

        zetDocumentOpActieBron(actieBronModel2, document);
        zetRechtsgrondOpActieBron(actieBronModel1, rechtsgrond);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testDocumentBovenRechtsgrondOmschrijving() {
        final DocumentHisVolledigImpl document = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 1");

        zetDocumentOpActieBron(actieBronModel1, document);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel2, rechtsgrondOmschrijving);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testRechtsgrondBovenRechtsgrondOmschrijving() {
        final RechtsgrondAttribuut rechtsgrond = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 2");

        zetRechtsgrondOpActieBron(actieBronModel1, rechtsgrond);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel2, rechtsgrondOmschrijving);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(-1, resultaat);
    }

    @Test
    public void testRechtsgrondOmschrijvingOnderRechtsgrond() {
        final RechtsgrondAttribuut rechtsgrond = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 3");

        zetRechtsgrondOpActieBron(actieBronModel2, rechtsgrond);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel1, rechtsgrondOmschrijving);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testDocumentenOpId() {
        final DocumentHisVolledigImpl document1 = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        zetIdOpElementIdentificeerbaar(document1, 2L);
        final DocumentHisVolledigImpl document2 = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        zetIdOpElementIdentificeerbaar(document2, 1L);

        zetDocumentOpActieBron(actieBronModel1, document1);
        zetDocumentOpActieBron(actieBronModel2, document2);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testRechtsgrondenOpId() {
        final RechtsgrondAttribuut rechtsgrond1 = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        zetIdOpElementIdentificeerbaar(rechtsgrond1.getWaarde(), (short) 2);
        final RechtsgrondAttribuut rechtsgrond2 = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        zetIdOpElementIdentificeerbaar(rechtsgrond2.getWaarde(), (short) 1);

        zetRechtsgrondOpActieBron(actieBronModel1, rechtsgrond1);
        zetRechtsgrondOpActieBron(actieBronModel2, rechtsgrond2);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testRechtsgrondOmschrijvingenOpOmschrijving() {
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving1 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 5");
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving2 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 4");

        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel1, rechtsgrondOmschrijving1);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel2, rechtsgrondOmschrijving2);

        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(1, resultaat);
    }

    @Test
    public void testGeenVerschil() {
        final int resultaat = administratieveHandelingBronComparator.compare(actieBronModel1, actieBronModel2);

        assertEquals(0, resultaat);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEersteNull() {
        administratieveHandelingBronComparator.compare(null, actieBronModel2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTweedeNull() {
        administratieveHandelingBronComparator.compare(actieBronModel1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllesNull() {
        administratieveHandelingBronComparator.compare(null, null);
    }

    @Test
    public void testMetLijst() {
        final ActieBronHisVolledigImpl actieBronModel3 = new ActieBronHisVolledigImpl(null, null, null, null);
        final ActieBronHisVolledigImpl actieBronModel4 = new ActieBronHisVolledigImpl(null, null, null, null);
        final ActieBronHisVolledigImpl actieBronModel5 = new ActieBronHisVolledigImpl(null, null, null, null);
        final ActieBronHisVolledigImpl actieBronModel6 = new ActieBronHisVolledigImpl(null, null, null, null);

        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving1 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 7");
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondOmschrijving2 = new OmschrijvingEnumeratiewaardeAttribuut("Omschrijving 6");
        final RechtsgrondAttribuut rechtsgrond1 = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        zetIdOpElementIdentificeerbaar(rechtsgrond1.getWaarde(), (short) 2);
        final RechtsgrondAttribuut rechtsgrond2 = new RechtsgrondAttribuut(new Rechtsgrond(null, null, null, null, null, null));
        zetIdOpElementIdentificeerbaar(rechtsgrond2.getWaarde(), (short) 1);
        final DocumentHisVolledigImpl document1 = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        zetIdOpElementIdentificeerbaar(document1, 2L);
        final DocumentHisVolledigImpl document2 = new DocumentHisVolledigImpl(new SoortDocumentAttribuut(null));
        zetIdOpElementIdentificeerbaar(document2, 1L);


        zetDocumentOpActieBron(actieBronModel6, document1);
        zetDocumentOpActieBron(actieBronModel5, document2);
        zetRechtsgrondOpActieBron(actieBronModel4, rechtsgrond1);
        zetRechtsgrondOpActieBron(actieBronModel3, rechtsgrond2);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel2, rechtsgrondOmschrijving1);
        zetRechtsgrondOmschrijvingOpActieBron(actieBronModel1, rechtsgrondOmschrijving2);

        final SortedSet<ActieBronHisVolledigImpl> actieBronnen = new TreeSet<>(new AdministratieveHandelingBronComparator());
        actieBronnen.addAll(Arrays.asList(actieBronModel1, actieBronModel2, actieBronModel3, actieBronModel4, actieBronModel5, actieBronModel6));

        assertEquals(actieBronModel5, actieBronnen.toArray()[0]);
        assertEquals(actieBronModel6, actieBronnen.toArray()[1]);
        assertEquals(actieBronModel3, actieBronnen.toArray()[2]);
        assertEquals(actieBronModel4, actieBronnen.toArray()[3]);
        assertEquals(actieBronModel1, actieBronnen.toArray()[4]);
        assertEquals(actieBronModel2, actieBronnen.toArray()[5]);
    }

    private void zetDocumentOpActieBron(final ActieBronHisVolledigImpl actieBronHisVolledig, final DocumentHisVolledigImpl documentHisVolledig) {
        ReflectionTestUtils.setField(actieBronHisVolledig, "document", documentHisVolledig);
    }

    private void zetRechtsgrondOpActieBron(final ActieBronHisVolledigImpl actieBronHisVolledig, final RechtsgrondAttribuut rechtsgrondAttribuut) {
        ReflectionTestUtils.setField(actieBronHisVolledig, "rechtsgrond", rechtsgrondAttribuut);
    }

    private void zetRechtsgrondOmschrijvingOpActieBron(final ActieBronHisVolledigImpl actieBronHisVolledig,
        final OmschrijvingEnumeratiewaardeAttribuut rechtsgrondomschrijving)
    {
        ReflectionTestUtils.setField(actieBronHisVolledig, "rechtsgrondomschrijving", rechtsgrondomschrijving);
    }

    private void zetIdOpElementIdentificeerbaar(final ElementIdentificeerbaar elementIdentificeerbaar, final Number id) {
        ReflectionTestUtils.setField(elementIdentificeerbaar, "iD", id);
    }

}
