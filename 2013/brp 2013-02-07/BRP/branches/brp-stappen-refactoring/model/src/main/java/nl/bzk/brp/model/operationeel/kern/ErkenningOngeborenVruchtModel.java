/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVrucht;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractErkenningOngeborenVruchtModel;


/**
 * De erkenning ongeboren vrucht zoals bedoeld in artikel 5, BW boek 1.
 *
 * Een erkenning ongeboren vrucht is een relatie tussen twee personen, de toekomstige ouders van het kind of de kinderen
 * waarvan ��n van de twee in verwachting is. Hierbij is er sprake van enerzijds een erkenner, en anderzijds een
 * instemmer. De laatst is de (toekomstig) ouder die thans de drager van het ongeboren vrucht is, zonder diens
 * toestemming is erkenning niet mogelijk.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.2.3.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-04 09:33:11.
 * Gegenereerd op: Tue Dec 04 09:50:47 CET 2012.
 */
@Entity
@DiscriminatorValue(value = "4")
public class ErkenningOngeborenVruchtModel extends AbstractErkenningOngeborenVruchtModel implements
        ErkenningOngeborenVrucht
{

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     */
    public ErkenningOngeborenVruchtModel() {
        super();
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenningOngeborenVrucht Te kopieren object type.
     */
    public ErkenningOngeborenVruchtModel(final ErkenningOngeborenVrucht erkenningOngeborenVrucht) {
        super(erkenningOngeborenVrucht);
    }

}
