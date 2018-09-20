/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;


/**
 * Factory om nieuwe Marshalling Contexts te maken.
 */
public interface MarshallingContextFactory {

    /**
     * Maakt een nieuwe marshalling context.
     *
     * @param klasse klasse voor marshalling
     * @return marshalling context
     * @throws JiBXException JiBX exception
     */
    IMarshallingContext nieuweMarshallingContext(Class klasse) throws JiBXException;
}
