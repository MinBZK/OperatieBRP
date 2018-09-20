/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.math.BigDecimal;

/*
 * CHECKSTYLE:OFF Deze class is indirect afgeleid gegenereerd o.b.v. het BRP datamodel en bevat daarom geen javadoc.
 */
/**
 *
 */
public interface MaterieleHistorie extends FormeleHistorie {
    /**
     * @return
     */
    BigDecimal getDatumAanvangGeldigheid();

    /**
     * @param datumAanvangGeldigheid
     */
    void setDatumAanvangGeldigheid(final BigDecimal datumAanvangGeldigheid);

    /**
     * @return
     */
    BigDecimal getDatumEindeGeldigheid();

    /**
     * @param datumEindeGeldigheid
     */
    void setDatumEindeGeldigheid(final BigDecimal datumEindeGeldigheid);

    /**
     * @return
     */
    BRPActie getActieAanpassingGeldigheid();

    /**
     * @param actieAanpassingGeldigheid
     */
    void setActieAanpassingGeldigheid(final BRPActie actieAanpassingGeldigheid);
}
