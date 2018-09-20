/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.autaut;

import nl.bzk.brp.domein.autaut.basis.BasisCertificaat;

import org.apache.commons.codec.DecoderException;


public interface Certificaat extends BasisCertificaat {

    /**
     * Methode voor het omzetten van een hexadecimale string naar de signature bytearray.
     *
     * @param signature de hexadecimal string die moet worden omgezet.
     * @return de byte array omgezet vanuit de string.
     * @throws DecoderException indien er een fout optreedt bij het omzetten.
     */
    byte[] converteerStringNaarSignature(final String signature) throws DecoderException;

    /**
     * Methode voor het omzetten van een signature bytearray naar een hexadecimale string.
     *
     * @param signature de signature bytearray die moet worden omgezet.
     * @return de hexadecimale string op basis van de bytearray.
     * @throws DecoderException indien er een fout optreedt bij het omzetten.
     */
    String converteerSignatureNaarString(final byte[] signature) throws DecoderException;
}
