/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;

/**
 * Deze service levert de functionaliteit om de LO3 categorie inhoud Verblijfplaats naar BRP inhoud te converteren.
 */
@Requirement({Requirements.CCA08, Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB03, Requirements.CCA08_LB04})
public class VerblijfplaatsConverteerder extends Converteerder {

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder Lo3AttribuutConverteerder
     */
    public VerblijfplaatsConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
    }

    /**
     * Converteer de verblijfplaats. Alle voorkomens wordt eerst gesorteerd van oud naar nieuw op basis van 85.10.
     * @param verblijfplaatsStapel de LO3 verblijfplaats stapel
     * @param tussenPersoonslijstBuilder migratie persoonlijst builder
     * @throws NullPointerException als verblijfplaatsStapel of tussenPersoonslijstBuilder null is
     */
    public final void converteer(final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel, final TussenPersoonslijstBuilder tussenPersoonslijstBuilder) {
        if (tussenPersoonslijstBuilder == null) {
            throw new NullPointerException("tussenPersoonslijstBuilder mag niet null zijn.");
        }

        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> voorkomens = verblijfplaatsStapel.getCategorieen();
        // Sorteer eerst op 85.10 van oud naar nieuw.
        voorkomens.sort(new IngangsdatumGeldigheidComparator());

        // Deze LO3 stapel converteert naar 3 stapels in het BRP model
        final List<TussenGroep<BrpAdresInhoud>> tussenAdresList = new ArrayList<>();
        final List<TussenGroep<BrpMigratieInhoud>> tussenMigratieList = new ArrayList<>();
        final List<TussenGroep<BrpOnverwerktDocumentAanwezigIndicatieInhoud>> onverwerktDocumentAanwezigIndicatieGroepen = new ArrayList<>();

        TussenGroep<BrpMigratieInhoud> oudsteVoorkomenInSetje = null;
        for (final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen : voorkomens) {
            oudsteVoorkomenInSetje =
                    getBrpMigratieInhoudTussenGroep(tussenAdresList, tussenMigratieList, onverwerktDocumentAanwezigIndicatieGroepen, oudsteVoorkomenInSetje,
                            voorkomen);
        }

        voegEmigratieToe(tussenMigratieList, oudsteVoorkomenInSetje);

        tussenPersoonslijstBuilder.adresStapel(new TussenStapel<>(tussenAdresList));
        if (!getUtils().isLijstMetAlleenLegeInhoud(onverwerktDocumentAanwezigIndicatieGroepen)) {
            tussenPersoonslijstBuilder.onverwerktDocumentAanwezigIndicatieStapel(new TussenStapel<>(onverwerktDocumentAanwezigIndicatieGroepen));
        }
        if (!getUtils().isLijstMetAlleenLegeInhoud(tussenMigratieList)) {
            tussenPersoonslijstBuilder.migratieStapel(new TussenStapel<>(tussenMigratieList));
        }
    }

    private TussenGroep<BrpMigratieInhoud> getBrpMigratieInhoudTussenGroep(final List<TussenGroep<BrpAdresInhoud>> tussenAdresList,
                                                                           final List<TussenGroep<BrpMigratieInhoud>> tussenMigratieList,
                                                                           final List<TussenGroep<BrpOnverwerktDocumentAanwezigIndicatieInhoud>>
                                                                                   onverwerktDocumentAanwezigIndicatieGroepen,
                                                                           TussenGroep<BrpMigratieInhoud> vorigeOudsteVoorkomenInSetje,
                                                                           final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen) {
        TussenGroep<BrpMigratieInhoud> oudsteVoorkomenInSetje = vorigeOudsteVoorkomenInSetje;
        converteerOnverwerktDocumentAanwezig(onverwerktDocumentAanwezigIndicatieGroepen, voorkomen);
        tussenAdresList.add(converteerAdres(voorkomen));

        final TussenGroep<BrpMigratieInhoud> tussenMigratieGroep = converteerMigratieInhoud(voorkomen);
        // - tussenMigratieGroep is null -> geen immigratie of emigratie
        // - tussenMigratieGroep is niet null -> wel immigratie of emigratie.
        // Indien soort migratie IMMIGRATIE: gelijk toevoegen en evt. oudsteVoorkomenInSetje toevoegen
        // Indien soort migratie EMIGRATIE: Eerst verwerkt voorkomen is het oudst, dus tijdelijk opslaan.
        // - als tussenMigratieGroep weer null wordt (wat niet zou mogen in Lo3) of van soort migratie wisselt,
        // tijdelijk opgeslagen tussengroep toevoegen aan de lijst.

        // Als de tussen migratie lijst nog leeg is en de groep is leeg, niet toevoegen.
        // Als de lijst niet leeg is, altijd toevoegen

        if (tussenMigratieGroep.isInhoudelijkLeeg()) {
            // Lege rij. Alleen toevoegen als er al een (immigratie)groep is toegevoegd. Als er nog geen groepen
            // zijn toegevoegd, controleren of er emigratie groepen zijn gevonden. Dan deze toevoegen + de lege rij.

            if (!tussenMigratieList.isEmpty() || oudsteVoorkomenInSetje != null) {
                oudsteVoorkomenInSetje = voegEmigratieToe(tussenMigratieList, oudsteVoorkomenInSetje);
                tussenMigratieList.add(tussenMigratieGroep);
            }
        } else {
            if (BrpSoortMigratieCode.IMMIGRATIE.equals(tussenMigratieGroep.getInhoud().getSoortMigratieCode())) {
                oudsteVoorkomenInSetje = voegEmigratieToe(tussenMigratieList, oudsteVoorkomenInSetje);
                tussenMigratieList.add(tussenMigratieGroep);
            } else {
                if (oudsteVoorkomenInSetje == null && !voorkomen.getHistorie().isOnjuist()) {
                    oudsteVoorkomenInSetje = tussenMigratieGroep;
                }
            }
        }
        return oudsteVoorkomenInSetje;
    }

    private TussenGroep<BrpMigratieInhoud> voegEmigratieToe(
            final List<TussenGroep<BrpMigratieInhoud>> tussenMigratieList,
            final TussenGroep<BrpMigratieInhoud> oudsteVoorkomenInSetje) {
        if (oudsteVoorkomenInSetje != null) {
            tussenMigratieList.add(oudsteVoorkomenInSetje);
        }
        return null;
    }

    private void converteerOnverwerktDocumentAanwezig(
            final List<TussenGroep<BrpOnverwerktDocumentAanwezigIndicatieInhoud>> collectie,
            final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen) {
        final BrpBoolean indicatieOnverwerktDocument = getLo3AttribuutConverteerder().converteerIndicatieDocument(voorkomen.getInhoud().getIndicatieDocument());

        final BrpOnverwerktDocumentAanwezigIndicatieInhoud indicatieInhoud;
        if (indicatieOnverwerktDocument.getWaarde()) {
            indicatieInhoud = new BrpOnverwerktDocumentAanwezigIndicatieInhoud(indicatieOnverwerktDocument, null, null);
        } else {
            indicatieInhoud = new BrpOnverwerktDocumentAanwezigIndicatieInhoud(null, null, null);
        }
        collectie.add(new TussenGroep<>(indicatieInhoud, voorkomen.getHistorie(), voorkomen.getDocumentatie(), voorkomen.getLo3Herkomst()));
    }

    @Definitie({Definities.DEF021, Definities.DEF022, Definities.DEF033, Definities.DEF034})
    @Requirement({Requirements.CCA08_LB01, Requirements.CCA08_LB02, Requirements.CCA08_LB03, Requirements.CCA08_LB04})
    private TussenGroep<BrpAdresInhoud> converteerAdres(final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen) {
        final Lo3AttribuutConverteerder converteerder = getLo3AttribuutConverteerder();
        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();

        // ORANJE-1213 Zet alleen adres op Nederland als er niet ook iets anders is opgegeven
        // (ook al is dat niet conform Lo3)
        final BrpLandOfGebiedCode landOfGebiedCode;
        if (lo3Inhoud.isNederlandsAdres() && !Lo3Validatie.isElementGevuld(lo3Inhoud.getLandAdresBuitenland())) {
            landOfGebiedCode = BrpLandOfGebiedCode.NEDERLAND;
        } else {
            landOfGebiedCode = converteerder.converteerLo3LandCode(lo3Inhoud.getLandAdresBuitenland());
        }

        final BrpAdresInhoud.Builder adresBuilder;

        if (lo3Inhoud.isNederlandsAdres()) {
            adresBuilder = converteerNederlandsAdres(lo3Inhoud, landOfGebiedCode);
        } else {
            // Geen Nederlands adres is automatisch een buitenlands adres
            adresBuilder = new BrpAdresInhoud.Builder(BrpSoortAdresCode.W);
            adresBuilder.landOfGebiedCode(landOfGebiedCode);

            // DEF033 minimaal 13.40 adres buitenland regel 2 is gevuld
            if (alleenBuitenlandsAdresRegel2(lo3Inhoud.getAdresBuitenland1(), lo3Inhoud.getAdresBuitenland2(), lo3Inhoud.getAdresBuitenland3())) {
                adresBuilder.buitenlandsAdresRegel1(converteerder.converteerString(lo3Inhoud.getAdresBuitenland2()));
            } else {
                // DEF034 Als 13.50 is gevuld, moet 13.30 ook aanwezig zijn
                adresBuilder.buitenlandsAdresRegel1(converteerder.converteerString(lo3Inhoud.getAdresBuitenland1()));
                adresBuilder.buitenlandsAdresRegel2(converteerder.converteerString(lo3Inhoud.getAdresBuitenland2()));
                adresBuilder.buitenlandsAdresRegel3(converteerder.converteerString(lo3Inhoud.getAdresBuitenland3()));
            }
        }
        // Adreshouding (alleen groep 72 in LO3)
        adresBuilder.redenWijzigingAdresCode(converteerder.converteerLo3AangifteAdreshoudingNaarBrpRedenWijziging(lo3Inhoud.getAangifteAdreshouding()));
        adresBuilder.aangeverAdreshoudingCode(converteerder.converteerLo3AangifteAdreshoudingNaarBrpAangeverAdreshouding(lo3Inhoud.getAangifteAdreshouding()));

        return new TussenGroep<>(adresBuilder.build(), converteerHistorie(voorkomen, false), voorkomen.getDocumentatie(), voorkomen.getLo3Herkomst());
    }

    private BrpAdresInhoud.Builder converteerNederlandsAdres(final Lo3VerblijfplaatsInhoud lo3Inhoud, final BrpLandOfGebiedCode landOfGebiedCode) {
        final Lo3AttribuutConverteerder converteerder = getLo3AttribuutConverteerder();
        final BrpAdresInhoud.Builder adresBuilder = new BrpAdresInhoud.Builder(converteerder.converteerLo3FunctieAdres(lo3Inhoud.getFunctieAdres()));
        adresBuilder.datumAanvangAdreshouding(converteerder.converteerDatum(lo3Inhoud.getAanvangAdreshouding()));

        // DEF021 en DEF022 moeten beide gemeentecode en land/gebied code invullen.
        adresBuilder.gemeenteCode(converteerder.converteerLo3GemeenteCode(lo3Inhoud.getGemeenteInschrijving()));
        adresBuilder.landOfGebiedCode(landOfGebiedCode);

        // DEF022 Adres is bekend.
        if (!lo3Inhoud.isPuntAdres()) {
            adresBuilder.identificatiecodeAdresseerbaarObject(converteerder.converteerString(lo3Inhoud.getIdentificatiecodeVerblijfplaats()));
            adresBuilder.identificatiecodeNummeraanduiding(converteerder.converteerString(lo3Inhoud.getIdentificatiecodeNummeraanduiding()));
            adresBuilder.naamOpenbareRuimte(converteerder.converteerString(lo3Inhoud.getNaamOpenbareRuimte()));
            adresBuilder.afgekorteNaamOpenbareRuimte(converteerder.converteerString(lo3Inhoud.getStraatnaam()));
            adresBuilder.gemeentedeel(converteerder.converteerString(lo3Inhoud.getGemeenteDeel()));
            adresBuilder.huisnummer(converteerder.converteerHuisnummer(lo3Inhoud.getHuisnummer()));
            adresBuilder.huisletter(converteerder.converteerCharacter(lo3Inhoud.getHuisletter()));
            adresBuilder.huisnummertoevoeging(converteerder.converteerString(lo3Inhoud.getHuisnummertoevoeging()));
            adresBuilder.postcode(converteerder.converteerString(lo3Inhoud.getPostcode()));
            final String lo3Woonplaatsnaam = Lo3String.unwrap(lo3Inhoud.getWoonplaatsnaam());
            if (!".".equals(lo3Woonplaatsnaam)) {
                adresBuilder.woonplaatsnaam(converteerder.converteerString(lo3Inhoud.getWoonplaatsnaam()));
            }
            adresBuilder.locatieTovAdres(converteerder.converteerAanduidingBijHuisnummer(lo3Inhoud.getAanduidingHuisnummer()));
            adresBuilder.locatieOmschrijving(converteerder.converteerString(lo3Inhoud.getLocatieBeschrijving()));
        }
        return adresBuilder;
    }

    /**
     * Converteert de historie. Voor Emigratie wordt de historie voor zowel de Migratiestapel als Adresstapel aangepast.
     * Bij Immigratie alleen de Migratiestapel.
     * @param voorkomen voorkomen met historie
     * @param isMigratieHistorie true als het voor de BRP Migratie stapel wordt geconverteerd.
     * @return lo3 historie
     */
    private Lo3Historie converteerHistorie(final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen, final boolean isMigratieHistorie) {
        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        final Lo3Historie oudeHistorie = voorkomen.getHistorie();
        Lo3Historie historie = oudeHistorie;

        if (lo3Inhoud.isEmigratie()) {
            historie = new Lo3Historie(oudeHistorie.getIndicatieOnjuist(), lo3Inhoud.getDatumVertrekUitNederland(), oudeHistorie.getDatumVanOpneming());
        } else if (isMigratieHistorie && lo3Inhoud.isImmigratie()) {
            historie = new Lo3Historie(oudeHistorie.getIndicatieOnjuist(), lo3Inhoud.getVestigingInNederland(), oudeHistorie.getDatumVanOpneming());
        }
        return historie;
    }

    @Definitie({Definities.DEF033, Definities.DEF034})
    @Requirement({Requirements.CCA08_LB03, Requirements.CCA08_LB04})
    private TussenGroep<BrpMigratieInhoud> converteerMigratieInhoud(final Lo3Categorie<Lo3VerblijfplaatsInhoud> voorkomen) {
        final Lo3AttribuutConverteerder converteerder = getLo3AttribuutConverteerder();
        final Lo3VerblijfplaatsInhoud lo3Inhoud = voorkomen.getInhoud();
        // Opties: of 13, of 14 of niets
        final BrpMigratieInhoud.Builder migratieBuilder;
        if (!lo3Inhoud.isEmigratie() && !lo3Inhoud.isImmigratie()) {
            migratieBuilder = new BrpMigratieInhoud.Builder(null, null);
        } else if (lo3Inhoud.isImmigratie()) {
            migratieBuilder =
                    new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.IMMIGRATIE, converteerder.converteerLo3LandCode(lo3Inhoud.getLandVanwaarIngeschreven()));
        } else {
            migratieBuilder =
                    new BrpMigratieInhoud.Builder(BrpSoortMigratieCode.EMIGRATIE, converteerder.converteerLo3LandCode(lo3Inhoud.getLandAdresBuitenland()));

            // DEF033 minimaal 13.40 adres buitenland regel 2 is gevuld
            if (alleenBuitenlandsAdresRegel2(lo3Inhoud.getAdresBuitenland1(), lo3Inhoud.getAdresBuitenland2(), lo3Inhoud.getAdresBuitenland3())) {
                migratieBuilder.buitenlandsAdresRegel1(converteerder.converteerString(lo3Inhoud.getAdresBuitenland2()));
            } else {
                // DEF034 Als 13.50 is gevuld, moet 13.30 ook aanwezig zijn
                migratieBuilder.buitenlandsAdresRegel1(converteerder.converteerString(lo3Inhoud.getAdresBuitenland1()));
                migratieBuilder.buitenlandsAdresRegel2(converteerder.converteerString(lo3Inhoud.getAdresBuitenland2()));
                migratieBuilder.buitenlandsAdresRegel3(converteerder.converteerString(lo3Inhoud.getAdresBuitenland3()));
            }
            migratieBuilder.redenWijzigingMigratieCode(
                    converteerder.converteerLo3AangifteAdreshoudingNaarBrpRedenWijziging(lo3Inhoud.getAangifteAdreshouding()));
            migratieBuilder.aangeverMigratieCode(
                    converteerder.converteerLo3AangifteAdreshoudingNaarBrpAangeverAdreshouding(lo3Inhoud.getAangifteAdreshouding()));
        }

        return new TussenGroep<>(migratieBuilder.build(), converteerHistorie(voorkomen, true), voorkomen.getDocumentatie(), voorkomen.getLo3Herkomst());
    }

    private boolean alleenBuitenlandsAdresRegel2(final Lo3String lo3Regel1, final Lo3String lo3Regel2, final Lo3String lo3Regel3) {
        final String regel1 = Lo3String.unwrap(lo3Regel1);
        final String regel2 = Lo3String.unwrap(lo3Regel2);
        final String regel3 = Lo3String.unwrap(lo3Regel3);

        final boolean regel1Aanwezig = regel1 != null && !regel1.isEmpty();
        final boolean regel2Aanwezig = regel2 != null && !regel2.isEmpty();
        final boolean regel3Aanwezig = regel3 != null && !regel3.isEmpty();
        return !regel1Aanwezig && regel2Aanwezig && !regel3Aanwezig;
    }

    /**
     * Vergelijkt {@link nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie} met een
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud} waarbij op 85.10 wordt
     * gesorteerd van oud naar nieuw.
     */
    private static final class IngangsdatumGeldigheidComparator implements Comparator<Lo3Categorie<Lo3VerblijfplaatsInhoud>>, Serializable {
        private static final long serialVersionUID = 1L;

        @Override
        public int compare(final Lo3Categorie<Lo3VerblijfplaatsInhoud> arg0, final Lo3Categorie<Lo3VerblijfplaatsInhoud> arg1) {
            int resultaat;

            // Sorteer op 85.10 ingangsdatum geldigheid van oud naar nieuw
            resultaat = arg0.getHistorie().getIngangsdatumGeldigheid().compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());

            // sorteer op lo3 volgorde, hoger voorkomen nummer betekend ouder, van oud naar nieuw
            if (resultaat == 0) {
                resultaat = arg1.getLo3Herkomst().getVoorkomen() - arg0.getLo3Herkomst().getVoorkomen();
            }

            return resultaat;
        }
    }
}
