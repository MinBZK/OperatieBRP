/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.gba.dataaccess.VerConvRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NadereAanduidingVervalAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingVerblijfsrechtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortNederlandsReisdocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.FormeleHistorieModel;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorieModel;
import nl.bzk.brp.model.hisvolledig.kern.ActieBronHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.logisch.verconv.LO3Voorkomen;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Mapper utilities.
 */
public final class BrpMapperUtil {

    /**
     * Niet instantieerbaar.
     */
    private BrpMapperUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Map historie.
     *
     * @param historie
     *            te mappen historie.
     * @param actieInhoud
     *            de actie inhoud
     * @param actieAanpassingGeldigheid
     *            de actie aanpassing geldigheid
     * @param actieVerval
     *            de actie verval
     * @param nadereAanduidingVerval
     *            nadere aanduiding verval
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param entiteitId
     *            entiteitId (voor onderzoek)
     * @param elementEnumDatumAanvang
     *            DB object voor datum aanvang (voor onderzoek)
     * @param elementEnumDatumEinde
     *            DB object voor datum aanvang (voor onderzoek)
     * @param elementEnumTijdstipRegistratie
     *            DB object voor tijdstip registratie (voor onderzoek)
     * @param elementEnumTijdstipVerval
     *            DB object voor tijdstip verval(voor onderzoek)
     * @return BrpHistorie
     */
    public static BrpHistorie mapHistorie(
        final FormeleHistorieModel historie,
        final ActieModel actieInhoud,
        final ActieModel actieAanpassingGeldigheid,
        final ActieModel actieVerval,
        final NadereAanduidingVervalAttribuut nadereAanduidingVerval,
        final OnderzoekMapper onderzoekMapper,
        final Number entiteitId,
        final ElementEnum elementEnumDatumAanvang,
        final ElementEnum elementEnumDatumEinde,
        final ElementEnum elementEnumTijdstipRegistratie,
        final ElementEnum elementEnumTijdstipVerval)
    {
        final BrpDatum aanvang;
        final BrpDatum einde;
        if (historie instanceof MaterieleHistorieModel) {
            final MaterieleHistorieModel matHist = (MaterieleHistorieModel) historie;
            aanvang =
                    BrpMapperUtil.mapBrpDatum(
                        matHist.getDatumAanvangGeldigheid(),
                        onderzoekMapper.bepaalOnderzoek(entiteitId, elementEnumDatumAanvang, true));
            einde = BrpMapperUtil.mapBrpDatum(matHist.getDatumEindeGeldigheid(), onderzoekMapper.bepaalOnderzoek(entiteitId, elementEnumDatumEinde, true));
        } else {
            aanvang = null;
            einde = null;
        }
        final BrpDatumTijd registratie =
                BrpMapperUtil.mapBrpDatumTijd(
                    historie.getTijdstipRegistratie(),
                    onderzoekMapper.bepaalOnderzoek(entiteitId, elementEnumTijdstipRegistratie, true));

        final BrpDatumTijd verval =
                BrpMapperUtil.mapBrpDatumTijd(historie.getDatumTijdVerval(), onderzoekMapper.bepaalOnderzoek(entiteitId, elementEnumTijdstipVerval, true));

        final BrpCharacter brpNadereAanduidingVerval = BrpMapperUtil.mapNadereAanduidingVerval(nadereAanduidingVerval);

        return new BrpHistorie(aanvang, einde, registratie, verval, brpNadereAanduidingVerval);
    }

    /**
     * Map nadere aanduiding verval naar een BRP conversiemodel character.
     *
     * @param nadereAanduidingVerval
     *            nadere aanduiding verval
     * @return brp character
     */
    public static BrpCharacter mapNadereAanduidingVerval(final NadereAanduidingVervalAttribuut nadereAanduidingVerval) {
        if (nadereAanduidingVerval == null || nadereAanduidingVerval.getWaarde() == null) {
            return null;
        }
        return new BrpCharacter(nadereAanduidingVerval.getWaarde().getVasteWaarde().charAt(0), null);
    }

