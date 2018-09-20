/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut.persistent;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.domein.autaut.Certificaat;
import nl.bzk.brp.domein.autaut.persistent.basis.AbstractPersistentCertificaat;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


@Entity
@Table(name = "Certificaat", schema = "AutAut")
public class PersistentCertificaat extends AbstractPersistentCertificaat implements Certificaat {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] converteerStringNaarSignature(final String signature) throws DecoderException {
        return Hex.decodeHex(signature.toCharArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String converteerSignatureNaarString(final byte[] signature) throws DecoderException {
        return Hex.encodeHexString(signature);
    }
}
