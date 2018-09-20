/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity;

import java.sql.Timestamp;
import java.util.Comparator;

/**
 * 
 */
public interface FormeleHistorie {

    /**
     * Sorteert de lijst van formelehistorie o.b.v. de datumtijd registratie van oud naar nieuw.
     */
    Comparator<FormeleHistorie> COMPARATOR = new Comparator<FormeleHistorie>() {

        @Override
        public int compare(final FormeleHistorie historie1, final FormeleHistorie historie2) {
            return historie1.getDatumTijdRegistratie().compareTo(historie2.getDatumTijdRegistratie());
        }
    };

    /**
     * @return datum tijd registratie
     */
    Timestamp getDatumTijdRegistratie();

    /**
     * @param datumTijdRegistratie
     *            de datumtijd registratie die gezet moet worden
     */
    void setDatumTijdRegistratie(final Timestamp datumTijdRegistratie);

    /**
     * @return datum tijd verval
     */
    Timestamp getDatumTijdVerval();

    /**
     * @param datumTijdVerval
     *            datum/tijd verval voor dit record
     */
    void setDatumTijdVerval(final Timestamp datumTijdVerval);

    /**
     * @return de actie inhoud
     */
    BRPActie getActieInhoud();

    /**
     * @param actieInhoud
     *            de actieinhoud voor dit record
     */
    void setActieInhoud(final BRPActie actieInhoud);

    /**
     * @return de actie verval
     */
    BRPActie getActieVerval();

    /**
     * @param actieVerval
     *            actie verval
     */
    void setActieVerval(final BRPActie actieVerval);
}
