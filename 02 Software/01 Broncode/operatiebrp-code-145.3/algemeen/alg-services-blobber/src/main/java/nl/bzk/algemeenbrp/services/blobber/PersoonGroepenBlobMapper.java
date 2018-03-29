/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.blobber;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsaanduidingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNaamgebruikHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNummerverwijzingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonPersoonskaartHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;

/**
 * Map entiteiten corresponderend met Persoon-groepen op Blob-records.
 */
class PersoonGroepenBlobMapper {
    private final PersoonBlobber blobber;

    /**
     * Constructor.
     * @param blobber persoon blob
     */
    PersoonGroepenBlobMapper(final PersoonBlobber blobber) {
        this.blobber = blobber;
    }

    /**
     * Map Persoon-groepen.
     */
    protected final void map() {
        // groepen
        mapAfgeleidAdministratiefGroep();
        mapBijhoudingGroep();
        mapDeelnameEUVerkiezingenGroep();
        mapGeboorteGroep();
        mapGeslachtsaanduidingGroep();
        mapIdentiteitGroep();
        mapIdentificatienummerGroep();
        mapInschrijvingGroep();
        mapMigratieGroep();
        mapNaamgebruikGroep();
        mapNummerverwijzingGroep();
        mapOverlijdenGroep();
        mapPersoonskaartGroep();
        mapUitsluitingKiesrechtGroep();
        mapVerblijfsrechtGroep();
        mapSamengesteldeNaamGroep();
        mapVersieGroep();
    }

