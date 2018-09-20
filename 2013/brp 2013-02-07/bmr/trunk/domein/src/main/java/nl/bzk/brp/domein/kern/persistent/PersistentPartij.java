/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.kern.persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.PersistentDomeinObjectFactory;
import nl.bzk.brp.domein.autaut.Autorisatiebesluit;
import nl.bzk.brp.domein.autaut.Doelbinding;
import nl.bzk.brp.domein.kern.Partij;
import nl.bzk.brp.domein.kern.persistent.basis.AbstractPersistentPartij;


@Entity
@Table(name = "Partij", schema = "Kern")
public class PersistentPartij extends AbstractPersistentPartij implements Partij {

    /**
     * Creëer een nieuwe doelbinding voor deze partij en voeg deze toe aan de verzameling doelbindingen.
     *
     * @param autorisatieBesluit het autorisatiebesluit dat aan deze partij wordt gebonden.
     * @return de doelbinding die gecreëerd wordt.
     */
    @Override
    public Doelbinding createDoelbinding(final Autorisatiebesluit autorisatieBesluit) {
        if (autorisatieBesluit == null) {
            throw new IllegalArgumentException("autorisatieBesluit is een verplichte paramter");
        }
        Doelbinding resultaat = new PersistentDomeinObjectFactory().createDoelbinding();
        resultaat.setLeveringsautorisatiebesluit(autorisatieBesluit);
        resultaat.setGeautoriseerde(this);
        addDoelbinding(resultaat);
        return resultaat;
    }
}
