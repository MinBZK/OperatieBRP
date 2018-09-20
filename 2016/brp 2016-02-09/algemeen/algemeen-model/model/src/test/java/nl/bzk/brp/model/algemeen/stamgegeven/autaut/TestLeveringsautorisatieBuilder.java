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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
public class TestLeveringsautorisatieBuilder {

    private Stelsel        stelsel = Stelsel.BRP;
    private JaNeeAttribuut indicatieModelautorisatie;
    private NaamEnumeratiewaardeAttribuut naam = new NaamEnumeratiewaardeAttribuut("test-leveringsautorisatie");
    private Protocolleringsniveau           protocolleringsniveau;
    private JaNeeAttribuut                  indicatieAliasSoortAdministratieveHandelingLeveren;
    private DatumAttribuut                  datumIngang = DatumAttribuut.gisteren();
    private DatumAttribuut                  datumEinde = DatumAttribuut.morgen();
    private PopulatiebeperkingAttribuut     populatiebeperking;
    private NeeAttribuut                    indicatiePopulatiebeperkingVolledigGeconverteerd;
    private OnbeperkteOmschrijvingAttribuut toelichting;
    private JaAttribuut                     indicatieGeblokkeerd;
    private Integer                         id = (int) (Math.random() * 100000);
    private Set<Dienstbundel>               dienstbundels;

    public static int GELDIGE_DIENST_ID = 1;

    private TestLeveringsautorisatieBuilder() {
    }

    public static TestLeveringsautorisatieBuilder maker() {
        return new TestLeveringsautorisatieBuilder();
    }

    public TestLeveringsautorisatieBuilder metStelsel(Stelsel stelsel) {
        this.stelsel = stelsel;
        return this;
    }

    public TestLeveringsautorisatieBuilder metIdicatieModelautorisatie() {
        this.indicatieModelautorisatie = indicatieModelautorisatie;
        return this;
    }

    public TestLeveringsautorisatieBuilder metNaam(String naam) {
        this.naam = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public TestLeveringsautorisatieBuilder metProtocolleringsniveau(Protocolleringsniveau protocolleringsniveau) {
        this.protocolleringsniveau = protocolleringsniveau;
        return this;
    }

    public TestLeveringsautorisatieBuilder metIndicatieAliasSoortAdministratieveHandelingenLeveren(JaNeeAttribuut attribuut) {
        this.indicatieAliasSoortAdministratieveHandelingLeveren = attribuut;
        return this;
    }


    public TestLeveringsautorisatieBuilder metDatumIngang(DatumAttribuut datumAttribuut) {
        this.datumIngang = datumAttribuut;
        return this;
    }


    public TestLeveringsautorisatieBuilder metDatumEinde(DatumAttribuut datumAttribuut) {
        this.datumEinde = datumAttribuut;
        return this;
    }

    public TestLeveringsautorisatieBuilder metIndicatiePopulatiebeperkingVolledigGeconverteerd(NeeAttribuut attribuut) {
        this.indicatiePopulatiebeperkingVolledigGeconverteerd = attribuut;
        return this;
    }

    public TestLeveringsautorisatieBuilder metPopulatiebeperking(PopulatiebeperkingAttribuut attribuut) {
        this.populatiebeperking = attribuut;
        return this;
    }

    public TestLeveringsautorisatieBuilder metPopulatiebeperking(String populatieBeperking) {
        this.populatiebeperking = new PopulatiebeperkingAttribuut(populatieBeperking);
        return this;
    }

    public TestLeveringsautorisatieBuilder metToelichting(OnbeperkteOmschrijvingAttribuut attribuut) {
        this.toelichting = attribuut;
        return this;
    }

    public TestLeveringsautorisatieBuilder metIndicatieGeblokkerd(JaAttribuut attribuut) {
        this.indicatieGeblokkeerd = attribuut;
        return this;
    }

    public TestLeveringsautorisatieBuilder metId(final int id) {
        this.id = id;
        return this;
    }

    public TestLeveringsautorisatieBuilder metDienstbundels(final Dienstbundel ... dienstbundels) {
        this.dienstbundels = new HashSet<>(Arrays.asList(dienstbundels));
        return this;
    }


    public Leveringsautorisatie maak() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(stelsel, indicatieModelautorisatie, naam, protocolleringsniveau,
            indicatieAliasSoortAdministratieveHandelingLeveren, datumIngang, datumEinde, populatiebeperking,
            indicatiePopulatiebeperkingVolledigGeconverteerd, toelichting, indicatieGeblokkeerd);

        if(this.id != null) {
            TestPersoonIdZetter.zetId(leveringsautorisatie, id);
        } else {
            TestPersoonIdZetter.zetRandomIntegerId(leveringsautorisatie);
        }
        if(dienstbundels != null) {
            leveringsautorisatie.setDienstbundels(dienstbundels.toArray(new Dienstbundel[dienstbundels.size()]));
            for (Dienstbundel dienstbundel : dienstbundels) {
                ReflectionTestUtils.setField(dienstbundel, "leveringsautorisatie", leveringsautorisatie);
            }
        }
        return leveringsautorisatie;
    }

    public static Leveringsautorisatie metOngeldigeDienst(String populatieBeperking, SoortDienst soortDienst) {
        return maakDienst(populatieBeperking, null, soortDienst, true);
    }

    public static Leveringsautorisatie metDienst(SoortDienst soortDienst) {
        return metDienst("WAAR", soortDienst);
    }

    public static Leveringsautorisatie metDienst(String populatieBeperking, SoortDienst soortDienst) {
        return metDienst(populatieBeperking, null, soortDienst);
    }

    public static Leveringsautorisatie metDienst(String populatieBeperking, String naderePopulatiebeperking, SoortDienst soortDienst) {
        return maakDienst(populatieBeperking, naderePopulatiebeperking, soortDienst, false);
    }

    private static Leveringsautorisatie maakDienst(final String populatieBeperking, final String naderePopulatiebeperking, final SoortDienst
        soortDienst, final boolean geblokkeerd) {
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).metId(GELDIGE_DIENST_ID).
            metDatumIngang(DatumAttribuut.gisteren()). metDatumEinde(DatumAttribuut.morgen()).metIndicatieGeblokkeerd(geblokkeerd).maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).metIndicatieGeblokkeerd(geblokkeerd)
            .metNaderePopulatiebeperking(naderePopulatiebeperking)
            .maak();
        return TestLeveringsautorisatieBuilder.maker().
            metDatumIngang(DatumAttribuut.gisteren()).
            metPopulatiebeperking(populatieBeperking).
            metDienstbundels(dienstbundel).maak();
    }

    public static Leveringsautorisatie zonderGeldigeDienst() {
        return zonderGeldigeDienst(null);
    }

    public static Leveringsautorisatie zonderGeldigeDienst(String populatieBeperking) {
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten().maak();
        return TestLeveringsautorisatieBuilder.maker().metPopulatiebeperking(populatieBeperking).metDienstbundels(dienstbundel).maak();
    }
}
