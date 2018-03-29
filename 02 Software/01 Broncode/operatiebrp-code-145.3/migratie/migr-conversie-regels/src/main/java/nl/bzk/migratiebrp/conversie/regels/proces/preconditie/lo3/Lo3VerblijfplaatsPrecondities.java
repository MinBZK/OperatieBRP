/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor categorie 08: Verblijfplaats.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3VerblijfplaatsPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3VerblijfplaatsPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3VerblijfplaatsInhoud> stapel) {
        if (stapel == null) {
            return;
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorie) {
        final Lo3VerblijfplaatsInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
        final Lo3Historie historie = categorie.getHistorie();

        final boolean groep09Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP09, inhoud);
        final boolean groep10Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP10, inhoud);
        final boolean groep11Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP11, inhoud);
        final boolean groep12Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP12, inhoud);
        final boolean groep13Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP13, inhoud);
        final boolean groep14Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP14, inhoud);

        // Groep 09: Gemeente
        if (!groep09Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE036, null);
        } else {
            controleerGroep09Gemeente(inhoud, herkomst);
        }

        controleerGroep10Of13Aanwezig(groep10Aanwezig, groep13Aanwezig, herkomst);
        controleerGroep13En14Aanwezig(groep13Aanwezig, groep14Aanwezig, herkomst);

        // Groep 10: Adreshouding
        if (groep10Aanwezig) {
            controleerGroep10Adreshouding(inhoud.getFunctieAdres(), inhoud.getGemeenteDeel(), inhoud.getAanvangAdreshouding(), herkomst);

            controleerGroep10GerelateerdeElementen(inhoud, herkomst);
        } else {
            if (groep11Aanwezig || groep12Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE102, null);
            }
        }

        // Groep 11: Adres
        if (groep11Aanwezig) {
            controleerGroep11Adres(
                    new BasisAdres(inhoud),
                    inhoud.getNaamOpenbareRuimte(),
                    inhoud.getIdentificatiecodeVerblijfplaats(),
                    inhoud.getIdentificatiecodeNummeraanduiding(),
                    herkomst);
        }

        // Groep 12: Locatie
        if (groep12Aanwezig) {
            controleerGroep12Locatie(inhoud.getLocatieBeschrijving(), herkomst);
        }

        // Groep 13: Emigratie
        if (groep13Aanwezig) {
            controleerGroep13Emigratie(inhoud, herkomst, historie);
        }

        // Groep 14: Immigratie
        if (groep14Aanwezig) {
            controleerGroep14Immigratie(inhoud.getLandVanwaarIngeschreven(), inhoud.getVestigingInNederland(), herkomst);
        }

        // Groep 72: Adesaangifte - geen controles
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP72, inhoud)) {
            controleerGroep72Adresaangifte(inhoud.getAangifteAdreshouding(), herkomst);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE111, null);
        }

        // Documentatie Groep 81, Groep 82 en Groep 88
        controleerDocumentatie(inhoud, categorie.getDocumentatie(), herkomst);
    }

    private void controleerGroep10GerelateerdeElementen(final Lo3VerblijfplaatsInhoud inhoud, final Lo3Herkomst herkomst) {
        final Foutmelding pre084Melding = Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE084, null);
        if (!(Lo3Validatie.isElementGevuld(inhoud.getStraatnaam()) ^ Lo3Validatie.isElementGevuld(inhoud.getLocatieBeschrijving()))) {
            pre084Melding.log();
        }
    }

    private void controleerGroep10Of13Aanwezig(final boolean groep10Aanwezig, final boolean groep13Aanwezig, final Lo3Herkomst herkomst) {
        if (!(groep10Aanwezig ^ groep13Aanwezig)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE080, null);
        }
    }

    private void controleerGroep13En14Aanwezig(final boolean groep13Aanwezig, final boolean groep14Aanwezig, final Lo3Herkomst herkomst) {
        if (groep13Aanwezig && groep14Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE094, null);
        }
    }

    private void controleerDocumentatie(final Lo3VerblijfplaatsInhoud inhoud, final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        // Groep 75: Documentindicatie
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP75, inhoud)) {
            controleerGroep75Documentindicatie(inhoud.getIndicatieDocument(), herkomst);
        }

        if (isAkteAanwezig(documentatie)) {
            controleerGroep81Akte(documentatie, herkomst);
        }
        if (isDocumentAanwezig(documentatie)) {
            controleerGroep82Document(
                    documentatie.getGemeenteDocument(),
                    documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(),
                    herkomst);
        }
        if (isRNIDeelnemerAanwezig(documentatie)) {
            controleerGroep88RNIDeelnemer(documentatie.getRniDeelnemerCode(), herkomst);
        }
    }

    private void controleerGroep09Gemeente(final Lo3VerblijfplaatsInhoud inhoud, final Lo3Herkomst herkomst) {
        final Lo3GemeenteCode gemeenteInschrijving = inhoud.getGemeenteInschrijving();
        final Lo3Datum datumInschrijving = inhoud.getDatumInschrijving();

        final Foutmelding pre054GemeenteInschrijving =
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0910);

        controleerAanwezig(
                gemeenteInschrijving,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE036, Lo3ElementEnum.ELEMENT_0910));

        controleerCode(gemeenteInschrijving, false, inhoud.isNederlandsAdres(), pre054GemeenteInschrijving);

        controleerAanwezig(
                datumInschrijving,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE036, Lo3ElementEnum.ELEMENT_0920));
        controleerDatum(
                datumInschrijving,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0920));
    }

    @Preconditie(SoortMeldingCode.PRE100)
    private void controleerGroep10Adreshouding(
            final Lo3FunctieAdres functieAdres,
            final Lo3String gemeenteDeel,
            final Lo3Datum aanvangAdreshouding,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(functieAdres, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE100, null));
        Lo3PreconditieEnumCodeChecks.controleerCode(
                functieAdres,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_1010));

        controleerMaximumLengte(
                gemeenteDeel,
                Lo3ElementEnum.ELEMENT_1020,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1020));

        controleerAanwezig(
                aanvangAdreshouding,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1030));
        controleerDatum(
                aanvangAdreshouding,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_1030));
    }

    private void controleerGroep11Adres(
            final BasisAdres basisAdres,
            final Lo3String naamOpenbareRuimte,
            final Lo3String identificatiecodeVerblijfplaats,
            final Lo3String identificatiecodeNummeraanduiding,
            final Lo3Herkomst herkomst) {
        controleerIndividueleAdresElementen(basisAdres, naamOpenbareRuimte, identificatiecodeVerblijfplaats, identificatiecodeNummeraanduiding, herkomst);

        final boolean element1115Aanwezig = Lo3Validatie.isElementGevuld(naamOpenbareRuimte);
        final boolean element1120Aanwezig = Lo3Validatie.isElementGevuld(basisAdres.getHuisnummer());
        final boolean element1170Aanwezig = Lo3Validatie.isElementGevuld(basisAdres.getWoonplaatsnaam());
        final boolean element1180Aanwezig = Lo3Validatie.isElementGevuld(identificatiecodeVerblijfplaats);
        final boolean element1190Aanwezig = Lo3Validatie.isElementGevuld(identificatiecodeNummeraanduiding);

        if (element1115Aanwezig || element1170Aanwezig || element1180Aanwezig || element1190Aanwezig) {
            controleerVerplichteAdresElementen(
                    element1115Aanwezig,
                    element1120Aanwezig,
                    element1170Aanwezig,
                    element1180Aanwezig,
                    element1190Aanwezig,
                    herkomst);
        }

        controleerBijzondereSituaties(basisAdres, herkomst, element1115Aanwezig, element1170Aanwezig, element1180Aanwezig, element1190Aanwezig);
    }

    private void controleerBijzondereSituaties(
            final BasisAdres basisAdres,
            final Lo3Herkomst herkomst,
            final boolean element1115Aanwezig,
            final boolean element1170Aanwezig,
            final boolean element1180Aanwezig,
            final boolean element1190Aanwezig) {
        final boolean heeftWoonplaatsStandaardwaarde = element1170Aanwezig && ".".equals(basisAdres.getWoonplaatsnaam().getWaarde());
        final boolean zijnBagGegevenIncompleet = !element1115Aanwezig || !element1180Aanwezig || !element1190Aanwezig;
        if (heeftWoonplaatsStandaardwaarde && zijnBagGegevenIncompleet) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB036, null);
        }

        final boolean zijnBagGegevenCompleet = element1115Aanwezig && element1180Aanwezig && element1190Aanwezig;
        if (!element1170Aanwezig && zijnBagGegevenCompleet) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB037, null);
        }
    }

    private void controleerIndividueleAdresElementen(
            final BasisAdres basisAdres,
            final Lo3String naamOpenbareRuimte,
            final Lo3String identificatiecodeVerblijfplaats,
            final Lo3String identificatiecodeNummeraanduiding,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(
                basisAdres.getStraatnaam(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1110));
        controleerMaximumLengte(
                basisAdres.getStraatnaam(),
                Lo3ElementEnum.ELEMENT_1110,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1110));

        controleerMaximumLengte(
                naamOpenbareRuimte,
                Lo3ElementEnum.ELEMENT_1115,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1115));

        controleerMaximumLengte(
                basisAdres.getHuisnummertoevoeging(),
                Lo3ElementEnum.ELEMENT_1140,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1140));

        controleerAanduidingHuisnummer(
                basisAdres.aanduidingHuisnummer,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE091, null));

        controleerMinimumLengte(
                basisAdres.getPostcode(),
                Lo3ElementEnum.ELEMENT_1160,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1160));

        controleerMaximumLengte(
                basisAdres.getPostcode(),
                Lo3ElementEnum.ELEMENT_1160,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1160));
        controleerMaximumLengte(
                basisAdres.getWoonplaatsnaam(),
                Lo3ElementEnum.ELEMENT_1170,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1170));

        if (basisAdres.isNederlandsVerblijfadres()) {
            controleerWoonplaatsnaam(
                    basisAdres.getWoonplaatsnaam(),
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE057, null));
        }

        controleerMinimumLengte(
                identificatiecodeVerblijfplaats,
                Lo3ElementEnum.ELEMENT_1180,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1180));
        controleerMaximumLengte(
                identificatiecodeVerblijfplaats,
                Lo3ElementEnum.ELEMENT_1180,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1180));
        controleerMinimumLengte(
                identificatiecodeNummeraanduiding,
                Lo3ElementEnum.ELEMENT_1190,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1190));
        controleerMaximumLengte(
                identificatiecodeNummeraanduiding,
                Lo3ElementEnum.ELEMENT_1190,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1190));
    }

    private void controleerVerplichteAdresElementen(
            final boolean element1115Aanwezig,
            final boolean element1120Aanwezig,
            final boolean element1170Aanwezig,
            final boolean element1180Aanwezig,
            final boolean element1190Aanwezig,
            final Lo3Herkomst herkomst) {
        if (!element1115Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1115);
        }

        if (!element1120Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1120);
        }

        if (!element1170Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1170);
        }

        if (!element1180Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1180);
        }

        if (!element1190Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1190);
        }
    }

    private void controleerGroep12Locatie(final Lo3String locatieBeschrijving, final Lo3Herkomst herkomst) {
        controleerMaximumLengte(
                locatieBeschrijving,
                Lo3ElementEnum.ELEMENT_1210,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1210));

    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB028)
    @Preconditie({SoortMeldingCode.PRE054, SoortMeldingCode.PRE081})
    private void controleerGroep13Emigratie(final Lo3VerblijfplaatsInhoud inhoud, final Lo3Herkomst herkomst, final Lo3Historie historie) {
        final Lo3LandCode landAdresBuitenland = inhoud.getLandAdresBuitenland();
        final Lo3Datum datumVertrekUitNederland = inhoud.getDatumVertrekUitNederland();
        final Lo3String adresBuitenland1 = inhoud.getAdresBuitenland1();
        final Lo3String adresBuitenland2 = inhoud.getAdresBuitenland2();
        final Lo3String adresBuitenland3 = inhoud.getAdresBuitenland3();
        final Lo3Datum datumInschrijvingGemeente = inhoud.getDatumInschrijving();
        final Lo3Datum datumIngangGeldigheid = historie.getIngangsdatumGeldigheid();

        controleerAanwezig(
                landAdresBuitenland,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE081, Lo3ElementEnum.ELEMENT_1310));

        controleerCode(landAdresBuitenland, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_1310));

        controleerAanwezig(
                datumVertrekUitNederland,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE081, Lo3ElementEnum.ELEMENT_1320));
        controleerDatum(
                datumVertrekUitNederland,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_1320));

        controleerBijzondereSituatie(herkomst, datumVertrekUitNederland, datumInschrijvingGemeente, datumIngangGeldigheid);

        final boolean isAdresBuitenland1Aanwezig = Lo3Validatie.isElementGevuld(adresBuitenland1);
        final boolean isAdresBuitenland2Aanwezig = Lo3Validatie.isElementGevuld(adresBuitenland2);
        final boolean isAdresBuitenland3Aanwezig = Lo3Validatie.isElementGevuld(adresBuitenland3);

        if (isAdresBuitenland1Aanwezig || isAdresBuitenland2Aanwezig || isAdresBuitenland3Aanwezig) {
            if (!isAdresBuitenland2Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1340);
            }
            if (isAdresBuitenland1Aanwezig && !isAdresBuitenland2Aanwezig && !isAdresBuitenland3Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB033, null);
            }
            controleerMaximumLengte(
                    adresBuitenland1,
                    Lo3ElementEnum.ELEMENT_1330,
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1330));
            controleerMaximumLengte(
                    adresBuitenland2,
                    Lo3ElementEnum.ELEMENT_1340,
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1340));
            controleerMaximumLengte(
                    adresBuitenland3,
                    Lo3ElementEnum.ELEMENT_1350,
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.LENGTE, Lo3ElementEnum.ELEMENT_1350));
        }
    }

    private void controleerBijzondereSituatie(
            final Lo3Herkomst herkomst,
            final Lo3Datum datumVertrekUitNederland,
            final Lo3Datum datumInschrijvingGemeente,
            final Lo3Datum datumIngangGeldigheid) {

        // Bijzondere situatie LB028
        if (datumVertrekUitNederland != null) {
            Lo3Datum controleDatum = datumVertrekUitNederland;
            if (datumInschrijvingGemeente != null && datumInschrijvingGemeente.compareTo(controleDatum) > 0) {
                controleDatum = datumInschrijvingGemeente;
            }

            if (!AbstractLo3Element.equalsWaarde(controleDatum, datumIngangGeldigheid)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB028, null);
            }
        }
    }

    private void controleerGroep14Immigratie(final Lo3LandCode landVanwaarIngeschreven, final Lo3Datum vestigingInNederland, final Lo3Herkomst herkomst) {
        if (!Lo3Validatie.isElementGevuld(landVanwaarIngeschreven) || !Lo3Validatie.isElementGevuld(vestigingInNederland)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE079, null);
        }

        controleerCode(
                landVanwaarIngeschreven,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_1410));
        controleerDatum(
                vestigingInNederland,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_1420));
    }

    private void controleerGroep72Adresaangifte(final Lo3AangifteAdreshouding aangifteAdreshouding, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
                aangifteAdreshouding,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_7210));
    }

    private void controleerGroep75Documentindicatie(final Lo3IndicatieDocument indicatieDocument, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(
                indicatieDocument,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_7510));
    }

    /**
     * Basis data voor een adres.
     */
    private static final class BasisAdres {
        private final Lo3String straatnaam;
        private final Lo3Huisnummer huisnummer;
        private final Lo3String woonplaatsnaam;
        private final Lo3String huisnummertoevoeging;
        private final Lo3AanduidingHuisnummer aanduidingHuisnummer;
        private final Lo3String postcode;
        private final Lo3LandCode landCode;

        private BasisAdres(final Lo3VerblijfplaatsInhoud inhoud) {
            straatnaam = inhoud.getStraatnaam();
            huisnummer = inhoud.getHuisnummer();
            woonplaatsnaam = inhoud.getWoonplaatsnaam();
            huisnummertoevoeging = inhoud.getHuisnummertoevoeging();
            aanduidingHuisnummer = inhoud.getAanduidingHuisnummer();
            postcode = inhoud.getPostcode();
            landCode = inhoud.getLandAdresBuitenland();
        }

        /**
         * Geef de nederlands verblijfadres.
         * @return nederlands verblijfadres
         */
        private boolean isNederlandsVerblijfadres() {
            return landCode == null || !landCode.isLandCodeBuitenland();
        }

        /**
         * Geef de waarde van straatnaam.
         * @return straatnaam
         */
        private Lo3String getStraatnaam() {
            return straatnaam;
        }

        /**
         * Geef de waarde van huisnummer.
         * @return huisnummer
         */
        private Lo3Huisnummer getHuisnummer() {
            return huisnummer;
        }

        /**
         * Geef de waarde van woonplaatsnaam.
         * @return woonplaatsnaam
         */
        private Lo3String getWoonplaatsnaam() {
            return woonplaatsnaam;
        }

        /**
         * Geef de waarde van huisnummertoevoeging.
         * @return huisnummertoevoeging
         */
        private Lo3String getHuisnummertoevoeging() {
            return huisnummertoevoeging;
        }

        /**
         * Geef de waarde van postcode.
         * @return postcode
         */
        private Lo3String getPostcode() {
            return postcode;
        }
    }
}
