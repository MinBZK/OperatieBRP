/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.brp;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.StapelVoorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.migratiebrp.ggo.viewer.builder.lo3.GgoLo3ValueConvert;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpGroepEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoBrpVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Checkt de waarden en zet ze om naar viewer format.
 */
@Component("newGgoBrpValueConvert")
public class GgoBrpValueConvert {
    /**
     * Code weergave.
     */
    private static final String CODE_WEERGAVE = "%s (%s)";
    /**
     * Code weergave (geslacht).
     */
    private static final String CODE_WEERGAVE_GESL = "%s (%s / %s)";

    /**
     * Zet de waarde om in een viewer geformatteerde waarde.
     * @param value De eventueel om te zetten waarde.
     * @param element Element enum welke aangeeft welk element het is.
     * @return De omgezette waarde.
     */
    final String convertToViewerValue(final Object value, final GgoBrpElementEnum element) {
        final String convertedValue;
        switch (element) {
            case AANGEVER_ADRESHOUDING:
            case AANGEVER_MIGRATIE:
                final Aangever aangever = (Aangever) value;
                convertedValue = maakCodeWeergave(aangever.getCode(), aangever.getNaam());
                break;
            case ADELLIJKE_TITEL:
                final AdellijkeTitel adellijkeTitel = (AdellijkeTitel) value;
                convertedValue = maakCodeWeergave(adellijkeTitel.getCode(), adellijkeTitel.getNaamMannelijk(), adellijkeTitel.getNaamVrouwelijk());
                break;
            case BIJHOUDINGSAARD:
                final Bijhoudingsaard bijhoudingsaard = (Bijhoudingsaard) value;
                convertedValue = maakCodeWeergave(bijhoudingsaard.getCode(), bijhoudingsaard.getNaam());
                break;
            case PARTIJ:
            case BIJHOUDINGSPARTIJ:
            case GEMEENTE_PK:
                final Partij partij = (Partij) value;
                convertedValue = maakCodeWeergave(partij.getCode(), partij.getNaam());
                break;
            case INGANGSDATUM_GELDIGHEID:
                convertedValue = converteerDatum((Integer) value);
                break;
            case GEMEENTE:
            case GEMEENTE_AANVANG:
            case GEMEENTE_EINDE:
            case GEMEENTE_GEBOORTE:
                final Gemeente gemeente = (Gemeente) value;
                convertedValue = maakCodeWeergave(gemeente.getCode(), gemeente.getNaam());
                break;
            case GESLACHTSAANDUIDING:
                final Geslachtsaanduiding geslacht = (Geslachtsaanduiding) value;
                convertedValue = maakCodeWeergave(geslacht.getCode(), geslacht.getNaam());
                break;
            case LAND_OF_GEBIED_MIGRATIE:
            case LAND_OF_GEBIED_AANVANG:
            case LAND_OF_GEBIED_EINDE:
            case LAND_OF_GEBIED_GEBOORTE:
            case LAND_OF_GEBIED:
                final LandOfGebied landOfGebied = (LandOfGebied) value;
                convertedValue = maakCodeWeergave(landOfGebied.getCode(), landOfGebied.getNaam());
                break;
            case NADERE_BIJHOUDINGSAARD:
                final NadereBijhoudingsaard nadereBijhoudingsaard = (NadereBijhoudingsaard) value;
                convertedValue = maakCodeWeergave(nadereBijhoudingsaard.getCode(), nadereBijhoudingsaard.getNaam());
                break;
            case NATIONALITEIT:
                final Nationaliteit nationaliteit = (Nationaliteit) value;
                convertedValue = maakCodeWeergave(nationaliteit.getCode(), nationaliteit.getNaam());
                break;
            case AUTORITEIT_VAN_AFGIFTE:
            case LOCATIE_TOV_ADRES:
            case WOONPLAATSNAAM_AANVANG:
            case WOONPLAATSNAAM_EINDE:
            case WOONPLAATS:
                convertedValue = (String) value;
                break;
            case PREDICAAT:
                final Predicaat predicaat = (Predicaat) value;
                convertedValue = maakCodeWeergave(predicaat.getCode(), predicaat.getNaamMannelijk(), predicaat.getNaamVrouwelijk());
                break;
            case REDEN_EINDE_RELATIE:
                final RedenBeeindigingRelatie redenEinde = (RedenBeeindigingRelatie) value;
                convertedValue = maakCodeWeergave(redenEinde.getCode(), redenEinde.getOmschrijving());
                break;
            case AANDUIDING_INHOUDING_OF_VERMISSING:
                final AanduidingInhoudingOfVermissingReisdocument aanduiding = (AanduidingInhoudingOfVermissingReisdocument) value;
                convertedValue = maakCodeWeergave(aanduiding.getCode(), aanduiding.getNaam());
                break;
            case REDEN_VERKRIJGING_NEDERLANDSCHAP:
                final RedenVerkrijgingNLNationaliteit redenVerkrijging = (RedenVerkrijgingNLNationaliteit) value;
                convertedValue = maakCodeWeergave(redenVerkrijging.getCode(), redenVerkrijging.getOmschrijving());
                break;
            case REDEN_VERLIES_NEDERLANDSCHAP:
                final RedenVerliesNLNationaliteit redenVerlies = (RedenVerliesNLNationaliteit) value;
                convertedValue = maakCodeWeergave(redenVerlies.getCode(), redenVerlies.getOmschrijving());
                break;
            case REDEN_WIJZIGING_ADRES:
            case REDEN_WIJZIGING_MIGRATIE:
                final RedenWijzigingVerblijf redenWijziging = (RedenWijzigingVerblijf) value;
                convertedValue = maakCodeWeergave(redenWijziging.getCode(), redenWijziging.getNaam());
                break;
            case SOORT:
                final SoortNederlandsReisdocument soortReisdocument = (SoortNederlandsReisdocument) value;
                convertedValue = maakCodeWeergave(soortReisdocument.getCode(), soortReisdocument.getOmschrijving());
                break;
            case SOORT_ACTIE:
                final SoortActie soortActie = (SoortActie) value;
                convertedValue = soortActie.getNaam();
                break;
            case SOORT_ADRES:
                final SoortAdres functieAdres = (SoortAdres) value;
                convertedValue = maakCodeWeergave(functieAdres.getCode(), functieAdres.getNaam());
                break;
            case SOORT_DOCUMENT:
                final SoortDocument soortDocument = (SoortDocument) value;
                convertedValue = soortDocument.getNaam();
                break;
            case SOORT_MIGRATIE:
                final SoortMigratie soortMigratie = (SoortMigratie) value;
                convertedValue = maakCodeWeergave(soortMigratie.getCode(), soortMigratie.getNaam());
                break;
            case SOORT_ADMINISTRATIEVE_HANDELING:
                final SoortAdministratieveHandeling soortAh = (SoortAdministratieveHandeling) value;
                convertedValue = soortAh.getNaam();
                break;
            case AANDUIDING_VERBLIJFSRECHT:
                final Verblijfsrecht verblijfsrecht = (Verblijfsrecht) value;
                convertedValue = verblijfsrecht.getOmschrijving();
                break;
            case VERSIENUMMER:
                final Long versienummer = (Long) value;
                convertedValue = VerwerkerUtil.zeroPad(String.valueOf(versienummer), GgoLo3ValueConvert.VERSIE_NUMMER_LENGTH);
                break;
            case NAAMGEBRUIK:
                final Naamgebruik naamgebruik = (Naamgebruik) value;
                convertedValue = maakCodeWeergave(naamgebruik.getCode(), naamgebruik.getNaam());
                break;
            case AUTORITEIT_VAN_AFGIFTE_BUITENLANDS_PERSOONSNUMMER:
                final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifte = (AutoriteitAfgifteBuitenlandsPersoonsnummer) value;
                convertedValue = maakCodeWeergave(String.valueOf(autoriteitAfgifte.getCode()), autoriteitAfgifte.getLandOfGebied().getNaam());
                break;
            default:
                convertedValue = defaultConvertToViewerValue(value, element);
                break;
        }
        return convertedValue;
    }

