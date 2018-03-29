/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.vrijbericht;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;

/**
 * Vrij bericht gegevens.
 */
@JsonAutoDetect
public final class VrijBerichtGegevens {

    private Stelsel stelsel;
    private String brpEndpointURI;
    private ArchiveringOpdracht archiveringOpdracht;
    private Partij partij;

    private VrijBerichtGegevens() {
    }


    /**
     * Maak een nieuwe {@link VrijBerichtGegevens.Builder}.
     * @return de builder
     */
    public static VrijBerichtGegevens.Builder builder() {
        return new VrijBerichtGegevens.Builder();
    }


    public String getBrpEndpointURI() {
        return brpEndpointURI;
    }

    public ArchiveringOpdracht getArchiveringOpdracht() {
        return archiveringOpdracht;
    }

    public Stelsel getStelsel() {
        return stelsel;
    }

    public Partij getPartij() {
        return partij;
    }

    /**
     * Builder voor {@link VrijBerichtGegevens}.
     */
    public static final class Builder {

        private final VrijBerichtGegevens vrijBerichtGegevens;

        private Builder() {
            vrijBerichtGegevens = new VrijBerichtGegevens();
        }

        /**
         * Voegt endpoint URL toe
         * @param endPointUrl endpoint URL
         * @return VrijBerichtGegevens builder
         */
        public Builder metBrpEndpointUrl(final String endPointUrl) {
            vrijBerichtGegevens.brpEndpointURI = endPointUrl;
            return this;
        }

        /**
         * Voegt {@link ArchiveringOpdracht} DTO toe
         * @param archiveringOpdracht archiveringOpdracht
         * @return VrijBerichtGegevens builder
         */
        public Builder metArchiveringOpdracht(final ArchiveringOpdracht archiveringOpdracht) {
            vrijBerichtGegevens.archiveringOpdracht = archiveringOpdracht;
            return this;
        }

        /**
         * Voegt {@link Stelsel} toe
         * @param stelsel stelsel
         * @return VrijBerichtGegevens builder
         */
        public Builder metStelsel(final Stelsel stelsel) {
            vrijBerichtGegevens.stelsel = stelsel;
            return this;
        }

        /**
         * Voegt {@link Partij} toe
         * @param partij partij
         * @return VrijBerichtGegevens builder
         */
        public Builder metPartij(final Partij partij) {
            vrijBerichtGegevens.partij = partij;
            return this;
        }

        /**
         * Bouwt een {@link VrijBerichtGegevens} instantie
         * @return berichtgegevensobject
         */
        public VrijBerichtGegevens build() {
            return vrijBerichtGegevens;
        }
    }

}
