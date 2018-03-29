/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 * Util klasse voor test om alles te autoriseren.
 */
public final class AutorisatieAlles extends ParentFirstModelVisitor {

    private final Berichtgegevens berichtgegevens;

    public AutorisatieAlles(final Berichtgegevens berichtgegevens) {
        this.berichtgegevens = berichtgegevens;
        visit(berichtgegevens.getPersoonslijst().getMetaObject());
    }

    @Override
    public void doVisit(final MetaObject object) {
        berichtgegevens.autoriseer(object);
    }

    @Override
    protected void doVisit(final MetaGroep groep) {
        berichtgegevens.autoriseer(groep);
    }

    @Override
    protected void doVisit(final MetaRecord record) {
        berichtgegevens.autoriseer(record);
    }

    @Override
    protected void doVisit(final MetaAttribuut attribuut) {
        berichtgegevens.autoriseer(attribuut);
    }

}
