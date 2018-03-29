/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht;

import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.ParentFirstModelVisitor;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;

/**
 */
public class AllesTonenVisitor extends ParentFirstModelVisitor {

    private final Berichtgegevens berichtgegevens;

    public AllesTonenVisitor(final Berichtgegevens berichtgegevens) {
        this.berichtgegevens = berichtgegevens;

    }

    @Override
    protected void doVisit(final MetaObject ot) {
        berichtgegevens.autoriseer(ot);
        //berichtgegevens.zetVerwerkingsoort(ot, Verwerkingssoort.REFERENTIE);
    }


    @Override
    protected void doVisit(final MetaGroep groep) {
        berichtgegevens.autoriseer(groep);
    }

    @Override
    protected void doVisit(final MetaAttribuut attribuut) {
        berichtgegevens.autoriseer(attribuut);
        if (attribuut.getAttribuutElement().isVerantwoording()) {
            final Actie actie = attribuut.getWaarde();
            if (actie != null) {
                berichtgegevens.autoriseerActie(actie);
            }
        }

    }

    @Override
    protected void doVisit(final MetaRecord record) {
        berichtgegevens.autoriseer(record);
        //berichtgegevens.zetRecordVerwerkingsoort(record, Verwerkingssoort.REFERENTIE);
    }
}
