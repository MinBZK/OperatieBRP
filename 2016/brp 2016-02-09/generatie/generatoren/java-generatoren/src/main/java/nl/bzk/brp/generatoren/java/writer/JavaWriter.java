/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.AbstractGenerator;
import nl.bzk.brp.generatoren.java.model.AbstractJavaType;

/**
 * Interface voor het genereren en opslaan van Java source bestanden (javaBroncodeObjecten en/of interfaces) op basis
 * van een Java specifiek object model.
 *
 * @param <T> Java Type dat weggeschreven dient te worden.
 */
public interface JavaWriter<T extends AbstractJavaType> {

    /**
     * Genereert voor elk Java broncode object uit de lijst een of meerdere Java source bestanden en slaat deze
     * vervolgens op.
     *
     * @param javaBroncodeObjecten de Java broncode objecten waarvoor de Java source bestanden gegenereerd en
     * opgeslagen dienen te worden.
     * @param generator de generator die de opgegeven Java broncode objecten heeft geinstantieerd/gegenereerd.
     * @return Lijst van weggeschreven java typen.
     */
    List<T> genereerEnSchrijfWeg(List<T> javaBroncodeObjecten, AbstractGenerator generator);

}
