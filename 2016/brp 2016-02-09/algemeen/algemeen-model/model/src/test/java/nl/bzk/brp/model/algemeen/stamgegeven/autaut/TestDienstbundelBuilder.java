/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class TestDienstbundelBuilder {


    private Leveringsautorisatie            leveringsautorisatie;
    private NaamEnumeratiewaardeAttribuut   naam;
    private DatumAttribuut                  datumIngang;
    private DatumAttribuut                  datumEinde;
    private PopulatiebeperkingAttribuut     naderePopulatiebeperking;
    private NeeAttribuut                    indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    private OnbeperkteOmschrijvingAttribuut toelichting;
    private JaAttribuut                     indicatieGeblokkeerd;

    private Set<Dienst>            diensten;
    private Set<DienstbundelGroep> groepen;

    private TestDienstbundelBuilder() {

    }

    public static TestDienstbundelBuilder maker() {
        return new TestDienstbundelBuilder();
    }

    public TestDienstbundelBuilder metLeveringsautoriastie(final Leveringsautorisatie la) {
        this.leveringsautorisatie = la;
        return this;
    }

    public TestDienstbundelBuilder metDiensten(final Dienst... diensten) {
        this.diensten = new HashSet<>(Arrays.asList(diensten));
        return this;
    }

    public TestDienstbundelBuilder metIndicatieGeblokkeerd(boolean indicatieGeblokkeerd) {
        if (indicatieGeblokkeerd) {
            this.indicatieGeblokkeerd = new JaAttribuut(Ja.J);
        }
        return this;
    }

    public TestDienstbundelBuilder metGroepen(DienstbundelGroep... groepen) {
        this.groepen = new HashSet<>(Arrays.asList(groepen));
        return this;
    }

    public TestDienstbundelBuilder metNaderePopulatiebeperking(String naderePopulatiebeperking) {
        this.naderePopulatiebeperking = new PopulatiebeperkingAttribuut(naderePopulatiebeperking);
        return this;
    }
//
//    private class GroepEnAttributen {
//
//        private final String groep;
//        private final List<String> attributen;
//
//        public GroepEnAttributen(final String groep, final String[] attributen) {
//            this.groep = groep;
//            this.attributen = Arrays.asList(attributen);
//        }
//
//    }

    public Dienstbundel maak() {
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie, naam, datumIngang, datumEinde, naderePopulatiebeperking,
            indicatieNaderePopulatiebeperkingVolledigGeconverteerd, toelichting, indicatieGeblokkeerd);

        dienstbundel.setDiensten(diensten.toArray(new Dienst[diensten.size()]));
        if (diensten != null && !diensten.isEmpty()) {
            for (Dienst dienst : diensten) {
                ReflectionTestUtils.setField(dienst, "dienstbundel", dienstbundel);
            }
        }
        ReflectionTestUtils.setField(dienstbundel, "dienstbundelGroepen", groepen);
        if (groepen != null && !groepen.isEmpty()) {
            for (DienstbundelGroep groep : groepen) {
                ReflectionTestUtils.setField(groep, "dienstbundel", dienstbundel);
            }
        }

        return dienstbundel;
    }
}
