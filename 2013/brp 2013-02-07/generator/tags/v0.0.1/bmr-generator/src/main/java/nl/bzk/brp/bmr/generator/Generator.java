/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator;

import nl.bzk.brp.bmr.generator.utils.FileSystemAccess;
import nl.bzk.brp.ecore.bmr.ModelElement;


/**
 * Interface waaraan alle code generatoren moeten voldoen, zodat ze allemaal in één bewerking aangeroepen kunnen worden.
 */
public interface Generator {

    /**
     * Genereer code uit het model element naar een file.
     *
     * @param element het model element waaruit code gegenereerd wordt.
     * @param file de file waarnaar gegenereerde code geschreven wordt.
     */
    void generate(final ModelElement element, final FileSystemAccess file);
}
