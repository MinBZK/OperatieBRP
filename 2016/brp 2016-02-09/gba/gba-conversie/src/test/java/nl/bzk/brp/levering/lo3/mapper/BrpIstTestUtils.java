/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CoderingOnjuistAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekInclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AktenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.ist.StapelModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieGerelateerdenGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieGezagsverhoudingGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieOudersGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;

public final class BrpIstTestUtils {

    private static final String GEBOORTE_AKTE = "geboorte akte";

    /**
     * Niet instantieerbaar.
     */
    private BrpIstTestUtils() {
    }

    public static StapelModel maakStapelModel(final PersoonModel persoon, final Lo3CategorieEnum lo3Categorie, final Integer volgnummer) {
        return new StapelModel(persoon, new LO3CategorieAttribuut(lo3Categorie.getCategorie()), new VolgnummerAttribuut(volgnummer));
    }

    public static StapelVoorkomenModel maakStapelVoorkomenModel(final StapelModel stapel, final Integer volgnummer) {
        return new StapelVoorkomenModel(stapel, new VolgnummerAttribuut(volgnummer));
    }

    public static StapelVoorkomenCategorieGezagsverhoudingGroepModel maakStapelVoorkomenCategorieGezagsverhoudingGroepModel(
        final boolean ouder1Gezag,
        final boolean ouder2Gezag,
        final boolean derdeGezag,
        final boolean onderCuratele)
    {
        return new StapelVoorkomenCategorieGezagsverhoudingGroepModel(
            converteerBooleanNaarJaAttribuut(ouder1Gezag),
            converteerBooleanNaarJaAttribuut(ouder2Gezag),
            converteerBooleanNaarJaAttribuut(derdeGezag),
            converteerBooleanNaarJaAttribuut(onderCuratele));
    }

    public static StapelVoorkomenCategorieGerelateerdenGroepModel maakStapelVoorkomenCategorieGerelateerdenGroepModel(
        final String aktenummer,
        final Long aNummer,
        final Integer bsn,
        final String voornamen,
        final String voorvoegsel,
        final String geslachtsnaam,
        final Integer geboorteDatum,
        final PredicaatAttribuut predicaat,
        final AdellijkeTitelAttribuut adellijkeTitel,
        final GemeenteAttribuut gemeenteGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte,
        final Geslachtsaanduiding geslacht)
    {
        final AktenummerAttribuut aktenummerAttribuut = new AktenummerAttribuut(aktenummer);
        final AdministratienummerAttribuut administratienummer = new AdministratienummerAttribuut(aNummer);
        final BurgerservicenummerAttribuut burgerservicenummer = new BurgerservicenummerAttribuut(bsn);
        final VoornamenAttribuut voornamenAttribuut = new VoornamenAttribuut(voornamen);
        final GeslachtsaanduidingAttribuut geslachtBijAdellijkeTitelPredikaat = new GeslachtsaanduidingAttribuut(geslacht);
        final VoorvoegselAttribuut voorvoegselAttribuut = new VoorvoegselAttribuut(voorvoegsel);
        final ScheidingstekenAttribuut scheidingsteken = new ScheidingstekenAttribuut("-");
        final GeslachtsnaamstamAttribuut geslachtsnaamstam = new GeslachtsnaamstamAttribuut(geslachtsnaam);
        final DatumEvtDeelsOnbekendAttribuut datumGeboorte = new DatumEvtDeelsOnbekendAttribuut(geboorteDatum);
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte = null;
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte = null;
        final GeslachtsaanduidingAttribuut geslachtsaanduiding = new GeslachtsaanduidingAttribuut(geslacht);
        return new StapelVoorkomenCategorieGerelateerdenGroepModel(
            aktenummerAttribuut,
            administratienummer,
            burgerservicenummer,
            voornamenAttribuut,
            predicaat,
            adellijkeTitel,
            geslachtBijAdellijkeTitelPredikaat,
            voorvoegselAttribuut,
            scheidingsteken,
            geslachtsnaamstam,
            datumGeboorte,
            gemeenteGeboorte,
            buitenlandsePlaatsGeboorte,
            omschrijvingLocatieGeboorte,
            landGebiedGeboorte,
            geslachtsaanduiding);
    }

