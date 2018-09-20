/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Package met classes voor het opslaan van Java source bestanden (javaBroncodeObjecten en/of interfaces) op basis
 * van een Java specifiek object model. Hierbij volgen deze allemaal de
 * {@link nl.bzk.brp.generatie.java.writer.JavaWriter} interface, maar zijn er verschillende implementaties die op een
 * andere wijze de Javacode wegschrijven; bijvoorbeeld naar een enkel bron bestand of conform het generation-gap
 * patroon naar twee bron bestanden (een voor manuele aanpassingen en een met de gegenereerde code).
 *
 * @see nl.bzk.brp.generatie.java.writer.JavaWriter
 */
package nl.bzk.brp.generatoren.java.writer;
