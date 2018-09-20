/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

import java.util.UUID;
import nl.bzk.brp.model.FormeleHistoriePredikaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.bericht.kern.OnderzoekStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekComparator;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonOnderzoekHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import org.apache.commons.collections.Predicate;
import org.junit.Assert;
import org.junit.Test;

public class PersoonOnderzoekComparatorTest {

    @Test
    public void testResultaatIsGelijk() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final OnderzoekHisVolledigImpl onderzoekHisVolledig1 = new OnderzoekHisVolledigImpl();
        final OnderzoekHisVolledigImpl onderzoekHisVolledig2 = new OnderzoekHisVolledigImpl();
        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = new PersoonOnderzoekHisVolledigImpl(persoonHisVolledig, onderzoekHisVolledig1);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = new PersoonOnderzoekHisVolledigImpl(persoonHisVolledig, onderzoekHisVolledig2);

        final int resultaat = new PersoonOnderzoekComparator().compare(persOnderzoek1, persOnderzoek2);
        Assert.assertEquals(0, resultaat);
    }

    @Test
    public void testResultaatisGroter() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20130101, null);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20120101, null);

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, formeleHistoriePredikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, formeleHistoriePredikaat));
        Assert.assertEquals(1, resultaat);
    }

    @Test
    public void testResultaatisGroterAlsDatumAanvangIsGelijk() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20130101,
            new DatumEvtDeelsOnbekendAttribuut(20140102));
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101,
            new DatumEvtDeelsOnbekendAttribuut(20140101));

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, formeleHistoriePredikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, formeleHistoriePredikaat));
        Assert.assertEquals(1, resultaat);
    }

    @Test
    public void testResultaatisGroterAlsDatumAanvangEnVerwachteAfhandelDatumZijnGelijk() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20130101,
            new DatumEvtDeelsOnbekendAttribuut(20140101));
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101,
            new DatumEvtDeelsOnbekendAttribuut(20140101));

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, formeleHistoriePredikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, formeleHistoriePredikaat));
        assertThat(resultaat, is(not(0)));
    }

    @Test
    public void testResultaatisKleiner() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20120101, null);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101, null);

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, formeleHistoriePredikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, formeleHistoriePredikaat));
        Assert.assertEquals(-1, resultaat);
    }

    @Test
    public void testRecord1EnRecord2Null() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20120101, null);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101, null);

        final Predicate predikaat = new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return false;
            }
        };

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, predikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, predikaat));
        Assert.assertEquals(0, resultaat);
    }

    @Test
    public void testRecord1IsNull() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20120101, null);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101, null);

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);
        final Predicate predikaat = new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return false;
            }
        };

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, predikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, formeleHistoriePredikaat));
        Assert.assertEquals(-1, resultaat);
    }

    @Test
    public void testRecord2IsNull() {
        final PersoonHisVolledigImpl persoonHisVolledig = TestPersoonAntwoordPersoon.maakAntwoordPersoon();

        final PersoonOnderzoekHisVolledigImpl persOnderzoek1 = maakPersoonOnderzoek(persoonHisVolledig, 20120101, null);
        final PersoonOnderzoekHisVolledigImpl persOnderzoek2 = maakPersoonOnderzoek(persoonHisVolledig, 20130101, null);

        final DatumTijdAttribuut nu = DatumTijdAttribuut.nu();
        final FormeleHistoriePredikaat formeleHistoriePredikaat = FormeleHistoriePredikaat.bekendOp(nu);
        final Predicate predikaat = new Predicate() {
            @Override
            public boolean evaluate(final Object object) {
                return false;
            }
        };

        final int resultaat = new PersoonOnderzoekComparator().compare(
            new PersoonOnderzoekHisVolledigView(persOnderzoek1, formeleHistoriePredikaat),
            new PersoonOnderzoekHisVolledigView(persOnderzoek2, predikaat));
        Assert.assertEquals(1, resultaat);
    }

    private PersoonOnderzoekHisVolledigImpl maakPersoonOnderzoek(final PersoonHisVolledigImpl persoonHisVolledig, final int datumAanvang,
            final DatumEvtDeelsOnbekendAttribuut verwachteAfhandelDatum) {
        final OnderzoekHisVolledigImpl onderzoekHisVolledig = new OnderzoekHisVolledigImpl();
        final PersoonOnderzoekHisVolledigImpl persOnderzoek = new PersoonOnderzoekHisVolledigImpl(persoonHisVolledig, onderzoekHisVolledig);

        final OnderzoekStandaardGroepBericht onderzoekStandaardGroep = new OnderzoekStandaardGroepBericht();
        onderzoekStandaardGroep.setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        onderzoekStandaardGroep.setVerwachteAfhandeldatum(verwachteAfhandelDatum);
        onderzoekStandaardGroep.setOmschrijving(new OnderzoekOmschrijvingAttribuut(UUID.randomUUID().toString()));
        onderzoekHisVolledig.getOnderzoekHistorie().voegToe(new HisOnderzoekModel(onderzoekHisVolledig, onderzoekStandaardGroep,
            new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null)));

        return persOnderzoek;
    }
}
