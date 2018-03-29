/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.springframework.util.Assert;

/**
 * MetaRecord predikaat : behoudt enkel voorkomens die actueel zijn en behoren tot een identificerende groep waarvan het parentobject
 * de root persoon is.
 */
final class ActueelEnIdentificerendMetaRecordPredicate implements Predicate<MetaRecord> {

    private final Persoonslijst persoonslijst;

    /**
     * Constructpr.
     * @param persoonslijst persoonslijst
     */
    ActueelEnIdentificerendMetaRecordPredicate(Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    @Override
    public boolean test(final MetaRecord record) {
        Assert.notNull(record, "Record voor predicaat mag niet null zijn.");
        final boolean isRecordUitIdentificerendeGroep =
                record.getParentGroep().getGroepElement().isIdentificerend()
                        && record.getParentGroep().getParentObject().getObjectElement().getElement() == Element.PERSOON;
        return isRecordUitIdentificerendeGroep && persoonslijst.isActueel(record);
    }
}
