/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;


/**
 * Map Betrokkenheid entiteit op BlobRecords.
 */
final class BetrokkenheidBlobMapper {

    private final PersoonBlobber blobber;

    /**
     * Constructor.
     *
     * @param blobber persoon blob
     */
    BetrokkenheidBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map betrokkenheden.
     */
    void map() {
        for (final Betrokkenheid betrokkenheid : blobber.getPersoon().getBetrokkenheidSet()) {
            if (blobber.getPersoon().getId().equals(betrokkenheid.getPersoon().getId())) {
                mapBetrokkenheid(betrokkenheid);
            }
        }
    }

    private void mapBetrokkenheid(final Betrokkenheid betrokkenheid) {
        final Element betrokkenheidElement;
        switch (betrokkenheid.getSoortBetrokkenheid()) {
            case KIND:
                mapKind(betrokkenheid);
                betrokkenheidElement = Element.PERSOON_KIND;
                break;
            case OUDER:
                mapOuder(betrokkenheid);
                betrokkenheidElement = Element.PERSOON_OUDER;
                break;
            case PARTNER:
                mapPartner(betrokkenheid);
                betrokkenheidElement = Element.PERSOON_PARTNER;
                break;
            default:
                throw new IllegalArgumentException("Kan betrokkenheid niet mappen: " + betrokkenheid.getSoortBetrokkenheid());
        }
        new RelatieBlobMapper(blobber, betrokkenheid, betrokkenheidElement).map();
    }

    private void mapPartner(final Betrokkenheid betrokkenheid) {
        // identiteit
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(betrokkenheid.getPersoon().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.PERSOON_PARTNER);
            record.setGroepElement(Element.PERSOON_PARTNER_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.PERSOON_PARTNER_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }
    }

    private void mapKind(final Betrokkenheid betrokkenheid) {
        // identiteit
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(betrokkenheid.getPersoon().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.PERSOON_KIND);
            record.setGroepElement(Element.PERSOON_KIND_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.PERSOON_KIND_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }
    }

    private void mapOuder(final Betrokkenheid betrokkenheid) {

        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(betrokkenheid.getPersoon().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.PERSOON_OUDER);
            record.setGroepElement(Element.PERSOON_OUDER_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.PERSOON_OUDER_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }

        mapOuderschapGroep(betrokkenheid);
    }

    private void mapOuderschapGroep(final Betrokkenheid betrokkenheid) {
        for (final BetrokkenheidOuderHistorie his : betrokkenheid.getBetrokkenheidOuderHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(Element.PERSOON);
            record.setParentObjectSleutel(betrokkenheid.getPersoon().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.PERSOON_OUDER);
            record.setGroepElement(Element.PERSOON_OUDER_OUDERSCHAP);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.PERSOON_OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, his.getIndicatieOuderUitWieKindIsGeboren());
        }
    }
}
