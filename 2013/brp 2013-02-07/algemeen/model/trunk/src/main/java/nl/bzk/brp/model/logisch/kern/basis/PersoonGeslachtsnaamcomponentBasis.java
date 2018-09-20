/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponentStandaardGroep;


/**
 * Component van de Geslachtsnaam van een Persoon
 *
 * De geslachtsnaam van een Persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan
 * er sprake zijn van het voorkomen van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de
 * Geslachtsnaam uiteen in meerdere Geslachtsnaamcomponenten. Een Geslachtsnaamcomponent bestaat vervolgens
 * mogelijkerwijs uit meerdere onderdelen, waaronder Voorvoegsel en Naamdeel. Zie verder toelichting bij Geslachtsnaam.
 *
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige)
 * geslachtsnaam van een kind te vormen door delen van de geslachtsnaam van beide ouders samen te voegen, is het alvast
 * mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na raadpleging van ministerie van
 * Justitie, in de persoon van Jet Lenters.
 *
 *
 *
 */
public interface PersoonGeslachtsnaamcomponentBasis extends ObjectType {

    /**
     * Retourneert Persoon van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Volgnummer van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Volgnummer.
     */
    Volgnummer getVolgnummer();

    /**
     * Retourneert Standaard van Persoon \ Geslachtsnaamcomponent.
     *
     * @return Standaard.
     */
    PersoonGeslachtsnaamcomponentStandaardGroep getStandaard();

}
