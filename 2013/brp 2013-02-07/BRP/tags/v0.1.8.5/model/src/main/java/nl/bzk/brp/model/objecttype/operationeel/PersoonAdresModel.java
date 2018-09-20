/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonAdresBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonAdresModel;

/**
 *
 */
@Entity
@Table(schema = "Kern", name = "PersAdres")
@SuppressWarnings("serial")
public class PersoonAdresModel extends AbstractPersoonAdresModel implements PersoonAdres {

    /**
     * Constructor die op basis van een (blauwdruk) object een nieuwe instantie creeert en alle velden direct
     * initialiseert naar de waardes uit het opgegeven (blauwdruk) object.
     *
     * @param adres het adres waaruit de initiele waardes worden overgenomen.
     * @param pers de persoon waarvoor het adres geldt.
     */
    public PersoonAdresModel(final PersoonAdresBasis adres, final PersoonModel pers) {
        super(adres, pers);
    }

    /**
     * Standaard (lege) constructor.
     */
    @SuppressWarnings("unused")
    private PersoonAdresModel() {
    }

    @Override
    public void vervang(final AbstractPersoonAdresModel nieuwAdres) {
        super.vervang(nieuwAdres);
    }
}
