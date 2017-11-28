/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de geslachtsaanduiding.
 */
@Component
public abstract class AbstractGeslachtsaanduidingMapper extends AbstractMapper<BrpGeslachtsaanduidingInhoud> {
    private final AttribuutElement geslachtsaanduidingCode;

    /**
     * Constructor.
     * @param identiteitGroep element voor identiteit groep
     * @param groep element voor te mappen groep
     * @param datumAanvangGeldigheid element voor datum aanvang geldigheid
     * @param datumEindeGeldigheid element voor datum einde geldigheid
     * @param tijdstipRegistratie element voor tijdstip registratie
     * @param tijdstipVerval element voor tijdstip verval
     * @param geslachtsaanduidingCode element voor geslachtsaanduiding
     */
    protected AbstractGeslachtsaanduidingMapper(
            final GroepElement identiteitGroep,
            final GroepElement groep,
            final AttribuutElement datumAanvangGeldigheid,
            final AttribuutElement datumEindeGeldigheid,
            final AttribuutElement tijdstipRegistratie,
            final AttribuutElement tijdstipVerval,
            final AttribuutElement geslachtsaanduidingCode) {
        super(identiteitGroep, groep, datumAanvangGeldigheid, datumEindeGeldigheid, tijdstipRegistratie, tijdstipVerval);
        this.geslachtsaanduidingCode = geslachtsaanduidingCode;
    }

    @Override
    public final BrpGeslachtsaanduidingInhoud mapInhoud(
            final MetaRecord identiteitsRecord,
            final MetaRecord record,
            final OnderzoekMapper onderzoekMapper) {
        return new BrpGeslachtsaanduidingInhoud(
                BrpMetaAttribuutMapper.mapBrpGeslachtsaanduidingCode(
                        record.getAttribuut(geslachtsaanduidingCode),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), geslachtsaanduidingCode, true)));
    }
}
