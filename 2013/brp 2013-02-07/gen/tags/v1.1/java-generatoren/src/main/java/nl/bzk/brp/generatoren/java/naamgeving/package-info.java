/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * De verschillende generatoren binnen deze Java generator groep volgen ieder een eigen naamgevingsconventie voor de
 * te genereren Java classes, packages etc.. Deze is grotendeels gelijk, maar wijkt uiteraard per Java generator net
 * iets af. Zo zal de logische interface generator geen suffix aan de gegenereerde classes toevoegen, maar de bericht
 * model generator juist wel, namelijk het woord 'Bericht'. Om nu de verschillende generatoren op alle plekken dezelfde
 * naamgeving te laten hanteren en omdat sommige generatoren ook moeten verwijzen naar artefacten uit andere
 * generatoren (denk aan een bericht model dat een logische interface moet implementeren), wordt er gebruik gemaakt van
 * naamgevingsstrategien. Hierbij is de logica voor de naamgeving geabstraheerd en wordt deze gecontroleerd in deze
 * losse naamgevingsstrategien, met als doel het eenvoudig kunnen aanpassen, hergebruik binnen een generator en zelfs
 * over generatoren heen.
 *
 * @see nl.bzk.brp.generatie.java.naamgeving.NaamgevingStrategie
 */
package nl.bzk.brp.generatoren.java.naamgeving;
