/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import java.util.Map;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Character;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoVoorkomen;
import nl.bzk.migratiebrp.ggo.viewer.service.Lo3StamtabelService;
import nl.bzk.migratiebrp.ggo.viewer.util.NaamUtil;
import nl.bzk.migratiebrp.ggo.viewer.util.VerwerkerUtil;
import nl.bzk.migratiebrp.ggo.viewer.util.ViewerDateUtil;
import org.springframework.stereotype.Component;

/**
 * Checkt de waarden en zet ze om naar viewer format.
 */
@Component
public class GgoLo3ValueConvert {
    /**
     * Getal wat opgeteld moet worden bij het actuele categorie nummer om een historisch categorie nummer te krijgen.
     */
    public static final int HISTORISCH = 50;
    /**
     * De lengte van het Lo3 element aanduiding gegevens in onderzoek.
     */
    public static final int AANDUIDING_GEGEVENS_IN_ONDERZOEK_LENGTH = 6;
    /**
     * De lengte van het Lo3 element versie nummer.
     */
    public static final int VERSIE_NUMMER_LENGTH = 4;
    /**
     * De lengte van het Lo3 element lengte houder.
     */
    public static final int LENGTE_HOUDER_LENGTH = 3;

    private static final String WHITE_SPACE = " ";

    @Inject
    private Lo3StamtabelService stamtabelService;

    /**
     * Zet de waarde om in een viewer geformatteerde waarde.
     *
     * @param value
     *            De eventueel om te zetten waarde.
     * @param element
     *            Element enum welke aangeeft welk element het is.
     * @return De omgezette waarde.
     */
    protected final String convertToViewerValue(final Lo3Element value, final Lo3ElementEnum element) {
        final String convertedValue;
        switch (element) {
            case ELEMENT_3570:
                convertedValue = stamtabelService.getAanduidingInhoudingVermissingNlReisdocument(value.getWaarde());
                break;
            case ELEMENT_7210:
                convertedValue = stamtabelService.getAangifteAdreshouding(value.getWaarde());
                break;
            case ELEMENT_0220:
                convertedValue = stamtabelService.getAdellijkeTitelPredikaat(value.getWaarde());
                break;
            case ELEMENT_3540:
                convertedValue = stamtabelService.getAutoriteitVanAfgifteReisdocument(value.getWaarde());
                break;
            case ELEMENT_0740:
                convertedValue = stamtabelService.getRedenEindeRelatie(value.getWaarde());
                break;
            case ELEMENT_6720:
                convertedValue = stamtabelService.getRedenOpschorting(value.getWaarde());
                break;
            case ELEMENT_3510:
                convertedValue = stamtabelService.getSoortNlReisdocument(value.getWaarde());
                break;
            case ELEMENT_1010:
                convertedValue = stamtabelService.getFunctieAdres(value.getWaarde());
                break;
            case ELEMENT_0820:
            case ELEMENT_6910:
            case ELEMENT_0320:
            case ELEMENT_0620:
            case ELEMENT_0720:
            case ELEMENT_0910:
            case ELEMENT_8210:
            case ELEMENT_8110:
                convertedValue = stamtabelService.getGemeente(value.getWaarde());
                break;
            case ELEMENT_6510:
                convertedValue = stamtabelService.getAanduidingBijzonderNederlandschap(value.getWaarde());
                break;
            case ELEMENT_3110:
                convertedValue = stamtabelService.getAanduidingEuropeesKiesrecht(value.getWaarde());
                break;
            case ELEMENT_1150:
                convertedValue = stamtabelService.getAanduidingHuisnummer(value.getWaarde());
                break;
            case ELEMENT_3810:
                convertedValue = stamtabelService.getAanduidingUitgeslotenKiesrecht(value.getWaarde());
                break;
            case ELEMENT_7010:
                convertedValue = stamtabelService.getIndicatieGeheim(value.getWaarde());
                break;
            case ELEMENT_8710:
                convertedValue = stamtabelService.getIndicatiePKVolledigGeconverteerdCode(value.getWaarde());
                break;
            case ELEMENT_1510:
                convertedValue = stamtabelService.getSoortVerbintenis(value.getWaarde());
                break;
            case ELEMENT_3310:
                convertedValue = stamtabelService.getIndicatieCurateleRegister(value.getWaarde());
                break;
            case ELEMENT_3210:
                convertedValue = stamtabelService.getIndicatieGezagMinderjarige(value.getWaarde());
                break;
            case ELEMENT_8410:
                convertedValue = stamtabelService.getIndicatieOnjuist(value.getWaarde());
                break;
            case ELEMENT_7510:
                convertedValue = stamtabelService.getIndicatieDocument(value.getWaarde());
                break;
            case ELEMENT_3610:
                convertedValue = stamtabelService.getSignalering(value.getWaarde());
                break;
            case ELEMENT_0830:
            case ELEMENT_0330:
            case ELEMENT_0630:
            case ELEMENT_0730:
            case ELEMENT_1310:
            case ELEMENT_1410:
                convertedValue = stamtabelService.getLandOfGebied(value.getWaarde());
                break;
            case ELEMENT_0410:
                convertedValue = stamtabelService.getGeslachtsaanduiding(value.getWaarde());
                break;
            case ELEMENT_0510:
                convertedValue = stamtabelService.getNationaliteit(value.getWaarde());
                break;
            case ELEMENT_6310:
                convertedValue = stamtabelService.getRedenVerkrijgingNederlandschap(value.getWaarde());
                break;
            case ELEMENT_6410:
                convertedValue = stamtabelService.getRedenVerliesNederlandschap(value.getWaarde());
                break;
            case ELEMENT_3910:
                convertedValue = stamtabelService.getVerblijfstitel(value.getWaarde());
                break;
            case ELEMENT_6110:
                convertedValue = stamtabelService.getNaamgebruik(value.getWaarde());
                break;
            case ELEMENT_3580:
                convertedValue = VerwerkerUtil.zeroPad(value.getWaarde(), LENGTE_HOUDER_LENGTH);
                break;
            case ELEMENT_8010:
                convertedValue = VerwerkerUtil.zeroPad(String.valueOf(Lo3Integer.unwrap((Lo3Integer) value)), VERSIE_NUMMER_LENGTH);
                break;
            case ELEMENT_8310:
                convertedValue = VerwerkerUtil.zeroPad(String.valueOf(Lo3Integer.unwrap((Lo3Integer) value)), AANDUIDING_GEGEVENS_IN_ONDERZOEK_LENGTH);
                break;
            case ELEMENT_8810:
                convertedValue = stamtabelService.getRNIDeelnemer((Lo3RNIDeelnemerCode) value);
                break;
            default:
                convertedValue = defaultConvertToViewerValue(value);
                break;
        }
        return convertedValue;
    }

