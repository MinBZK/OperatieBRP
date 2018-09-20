/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * The BrpIdentiteitInhoudConverter object is used to convert an <code>BrpIdentiteitInhoud</code> to XML by intercepting
 * the normal serialization process. When serializing an <code>BrpIdentiteitInhoud</code> the write method is invoked.
 * This is provided with the <code>BrpIdentiteitInhoud</code> instance to be serialized and the OutputNode to use to
 * write the XML. Values can be taken from the instance and transferred to the node.
 * <p/>
 * For deserialization the read method is invoked. This is provided with the InputNode, which can be used to read the
 * elements and attributes representing the member data of the <code>BrpIdentiteitInhoud</code> being deserialized. Once
 * the <code>BrpIdentiteitInhoud</code> object has been instantiated it must be returned.
 * <p/>
 * This custom converter is designed to cope with the single object instance behaviour of
 * <code>BrpIdentiteitInhoud</code>.
 *
 * @see BrpIdentiteitInhoud
 */
public final class BrpIdentiteitInhoudConverter implements Converter<BrpIdentiteitInhoud> {

    @Override
    public BrpIdentiteitInhoud read(final InputNode node) {
        return BrpIdentiteitInhoud.IDENTITEIT;
    }

    @Override
    public void write(final OutputNode node, final BrpIdentiteitInhoud value) {
        // BrpIdentiteitInhoud bevat geen state
    }

}
