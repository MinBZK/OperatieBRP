/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import nl.bzk.brp.model.logisch.kern.basis.PersoonGeslachtsnaamcomponentBasis;


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
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.1.8.
 * Gegenereerd op: Tue Nov 27 12:05:00 CET 2012.
 */
public interface PersoonGeslachtsnaamcomponent extends PersoonGeslachtsnaamcomponentBasis {

}
