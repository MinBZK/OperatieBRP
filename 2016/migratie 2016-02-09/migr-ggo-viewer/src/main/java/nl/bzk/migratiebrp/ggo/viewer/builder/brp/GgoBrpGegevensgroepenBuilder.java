/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.FormeleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.MaterieleHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.RelatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import org.springframework.stereotype.Component;

/**
 * Builder voor grotere groepen. Merk op dat we het hier hebben over Lo3-achtige (gegevens)groepen en niet over de
 * reguliere BRP groepen (a la categorie).
 */
@Component
public class GgoBrpGegevensgroepenBuilder {
    @Inject
    private GgoBrpValueConvert ggoBrpValueConvert;

    /**
     * Voeg Brp Relatie toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param relatie
     *            inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            De naam van de groep.
     */
    public final void addGroepRelatie(final GgoBrpVoorkomen voorkomen, final RelatieHistorie relatie, final GgoBrpGroepEnum brpGroepEnum) {
        if (relatie != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_AANVANG, relatie.getDatumAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GEMEENTE_AANVANG, relatie.getGemeenteAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.WOONPLAATSNAAM_AANVANG, relatie.getWoonplaatsnaamAanvang());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                brpGroepEnum,
                GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG,
                relatie.getBuitenlandsePlaatsAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDSE_REGIO_AANVANG, relatie.getBuitenlandseRegioAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG, relatie.getLandOfGebiedAanvang());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                brpGroepEnum,
                GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG,
                relatie.getOmschrijvingLocatieAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.REDEN_EINDE_RELATIE, relatie.getRedenBeeindigingRelatie());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_EINDE, relatie.getDatumEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.GEMEENTE_EINDE, relatie.getGemeenteEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.WOONPLAATSNAAM_EINDE, relatie.getWoonplaatsnaamEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE, relatie.getBuitenlandsePlaatsEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.BUITENLANDSE_REGIO_EINDE, relatie.getBuitenlandseRegioEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE, relatie.getLandOfGebiedEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE, relatie.getOmschrijvingLocatieEinde());
        }
    }

    /**
     * Voeg Brp Document toe aan het voorkomen.
     *
     * @param ggoInhoud
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            De naam van de groep.
     */
    public final void addGroepDocument(final Map<String, String> ggoInhoud, final DocumentHistorie inhoud, final GgoBrpGroepEnum brpGroepEnum) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(ggoInhoud, GgoBrpGroepEnum.DOCUMENT, GgoBrpElementEnum.SOORT_DOCUMENT, inhoud.getDocument()
                                                                                                                           .getSoortDocument());
            ggoBrpValueConvert.verwerkElement(ggoInhoud, GgoBrpGroepEnum.DOCUMENT, GgoBrpElementEnum.IDENTIFICATIE, inhoud.getIdentificatie());
            ggoBrpValueConvert.verwerkElement(ggoInhoud, GgoBrpGroepEnum.DOCUMENT, GgoBrpElementEnum.AKTENUMMER, inhoud.getAktenummer());
            ggoBrpValueConvert.verwerkElement(ggoInhoud, GgoBrpGroepEnum.DOCUMENT, GgoBrpElementEnum.OMSCHRIJVING, inhoud.getOmschrijving());
            ggoBrpValueConvert.verwerkElement(ggoInhoud, GgoBrpGroepEnum.DOCUMENT, GgoBrpElementEnum.PARTIJ, inhoud.getPartij());
        }
    }

    /**
     * Voeg Brp OuderlijkGezag toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            Het label van de groep waar ouderlijkGezag aan wordt toegevoegd.
     */
    public final void addGroepOuderlijkGezag(
        final GgoBrpVoorkomen voorkomen,
        final BetrokkenheidOuderlijkGezagHistorie inhoud,
        final GgoBrpGroepEnum brpGroepEnum)
    {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.OUDER_HEEFT_GEZAG, inhoud.getIndicatieOuderHeeftGezag());
        }
    }

    /**
     * Voeg Brp Ouder toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            inhoud waarvan de elementen eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            De naam van de groep
     */
    public final void addGroepOuder(final GgoBrpVoorkomen voorkomen, final BetrokkenheidOuderHistorie inhoud, final GgoBrpGroepEnum brpGroepEnum) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.INDICATIE_OUDER, inhoud.getIndicatieOuder());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                brpGroepEnum,
                GgoBrpElementEnum.INDICATIE_OUDERUITWIEKINDISGEBOREN,
                inhoud.getIndicatieOuderUitWieKindIsGeboren());
        }
    }

    /**
     * Voeg BrpHistorie elementen toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param brpHistorie
     *            Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param brpGroepEnum
     *            De naam van de groep waaraan de historie wordt toegevoegd.
     */
    public final void addHistorie(final GgoBrpVoorkomen voorkomen, final FormeleHistorie brpHistorie, final GgoBrpGroepEnum brpGroepEnum) {
        if (brpHistorie != null) {
            if (brpHistorie instanceof MaterieleHistorie) {
                addGeldigheid(voorkomen, (MaterieleHistorie) brpHistorie, brpGroepEnum);
            }
            addRegistratieVerval(voorkomen, brpHistorie, brpGroepEnum);
            ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.NADERE_AANDUIDING_VERVAL, brpHistorie.getNadereAanduidingVerval());
        }
    }

    private void addGeldigheid(final GgoBrpVoorkomen voorkomen, final MaterieleHistorie brpHistorie, final GgoBrpGroepEnum brpGroepEnum) {
        String datAanvangGeldigheid = null;
        String datEindeGeldigheid = null;

        if (brpHistorie.getDatumAanvangGeldigheid() != null) {
            datAanvangGeldigheid = ViewerDateUtil.formatDatum(brpHistorie.getDatumAanvangGeldigheid());
        }

        if (brpHistorie.getDatumEindeGeldigheid() != null) {
            datEindeGeldigheid = ViewerDateUtil.formatDatum(brpHistorie.getDatumEindeGeldigheid());
        }

        final String geldigheid = joinDates(datAanvangGeldigheid, datEindeGeldigheid);
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_GELDIGHEID, geldigheid);
    }

    private void addRegistratieVerval(final GgoBrpVoorkomen voorkomen, final FormeleHistorie brpHistorie, final GgoBrpGroepEnum brpGroepEnum) {
        String datTijdRegistratie = null;
        String datTijdVerval = null;
        if (brpHistorie.getDatumTijdRegistratie() != null) {
            datTijdRegistratie = ViewerDateUtil.formatDatumTijdUtc(brpHistorie.getDatumTijdRegistratie());
        }
        if (brpHistorie.getDatumTijdVerval() != null) {
            datTijdVerval = ViewerDateUtil.formatDatumTijdUtc(brpHistorie.getDatumTijdVerval());
        }
        final String registratieVerval = joinDates(datTijdRegistratie, datTijdVerval);
        ggoBrpValueConvert.verwerkElement(voorkomen, brpGroepEnum, GgoBrpElementEnum.DATUM_TIJD_REGISTRATIE_VERVAL, registratieVerval);
    }

    /**
     * Voegt twee velden samen als een veld, gescheiden door ' - '. Note: Bij aanpassen van dit formaat ook de
     * BrpPersoonslijst.js controleren.
     *
     * @param datumVan
     *            String
     * @param datumTot
     *            String
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
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            De IST relatie waar de gegevens vandaan komen.
     */
    public final void addIstRelatie(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.ADMINISTRATIENUMMER, inhoud.getAnummer());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.BURGERSERVICENUMMER, inhoud.getBsn());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.PREDICAAT, inhoud.getPredicaat());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.VOORNAMEN, inhoud.getVoornamen());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.VOORVOEGSEL, inhoud.getVoorvoegsel());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.SCHEIDINGSTEKEN, inhoud.getScheidingsteken());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.ADELLIJKE_TITEL, inhoud.getAdellijkeTitel());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.GESLACHTSNAAMSTAM, inhoud.getGeslachtsnaamstam());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_GEBOORTE, inhoud.getDatumGeboorte());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.GEMEENTE_GEBOORTE, inhoud.getGemeenteGeboorte());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.BUITENLANDSE_PLAATS_GEBOORTE, inhoud.getBuitenlandsePlaatsGeboorte());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.OMSCHRIJVING_GEBOORTELOCATIE, inhoud.getOmschrijvingLocatieGeboorte());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.LAND_OF_GEBIED_GEBOORTE, inhoud.getLandOfGebiedGeboorte());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.GESLACHTSAANDUIDING, inhoud.getGeslachtsaanduiding());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                null,
                GgoBrpElementEnum.DATUM_INGANG_FAMILIERECHTELIJKE_BETREKKING,
                inhoud.getRubriek6210DatumIngangFamilierechtelijkeBetrekking());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }

    private void addIstStandaardGegevens(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.AKTENUMMER, inhoud.getAktenummer());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.OMSCHRIJVING, inhoud.getDocumentOmschrijving());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.SOORT_DOCUMENT, inhoud.getSoortDocument());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.PARTIJ, inhoud.getPartij());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_DOCUMENT, inhoud.getRubriek8220DatumDocument());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                null,
                GgoBrpElementEnum.AANDUIDING_GEGEVENS_IN_ONDERZOEK,
                inhoud.getRubriek8310AanduidingGegevensInOnderzoek());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_INGANG_ONDERZOEK, inhoud.getRubriek8320DatumIngangOnderzoek());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_EINDE_ONDERZOEK, inhoud.getRubriek8330DatumEindeOnderzoek());
            ggoBrpValueConvert.verwerkElement(
                voorkomen,
                null,
                GgoBrpElementEnum.ONJUIST_OF_STRIJDIG_MET_OPENBARE_ORDE,
                inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.INGANGSDATUM_GELDIGHEID, inhoud.getRubriek8510IngangsdatumGeldigheid());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_VAN_OPNEMING, inhoud.getRubriek8610DatumVanOpneming());
        }
    }

    /**
     * Voeg een IST gezagsVerhouding toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            De IST gezagsVerhouding waar de gegevens vandaan komen.
     */
    public final void addIstGezagsVerhouding(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.INDICATIE_OUDER1_HEEFT_GEZAG, inhoud.getIndicatieOuder1HeeftGezag());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.INDICATIE_OUDER2_HEEFT_GEZAG, inhoud.getIndicatieOuder2HeeftGezag());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.INDICATIE_DERDE_HEEFT_GEZAG, inhoud.getIndicatieDerdeHeeftGezag());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.INDICATIE_ONDER_CURATELE, inhoud.getIndicatieOnderCuratele());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }

    /**
     * Voeg een IST huwelijkOfGp toe aan het voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud
     *            De IST gezagsVerhouding waar de gegevens vandaan komen.
     */
    public final void addIstHuwelijkOfGp(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        if (inhoud != null) {
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_AANVANG, inhoud.getDatumAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.GEMEENTE_AANVANG, inhoud.getGemeenteAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.BUITENLANDSE_PLAATS_AANVANG, inhoud.getBuitenlandsePlaatsAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_AANVANG, inhoud.getOmschrijvingLocatieAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.LAND_OF_GEBIED_AANVANG, inhoud.getLandOfGebiedAanvang());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.REDEN_EINDE_RELATIE, inhoud.getRedenBeeindigingRelatie());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.DATUM_EINDE, inhoud.getDatumEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.GEMEENTE_EINDE, inhoud.getGemeenteEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.BUITENLANDSE_PLAATS_EINDE, inhoud.getBuitenlandsePlaatsEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.OMSCHRIJVING_LOCATIE_EINDE, inhoud.getOmschrijvingLocatieEinde());
            ggoBrpValueConvert.verwerkElement(voorkomen, null, GgoBrpElementEnum.LAND_OF_GEBIED_EINDE, inhoud.getLandOfGebiedEinde());

            addIstStandaardGegevens(voorkomen, inhoud);
        }
    }
}