    /**
     * Map een BRP database actieInhoud naar een BRP conversiemodel actie.
     *
     * @param formeleHistorieModel
     *            FormeleHistorieModel
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActieInhoud(
        final FormeelHistorisch formeleHistorieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (formeleHistorieModel instanceof FormeelVerantwoordbaar<?>) {
            final Object actieInhoud = ((FormeelVerantwoordbaar<?>) formeleHistorieModel).getVerantwoordingInhoud();
            if (actieInhoud instanceof ActieModel) {
                return maakActie((ActieModel) actieInhoud, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            }
        }

        return null;
    }

    /**
     * Map een BRP database actieVerval naar een BRP conversiemodel actie.
     *
     * @param formeleHistorieModel
     *            FormeleHistorieModel
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActieVerval(
        final FormeelHistorisch formeleHistorieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (formeleHistorieModel instanceof FormeelVerantwoordbaar<?>) {
            final Object actieVerval = ((FormeelVerantwoordbaar<?>) formeleHistorieModel).getVerantwoordingVerval();
            if (actieVerval instanceof ActieModel) {
                return maakActie((ActieModel) actieVerval, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            }
        }
        return null;
    }

    /**
     * Map een BRP database actieInhoud naar een BRP conversiemodel actie.
     *
     * @param materieleHistorieModel
     *            materieleHistorieModel
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActieInhoud(
        final MaterieelHistorisch materieleHistorieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (materieleHistorieModel instanceof MaterieelVerantwoordbaar<?>) {
            final Object actieInhoud = ((MaterieelVerantwoordbaar<?>) materieleHistorieModel).getVerantwoordingInhoud();
            if (actieInhoud instanceof ActieModel) {
                return maakActie((ActieModel) actieInhoud, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            }
        }

        return null;
    }

    /**
     * Map een BRP database actieVerval naar een BRP conversiemodel actie.
     *
     * @param materieleHistorieModel
     *            materieleHistorieModel
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActieVerval(
        final MaterieelHistorisch materieleHistorieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (materieleHistorieModel instanceof MaterieelVerantwoordbaar<?>) {
            final Object actieVerval = ((MaterieelVerantwoordbaar<?>) materieleHistorieModel).getVerantwoordingVerval();
            if (actieVerval instanceof ActieModel) {
                return maakActie((ActieModel) actieVerval, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            }
        }
        return null;
    }

    /**
     * Map een BRP database actieAanpassingGeldigheid naar een BRP conversiemodel actie.
     *
     * @param materieleHistorieModel
     *            MaterieleHistorieModel
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */
    public static BrpActie mapActieAanpassingGeldigheid(
        final MaterieelHistorisch materieleHistorieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (materieleHistorieModel instanceof MaterieelVerantwoordbaar<?>) {
            final Object actieAanpassingGeldigheid = ((MaterieelVerantwoordbaar<?>) materieleHistorieModel).getVerantwoordingAanpassingGeldigheid();

            if (actieAanpassingGeldigheid instanceof ActieModel) {
                return maakActie((ActieModel) actieAanpassingGeldigheid, onderzoekMapper, stapelNummer, verConvRepository, actieHisVolledigLocator);
            }
        }

        return null;
    }

    /**
     * Map een BRP database actie naar een BRP conversiemodel actie.
     *
     * @param actieModel
     *            BRP database actie
     * @param onderzoekMapper
     *            onderzoek mapper
     * @param stapelNummer
     *            stapel nummer
     * @param verConvRepository
     *            repository om de Lo3 herkomst te bepalen
     * @param actieHisVolledigLocator
     *            actieHisVolledig locator
     * @return BRP conversiemodel actie
     */

