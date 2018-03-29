/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import nl.bzk.brp.domain.leveringmodel.MetaRecord;

/**
 * Een literal expressie voor een {@link MetaRecord}.
 */
public final class MetaRecordLiteral implements Literal {

    private final MetaRecord metaRecord;

    /**
     * Constructor.
     *
     * @param metaRecord een {@link MetaRecord}
     */
    public MetaRecordLiteral(final MetaRecord metaRecord) {
        this.metaRecord = metaRecord;
    }

    public MetaRecord getMetaRecord() {
        return metaRecord;
    }

    @Override
    public String toString() {
        return metaRecord.getVoorkomensleutel() + "@" + metaRecord.getParentGroep().getGroepElement
                ().getNaam();
    }

    @Override
    public ExpressieType getType(final Context context) {
        return ExpressieType.BRP_METARECORD;
    }
}
