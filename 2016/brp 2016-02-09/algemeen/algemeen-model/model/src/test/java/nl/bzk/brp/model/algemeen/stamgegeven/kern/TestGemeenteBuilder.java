/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Builder voor unittests waar we een Gemeente nodig hebben.
 */
public class TestGemeenteBuilder {

    private Short id;
    private NaamEnumeratiewaardeAttribuut naamEnumeratiewaardeAttribuut;
    private GemeenteCodeAttribuut gemeenteCodeAttribuut;
    private Partij partij;
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;
    private DatumEvtDeelsOnbekendAttribuut datumEinde;


    private TestGemeenteBuilder() {
        // instantieren via de statische fabrieksmethode
    }

    public static TestGemeenteBuilder maker() {
        return new TestGemeenteBuilder();
    }

    public TestGemeenteBuilder metId(final Integer id) {
        this.id = id == null ? null : id.shortValue();
        return this;
    }

    public TestGemeenteBuilder metNaam(final String naam) {
        this.naamEnumeratiewaardeAttribuut = new NaamEnumeratiewaardeAttribuut(naam);
        return this;
    }

    public TestGemeenteBuilder metCode(final GemeenteCodeAttribuut code) {
        this.gemeenteCodeAttribuut = code;
        return this;
    }

    public TestGemeenteBuilder metCode(final Integer code) {
        return metCode(new GemeenteCodeAttribuut(Integer.valueOf(code).shortValue()));
    }

    public TestGemeenteBuilder metPartij(final Partij partij) {
        this.partij = partij;
        return this;
    }

    public TestGemeenteBuilder metDatumAanvang(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumAanvang = datum;
        return this;
    }

    public TestGemeenteBuilder metDatumAanvang(final Integer datum) {
        return metDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(datum));
    }

    public TestGemeenteBuilder metDatumEinde(final DatumEvtDeelsOnbekendAttribuut datum) {
        this.datumEinde = datum;
        return this;
    }

    public TestGemeenteBuilder metDatumEinde(final Integer datum) {
        return metDatumEinde(new DatumEvtDeelsOnbekendAttribuut(datum));
    }

    public Gemeente maak() {
        Gemeente gemeente = new Gemeente(naamEnumeratiewaardeAttribuut, gemeenteCodeAttribuut, partij, null, datumAanvang, datumEinde);
        if (id != null) {
            ReflectionTestUtils.setField(gemeente, "iD", id);
        }
        return gemeente;
    }

}
