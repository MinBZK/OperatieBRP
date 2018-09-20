/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder voor unittests waar we een Partij nodig hebben.
 */
public class TestPartijBuilder {

    private Short                         id;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;
    private SoortPartijAttribuut          soortPartij;
    private PartijCodeAttribuut partijCodeAttribuut = new PartijCodeAttribuut((int) (Math.random() * 1000));
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;
    private DatumEvtDeelsOnbekendAttribuut datumEinde;
    private OINAttribuut                   oIN;
    private JaNeeAttribuut                 indicatieVerstrekkingsbeperkingMogelijk;
    private JaNeeAttribuut                 indicatieAutomatischFiatteren;
    private DatumAttribuut                 datumOvergangNaarBRP;

    private TestPartijBuilder() {
    }

    public static TestPartijBuilder maker() {
        return new TestPartijBuilder();
    }

    public TestPartijBuilder metId(final int id) {
        this.id = (short) id;
        return this;
    }

    public TestPartijBuilder metNaam(final NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut) {
        this.naamEnumeratiewaardeAttribuut = naamEnumeratiewaardeAttribuut;
        return this;
    }

    public TestPartijBuilder metNaam(final String abonnementNaam) {
        this.naamEnumeratiewaardeAttribuut = new NaamEnumeratiewaardeAttribuut(abonnementNaam);
        return this;
    }

    public TestPartijBuilder metSoort(final SoortPartij soort) {
        this.soortPartij = new SoortPartijAttribuut(soort);
        return this;
    }

    public TestPartijBuilder metOin(final OINAttribuut oIN) {
        this.oIN = oIN;
        return this;
    }

    public TestPartijBuilder metDatumOvergangNaarBrp(final DatumAttribuut attribuut) {
        this.datumOvergangNaarBRP = attribuut;
        return this;
    }

    public TestPartijBuilder metIndicatieAutomatischFiatteren(final JaNeeAttribuut attribuut) {
        this.indicatieAutomatischFiatteren = attribuut;
        return this;
    }

    public TestPartijBuilder metCode(final PartijCodeAttribuut code) {
        this.partijCodeAttribuut = code;
        return this;
    }

    public TestPartijBuilder metCode(final Integer partijCode) {
        this.partijCodeAttribuut = new PartijCodeAttribuut(partijCode);
        return this;
    }

    public TestPartijBuilder metIndicatieVerstrekkingsbeperkingMogelijk(final JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk) {
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        return this;
    }

    public TestPartijBuilder metDatumAanvang(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumAanvang = datum;
        return this;
    }

    public TestPartijBuilder metDatumAanvang(final Integer datum) {
        return metDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(datum));
    }

    public TestPartijBuilder metDatumEinde(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumEinde = datum;
        return this;
    }

    public TestPartijBuilder metDatumEinde(final Integer datum) {
        return metDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datum));
    }

    public final Partij maak() {
        final Partij partij = new Partij(naamEnumeratiewaardeAttribuut, soortPartij, partijCodeAttribuut, datumAanvang, datumEinde, oIN,
            indicatieVerstrekkingsbeperkingMogelijk, indicatieAutomatischFiatteren, datumOvergangNaarBRP);
        if (id != null) {
            ReflectionTestUtils.setField(partij, "iD", id);
        }
        return partij;
    }

}
