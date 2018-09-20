/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.web.beheer.controller.partijen;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import nl.bzk.brp.beheer.model.AuthenticatieMiddel;
import nl.bzk.brp.beheer.model.Certificaat;
import nl.bzk.brp.beheer.model.Partij;
import org.apache.commons.codec.DecoderException;


/**
 * Form bean voor authenticatiemiddel scherm.
 *
 */
public class AuthenticatiemiddelFormBean {
    private Partij              partij;

    @Valid
    private AuthenticatieMiddel authenticatiemiddel;

    private boolean             leesModus;

    /** Invoerveld geselecteerde authenticatie middelen. */
    private List<String>        teVerwijderenAuthmiddelen;

    /**
     * Constructor.
     *
     * @param partij de gerelateerd partij
     */
    public AuthenticatiemiddelFormBean(final Partij partij) {
        this.partij = partij;
        maakNieuwAuthenticatieMiddel();
    }

    /**
     * Bereid de authenticatiemiddel voor voor invoer.
     */
    public void maakNieuwAuthenticatieMiddel() {
        // TODO implementeer certificaat zoek en toevoeg functie, nu tijdelijk
        // hardcoded aangemaakt



        Certificaat certificaatOndertekening = new Certificaat();
        certificaatOndertekening.setSerial(BigInteger.ZERO);
        try {
            certificaatOndertekening.setSignature(UUID.randomUUID().toString().getBytes());
        } catch (DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        authenticatiemiddel = new AuthenticatieMiddel(partij, certificaatOndertekening);

        Certificaat certificaatSsl = new Certificaat();
        certificaatSsl.setSerial(BigInteger.ZERO);
        try {
            certificaatSsl.setSignature(UUID.randomUUID().toString().getBytes());
        } catch (DecoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        authenticatiemiddel.setCertificaatTbvSsl(certificaatSsl);
        authenticatiemiddel.setAuthenticatiemiddelStatusHis("A");
    }

    public Partij getPartij() {
        return partij;
    }

    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public AuthenticatieMiddel getAuthenticatiemiddel() {
        return authenticatiemiddel;
    }

    public void setAuthenticatiemiddel(final AuthenticatieMiddel authenticatiemiddel) {
        this.authenticatiemiddel = authenticatiemiddel;
    }

    public List<String> getTeVerwijderenAuthmiddelen() {
        return teVerwijderenAuthmiddelen;
    }

    public void setTeVerwijderenAuthmiddelen(final List<String> teVerwijderenAuthmiddelen) {
        this.teVerwijderenAuthmiddelen = teVerwijderenAuthmiddelen;
    }

    public boolean isLeesModus() {
        return leesModus;
    }

    public void setLeesModus(final boolean leesModus) {
        this.leesModus = leesModus;
    }

    /**
     * Check of Rol is toegestaan.
     *
     * @return true als het toegestaan is en false als het niet toegestaan is.
     */
    public boolean isRolToegestaan() {
        for (AuthenticatieMiddel authmiddel : partij.getAuthenticatieMiddels()) {
            if (authmiddel.getRol() == null) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check of Rol niet toegestaan is.
     *
     * @return true als rol niet toegestaan is en false als rol wel toegestaan is.
     */
    public boolean isRolGeenToegestaan() {
        for (AuthenticatieMiddel authmiddel : partij.getAuthenticatieMiddels()) {
            if (authmiddel.getRol() != null) {
                return false;
            }
        }

        return true;
    }
}
