/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.jibx.writer;

import nl.bzk.brp.generatoren.algemeen.basis.Generator;
import nl.bzk.brp.generatoren.jibx.common.JibxBinding;
import nl.bzk.brp.generatoren.jibx.model.Binding;

/**
 * Generieke jibx writer interface.
 */
public interface JibxWriter {

    /**
     * Marshall de xml binding en schrijf de gegenereerde binding weg naar disk.
     *
     * @param jibxBinding de jibx binding
     * @param binding de te marshallen binding
     * @param generator de generator
     */
    void marshallXmlEnSchrijfWeg(final JibxBinding jibxBinding, final Binding binding, final Generator generator);

}
