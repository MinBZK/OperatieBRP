/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;

/**
 */
public class TestToegangLeveringautorisatieBuilder {

    private PartijRol                   geautoriseerde;
    private Leveringsautorisatie        leveringsautorisatie;
    private Partij                      ondertekenaar;
    private Partij                      transporteur;
    private DatumAttribuut              datumIngang;
    private DatumAttribuut              datumEinde;
    private PopulatiebeperkingAttribuut naderePopulatiebeperking;
    private UriAttribuut                afleverpunt;
    private JaAttribuut                 indicatieGeblokkeerd;
    private Integer                     id;

    private TestToegangLeveringautorisatieBuilder() {
    }

    public static TestToegangLeveringautorisatieBuilder maker() {
        return new TestToegangLeveringautorisatieBuilder();
    }

    public TestToegangLeveringautorisatieBuilder metDatumIngang(DatumAttribuut datumIngang) {
        this.datumIngang = datumIngang;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metDatumEinde(DatumAttribuut datumEinde) {
        this.datumEinde = datumEinde;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        this.leveringsautorisatie = leveringsautorisatie;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metGeautoriseerde(final PartijRol geautoriseerde) {
        this.geautoriseerde = geautoriseerde;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metDummyGeautoriseerde() {
        this.geautoriseerde = new PartijRol(TestPartijBuilder.maker().metNaam("DUMMY").metCode(1).maak(), Rol.DUMMY, null, null);
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metAfleverpunt(final String uri) {
        if (uri != null) {
            this.afleverpunt = new UriAttribuut(uri);
        }
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metOndertekenaar(final Partij ondertekenaar) {
        this.ondertekenaar = ondertekenaar;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metTransporteur(final Partij transporteur) {
        this.transporteur = transporteur;
        return this;
    }


    public TestToegangLeveringautorisatieBuilder metId(final int id) {
        this.id = id;
        return this;
    }

    public TestToegangLeveringautorisatieBuilder metIndicatieGeblokkeerd(JaAttribuut attribuut) {
        this.indicatieGeblokkeerd = attribuut;
        return this;
    }

    public ToegangLeveringsautorisatie maak() {
        final ToegangLeveringsautorisatie tla = new ToegangLeveringsautorisatie(geautoriseerde, leveringsautorisatie,
            ondertekenaar, transporteur, datumIngang, datumEinde, naderePopulatiebeperking, afleverpunt, indicatieGeblokkeerd);
        if (this.id != null) {
            TestPersoonIdZetter.zetId(tla, id);
        } else {
            TestPersoonIdZetter.zetRandomIntegerId(tla);
        }
        return tla;
    }
}
