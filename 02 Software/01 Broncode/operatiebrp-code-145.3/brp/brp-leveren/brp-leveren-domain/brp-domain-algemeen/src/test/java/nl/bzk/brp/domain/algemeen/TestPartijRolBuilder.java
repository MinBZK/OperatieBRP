/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * TestPartijRolBuilder
 */
public class TestPartijRolBuilder {

    private Integer id;
    private Partij partij;
    private Rol rol;
    private Integer datumIngang;
    private Integer datumEinde;
    private boolean actueelEnGeldig = true;

    private TestPartijRolBuilder() {
    }

    public static TestPartijRolBuilder maker() {
        return new TestPartijRolBuilder();
    }

    public TestPartijRolBuilder metId(final int id) {
        this.id = id;
        return this;
    }

    public TestPartijRolBuilder metDatumIngang(final Integer datumIngang) {
        this.datumIngang = datumIngang;
        return this;
    }

    public TestPartijRolBuilder metDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
        return this;
    }

    public TestPartijRolBuilder metRol(final Rol rol) {
        this.rol = rol;
        return this;
    }

    public TestPartijRolBuilder metPartij(final Partij partij) {
        this.partij = partij;
        return this;
    }

    public TestPartijRolBuilder metActueelEnGeldig(boolean actueelEnGeldig) {
        this.actueelEnGeldig = actueelEnGeldig;
        return this;
    }

    public final PartijRol maak() {
        final PartijRol partijRol = new PartijRol(partij, rol);
        partijRol.setDatumIngang(datumIngang);
        partijRol.setDatumEinde(datumEinde);
        partijRol.setActueelEnGeldig(actueelEnGeldig);
        if (id != null) {
            ReflectionTestUtils.setField(partijRol, "id", id);
        }
        return partijRol;
    }

}