    private String defaultConvertToViewerValue(final Lo3Element value) {
        final String convertedValue;
        if (value instanceof Lo3Datum) {
            convertedValue = ViewerDateUtil.formatDatum(((Lo3Datum) value).getIntegerWaarde());
        } else if (value instanceof Lo3Datumtijdstempel) {
            convertedValue = ViewerDateUtil.formatDatumTijd(((Lo3Datumtijdstempel) value).getLongWaarde());
        } else if (value instanceof Lo3String) {
            convertedValue = Lo3String.unwrap((Lo3String) value);
        } else if (value instanceof Lo3Character) {
            convertedValue = String.valueOf(Lo3Character.unwrap((Lo3Character) value));
        } else if (value instanceof Lo3Integer) {
            convertedValue = String.valueOf(Lo3Integer.unwrap((Lo3Integer) value));
        } else if (value instanceof Lo3Long) {
            convertedValue = String.valueOf(Lo3Long.unwrap((Lo3Long) value));
        } else {
            convertedValue = value.getWaarde();
        }
        return convertedValue;
    }

    /**
     * Maak een Ggo categorie key van een Lo3Categorie.
     *
     * @param categorie
     *            Lo3Categorie.
     * @param aNummer
     *            Het anummer van de PL.
     * @param catNr
     *            Het nummer van de categorie.
     * @param vermoedelijkStapelNr
     *            Het vermoedelijke stapelNr, te gebruiken als Herkomst niet is ingevuld
     * @param vermoedelijkVoorkomenNr
     *            Het vermoedelijke voorkomenNr, te gebruiken als Herkomst niet is ingevuld
     * @return GgoCategorieKey met de unieke plek in LO3.
     */
    public final GgoVoorkomen createVoorkomen(
        final Lo3Categorie<?> categorie,
        final Long aNummer,
        final int catNr,
        final int vermoedelijkStapelNr,
        final int vermoedelijkVoorkomenNr)
    {
        final GgoVoorkomen voorkomen = new GgoVoorkomen();

        voorkomen.setCategorieLabelNr(catNr);

        if (categorie.getLo3Herkomst() != null) {
            voorkomen.setCategorieNr(categorie.getLo3Herkomst().getCategorie().getCategorieAsInt());
            voorkomen.setStapelNr(categorie.getLo3Herkomst().getStapel());
            voorkomen.setVoorkomenNr(categorie.getLo3Herkomst().getVoorkomen());
        } else {
            voorkomen.setCategorieNr(catNr);
            voorkomen.setStapelNr(vermoedelijkStapelNr);
            voorkomen.setVoorkomenNr(vermoedelijkVoorkomenNr);
        }
        voorkomen.setaNummer(aNummer);
        // label halen bij de categorie
        voorkomen.setLabel(NaamUtil.createLo3CategorieLabel(catNr));

        if (categorie.getHistorie() != null) {
            if (categorie.getHistorie().isOnjuist()) {
                voorkomen.setVervallen(true);
            }
            if (Lo3CategorieEnum.CATEGORIE_07.getCategorieAsInt() != catNr && categorie.getHistorie().getIngangsdatumGeldigheid() != null) {
                voorkomen.setDatumAanvangGeldigheid(ViewerDateUtil.formatDatum(categorie.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde()));
            }
        }

        return voorkomen;
    }

    /**
     * Voeg een element toe aan voorkomen.
     *
     * @param voorkomen
     *            Voorkomen waar het element aan toegevoegd wordt.
     * @param lo3ElementEnum
     *            Het element.
     * @param value
     *            De waarde die gezet moet worden.
     */
    public final void addElement(final Map<String, String> voorkomen, final Lo3ElementEnum lo3ElementEnum, final Lo3Element value) {
        if (value != null && value.isInhoudelijkGevuld()) {
            voorkomen.put(lo3ElementEnum.getElementNummer(true) + WHITE_SPACE + lo3ElementEnum.getLabel(), convertToViewerValue(value, lo3ElementEnum));
        }
    }
}
