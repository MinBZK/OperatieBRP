/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ReflectionUtils;

/**
 */
public class TestDienstBuilder {

    private Dienstbundel            dienstbundel;
    private SoortDienst             soort;
    private EffectAfnemerindicaties effectAfnemerindicaties;
    private DatumAttribuut datumIngang = DatumAttribuut.gisteren();
    private DatumAttribuut                    datumEinde;
    private JaAttribuut                       indicatieGeblokkeerd;
    private AttenderingscriteriumAttribuut    attenderingscriterium;
    private DatumAttribuut                    eersteSelectiedatum;
    private SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden;

    private boolean dummyBundel;
    private Integer  id;

    private TestDienstBuilder() {
    }

    public TestDienstBuilder metSoortDienst(SoortDienst soortDienst) {
        this.soort = soortDienst;
        return this;
    }

    public TestDienstBuilder metDatumIngang(DatumAttribuut datumIngang) {
        this.datumIngang = datumIngang;
        return this;
    }

    public TestDienstBuilder metDatumEinde(DatumAttribuut datumEinde) {
        this.datumEinde = datumEinde;
        return this;
    }

    public TestDienstBuilder metId(Integer id) {
        this.id = id;
        return this;
    }


    public TestDienstBuilder metAttenderingscriterium(String attenderingscriterium) {
        this.attenderingscriterium = new AttenderingscriteriumAttribuut(attenderingscriterium);
        return this;
    }

    public TestDienstBuilder metEffectAfnemerindicaties(EffectAfnemerindicaties effectAfnemerindicaties) {
        this.effectAfnemerindicaties = effectAfnemerindicaties;
        return this;
    }


    public TestDienstBuilder metIndicatieGeblokkeerd(boolean indicatieGeblokkeerd) {
        if (indicatieGeblokkeerd) {
            this.indicatieGeblokkeerd = new JaAttribuut(Ja.J);
        }
        return this;
    }

    public static TestDienstBuilder maker() {
        return new TestDienstBuilder();
    }

    public static Dienst dummy() {
        return new TestDienstBuilder().metSoortDienst(SoortDienst.DUMMY).maak();
    }

    public TestDienstBuilder metDummyBundel() {
        dummyBundel = true;
        return this;
    }

    public TestDienstBuilder metDienstbundel(Dienstbundel dienstbundel) {
        this.dienstbundel = dienstbundel;
       return this;
    }


    public Dienst maak() {
        final Dienst dienst = new Dienst(dienstbundel, soort, effectAfnemerindicaties, datumIngang, datumEinde, indicatieGeblokkeerd,
            attenderingscriterium, eersteSelectiedatum, selectieperiodeInMaanden);

        if(this.id != null) {
            TestPersoonIdZetter.zetId(dienst, id);
        } else {
            TestPersoonIdZetter.zetRandomIntegerId(dienst);
        }
        if (dummyBundel) {

            Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
            Dienstbundel dienstbundel = new Dienstbundel();
            ReflectionTestUtils.setField(dienstbundel, "leveringsautorisatie", la);
            ReflectionTestUtils.setField(dienst, "dienstbundel", dienstbundel);

        }
        return dienst;

    }
}
