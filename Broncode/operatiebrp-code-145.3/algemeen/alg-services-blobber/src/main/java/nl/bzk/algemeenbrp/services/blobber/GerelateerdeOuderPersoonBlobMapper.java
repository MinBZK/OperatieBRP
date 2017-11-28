/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map de GerelateerdeOuder.Persoon op blobrecords.
 */
final class GerelateerdeOuderPersoonBlobMapper {

    private final PersoonBlobber blobber;
    private final Betrokkenheid betrokkenheid;
    private final Persoon gerelateerdePersoon;

    /**
     * Constructor.
     *
     * @param persoonBlobber persoon blobber
     * @param betrokkenheid betrokkenheid
     */
    GerelateerdeOuderPersoonBlobMapper(final PersoonBlobber persoonBlobber, final Betrokkenheid betrokkenheid) {
        blobber = persoonBlobber;
        this.betrokkenheid = betrokkenheid;
        gerelateerdePersoon = betrokkenheid.getPersoon();
    }

    /**
     * Map gerelateerde ouder incl. identificerende groepen,
     */
    void map() {
        if (gerelateerdePersoon != null) {
            final BlobRecord record = blobber.maakBlobRecord();
            record.setParentObjectElement(Element.GERELATEERDEOUDER);
            record.setParentObjectSleutel(betrokkenheid.getId());
            record.setObjectSleutel(gerelateerdePersoon.getId());
            record.setObjectElement(Element.GERELATEERDEOUDER_PERSOON);
            record.setGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT);
            record.setVoorkomenSleutel(gerelateerdePersoon.getId());
            record.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SOORTCODE, gerelateerdePersoon.getSoortPersoon().getCode());

            mapGeboorteGroep();
            mapSamengesteldeNaamGroep();
            mapGeslachtsaanduidingGroep();
            mapIdentificatienummersGroep();
        }
    }

    private void mapGeboorteGroep() {
        for (final PersoonGeboorteHistorie his : gerelateerdePersoon.getPersoonGeboorteHistorieSet()) {
            final BlobRecord geboorteRecord = blobber.maakBlobRecord(his);
            geboorteRecord.setParentObjectElement(Element.GERELATEERDEOUDER);
            geboorteRecord.setParentObjectSleutel(betrokkenheid.getId());
            geboorteRecord.setObjectSleutel(gerelateerdePersoon.getId());
            geboorteRecord.setObjectElement(Element.GERELATEERDEOUDER_PERSOON);
            geboorteRecord.setGroepElement(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE);
            geboorteRecord.setVoorkomenSleutel(his.getId());

            geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS, his.getBuitenlandsePlaatsGeboorte());
            geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO, his.getBuitenlandseRegioGeboorte());
            geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM, his.getDatumGeboorte());
            if (his.getGemeente() != null) {
                geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE, his.getGemeente().getCode());
            }
            if (his.getLandOfGebied() != null) {
                geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE, his.getLandOfGebied().getCode());
            }
            geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, his.getOmschrijvingGeboortelocatie());
            geboorteRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM, his.getWoonplaatsnaamGeboorte());
        }
    }

    private void mapSamengesteldeNaamGroep() {
        for (final PersoonSamengesteldeNaamHistorie his : gerelateerdePersoon.getPersoonSamengesteldeNaamHistorieSet()) {
            final BlobRecord samengesteldeNaamRecord = blobber.maakBlobRecord(his);
            samengesteldeNaamRecord.setParentObjectElement(Element.GERELATEERDEOUDER);
            samengesteldeNaamRecord.setParentObjectSleutel(betrokkenheid.getId());
            samengesteldeNaamRecord.setObjectSleutel(gerelateerdePersoon.getId());
            samengesteldeNaamRecord.setObjectElement(Element.GERELATEERDEOUDER_PERSOON);
            samengesteldeNaamRecord.setGroepElement(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM);
            samengesteldeNaamRecord.setVoorkomenSleutel(his.getId());

            if (his.getAdellijkeTitel() != null) {
                samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, his.getAdellijkeTitel().getCode());
            }
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, his.getGeslachtsnaamstam());
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, his.getIndicatieAfgeleid());
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, his.getIndicatieNamenreeks());
            if (his.getPredicaat() != null) {
                samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, his.getPredicaat().getCode());
            }
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, his.getScheidingsteken());
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN, his.getVoornamen());
            samengesteldeNaamRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, his.getVoorvoegsel());
        }
    }

    private void mapGeslachtsaanduidingGroep() {
        for (final PersoonGeslachtsaanduidingHistorie his : gerelateerdePersoon.getPersoonGeslachtsaanduidingHistorieSet()) {
            final BlobRecord geslachtsaanduidingRecord = blobber.maakBlobRecord(his);
            geslachtsaanduidingRecord.setParentObjectElement(Element.GERELATEERDEOUDER);
            geslachtsaanduidingRecord.setParentObjectSleutel(betrokkenheid.getId());
            geslachtsaanduidingRecord.setObjectSleutel(gerelateerdePersoon.getId());
            geslachtsaanduidingRecord.setObjectElement(Element.GERELATEERDEOUDER_PERSOON);
            geslachtsaanduidingRecord.setGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING);
            geslachtsaanduidingRecord.setVoorkomenSleutel(his.getId());
            if (his.getGeslachtsaanduiding() != null) {
                geslachtsaanduidingRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE, his.getGeslachtsaanduiding().getCode());
            }
        }
    }

    private void mapIdentificatienummersGroep() {
        for (final PersoonIDHistorie his : gerelateerdePersoon.getPersoonIDHistorieSet()) {
            final BlobRecord identificatieRecord = blobber.maakBlobRecord(his);
            identificatieRecord.setParentObjectElement(Element.GERELATEERDEOUDER);
            identificatieRecord.setParentObjectSleutel(betrokkenheid.getId());
            identificatieRecord.setObjectSleutel(gerelateerdePersoon.getId());
            identificatieRecord.setObjectElement(Element.GERELATEERDEOUDER_PERSOON);
            identificatieRecord.setGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS);
            identificatieRecord.setVoorkomenSleutel(his.getId());
            identificatieRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, his.getAdministratienummer());
            identificatieRecord.addAttribuut(Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, his.getBurgerservicenummer());
        }
    }
}