    private static BrpActie maakActie(
        final ActieModel actieModel,
        final OnderzoekMapper onderzoekMapper,
        final Integer stapelNummer,
        final VerConvRepository verConvRepository,
        final ActieHisVolledigLocator actieHisVolledigLocator)
    {
        if (actieModel != null) {

            final BrpSoortActieCode soortActieCode =
                    mapBrpSoortActieCode(actieModel.getSoort(), onderzoekMapper.bepaalOnderzoek(actieModel.getID(), ElementEnum.ACTIE_SOORTNAAM, true));
            final BrpPartijCode partijCode =
                    mapBrpPartijCode(actieModel.getPartij(), onderzoekMapper.bepaalOnderzoek(actieModel.getID(), ElementEnum.ACTIE_PARTIJCODE, true));
            final BrpDatumTijd datumTijdRegistratie =
                    mapBrpDatumTijd(
                        actieModel.getTijdstipRegistratie(),
                        onderzoekMapper.bepaalOnderzoek(actieModel.getID(), ElementEnum.ACTIE_TIJDSTIPREGISTRATIE, true));
            final BrpDatum datumOntlening =
                    mapBrpDatum(
                        actieModel.getDatumOntlening(),
                        onderzoekMapper.bepaalOnderzoek(actieModel.getID(), ElementEnum.ACTIE_DATUMONTLENING, true));

            // Bepaal ActieHisVolledig
            final ActieHisVolledig actieHisVolledig = actieHisVolledigLocator.locate(actieModel);
            if (actieHisVolledig == null) {
                throw new IllegalStateException("Actie(HisVolledig) kan niet gevonden worden in de verantwoording (id=" + actieModel.getID() + ")");
            }

            final List<BrpActieBron> actieBronnen = mapActieBronSet(actieHisVolledig.getBronnen(), onderzoekMapper);

            // TODO Stapelnummer fixen voor BOP Stap 4.3 (bijhoudingen op relaties).
            // Stapelnummer: met stapelnummer moeten we zorgen dat bijvoorbeeld IST relatie stapels en BRP relatie
            // stapels bij hetzelfde stapelvoorkomen uitkomen. Nu levert IST vast nog een ander nummer op en dat gaat
            // waarschijnlijk fout. Bij reisdocumenten en nationaliteit is het nu wel opgelost omdat daar geen IST onder
            // hangt. Nu (BOP Stap 3.1) gaat het goed bij relaties omdat die volledig uit de IST tabel worden gehaald.
            // Dit wordt pas relevant bij BOP Stap 4.3 wanneer BRP bijhoudingen gedaan worden op relaties.
            final LO3Voorkomen lo3Voorkomen = verConvRepository.zoekLo3VoorkomenVoorActie(actieModel);
            final Lo3Herkomst lo3Herkomst;
            if (lo3Voorkomen != null) {
                lo3Herkomst = mapHerkomst(lo3Voorkomen, stapelNummer);
            } else if (stapelNummer != null) {
                lo3Herkomst = new Lo3Herkomst(null, stapelNummer, Lo3CategorieWaarde.DEFAULT_VOORKOMEN);
            } else {
                lo3Herkomst = null;
            }

            return new BrpActie(actieModel.getID(), soortActieCode, partijCode, datumTijdRegistratie, datumOntlening, actieBronnen, 0, lo3Herkomst);
        }
        return null;
    }

    /**
     * Map een LO3 Voorkomen naar een LO3 Herkomst
     *
     * @param lo3Voorkomen
     *            lo3 voorkomen uit de BRP database
     * @param brpStapelnummer
     *            stapelnummer obv BRP logica (kind krijgt stapelnummer obv relatie id in a-laag, reisdocument ook, etc)
     * @return LO3 Herkomst
     */
    private static Lo3Herkomst mapHerkomst(final LO3Voorkomen lo3Voorkomen, final Integer brpStapelnummer) {
        try {
            final Lo3CategorieEnum lo3Categorie = Lo3CategorieEnum.getLO3Categorie(lo3Voorkomen.getLO3Categorie().getWaarde());
            final Lo3CategorieEnum actueleLo3Categorie = Lo3CategorieEnum.bepaalActueleCategorie(lo3Categorie);
            final Integer stapelnummer;
            if (brpStapelnummer != null) {
                // Dit zou dan voor CAT_04, CAT_05, CAT_12 en CAT_13 gevuld moeten zijn.
                stapelnummer = brpStapelnummer;
            } else {
                stapelnummer = bepaalStapelnummerHerkomst(actueleLo3Categorie, lo3Voorkomen);
            }

            final Integer volgnummer;

            volgnummer = bepaalVolgnummerHerkomst(actueleLo3Categorie, lo3Voorkomen);

            return new Lo3Herkomst(lo3Categorie, stapelnummer, volgnummer, toInteger(lo3Voorkomen.getMapping().getLO3ConversieSortering().getWaarde()));
        } catch (final Lo3SyntaxException e) {
            throw new IllegalArgumentException("Categorie (uit LO3Voorkomen) is ongeldig.", e);
        }
    }