    public static StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel maakStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
        final Integer aanvangDatum,
        final Integer eindDatum,
        final GemeenteAttribuut gemeenteAanvang,
        final LandGebiedAttribuut landAanvang,
        final String locatieOmschrijving,
        final String redenEindeRelatieCode,
        final String omschrijvingRedenEinde,
        final SoortRelatie soortRelatieCode)
    {
        final DatumEvtDeelsOnbekendAttribuut datumAanvang = new DatumEvtDeelsOnbekendAttribuut(aanvangDatum);
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang = null;
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang = new LocatieomschrijvingAttribuut(locatieOmschrijving);
        RedenEindeRelatieAttribuut redenEinde;
        if (redenEindeRelatieCode != null && omschrijvingRedenEinde != null) {
            redenEinde = StatischeObjecttypeBuilder.bouwRedenEindeRelatie(redenEindeRelatieCode, omschrijvingRedenEinde);
        } else {
            redenEinde = null;
        }
        final DatumEvtDeelsOnbekendAttribuut datumEinde = new DatumEvtDeelsOnbekendAttribuut(eindDatum);
        final GemeenteAttribuut gemeenteEinde = null;
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde = null;
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde = null;
        final LandGebiedAttribuut landEinde = null;
        final SoortRelatieAttribuut soortRelatie = new SoortRelatieAttribuut(soortRelatieCode);
        return new StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
            datumAanvang,
            gemeenteAanvang,
            buitenlandsePlaatsAanvang,
            omschrijvingLocatieAanvang,
            landAanvang,
            redenEinde,
            datumEinde,
            gemeenteEinde,
            buitenlandsePlaatsEinde,
            omschrijvingLocatieEinde,
            landEinde,
            soortRelatie);
    }

    public static StapelVoorkomenStandaardGroepModel maakStapelVoorkomenStandaardGroepModel(
        final AdministratieveHandelingModel administratieveHandeling,
        final SoortDocumentAttribuut soortDocument,
        final PartijAttribuut partij,
        final Integer rubriek8220DatumDocument,
        final String documentOmschrijving,
        final Integer rubriek8310AanduidingGegevensInOnderzoek,
        final Integer rubriek8320DatumIngangOnderzoek,
        final Integer rubriek8330DatumEindeOnderzoek,
        final String rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde,
        final Integer rubriek8510IngangsdatumGeldigheid,
        final Integer rubriek8610DatumVanOpneming)
    {
        final DatumEvtDeelsOnbekendAttribuut rubriek8220DatumDocumentAttribuut = new DatumEvtDeelsOnbekendAttribuut(rubriek8220DatumDocument);
        final DocumentOmschrijvingAttribuut documentOmschrijvingAttribuut = new DocumentOmschrijvingAttribuut(GEBOORTE_AKTE);
        final LO3RubriekInclCategorieEnGroepAttribuut rubriek8310AanduidingGegevensInOnderzoekAttribuut =
                new LO3RubriekInclCategorieEnGroepAttribuut(rubriek8310AanduidingGegevensInOnderzoek);
        final DatumEvtDeelsOnbekendAttribuut rubriek8320DatumIngangOnderzoeAttribuut = new DatumEvtDeelsOnbekendAttribuut(rubriek8320DatumIngangOnderzoek);
        final DatumEvtDeelsOnbekendAttribuut rubriek8330DatumEindeOnderzoekAttribuut = new DatumEvtDeelsOnbekendAttribuut(rubriek8330DatumEindeOnderzoek);
        final LO3CoderingOnjuistAttribuut rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrdeAttribuut =
                new LO3CoderingOnjuistAttribuut(rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrde);
        final DatumEvtDeelsOnbekendAttribuut rubriek8510IngangsdatumGeldigheidAttribuut = new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(20100101));
        final DatumEvtDeelsOnbekendAttribuut rubriek8610DatumVanOpnemingAttribuut = new DatumEvtDeelsOnbekendAttribuut(new DatumAttribuut(20100101));
        return new StapelVoorkomenStandaardGroepModel(
            administratieveHandeling,
            soortDocument,
            partij,
            rubriek8220DatumDocumentAttribuut,
            documentOmschrijvingAttribuut,
            rubriek8310AanduidingGegevensInOnderzoekAttribuut,
            rubriek8320DatumIngangOnderzoeAttribuut,
            rubriek8330DatumEindeOnderzoekAttribuut,
            rubriek8410IndicatieOnjuistStrijdigheidOpenbareOrdeAttribuut,
            rubriek8510IngangsdatumGeldigheidAttribuut,
            rubriek8610DatumVanOpnemingAttribuut);
    }

    public static StapelVoorkomenCategorieOudersGroepModel maakStapelVoorkomenCategorieOudersGroepModel(
        final Integer datumIngangFamilierechtelijkeBetrekking)
    {
        return new StapelVoorkomenCategorieOudersGroepModel(new DatumEvtDeelsOnbekendAttribuut(datumIngangFamilierechtelijkeBetrekking));
    }

    private static JaAttribuut converteerBooleanNaarJaAttribuut(final boolean booleanWaarde) {
        if (booleanWaarde) {
            return new JaAttribuut(Ja.J);
        }

        return null;
    }

    public static Set<Stapel> maakSimpeleStapelAlleCategorien() {
        final PersoonModel persoon = new PersoonModel(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        final Set<Stapel> istStapel = new HashSet<>();
        final StapelModel stapelCategorie2 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_02, 0);
        final StapelModel stapelCategorie3 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_03, 0);
        final StapelModel stapelCategorie5 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_05, 0);
        final StapelModel stapelCategorie9 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_09, 0);
        final StapelModel stapelCategorie11 = BrpIstTestUtils.maakStapelModel(persoon, Lo3CategorieEnum.CATEGORIE_11, 0);

        final StapelVoorkomenModel stapelVoorkomenCategorie2 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie2, 0);
        final StapelVoorkomenModel stapelVoorkomenCategorie3 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie3, 0);
        final StapelVoorkomenModel stapelVoorkomenCategorie5 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie5, 0);
        final StapelVoorkomenModel stapelVoorkomenCategorie9 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie9, 0);
        final StapelVoorkomenModel stapelVoorkomenCategorie11 = BrpIstTestUtils.maakStapelVoorkomenModel(stapelCategorie11, 0);

        final StapelVoorkomenCategorieGezagsverhoudingGroepModel categorieGezagsverhouding =
                BrpIstTestUtils.maakStapelVoorkomenCategorieGezagsverhoudingGroepModel(true, true, false, false);
        final StapelVoorkomenCategorieOudersGroepModel categorieOuders = BrpIstTestUtils.maakStapelVoorkomenCategorieOudersGroepModel(20100101);
        final StapelVoorkomenCategorieGerelateerdenGroepModel categorieGerelateerden =
                BrpIstTestUtils.maakStapelVoorkomenCategorieGerelateerdenGroepModel(
                    "a3523432",
                    2563463632L,
                    556735635,
                    "Hendrik Jan",
                    "van",
                    "Heuvel tot Veld",
                    19680405,
                    StatischeObjecttypeBuilder.PREDICAAT_JONKHEER,
                    StatischeObjecttypeBuilder.ADEL_TITEL_BARON,
                    StatischeObjecttypeBuilder.GEMEENTE_BREDA,
                    StatischeObjecttypeBuilder.LAND_NEDERLAND,
                    Geslachtsaanduiding.MAN);
        final StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel categorieHuwelijkGeregistreerdPartnerschap =
                BrpIstTestUtils.maakStapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel(
                    20120604,
                    null,
                    StatischeObjecttypeBuilder.GEMEENTE_DEN_HAAG,
                    StatischeObjecttypeBuilder.LAND_NEDERLAND,
                    null,
                    null,
                    null,
                    SoortRelatie.HUWELIJK);

        stapelVoorkomenCategorie2.setCategorieGezagsverhouding(categorieGezagsverhouding);
        stapelVoorkomenCategorie3.setCategorieGezagsverhouding(categorieGezagsverhouding);
        stapelVoorkomenCategorie11.setCategorieGezagsverhouding(categorieGezagsverhouding);

        stapelVoorkomenCategorie2.setCategorieOuders(categorieOuders);
        stapelVoorkomenCategorie3.setCategorieOuders(categorieOuders);
        stapelVoorkomenCategorie5.setCategorieOuders(categorieOuders);
        stapelVoorkomenCategorie9.setCategorieOuders(categorieOuders);

        stapelVoorkomenCategorie2.setCategorieGerelateerden(categorieGerelateerden);
        stapelVoorkomenCategorie3.setCategorieGerelateerden(categorieGerelateerden);
        stapelVoorkomenCategorie5.setCategorieGerelateerden(categorieGerelateerden);
        stapelVoorkomenCategorie9.setCategorieGerelateerden(categorieGerelateerden);
        stapelVoorkomenCategorie11.setCategorieGerelateerden(categorieGerelateerden);

        stapelVoorkomenCategorie5.setCategorieHuwelijkGeregistreerdPartnerschap(categorieHuwelijkGeregistreerdPartnerschap);

        final AdministratieveHandelingModel administratieveHandelingModel =
                new AdministratieveHandelingModel(
                    new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING),
                    StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
                    null,
                    new DatumTijdAttribuut(new Date()));

        stapelVoorkomenCategorie2.setStandaard(BrpIstTestUtils.maakStapelVoorkomenStandaardGroepModel(
            administratieveHandelingModel,
            StatischeObjecttypeBuilder.GEBOORTE_AKTE,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
            20100101,
            GEBOORTE_AKTE,
            null,
            null,
            null,
            null,
            20100101,
            20100101));
        stapelVoorkomenCategorie3.setStandaard(BrpIstTestUtils.maakStapelVoorkomenStandaardGroepModel(
            administratieveHandelingModel,
            StatischeObjecttypeBuilder.GEBOORTE_AKTE,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
            20100101,
            GEBOORTE_AKTE,
            null,
            null,
            null,
            null,
            20100101,
            20100101));
        stapelVoorkomenCategorie5.setStandaard(BrpIstTestUtils.maakStapelVoorkomenStandaardGroepModel(
            administratieveHandelingModel,
            StatischeObjecttypeBuilder.GEBOORTE_AKTE,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
            20100101,
            GEBOORTE_AKTE,
            null,
            null,
            null,
            null,
            20100101,
            20100101));
        stapelVoorkomenCategorie9.setStandaard(BrpIstTestUtils.maakStapelVoorkomenStandaardGroepModel(
            administratieveHandelingModel,
            StatischeObjecttypeBuilder.GEBOORTE_AKTE,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
            20100101,
            GEBOORTE_AKTE,
            null,
            null,
            null,
            null,
            20100101,
            20100101));
        stapelVoorkomenCategorie11.setStandaard(BrpIstTestUtils.maakStapelVoorkomenStandaardGroepModel(
            administratieveHandelingModel,
            StatischeObjecttypeBuilder.GEBOORTE_AKTE,
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
            20100101,
            GEBOORTE_AKTE,
            null,
            null,
            null,
            null,
            20100101,
            20100101));

        stapelCategorie2.getStapelVoorkomens().add(stapelVoorkomenCategorie2);
        stapelCategorie3.getStapelVoorkomens().add(stapelVoorkomenCategorie3);
        stapelCategorie5.getStapelVoorkomens().add(stapelVoorkomenCategorie5);
        stapelCategorie9.getStapelVoorkomens().add(stapelVoorkomenCategorie9);
        stapelCategorie11.getStapelVoorkomens().add(stapelVoorkomenCategorie11);

        istStapel.add(stapelCategorie2);
        istStapel.add(stapelCategorie3);
        istStapel.add(stapelCategorie5);
        istStapel.add(stapelCategorie9);
        istStapel.add(stapelCategorie11);

        return istStapel;
    }
}
