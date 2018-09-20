/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.lev;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.lev.basis.AbstractAbonnementGegevenselement;


/**
 * Het voorkomen van een gegevenselement in een Abonnement.
 *
 * Bij het Abonnement wordt vastgelegd welke Gegevenselementen in het kader van het Abonnement geraakt mogen worden. Het
 * betreft een deelverzameling van de Gegevenselement doelbinding.
 *
 * 1. Bij de naamskeuze van het objecttype sluiten we aan bij de standaard, en dus kiezen we niet voor de naam
 * "Gegevenselement [van het] Abonnement".
 *
 * 2. Bij de naam '(Gegevens)Element' is de vraag welke elementen worden bedoeld: zijn dit de LOGISCHE elementen, of de
 * representanten hiervan in de database, de DATABASE OBJECTEN.
 * Voor een aantal objecttypen is de hypothese waaronder gewerkt wordt dat het de DATABASE OBJECTEN zijn. Om deze
 * duidelijk te kunnen scheiden van (andere) Elementen, hebben deze een aparte naam gekregen: Databaseobject.
 * In de verwijzing van het attribuut gebruiken we echter nog de naam 'Element': een Databaseobject zou immers kunnen
 * worden beschouwd als een specialisatie van Element. Alleen is die specialisatie in het model niet uitgemodelleerd.
 * RvdP 16 november 2011.
 *
 *
 *
 */
@Entity
@Table(schema = "Lev", name = "AbonnementGegevenselement")
public class AbonnementGegevenselement extends AbstractAbonnementGegevenselement {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbonnementGegevenselement() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param abonnement abonnement van AbonnementGegevenselement.
     * @param gegevenselement gegevenselement van AbonnementGegevenselement.
     */
    protected AbonnementGegevenselement(final Abonnement abonnement, final DatabaseObject gegevenselement) {
        super(abonnement, gegevenselement);
    }

}