    private static Integer bepaalStapelnummerHerkomst(final Lo3CategorieEnum actueleLo3Categorie, final LO3Voorkomen lo3Voorkomen) {
        Integer stapelnummer;
        switch (actueleLo3Categorie) {
            case CATEGORIE_01:
            case CATEGORIE_02:
            case CATEGORIE_03:
            case CATEGORIE_06:
            case CATEGORIE_07:
            case CATEGORIE_08:
            case CATEGORIE_10:
            case CATEGORIE_11:
            case CATEGORIE_13:
                // Deze categorien kunnen niet meer dan 1 keer voorkomen, dus overschrijven met default waarde.
                stapelnummer = Lo3CategorieWaarde.DEFAULT_STAPEL;
                break;
            default:
                // Deze zou dus theoretisch niet meer voor moeten komen.
                stapelnummer = lo3Voorkomen.getLO3Stapelvolgnummer().getWaarde();
        }
        return stapelnummer;
    }

    private static Integer bepaalVolgnummerHerkomst(final Lo3CategorieEnum actueleLo3Categorie, final LO3Voorkomen lo3Voorkomen) {
        Integer volgnummer;
        switch (actueleLo3Categorie) {
            case CATEGORIE_07:
            case CATEGORIE_12:
            case CATEGORIE_13:
                // Deze categorieen bevatten geen historie, dus voorkomenvolgnummer overschrijven met default waarde.
                volgnummer = Lo3CategorieWaarde.DEFAULT_VOORKOMEN;
                break;
            default:
                volgnummer = lo3Voorkomen.getLO3Voorkomenvolgnummer().getWaarde();
        }
        return volgnummer;
    }

    /**
     * Map byte naar integer.
     *
     * @param waarde
     *            byte
     * @return integer
     */
    private static Integer toInteger(final Byte waarde) {
        return waarde == null ? null : waarde.intValue();
    }

    /**
     * Map een set van ActieBronHisVolledig naar een Lijst van BrpActieBron.
     *
     * @param actieBronHisVolledigSet
     *            set van ActieBronHisVolledig
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return Lijst van BrpActieBron
     */
    private static List<BrpActieBron> mapActieBronSet(
        final Set<? extends ActieBronHisVolledig> actieBronHisVolledigSet,
        final OnderzoekMapper onderzoekMapper)
    {
        if (actieBronHisVolledigSet == null || actieBronHisVolledigSet.isEmpty()) {
            return null;
        }

        final List<BrpActieBron> result = new ArrayList<>();

        for (final ActieBronHisVolledig actieBronModel : actieBronHisVolledigSet) {
            result.add(mapActieBron(actieBronModel, onderzoekMapper));
        }

        return result;
    }

    /**
     * Map een ActieBronHisVolledig naar een BrpActieBron.
     *
     * @param actieBronHisVolledig
     *            model van ActieBronHisVolledig
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return BrpActieBron
     */
    private static BrpActieBron mapActieBron(final ActieBronHisVolledig actieBronHisVolledig, final OnderzoekMapper onderzoekMapper) {
        final BrpStapel<BrpDocumentInhoud> documentStapel = mapDocument(actieBronHisVolledig.getDocument(), onderzoekMapper);

        final BrpString rechtsgrondOmschrijving =
                BrpMapperUtil.mapBrpString(
                    actieBronHisVolledig.getRechtsgrondomschrijving(),
                    onderzoekMapper.bepaalOnderzoek(actieBronHisVolledig.getID(), ElementEnum.ACTIEBRON_RECHTSGRONDOMSCHRIJVING, false));

        return new BrpActieBron(documentStapel, rechtsgrondOmschrijving);
    }

