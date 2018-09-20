/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.basis.FamilierechtelijkeBetrekkingBasis;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;


/**
 * De familierechtelijke betrekking tussen het Kind enerzijds, en zijn/haar ouders anderzijds.
 *
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft g��n invloed op de familierechtelijke betrekking zelf: het blijft ��n en dezelfde Relatie.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractFamilierechtelijkeBetrekkingModel extends RelatieModel implements
        FamilierechtelijkeBetrekkingBasis
{

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public AbstractFamilierechtelijkeBetrekkingModel() {
        super(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param familierechtelijkeBetrekking Te kopieren object type.
     */
    public AbstractFamilierechtelijkeBetrekkingModel(final FamilierechtelijkeBetrekking familierechtelijkeBetrekking) {
        super(familierechtelijkeBetrekking);

    }

}
