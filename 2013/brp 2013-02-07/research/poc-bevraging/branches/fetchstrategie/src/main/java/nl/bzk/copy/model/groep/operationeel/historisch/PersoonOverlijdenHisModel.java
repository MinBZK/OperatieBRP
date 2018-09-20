/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.historisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.copy.model.groep.operationeel.AbstractPersoonOverlijdenGroep;
import nl.bzk.copy.model.groep.operationeel.historisch.basis.AbstractPersoonOverlijdenHisModel;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;


/**
 * User implementatie van PersoonOpschortingHis.
 */
@Entity
@Table(schema = "kern", name = "his_persoverlijden")
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public class PersoonOverlijdenHisModel extends AbstractPersoonOverlijdenHisModel {

    /**
     * Standaard (lege) constructor.
     */
    protected PersoonOverlijdenHisModel() {
    }

    /**
     * Constructor die op basis van een (blauwdruk) groep een nieuwe instantie creeert en alle velden direct
     * initialiseert naar de waardes uit de opgegeven (blauwdruk) groep.
     *
     * @param persoonOverlijdenGroep de (blauwdruk) groep met de te gebruiken waardes.
     * @param persoonModel           de persoon waarvoor de overlijden groep wordt geinstantieerd.
     */
    public PersoonOverlijdenHisModel(final AbstractPersoonOverlijdenGroep persoonOverlijdenGroep,
                                     final PersoonModel persoonModel)
    {
        super(persoonOverlijdenGroep, persoonModel);
    }
}
