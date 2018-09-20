/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.logisch.ber.AdministratieveHandelingBijgehoudenPersoon;
import nl.bzk.brp.model.operationeel.ber.basis.AbstractAdministratieveHandelingBijgehoudenPersoonModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


/**
 * De bijhouding van gegevens over een persoon door middel van een administratieve handeling.
 * 
 * Bijhoudingen gebeuren doordat een administratieve handeling wordt verwerkt dat tot wijzigingen leid van
 * persoonsgegevens. Daar waar een administratieve handeling leidt tot een aanpassing van het veld datumtijdstip laatste
 * wijziging van die persoon, is er sprake van een "Administratieve handeling\Bijgehouden persoon". Meer informatie is
 * te vinden bij de beschrijving van de verwerkingsregel voor datumtijd laatste wijziging. Kort gezegd komt het neer op:
 * - 0e graads: dit zijn wijzigingen in het desbetreffende record van de tabel Kern.Pers zelf.
 * - 1e graads: dit zijn wijzigingen in een record van een tabel die verwijst naar �het desbetreffende record van de
 * tabel Kern.Pers�.
 * - 2e graads: dit zijn wijzigingen in een record van een tabel�
 * o � die verwijst naar �een record van een tabel die verwijst naar het desbetreffende record van de tabel Kern.Pers�
 * o � of waarnaar wordt verwezen door �een record van een tabel die verwijst naar het desbetreffende record van de
 * tabel Kern.Pers�.
 * 
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.4.6.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-18 10:50:13.
 * Gegenereerd op: Tue Dec 18 10:54:29 CET 2012.
 */
@Entity
@Table(schema = "Ber", name = "AdmHndBijgehoudenPers")
public class AdministratieveHandelingBijgehoudenPersoonModel extends
        AbstractAdministratieveHandelingBijgehoudenPersoonModel implements AdministratieveHandelingBijgehoudenPersoon
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     * 
     */
    protected AdministratieveHandelingBijgehoudenPersoonModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     * 
     * @param administratieveHandeling administratieveHandeling van Administratieve handeling \ Bijgehouden persoon.
     * @param persoon persoon van Administratieve handeling \ Bijgehouden persoon.
     */
    public AdministratieveHandelingBijgehoudenPersoonModel(
            final AdministratieveHandelingModel administratieveHandeling, final PersoonModel persoon)
    {
        super(administratieveHandeling, persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     * 
     * @param administratieveHandelingBijgehoudenPersoon Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     * @param persoon Bijbehorende Persoon.
     */
    public AdministratieveHandelingBijgehoudenPersoonModel(
            final AdministratieveHandelingBijgehoudenPersoon administratieveHandelingBijgehoudenPersoon,
            final AdministratieveHandelingModel administratieveHandeling, final PersoonModel persoon)
    {
        super(administratieveHandelingBijgehoudenPersoon, administratieveHandeling, persoon);
    }

}
