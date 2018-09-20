/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

/**
 * Interface voor rijen in BRP met materiële historie (datum aanvang/einde geldigheid).
 */
public interface MaterieleHistorie extends FormeleHistorie {

    /**
     * Geef de waarde van datum aanvang geldigheid.
     *
     * @return datum aanvang geldigheid
     */
    Integer getDatumAanvangGeldigheid();

    /**
     * Zet de waarde van datum aanvang geldigheid.
     *
     * @param datumAanvangGeldigheid
     *            datum aanvang geldigheid
     */
    void setDatumAanvangGeldigheid(Integer datumAanvangGeldigheid);

    /**
     * Geef de waarde van datum einde geldigheid.
     *
     * @return datum einde geldigheid
     */
    Integer getDatumEindeGeldigheid();

    /**
     * Zet de waarde van datum einde geldigheid.
     *
     * @param datumEindeGeldigheid
     *            datum einde geldigheid
     */
    void setDatumEindeGeldigheid(Integer datumEindeGeldigheid);

    /**
     * Geef de waarde van actie aanpassing geldigheid.
     *
     * @return actie aanpassing geldigheid
     */
    BRPActie getActieAanpassingGeldigheid();

    /**
     * Zet de waarde van actie aanpassing geldigheid.
     *
     * @param actieAanpassingGeldigheid
     *            actie aanpassing geldigheid
     */
    void setActieAanpassingGeldigheid(BRPActie actieAanpassingGeldigheid);
}
