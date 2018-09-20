/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3CategorieWaardeFormatter;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstDecoder;
import nl.moderniseringgba.migratie.conversie.serialize.PersoonslijstEncoder;

/**
 * Tb02.
 */
public class Tb02Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {

    /**
     * Voor Tb02 berichten gebruikte header informatie.
     */
    protected static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING, Lo3HeaderVeld.AANTAL, Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN,
            Lo3HeaderVeld.AKTENUMMER);

    /**
     * Persoonslijst parser.
     */
    protected static final Lo3PersoonslijstParser PL_PARSER = new Lo3PersoonslijstParser();

    private static final long serialVersionUID = 1L;

    private String registratieGemeente;
    private String aktenummer;
    private Lo3Datum ingangsdatumGeldigheid;
    private transient Lo3Persoonslijst lo3Persoonslijst;

    /**
     * Constructor.
     */
    public Tb02Bericht() {
        super(HEADER);

        setHeader(Lo3HeaderVeld.RANDOM_KEY, null);
        setHeader(Lo3HeaderVeld.BERICHTNUMMER, getBerichtType());
        setHeader(Lo3HeaderVeld.HERHALING, null);
        setHeader(Lo3HeaderVeld.AANTAL, null);
        setHeader(Lo3HeaderVeld.IDENTIFICERENDE_RUBRIEKEN, null);
        setHeader(Lo3HeaderVeld.AKTENUMMER, null);
    }

    /* ************************************************************************************************************* */

    @Override
    protected final void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        try {
            setLo3Persoonslijst(PL_PARSER.parse(categorieen));
            // CHECKSTYLE:OFF - Catch all, anders final klapt de ESB final er lelijk uit
        } catch (final Exception e) {
            // CHECSTYLE:ON
            throw new BerichtInhoudException("Fout bij parsen lo3 persoonslijst", e);
        }
    }

    /* ************************************************************************************************************* */

    @Override
    public String getStartCyclus() {
        return "uc309";
    }

    @Override
    public String getBerichtType() {
        return "Tb02";
    }

    /**
     * Geeft de LO3 persoonslijst terug.
     * 
     * @return De LO3 persoonslijst.
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    /**
     * Zet de LO3 persoonslijst.
     * 
     * @param lo3Persoonslijst
     *            De te zetten LO3 persoonslijst.
     */
    public void setLo3Persoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        this.lo3Persoonslijst = lo3Persoonslijst;
    }

    /**
     * Geeft de registratiegemeente van de erkenning terug.
     * 
     * @return De registratiegemeente van de erkenning.
     */
    public String getRegistratieGemeente() {
        return registratieGemeente;
    }

    /**
     * Geeft het aktenummer van de erkenning terug.
     * 
     * @return Het aktenummer van de erkenning.
     */
    public String getAktenummer() {
        return aktenummer;
    }

    /**
     * Geeft de ingangsdatum van de erkenning terug.
     * 
     * @return De ingangsdatum van de erkenning.
     */
    public Lo3Datum getIngangsdatumGeldigheid() {
        return ingangsdatumGeldigheid;
    }

    /**
     * Zet de registratiegemeente van de erkenning.
     * 
     * @param registratieGemeente
     *            De te zetten gemeente.
     */
    public void setRegistratieGemeente(final String registratieGemeente) {
        this.registratieGemeente = registratieGemeente;
    }

    /**
     * Zet het aktenummer van de erkenning.
     * 
     * @param aktenummer
     *            Het te zetten aktenummer.
     */
    public void setAktenummer(final String aktenummer) {
        this.aktenummer = aktenummer;
    }

    /**
     * Zet de ingangsdatum van de erkenning.
     * 
     * @param ingangsdatumGeldigheid
     *            De te zetten ingangsdatum.
     */
    public void setIngangsdatumGeldigheid(final Lo3Datum ingangsdatumGeldigheid) {
        this.ingangsdatumGeldigheid = ingangsdatumGeldigheid;
    }

    /**
     * Voegt de persoonscategorieën (categorie 01 & 51) toe aan de formatter.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 01 & 51 worden toegevoegd.
     */
    protected void voegPersoonsCategorieenToe(final Lo3CategorieWaardeFormatter formatter) {
        voegCategorie01Toe(formatter);
        voegCategorie51Toe(formatter);
    }

    /**
     * Voegt de persoonscategorieën (categorie 02 & 52) toe aan de formatter.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 02 & 52 worden toegevoegd.
     */
    protected void voegOuder1CategorieenToe(final Lo3CategorieWaardeFormatter formatter) {
        voegCategorie02Toe(formatter);
        voegCategorie52Toe(formatter);
    }

    /**
     * Voegt de persoonscategorieën (categorie 03 & 53) toe aan de formatter.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 03 & 53 worden toegevoegd.
     */
    protected void voegOuder2CategorieenToe(final Lo3CategorieWaardeFormatter formatter) {
        voegCategorie03Toe(formatter);
        voegCategorie53Toe(formatter);
    }

    /**
     * Voegt aan de formatter categorie 01 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 01 wordt toegevoegd.
     */
    private void voegCategorie01Toe(final Lo3CategorieWaardeFormatter formatter) {

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = getLo3Persoonslijst().getPersoonStapel();
        final Lo3Categorie<Lo3PersoonInhoud> persoonActueleCategorie = persoonStapel.getMeestRecenteElement();
        final Lo3PersoonInhoud persoonInhoud = persoonActueleCategorie.getInhoud();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_01);
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(persoonInhoud.getVoornamen()));
        formatter.element(Lo3ElementEnum.ELEMENT_0220,
                Lo3Format.format(persoonInhoud.getAdellijkeTitelPredikaatCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(persoonInhoud.getVoorvoegselGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(persoonInhoud.getGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(persoonInhoud.getGeboortedatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(persoonInhoud.getGeboorteGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(persoonInhoud.getGeboorteLandCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0410, Lo3Format.format(persoonInhoud.getGeslachtsaanduiding()));
        formatter.element(Lo3ElementEnum.ELEMENT_8110, Lo3Format.format(registratieGemeente));
        formatter.element(Lo3ElementEnum.ELEMENT_8120, Lo3Format.format(aktenummer));
        formatter.element(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(ingangsdatumGeldigheid));

    }

    /**
     * Voegt aan de formatter categorie 51 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 51 wordt toegevoegd.
     */
    private void voegCategorie51Toe(final Lo3CategorieWaardeFormatter formatter) {

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = getLo3Persoonslijst().getPersoonStapel();
        final Lo3Categorie<Lo3PersoonInhoud> persoonActueleCategorie = persoonStapel.getMeestRecenteElement();
        final Lo3Categorie<Lo3PersoonInhoud> persoonHistorieCategorie =
                persoonStapel.getVorigElement(persoonActueleCategorie);
        if (persoonHistorieCategorie != null) {
            final Lo3PersoonInhoud persoonHistorieInhoud = persoonHistorieCategorie.getInhoud();
            if (persoonHistorieInhoud != null) {
                formatter.categorie(Lo3CategorieEnum.CATEGORIE_51);
                formatter
                        .element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(persoonHistorieInhoud.getVoornamen()));
                formatter.element(Lo3ElementEnum.ELEMENT_0220,
                        Lo3Format.format(persoonHistorieInhoud.getAdellijkeTitelPredikaatCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0230,
                        Lo3Format.format(persoonHistorieInhoud.getVoorvoegselGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0240,
                        Lo3Format.format(persoonHistorieInhoud.getGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0310,
                        Lo3Format.format(persoonHistorieInhoud.getGeboortedatum()));
                formatter.element(Lo3ElementEnum.ELEMENT_0320,
                        Lo3Format.format(persoonHistorieInhoud.getGeboorteGemeenteCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0330,
                        Lo3Format.format(persoonHistorieInhoud.getGeboorteLandCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0410,
                        Lo3Format.format(persoonHistorieInhoud.getGeslachtsaanduiding()));
            }
        }

    }

    /**
     * Voegt aan de formatter categorie 02 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 02 wordt toegevoegd.
     */
    private void voegCategorie02Toe(final Lo3CategorieWaardeFormatter formatter) {

        final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = getLo3Persoonslijst().getOuder1Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = ouder1Stapels.get(0);
        final Lo3OuderInhoud ouder1Inhoud = ouder1Stapel.getMeestRecenteElement().getInhoud();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_02);
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(ouder1Inhoud.getVoornamen()));
        formatter.element(Lo3ElementEnum.ELEMENT_0220,
                Lo3Format.format(ouder1Inhoud.getAdellijkeTitelPredikaatCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(ouder1Inhoud.getVoorvoegselGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(ouder1Inhoud.getGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(ouder1Inhoud.getGeboortedatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(ouder1Inhoud.getGeboorteGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(ouder1Inhoud.getGeboorteLandCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0410, Lo3Format.format(ouder1Inhoud.getGeslachtsaanduiding()));
        formatter.element(Lo3ElementEnum.ELEMENT_6210,
                Lo3Format.format(ouder1Inhoud.getFamilierechtelijkeBetrekking()));
        formatter.element(Lo3ElementEnum.ELEMENT_8110, Lo3Format.format(registratieGemeente));
        formatter.element(Lo3ElementEnum.ELEMENT_8120, Lo3Format.format(aktenummer));
        formatter.element(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(ingangsdatumGeldigheid));

    }

    /**
     * Voegt aan de formatter categorie 52 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 52 wordt toegevoegd.
     */
    private void voegCategorie52Toe(final Lo3CategorieWaardeFormatter formatter) {

        final List<Lo3Stapel<Lo3OuderInhoud>> ouder1Stapels = getLo3Persoonslijst().getOuder1Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = ouder1Stapels.get(0);
        final Lo3Categorie<Lo3OuderInhoud> ouder1ActueleCategorie = ouder1Stapel.getMeestRecenteElement();
        final Lo3Categorie<Lo3OuderInhoud> ouder1HistorieCategorie =
                ouder1Stapel.getVorigElement(ouder1ActueleCategorie);
        if (ouder1HistorieCategorie != null) {
            final Lo3OuderInhoud ouder1HistorieInhoud = ouder1HistorieCategorie.getInhoud();
            if (ouder1HistorieInhoud != null) {
                formatter.categorie(Lo3CategorieEnum.CATEGORIE_52);
                formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(ouder1HistorieInhoud.getVoornamen()));
                formatter.element(Lo3ElementEnum.ELEMENT_0220,
                        Lo3Format.format(ouder1HistorieInhoud.getAdellijkeTitelPredikaatCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0230,
                        Lo3Format.format(ouder1HistorieInhoud.getVoorvoegselGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0240,
                        Lo3Format.format(ouder1HistorieInhoud.getGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0310,
                        Lo3Format.format(ouder1HistorieInhoud.getGeboortedatum()));
                formatter.element(Lo3ElementEnum.ELEMENT_0320,
                        Lo3Format.format(ouder1HistorieInhoud.getGeboorteGemeenteCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(ouder1HistorieInhoud.getGeboorteLandCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_6210,
                        Lo3Format.format(ouder1HistorieInhoud.getFamilierechtelijkeBetrekking()));
            }
        }

    }

    /**
     * Voegt aan de formatter categorie 03 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 03 wordt toegevoegd.
     */
    private void voegCategorie03Toe(final Lo3CategorieWaardeFormatter formatter) {

        final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = getLo3Persoonslijst().getOuder2Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = ouder2Stapels.get(0);
        final Lo3OuderInhoud ouder2Inhoud = ouder2Stapel.getMeestRecenteElement().getInhoud();
        formatter.categorie(Lo3CategorieEnum.CATEGORIE_03);
        formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(ouder2Inhoud.getVoornamen()));
        formatter.element(Lo3ElementEnum.ELEMENT_0220,
                Lo3Format.format(ouder2Inhoud.getAdellijkeTitelPredikaatCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0230, Lo3Format.format(ouder2Inhoud.getVoorvoegselGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format(ouder2Inhoud.getGeslachtsnaam()));
        formatter.element(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(ouder2Inhoud.getGeboortedatum()));
        formatter.element(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(ouder2Inhoud.getGeboorteGemeenteCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(ouder2Inhoud.getGeboorteLandCode()));
        formatter.element(Lo3ElementEnum.ELEMENT_0410, Lo3Format.format(ouder2Inhoud.getGeslachtsaanduiding()));
        formatter.element(Lo3ElementEnum.ELEMENT_6210,
                Lo3Format.format(ouder2Inhoud.getFamilierechtelijkeBetrekking()));
        formatter.element(Lo3ElementEnum.ELEMENT_8110, Lo3Format.format(registratieGemeente));
        formatter.element(Lo3ElementEnum.ELEMENT_8120, Lo3Format.format(aktenummer));
        formatter.element(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(ingangsdatumGeldigheid));

    }

    /**
     * Voegt aan de formatter categorie 53 toe.
     * 
     * @param formatter
     *            De formatter waaraan de categorie 53 wordt toegevoegd.
     */
    private void voegCategorie53Toe(final Lo3CategorieWaardeFormatter formatter) {

        final List<Lo3Stapel<Lo3OuderInhoud>> ouder2Stapels = getLo3Persoonslijst().getOuder2Stapels();
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = ouder2Stapels.get(0);
        final Lo3Categorie<Lo3OuderInhoud> ouder2ActueleCategorie = ouder2Stapel.getMeestRecenteElement();
        final Lo3Categorie<Lo3OuderInhoud> ouder2HistorieCategorie =
                ouder2Stapel.getVorigElement(ouder2ActueleCategorie);
        if (ouder2HistorieCategorie != null) {
            final Lo3OuderInhoud ouder2HistorieInhoud = ouder2HistorieCategorie.getInhoud();
            if (ouder2HistorieInhoud != null) {
                formatter.categorie(Lo3CategorieEnum.CATEGORIE_53);
                formatter.element(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format(ouder2HistorieInhoud.getVoornamen()));
                formatter.element(Lo3ElementEnum.ELEMENT_0220,
                        Lo3Format.format(ouder2HistorieInhoud.getAdellijkeTitelPredikaatCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0230,
                        Lo3Format.format(ouder2HistorieInhoud.getVoorvoegselGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0240,
                        Lo3Format.format(ouder2HistorieInhoud.getGeslachtsnaam()));
                formatter.element(Lo3ElementEnum.ELEMENT_0310,
                        Lo3Format.format(ouder2HistorieInhoud.getGeboortedatum()));
                formatter.element(Lo3ElementEnum.ELEMENT_0320,
                        Lo3Format.format(ouder2HistorieInhoud.getGeboorteGemeenteCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(ouder2HistorieInhoud.getGeboorteLandCode()));
                formatter.element(Lo3ElementEnum.ELEMENT_6210,
                        Lo3Format.format(ouder2HistorieInhoud.getFamilierechtelijkeBetrekking()));
            }
        }

    }

    /* ********************************************************************************************************** */

    private void readObject(final ObjectInputStream is) throws ClassNotFoundException, IOException {
        // always perform the default de-serialization first
        is.defaultReadObject();

        if (is.available() > 0) {
            lo3Persoonslijst = PersoonslijstDecoder.decodeLo3Persoonslijst(is);
        }
    }

    private void writeObject(final ObjectOutputStream os) throws IOException {
        // perform the default serialization for all non-transient, non-static fields
        os.defaultWriteObject();

        if (lo3Persoonslijst != null) {
            PersoonslijstEncoder.encodePersoonslijst(lo3Persoonslijst, os);
        }
    }
}
