/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.basis.ObjectType;
import nl.bzk.brp.model.logisch.kern.Relatie;


/**
 * De familierechtelijke betrekking tussen het Kind enerzijds, en zijn/haar ouders anderzijds.
 *
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft g��n invloed op de familierechtelijke betrekking zelf: het blijft ��n en dezelfde Relatie.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
public interface FamilierechtelijkeBetrekkingBasis extends Relatie, ObjectType {

}
