/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map de Gerelateerdebetrokkenheid entiteit op BlobRecords.
 */
final class GerelateerdeBetrokkenheidBlobMapper {

    private final PersoonBlobber blobber;
    private final Betrokkenheid betrokkenheid;
    private final SoortBetrokkenheid soortBetrokkenheid;
    private final Element typeRelatie;

    /**
     * Constructor.
     *
     * @param blobber de blob
     * @param betrokkenheid de betrokkenheid
     */
    GerelateerdeBetrokkenheidBlobMapper(final PersoonBlobber blobber, final Betrokkenheid betrokkenheid) {
        this.blobber = blobber;
        this.betrokkenheid = betrokkenheid;

        soortBetrokkenheid = betrokkenheid.getSoortBetrokkenheid();
        typeRelatie = geeftObjectTypeVoorRelatie();
    }

    /**
     * Voert de mapping uit.
     */
    public void map() {
        switch (soortBetrokkenheid) {
            case KIND:
                mapGerelateerdKind();
                break;
            case OUDER:
                mapGerelateerdeOuder();
                break;
            case PARTNER:
                mapGerelateerdePartner();
                break;
            default:
                throw new IllegalArgumentException("Kan gerelateerde betrokkenheid niet mappen: " + soortBetrokkenheid);
        }
    }

    private void mapGerelateerdePartner() {
        final SoortRelatie soortRelatie = betrokkenheid.getRelatie().getSoortRelatie();
        switch (soortRelatie) {
            case HUWELIJK:
                mapGerelateerdeHuwelijksPartner();
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                mapGerelateerdeGeregistreerdPartner();
                break;
            default:
                throw new IllegalArgumentException("Kan gerelateerde partner niet mappen: " + soortBetrokkenheid);
        }
    }

    private void mapGerelateerdeGeregistreerdPartner() {
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEGEREGISTREERDEPARTNER);
            record.setGroepElement(Element.GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.GERELATEERDEGEREGISTREERDEPARTNER_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }
        new GerelateerdeGeregistreerdePartnerPersoonBlobMapper(blobber, betrokkenheid).map();
    }

    private void mapGerelateerdeHuwelijksPartner() {
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEHUWELIJKSPARTNER);
            record.setGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.GERELATEERDEHUWELIJKSPARTNER_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }
        new GerelateerdeHuwelijkspartnerPersoonBlobMapper(blobber, betrokkenheid).map();
    }

    private void mapGerelateerdKind() {
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEKIND);
            record.setGroepElement(Element.GERELATEERDEKIND_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.GERELATEERDEKIND_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }
        new GerelateerdeKindPersoonBlobMapper(blobber, betrokkenheid).map();
    }

    private void mapGerelateerdeOuder() {
        for (final BetrokkenheidHistorie his : betrokkenheid.getBetrokkenheidHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEOUDER);
            record.setGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT);
            record.setVoorkomenSleutel(his.getId()/* dummy */);
            record.addAttribuut(Element.GERELATEERDEOUDER_ROLCODE, betrokkenheid.getSoortBetrokkenheid().getCode());
        }

        new GerelateerdeOuderPersoonBlobMapper(blobber, betrokkenheid).map();
        mapOuderschap();
        mapOuderlijkgezag();
    }

    private void mapOuderlijkgezag() {
        for (final BetrokkenheidOuderlijkGezagHistorie his : betrokkenheid.getBetrokkenheidOuderlijkGezagHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEOUDER);
            record.setGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG, his.getIndicatieOuderHeeftGezag());
        }
    }

    private void mapOuderschap() {
        for (final BetrokkenheidOuderHistorie his : betrokkenheid.getBetrokkenheidOuderHistorieSet()) {
            final BlobRecord record = blobber.maakBlobRecord(his);
            record.setParentObjectElement(typeRelatie);
            record.setParentObjectSleutel(betrokkenheid.getRelatie().getId());
            record.setObjectSleutel(betrokkenheid.getId());
            record.setObjectElement(Element.GERELATEERDEOUDER);
            record.setGroepElement(Element.GERELATEERDEOUDER_OUDERSCHAP);
            record.setVoorkomenSleutel(his.getId());
            record.addAttribuut(Element.GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN, his.getIndicatieOuderUitWieKindIsGeboren());
        }
    }

    private Element geeftObjectTypeVoorRelatie() {
        final Element ot;
        final SoortRelatie soortRelatie = betrokkenheid.getRelatie().getSoortRelatie();
        switch (soortRelatie) {
            case HUWELIJK:
                ot = Element.HUWELIJK;
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                ot = Element.GEREGISTREERDPARTNERSCHAP;
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                ot = Element.FAMILIERECHTELIJKEBETREKKING;
                break;
            default:
                throw new IllegalArgumentException("Kan type relatie niet mappen: " + soortRelatie);
        }
        return ot;
    }
}
