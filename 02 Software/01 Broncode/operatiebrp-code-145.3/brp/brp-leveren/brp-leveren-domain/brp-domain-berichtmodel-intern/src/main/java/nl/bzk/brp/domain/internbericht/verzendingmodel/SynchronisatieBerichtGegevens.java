/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.internbericht.verzendingmodel;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;

/**
 * Bericht object dat via JMS gecommuniceerd wordt tussen de levering componenten. JSON wordt gebruikt voor (De)serialisatie.
 */
@JsonAutoDetect
public final class SynchronisatieBerichtGegevens {

    private Stelsel stelsel;
    private SoortDienst soortDienst;
    private String brpEndpointURI;
    private Protocolleringsniveau protocolleringsniveau;
    private ProtocolleringOpdracht protocolleringOpdracht;
    private ArchiveringOpdracht archiveringOpdracht;

    private SynchronisatieBerichtGegevens() {
    }

    /**
     * Maak een nieuwe {@link SynchronisatieBerichtGegevens.Builder}.
     * @return de builder
     */
    public static Builder builder() {
        return new Builder();
    }

    public Stelsel getStelsel() {
        return stelsel;
    }

    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    public Protocolleringsniveau getProtocolleringsniveau() {
        return protocolleringsniveau;
    }

    public String getBrpEndpointURI() {
        return brpEndpointURI;
    }

    public ProtocolleringOpdracht getProtocolleringOpdracht() {
        return protocolleringOpdracht;
    }

    public ArchiveringOpdracht getArchiveringOpdracht() {
        return archiveringOpdracht;
    }

    /**
     * Builder voor {@link SynchronisatieBerichtGegevens}.
     */
    public static final class Builder {

        private final SynchronisatieBerichtGegevens synchronisatieBerichtGegevens;

        private Builder() {
            synchronisatieBerichtGegevens = new SynchronisatieBerichtGegevens();
        }

        /**
         * @param stelsel het stelsel
         * @return de builder
         */
        public Builder metStelsel(final Stelsel stelsel) {
            synchronisatieBerichtGegevens.stelsel = stelsel;
            return this;
        }

        /**
         * @param soortDienst de soort dienst
         * @return de builder
         */
        public Builder metSoortDienst(final SoortDienst soortDienst) {
            synchronisatieBerichtGegevens.soortDienst = soortDienst;
            return this;
        }

        /**
         * @param protocolleringsniveau het protocolleringsniveau
         * @return de builder
         */
        public Builder metProtocolleringsniveau(final Protocolleringsniveau protocolleringsniveau) {
            synchronisatieBerichtGegevens.protocolleringsniveau = protocolleringsniveau;
            return this;
        }

        /**
         * @param brpEndpointURI de endpoint URI voor BRP leveringberichten
         * @return de builder
         */
        public Builder metBrpEndpointURI(final String brpEndpointURI) {
            synchronisatieBerichtGegevens.brpEndpointURI = brpEndpointURI;
            return this;
        }

        /**
         * @param protocolleringOpdracht het {@link ProtocolleringOpdracht}
         * @return de builder
         */
        public Builder metProtocolleringOpdracht(final ProtocolleringOpdracht protocolleringOpdracht) {
            synchronisatieBerichtGegevens.protocolleringOpdracht = protocolleringOpdracht;
            return this;
        }

        /**
         * @param archiveringOpdracht het {@link ArchiveringOpdracht}
         * @return de builder
         */
        public Builder metArchiveringOpdracht(final ArchiveringOpdracht archiveringOpdracht) {
            synchronisatieBerichtGegevens.archiveringOpdracht = archiveringOpdracht;
            return this;
        }

        /**
         * Levert de instantie van {@link SynchronisatieBerichtGegevens} op.
         * @return de instantie
         */
        public SynchronisatieBerichtGegevens build() {
            return synchronisatieBerichtGegevens;
        }
    }
}