    /**
     * Map een DocumentHisVolledig naar een BrpStapel<BrpDocumentInhoud>.
     *
     * @param actieBronHisVolledig
     *            model van ActieBronHisVolledig
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return BrpActieBron
     */
    private static BrpStapel<BrpDocumentInhoud> mapDocument(final DocumentHisVolledig document, final OnderzoekMapper onderzoekMapper) {
        if (document == null) {
            return null;
        }

        final List<BrpGroep<BrpDocumentInhoud>> documentGroepen = new ArrayList<>();
        for (final HisDocumentModel hisDocument : document.getDocumentHistorie()) {
            final BrpDocumentInhoud inhoud = mapDocument(hisDocument, onderzoekMapper);
            final BrpHistorie historie =
                    mapHistorie(
                        hisDocument.getFormeleHistorie(),
                        hisDocument.getVerantwoordingInhoud(),
                        null,
                        hisDocument.getVerantwoordingVerval(),
                        hisDocument.getNadereAanduidingVerval(),
                        onderzoekMapper,
                        hisDocument.getID(),
                        null,
                        null,
                        ElementEnum.DOCUMENT_TIJDSTIPREGISTRATIE,
                        ElementEnum.DOCUMENT_TIJDSTIPVERVAL);
            documentGroepen.add(new BrpGroep<>(inhoud, historie, null, null, null));
        }

        final BrpStapel<BrpDocumentInhoud> documentStapel;
        if (!documentGroepen.isEmpty()) {
            documentStapel = new BrpStapel<>(documentGroepen);
        } else {
            documentStapel = null;
        }

        return documentStapel;
    }

    /**
     * Map een DocumentModel naar een BrpDocumentInhoud.
     *
     * @param documentModel
     *            model van document
     * @param onderzoekMapper
     *            onderzoek mapper
     * @return BrpDocumentInhoud
     */
    private static BrpDocumentInhoud mapDocument(final HisDocumentModel documentModel, final OnderzoekMapper onderzoekMapper) {
        final BrpSoortDocumentCode soortDocumentCode =
                BrpMapperUtil.mapBrpSoortDocumentCode(
                    documentModel.getDocument().getSoort(),
                    onderzoekMapper.bepaalOnderzoek(documentModel.getID(), ElementEnum.DOCUMENT_DOCUMENT, true));
        final BrpString identificatie =
                BrpMapperUtil.mapBrpString(
                    documentModel.getIdentificatie(),
                    onderzoekMapper.bepaalOnderzoek(documentModel.getID(), ElementEnum.DOCUMENT_IDENTIFICATIE, true));
        final BrpString aktenummer =
                BrpMapperUtil.mapBrpString(
                    documentModel.getAktenummer(),
                    onderzoekMapper.bepaalOnderzoek(documentModel.getID(), ElementEnum.DOCUMENT_AKTENUMMER, true));
        final BrpString omschrijving =
                BrpMapperUtil.mapBrpString(
                    documentModel.getOmschrijving(),
                    onderzoekMapper.bepaalOnderzoek(documentModel.getID(), ElementEnum.DOCUMENT_OMSCHRIJVING, true));
        final BrpPartijCode partijCode =
                BrpMapperUtil.mapBrpPartijCode(
                    documentModel.getPartij(),
                    onderzoekMapper.bepaalOnderzoek(documentModel.getID(), ElementEnum.DOCUMENT_PARTIJCODE, true));

        return new BrpDocumentInhoud(soortDocumentCode, identificatie, aktenummer, omschrijving, partijCode);
    }

