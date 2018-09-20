/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.logisch.basis.PersoonAdresBasis;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonAdresModel;
import org.springframework.util.CollectionUtils;


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

    public PersoonAdresModel(final PersoonAdresHisModel hisModel) {
        super();

        PersoonAdresStandaardGroepModel groep = new PersoonAdresStandaardGroepModel(hisModel);
        setGegevens(groep);
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

    @Override
    public void vervangGroepen(final AbstractGroep ... groepen) {
        super.vervangGroepen(groepen);
    }


    /**
     * Controlleert of adres historie heeft.
     *
     * @return true als adres historie heeft
     */
    public boolean heeftGeenHistorie() {
        boolean geenHistorie;

        if (CollectionUtils.isEmpty(getHistorie())) {
            geenHistorie = true;
        } else {
            geenHistorie = false;
        }

        return geenHistorie;
    }

    @Override
    public PersoonAdresModel kopieer() {
        return new PersoonAdresModel(this, getPersoon());
    }
}
