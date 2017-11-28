/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.levering.lo3.conversie.brpnaarlo3.BrpFamilierechtelijkeBetrekkingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een ouderschap.
 */
@Component
public abstract class AbstractOuderschapMapper extends AbstractMapper<BrpFamilierechtelijkeBetrekkingInhoud> {

    private final AttribuutElement datumAanvangGeldigheidElement;

    /**
     * Constructor.
     * @param identiteitGroepElement element voor identiteit groep
     * @param groepElement element voor te mappen groep
     * @param datumAanvangGeldigheidElement datum aanvang geldigheid attribuut element
     * @param datumEindeGeldigheidElement datum einde geldigheid attribuut element
     * @param tijdstipRegistratieElement tijdstip registratie attribuut element
     * @param tijdstipVervalElement tijdstip verval attribuut element
     */
    protected AbstractOuderschapMapper(
            final GroepElement identiteitGroepElement,
            final GroepElement groepElement,
            final AttribuutElement datumAanvangGeldigheidElement,
            final AttribuutElement datumEindeGeldigheidElement,
            final AttribuutElement tijdstipRegistratieElement,
            final AttribuutElement tijdstipVervalElement) {
        super(identiteitGroepElement,
                groepElement,
                datumAanvangGeldigheidElement,
                datumEindeGeldigheidElement,
                tijdstipRegistratieElement,
                tijdstipVervalElement);
        this.datumAanvangGeldigheidElement = datumAanvangGeldigheidElement;
    }

    @Override
    public final BrpFamilierechtelijkeBetrekkingInhoud mapInhoud(
            final MetaRecord identiteitRecord,
            final MetaRecord record,
            final OnderzoekMapper onderzoekMapper) {
        return new BrpFamilierechtelijkeBetrekkingInhoud(
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(datumAanvangGeldigheidElement),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), datumAanvangGeldigheidElement, true)));
    }
}