    /* ************************************************************************************************************** */
    /* *** Attribuut conversie (algemeen) *************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * Map naar een BrpBoolean.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpBoolean mapBrpBoolean(final NeeAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        final BrpBoolean result;
        if (attribuut == null || attribuut.getWaarde() == null) {
            result = new BrpBoolean(Boolean.TRUE, onderzoek);
        } else {
            if (attribuut.getWaarde() == Nee.N) {
                result = new BrpBoolean(Boolean.FALSE, onderzoek);
            } else {
                result = new BrpBoolean(Boolean.TRUE, onderzoek);
            }
        }

        return result;
    }

    /**
     * Map naar een BrpBoolean.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpBoolean mapBrpBoolean(final JaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        final BrpBoolean result;
        if (attribuut == null || attribuut.getWaarde() == null) {
            result = new BrpBoolean(Boolean.FALSE, onderzoek);
        } else {
            if (attribuut.getWaarde() == Ja.J) {
                result = new BrpBoolean(Boolean.TRUE, onderzoek);
            } else {
                result = new BrpBoolean(Boolean.FALSE, onderzoek);
            }
        }

        return result;
    }

    /**
     * Map naar een BrpBoolean.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpBoolean mapBrpBoolean(final JaNeeAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpBoolean(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpCharacter.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpCharacter mapBrpCharacter(final Attribuut<String> attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().isEmpty()) {
            return null;
        } else {
            return new BrpCharacter(attribuut.getWaarde().charAt(0), onderzoek);
        }
    }

    /**
     * Map naar een BrpDatum.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpDatum mapBrpDatum(final DatumBasisAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpDatum(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpDatumTijd.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpDatumTijd mapBrpDatumTijd(final DatumTijdAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpDatumTijd(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpInteger.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpInteger mapBrpInteger(final Attribuut<Integer> attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpInteger(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpInteger.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpInteger mapBrpIntegerFromBigDecimal(final Attribuut<BigDecimal> attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpInteger(attribuut.getWaarde().intValue(), onderzoek);
        }
    }

    /**
     * Map naar een BrpLong.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpLong mapBrpLong(final Attribuut<Long> attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpLong(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpString.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpString mapBrpString(final Attribuut<String> attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpString(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpString.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpString mapBrpString(final NaamEnumeratiewaardeAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpString(attribuut.getWaarde(), onderzoek);
        }
    }

    /* ************************************************************************************************************** */
    /* *** Attribuut conversie (specifiek) ************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * Map naar een BrpAanduidingBijHuisnummerCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpAanduidingBijHuisnummerCode mapBrpAanduidingBijHuisnummerCode(
        final LocatieTenOpzichteVanAdresAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getVasteWaarde() == null) {
            return null;
        } else {
            return new BrpAanduidingBijHuisnummerCode(attribuut.getWaarde().getVasteWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpAanduidingInhoudingOfVermissingReisdocumentCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpAanduidingInhoudingOfVermissingReisdocumentCode mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
        final AanduidingInhoudingVermissingReisdocumentAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null
            || attribuut.getWaarde().getCode().getWaarde().isEmpty())
        {
            return null;
        } else {
            return new BrpAanduidingInhoudingOfVermissingReisdocumentCode(attribuut.getWaarde().getCode().getWaarde().charAt(0), onderzoek);
        }
    }

    /**
     * Map naar een BrpAangeverCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpAangeverCode mapBrpAangeverCode(final AangeverAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null
            || attribuut.getWaarde().getCode().getWaarde().isEmpty())
        {
            return null;
        } else {
            return new BrpAangeverCode(attribuut.getWaarde().getCode().getWaarde().charAt(0), onderzoek);
        }
    }

    /**
     * Map naar een BrpAdellijkeTitelCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpAdellijkeTitelCode mapBrpAdellijkeTitelCode(final AdellijkeTitelAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpAdellijkeTitelCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpBijhoudingsaardCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpBijhoudingsaardCode mapBrpBijhoudingsaardCode(final BijhoudingsaardAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpBijhoudingsaardCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpGemeenteCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpGemeenteCode mapBrpGemeenteCode(final GemeenteAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpGemeenteCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpGeslachtsaanduidingCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpGeslachtsaanduidingCode mapBrpGeslachtsaanduidingCode(final GeslachtsaanduidingAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpGeslachtsaanduidingCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpLandOfGebiedCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpLandOfGebiedCode mapBrpLandOfGebiedCode(final LandGebiedAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpLandOfGebiedCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpNaamgebruikCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpNaamgebruikCode mapBrpNaamgebruikCode(final NaamgebruikAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpNaamgebruikCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpNadereBijhoudingsaardCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpNadereBijhoudingsaardCode mapBrpNadereBijhoudingsaardCode(
        final NadereBijhoudingsaardAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpNadereBijhoudingsaardCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpNationaliteitCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpNationaliteitCode mapBrpNationaliteitCode(final NationaliteitAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpNationaliteitCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpPartijCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpPartijCode mapBrpPartijCode(final PartijAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpPartijCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortPartijCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortPartijCode mapBrpSoortPartijCode(final PartijAttribuut attribuut) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getSoort() == null) {
            return null;
        } else {
            return new BrpSoortPartijCode(attribuut.getWaarde().getSoort().getWaarde().getNaam());
        }
    }

    /**
     * Map naar een BrpPredicaatCode.
     *
     * @param attr
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpPredicaatCode mapBrpPredicaatCode(final PredicaatAttribuut attr, final Lo3Onderzoek onderzoek) {
        if (attr == null || attr.getWaarde() == null || attr.getWaarde().getCode() == null || attr.getWaarde().getCode().getWaarde() == null) {
            return null;
        } else {
            return new BrpPredicaatCode(attr.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenEindeRelatieCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenEindeRelatieCode mapBrpRedenEindeRelatieCode(final RedenEindeRelatieAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpRedenEindeRelatieCode(attribuut.getWaarde().getCode().getWaarde().charAt(0), onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenVerkrijgingNederlandschapCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerkrijgingNederlandschapCode mapBrpRedenVerkrijgingNederlandschapCode(
        final RedenVerkrijgingNLNationaliteitAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpRedenVerkrijgingNederlandschapCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenVerliesNederlandschapCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerliesNederlandschapCode mapBrpRedenVerliesNederlanderschapCode(
        final RedenVerliesNLNationaliteitAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpRedenVerliesNederlandschapCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenWijzigingVerblijfCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenWijzigingVerblijfCode mapBrpRedenWijzigingVerblijfCode(
        final RedenWijzigingVerblijfAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null
            || attribuut.getWaarde().getCode().getWaarde().isEmpty())
        {
            return null;
        } else {
            return new BrpRedenWijzigingVerblijfCode(attribuut.getWaarde().getCode().getWaarde().charAt(0), onderzoek);
        }
    }

    /**
     * Map naar een BrpReisdocumentAutoriteitVanAfgifteCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpReisdocumentAutoriteitVanAfgifteCode mapBrpReisdocumentAutoriteitVanAfgifteCode(
        final AutoriteitVanAfgifteReisdocumentCodeAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null || attribuut.getWaarde() == null) {
            return null;
        } else {
            return new BrpReisdocumentAutoriteitVanAfgifteCode(attribuut.getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortActieCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortActieCode mapBrpSoortActieCode(final SoortActieAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getNaam() == null) {
            return null;
        } else {
            return new BrpSoortActieCode(attribuut.getWaarde().getNaam(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortAdresCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortAdresCode mapBrpSoortAdresCode(final FunctieAdresAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpSoortAdresCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortDocumentCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortDocumentCode mapBrpSoortDocumentCode(final SoortDocumentAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getNaam() == null) {
            return null;
        } else {
            return new BrpSoortDocumentCode(attribuut.getWaarde().getNaam().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortMigratieCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortMigratieCode mapBrpSoortMigratieCode(final SoortMigratieAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpSoortMigratieCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortNederlandsReisdocumentCode mapBrpSoortNederlandsReisdocumentCode(
        final SoortNederlandsReisdocumentAttribuut attribuut,
        final Lo3Onderzoek onderzoek)
    {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpSoortNederlandsReisdocumentCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortRelatieCode mapBrpSoortRelatieCode(final SoortRelatieAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null || attribuut.getWaarde() == null || attribuut.getWaarde().getCode() == null) {
            return null;
        } else {
            return new BrpSoortRelatieCode(attribuut.getWaarde().getCode(), onderzoek);
        }
    }

    /**
     * Map naar een BrpVerblijfsrechtCode.
     *
     * @param attribuut
     *            BRP database waarde
     * @param onderzoek
     *            LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    public static BrpVerblijfsrechtCode mapBrpVerblijfsrechtCode(final AanduidingVerblijfsrechtAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        if (attribuut == null
            || attribuut.getWaarde() == null
            || attribuut.getWaarde().getCode() == null
            || attribuut.getWaarde().getCode().getWaarde() == null)
        {
            return null;
        } else {
            return new BrpVerblijfsrechtCode(attribuut.getWaarde().getCode().getWaarde(), onderzoek);
        }
    }
}
