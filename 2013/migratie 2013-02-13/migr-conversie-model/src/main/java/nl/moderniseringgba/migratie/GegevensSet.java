/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Geeft aan het geannoteerde element alleen voorkomt in de gegevens set. Dit betekend dat het element in de BRP DAL
 * *NIET* geschreven of gelezen wordt. Voor de conversie is het element wel van belang om de juiste afleiding te kunnen
 * doen.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface GegevensSet {

}
