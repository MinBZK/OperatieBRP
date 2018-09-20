/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonIdZetter;

/**
 * Builder om {@link ToegangBijhoudingsautorisatie} objecten mee te instantieren.
 */
public class TestToegangBijhoudingsautorisatieBuilder {

    private PartijRol                                                 geautoriseerde;
    private Partij                                                    ondertekenaar;
    private Partij                                                    transporteur;
    private DatumAttribuut                                            datumIngang;
    private DatumAttribuut                                            datumEinde;
    private JaAttribuut                                               indicatieGeblokkeerd;
    private Integer                                                   id;
    private List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen;

    private TestToegangBijhoudingsautorisatieBuilder() {
    }

    public static TestToegangBijhoudingsautorisatieBuilder maker() {
        return new TestToegangBijhoudingsautorisatieBuilder();
    }

    public TestToegangBijhoudingsautorisatieBuilder metDatumIngang(DatumAttribuut datumIngang) {
        this.datumIngang = datumIngang;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metDatumEinde(DatumAttribuut datumEinde) {
        this.datumEinde = datumEinde;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metGeautoriseerde(final PartijRol geautoriseerde) {
        this.geautoriseerde = geautoriseerde;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metOndertekenaar(final Partij ondertekenaar) {
        this.ondertekenaar = ondertekenaar;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metTransporteur(final Partij transporteur) {
        this.transporteur = transporteur;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metDummyGeautoriseerde() {
        this.geautoriseerde = new PartijRol(TestPartijBuilder.maker().metNaam("DUMMY").metCode(1).maak(), Rol.DUMMY, null, null);
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metId(final int id) {
        this.id = id;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metIndicatieGeblokkeerd(final boolean indicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = indicatieGeblokkeerd ? new JaAttribuut(Ja.J) : null;
        return this;
    }

    public TestToegangBijhoudingsautorisatieBuilder metGeautoriseerdeHandelingen(final List<BijhoudingsautorisatieSoortAdministratieveHandeling>
        geautoriseerdeHandelingen)
    {
        this.geautoriseerdeHandelingen = geautoriseerdeHandelingen;
        return this;
    }

    public ToegangBijhoudingsautorisatie maak() {
        final ToegangBijhoudingsautorisatie tba = new ToegangBijhoudingsautorisatie(geautoriseerde,
            ondertekenaar, transporteur, datumIngang, datumEinde, indicatieGeblokkeerd, geautoriseerdeHandelingen);
        if (this.id != null) {
            TestPersoonIdZetter.zetId(tba, id);
        } else {
            TestPersoonIdZetter.zetRandomIntegerId(tba);
        }
        return tba;
    }
}