    private String maakCodeWeergave(final String code, final String waarde) {
        return String.format(CODE_WEERGAVE, code, waarde);
    }

    private String maakCodeWeergave(final Character code, final String waarde) {
        return String.format(CODE_WEERGAVE, code, waarde);
    }

    private String maakCodeWeergave(final String code, final String waarde, final String waarde2) {
        return String.format(CODE_WEERGAVE_GESL, code, waarde, waarde2);
    }

    private String converteerDatum(final Integer value) {
        final String convertedValue;
        if (value == null) {
            convertedValue = null;
        } else {
            convertedValue = ViewerDateUtil.formatDatum(value);
        }
        return convertedValue;
    }

    /**
     * Wanneer het element geen specifieke conversie vereist, converteer attribuut op basis van class.
     */
    private String defaultConvertToViewerValue(final Object value, final GgoBrpElementEnum element) {
        final String convertedValue;

        if (element.name().startsWith("DATUM_") && value instanceof Integer) {
            convertedValue = converteerDatum((Integer) value);
        } else if (value instanceof String) {
            convertedValue = (String) value;
        } else if (value instanceof Timestamp) {
            convertedValue = ViewerDateUtil.formatDatumTijdUtc((Timestamp) value);
        } else if (value instanceof Boolean) {
            convertedValue = getBooleanValue((Boolean) value);
        } else {
            convertedValue = String.valueOf(value);
        }

        return convertedValue;
    }

