/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;

/**
 * Builder voor unittests waar we een Partij nodig hebben.
 */
public class TestPartijBuilder {

    private Short id;
    private String naam;
    private SoortPartij soortPartij;
    private String code;
    private Integer datumAanvang;
    private Integer datumEinde;
    private String oIN;
    private Boolean indicatieVerstrekkingsbeperkingMogelijk;
    private Boolean indicatieAutomatischFiatteren;
    private Integer datumOvergangNaarBRP;
    private Boolean actueelEnGeldig;

    private Rol rol;

    private TestPartijBuilder() {
    }

    public static TestPartijBuilder maakBuilder() {
        return new TestPartijBuilder();
    }

    public TestPartijBuilder metId(final int id) {
        this.id = (short) id;
        return this;
    }

    public TestPartijBuilder metNaam(final String naam) {
        this.naam = naam;
        return this;
    }

    public TestPartijBuilder metSoort(final SoortPartij soort) {
        this.soortPartij = soort;
        return this;
    }

    public TestPartijBuilder metOin(final String oIN) {
        this.oIN = oIN;
        return this;
    }

    public TestPartijBuilder metDatumOvergangNaarBrp(final Integer attribuut) {
        this.datumOvergangNaarBRP = attribuut;
        return this;
    }

    public TestPartijBuilder metIndicatieAutomatischFiatteren(final Boolean attribuut) {
        this.indicatieAutomatischFiatteren = attribuut;
        return this;
    }

    public TestPartijBuilder metCode(final String code) {
        this.code = code;
        return this;
    }

    public TestPartijBuilder metIndicatieVerstrekkingsbeperkingMogelijk(final Boolean indicatieVerstrekkingsbeperkingMogelijk) {
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        return this;
    }

    public TestPartijBuilder metDatumAanvang(final Integer datum) {
        this.datumAanvang = datum;
        return this;
    }

    public TestPartijBuilder metRol(final Rol rol) {
        this.rol = rol;
        return this;
    }

    public TestPartijBuilder metDatumEinde(final Integer datum) {
        this.datumEinde = datum;
        return this;
    }

    public TestPartijBuilder metActueelEnGeldig(final boolean actueelEnGeldig) {
        this.actueelEnGeldig = actueelEnGeldig;
        return this;
    }

    public final Partij build() {
        final Partij partij = new Partij(naam == null ? "dummy" : naam, code);
        partij.setDatumIngang(datumAanvang);
        partij.setDatumEinde(datumEinde);
        partij.setOin(oIN);
        partij.setSoortPartij(soortPartij);
        partij.setIndicatieVerstrekkingsbeperkingMogelijk(indicatieVerstrekkingsbeperkingMogelijk);
        partij.setIndicatieAutomatischFiatteren(indicatieAutomatischFiatteren);
        partij.setDatumOvergangNaarBrp(datumOvergangNaarBRP);
        partij.setActueelEnGeldig(actueelEnGeldig == null ? true : actueelEnGeldig);
        if (id != null) {
            partij.setId(id);
        }
        if (rol != null) {
            PartijRol partijRol = new PartijRol(partij, rol);
            Set<PartijRol> rollen = new HashSet<>();
            rollen.add(partijRol);
            partij.addPartijRol(partijRol);
        }
        return partij;
    }

}
