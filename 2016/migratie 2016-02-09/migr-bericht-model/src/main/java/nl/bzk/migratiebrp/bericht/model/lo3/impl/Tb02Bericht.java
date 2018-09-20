/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.AkteOnbekendException;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Tb02.
 */
public final class Tb02Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {

    private static final String INCORRECTE_AKTE_IN_BERICHT = "Aktenummer %s ongeschikt voor tb02";
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Voor Tb02 berichten gebruikte header informatie.
     */
    private static final Lo3Header HEADER = new Lo3Header(
        Lo3HeaderVeld.RANDOM_KEY,
        Lo3HeaderVeld.BERICHTNUMMER,
        Lo3HeaderVeld.HERHALING,
        Lo3HeaderVeld.AANTAL,
        Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
        Lo3HeaderVeld.AKTENUMMER);

    private List<Lo3CategorieWaarde> categorieen;
    private Lo3CategorieWaarde cat01;
    private Lo3CategorieWaarde cat51;
    private Lo3CategorieWaarde cat05;
    private Lo3CategorieWaarde cat55;

    /**
     * Constructor.
     */
    public Tb02Bericht() {
        super(HEADER, "Tb02", "uc309");

        setHeader(Lo3HeaderVeld.RANDOM_KEY, null);
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());
        setHeader(Lo3HeaderVeld.HERHALING, null);
        setHeader(Lo3HeaderVeld.AANTAL, String.valueOf(HEADER.getHeaderVelden().length));
        setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, null);
        setHeader(Lo3HeaderVeld.AKTENUMMER, null);
        categorieen = new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return null;
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> deCategorieen) {
        categorieen = deCategorieen;
        for (final Lo3CategorieWaarde waarde : categorieen) {
            switch (waarde.getCategorie()) {
                case CATEGORIE_01:
                    cat01 = waarde;
                    break;
                case CATEGORIE_51:
                    cat51 = waarde;
                    break;
                case CATEGORIE_05:
                    cat05 = waarde;
                    break;
                case CATEGORIE_55:
                    cat55 = waarde;
                    break;
                default:
                    LOG.debug("Categorie niet van belang voor controle");
            }
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        return categorieen;
    }

    /**
     * Geef het soort akte terug. Bepaling wordt gedaan obv header informatie.
     *
     * @throws AkteOnbekendException
     *             indien Akte geen tb02 akte is
     * @return Het soort akte
     */
    public AkteEnum getSoortAkte() throws AkteOnbekendException {
        final String aktenummer = getHeader(Lo3HeaderVeld.AKTENUMMER);
        try {
            return AkteEnum.valueOf(aktenummer.charAt(0), aktenummer.charAt(2));
        } catch (final IllegalArgumentException iae) {
            throw new AkteOnbekendException(String.format(INCORRECTE_AKTE_IN_BERICHT, aktenummer), iae);
        }
    }

    /**
     * Controleer of bericht de correcte elementen bevat.
     *
     * @throws AkteOnbekendException
     *             indien Akte onbekend is
     * @throws BerichtInhoudException
     *             indien tb02 bericht niet de juiste elementen bevat
     */
    public void controleerBerichtOpCorrectheid() throws AkteOnbekendException, BerichtInhoudException {
        final String aktenummer = getHeader(Lo3HeaderVeld.AKTENUMMER);
        try {
            final AkteEnum soortAkte = AkteEnum.valueOf(aktenummer.charAt(0), aktenummer.charAt(2));
            soortAkte.zijnJuisteCategorieenAanwezig(aktenummer, categorieen);
        } catch (final IllegalArgumentException iae) {
            throw new AkteOnbekendException(String.format(INCORRECTE_AKTE_IN_BERICHT, aktenummer), iae);
        } catch (final AkteEnum.AkteEnumException ae) {
            throw new BerichtInhoudException("Bericht inhoud is onjuist", ae);
        }
    }

    /**
     * Controleer of registergemeente akte overeenkomen.
     *
     * @throws BerichtInhoudException
     *             als registergemeente afwijkt van verzendende gemeente
     */
    public void controleerOfRegisterGemeenteOvereenkomtMetVerzendendeGemeente() throws BerichtInhoudException {
        for (final Lo3CategorieWaarde waarde : categorieen) {
            if (waarde.getElementen().containsKey(Lo3ElementEnum.ELEMENT_8110)) {
                if (!waarde.getElement(Lo3ElementEnum.ELEMENT_8110).equals(getBronGemeente())) {
                    throw new BerichtInhoudException(String.format(
                        "81.10 Registergemeente akte met waarde %s komt niet overeen met de verzendende gemeente %s van het bericht",
                        waarde.getElement(Lo3ElementEnum.ELEMENT_8110),
                        getBronGemeente()));
                }
            }
        }
    }

    /**
     * Controleer of aktenummer overeenkomt met aktenummer in header.
     *
     * @return true indien ze overeenkomen
     */
    public boolean isAktenummerHetzelfdeAlsAktenummerInHeader() {
        boolean result = true;
        for (final Lo3CategorieWaarde waarde : categorieen) {
            if (waarde.getElementen().containsKey(Lo3ElementEnum.ELEMENT_8120)) {
                result = result && waarde.getElement(Lo3ElementEnum.ELEMENT_8120).equals(getHeader(Lo3HeaderVeld.AKTENUMMER));
            }
        }
        return result;
    }

    /**
     * Controleer of ingangsdata van de akte overeenkomen.
     *
     * @return true indien ze overeenkomen
     */
    public boolean isIngangsdatumGelijkInMeegegevenAkten() {
        String ingangsdatum = null;
        boolean result = true;
        for (final Lo3CategorieWaarde waarde : categorieen) {
            if (waarde.getElementen().containsKey(Lo3ElementEnum.ELEMENT_8510)) {
                if (ingangsdatum == null) {
                    ingangsdatum = waarde.getElement(Lo3ElementEnum.ELEMENT_8510);
                } else {
                    result = result && waarde.getElement(Lo3ElementEnum.ELEMENT_8510).equals(ingangsdatum);
                }
            }
        }
        return result;
    }

    /**
     * Controleer of groep 2 en 3 niet is gewijzigd en of groep 15 wel gewijzigd is.
     *
     * @throws BerichtInhoudException
     *             als bericht inhoudelijk niet correct is
     * @throws AkteOnbekendException
     *             indien akte niet naar bestaand type kan worden omgezet
     */
    public void controleerGroepenOpWijzigingen() throws BerichtInhoudException, AkteOnbekendException {

        switch (getSoortAkte()) {
            case AKTE_1H:
                controleerGroep02Geslachtsnaamwijziging(cat01, cat51);
                controleerGroep03(cat01, cat51);
                controleerGroep04Ongewijzigd(cat01, cat51);
                break;
            case AKTE_1M:
                controleerGroep02Voornaamwijziging(cat01, cat51);
                controleerGroep03(cat01, cat51);
                controleerGroep04Ongewijzigd(cat01, cat51);
                break;
            case AKTE_1S:
                controleerGroep02Geslachtswijziging(cat01, cat51);
                controleerGroep03(cat01, cat51);
                controleerGroep04Gewijzigd(cat01, cat51);
                break;
            case AKTE_3A:
            case AKTE_3B:
            case AKTE_3H:
            case AKTE_5A:
            case AKTE_5B:
            case AKTE_5H:
                controleerGroep03(cat05, cat55);
                controleerGroep02Relatie(cat05, cat55);
                controleerGroep15(cat05, cat55);
                break;
            default:
                LOG.debug("Aktecontrole niet nodig");
        }

    }

    private void controleerGroep02Geslachtsnaamwijziging(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch)
        throws BerichtInhoudException
    {
        if (historisch != null && actueel != null) {
            boolean berichtOngewijzigd = isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0230);
            berichtOngewijzigd = berichtOngewijzigd && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0240);

            if (!(!berichtOngewijzigd && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0210) && isElementVanCategorieOngewijzigd(
                actueel,
                historisch,
                Lo3ElementEnum.ELEMENT_0220)))
            {
                throw new BerichtInhoudException(String.format("Voor aktenummer %s is een gewijzigde geslachtsnaam nodig. "
                                                               + "Groep 02 uit categorie 01 komt geheel overeen met groep 02 uit categorie 51 of "
                                                               + "element 02.10 is gewijzigd.", getHeader(Lo3HeaderVeld.AKTENUMMER)));
            }
        }
    }

    private void controleerGroep02Voornaamwijziging(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            boolean berichtOngewijzigd = isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0220);
            berichtOngewijzigd = berichtOngewijzigd && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0230);
            berichtOngewijzigd = berichtOngewijzigd && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0240);

            if (!(berichtOngewijzigd && !isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0210))) {
                throw new BerichtInhoudException(String.format(
                    "Voor aktenummer %s is een gewijzigde voornaam nodig. "
                            + "Groep 02 uit categorie 01 komt geheel overeen met groep 02 uit categorie 51 "
                            + "of een van de elementen 02.20, 02.30 of 02.40 is gewijzigd",
                    getHeader(Lo3HeaderVeld.AKTENUMMER)));
            }
        }
    }

    private void controleerGroep02Geslachtswijziging(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            boolean berichtCorrect = isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0220);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0230);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0240);

            if (!berichtCorrect) {
                throw new BerichtInhoudException(String.format(
                    "Voor aktenummer %s is het niet toegestaan om gelijktijdig een geslachtsnaamswijziging door te voeren. "
                            + "Groep 02 uit categorie %s komt niet overeen met groep 02 uit categorie %s.",
                    getHeader(Lo3HeaderVeld.AKTENUMMER),
                    actueel.getCategorie().getCategorie(),
                    historisch.getCategorie().getCategorie()));
            }
        }
    }

    private void controleerGroep02Relatie(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            boolean berichtCorrect = isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0210);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0220);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0230);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0240);

            if (!berichtCorrect) {
                throw new BerichtInhoudException(String.format(
                    "Voor aktenummer %s is het niet toegestaan om gelijktijdig een naamswijziging door te voeren. "
                            + "Groep 02 uit categorie %s komt niet geheel overeen met groep 02 uit categorie %s.",
                    getHeader(Lo3HeaderVeld.AKTENUMMER),
                    actueel.getCategorie().getCategorie(),
                    historisch.getCategorie().getCategorie()));
            }
        }
    }

    private void controleerGroep03(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            boolean berichtCorrect = isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0310);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0320);
            berichtCorrect = berichtCorrect && isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0330);
            if (!berichtCorrect) {
                throw new BerichtInhoudException(String.format(
                    "Groep 03 van de actuele categorie %s komt niet overeen met groep 03 van de historische categorie %s",
                    actueel.getCategorie().getCategorie(),
                    historisch.getCategorie().getCategorie()));
            }
        }
    }

    private void controleerGroep04Gewijzigd(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            if (isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0410)) {
                throw new BerichtInhoudException("Groep 04 van de actuele categorie 01 komt niet overeen met groep 04 van de historische categorie 51");
            }
        }
    }

    private void controleerGroep04Ongewijzigd(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException {
        if (historisch != null && actueel != null) {
            if (!isElementVanCategorieOngewijzigd(actueel, historisch, Lo3ElementEnum.ELEMENT_0410)) {
                throw new BerichtInhoudException("Groep 04 van de actuele categorie 01 komt overeen met groep 04 van de historische categorie 51");
            }
        }
    }

    private void controleerGroep15(final Lo3CategorieWaarde actueel, final Lo3CategorieWaarde historisch) throws BerichtInhoudException,
        AkteOnbekendException
    {
        if (historisch != null && actueel != null) {
            final AkteEnum soortAkte = getSoortAkte();
            if (AkteEnum.AKTE_3H == soortAkte || AkteEnum.AKTE_5H == soortAkte) {
                if (isElementVanCategorieOngewijzigd(historisch, actueel, Lo3ElementEnum.ELEMENT_1510)) {
                    throw new BerichtInhoudException(String.format(
                        "Voor aktenummer %s dient het soort verbintenis gewijzigd te zijn. "
                                + "Groep 15 van categorie 05 komt overeen met groep 15 van categorie 55",
                        getHeader(Lo3HeaderVeld.AKTENUMMER)));
                }
            } else {
                if (!isElementVanCategorieOngewijzigd(historisch, actueel, Lo3ElementEnum.ELEMENT_1510)) {
                    throw new BerichtInhoudException(String.format(
                        "Voor aktenummer %s dient het soort verbintenis ongewijzigd te zijn. "
                                + "Groep 15 van categorie 05 komt niet overeen met groep 15 van categorie 55",
                        getHeader(Lo3HeaderVeld.AKTENUMMER)));
                }
            }
        }
    }

    private boolean isElementVanCategorieOngewijzigd(
        final Lo3CategorieWaarde actueleCategorie,
        final Lo3CategorieWaarde historischeCategorie,
        final Lo3ElementEnum element)
    {
        return historischeCategorie.getElement(element) == null
               && actueleCategorie.getElement(element) == null
               || historischeCategorie.getElement(element) != null
               && historischeCategorie.getElement(element).equals(actueleCategorie.getElement(element));
    }

    /**
     * Geef waarde van gegeven categorie en element.
     *
     * @param categorie
     *            de gewenste categorie
     * @param element
     *            het gewenste element
     * @return de bijbehorende waarde
     */
    public String getWaarde(final Lo3CategorieEnum categorie, final Lo3ElementEnum element) {
        String result = null;
        final Lo3CategorieWaarde cat = getCategorie(categorieen, categorie);
        if (cat != null) {
            result = cat.getElement(element);
        }
        return result;
    }
}