    /**
     * Maak een GgoBrpVoorkomen.
     * @param brpGroep BrpGroep<? extends BrpGroepInhoud>.
     * @param aNummer Het anummer van de persoon.
     * @param ggoBrpGroepEnum De label van de BrpGroep.
     * @param brpStapelNr Het index nummer van de brp stapel.
     * @return GgoCategorieKey met de waarden.
     */
    public final GgoBrpVoorkomen createGgoBrpVoorkomen(
            final FormeleHistorie brpGroep,
            final String aNummer,
            final GgoBrpGroepEnum ggoBrpGroepEnum,
            final int brpStapelNr) {
        final GgoBrpVoorkomen voorkomen = new GgoBrpVoorkomen();
        voorkomen.setInhoud(new LinkedHashMap<>());

        if (brpGroep.getActieInhoud() != null && brpGroep.getActieInhoud().getLo3Voorkomen() != null) {
            final Lo3Voorkomen lo3Herkomst = brpGroep.getActieInhoud().getLo3Voorkomen();
            voorkomen.setCategorieNr(Integer.parseInt(lo3Herkomst.getCategorie()));
            voorkomen.setStapelNr(lo3Herkomst.getStapelvolgnummer());
            voorkomen.setVoorkomenNr(lo3Herkomst.getVoorkomenvolgnummer());
            voorkomen.setBrpStapelNr(brpStapelNr);
        }
        voorkomen.setaNummer(aNummer);
        voorkomen.setLabel(ggoBrpGroepEnum.getLabel());

        if (brpGroep.getDatumTijdVerval() != null) {
            voorkomen.setVervallen(true);
        }
        if (brpGroep instanceof MaterieleHistorie) {
            final MaterieleHistorie materieleHistorie = (MaterieleHistorie) brpGroep;
            if (materieleHistorie.getDatumAanvangGeldigheid() != null) {
                voorkomen.setDatumAanvangGeldigheid(ViewerDateUtil.formatDatum(materieleHistorie.getDatumAanvangGeldigheid()));
            }
        }

        return voorkomen;
    }

    /**
     * Bepaal vervallen en datum geldigheid.
     * @param voorkomen Het voorkomen.
     * @param inhoud De inhoud.
     */
    public final void bepaalVervallenEnDatumGeldigheid(final GgoBrpVoorkomen voorkomen, final StapelVoorkomen inhoud) {
        final Character onjuist = inhoud.getRubriek8410OnjuistOfStrijdigOpenbareOrde();
        if (onjuist != null && (onjuist.equals(Character.valueOf('O')) || onjuist.equals(Character.valueOf('S')))) {
            voorkomen.setVervallen(true);
        }
        if (inhoud.getRubriek8510IngangsdatumGeldigheid() != null) {
            voorkomen.setDatumAanvangGeldigheid(ViewerDateUtil.formatDatum(inhoud.getRubriek8510IngangsdatumGeldigheid()));
        }
    }

    /**
     * Geeft de tekstuele waarde van een Boolean terug.
     * @param booleanValue Boolean.
     * @return String Ja of Nee
     */
    public final String getBooleanValue(final Boolean booleanValue) {
        if (booleanValue != null && booleanValue) {
            return "Ja";
        } else {
            return "Nee";
        }
    }

    /**
     * Voeg een element toe aan voorkomen. Verwerk bovendien de onderzoeksgegevens.
     * @param voorkomen Voorkomen waar het element aan toegevoegd wordt.
     * @param brpElementEnum Het element.
     * @param value De waarde die gezet moet worden. Als deze null is wordt niets toegevoegd.
     */
    public final void verwerkElement(
            final GgoBrpVoorkomen voorkomen,
            final GgoBrpElementEnum brpElementEnum,
            final Object value) {
        verwerkElement(voorkomen.getInhoud(), brpElementEnum, value);
    }

    /**
     * Voeg een element toe aan de Map<String,String> ggoInhoud. Verwerk bovendien de onderzoeksgegevens.
     * @param ggoInhoud Doorgaans de inhoud van het meegegeven voorkomen. In enkele gevallen (samenvatting, acties) staat deze Map<String,String> echter los van
     * een voorkomen.
     * @param brpElementEnum Het element.
     * @param value De waarde die gezet moet worden. Als deze null is wordt niets toegevoegd.
     */
    public final void verwerkElement(
            final Map<String, String> ggoInhoud,
            final GgoBrpElementEnum brpElementEnum,
            final Object value) {
        if (value != null) {
            ggoInhoud.put(brpElementEnum.getLabel(), convertToViewerValue(value, brpElementEnum));
        }
    }
}
