/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Huisnummer;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 08: Verblijfplaats.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3VerblijfplaatsPrecondities extends Lo3Precondities {

    private static final String MAXIMAAL_24 = "MAXIMAAL-24";
    private static final String MAXIMAAL_16 = "MAXIMAAL-16";
    private static final String MAXIMAAL_35 = "MAXIMAAL-35";

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel) {
        if (stapel == null) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie) {
        final Lo3VerblijfplaatsInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 09: Gemeente
        if (!isGroepAanwezig(inhoud.getGemeenteInschrijving(), inhoud.getDatumInschrijving())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE036,
                    "Groep 09: Gemeente moet verplicht voorkomen in categorie 08: Verblijfplaats.")

            ;
        } else {
            controleerGroep09Gemeente(inhoud.getGemeenteInschrijving(), inhoud.getDatumInschrijving(), herkomst);
        }

        final boolean groep10Aanwezig =
                isGroepAanwezig(inhoud.getFunctieAdres(), inhoud.getGemeenteDeel(), inhoud.getAanvangAdreshouding());
        final boolean groep11Aanwezig =
                isGroepAanwezig(inhoud.getStraatnaam(), inhoud.getNaamOpenbareRuimte(), inhoud.getHuisnummer(),
                        inhoud.getHuisletter(), inhoud.getHuisnummertoevoeging(), inhoud.getAanduidingHuisnummer(),
                        inhoud.getPostcode(), inhoud.getWoonplaatsnaam(),
                        inhoud.getIdentificatiecodeVerblijfplaats(), inhoud.getIdentificatiecodeNummeraanduiding());
        final boolean groep12Aanwezig = isGroepAanwezig(inhoud.getLocatieBeschrijving());
        final boolean groep13Aanwezig =
                isGroepAanwezig(inhoud.getLandWaarnaarVertrokken(), inhoud.getDatumVertrekUitNederland(),
                        inhoud.getAdresBuitenland1(), inhoud.getAdresBuitenland2(), inhoud.getAdresBuitenland3());
        final boolean groep14Aanwezig =
                isGroepAanwezig(inhoud.getLandVanwaarIngeschreven(), inhoud.getVestigingInNederland());

        controleerGroepRelaties(groep10Aanwezig, groep11Aanwezig, groep12Aanwezig, groep13Aanwezig, groep14Aanwezig,
                herkomst);

        // Groep 10: Adreshouding
        if (groep10Aanwezig) {
            controleerGroep10Adreshouding(inhoud.getFunctieAdres(), inhoud.getGemeenteDeel(),
                    inhoud.getAanvangAdreshouding(), herkomst);
        }

        // Groep 11: Adres
        if (groep11Aanwezig) {
            controleerGroep11Adres(
                    new BasisAdres(inhoud.getStraatnaam(), inhoud.getHuisnummer(), inhoud.getWoonplaatsnaam(), inhoud
                            .getHuisnummertoevoeging(), inhoud.getAanduidingHuisnummer(), inhoud.getPostcode()),
                    inhoud.getNaamOpenbareRuimte(), inhoud.getIdentificatiecodeVerblijfplaats(), inhoud
                            .getIdentificatiecodeNummeraanduiding(), herkomst);
        }

        // Groep 12: Locatie
        if (groep12Aanwezig) {
            controleerGroep12Locatie(inhoud.getLocatieBeschrijving(), herkomst);
        }

        // Groep 13: Emigratie
        if (groep13Aanwezig) {
            controleerGroep13Emigratie(inhoud.getLandWaarnaarVertrokken(), inhoud.getDatumVertrekUitNederland(),
                    inhoud.getAdresBuitenland1(), inhoud.getAdresBuitenland2(), inhoud.getAdresBuitenland3(),
                    herkomst);
        }

        // Groep 14: Immigratie
        if (groep14Aanwezig) {
            controleerGroep14Immigratie(inhoud.getLandVanwaarIngeschreven(), inhoud.getVestigingInNederland(),
                    herkomst);
        }

        // Groep 72: Adesaangifte - geen controles
        if (!isGroepAanwezig(inhoud.getAangifteAdreshouding())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 72: Adresaangifte moet verplicht voorkomen in categorie 08: Verblijfplaats.");
        } else {
            controleerGroep72Adresaangifte(inhoud.getAangifteAdreshouding(), herkomst);
        }

        controleerDocumentatie(inhoud.getIndicatieDocument(), categorie.getDocumentatie(), herkomst);
    }

    private void controleerGroepRelaties(
            final boolean groep10Aanwezig,
            final boolean groep11Aanwezig,
            final boolean groep12Aanwezig,
            final boolean groep13Aanwezig,
            final boolean groep14Aanwezig,
            final Lo3Herkomst herkomst) {
        if (groep13Aanwezig) {
            if (groep14Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Groep 13: Emigratie en groep 14: Immigratie mogen niet "
                                + "tegelijkertijd voorkomen in categorie 08: Verblijfplaats");
            }
            if (groep10Aanwezig || groep11Aanwezig || groep12Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Groep 13: Emigratie komt voor en of de groepen 10: Adreshouding, "
                                + "en of 11: Adres of 12: " + "Locatie komen voor in categorie 08: Verblijfplaats");
            }
        } else {
            if (groep10Aanwezig) {
                if (groep11Aanwezig == groep12Aanwezig) {
                    Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                            "Groep 13: Emigratie komt voor of de groepen 10: Adreshouding, en of 11: Adres "
                                    + "of 12: Locatie komen voor in categorie 08: Verblijfplaats");
                }
            } else {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Groep 13: Emigratie komt voor of de groepen 10: Adreshouding, en of 11: Adres of 12:"
                                + " Locatie komen voor in categorie 08: Verblijfplaats");
            }
        }
    }

    private void controleerDocumentatie(
            final Lo3IndicatieDocument indicatieDocument,
            final Lo3Documentatie documentatie,
            final Lo3Herkomst herkomst) {
        // Groep 75: Documentindicatie
        if (isGroepAanwezig(indicatieDocument)) {
            controleerGroep75Documentindicatie(indicatieDocument, herkomst);
        }

        if (isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 08: Verblijfplaats.");
            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 82: Document mag niet voorkomen in categorie 08: Verblijfplaats.");
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }
    }

    private void controleerGroep09Gemeente(
            final Lo3GemeenteCode gemeenteInschrijving,
            final Lo3Datum datumInschrijving,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(gemeenteInschrijving, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE036,
                "Element 09.10: Gemeente van inschrijving moet verplicht voorkomen in groep 09: Gemeente."));
        controleerCode(gemeenteInschrijving, false, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 09.10: Gemeente van inschrijving bevat geen geldige waarde."));

        controleerAanwezig(datumInschrijving,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE036,
                        "Element 09.20: Datum inschrijving moet verplicht voorkomen in groep 09: " + "Gemeente."));
        controleerDatum(datumInschrijving, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 09.20: Datum inschrijving bevat geen geldige datum."));
    }

    private void controleerGroep10Adreshouding(
            final Lo3FunctieAdres functieAdres,
            final String gemeenteDeel,
            final Lo3Datum aanvangAdreshouding,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(functieAdres, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 10.10: Functie adres moet verplicht voorkomen in groep 10: Adreshouding."));
        Lo3PreconditieEnumCodeChecks.controleerCode(functieAdres, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE054, "Element 10.10: Functie adres bevat geen geldige waarde."));

        controleerMaximumLengte(gemeenteDeel, Lo3ElementEnum.ELEMENT_1020, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR, MAXIMAAL_24, "Element 10.20: Gemeentedeel mag maximaal 24 tekens bevatten."));

        controleerAanwezig(aanvangAdreshouding, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 10.30: Datum aanvang moet verplicht voorkomen in groep 10: Adreshouding."));
        controleerDatum(aanvangAdreshouding, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 10.30: Datum aanvang bevat geen geldige datum."));
    }

    private void controleerGroep11Adres(
            final BasisAdres basisAdres,
            final String naamOpenbareRuimte,
            final String identificatiecodeVerblijfplaats,
            final String identificatiecodeNummeraanduiding,
            final Lo3Herkomst herkomst) {
        controleerIndividueleAdresElementen(basisAdres, naamOpenbareRuimte, identificatiecodeVerblijfplaats,
                identificatiecodeNummeraanduiding, herkomst);

        final boolean element1140Aanwezig = basisAdres.getHuisnummertoevoeging() != null;
        final boolean element1150Aanwezig = basisAdres.getAanduidingHuisnummer() != null;

        if (element1140Aanwezig && element1150Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Element 11.40: Huisnummertoevoeging en element 11.50: Aanduiding bij huisnummer mogen niet "
                            + "tegelijk voorkomen in categorie 08: Verblijfsplaats.");
        }

        final boolean element1115Aanwezig = isAanwezig(naamOpenbareRuimte);
        final boolean element1120Aanwezig = isAanwezig(basisAdres.getHuisnummer());
        final boolean element1170Aanwezig = isAanwezig(basisAdres.getWoonplaatsnaam());
        final boolean element1180Aanwezig = isAanwezig(identificatiecodeVerblijfplaats);
        final boolean element1190Aanwezig = isAanwezig(identificatiecodeNummeraanduiding);

        if (element1115Aanwezig || element1170Aanwezig || element1180Aanwezig || element1190Aanwezig) {
            controleerVerplichteAdresElementen(element1115Aanwezig, element1120Aanwezig, element1170Aanwezig,
                    element1180Aanwezig, element1190Aanwezig, herkomst);

            if (element1150Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, dan mag "
                                + "element 11.50 niet voorkomen in categorie 08: Verblijfplaats.");
            }
        }
    }

    private void controleerIndividueleAdresElementen(
            final BasisAdres basisAdres,
            final String naamOpenbareRuimte,
            final String identificatiecodeVerblijfplaats,
            final String identificatiecodeNummeraanduiding,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(basisAdres.getStraatnaam(), Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 11.10: Straatnaam moet verplicht voorkomen in groep 11: Adres."));
        controleerMaximumLengte(basisAdres.getStraatnaam(), Lo3ElementEnum.ELEMENT_1110,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, MAXIMAAL_24,
                        "Element 11.10: Straatnaam mag maximaal 24 tekens bevatten."));

        controleerMaximumLengte(naamOpenbareRuimte, Lo3ElementEnum.ELEMENT_1115, Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.ERROR, "MAXIMAAL-80",
                "Element 11.15: Naam openbare ruimte mag maximaal 80 tekens bevatten."));

        controleerMaximumLengte(basisAdres.getHuisnummertoevoeging(), Lo3ElementEnum.ELEMENT_1140,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, "MAXIMAAL-4",
                        "Element 11.40: Huisnummertoevoeging mag maximaal 4 tekens bevatten."));
        Lo3PreconditieEnumCodeChecks.controleerCode(basisAdres.getAanduidingHuisnummer(), Foutmelding
                .maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 11.50: Aanduiding bij huisnummer bevat een ongeldige waarde."));

        controleerMinimumLengte(basisAdres.getPostcode(), Lo3ElementEnum.ELEMENT_1160, Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.INFO, "Element 11.60: Postcode moet minimaal 6 tekens bevatten."));

        controleerMaximumLengte(basisAdres.getPostcode(), Lo3ElementEnum.ELEMENT_1160, Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.ERROR, "MAXIMAAL-6", "Element 11.60: Postcode mag maximaal 6 tekens bevatten."));
        controleerMaximumLengte(basisAdres.getWoonplaatsnaam(), Lo3ElementEnum.ELEMENT_1170,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                        "Element 11.70: Woonplaatsnaam mag maximaal 80 tekens bevatten."));
        controleerWoonplaats(basisAdres.getWoonplaatsnaam(), Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE057, "Element 11.70: Woonplaatsnaam bevat een ongeldige waarde."));
        controleerMinimumLengte(identificatiecodeVerblijfplaats, Lo3ElementEnum.ELEMENT_1180,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 11.80: Identificatiecode verblijfplaats moet minimaal 16 tekens " + "bevatten."));
        controleerMaximumLengte(identificatiecodeVerblijfplaats, Lo3ElementEnum.ELEMENT_1180,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, MAXIMAAL_16,
                        "Element 11.80: Identificatiecode verblijfplaats mag maximaal 16 tekens bevatten."));
        controleerMinimumLengte(identificatiecodeNummeraanduiding, Lo3ElementEnum.ELEMENT_1190,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 11.90: Identificatiecode nummeraanduiding moet minimaal 16 tekens bevatten."));
        controleerMaximumLengte(identificatiecodeNummeraanduiding, Lo3ElementEnum.ELEMENT_1190,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, MAXIMAAL_16,
                        "Element 11.90: Identificatiecode nummeraanduiding mag maximaal 16 tekens bevatten."));
    }

    private void controleerVerplichteAdresElementen(
            final boolean element1115Aanwezig,
            final boolean element1120Aanwezig,
            final boolean element1170Aanwezig,
            final boolean element1180Aanwezig,
            final boolean element1190Aanwezig,
            final Lo3Herkomst herkomst) {
        if (!element1115Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, dan moet "
                            + "element 11.15 verplicht voorkomen in categorie 08: Verblijfplaats.");
        }

        if (!element1120Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, dan moet"
                            + " element 11.20 verplicht voorkomen in categorie 08: Verblijfplaats.");
        }

        if (!element1170Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, " + "dan moet element"
                            + " 11.70 verplicht voorkomen in categorie 08: Verblijfplaats.");
        }

        if (!element1180Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, dan moet element "
                            + "11.80 verplicht voorkomen in categorie 08: Verblijfplaats.");
        }

        if (!element1190Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als een van de vier elementen 11.15, 11.70, 11.80 of 11.90 voorkomt, dan "
                            + "moet element 11.90 verplicht voorkomen in categorie 08: Verblijfplaats.");
        }
    }

    private void controleerGroep12Locatie(final String locatieBeschrijving, final Lo3Herkomst herkomst) {
        controleerMaximumLengte(locatieBeschrijving, Lo3ElementEnum.ELEMENT_1210, Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.ERROR, MAXIMAAL_35,
                "Element 12.10: Locatiebeschrijving mag maximaal 35 tekens bevatten."));

    }

    private void controleerGroep13Emigratie(
            final Lo3LandCode landWaarnaarVertrokken,
            final Lo3Datum datumVertrekUitNederland,
            final String adresBuitenland1,
            final String adresBuitenland2,
            final String adresBuitenland3,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(landWaarnaarVertrokken, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 13.10: Land waarnaar vertrokken is verplicht in groep 13: Emigratie."));
        controleerCode(landWaarnaarVertrokken, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 13.10: Land waarnaar vertrokken bevat een ongeldige waarde."));

        controleerAanwezig(datumVertrekUitNederland, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 13.20: Datum vertrek uit Nederland is verplicht in groep 13: Emigratie."));
        controleerDatum(datumVertrekUitNederland, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 13.20: Datum vertrek uit Nederland bevat een ongeldige datum."));

        final boolean isAdresBuitenland1Aanwezig = isAanwezig(adresBuitenland1);
        final boolean isAdresBuitenland2Aanwezig = isAanwezig(adresBuitenland2);
        final boolean isAdresBuitenland3Aanwezig = isAanwezig(adresBuitenland3);

        if (isAdresBuitenland1Aanwezig || isAdresBuitenland2Aanwezig || isAdresBuitenland3Aanwezig) {
            if (!isAdresBuitenland2Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 13.40: Adres buitenland waarnaar vertrokken 2 is verplicht in groep 13: Emigratie.");
            }

            if (isAdresBuitenland3Aanwezig && !isAdresBuitenland1Aanwezig) {
                Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                        "Als element 13.50: Adres buitenland waarnaar vertrokken 3 voorkomt dan moet element 13.30: "
                                + "Adres buitenland waarnaar vertrokken 1 verplicht voorkomen in groep 13: "
                                + "Emigratie.");
            }

            controleerMaximumLengte(adresBuitenland1, Lo3ElementEnum.ELEMENT_1330,
                    Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, MAXIMAAL_35,
                            "Element 13.30: Adres buitenland waarnaar vertrokken 1 mag maximaal 35"
                                    + " tekens bevatten."));
            controleerMaximumLengte(adresBuitenland2, Lo3ElementEnum.ELEMENT_1340, Foutmelding.maakStructuurFout(
                    herkomst, LogSeverity.ERROR, MAXIMAAL_35,
                    "Element 13.40: Adres buitenland waarnaar vertrokken 2 mag maximaal 35 tekens bevatten."));
            controleerMaximumLengte(adresBuitenland3, Lo3ElementEnum.ELEMENT_1350,
                    Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, MAXIMAAL_35,
                            "Element 13.50: Adres buitenland waarnaar vertrokken 3 mag maximaal 35 "
                                    + "tekens bevatten."));
        }
    }

    private void controleerGroep14Immigratie(
            final Lo3LandCode landVanwaarIngeschreven,
            final Lo3Datum vestigingInNederland,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(landVanwaarIngeschreven, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 14.10: Land vanwaar ingeschreven is verplicht in groep 14: Immigratie."));
        controleerCode(landVanwaarIngeschreven, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 14.10: Land vanwaar ingeschreven bevat een ongeldige waarde."));

        controleerAanwezig(vestigingInNederland, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 14.20: Datum vestiging in Nederland is verplicht in groep 14: Immigratie."));
        controleerDatum(vestigingInNederland, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 14.20: Datum vestiging in Nederland bevat een ongeldige datum."));
    }

    private void controleerGroep72Adresaangifte(
            final Lo3AangifteAdreshouding aangifteAdreshouding,
            final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(aangifteAdreshouding, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE054,
                "Element 72.10: Aangifte adreshouding bevat een ongeldige waarde."));
    }

    private void controleerGroep75Documentindicatie(
            final Lo3IndicatieDocument indicatieDocument,
            final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(indicatieDocument, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE054,
                "Element 75.10: Indicatie document bevat een ongeldige waarde."));
    }

    /**
     * Basis data voor een adres.
     */
    private static final class BasisAdres {
        private final String straatnaam;
        private final Lo3Huisnummer huisnummer;
        private final String woonplaatsnaam;
        private final String huisnummertoevoeging;
        private final Lo3AanduidingHuisnummer aanduidingHuisnummer;
        private final String postcode;

        private BasisAdres(
                final String straatnaam,
                final Lo3Huisnummer huisnummer,
                final String woonplaatsnaam,
                final String huisnummertoevoeging,
                final Lo3AanduidingHuisnummer aanduidingHuisnummer,
                final String postcode) {
            this.straatnaam = straatnaam;
            this.huisnummer = huisnummer;
            this.woonplaatsnaam = woonplaatsnaam;
            this.huisnummertoevoeging = huisnummertoevoeging;
            this.aanduidingHuisnummer = aanduidingHuisnummer;
            this.postcode = postcode;
        }

        private String getStraatnaam() {
            return straatnaam;
        }

        private Lo3Huisnummer getHuisnummer() {
            return huisnummer;
        }

        private String getWoonplaatsnaam() {
            return woonplaatsnaam;
        }

        private String getHuisnummertoevoeging() {
            return huisnummertoevoeging;
        }

        private Lo3AanduidingHuisnummer getAanduidingHuisnummer() {
            return aanduidingHuisnummer;
        }

        private String getPostcode() {
            return postcode;
        }
    }
}