    private void mapAfgeleidAdministratiefGroep() {
        for (final PersoonAfgeleidAdministratiefHistorie his : blobber.getPersoon().getPersoonAfgeleidAdministratiefHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setObjectSleutel(his.getPersoon().getId());
            record.setGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF);
            record.addAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING, his.getAdministratieveHandeling().getId());
            record.addAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING, his.getDatumTijdLaatsteWijziging());
            record.addAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK, his.getDatumTijdLaatsteWijzigingGba());
            record.addAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE, his.getSorteervolgorde());
        }
    }

    private void mapIdentiteitGroep() {
        final BlobRecord record = vulRecord(blobber.maakBlobRecord(), null);
        record.setObjectSleutel(blobber.getPersoon().getId());
        record.setObjectElementId(Element.PERSOON.getId());
        record.setGroepElement(Element.PERSOON_IDENTITEIT);
        record.setVoorkomenSleutel(record.getObjectSleutel() /* dummy */);
        record.addAttribuut(Element.PERSOON_SOORTCODE, blobber.getPersoon().getSoortPersoon().getCode());
    }

    private void mapIdentificatienummerGroep() {
        for (final PersoonIDHistorie his : blobber.getPersoon().getPersoonIDHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_IDENTIFICATIENUMMERS);
            record.addAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER, his.getBurgerservicenummer());
            record.addAttribuut(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER, his.getAdministratienummer());
        }
    }

    private void mapSamengesteldeNaamGroep() {
        for (final PersoonSamengesteldeNaamHistorie his : blobber.getPersoon().getPersoonSamengesteldeNaamHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_SAMENGESTELDENAAM);
            record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, his.getIndicatieAfgeleid());
            record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, his.getIndicatieNamenreeks());
            if (his.getPredicaat() != null) {
                record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, his.getPredicaat().getCode());
            }
            record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, his.getVoornamen());
            if (his.getAdellijkeTitel() != null) {
                record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, his.getAdellijkeTitel().getCode());
            }
            record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, his.getVoorvoegsel());
            if (his.getScheidingsteken() != null) {
                record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, his.getScheidingsteken().toString());
            }
            record.addAttribuut(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, his.getGeslachtsnaamstam());
        }
    }

    private void mapGeboorteGroep() {
        for (final PersoonGeboorteHistorie his : blobber.getPersoon().getPersoonGeboorteHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_GEBOORTE);
            record.addAttribuut(Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS, his.getBuitenlandsePlaatsGeboorte());
            record.addAttribuut(Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO, his.getBuitenlandseRegioGeboorte());
            record.addAttribuut(Element.PERSOON_GEBOORTE_DATUM, his.getDatumGeboorte());
            if (his.getGemeente() != null) {
                record.addAttribuut(Element.PERSOON_GEBOORTE_GEMEENTECODE, his.getGemeente().getCode());
            }
            if (his.getLandOfGebied() != null) {
                record.addAttribuut(Element.PERSOON_GEBOORTE_LANDGEBIEDCODE, his.getLandOfGebied().getCode());
            }
            record.addAttribuut(Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE, his.getOmschrijvingGeboortelocatie());
            record.addAttribuut(Element.PERSOON_GEBOORTE_WOONPLAATSNAAM, his.getWoonplaatsnaamGeboorte());
        }
    }


    private void mapGeslachtsaanduidingGroep() {
        for (final PersoonGeslachtsaanduidingHistorie his : blobber.getPersoon().getPersoonGeslachtsaanduidingHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_GESLACHTSAANDUIDING);
            if (his.getGeslachtsaanduiding() != null) {
                record.addAttribuut(Element.PERSOON_GESLACHTSAANDUIDING_CODE, his.getGeslachtsaanduiding().getCode());
            }
        }
    }

    private void mapInschrijvingGroep() {
        for (final PersoonInschrijvingHistorie his : blobber.getPersoon().getPersoonInschrijvingHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_INSCHRIJVING);
            record.addAttribuut(Element.PERSOON_INSCHRIJVING_DATUM, his.getDatumInschrijving());
            record.addAttribuut(Element.PERSOON_INSCHRIJVING_VERSIENUMMER, his.getVersienummer());
            record.addAttribuut(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL, his.getDatumtijdstempel());
        }
    }

    private void mapNummerverwijzingGroep() {
        for (final PersoonNummerverwijzingHistorie his : blobber.getPersoon().getPersoonNummerverwijzingHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_NUMMERVERWIJZING);
            record.addAttribuut(Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER, his.getVorigeBurgerservicenummer());
            record.addAttribuut(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER, his.getVolgendeBurgerservicenummer());
            record.addAttribuut(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER, his.getVorigeAdministratienummer());
            record.addAttribuut(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER, his.getVolgendeAdministratienummer());
        }
    }

    private void mapBijhoudingGroep() {
        for (final PersoonBijhoudingHistorie his : blobber.getPersoon().getPersoonBijhoudingHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_BIJHOUDING);
            if (his.getPartij() != null) {
                record.addAttribuut(Element.PERSOON_BIJHOUDING_PARTIJCODE, his.getPartij().getCode());
            }
            if (his.getBijhoudingsaard() != null) {
                record.addAttribuut(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE, his.getBijhoudingsaard().getCode());
            }
            if (his.getNadereBijhoudingsaard() != null) {
                record.addAttribuut(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE, his.getNadereBijhoudingsaard().getCode());
            }
        }
    }

    private void mapOverlijdenGroep() {
        for (final PersoonOverlijdenHistorie his : blobber.getPersoon().getPersoonOverlijdenHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_OVERLIJDEN);
            record.addAttribuut(Element.PERSOON_OVERLIJDEN_DATUM, his.getDatumOverlijden());
            if (his.getGemeente() != null) {
                record.addAttribuut(Element.PERSOON_OVERLIJDEN_GEMEENTECODE, his.getGemeente().getCode());
            }
            record.addAttribuut(Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM, his.getWoonplaatsnaamOverlijden());
            record.addAttribuut(Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS, his.getBuitenlandsePlaatsOverlijden());
            record.addAttribuut(Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO, his.getBuitenlandseRegioOverlijden());
            record.addAttribuut(Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE, his.getOmschrijvingLocatieOverlijden());
            if (his.getLandOfGebied() != null) {
                record.addAttribuut(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE, his.getLandOfGebied().getCode());
            }
        }
    }


    private void mapNaamgebruikGroep() {
        for (final PersoonNaamgebruikHistorie his : blobber.getPersoon().getPersoonNaamgebruikHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_NAAMGEBRUIK);
            if (his.getNaamgebruik() != null) {
                record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_CODE, his.getNaamgebruik().getCode());
            }
            record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID, his.getIndicatieNaamgebruikAfgeleid());
            if (his.getPredicaat() != null) {
                record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE, his.getPredicaat().getCode());
            }
            record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_VOORNAMEN, his.getVoornamenNaamgebruik());
            if (his.getAdellijkeTitel() != null) {
                record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE, his.getAdellijkeTitel().getCode());
            }
            record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL, his.getVoorvoegselNaamgebruik());
            record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN, his.getScheidingstekenNaamgebruik());
            record.addAttribuut(Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM, his.getGeslachtsnaamstamNaamgebruik());
        }
    }

    private void mapMigratieGroep() {
        for (final PersoonMigratieHistorie his : blobber.getPersoon().getPersoonMigratieHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_MIGRATIE);
            if (his.getSoortMigratie() != null) {
                record.addAttribuut(Element.PERSOON_MIGRATIE_SOORTCODE, his.getSoortMigratie().getCode());
            }
            if (his.getRedenWijzigingMigratie() != null) {
                record.addAttribuut(Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE, String.valueOf(his.getRedenWijzigingMigratie().getCode()));
            }
            if (his.getAangeverMigratie() != null) {
                record.addAttribuut(Element.PERSOON_MIGRATIE_AANGEVERCODE, String.valueOf(his.getAangeverMigratie().getCode()));
            }
            if (his.getLandOfGebied() != null) {
                record.addAttribuut(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE, his.getLandOfGebied().getCode());
            }
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1, his.getBuitenlandsAdresRegel1());
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2, his.getBuitenlandsAdresRegel2());
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3, his.getBuitenlandsAdresRegel3());
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4, his.getBuitenlandsAdresRegel4());
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5, his.getBuitenlandsAdresRegel5());
            record.addAttribuut(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6, his.getBuitenlandsAdresRegel6());
        }
    }

    private void mapVerblijfsrechtGroep() {
        for (final PersoonVerblijfsrechtHistorie his : blobber.getPersoon().getPersoonVerblijfsrechtHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_VERBLIJFSRECHT);
            if (his.getVerblijfsrecht() != null) {
                record.addAttribuut(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE, his.getVerblijfsrecht().getCode());
            }
            record.addAttribuut(Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG, his.getDatumAanvangVerblijfsrecht());
            record.addAttribuut(Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING, his.getDatumMededelingVerblijfsrecht());
            record.addAttribuut(Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE, his.getDatumVoorzienEindeVerblijfsrecht());
        }
    }

    private void mapUitsluitingKiesrechtGroep() {
        for (final PersoonUitsluitingKiesrechtHistorie his : blobber.getPersoon().getPersoonUitsluitingKiesrechtHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_UITSLUITINGKIESRECHT);
            record.addAttribuut(Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE, his.getIndicatieUitsluitingKiesrecht());
            record.addAttribuut(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE, his.getDatumVoorzienEindeUitsluitingKiesrecht());
        }
    }

    private void mapDeelnameEUVerkiezingenGroep() {
        for (final PersoonDeelnameEuVerkiezingenHistorie his : blobber.getPersoon().getPersoonDeelnameEuVerkiezingenHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN);
            record.addAttribuut(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME, his.getIndicatieDeelnameEuVerkiezingen());
            record.addAttribuut(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING, his.getDatumAanleidingAanpassingDeelnameEuVerkiezingen());
            record.addAttribuut(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING, his.getDatumVoorzienEindeUitsluitingEuVerkiezingen());
        }
    }

    private void mapPersoonskaartGroep() {
        for (final PersoonPersoonskaartHistorie his : blobber.getPersoon().getPersoonPersoonskaartHistorieSet()) {
            if (his == null) {
                continue;
            }
            final BlobRecord record = vulRecord(blobber.maakBlobRecord(his), his);
            record.setGroepElement(Element.PERSOON_PERSOONSKAART);
            if (his.getPartij() != null) {
                record.addAttribuut(Element.PERSOON_PERSOONSKAART_PARTIJCODE, his.getPartij().getCode());
            }
            record.addAttribuut(Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD, his.getIndicatiePersoonskaartVolledigGeconverteerd());
        }
    }

    private void mapVersieGroep() {
        final BlobRecord record = vulRecord(blobber.maakBlobRecord(), null);
        record.setObjectSleutel(blobber.getPersoon().getId());
        record.setObjectElementId(Element.PERSOON.getId());
        record.setGroepElement(Element.PERSOON_VERSIE);
        record.setVoorkomenSleutel(record.getObjectSleutel() /* dummy */);
        record.addAttribuut(Element.PERSOON_VERSIE_LOCK, blobber.getPersoon().getLockVersie());
    }

    private BlobRecord vulRecord(final BlobRecord record, final Entiteit his) {
        record.setObjectElement(Element.PERSOON);
        record.setObjectSleutel(blobber.getPersoon().getId());

        if (his != null) {
            record.setVoorkomenSleutel(his.getId().longValue());
        } else {
            record.setVoorkomenSleutel(record.getObjectSleutel()/* dummy */);
        }
        return record;
    }
}
