/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpVerbintenisInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een verbintenis.
 */
@Component
public abstract class AbstractVerbintenisMapper extends AbstractMapper<BrpVerbintenisInhoud> {

    private final AttribuutElement soort;
    private final GroepElement groep;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor te mappen groep
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param soort element voor soort relatie
     */
    public AbstractVerbintenisMapper(final GroepElement identiteitGroep, final GroepElement groep, final AttribuutElement tijdstipRegistratie,
                                     final AttribuutElement tijdstipVerval, final AttribuutElement soort) {
        super(identiteitGroep, groep, null, null, tijdstipRegistratie, tijdstipVerval);
        this.groep = groep;
        this.soort = soort;
    }

    @Override
    public final BrpVerbintenisInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpVerbintenisInhoud(BrpMetaAttribuutMapper.mapBrpSoortRelatieCode(identiteitRecord.getAttribuut(soort),
                onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), soort, true, record.getVoorkomensleutel(), groep)));
    }
}
