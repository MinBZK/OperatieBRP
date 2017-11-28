/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.verzending;

import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;

/**
 * DTO met protocollering metadata.
 */
final class ProtocolleringData {

    private Protocolleringsniveau protocolleringNiveau;
    private String leveringsautorisatieId;
    private String partijCode;

    /**
     * Constructor.
     * @param protocolleringNiveau het {@link Protocolleringsniveau}
     * @param leveringsautorisatieId de leveringsautorisatie ID
     * @param partijCode de partijcode
     */
    ProtocolleringData(final Protocolleringsniveau protocolleringNiveau, final String leveringsautorisatieId, final String partijCode) {
        this.protocolleringNiveau = protocolleringNiveau;
        this.leveringsautorisatieId = leveringsautorisatieId;
        this.partijCode = partijCode;
    }

    Protocolleringsniveau getProtocolleringNiveau() {
        return protocolleringNiveau;
    }

    String getLeveringsautorisatieId() {
        return leveringsautorisatieId;
    }

    String getPartijCode() {
        return partijCode;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProtocolleringData that = (ProtocolleringData) o;
        return protocolleringNiveau == that.protocolleringNiveau
                && Objects.equals(leveringsautorisatieId, that.leveringsautorisatieId)
                && Objects.equals(partijCode, that.partijCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocolleringNiveau, leveringsautorisatieId, partijCode);
    }
}
