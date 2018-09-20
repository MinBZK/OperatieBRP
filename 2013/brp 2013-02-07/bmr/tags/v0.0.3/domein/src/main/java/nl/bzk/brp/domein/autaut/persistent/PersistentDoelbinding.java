/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut.persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.autaut.persistent.basis.AbstractPersistentDoelbinding;
import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.SoortAbonnement;


@Entity
@Table(name = "Doelbinding", schema = "AutAut")
public class PersistentDoelbinding extends AbstractPersistentDoelbinding implements Doelbinding {

    /**
     * Creëer een abonnement bij deze doelbinding.
     *
     * @param soortAbonnement het soort abonnement dat gecreëerd wordt.
     * @return het abonnement dat gecreëerd is.
     */
    @Override
    public Abonnement createAbonnement(final SoortAbonnement soortAbonnement) {
        if (soortAbonnement == null) {
            throw new IllegalArgumentException("soortAbonnement mag niet <null> zijn");
        }
        Abonnement resultaat = new PersistentDomeinObjectFactory().createAbonnement();
        resultaat.setSoortAbonnement(soortAbonnement);
        addAbonnement(resultaat);
        return resultaat;
    }
}
