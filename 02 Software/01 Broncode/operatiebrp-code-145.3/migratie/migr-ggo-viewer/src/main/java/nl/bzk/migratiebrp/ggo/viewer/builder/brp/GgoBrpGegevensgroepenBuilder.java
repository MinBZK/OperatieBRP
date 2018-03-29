/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

/**
 * Builder voor grotere groepen. Merk op dat we het hier hebben over Lo3-achtige (gegevens)groepen en niet over de
 * reguliere BRP groepen (a la categorie).
 */
@Component
class GgoBrpGegevensgroepenBuilder {
    private final GgoBrpValueConvert valueConvert;

    /**
     * Constructor.
     * @param valueConvert de waarde converteerder
     */
    @Inject
    public GgoBrpGegevensgroepenBuilder(final GgoBrpValueConvert valueConvert) {
        this.valueConvert = valueConvert;
    }

    /**
     * Voeg Brp Relatie toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param relatie inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     */
    final void addGroepRelatie(final GgoBrpVoorkomen voorkomen, final RelatieHistorie relatie) {
        if (relatie != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_AANVANG, relatie.getDatumAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE_AANVANG, relatie.getGemeenteAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.WOONPLAATSNAAM_AANVANG, relatie.getWoonplaatsnaamAanvang());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG,
                    relatie.getBuitenlandsePlaatsAanvang());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.BUITENLANDSE_REGIO_AANVANG,
                    relatie.getBuitenlandseRegioAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG, relatie.getLandOfGebiedAanvang());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG,
                    relatie.getOmschrijvingLocatieAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.REDEN_EINDE_RELATIE, relatie.getRedenBeeindigingRelatie());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_EINDE, relatie.getDatumEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE_EINDE, relatie.getGemeenteEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.WOONPLAATSNAAM_EINDE, relatie.getWoonplaatsnaamEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE, relatie.getBuitenlandsePlaatsEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDSE_REGIO_EINDE, relatie.getBuitenlandseRegioEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE, relatie.getLandOfGebiedEinde());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE,
                    relatie.getOmschrijvingLocatieEinde());
        }
    }

    /**
     * Voeg Brp Document toe aan het voorkomen.
     * @param ggoInhoud Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     */
    final void addGroepDocument(final Map<String, String> ggoInhoud, final Document inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(
                    ggoInhoud,
                    GgoBrpElementEnum.SOORT_DOCUMENT,
                    inhoud.getSoortDocument());
            valueConvert.verwerkElement(ggoInhoud, GgoBrpElementEnum.AKTENUMMER, inhoud.getAktenummer());
            valueConvert.verwerkElement(ggoInhoud, GgoBrpElementEnum.OMSCHRIJVING, inhoud.getOmschrijving());
            valueConvert.verwerkElement(ggoInhoud, GgoBrpElementEnum.PARTIJ, inhoud.getPartij());
        }
    }

    /**
     * Voeg Brp OuderlijkGezag toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     */
    final void addGroepOuderlijkGezag(
            final GgoBrpVoorkomen voorkomen,
            final BetrokkenheidOuderlijkGezagHistorie inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.OUDER_HEEFT_GEZAG, inhoud.getIndicatieOuderHeeftGezag());
        }
    }

    /**
     * Voeg Brp Ouder toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     */
    final void addGroepOuder(final GgoBrpVoorkomen voorkomen, final BetrokkenheidOuderHistorie inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.INDICATIE_OUDERUITWIEKINDISGEBOREN,
                    inhoud.getIndicatieOuderUitWieKindIsGeboren());
        }
    }

    /**
     * Voeg BrpHistorie elementen toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param brpHistorie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    final void addHistorie(final GgoBrpVoorkomen voorkomen, final FormeleHistorie brpHistorie) {
        if (brpHistorie != null) {
            if (brpHistorie instanceof MaterieleHistorie) {
                addGeldigheid(voorkomen, (MaterieleHistorie) brpHistorie);
            }
            addRegistratieVerval(voorkomen, brpHistorie);
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.NADERE_AANDUIDING_VERVAL,
                    brpHistorie.getNadereAanduidingVerval());
        }
    }

    private void addGeldigheid(final GgoBrpVoorkomen voorkomen, final MaterieleHistorie brpHistorie) {
        String datAanvangGeldigheid = null;
        String datEindeGeldigheid = null;

        if (brpHistorie.getDatumAanvangGeldigheid() != null) {
            datAanvangGeldigheid = ViewerDateUtil.formatDatum(brpHistorie.getDatumAanvangGeldigheid());
        }

        if (brpHistorie.getDatumEindeGeldigheid() != null) {
            datEindeGeldigheid = ViewerDateUtil.formatDatum(brpHistorie.getDatumEindeGeldigheid());
        }

        final String geldigheid = joinDates(datAanvangGeldigheid, datEindeGeldigheid);
        valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_GELDIGHEID, geldigheid);
    }

    private void addRegistratieVerval(final GgoBrpVoorkomen voorkomen, final FormeleHistorie brpHistorie) {
        String datTijdRegistratie = null;
        String datTijdVerval = null;
        if (brpHistorie.getDatumTijdRegistratie() != null) {
            datTijdRegistratie = ViewerDateUtil.formatDatumTijdUtc(brpHistorie.getDatumTijdRegistratie());
        }
        if (brpHistorie.getDatumTijdVerval() != null) {
            datTijdVerval = ViewerDateUtil.formatDatumTijdUtc(brpHistorie.getDatumTijdVerval());
        }
        final String registratieVerval = joinDates(datTijdRegistratie, datTijdVerval);
        valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL, registratieVerval);
    }

    /**
     * Voegt twee velden samen als een veld, gescheiden door ' - '. Note: Bij aanpassen van dit formaat ook de
     * BrpPersoonslijst.js controleren.
     * @param datumVan String
     * @param datumTot String
     * @return Joined 'datumVan - datumTot' String of null als beide parameters null zijn.
     */
    private String joinDates(final String datumVan, final String datumTot) {
        if (datumVan == null && datumTot == null) {
            return null;
        } else {
            return (datumVan != null ? datumVan : "") + " - " + (datumTot != null ? datumTot : "");
        }
    }

    /**
     * Voeg een IST relatie toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud De IST relatie waar de gegevens vandaan komen.
     */
    final void addIstRelatie(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.ADMINISTRATIENUMMER, inhoud.getAnummer());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BURGERSERVICENUMMER, inhoud.getBsn());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.PREDICAAT, inhoud.getPredicaat());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.VOORNAMEN, inhoud.getVoornamen());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.VOORVOEGSEL, inhoud.getVoorvoegsel());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.SCHEIDINGSTEKEN, inhoud.getScheidingsteken());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.ADELLIJKE_TITEL, inhoud.getAdellijkeTitel());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GESLACHTSNAAMSTAM, inhoud.getGeslachtsnaamstam());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_GEBOORTE, inhoud.getDatumGeboorte());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE_GEBOORTE, inhoud.getGemeenteGeboorte());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDSE_PLAATS_GEBOORTE, inhoud.getBuitenlandsePlaatsGeboorte());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.OMSCHRIJVING_GEBOORTELOCATIE, inhoud.getOmschrijvingLocatieGeboorte());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_GEBOORTE, inhoud.getLandOfGebiedGeboorte());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GESLACHTSAANDUIDING, inhoud.getGeslachtsaanduiding());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.DATUM_INGANG_FAMILIERECHTELIJKE_BETREKKING,
                    inhoud.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }

    private void addIstStandaardGegevens(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.AKTENUMMER, inhoud.getAktenummer());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.OMSCHRIJVING, inhoud.getDocumentOmschrijving());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.SOORT_DOCUMENT, inhoud.getSoortDocument());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.PARTIJ, inhoud.getPartij());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_DOCUMENT, inhoud.getRubriek8220DatumDocument());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.AANDUIDING_GEGEVENS_IN_ONDERZOEK,
                    inhoud.getRubriek8310AanduidingGegevensInOnderzoek());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_INGANG_ONDERZOEK, inhoud.getRubriek8320DatumIngangOnderzoek());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_EINDE_ONDERZOEK, inhoud.getRubriek8330DatumEindeOnderzoek());
            valueConvert.verwerkElement(
                    voorkomen,
                    GgoBrpElementEnum.ONJUIST_OF_STRIJDIG_MET_OPENBARE_ORDE,
                    inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.INGANGSDATUM_GELDIGHEID, inhoud.getRubriek8510IngangsdatumGeldigheid());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_VAN_OPNEMING, inhoud.getRubriek8610DatumVanOpneming());
        }
    }

    /**
     * Voeg een IST gezagsVerhouding toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud De IST gezagsVerhouding waar de gegevens vandaan komen.
     */
    final void addIstGezagsVerhouding(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.INDICATIE_OUDER1_HEEFT_GEZAG, inhoud.getIndicatieOuder1HeeftGezag());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.INDICATIE_OUDER2_HEEFT_GEZAG, inhoud.getIndicatieOuder2HeeftGezag());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.INDICATIE_DERDE_HEEFT_GEZAG, inhoud.getIndicatieDerdeHeeftGezag());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.INDICATIE_ONDER_CURATELE, inhoud.getIndicatieOnderCuratele());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }

    /**
     * Voeg een IST huwelijkOfGp toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud De IST gezagsVerhouding waar de gegevens vandaan komen.
     */
    final void addIstHuwelijkOfGp(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_AANVANG, inhoud.getDatumAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE_AANVANG, inhoud.getGemeenteAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG, inhoud.getBuitenlandsePlaatsAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG, inhoud.getOmschrijvingLocatieAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG, inhoud.getLandOfGebiedAanvang());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.REDEN_EINDE_RELATIE, inhoud.getRedenBeeindigingRelatie());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.DATUM_EINDE, inhoud.getDatumEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.GEMEENTE_EINDE, inhoud.getGemeenteEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE, inhoud.getBuitenlandsePlaatsEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE, inhoud.getOmschrijvingLocatieEinde());
            valueConvert.verwerkElement(voorkomen, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE, inhoud.getLandOfGebiedEinde());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }
}
