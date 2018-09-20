/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.Generated;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import nl.bzk.brp.model.jpa.usertypes.PersistentEnum;

/**
 * Het 'element' is het kern begrip in het meta model. Dit objecttype bevat bevat: - Alle elementen uit het kernschema
 * van het LGM. - Alle patroonvelden die toegevoegd worden tijdens de transformatie van LGM naar OGM. - Alle 'virtuele'
 * elementen over gerelateerde die noodzakelijk zijn voor het reconstrueren van de juridische persoonslijst.
 *
 * Deze tabel wordt gebruikt om te verwijzen naar gegevenselementen van de BRP.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.ElementGenerator")
public enum ElementEnum implements PersistentEnum, SynchroniseerbaarStamgegeven {

    /**
     * Waarde voor: Geslachtsaanduiding.
     */
    GESLACHTSAANDUIDING(1973),
    /**
     * Waarde voor: SoortPersoon.
     */
    SOORTPERSOON(1987),
    /**
     * Waarde voor: Rol.
     */
    ROL(2152),
    /**
     * Waarde voor: SoortPartij.
     */
    SOORTPARTIJ(2171),
    /**
     * Waarde voor: PartijRol.
     */
    PARTIJROL(2181),
    /**
     * Waarde voor: Persoon.
     */
    PERSOON(3010),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT(3020),
    /**
     * Waarde voor: Persoon - Voornaam.
     */
    PERSOON_VOORNAAM(3022),
    /**
     * Waarde voor: Plaats.
     */
    PLAATS(3037),
    /**
     * Waarde voor: LandGebied.
     */
    LANDGEBIED(3041),
    /**
     * Waarde voor: SoortActie.
     */
    SOORTACTIE(3063),
    /**
     * Waarde voor: Actie.
     */
    ACTIE(3071),
    /**
     * Waarde voor: Nationaliteit.
     */
    NATIONALITEIT(3087),
    /**
     * Waarde voor: Predicaat.
     */
    PREDICAAT(3095),
    /**
     * Waarde voor: AdellijkeTitel.
     */
    ADELLIJKETITEL(3096),
    /**
     * Waarde voor: Persoon - Nationaliteit.
     */
    PERSOON_NATIONALITEIT(3129),
    /**
     * Waarde voor: Document.
     */
    DOCUMENT(3135),
    /**
     * Waarde voor: Partij.
     */
    PARTIJ(3141),
    /**
     * Waarde voor: SoortDocument.
     */
    SOORTDOCUMENT(3149),
    /**
     * Waarde voor: Onderzoek.
     */
    ONDERZOEK(3167),
    /**
     * Waarde voor: Element.
     */
    ELEMENT(3171),
    /**
     * Waarde voor: Relatie.
     */
    RELATIE(3184),
    /**
     * Waarde voor: SoortRelatie.
     */
    SOORTRELATIE(3191),
    /**
     * Waarde voor: RedenEindeRelatie.
     */
    REDENEINDERELATIE(3200),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit.
     */
    REDENVERKRIJGINGNLNATIONALITEIT(3214),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit.
     */
    REDENVERLIESNLNATIONALITEIT(3215),
    /**
     * Waarde voor: Persoon - Adres.
     */
    PERSOON_ADRES(3237),
    /**
     * Waarde voor: FunctieAdres.
     */
    FUNCTIEADRES(3256),
    /**
     * Waarde voor: Aangever.
     */
    AANGEVER(3294),
    /**
     * Waarde voor: AanduidingVerblijfsrecht.
     */
    AANDUIDINGVERBLIJFSRECHT(3302),
    /**
     * Waarde voor: Bijhoudingsaard.
     */
    BIJHOUDINGSAARD(3522),
    /**
     * Waarde voor: Persoon - Reisdocument.
     */
    PERSOON_REISDOCUMENT(3576),
    /**
     * Waarde voor: SoortIndicatie.
     */
    SOORTINDICATIE(3582),
    /**
     * Waarde voor: Naamgebruik.
     */
    NAAMGEBRUIK(3617),
    /**
     * Waarde voor: Persoon - Indicatie.
     */
    PERSOON_INDICATIE(3637),
    /**
     * Waarde voor: SoortElement.
     */
    SOORTELEMENT(3716),
    /**
     * Waarde voor: Persoon - Verificatie.
     */
    PERSOON_VERIFICATIE(3775),
    /**
     * Waarde voor: RedenWijzigingVerblijf.
     */
    REDENWIJZIGINGVERBLIJF(3789),
    /**
     * Waarde voor: SoortNederlandsReisdocument.
     */
    SOORTNEDERLANDSREISDOCUMENT(3791),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT(3802),
    /**
     * Waarde voor: AanduidingInhoudingVermissingReisdocument.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT(3813),
    /**
     * Waarde voor: SoortBetrokkenheid.
     */
    SOORTBETROKKENHEID(3846),
    /**
     * Waarde voor: Betrokkenheid.
     */
    BETROKKENHEID(3857),
    /**
     * Waarde voor: GegevenInOnderzoek.
     */
    GEGEVENINONDERZOEK(3863),
    /**
     * Waarde voor: Regel.
     */
    REGEL(5800),
    /**
     * Waarde voor: Persoon - Onderzoek.
     */
    PERSOON_ONDERZOEK(6127),
    /**
     * Waarde voor: Regelverantwoording.
     */
    REGELVERANTWOORDING(6145),
    /**
     * Waarde voor: GedeblokkeerdeMelding.
     */
    GEDEBLOKKEERDEMELDING(6216),
    /**
     * Waarde voor: AdministratieveHandelingGedeblokkeerdeMelding.
     */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING(6222),
    /**
     * Waarde voor: ActieBron.
     */
    ACTIEBRON(8118),
    /**
     * Waarde voor: Rechtsgrond.
     */
    RECHTSGROND(8125),
    /**
     * Waarde voor: SoortRechtsgrond.
     */
    SOORTRECHTSGROND(8132),
    /**
     * Waarde voor: AdministratieveHandeling.
     */
    ADMINISTRATIEVEHANDELING(9018),
    /**
     * Waarde voor: SoortAdministratieveHandeling.
     */
    SOORTADMINISTRATIEVEHANDELING(9196),
    /**
     * Waarde voor: Voorvoegsel.
     */
    VOORVOEGSEL(9262),
    /**
     * Waarde voor: Kind.
     */
    KIND(9303),
    /**
     * Waarde voor: Ouder.
     */
    OUDER(9304),
    /**
     * Waarde voor: Partner.
     */
    PARTNER(9305),
    /**
     * Waarde voor: Huwelijk.
     */
    HUWELIJK(9306),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP(9307),
    /**
     * Waarde voor: GeregistreerdPartnerschap.
     */
    GEREGISTREERDPARTNERSCHAP(9308),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking.
     */
    FAMILIERECHTELIJKEBETREKKING(9309),
    /**
     * Waarde voor: ErkenningOngeborenVrucht.
     */
    ERKENNINGONGEBORENVRUCHT(9313),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht.
     */
    NAAMSKEUZEONGEBORENVRUCHT(9315),
    /**
     * Waarde voor: Erkenner.
     */
    ERKENNER(9316),
    /**
     * Waarde voor: Instemmer.
     */
    INSTEMMER(9317),
    /**
     * Waarde voor: Naamgever.
     */
    NAAMGEVER(9320),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking.
     */
    PERSOON_VERSTREKKINGSBEPERKING(9344),
    /**
     * Waarde voor: BurgerzakenModule.
     */
    BURGERZAKENMODULE(9510),
    /**
     * Waarde voor: Gemeente.
     */
    GEMEENTE(9558),
    /**
     * Waarde voor: CategorieAdministratieveHandeling.
     */
    CATEGORIEADMINISTRATIEVEHANDELING(9866),
    /**
     * Waarde voor: Persoon - Afnemerindicatie.
     */
    PERSOON_AFNEMERINDICATIE(10317),
    /**
     * Waarde voor: Persoon - Cache.
     */
    PERSOON_CACHE(10385),
    /**
     * Waarde voor: Terugmelding.
     */
    TERUGMELDING(10716),
    /**
     * Waarde voor: StatusTerugmelding.
     */
    STATUSTERUGMELDING(10742),
    /**
     * Waarde voor: GegevenInTerugmelding.
     */
    GEGEVENINTERUGMELDING(10753),
    /**
     * Waarde voor: SoortPersoonOnderzoek.
     */
    SOORTPERSOONONDERZOEK(10764),
    /**
     * Waarde voor: PartijenInOnderzoek.
     */
    PARTIJENINONDERZOEK(10775),
    /**
     * Waarde voor: SoortPartijOnderzoek.
     */
    SOORTPARTIJONDERZOEK(10784),
    /**
     * Waarde voor: StatusOnderzoek.
     */
    STATUSONDERZOEK(10853),
    /**
     * Waarde voor: NadereBijhoudingsaard.
     */
    NADEREBIJHOUDINGSAARD(10865),
    /**
     * Waarde voor: SoortMigratie.
     */
    SOORTMIGRATIE(10872),
    /**
     * Waarde voor: GerelateerdeOuder.
     */
    GERELATEERDEOUDER(12825),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon.
     */
    GERELATEERDEOUDER_PERSOON(12840),
    /**
     * Waarde voor: GerelateerdeKind.
     */
    GERELATEERDEKIND(12920),
    /**
     * Waarde voor: GerelateerdeKind - Persoon.
     */
    GERELATEERDEKIND_PERSOON(12922),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner.
     */
    GERELATEERDEHUWELIJKSPARTNER(12991),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON(12993),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner.
     */
    GERELATEERDEGEREGISTREERDEPARTNER(13073),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON(13075),
    /**
     * Waarde voor: GerelateerdeInstemmer.
     */
    GERELATEERDEINSTEMMER(13155),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon.
     */
    GERELATEERDEINSTEMMER_PERSOON(13157),
    /**
     * Waarde voor: GerelateerdeErkenner.
     */
    GERELATEERDEERKENNER(13237),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon.
     */
    GERELATEERDEERKENNER_PERSOON(13239),
    /**
     * Waarde voor: GerelateerdeNaamgever.
     */
    GERELATEERDENAAMGEVER(13319),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon.
     */
    GERELATEERDENAAMGEVER_PERSOON(13321),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner.
     */
    GERELATEERDENAAMSKEUZEPARTNER(13401),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON(13403),
    /**
     * Waarde voor: SoortElementAutorisatie.
     */
    SOORTELEMENTAUTORISATIE(13515),
    /**
     * Waarde voor: Stelsel.
     */
    STELSEL(21398),
    /**
     * Waarde voor: Koppelvlak.
     */
    KOPPELVLAK(21407),
    /**
     * Waarde voor: Geslachtsaanduiding - Identiteit.
     */
    GESLACHTSAANDUIDING_IDENTITEIT(2088),
    /**
     * Waarde voor: Geslachtsaanduiding - ObjectSleutel.
     */
    GESLACHTSAANDUIDING_OBJECTSLEUTEL(1976),
    /**
     * Waarde voor: Geslachtsaanduiding - Code.
     */
    GESLACHTSAANDUIDING_CODE(1978),
    /**
     * Waarde voor: Geslachtsaanduiding - Naam.
     */
    GESLACHTSAANDUIDING_NAAM(1981),
    /**
     * Waarde voor: Geslachtsaanduiding - Omschrijving.
     */
    GESLACHTSAANDUIDING_OMSCHRIJVING(1982),
    /**
     * Waarde voor: SoortPersoon - Identiteit.
     */
    SOORTPERSOON_IDENTITEIT(2106),
    /**
     * Waarde voor: SoortPersoon - ObjectSleutel.
     */
    SOORTPERSOON_OBJECTSLEUTEL(1990),
    /**
     * Waarde voor: SoortPersoon - Code.
     */
    SOORTPERSOON_CODE(1992),
    /**
     * Waarde voor: SoortPersoon - Naam.
     */
    SOORTPERSOON_NAAM(1993),
    /**
     * Waarde voor: SoortPersoon - Omschrijving.
     */
    SOORTPERSOON_OMSCHRIJVING(2002),
    /**
     * Waarde voor: SoortPersoon - DatumAanvangGeldigheid.
     */
    SOORTPERSOON_DATUMAANVANGGELDIGHEID(4012),
    /**
     * Waarde voor: SoortPersoon - DatumEindeGeldigheid.
     */
    SOORTPERSOON_DATUMEINDEGELDIGHEID(4013),
    /**
     * Waarde voor: Rol - Identiteit.
     */
    ROL_IDENTITEIT(2189),
    /**
     * Waarde voor: Rol - Naam.
     */
    ROL_NAAM(2192),
    /**
     * Waarde voor: Rol - DatumAanvangGeldigheid.
     */
    ROL_DATUMAANVANGGELDIGHEID(4781),
    /**
     * Waarde voor: Rol - DatumEindeGeldigheid.
     */
    ROL_DATUMEINDEGELDIGHEID(4782),
    /**
     * Waarde voor: Rol - ObjectSleutel.
     */
    ROL_OBJECTSLEUTEL(22005),
    /**
     * Waarde voor: SoortPartij - Identiteit.
     */
    SOORTPARTIJ_IDENTITEIT(2172),
    /**
     * Waarde voor: SoortPartij - ObjectSleutel.
     */
    SOORTPARTIJ_OBJECTSLEUTEL(2173),
    /**
     * Waarde voor: SoortPartij - Naam.
     */
    SOORTPARTIJ_NAAM(2176),
    /**
     * Waarde voor: SoortPartij - DatumAanvangGeldigheid.
     */
    SOORTPARTIJ_DATUMAANVANGGELDIGHEID(4009),
    /**
     * Waarde voor: SoortPartij - DatumEindeGeldigheid.
     */
    SOORTPARTIJ_DATUMEINDEGELDIGHEID(4010),
    /**
     * Waarde voor: PartijRol - Identiteit.
     */
    PARTIJROL_IDENTITEIT(2182),
    /**
     * Waarde voor: PartijRol - Standaard.
     */
    PARTIJROL_STANDAARD(21987),
    /**
     * Waarde voor: PartijRol - ObjectSleutel.
     */
    PARTIJROL_OBJECTSLEUTEL(2184),
    /**
     * Waarde voor: PartijRol - PartijCode.
     */
    PARTIJROL_PARTIJCODE(2185),
    /**
     * Waarde voor: PartijRol - RolNaam.
     */
    PARTIJROL_ROLNAAM(2186),
    /**
     * Waarde voor: PartijRol - DatumIngang.
     */
    PARTIJROL_DATUMINGANG(21988),
    /**
     * Waarde voor: PartijRol - DatumEinde.
     */
    PARTIJROL_DATUMEINDE(21989),
    /**
     * Waarde voor: PartijRol - PartijRol.
     */
    PARTIJROL_PARTIJROL(21990),
    /**
     * Waarde voor: PartijRol - TijdstipRegistratie.
     */
    PARTIJROL_TIJDSTIPREGISTRATIE(21991),
    /**
     * Waarde voor: PartijRol - TijdstipVerval.
     */
    PARTIJROL_TIJDSTIPVERVAL(21992),
    /**
     * Waarde voor: PartijRol - NadereAanduidingVerval.
     */
    PARTIJROL_NADEREAANDUIDINGVERVAL(21993),
    /**
     * Waarde voor: PartijRol - ActieInhoud.
     */
    PARTIJROL_ACTIEINHOUD(21994),
    /**
     * Waarde voor: PartijRol - ActieVerval.
     */
    PARTIJROL_ACTIEVERVAL(21995),
    /**
     * Waarde voor: PartijRol - ActieVervalTbvLeveringMutaties.
     */
    PARTIJROL_ACTIEVERVALTBVLEVERINGMUTATIES(21996),
    /**
     * Waarde voor: PartijRol - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PARTIJROL_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21997),
    /**
     * Waarde voor: PartijRol - VoorkomenSleutel.
     */
    PARTIJROL_VOORKOMENSLEUTEL(22002),
    /**
     * Waarde voor: Persoon - Identiteit.
     */
    PERSOON_IDENTITEIT(2064),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - Groep.
     */
    PERSOON_INDICATIE_STAATLOOS_GROEP(2140),
    /**
     * Waarde voor: Persoon - Identificatienummers.
     */
    PERSOON_IDENTIFICATIENUMMERS(3344),
    /**
     * Waarde voor: Persoon - Naamgebruik.
     */
    PERSOON_NAAMGEBRUIK(3487),
    /**
     * Waarde voor: Persoon - Geboorte.
     */
    PERSOON_GEBOORTE(3514),
    /**
     * Waarde voor: Persoon - Overlijden.
     */
    PERSOON_OVERLIJDEN(3515),
    /**
     * Waarde voor: Persoon - Verblijfsrecht.
     */
    PERSOON_VERBLIJFSRECHT(3517),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - Groep.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_GROEP(3518),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht.
     */
    PERSOON_UITSLUITINGKIESRECHT(3519),
    /**
     * Waarde voor: Persoon - Inschrijving.
     */
    PERSOON_INSCHRIJVING(3521),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding.
     */
    PERSOON_GESLACHTSAANDUIDING(3554),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam.
     */
    PERSOON_SAMENGESTELDENAAM(3557),
    /**
     * Waarde voor: Persoon - Persoonskaart.
     */
    PERSOON_PERSOONSKAART(3662),
    /**
     * Waarde voor: Persoon - Bijhouding.
     */
    PERSOON_BIJHOUDING(3664),
    /**
     * Waarde voor: Persoon - Migratie.
     */
    PERSOON_MIGRATIE(3790),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - Groep.
     */
    PERSOON_INDICATIE_ONDERCURATELE_GROEP(3900),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN(3901),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - Groep.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_GROEP(3903),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - Groep.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_GROEP(3904),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - Groep.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_GROEP(3905),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - Groep.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_GROEP(3906),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - Groep.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_GROEP(3907),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief.
     */
    PERSOON_AFGELEIDADMINISTRATIEF(3909),
    /**
     * Waarde voor: Persoon - Nummerverwijzing.
     */
    PERSOON_NUMMERVERWIJZING(10900),
    /**
     * Waarde voor: Persoon - SoortCode.
     */
    PERSOON_SOORTCODE(1997),
    /**
     * Waarde voor: Persoon - ObjectSleutel.
     */
    PERSOON_OBJECTSLEUTEL(3015),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - ActieVerval.
     */
    PERSOON_INDICATIE_STAATLOOS_ACTIEVERVAL(13908),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_STAATLOOS_DATUMAANVANGGELDIGHEID(14137),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_STAATLOOS_DATUMEINDEGELDIGHEID(14138),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - ActieInhoud.
     */
    PERSOON_INDICATIE_STAATLOOS_ACTIEINHOUD(14141),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_STAATLOOS_ACTIEAANPASSINGGELDIGHEID(14142),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_STAATLOOS_NADEREAANDUIDINGVERVAL(14143),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_STAATLOOS_TIJDSTIPREGISTRATIE(14176),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos - TijdstipVerval.
     */
    PERSOON_INDICATIE_STAATLOOS_TIJDSTIPVERVAL(14177),
    /**
     * Waarde voor: Persoon - Indicatie - Staatloos.
     */
    PERSOON_INDICATIE_STAATLOOS(14396),
    /**
     * Waarde voor: Persoon - Identificatienummers - Administratienummer.
     */
    PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(3013),
    /**
     * Waarde voor: Persoon - Identificatienummers - Burgerservicenummer.
     */
    PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(3018),
    /**
     * Waarde voor: Persoon - Identificatienummers - Persoon.
     */
    PERSOON_IDENTIFICATIENUMMERS_PERSOON(4077),
    /**
     * Waarde voor: Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(4078),
    /**
     * Waarde voor: Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(4079),
    /**
     * Waarde voor: Persoon - Identificatienummers - TijdstipRegistratie.
     */
    PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(4080),
    /**
     * Waarde voor: Persoon - Identificatienummers - TijdstipVerval.
     */
    PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(4081),
    /**
     * Waarde voor: Persoon - Identificatienummers - ActieInhoud.
     */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(4082),
    /**
     * Waarde voor: Persoon - Identificatienummers - ActieVerval.
     */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(4083),
    /**
     * Waarde voor: Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(4084),
    /**
     * Waarde voor: Persoon - Identificatienummers - VoorkomenSleutel.
     */
    PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(4524),
    /**
     * Waarde voor: Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(11127),
    /**
     * Waarde voor: Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18844),
    /**
     * Waarde voor: Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18845),
    /**
     * Waarde voor: Persoon - Naamgebruik - Voornamen.
     */
    PERSOON_NAAMGEBRUIK_VOORNAMEN(3319),
    /**
     * Waarde voor: Persoon - Naamgebruik - Geslachtsnaamstam.
     */
    PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM(3323),
    /**
     * Waarde voor: Persoon - Naamgebruik - Voorvoegsel.
     */
    PERSOON_NAAMGEBRUIK_VOORVOEGSEL(3355),
    /**
     * Waarde voor: Persoon - Naamgebruik - Scheidingsteken.
     */
    PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN(3580),
    /**
     * Waarde voor: Persoon - Naamgebruik - Code.
     */
    PERSOON_NAAMGEBRUIK_CODE(3593),
    /**
     * Waarde voor: Persoon - Naamgebruik - IndicatieAfgeleid.
     */
    PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID(3633),
    /**
     * Waarde voor: Persoon - Naamgebruik - PredicaatCode.
     */
    PERSOON_NAAMGEBRUIK_PREDICAATCODE(3703),
    /**
     * Waarde voor: Persoon - Naamgebruik - Persoon.
     */
    PERSOON_NAAMGEBRUIK_PERSOON(4115),
    /**
     * Waarde voor: Persoon - Naamgebruik - TijdstipRegistratie.
     */
    PERSOON_NAAMGEBRUIK_TIJDSTIPREGISTRATIE(4118),
    /**
     * Waarde voor: Persoon - Naamgebruik - TijdstipVerval.
     */
    PERSOON_NAAMGEBRUIK_TIJDSTIPVERVAL(4119),
    /**
     * Waarde voor: Persoon - Naamgebruik - ActieInhoud.
     */
    PERSOON_NAAMGEBRUIK_ACTIEINHOUD(4120),
    /**
     * Waarde voor: Persoon - Naamgebruik - ActieVerval.
     */
    PERSOON_NAAMGEBRUIK_ACTIEVERVAL(4121),
    /**
     * Waarde voor: Persoon - Naamgebruik - VoorkomenSleutel.
     */
    PERSOON_NAAMGEBRUIK_VOORKOMENSLEUTEL(4533),
    /**
     * Waarde voor: Persoon - Naamgebruik - AdellijkeTitelCode.
     */
    PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE(6113),
    /**
     * Waarde voor: Persoon - Naamgebruik - NadereAanduidingVerval.
     */
    PERSOON_NAAMGEBRUIK_NADEREAANDUIDINGVERVAL(11135),
    /**
     * Waarde voor: Persoon - Naamgebruik - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_NAAMGEBRUIK_ACTIEVERVALTBVLEVERINGMUTATIES(18860),
    /**
     * Waarde voor: Persoon - Naamgebruik - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_NAAMGEBRUIK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18861),
    /**
     * Waarde voor: Persoon - Geboorte - BuitenlandseRegio.
     */
    PERSOON_GEBOORTE_BUITENLANDSEREGIO(3530),
    /**
     * Waarde voor: Persoon - Geboorte - LandGebiedCode.
     */
    PERSOON_GEBOORTE_LANDGEBIEDCODE(3543),
    /**
     * Waarde voor: Persoon - Geboorte - Datum.
     */
    PERSOON_GEBOORTE_DATUM(3673),
    /**
     * Waarde voor: Persoon - Geboorte - GemeenteCode.
     */
    PERSOON_GEBOORTE_GEMEENTECODE(3675),
    /**
     * Waarde voor: Persoon - Geboorte - Woonplaatsnaam.
     */
    PERSOON_GEBOORTE_WOONPLAATSNAAM(3676),
    /**
     * Waarde voor: Persoon - Geboorte - BuitenlandsePlaats.
     */
    PERSOON_GEBOORTE_BUITENLANDSEPLAATS(3677),
    /**
     * Waarde voor: Persoon - Geboorte - OmschrijvingLocatie.
     */
    PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(3678),
    /**
     * Waarde voor: Persoon - Geboorte - Persoon.
     */
    PERSOON_GEBOORTE_PERSOON(4132),
    /**
     * Waarde voor: Persoon - Geboorte - TijdstipRegistratie.
     */
    PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(4133),
    /**
     * Waarde voor: Persoon - Geboorte - TijdstipVerval.
     */
    PERSOON_GEBOORTE_TIJDSTIPVERVAL(4134),
    /**
     * Waarde voor: Persoon - Geboorte - ActieInhoud.
     */
    PERSOON_GEBOORTE_ACTIEINHOUD(4135),
    /**
     * Waarde voor: Persoon - Geboorte - ActieVerval.
     */
    PERSOON_GEBOORTE_ACTIEVERVAL(4136),
    /**
     * Waarde voor: Persoon - Geboorte - VoorkomenSleutel.
     */
    PERSOON_GEBOORTE_VOORKOMENSLEUTEL(4536),
    /**
     * Waarde voor: Persoon - Geboorte - NadereAanduidingVerval.
     */
    PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(11129),
    /**
     * Waarde voor: Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18848),
    /**
     * Waarde voor: Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18849),
    /**
     * Waarde voor: Persoon - Overlijden - Woonplaatsnaam.
     */
    PERSOON_OVERLIJDEN_WOONPLAATSNAAM(3544),
    /**
     * Waarde voor: Persoon - Overlijden - Datum.
     */
    PERSOON_OVERLIJDEN_DATUM(3546),
    /**
     * Waarde voor: Persoon - Overlijden - GemeenteCode.
     */
    PERSOON_OVERLIJDEN_GEMEENTECODE(3551),
    /**
     * Waarde voor: Persoon - Overlijden - BuitenlandsePlaats.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS(3552),
    /**
     * Waarde voor: Persoon - Overlijden - OmschrijvingLocatie.
     */
    PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE(3555),
    /**
     * Waarde voor: Persoon - Overlijden - BuitenlandseRegio.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSEREGIO(3556),
    /**
     * Waarde voor: Persoon - Overlijden - LandGebiedCode.
     */
    PERSOON_OVERLIJDEN_LANDGEBIEDCODE(3558),
    /**
     * Waarde voor: Persoon - Overlijden - Persoon.
     */
    PERSOON_OVERLIJDEN_PERSOON(4145),
    /**
     * Waarde voor: Persoon - Overlijden - TijdstipRegistratie.
     */
    PERSOON_OVERLIJDEN_TIJDSTIPREGISTRATIE(4146),
    /**
     * Waarde voor: Persoon - Overlijden - TijdstipVerval.
     */
    PERSOON_OVERLIJDEN_TIJDSTIPVERVAL(4147),
    /**
     * Waarde voor: Persoon - Overlijden - ActieInhoud.
     */
    PERSOON_OVERLIJDEN_ACTIEINHOUD(4148),
    /**
     * Waarde voor: Persoon - Overlijden - ActieVerval.
     */
    PERSOON_OVERLIJDEN_ACTIEVERVAL(4149),
    /**
     * Waarde voor: Persoon - Overlijden - VoorkomenSleutel.
     */
    PERSOON_OVERLIJDEN_VOORKOMENSLEUTEL(4539),
    /**
     * Waarde voor: Persoon - Overlijden - NadereAanduidingVerval.
     */
    PERSOON_OVERLIJDEN_NADEREAANDUIDINGVERVAL(11134),
    /**
     * Waarde voor: Persoon - Overlijden - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_OVERLIJDEN_ACTIEVERVALTBVLEVERINGMUTATIES(18858),
    /**
     * Waarde voor: Persoon - Overlijden - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_OVERLIJDEN_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18859),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - AanduidingCode.
     */
    PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE(3310),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - DatumMededeling.
     */
    PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING(3325),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - DatumVoorzienEinde.
     */
    PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE(3481),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - Persoon.
     */
    PERSOON_VERBLIJFSRECHT_PERSOON(4158),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - TijdstipRegistratie.
     */
    PERSOON_VERBLIJFSRECHT_TIJDSTIPREGISTRATIE(4161),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - TijdstipVerval.
     */
    PERSOON_VERBLIJFSRECHT_TIJDSTIPVERVAL(4162),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - ActieInhoud.
     */
    PERSOON_VERBLIJFSRECHT_ACTIEINHOUD(4163),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - ActieVerval.
     */
    PERSOON_VERBLIJFSRECHT_ACTIEVERVAL(4164),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - VoorkomenSleutel.
     */
    PERSOON_VERBLIJFSRECHT_VOORKOMENSLEUTEL(4542),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - NadereAanduidingVerval.
     */
    PERSOON_VERBLIJFSRECHT_NADEREAANDUIDINGVERVAL(11137),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_VERBLIJFSRECHT_ACTIEVERVALTBVLEVERINGMUTATIES(18864),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_VERBLIJFSRECHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18865),
    /**
     * Waarde voor: Persoon - Verblijfsrecht - DatumAanvang.
     */
    PERSOON_VERBLIJFSRECHT_DATUMAANVANG(21315),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - ActieVerval.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEVERVAL(13887),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMAANVANGGELDIGHEID(14119),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_DATUMEINDEGELDIGHEID(14120),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - ActieInhoud.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEINHOUD(14123),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_ACTIEAANPASSINGGELDIGHEID(14124),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_NADEREAANDUIDINGVERVAL(14125),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPREGISTRATIE(14170),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag - TijdstipVerval.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG_TIJDSTIPVERVAL(14171),
    /**
     * Waarde voor: Persoon - Indicatie - DerdeHeeftGezag.
     */
    PERSOON_INDICATIE_DERDEHEEFTGEZAG(14393),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - Indicatie.
     */
    PERSOON_UITSLUITINGKIESRECHT_INDICATIE(3322),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - DatumVoorzienEinde.
     */
    PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE(3559),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - Persoon.
     */
    PERSOON_UITSLUITINGKIESRECHT_PERSOON(4171),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - TijdstipRegistratie.
     */
    PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPREGISTRATIE(4172),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - TijdstipVerval.
     */
    PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPVERVAL(4173),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - ActieInhoud.
     */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEINHOUD(4174),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - ActieVerval.
     */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEVERVAL(4175),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - VoorkomenSleutel.
     */
    PERSOON_UITSLUITINGKIESRECHT_VOORKOMENSLEUTEL(4545),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - NadereAanduidingVerval.
     */
    PERSOON_UITSLUITINGKIESRECHT_NADEREAANDUIDINGVERVAL(11138),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_UITSLUITINGKIESRECHT_ACTIEVERVALTBVLEVERINGMUTATIES(18866),
    /**
     * Waarde voor: Persoon - UitsluitingKiesrecht - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_UITSLUITINGKIESRECHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18867),
    /**
     * Waarde voor: Persoon - Inschrijving - Versienummer.
     */
    PERSOON_INSCHRIJVING_VERSIENUMMER(3250),
    /**
     * Waarde voor: Persoon - Inschrijving - Datum.
     */
    PERSOON_INSCHRIJVING_DATUM(3570),
    /**
     * Waarde voor: Persoon - Inschrijving - Persoon.
     */
    PERSOON_INSCHRIJVING_PERSOON(4236),
    /**
     * Waarde voor: Persoon - Inschrijving - TijdstipRegistratie.
     */
    PERSOON_INSCHRIJVING_TIJDSTIPREGISTRATIE(4239),
    /**
     * Waarde voor: Persoon - Inschrijving - TijdstipVerval.
     */
    PERSOON_INSCHRIJVING_TIJDSTIPVERVAL(4240),
    /**
     * Waarde voor: Persoon - Inschrijving - ActieInhoud.
     */
    PERSOON_INSCHRIJVING_ACTIEINHOUD(4241),
    /**
     * Waarde voor: Persoon - Inschrijving - ActieVerval.
     */
    PERSOON_INSCHRIJVING_ACTIEVERVAL(4242),
    /**
     * Waarde voor: Persoon - Inschrijving - VoorkomenSleutel.
     */
    PERSOON_INSCHRIJVING_VOORKOMENSLEUTEL(4566),
    /**
     * Waarde voor: Persoon - Inschrijving - NadereAanduidingVerval.
     */
    PERSOON_INSCHRIJVING_NADEREAANDUIDINGVERVAL(11131),
    /**
     * Waarde voor: Persoon - Inschrijving - Datumtijdstempel.
     */
    PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL(11186),
    /**
     * Waarde voor: Persoon - Inschrijving - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_INSCHRIJVING_ACTIEVERVALTBVLEVERINGMUTATIES(18852),
    /**
     * Waarde voor: Persoon - Inschrijving - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_INSCHRIJVING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18853),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - Code.
     */
    PERSOON_GESLACHTSAANDUIDING_CODE(3031),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - Persoon.
     */
    PERSOON_GESLACHTSAANDUIDING_PERSOON(4088),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(4089),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(4090),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(4091),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(4092),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(4093),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - ActieVerval.
     */
    PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(4094),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(4095),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(4527),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(11130),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18850),
    /**
     * Waarde voor: Persoon - Geslachtsaanduiding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18851),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(1968),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - PredicaatCode.
     */
    PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(1969),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - Voornamen.
     */
    PERSOON_SAMENGESTELDENAAM_VOORNAMEN(3092),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(3094),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(3253),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(3309),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(3592),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(3914),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - Persoon.
     */
    PERSOON_SAMENGESTELDENAAM_PERSOON(4098),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(4099),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(4100),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(4101),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(4102),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - ActieInhoud.
     */
    PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(4103),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - ActieVerval.
     */
    PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(4104),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(4105),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(4530),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(11128),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18846),
    /**
     * Waarde voor: Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18847),
    /**
     * Waarde voor: Persoon - Persoonskaart - PartijCode.
     */
    PERSOON_PERSOONSKAART_PARTIJCODE(3233),
    /**
     * Waarde voor: Persoon - Persoonskaart - IndicatieVolledigGeconverteerd.
     */
    PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD(3313),
    /**
     * Waarde voor: Persoon - Persoonskaart - Persoon.
     */
    PERSOON_PERSOONSKAART_PERSOON(4217),
    /**
     * Waarde voor: Persoon - Persoonskaart - TijdstipRegistratie.
     */
    PERSOON_PERSOONSKAART_TIJDSTIPREGISTRATIE(4218),
    /**
     * Waarde voor: Persoon - Persoonskaart - TijdstipVerval.
     */
    PERSOON_PERSOONSKAART_TIJDSTIPVERVAL(4219),
    /**
     * Waarde voor: Persoon - Persoonskaart - ActieInhoud.
     */
    PERSOON_PERSOONSKAART_ACTIEINHOUD(4220),
    /**
     * Waarde voor: Persoon - Persoonskaart - ActieVerval.
     */
    PERSOON_PERSOONSKAART_ACTIEVERVAL(4221),
    /**
     * Waarde voor: Persoon - Persoonskaart - VoorkomenSleutel.
     */
    PERSOON_PERSOONSKAART_VOORKOMENSLEUTEL(4560),
    /**
     * Waarde voor: Persoon - Persoonskaart - NadereAanduidingVerval.
     */
    PERSOON_PERSOONSKAART_NADEREAANDUIDINGVERVAL(11140),
    /**
     * Waarde voor: Persoon - Persoonskaart - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_PERSOONSKAART_ACTIEVERVALTBVLEVERINGMUTATIES(18870),
    /**
     * Waarde voor: Persoon - Persoonskaart - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_PERSOONSKAART_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18871),
    /**
     * Waarde voor: Persoon - Bijhouding - BijhoudingsaardCode.
     */
    PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE(3568),
    /**
     * Waarde voor: Persoon - Bijhouding - PartijCode.
     */
    PERSOON_BIJHOUDING_PARTIJCODE(3573),
    /**
     * Waarde voor: Persoon - Bijhouding - IndicatieOnverwerktDocumentAanwezig.
     */
    PERSOON_BIJHOUDING_INDICATIEONVERWERKTDOCUMENTAANWEZIG(3578),
    /**
     * Waarde voor: Persoon - Bijhouding - Persoon.
     */
    PERSOON_BIJHOUDING_PERSOON(4188),
    /**
     * Waarde voor: Persoon - Bijhouding - DatumAanvangGeldigheid.
     */
    PERSOON_BIJHOUDING_DATUMAANVANGGELDIGHEID(4189),
    /**
     * Waarde voor: Persoon - Bijhouding - DatumEindeGeldigheid.
     */
    PERSOON_BIJHOUDING_DATUMEINDEGELDIGHEID(4190),
    /**
     * Waarde voor: Persoon - Bijhouding - TijdstipRegistratie.
     */
    PERSOON_BIJHOUDING_TIJDSTIPREGISTRATIE(4191),
    /**
     * Waarde voor: Persoon - Bijhouding - TijdstipVerval.
     */
    PERSOON_BIJHOUDING_TIJDSTIPVERVAL(4192),
    /**
     * Waarde voor: Persoon - Bijhouding - ActieInhoud.
     */
    PERSOON_BIJHOUDING_ACTIEINHOUD(4193),
    /**
     * Waarde voor: Persoon - Bijhouding - ActieVerval.
     */
    PERSOON_BIJHOUDING_ACTIEVERVAL(4194),
    /**
     * Waarde voor: Persoon - Bijhouding - ActieAanpassingGeldigheid.
     */
    PERSOON_BIJHOUDING_ACTIEAANPASSINGGELDIGHEID(4195),
    /**
     * Waarde voor: Persoon - Bijhouding - VoorkomenSleutel.
     */
    PERSOON_BIJHOUDING_VOORKOMENSLEUTEL(4551),
    /**
     * Waarde voor: Persoon - Bijhouding - NadereBijhoudingsaardCode.
     */
    PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE(10864),
    /**
     * Waarde voor: Persoon - Bijhouding - NadereAanduidingVerval.
     */
    PERSOON_BIJHOUDING_NADEREAANDUIDINGVERVAL(11133),
    /**
     * Waarde voor: Persoon - Bijhouding - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_BIJHOUDING_ACTIEVERVALTBVLEVERINGMUTATIES(18856),
    /**
     * Waarde voor: Persoon - Bijhouding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_BIJHOUDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18857),
    /**
     * Waarde voor: Persoon - Migratie - LandGebiedCode.
     */
    PERSOON_MIGRATIE_LANDGEBIEDCODE(3579),
    /**
     * Waarde voor: Persoon - Migratie - Persoon.
     */
    PERSOON_MIGRATIE_PERSOON(4225),
    /**
     * Waarde voor: Persoon - Migratie - DatumAanvangGeldigheid.
     */
    PERSOON_MIGRATIE_DATUMAANVANGGELDIGHEID(4226),
    /**
     * Waarde voor: Persoon - Migratie - DatumEindeGeldigheid.
     */
    PERSOON_MIGRATIE_DATUMEINDEGELDIGHEID(4227),
    /**
     * Waarde voor: Persoon - Migratie - TijdstipRegistratie.
     */
    PERSOON_MIGRATIE_TIJDSTIPREGISTRATIE(4228),
    /**
     * Waarde voor: Persoon - Migratie - TijdstipVerval.
     */
    PERSOON_MIGRATIE_TIJDSTIPVERVAL(4229),
    /**
     * Waarde voor: Persoon - Migratie - ActieInhoud.
     */
    PERSOON_MIGRATIE_ACTIEINHOUD(4230),
    /**
     * Waarde voor: Persoon - Migratie - ActieVerval.
     */
    PERSOON_MIGRATIE_ACTIEVERVAL(4231),
    /**
     * Waarde voor: Persoon - Migratie - ActieAanpassingGeldigheid.
     */
    PERSOON_MIGRATIE_ACTIEAANPASSINGGELDIGHEID(4232),
    /**
     * Waarde voor: Persoon - Migratie - VoorkomenSleutel.
     */
    PERSOON_MIGRATIE_VOORKOMENSLEUTEL(4563),
    /**
     * Waarde voor: Persoon - Migratie - SoortCode.
     */
    PERSOON_MIGRATIE_SOORTCODE(10881),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel1.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1(10882),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel2.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2(10883),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel3.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3(10884),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel4.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4(10885),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel5.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5(10886),
    /**
     * Waarde voor: Persoon - Migratie - BuitenlandsAdresRegel6.
     */
    PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6(10887),
    /**
     * Waarde voor: Persoon - Migratie - NadereAanduidingVerval.
     */
    PERSOON_MIGRATIE_NADEREAANDUIDINGVERVAL(11136),
    /**
     * Waarde voor: Persoon - Migratie - RedenWijzigingCode.
     */
    PERSOON_MIGRATIE_REDENWIJZIGINGCODE(11277),
    /**
     * Waarde voor: Persoon - Migratie - AangeverCode.
     */
    PERSOON_MIGRATIE_AANGEVERCODE(11278),
    /**
     * Waarde voor: Persoon - Migratie - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_MIGRATIE_ACTIEVERVALTBVLEVERINGMUTATIES(18862),
    /**
     * Waarde voor: Persoon - Migratie - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_MIGRATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18863),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - ActieVerval.
     */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEVERVAL(13895),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_ONDERCURATELE_DATUMAANVANGGELDIGHEID(14126),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_ONDERCURATELE_DATUMEINDEGELDIGHEID(14127),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - ActieInhoud.
     */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEINHOUD(14130),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_ONDERCURATELE_ACTIEAANPASSINGGELDIGHEID(14131),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_ONDERCURATELE_NADEREAANDUIDINGVERVAL(14132),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPREGISTRATIE(14172),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele - TijdstipVerval.
     */
    PERSOON_INDICATIE_ONDERCURATELE_TIJDSTIPVERVAL(14173),
    /**
     * Waarde voor: Persoon - Indicatie - OnderCuratele.
     */
    PERSOON_INDICATIE_ONDERCURATELE(14394),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - IndicatieDeelname.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME(3320),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - DatumAanleidingAanpassing.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING(3562),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - DatumVoorzienEindeUitsluiting.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING(3564),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - Persoon.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_PERSOON(4179),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - TijdstipRegistratie.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPREGISTRATIE(4180),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - TijdstipVerval.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPVERVAL(4181),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - ActieInhoud.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEINHOUD(4182),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - ActieVerval.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEVERVAL(4183),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - VoorkomenSleutel.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_VOORKOMENSLEUTEL(4548),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - NadereAanduidingVerval.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_NADEREAANDUIDINGVERVAL(11139),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_ACTIEVERVALTBVLEVERINGMUTATIES(18868),
    /**
     * Waarde voor: Persoon - DeelnameEUVerkiezingen - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18869),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - ActieVerval.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_ACTIEVERVAL(13914),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - ActieInhoud.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_ACTIEINHOUD(14146),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_NADEREAANDUIDINGVERVAL(14147),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE(14178),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking - TijdstipVerval.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_TIJDSTIPVERVAL(14179),
    /**
     * Waarde voor: Persoon - Indicatie - VolledigeVerstrekkingsbeperking.
     */
    PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING(14397),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - ActieVerval.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_ACTIEVERVAL(13901),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - ActieInhoud.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_ACTIEINHOUD(14135),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_NADEREAANDUIDINGVERVAL(14136),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPREGISTRATIE(14174),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie - TijdstipVerval.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_TIJDSTIPVERVAL(14175),
    /**
     * Waarde voor: Persoon - Indicatie - BijzondereVerblijfsrechtelijkePositie.
     */
    PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE(14395),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - ActieVerval.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEVERVAL(13921),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMAANVANGGELDIGHEID(14148),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_DATUMEINDEGELDIGHEID(14149),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - ActieInhoud.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEINHOUD(14152),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_ACTIEAANPASSINGGELDIGHEID(14153),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_NADEREAANDUIDINGVERVAL(14154),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPREGISTRATIE(14180),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander - TijdstipVerval.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_TIJDSTIPVERVAL(14181),
    /**
     * Waarde voor: Persoon - Indicatie - VastgesteldNietNederlander.
     */
    PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER(14398),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - ActieVerval.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEVERVAL(13929),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMAANVANGGELDIGHEID(14155),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_DATUMEINDEGELDIGHEID(14156),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - ActieInhoud.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEINHOUD(14159),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_ACTIEAANPASSINGGELDIGHEID(14160),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_NADEREAANDUIDINGVERVAL(14161),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPREGISTRATIE(14182),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander - TijdstipVerval.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_TIJDSTIPVERVAL(14183),
    /**
     * Waarde voor: Persoon - Indicatie - BehandeldAlsNederlander.
     */
    PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER(14399),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - ActieVerval.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_ACTIEVERVAL(13935),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - ActieInhoud.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_ACTIEINHOUD(14164),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_NADEREAANDUIDINGVERVAL(14165),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPREGISTRATIE(14184),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument - TijdstipVerval.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_TIJDSTIPVERVAL(14185),
    /**
     * Waarde voor: Persoon - Indicatie - SignaleringMetBetrekkingTotVerstrekkenReisdocument.
     */
    PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT(14400),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - TijdstipLaatsteWijziging.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING(3251),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - AdministratieveHandeling.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING(10111),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - Persoon.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_PERSOON(10148),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - TijdstipRegistratie.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPREGISTRATIE(10149),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - TijdstipVerval.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPVERVAL(10150),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - ActieInhoud.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEINHOUD(10151),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - ActieVerval.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEVERVAL(10152),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - VoorkomenSleutel.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_VOORKOMENSLEUTEL(10169),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - Sorteervolgorde.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_SORTEERVOLGORDE(10404),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - IndicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEONVERWERKTBIJHOUDINGSVOORSTELNIETINGEZETENEAANWEZIG(10899),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - TijdstipLaatsteWijzigingGBASystematiek.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGINGGBASYSTEMATIEK(10901),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - NadereAanduidingVerval.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_NADEREAANDUIDINGVERVAL(11126),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_ACTIEVERVALTBVLEVERINGMUTATIES(18842),
    /**
     * Waarde voor: Persoon - AfgeleidAdministratief - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_AFGELEIDADMINISTRATIEF_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18843),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - VorigeBurgerservicenummer.
     */
    PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER(3134),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - VolgendeBurgerservicenummer.
     */
    PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER(3136),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - VorigeAdministratienummer.
     */
    PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER(3247),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - VolgendeAdministratienummer.
     */
    PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER(3248),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - Persoon.
     */
    PERSOON_NUMMERVERWIJZING_PERSOON(10933),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - DatumAanvangGeldigheid.
     */
    PERSOON_NUMMERVERWIJZING_DATUMAANVANGGELDIGHEID(10934),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - DatumEindeGeldigheid.
     */
    PERSOON_NUMMERVERWIJZING_DATUMEINDEGELDIGHEID(10935),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - TijdstipRegistratie.
     */
    PERSOON_NUMMERVERWIJZING_TIJDSTIPREGISTRATIE(10936),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - TijdstipVerval.
     */
    PERSOON_NUMMERVERWIJZING_TIJDSTIPVERVAL(10937),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - ActieInhoud.
     */
    PERSOON_NUMMERVERWIJZING_ACTIEINHOUD(10938),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - ActieVerval.
     */
    PERSOON_NUMMERVERWIJZING_ACTIEVERVAL(10939),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - ActieAanpassingGeldigheid.
     */
    PERSOON_NUMMERVERWIJZING_ACTIEAANPASSINGGELDIGHEID(10940),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - VoorkomenSleutel.
     */
    PERSOON_NUMMERVERWIJZING_VOORKOMENSLEUTEL(10976),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - NadereAanduidingVerval.
     */
    PERSOON_NUMMERVERWIJZING_NADEREAANDUIDINGVERVAL(11132),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_NUMMERVERWIJZING_ACTIEVERVALTBVLEVERINGMUTATIES(18854),
    /**
     * Waarde voor: Persoon - Nummerverwijzing - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_NUMMERVERWIJZING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18855),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Identiteit.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT(2066),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Standaard.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD(3598),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Persoon.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_PERSOON(3024),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Volgnummer.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER(3029),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - ObjectSleutel.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_OBJECTSLEUTEL(3648),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Stam.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_STAM(3025),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Voorvoegsel.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL(3030),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - Scheidingsteken.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN(3069),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - PredicaatCode.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE(3117),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - AdellijkeTitelCode.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE(3118),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - PersoonGeslachtsnaamcomponent.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_PERSOONGESLACHTSNAAMCOMPONENT(4301),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - DatumAanvangGeldigheid.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_DATUMAANVANGGELDIGHEID(4302),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - DatumEindeGeldigheid.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_DATUMEINDEGELDIGHEID(4303),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - TijdstipRegistratie.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPREGISTRATIE(4304),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - TijdstipVerval.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPVERVAL(4305),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - ActieInhoud.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEINHOUD(4306),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - ActieVerval.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEVERVAL(4307),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - ActieAanpassingGeldigheid.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEAANPASSINGGELDIGHEID(4308),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - VoorkomenSleutel.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_VOORKOMENSLEUTEL(4578),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - NadereAanduidingVerval.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_NADEREAANDUIDINGVERVAL(11143),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_ACTIEVERVALTBVLEVERINGMUTATIES(18874),
    /**
     * Waarde voor: Persoon - Geslachtsnaamcomponent - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_GESLACHTSNAAMCOMPONENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18875),
    /**
     * Waarde voor: Persoon - Voornaam - Identiteit.
     */
    PERSOON_VOORNAAM_IDENTITEIT(2065),
    /**
     * Waarde voor: Persoon - Voornaam - Standaard.
     */
    PERSOON_VOORNAAM_STANDAARD(3050),
    /**
     * Waarde voor: Persoon - Voornaam - Persoon.
     */
    PERSOON_VOORNAAM_PERSOON(3023),
    /**
     * Waarde voor: Persoon - Voornaam - Volgnummer.
     */
    PERSOON_VOORNAAM_VOLGNUMMER(3028),
    /**
     * Waarde voor: Persoon - Voornaam - ObjectSleutel.
     */
    PERSOON_VOORNAAM_OBJECTSLEUTEL(3644),
    /**
     * Waarde voor: Persoon - Voornaam - Naam.
     */
    PERSOON_VOORNAAM_NAAM(3026),
    /**
     * Waarde voor: Persoon - Voornaam - PersoonVoornaam.
     */
    PERSOON_VOORNAAM_PERSOONVOORNAAM(4359),
    /**
     * Waarde voor: Persoon - Voornaam - DatumAanvangGeldigheid.
     */
    PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID(4360),
    /**
     * Waarde voor: Persoon - Voornaam - DatumEindeGeldigheid.
     */
    PERSOON_VOORNAAM_DATUMEINDEGELDIGHEID(4361),
    /**
     * Waarde voor: Persoon - Voornaam - TijdstipRegistratie.
     */
    PERSOON_VOORNAAM_TIJDSTIPREGISTRATIE(4362),
    /**
     * Waarde voor: Persoon - Voornaam - TijdstipVerval.
     */
    PERSOON_VOORNAAM_TIJDSTIPVERVAL(4363),
    /**
     * Waarde voor: Persoon - Voornaam - ActieInhoud.
     */
    PERSOON_VOORNAAM_ACTIEINHOUD(4364),
    /**
     * Waarde voor: Persoon - Voornaam - ActieVerval.
     */
    PERSOON_VOORNAAM_ACTIEVERVAL(4365),
    /**
     * Waarde voor: Persoon - Voornaam - ActieAanpassingGeldigheid.
     */
    PERSOON_VOORNAAM_ACTIEAANPASSINGGELDIGHEID(4366),
    /**
     * Waarde voor: Persoon - Voornaam - VoorkomenSleutel.
     */
    PERSOON_VOORNAAM_VOORKOMENSLEUTEL(4593),
    /**
     * Waarde voor: Persoon - Voornaam - NadereAanduidingVerval.
     */
    PERSOON_VOORNAAM_NADEREAANDUIDINGVERVAL(11150),
    /**
     * Waarde voor: Persoon - Voornaam - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_VOORNAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18888),
    /**
     * Waarde voor: Persoon - Voornaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_VOORNAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18889),
    /**
     * Waarde voor: Plaats - Identiteit.
     */
    PLAATS_IDENTITEIT(2091),
    /**
     * Waarde voor: Plaats - ObjectSleutel.
     */
    PLAATS_OBJECTSLEUTEL(3039),
    /**
     * Waarde voor: Plaats - Naam.
     */
    PLAATS_NAAM(3115),
    /**
     * Waarde voor: Plaats - DatumAanvangGeldigheid.
     */
    PLAATS_DATUMAANVANGGELDIGHEID(3984),
    /**
     * Waarde voor: Plaats - DatumEindeGeldigheid.
     */
    PLAATS_DATUMEINDEGELDIGHEID(3985),
    /**
     * Waarde voor: Plaats - Code.
     */
    PLAATS_CODE(5668),
    /**
     * Waarde voor: LandGebied - Identiteit.
     */
    LANDGEBIED_IDENTITEIT(2089),
    /**
     * Waarde voor: LandGebied - Iso31661Alpha2Code.
     */
    LANDGEBIED_ISO31661ALPHA2CODE(2144),
    /**
     * Waarde voor: LandGebied - ObjectSleutel.
     */
    LANDGEBIED_OBJECTSLEUTEL(3049),
    /**
     * Waarde voor: LandGebied - Naam.
     */
    LANDGEBIED_NAAM(3107),
    /**
     * Waarde voor: LandGebied - DatumAanvangGeldigheid.
     */
    LANDGEBIED_DATUMAANVANGGELDIGHEID(3937),
    /**
     * Waarde voor: LandGebied - DatumEindeGeldigheid.
     */
    LANDGEBIED_DATUMEINDEGELDIGHEID(3938),
    /**
     * Waarde voor: LandGebied - Code.
     */
    LANDGEBIED_CODE(5614),
    /**
     * Waarde voor: SoortActie - Identiteit.
     */
    SOORTACTIE_IDENTITEIT(2102),
    /**
     * Waarde voor: SoortActie - ObjectSleutel.
     */
    SOORTACTIE_OBJECTSLEUTEL(3065),
    /**
     * Waarde voor: SoortActie - Naam.
     */
    SOORTACTIE_NAAM(3068),
    /**
     * Waarde voor: Actie - Identiteit.
     */
    ACTIE_IDENTITEIT(2077),
    /**
     * Waarde voor: Actie - ObjectSleutel.
     */
    ACTIE_OBJECTSLEUTEL(3054),
    /**
     * Waarde voor: Actie - SoortNaam.
     */
    ACTIE_SOORTNAAM(3055),
    /**
     * Waarde voor: Actie - TijdstipRegistratie.
     */
    ACTIE_TIJDSTIPREGISTRATIE(3181),
    /**
     * Waarde voor: Actie - PartijCode.
     */
    ACTIE_PARTIJCODE(3209),
    /**
     * Waarde voor: Actie - DatumAanvangGeldigheid.
     */
    ACTIE_DATUMAANVANGGELDIGHEID(6175),
    /**
     * Waarde voor: Actie - DatumEindeGeldigheid.
     */
    ACTIE_DATUMEINDEGELDIGHEID(6265),
    /**
     * Waarde voor: Actie - AdministratieveHandeling.
     */
    ACTIE_ADMINISTRATIEVEHANDELING(9023),
    /**
     * Waarde voor: Actie - DatumOntlening.
     */
    ACTIE_DATUMONTLENING(10183),
    /**
     * Waarde voor: Nationaliteit - Identiteit.
     */
    NATIONALITEIT_IDENTITEIT(2090),
    /**
     * Waarde voor: Nationaliteit - ObjectSleutel.
     */
    NATIONALITEIT_OBJECTSLEUTEL(3089),
    /**
     * Waarde voor: Nationaliteit - Naam.
     */
    NATIONALITEIT_NAAM(3109),
    /**
     * Waarde voor: Nationaliteit - DatumAanvangGeldigheid.
     */
    NATIONALITEIT_DATUMAANVANGGELDIGHEID(3942),
    /**
     * Waarde voor: Nationaliteit - DatumEindeGeldigheid.
     */
    NATIONALITEIT_DATUMEINDEGELDIGHEID(3943),
    /**
     * Waarde voor: Nationaliteit - Code.
     */
    NATIONALITEIT_CODE(6080),
    /**
     * Waarde voor: Predicaat - Identiteit.
     */
    PREDICAAT_IDENTITEIT(2092),
    /**
     * Waarde voor: Predicaat - ObjectSleutel.
     */
    PREDICAAT_OBJECTSLEUTEL(3112),
    /**
     * Waarde voor: Predicaat - NaamMannelijk.
     */
    PREDICAAT_NAAMMANNELIJK(3119),
    /**
     * Waarde voor: Predicaat - NaamVrouwelijk.
     */
    PREDICAAT_NAAMVROUWELIJK(3120),
    /**
     * Waarde voor: Predicaat - Code.
     */
    PREDICAAT_CODE(3613),
    /**
     * Waarde voor: AdellijkeTitel - Identiteit.
     */
    ADELLIJKETITEL_IDENTITEIT(2082),
    /**
     * Waarde voor: AdellijkeTitel - ObjectSleutel.
     */
    ADELLIJKETITEL_OBJECTSLEUTEL(3098),
    /**
     * Waarde voor: AdellijkeTitel - NaamMannelijk.
     */
    ADELLIJKETITEL_NAAMMANNELIJK(3101),
    /**
     * Waarde voor: AdellijkeTitel - NaamVrouwelijk.
     */
    ADELLIJKETITEL_NAAMVROUWELIJK(3102),
    /**
     * Waarde voor: AdellijkeTitel - Code.
     */
    ADELLIJKETITEL_CODE(3345),
    /**
     * Waarde voor: Persoon - Nationaliteit - Identiteit.
     */
    PERSOON_NATIONALITEIT_IDENTITEIT(2067),
    /**
     * Waarde voor: Persoon - Nationaliteit - Standaard.
     */
    PERSOON_NATIONALITEIT_STANDAARD(3604),
    /**
     * Waarde voor: Persoon - Nationaliteit - Persoon.
     */
    PERSOON_NATIONALITEIT_PERSOON(3130),
    /**
     * Waarde voor: Persoon - Nationaliteit - NationaliteitCode.
     */
    PERSOON_NATIONALITEIT_NATIONALITEITCODE(3131),
    /**
     * Waarde voor: Persoon - Nationaliteit - ObjectSleutel.
     */
    PERSOON_NATIONALITEIT_OBJECTSLEUTEL(3652),
    /**
     * Waarde voor: Persoon - Nationaliteit - RedenVerkrijgingCode.
     */
    PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE(3229),
    /**
     * Waarde voor: Persoon - Nationaliteit - RedenVerliesCode.
     */
    PERSOON_NATIONALITEIT_REDENVERLIESCODE(3230),
    /**
     * Waarde voor: Persoon - Nationaliteit - PersoonNationaliteit.
     */
    PERSOON_NATIONALITEIT_PERSOONNATIONALITEIT(4325),
    /**
     * Waarde voor: Persoon - Nationaliteit - DatumAanvangGeldigheid.
     */
    PERSOON_NATIONALITEIT_DATUMAANVANGGELDIGHEID(4326),
    /**
     * Waarde voor: Persoon - Nationaliteit - DatumEindeGeldigheid.
     */
    PERSOON_NATIONALITEIT_DATUMEINDEGELDIGHEID(4327),
    /**
     * Waarde voor: Persoon - Nationaliteit - TijdstipRegistratie.
     */
    PERSOON_NATIONALITEIT_TIJDSTIPREGISTRATIE(4328),
    /**
     * Waarde voor: Persoon - Nationaliteit - TijdstipVerval.
     */
    PERSOON_NATIONALITEIT_TIJDSTIPVERVAL(4329),
    /**
     * Waarde voor: Persoon - Nationaliteit - ActieInhoud.
     */
    PERSOON_NATIONALITEIT_ACTIEINHOUD(4330),
    /**
     * Waarde voor: Persoon - Nationaliteit - ActieVerval.
     */
    PERSOON_NATIONALITEIT_ACTIEVERVAL(4331),
    /**
     * Waarde voor: Persoon - Nationaliteit - ActieAanpassingGeldigheid.
     */
    PERSOON_NATIONALITEIT_ACTIEAANPASSINGGELDIGHEID(4332),
    /**
     * Waarde voor: Persoon - Nationaliteit - VoorkomenSleutel.
     */
    PERSOON_NATIONALITEIT_VOORKOMENSLEUTEL(4584),
    /**
     * Waarde voor: Persoon - Nationaliteit - NadereAanduidingVerval.
     */
    PERSOON_NATIONALITEIT_NADEREAANDUIDINGVERVAL(11145),
    /**
     * Waarde voor: Persoon - Nationaliteit - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_NATIONALITEIT_ACTIEVERVALTBVLEVERINGMUTATIES(18878),
    /**
     * Waarde voor: Persoon - Nationaliteit - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_NATIONALITEIT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18879),
    /**
     * Waarde voor: Persoon - Nationaliteit - IndicatieBijhoudingBeeindigd.
     */
    PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD(21230),
    /**
     * Waarde voor: Persoon - Nationaliteit - MigratieRedenOpnameNationaliteit.
     */
    PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT(21231),
    /**
     * Waarde voor: Persoon - Nationaliteit - MigratieRedenBeeindigenNationaliteit.
     */
    PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT(21232),
    /**
     * Waarde voor: Persoon - Nationaliteit - MigratieDatumEindeBijhouding.
     */
    PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING(21233),
    /**
     * Waarde voor: Document - Identiteit.
     */
    DOCUMENT_IDENTITEIT(2078),
    /**
     * Waarde voor: Document - Standaard.
     */
    DOCUMENT_STANDAARD(3784),
    /**
     * Waarde voor: Document - ObjectSleutel.
     */
    DOCUMENT_OBJECTSLEUTEL(3138),
    /**
     * Waarde voor: Document - SoortNaam.
     */
    DOCUMENT_SOORTNAAM(3157),
    /**
     * Waarde voor: Document - PartijCode.
     */
    DOCUMENT_PARTIJCODE(3139),
    /**
     * Waarde voor: Document - Identificatie.
     */
    DOCUMENT_IDENTIFICATIE(3160),
    /**
     * Waarde voor: Document - Omschrijving.
     */
    DOCUMENT_OMSCHRIJVING(3162),
    /**
     * Waarde voor: Document - Aktenummer.
     */
    DOCUMENT_AKTENUMMER(3786),
    /**
     * Waarde voor: Document - Document.
     */
    DOCUMENT_DOCUMENT(4043),
    /**
     * Waarde voor: Document - TijdstipRegistratie.
     */
    DOCUMENT_TIJDSTIPREGISTRATIE(4044),
    /**
     * Waarde voor: Document - TijdstipVerval.
     */
    DOCUMENT_TIJDSTIPVERVAL(4045),
    /**
     * Waarde voor: Document - ActieInhoud.
     */
    DOCUMENT_ACTIEINHOUD(4046),
    /**
     * Waarde voor: Document - ActieVerval.
     */
    DOCUMENT_ACTIEVERVAL(4047),
    /**
     * Waarde voor: Document - VoorkomenSleutel.
     */
    DOCUMENT_VOORKOMENSLEUTEL(4515),
    /**
     * Waarde voor: Document - NadereAanduidingVerval.
     */
    DOCUMENT_NADEREAANDUIDINGVERVAL(11118),
    /**
     * Waarde voor: Document - ActieVervalTbvLeveringMutaties.
     */
    DOCUMENT_ACTIEVERVALTBVLEVERINGMUTATIES(18826),
    /**
     * Waarde voor: Document - IndicatieVoorkomenTbvLeveringMutaties.
     */
    DOCUMENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18827),
    /**
     * Waarde voor: Partij - Identiteit.
     */
    PARTIJ_IDENTITEIT(2154),
    /**
     * Waarde voor: Partij - Standaard.
     */
    PARTIJ_STANDAARD(4618),
    /**
     * Waarde voor: Partij - Bijhouding.
     */
    PARTIJ_BIJHOUDING(21445),
    /**
     * Waarde voor: Partij - ObjectSleutel.
     */
    PARTIJ_OBJECTSLEUTEL(3143),
    /**
     * Waarde voor: Partij - Code.
     */
    PARTIJ_CODE(4601),
    /**
     * Waarde voor: Partij - SoortNaam.
     */
    PARTIJ_SOORTNAAM(2195),
    /**
     * Waarde voor: Partij - IndicatieVerstrekkingsbeperkingMogelijk.
     */
    PARTIJ_INDICATIEVERSTREKKINGSBEPERKINGMOGELIJK(2196),
    /**
     * Waarde voor: Partij - DatumIngang.
     */
    PARTIJ_DATUMINGANG(2199),
    /**
     * Waarde voor: Partij - DatumEinde.
     */
    PARTIJ_DATUMEINDE(2200),
    /**
     * Waarde voor: Partij - Naam.
     */
    PARTIJ_NAAM(3145),
    /**
     * Waarde voor: Partij - Partij.
     */
    PARTIJ_PARTIJ(4620),
    /**
     * Waarde voor: Partij - TijdstipRegistratie.
     */
    PARTIJ_TIJDSTIPREGISTRATIE(4621),
    /**
     * Waarde voor: Partij - TijdstipVerval.
     */
    PARTIJ_TIJDSTIPVERVAL(4622),
    /**
     * Waarde voor: Partij - ActieInhoud.
     */
    PARTIJ_ACTIEINHOUD(4623),
    /**
     * Waarde voor: Partij - ActieVerval.
     */
    PARTIJ_ACTIEVERVAL(4624),
    /**
     * Waarde voor: Partij - VoorkomenSleutel.
     */
    PARTIJ_VOORKOMENSLEUTEL(4630),
    /**
     * Waarde voor: Partij - NadereAanduidingVerval.
     */
    PARTIJ_NADEREAANDUIDINGVERVAL(11123),
    /**
     * Waarde voor: Partij - ActieVervalTbvLeveringMutaties.
     */
    PARTIJ_ACTIEVERVALTBVLEVERINGMUTATIES(18834),
    /**
     * Waarde voor: Partij - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PARTIJ_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18835),
    /**
     * Waarde voor: Partij - OIN.
     */
    PARTIJ_OIN(21905),
    /**
     * Waarde voor: Partij - Bijhouding - IndicatieAutomatischFiatteren.
     */
    PARTIJ_BIJHOUDING_INDICATIEAUTOMATISCHFIATTEREN(21446),
    /**
     * Waarde voor: Partij - Bijhouding - DatumOvergangNaarBRP.
     */
    PARTIJ_BIJHOUDING_DATUMOVERGANGNAARBRP(21447),
    /**
     * Waarde voor: Partij - Bijhouding - Partij.
     */
    PARTIJ_BIJHOUDING_PARTIJ(21493),
    /**
     * Waarde voor: Partij - Bijhouding - TijdstipRegistratie.
     */
    PARTIJ_BIJHOUDING_TIJDSTIPREGISTRATIE(21494),
    /**
     * Waarde voor: Partij - Bijhouding - TijdstipVerval.
     */
    PARTIJ_BIJHOUDING_TIJDSTIPVERVAL(21495),
    /**
     * Waarde voor: Partij - Bijhouding - NadereAanduidingVerval.
     */
    PARTIJ_BIJHOUDING_NADEREAANDUIDINGVERVAL(21496),
    /**
     * Waarde voor: Partij - Bijhouding - ActieInhoud.
     */
    PARTIJ_BIJHOUDING_ACTIEINHOUD(21497),
    /**
     * Waarde voor: Partij - Bijhouding - ActieVerval.
     */
    PARTIJ_BIJHOUDING_ACTIEVERVAL(21498),
    /**
     * Waarde voor: Partij - Bijhouding - ActieVervalTbvLeveringMutaties.
     */
    PARTIJ_BIJHOUDING_ACTIEVERVALTBVLEVERINGMUTATIES(21499),
    /**
     * Waarde voor: Partij - Bijhouding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PARTIJ_BIJHOUDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21500),
    /**
     * Waarde voor: Partij - Bijhouding - VoorkomenSleutel.
     */
    PARTIJ_BIJHOUDING_VOORKOMENSLEUTEL(21538),
    /**
     * Waarde voor: SoortDocument - Identiteit.
     */
    SOORTDOCUMENT_IDENTITEIT(2100),
    /**
     * Waarde voor: SoortDocument - ObjectSleutel.
     */
    SOORTDOCUMENT_OBJECTSLEUTEL(3151),
    /**
     * Waarde voor: SoortDocument - Naam.
     */
    SOORTDOCUMENT_NAAM(3154),
    /**
     * Waarde voor: SoortDocument - Omschrijving.
     */
    SOORTDOCUMENT_OMSCHRIJVING(9443),
    /**
     * Waarde voor: SoortDocument - Rangorde.
     */
    SOORTDOCUMENT_RANGORDE(9444),
    /**
     * Waarde voor: Onderzoek - Identiteit.
     */
    ONDERZOEK_IDENTITEIT(2072),
    /**
     * Waarde voor: Onderzoek - Standaard.
     */
    ONDERZOEK_STANDAARD(3774),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF(10841),
    /**
     * Waarde voor: Onderzoek - ObjectSleutel.
     */
    ONDERZOEK_OBJECTSLEUTEL(3169),
    /**
     * Waarde voor: Onderzoek - DatumAanvang.
     */
    ONDERZOEK_DATUMAANVANG(3178),
    /**
     * Waarde voor: Onderzoek - DatumEinde.
     */
    ONDERZOEK_DATUMEINDE(3179),
    /**
     * Waarde voor: Onderzoek - Omschrijving.
     */
    ONDERZOEK_OMSCHRIJVING(3772),
    /**
     * Waarde voor: Onderzoek - Onderzoek.
     */
    ONDERZOEK_ONDERZOEK(4068),
    /**
     * Waarde voor: Onderzoek - TijdstipRegistratie.
     */
    ONDERZOEK_TIJDSTIPREGISTRATIE(4069),
    /**
     * Waarde voor: Onderzoek - TijdstipVerval.
     */
    ONDERZOEK_TIJDSTIPVERVAL(4070),
    /**
     * Waarde voor: Onderzoek - ActieInhoud.
     */
    ONDERZOEK_ACTIEINHOUD(4071),
    /**
     * Waarde voor: Onderzoek - ActieVerval.
     */
    ONDERZOEK_ACTIEVERVAL(4072),
    /**
     * Waarde voor: Onderzoek - VoorkomenSleutel.
     */
    ONDERZOEK_VOORKOMENSLEUTEL(4521),
    /**
     * Waarde voor: Onderzoek - VerwachteAfhandeldatum.
     */
    ONDERZOEK_VERWACHTEAFHANDELDATUM(10848),
    /**
     * Waarde voor: Onderzoek - StatusNaam.
     */
    ONDERZOEK_STATUSNAAM(10849),
    /**
     * Waarde voor: Onderzoek - NadereAanduidingVerval.
     */
    ONDERZOEK_NADEREAANDUIDINGVERVAL(11121),
    /**
     * Waarde voor: Onderzoek - ActieVervalTbvLeveringMutaties.
     */
    ONDERZOEK_ACTIEVERVALTBVLEVERINGMUTATIES(18830),
    /**
     * Waarde voor: Onderzoek - IndicatieVoorkomenTbvLeveringMutaties.
     */
    ONDERZOEK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18831),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - AdministratieveHandeling.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ADMINISTRATIEVEHANDELING(10842),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - Onderzoek.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ONDERZOEK(10923),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - TijdstipRegistratie.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_TIJDSTIPREGISTRATIE(10924),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - TijdstipVerval.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_TIJDSTIPVERVAL(10925),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - ActieInhoud.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEINHOUD(10926),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - ActieVerval.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEVERVAL(10927),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - VoorkomenSleutel.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_VOORKOMENSLEUTEL(10973),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - NadereAanduidingVerval.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_NADEREAANDUIDINGVERVAL(11122),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - ActieVervalTbvLeveringMutaties.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_ACTIEVERVALTBVLEVERINGMUTATIES(18832),
    /**
     * Waarde voor: Onderzoek - AfgeleidAdministratief - IndicatieVoorkomenTbvLeveringMutaties.
     */
    ONDERZOEK_AFGELEIDADMINISTRATIEF_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18833),
    /**
     * Waarde voor: Element - Identiteit.
     */
    ELEMENT_IDENTITEIT(2085),
    /**
     * Waarde voor: Element - ObjectSleutel.
     */
    ELEMENT_OBJECTSLEUTEL(3173),
    /**
     * Waarde voor: Element - ElementNaam.
     */
    ELEMENT_ELEMENTNAAM(3175),
    /**
     * Waarde voor: Element - SoortNaam.
     */
    ELEMENT_SOORTNAAM(3721),
    /**
     * Waarde voor: Element - DatumAanvangGeldigheid.
     */
    ELEMENT_DATUMAANVANGGELDIGHEID(3930),
    /**
     * Waarde voor: Element - DatumEindeGeldigheid.
     */
    ELEMENT_DATUMEINDEGELDIGHEID(3931),
    /**
     * Waarde voor: Element - ObjecttypeNaam.
     */
    ELEMENT_OBJECTTYPENAAM(5663),
    /**
     * Waarde voor: Element - GroepNaam.
     */
    ELEMENT_GROEPNAAM(10485),
    /**
     * Waarde voor: Element - Naam.
     */
    ELEMENT_NAAM(13495),
    /**
     * Waarde voor: Element - HisTabelNaam.
     */
    ELEMENT_HISTABELNAAM(13496),
    /**
     * Waarde voor: Element - AliasVanNaam.
     */
    ELEMENT_ALIASVANNAAM(13497),
    /**
     * Waarde voor: Element - IdentificatieDatabase.
     */
    ELEMENT_IDENTIFICATIEDATABASE(13498),
    /**
     * Waarde voor: Element - HisIdentifierDatabase.
     */
    ELEMENT_HISIDENTIFIERDATABASE(13499),
    /**
     * Waarde voor: Element - Expressie.
     */
    ELEMENT_EXPRESSIE(13500),
    /**
     * Waarde voor: Element - AutorisatieNaam.
     */
    ELEMENT_AUTORISATIENAAM(13528),
    /**
     * Waarde voor: Element - DatabaseObject.
     */
    ELEMENT_DATABASEOBJECT(13529),
    /**
     * Waarde voor: Element - HisDatabaseObject.
     */
    ELEMENT_HISDATABASEOBJECT(13530),
    /**
     * Waarde voor: Element - Volgnummer.
     */
    ELEMENT_VOLGNUMMER(14262),
    /**
     * Waarde voor: Element - LeverenAlsStamgegeven.
     */
    ELEMENT_LEVERENALSSTAMGEGEVEN(14263),
    /**
     * Waarde voor: Element - TabelNaam.
     */
    ELEMENT_TABELNAAM(14268),
    /**
     * Waarde voor: Relatie - Identiteit.
     */
    RELATIE_IDENTITEIT(2070),
    /**
     * Waarde voor: Relatie - Standaard.
     */
    RELATIE_STANDAARD(3599),
    /**
     * Waarde voor: Relatie - ObjectSleutel.
     */
    RELATIE_OBJECTSLEUTEL(3186),
    /**
     * Waarde voor: Relatie - SoortCode.
     */
    RELATIE_SOORTCODE(3198),
    /**
     * Waarde voor: Relatie - RedenEindeCode.
     */
    RELATIE_REDENEINDECODE(3207),
    /**
     * Waarde voor: Relatie - DatumAanvang.
     */
    RELATIE_DATUMAANVANG(3754),
    /**
     * Waarde voor: Relatie - GemeenteAanvangCode.
     */
    RELATIE_GEMEENTEAANVANGCODE(3755),
    /**
     * Waarde voor: Relatie - WoonplaatsnaamAanvang.
     */
    RELATIE_WOONPLAATSNAAMAANVANG(3756),
    /**
     * Waarde voor: Relatie - BuitenlandsePlaatsAanvang.
     */
    RELATIE_BUITENLANDSEPLAATSAANVANG(3757),
    /**
     * Waarde voor: Relatie - OmschrijvingLocatieAanvang.
     */
    RELATIE_OMSCHRIJVINGLOCATIEAANVANG(3758),
    /**
     * Waarde voor: Relatie - BuitenlandseRegioAanvang.
     */
    RELATIE_BUITENLANDSEREGIOAANVANG(3759),
    /**
     * Waarde voor: Relatie - LandGebiedAanvangCode.
     */
    RELATIE_LANDGEBIEDAANVANGCODE(3760),
    /**
     * Waarde voor: Relatie - DatumEinde.
     */
    RELATIE_DATUMEINDE(3762),
    /**
     * Waarde voor: Relatie - GemeenteEindeCode.
     */
    RELATIE_GEMEENTEEINDECODE(3763),
    /**
     * Waarde voor: Relatie - WoonplaatsnaamEinde.
     */
    RELATIE_WOONPLAATSNAAMEINDE(3764),
    /**
     * Waarde voor: Relatie - BuitenlandsePlaatsEinde.
     */
    RELATIE_BUITENLANDSEPLAATSEINDE(3765),
    /**
     * Waarde voor: Relatie - OmschrijvingLocatieEinde.
     */
    RELATIE_OMSCHRIJVINGLOCATIEEINDE(3766),
    /**
     * Waarde voor: Relatie - BuitenlandseRegioEinde.
     */
    RELATIE_BUITENLANDSEREGIOEINDE(3767),
    /**
     * Waarde voor: Relatie - LandGebiedEindeCode.
     */
    RELATIE_LANDGEBIEDEINDECODE(3768),
    /**
     * Waarde voor: Relatie - Relatie.
     */
    RELATIE_RELATIE(4369),
    /**
     * Waarde voor: Relatie - TijdstipRegistratie.
     */
    RELATIE_TIJDSTIPREGISTRATIE(4370),
    /**
     * Waarde voor: Relatie - TijdstipVerval.
     */
    RELATIE_TIJDSTIPVERVAL(4371),
    /**
     * Waarde voor: Relatie - ActieInhoud.
     */
    RELATIE_ACTIEINHOUD(4372),
    /**
     * Waarde voor: Relatie - ActieVerval.
     */
    RELATIE_ACTIEVERVAL(4373),
    /**
     * Waarde voor: Relatie - VoorkomenSleutel.
     */
    RELATIE_VOORKOMENSLEUTEL(4596),
    /**
     * Waarde voor: Relatie - NadereAanduidingVerval.
     */
    RELATIE_NADEREAANDUIDINGVERVAL(11153),
    /**
     * Waarde voor: Relatie - ActieVervalTbvLeveringMutaties.
     */
    RELATIE_ACTIEVERVALTBVLEVERINGMUTATIES(18892),
    /**
     * Waarde voor: Relatie - IndicatieVoorkomenTbvLeveringMutaties.
     */
    RELATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18893),
    /**
     * Waarde voor: SoortRelatie - Identiteit.
     */
    SOORTRELATIE_IDENTITEIT(2107),
    /**
     * Waarde voor: SoortRelatie - ObjectSleutel.
     */
    SOORTRELATIE_OBJECTSLEUTEL(3193),
    /**
     * Waarde voor: SoortRelatie - Naam.
     */
    SOORTRELATIE_NAAM(3195),
    /**
     * Waarde voor: SoortRelatie - Code.
     */
    SOORTRELATIE_CODE(3492),
    /**
     * Waarde voor: SoortRelatie - Omschrijving.
     */
    SOORTRELATIE_OMSCHRIJVING(5620),
    /**
     * Waarde voor: RedenEindeRelatie - Identiteit.
     */
    REDENEINDERELATIE_IDENTITEIT(2093),
    /**
     * Waarde voor: RedenEindeRelatie - ObjectSleutel.
     */
    REDENEINDERELATIE_OBJECTSLEUTEL(3202),
    /**
     * Waarde voor: RedenEindeRelatie - Omschrijving.
     */
    REDENEINDERELATIE_OMSCHRIJVING(3204),
    /**
     * Waarde voor: RedenEindeRelatie - Code.
     */
    REDENEINDERELATIE_CODE(6078),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - Identiteit.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_IDENTITEIT(2096),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - ObjectSleutel.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_OBJECTSLEUTEL(3217),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - Omschrijving.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_OMSCHRIJVING(3219),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - DatumAanvangGeldigheid.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_DATUMAANVANGGELDIGHEID(3991),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - DatumEindeGeldigheid.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_DATUMEINDEGELDIGHEID(3992),
    /**
     * Waarde voor: RedenVerkrijgingNLNationaliteit - Code.
     */
    REDENVERKRIJGINGNLNATIONALITEIT_CODE(6262),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - Identiteit.
     */
    REDENVERLIESNLNATIONALITEIT_IDENTITEIT(2097),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - ObjectSleutel.
     */
    REDENVERLIESNLNATIONALITEIT_OBJECTSLEUTEL(3223),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - Code.
     */
    REDENVERLIESNLNATIONALITEIT_CODE(3226),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - DatumAanvangGeldigheid.
     */
    REDENVERLIESNLNATIONALITEIT_DATUMAANVANGGELDIGHEID(3994),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - DatumEindeGeldigheid.
     */
    REDENVERLIESNLNATIONALITEIT_DATUMEINDEGELDIGHEID(3995),
    /**
     * Waarde voor: RedenVerliesNLNationaliteit - Omschrijving.
     */
    REDENVERLIESNLNATIONALITEIT_OMSCHRIJVING(6267),
    /**
     * Waarde voor: Persoon - Adres - Identiteit.
     */
    PERSOON_ADRES_IDENTITEIT(2068),
    /**
     * Waarde voor: Persoon - Adres - Standaard.
     */
    PERSOON_ADRES_STANDAARD(6063),
    /**
     * Waarde voor: Persoon - Adres - ObjectSleutel.
     */
    PERSOON_ADRES_OBJECTSLEUTEL(3239),
    /**
     * Waarde voor: Persoon - Adres - Persoon.
     */
    PERSOON_ADRES_PERSOON(3241),
    /**
     * Waarde voor: Persoon - Adres - SoortCode.
     */
    PERSOON_ADRES_SOORTCODE(3263),
    /**
     * Waarde voor: Persoon - Adres - Gemeentedeel.
     */
    PERSOON_ADRES_GEMEENTEDEEL(3265),
    /**
     * Waarde voor: Persoon - Adres - AfgekorteNaamOpenbareRuimte.
     */
    PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE(3267),
    /**
     * Waarde voor: Persoon - Adres - NaamOpenbareRuimte.
     */
    PERSOON_ADRES_NAAMOPENBARERUIMTE(3269),
    /**
     * Waarde voor: Persoon - Adres - Huisnummer.
     */
    PERSOON_ADRES_HUISNUMMER(3271),
    /**
     * Waarde voor: Persoon - Adres - Huisletter.
     */
    PERSOON_ADRES_HUISLETTER(3273),
    /**
     * Waarde voor: Persoon - Adres - Huisnummertoevoeging.
     */
    PERSOON_ADRES_HUISNUMMERTOEVOEGING(3275),
    /**
     * Waarde voor: Persoon - Adres - LocatieTenOpzichteVanAdres.
     */
    PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES(3278),
    /**
     * Waarde voor: Persoon - Adres - Postcode.
     */
    PERSOON_ADRES_POSTCODE(3281),
    /**
     * Waarde voor: Persoon - Adres - Woonplaatsnaam.
     */
    PERSOON_ADRES_WOONPLAATSNAAM(3282),
    /**
     * Waarde voor: Persoon - Adres - IdentificatiecodeAdresseerbaarObject.
     */
    PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT(3284),
    /**
     * Waarde voor: Persoon - Adres - IdentificatiecodeNummeraanduiding.
     */
    PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING(3286),
    /**
     * Waarde voor: Persoon - Adres - Locatieomschrijving.
     */
    PERSOON_ADRES_LOCATIEOMSCHRIJVING(3288),
    /**
     * Waarde voor: Persoon - Adres - LandGebiedCode.
     */
    PERSOON_ADRES_LANDGEBIEDCODE(3289),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel1.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL1(3291),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel2.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL2(3292),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel3.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL3(3293),
    /**
     * Waarde voor: Persoon - Adres - AangeverAdreshoudingCode.
     */
    PERSOON_ADRES_AANGEVERADRESHOUDINGCODE(3301),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel4.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL4(3709),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel5.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL5(3710),
    /**
     * Waarde voor: Persoon - Adres - BuitenlandsAdresRegel6.
     */
    PERSOON_ADRES_BUITENLANDSADRESREGEL6(3711),
    /**
     * Waarde voor: Persoon - Adres - RedenWijzigingCode.
     */
    PERSOON_ADRES_REDENWIJZIGINGCODE(3715),
    /**
     * Waarde voor: Persoon - Adres - DatumAanvangAdreshouding.
     */
    PERSOON_ADRES_DATUMAANVANGADRESHOUDING(3730),
    /**
     * Waarde voor: Persoon - Adres - GemeenteCode.
     */
    PERSOON_ADRES_GEMEENTECODE(3788),
    /**
     * Waarde voor: Persoon - Adres - PersoonAdres.
     */
    PERSOON_ADRES_PERSOONADRES(6065),
    /**
     * Waarde voor: Persoon - Adres - DatumAanvangGeldigheid.
     */
    PERSOON_ADRES_DATUMAANVANGGELDIGHEID(6066),
    /**
     * Waarde voor: Persoon - Adres - DatumEindeGeldigheid.
     */
    PERSOON_ADRES_DATUMEINDEGELDIGHEID(6067),
    /**
     * Waarde voor: Persoon - Adres - TijdstipRegistratie.
     */
    PERSOON_ADRES_TIJDSTIPREGISTRATIE(6068),
    /**
     * Waarde voor: Persoon - Adres - TijdstipVerval.
     */
    PERSOON_ADRES_TIJDSTIPVERVAL(6069),
    /**
     * Waarde voor: Persoon - Adres - ActieInhoud.
     */
    PERSOON_ADRES_ACTIEINHOUD(6070),
    /**
     * Waarde voor: Persoon - Adres - ActieVerval.
     */
    PERSOON_ADRES_ACTIEVERVAL(6071),
    /**
     * Waarde voor: Persoon - Adres - ActieAanpassingGeldigheid.
     */
    PERSOON_ADRES_ACTIEAANPASSINGGELDIGHEID(6072),
    /**
     * Waarde voor: Persoon - Adres - VoorkomenSleutel.
     */
    PERSOON_ADRES_VOORKOMENSLEUTEL(6075),
    /**
     * Waarde voor: Persoon - Adres - IndicatiePersoonAangetroffenOpAdres.
     */
    PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES(9540),
    /**
     * Waarde voor: Persoon - Adres - NadereAanduidingVerval.
     */
    PERSOON_ADRES_NADEREAANDUIDINGVERVAL(11141),
    /**
     * Waarde voor: Persoon - Adres - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_ADRES_ACTIEVERVALTBVLEVERINGMUTATIES(18872),
    /**
     * Waarde voor: Persoon - Adres - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_ADRES_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18873),
    /**
     * Waarde voor: FunctieAdres - Identiteit.
     */
    FUNCTIEADRES_IDENTITEIT(2086),
    /**
     * Waarde voor: FunctieAdres - ObjectSleutel.
     */
    FUNCTIEADRES_OBJECTSLEUTEL(3258),
    /**
     * Waarde voor: FunctieAdres - Naam.
     */
    FUNCTIEADRES_NAAM(3260),
    /**
     * Waarde voor: FunctieAdres - Code.
     */
    FUNCTIEADRES_CODE(3486),
    /**
     * Waarde voor: Aangever - Identiteit.
     */
    AANGEVER_IDENTITEIT(2081),
    /**
     * Waarde voor: Aangever - ObjectSleutel.
     */
    AANGEVER_OBJECTSLEUTEL(3296),
    /**
     * Waarde voor: Aangever - Naam.
     */
    AANGEVER_NAAM(3298),
    /**
     * Waarde voor: Aangever - Code.
     */
    AANGEVER_CODE(3480),
    /**
     * Waarde voor: Aangever - Omschrijving.
     */
    AANGEVER_OMSCHRIJVING(5619),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - Identiteit.
     */
    AANDUIDINGVERBLIJFSRECHT_IDENTITEIT(2110),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - ObjectSleutel.
     */
    AANDUIDINGVERBLIJFSRECHT_OBJECTSLEUTEL(3304),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - Omschrijving.
     */
    AANDUIDINGVERBLIJFSRECHT_OMSCHRIJVING(3306),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - DatumAanvangGeldigheid.
     */
    AANDUIDINGVERBLIJFSRECHT_DATUMAANVANGGELDIGHEID(4018),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - DatumEindeGeldigheid.
     */
    AANDUIDINGVERBLIJFSRECHT_DATUMEINDEGELDIGHEID(4019),
    /**
     * Waarde voor: AanduidingVerblijfsrecht - Code.
     */
    AANDUIDINGVERBLIJFSRECHT_CODE(9191),
    /**
     * Waarde voor: Bijhoudingsaard - Identiteit.
     */
    BIJHOUDINGSAARD_IDENTITEIT(2109),
    /**
     * Waarde voor: Bijhoudingsaard - ObjectSleutel.
     */
    BIJHOUDINGSAARD_OBJECTSLEUTEL(3535),
    /**
     * Waarde voor: Bijhoudingsaard - Naam.
     */
    BIJHOUDINGSAARD_NAAM(3536),
    /**
     * Waarde voor: Bijhoudingsaard - Omschrijving.
     */
    BIJHOUDINGSAARD_OMSCHRIJVING(10918),
    /**
     * Waarde voor: Bijhoudingsaard - Code.
     */
    BIJHOUDINGSAARD_CODE(11178),
    /**
     * Waarde voor: Persoon - Reisdocument - Identiteit.
     */
    PERSOON_REISDOCUMENT_IDENTITEIT(2069),
    /**
     * Waarde voor: Persoon - Reisdocument - Standaard.
     */
    PERSOON_REISDOCUMENT_STANDAARD(3577),
    /**
     * Waarde voor: Persoon - Reisdocument - SoortCode.
     */
    PERSOON_REISDOCUMENT_SOORTCODE(3739),
    /**
     * Waarde voor: Persoon - Reisdocument - ObjectSleutel.
     */
    PERSOON_REISDOCUMENT_OBJECTSLEUTEL(3751),
    /**
     * Waarde voor: Persoon - Reisdocument - Persoon.
     */
    PERSOON_REISDOCUMENT_PERSOON(3752),
    /**
     * Waarde voor: Persoon - Reisdocument - Nummer.
     */
    PERSOON_REISDOCUMENT_NUMMER(3741),
    /**
     * Waarde voor: Persoon - Reisdocument - DatumUitgifte.
     */
    PERSOON_REISDOCUMENT_DATUMUITGIFTE(3742),
    /**
     * Waarde voor: Persoon - Reisdocument - AutoriteitVanAfgifte.
     */
    PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE(3744),
    /**
     * Waarde voor: Persoon - Reisdocument - DatumEindeDocument.
     */
    PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT(3745),
    /**
     * Waarde voor: Persoon - Reisdocument - DatumInhoudingVermissing.
     */
    PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING(3746),
    /**
     * Waarde voor: Persoon - Reisdocument - AanduidingInhoudingVermissingCode.
     */
    PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE(3747),
    /**
     * Waarde voor: Persoon - Reisdocument - PersoonReisdocument.
     */
    PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT(4336),
    /**
     * Waarde voor: Persoon - Reisdocument - TijdstipRegistratie.
     */
    PERSOON_REISDOCUMENT_TIJDSTIPREGISTRATIE(4339),
    /**
     * Waarde voor: Persoon - Reisdocument - TijdstipVerval.
     */
    PERSOON_REISDOCUMENT_TIJDSTIPVERVAL(4340),
    /**
     * Waarde voor: Persoon - Reisdocument - ActieInhoud.
     */
    PERSOON_REISDOCUMENT_ACTIEINHOUD(4341),
    /**
     * Waarde voor: Persoon - Reisdocument - ActieVerval.
     */
    PERSOON_REISDOCUMENT_ACTIEVERVAL(4342),
    /**
     * Waarde voor: Persoon - Reisdocument - VoorkomenSleutel.
     */
    PERSOON_REISDOCUMENT_VOORKOMENSLEUTEL(4587),
    /**
     * Waarde voor: Persoon - Reisdocument - DatumIngangDocument.
     */
    PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT(6126),
    /**
     * Waarde voor: Persoon - Reisdocument - NadereAanduidingVerval.
     */
    PERSOON_REISDOCUMENT_NADEREAANDUIDINGVERVAL(11147),
    /**
     * Waarde voor: Persoon - Reisdocument - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_REISDOCUMENT_ACTIEVERVALTBVLEVERINGMUTATIES(18882),
    /**
     * Waarde voor: Persoon - Reisdocument - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_REISDOCUMENT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18883),
    /**
     * Waarde voor: SoortIndicatie - Identiteit.
     */
    SOORTINDICATIE_IDENTITEIT(2103),
    /**
     * Waarde voor: SoortIndicatie - IndicatieMaterieleHistorieVanToepassing.
     */
    SOORTINDICATIE_INDICATIEMATERIELEHISTORIEVANTOEPASSING(2014),
    /**
     * Waarde voor: SoortIndicatie - ObjectSleutel.
     */
    SOORTINDICATIE_OBJECTSLEUTEL(3588),
    /**
     * Waarde voor: SoortIndicatie - Naam.
     */
    SOORTINDICATIE_NAAM(3590),
    /**
     * Waarde voor: Naamgebruik - Identiteit.
     */
    NAAMGEBRUIK_IDENTITEIT(2111),
    /**
     * Waarde voor: Naamgebruik - Omschrijving.
     */
    NAAMGEBRUIK_OMSCHRIJVING(2001),
    /**
     * Waarde voor: Naamgebruik - ObjectSleutel.
     */
    NAAMGEBRUIK_OBJECTSLEUTEL(3619),
    /**
     * Waarde voor: Naamgebruik - Code.
     */
    NAAMGEBRUIK_CODE(3621),
    /**
     * Waarde voor: Naamgebruik - Naam.
     */
    NAAMGEBRUIK_NAAM(3623),
    /**
     * Waarde voor: Persoon - Indicatie - Identiteit.
     */
    PERSOON_INDICATIE_IDENTITEIT(2022),
    /**
     * Waarde voor: Persoon - Indicatie - Standaard.
     */
    PERSOON_INDICATIE_STANDAARD(3654),
    /**
     * Waarde voor: Persoon - Indicatie - ObjectSleutel.
     */
    PERSOON_INDICATIE_OBJECTSLEUTEL(3656),
    /**
     * Waarde voor: Persoon - Indicatie - Persoon.
     */
    PERSOON_INDICATIE_PERSOON(3657),
    /**
     * Waarde voor: Persoon - Indicatie - SoortNaam.
     */
    PERSOON_INDICATIE_SOORTNAAM(3658),
    /**
     * Waarde voor: Persoon - Indicatie - Waarde.
     */
    PERSOON_INDICATIE_WAARDE(3659),
    /**
     * Waarde voor: Persoon - Indicatie - PersoonIndicatie.
     */
    PERSOON_INDICATIE_PERSOONINDICATIE(4315),
    /**
     * Waarde voor: Persoon - Indicatie - DatumAanvangGeldigheid.
     */
    PERSOON_INDICATIE_DATUMAANVANGGELDIGHEID(4316),
    /**
     * Waarde voor: Persoon - Indicatie - DatumEindeGeldigheid.
     */
    PERSOON_INDICATIE_DATUMEINDEGELDIGHEID(4317),
    /**
     * Waarde voor: Persoon - Indicatie - TijdstipRegistratie.
     */
    PERSOON_INDICATIE_TIJDSTIPREGISTRATIE(4318),
    /**
     * Waarde voor: Persoon - Indicatie - TijdstipVerval.
     */
    PERSOON_INDICATIE_TIJDSTIPVERVAL(4319),
    /**
     * Waarde voor: Persoon - Indicatie - ActieInhoud.
     */
    PERSOON_INDICATIE_ACTIEINHOUD(4320),
    /**
     * Waarde voor: Persoon - Indicatie - ActieVerval.
     */
    PERSOON_INDICATIE_ACTIEVERVAL(4321),
    /**
     * Waarde voor: Persoon - Indicatie - ActieAanpassingGeldigheid.
     */
    PERSOON_INDICATIE_ACTIEAANPASSINGGELDIGHEID(4322),
    /**
     * Waarde voor: Persoon - Indicatie - VoorkomenSleutel.
     */
    PERSOON_INDICATIE_VOORKOMENSLEUTEL(4581),
    /**
     * Waarde voor: Persoon - Indicatie - NadereAanduidingVerval.
     */
    PERSOON_INDICATIE_NADEREAANDUIDINGVERVAL(11144),
    /**
     * Waarde voor: Persoon - Indicatie - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_INDICATIE_ACTIEVERVALTBVLEVERINGMUTATIES(18876),
    /**
     * Waarde voor: Persoon - Indicatie - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_INDICATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18877),
    /**
     * Waarde voor: Persoon - Indicatie - MigratieRedenOpnameNationaliteit.
     */
    PERSOON_INDICATIE_MIGRATIEREDENOPNAMENATIONALITEIT(21234),
    /**
     * Waarde voor: Persoon - Indicatie - MigratieRedenBeeindigenNationaliteit.
     */
    PERSOON_INDICATIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT(21235),
    /**
     * Waarde voor: SoortElement - Identiteit.
     */
    SOORTELEMENT_IDENTITEIT(2101),
    /**
     * Waarde voor: SoortElement - ObjectSleutel.
     */
    SOORTELEMENT_OBJECTSLEUTEL(3717),
    /**
     * Waarde voor: SoortElement - Naam.
     */
    SOORTELEMENT_NAAM(3718),
    /**
     * Waarde voor: Persoon - Verificatie - Identiteit.
     */
    PERSOON_VERIFICATIE_IDENTITEIT(2076),
    /**
     * Waarde voor: Persoon - Verificatie - Standaard.
     */
    PERSOON_VERIFICATIE_STANDAARD(3783),
    /**
     * Waarde voor: Persoon - Verificatie - Geverifieerde.
     */
    PERSOON_VERIFICATIE_GEVERIFIEERDE(2142),
    /**
     * Waarde voor: Persoon - Verificatie - ObjectSleutel.
     */
    PERSOON_VERIFICATIE_OBJECTSLEUTEL(3777),
    /**
     * Waarde voor: Persoon - Verificatie - Soort.
     */
    PERSOON_VERIFICATIE_SOORT(3779),
    /**
     * Waarde voor: Persoon - Verificatie - PartijCode.
     */
    PERSOON_VERIFICATIE_PARTIJCODE(10915),
    /**
     * Waarde voor: Persoon - Verificatie - Datum.
     */
    PERSOON_VERIFICATIE_DATUM(3778),
    /**
     * Waarde voor: Persoon - Verificatie - PersoonVerificatie.
     */
    PERSOON_VERIFICATIE_PERSOONVERIFICATIE(4352),
    /**
     * Waarde voor: Persoon - Verificatie - TijdstipRegistratie.
     */
    PERSOON_VERIFICATIE_TIJDSTIPREGISTRATIE(4353),
    /**
     * Waarde voor: Persoon - Verificatie - TijdstipVerval.
     */
    PERSOON_VERIFICATIE_TIJDSTIPVERVAL(4354),
    /**
     * Waarde voor: Persoon - Verificatie - ActieInhoud.
     */
    PERSOON_VERIFICATIE_ACTIEINHOUD(4355),
    /**
     * Waarde voor: Persoon - Verificatie - ActieVerval.
     */
    PERSOON_VERIFICATIE_ACTIEVERVAL(4356),
    /**
     * Waarde voor: Persoon - Verificatie - VoorkomenSleutel.
     */
    PERSOON_VERIFICATIE_VOORKOMENSLEUTEL(4590),
    /**
     * Waarde voor: Persoon - Verificatie - NadereAanduidingVerval.
     */
    PERSOON_VERIFICATIE_NADEREAANDUIDINGVERVAL(11148),
    /**
     * Waarde voor: Persoon - Verificatie - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_VERIFICATIE_ACTIEVERVALTBVLEVERINGMUTATIES(18884),
    /**
     * Waarde voor: Persoon - Verificatie - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_VERIFICATIE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18885),
    /**
     * Waarde voor: RedenWijzigingVerblijf - Identiteit.
     */
    REDENWIJZIGINGVERBLIJF_IDENTITEIT(2098),
    /**
     * Waarde voor: RedenWijzigingVerblijf - ObjectSleutel.
     */
    REDENWIJZIGINGVERBLIJF_OBJECTSLEUTEL(3683),
    /**
     * Waarde voor: RedenWijzigingVerblijf - Code.
     */
    REDENWIJZIGINGVERBLIJF_CODE(3685),
    /**
     * Waarde voor: RedenWijzigingVerblijf - Naam.
     */
    REDENWIJZIGINGVERBLIJF_NAAM(3687),
    /**
     * Waarde voor: SoortNederlandsReisdocument - Identiteit.
     */
    SOORTNEDERLANDSREISDOCUMENT_IDENTITEIT(2105),
    /**
     * Waarde voor: SoortNederlandsReisdocument - ObjectSleutel.
     */
    SOORTNEDERLANDSREISDOCUMENT_OBJECTSLEUTEL(3794),
    /**
     * Waarde voor: SoortNederlandsReisdocument - Code.
     */
    SOORTNEDERLANDSREISDOCUMENT_CODE(3796),
    /**
     * Waarde voor: SoortNederlandsReisdocument - Omschrijving.
     */
    SOORTNEDERLANDSREISDOCUMENT_OMSCHRIJVING(3798),
    /**
     * Waarde voor: SoortNederlandsReisdocument - DatumAanvangGeldigheid.
     */
    SOORTNEDERLANDSREISDOCUMENT_DATUMAANVANGGELDIGHEID(6083),
    /**
     * Waarde voor: SoortNederlandsReisdocument - DatumEindeGeldigheid.
     */
    SOORTNEDERLANDSREISDOCUMENT_DATUMEINDEGELDIGHEID(6084),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - Identiteit.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_IDENTITEIT(2083),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - ObjectSleutel.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_OBJECTSLEUTEL(3805),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - Code.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_CODE(3808),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - Naam.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_NAAM(3809),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - DatumAanvangGeldigheid.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_DATUMAANVANGGELDIGHEID(3920),
    /**
     * Waarde voor: AutoriteittypeVanAfgifteReisdocument - DatumEindeGeldigheid.
     */
    AUTORITEITTYPEVANAFGIFTEREISDOCUMENT_DATUMEINDEGELDIGHEID(3921),
    /**
     * Waarde voor: AanduidingInhoudingVermissingReisdocument - Identiteit.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT_IDENTITEIT(2094),
    /**
     * Waarde voor: AanduidingInhoudingVermissingReisdocument - ObjectSleutel.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT_OBJECTSLEUTEL(3818),
    /**
     * Waarde voor: AanduidingInhoudingVermissingReisdocument - Code.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT_CODE(3819),
    /**
     * Waarde voor: AanduidingInhoudingVermissingReisdocument - Naam.
     */
    AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT_NAAM(3820),
    /**
     * Waarde voor: SoortBetrokkenheid - Identiteit.
     */
    SOORTBETROKKENHEID_IDENTITEIT(2099),
    /**
     * Waarde voor: SoortBetrokkenheid - ObjectSleutel.
     */
    SOORTBETROKKENHEID_OBJECTSLEUTEL(3851),
    /**
     * Waarde voor: SoortBetrokkenheid - Code.
     */
    SOORTBETROKKENHEID_CODE(3852),
    /**
     * Waarde voor: SoortBetrokkenheid - Naam.
     */
    SOORTBETROKKENHEID_NAAM(3853),
    /**
     * Waarde voor: Betrokkenheid - Identiteit.
     */
    BETROKKENHEID_IDENTITEIT(2071),
    /**
     * Waarde voor: Betrokkenheid - Persoon.
     */
    BETROKKENHEID_PERSOON(3859),
    /**
     * Waarde voor: Betrokkenheid - Relatie.
     */
    BETROKKENHEID_RELATIE(3860),
    /**
     * Waarde voor: Betrokkenheid - RolCode.
     */
    BETROKKENHEID_ROLCODE(3861),
    /**
     * Waarde voor: Betrokkenheid - ObjectSleutel.
     */
    BETROKKENHEID_OBJECTSLEUTEL(6102),
    /**
     * Waarde voor: Betrokkenheid - Betrokkenheid.
     */
    BETROKKENHEID_BETROKKENHEID(14269),
    /**
     * Waarde voor: Betrokkenheid - TijdstipRegistratie.
     */
    BETROKKENHEID_TIJDSTIPREGISTRATIE(14270),
    /**
     * Waarde voor: Betrokkenheid - TijdstipVerval.
     */
    BETROKKENHEID_TIJDSTIPVERVAL(14271),
    /**
     * Waarde voor: Betrokkenheid - NadereAanduidingVerval.
     */
    BETROKKENHEID_NADEREAANDUIDINGVERVAL(14272),
    /**
     * Waarde voor: Betrokkenheid - ActieInhoud.
     */
    BETROKKENHEID_ACTIEINHOUD(14273),
    /**
     * Waarde voor: Betrokkenheid - ActieVerval.
     */
    BETROKKENHEID_ACTIEVERVAL(14274),
    /**
     * Waarde voor: Betrokkenheid - VoorkomenSleutel.
     */
    BETROKKENHEID_VOORKOMENSLEUTEL(14277),
    /**
     * Waarde voor: Betrokkenheid - ActieVervalTbvLeveringMutaties.
     */
    BETROKKENHEID_ACTIEVERVALTBVLEVERINGMUTATIES(18814),
    /**
     * Waarde voor: Betrokkenheid - IndicatieVoorkomenTbvLeveringMutaties.
     */
    BETROKKENHEID_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18815),
    /**
     * Waarde voor: GegevenInOnderzoek - Identiteit.
     */
    GEGEVENINONDERZOEK_IDENTITEIT(2075),
    /**
     * Waarde voor: GegevenInOnderzoek - ObjectSleutelGegeven.
     */
    GEGEVENINONDERZOEK_OBJECTSLEUTELGEGEVEN(3649),
    /**
     * Waarde voor: GegevenInOnderzoek - Onderzoek.
     */
    GEGEVENINONDERZOEK_ONDERZOEK(3865),
    /**
     * Waarde voor: GegevenInOnderzoek - ElementNaam.
     */
    GEGEVENINONDERZOEK_ELEMENTNAAM(3866),
    /**
     * Waarde voor: GegevenInOnderzoek - ID.
     */
    GEGEVENINONDERZOEK_ID(10844),
    /**
     * Waarde voor: GegevenInOnderzoek - VoorkomenSleutelGegeven.
     */
    GEGEVENINONDERZOEK_VOORKOMENSLEUTELGEGEVEN(14188),
    /**
     * Waarde voor: Regel - Identiteit.
     */
    REGEL_IDENTITEIT(5801),
    /**
     * Waarde voor: Regel - ObjectSleutel.
     */
    REGEL_OBJECTSLEUTEL(5803),
    /**
     * Waarde voor: Regel - Code.
     */
    REGEL_CODE(5807),
    /**
     * Waarde voor: Regel - Omschrijving.
     */
    REGEL_OMSCHRIJVING(5808),
    /**
     * Waarde voor: Regel - Specificatie.
     */
    REGEL_SPECIFICATIE(5813),
    /**
     * Waarde voor: Regel - SoortNaam.
     */
    REGEL_SOORTNAAM(5990),
    /**
     * Waarde voor: Persoon - Onderzoek - Identiteit.
     */
    PERSOON_ONDERZOEK_IDENTITEIT(6128),
    /**
     * Waarde voor: Persoon - Onderzoek - Standaard.
     */
    PERSOON_ONDERZOEK_STANDAARD(10763),
    /**
     * Waarde voor: Persoon - Onderzoek - ObjectSleutel.
     */
    PERSOON_ONDERZOEK_OBJECTSLEUTEL(6130),
    /**
     * Waarde voor: Persoon - Onderzoek - Persoon.
     */
    PERSOON_ONDERZOEK_PERSOON(6131),
    /**
     * Waarde voor: Persoon - Onderzoek - Onderzoek.
     */
    PERSOON_ONDERZOEK_ONDERZOEK(6132),
    /**
     * Waarde voor: Persoon - Onderzoek - RolNaam.
     */
    PERSOON_ONDERZOEK_ROLNAAM(10771),
    /**
     * Waarde voor: Persoon - Onderzoek - PersoonOnderzoek.
     */
    PERSOON_ONDERZOEK_PERSOONONDERZOEK(10804),
    /**
     * Waarde voor: Persoon - Onderzoek - TijdstipRegistratie.
     */
    PERSOON_ONDERZOEK_TIJDSTIPREGISTRATIE(10805),
    /**
     * Waarde voor: Persoon - Onderzoek - TijdstipVerval.
     */
    PERSOON_ONDERZOEK_TIJDSTIPVERVAL(10806),
    /**
     * Waarde voor: Persoon - Onderzoek - ActieInhoud.
     */
    PERSOON_ONDERZOEK_ACTIEINHOUD(10807),
    /**
     * Waarde voor: Persoon - Onderzoek - ActieVerval.
     */
    PERSOON_ONDERZOEK_ACTIEVERVAL(10808),
    /**
     * Waarde voor: Persoon - Onderzoek - VoorkomenSleutel.
     */
    PERSOON_ONDERZOEK_VOORKOMENSLEUTEL(10833),
    /**
     * Waarde voor: Persoon - Onderzoek - NadereAanduidingVerval.
     */
    PERSOON_ONDERZOEK_NADEREAANDUIDINGVERVAL(11146),
    /**
     * Waarde voor: Persoon - Onderzoek - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_ONDERZOEK_ACTIEVERVALTBVLEVERINGMUTATIES(18880),
    /**
     * Waarde voor: Persoon - Onderzoek - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_ONDERZOEK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18881),
    /**
     * Waarde voor: Regelverantwoording - Identiteit.
     */
    REGELVERANTWOORDING_IDENTITEIT(6153),
    /**
     * Waarde voor: Regelverantwoording - ObjectSleutel.
     */
    REGELVERANTWOORDING_OBJECTSLEUTEL(6148),
    /**
     * Waarde voor: Regelverantwoording - Actie.
     */
    REGELVERANTWOORDING_ACTIE(6149),
    /**
     * Waarde voor: Regelverantwoording - RegelCode.
     */
    REGELVERANTWOORDING_REGELCODE(6152),
    /**
     * Waarde voor: GedeblokkeerdeMelding - Identiteit.
     */
    GEDEBLOKKEERDEMELDING_IDENTITEIT(6217),
    /**
     * Waarde voor: GedeblokkeerdeMelding - ObjectSleutel.
     */
    GEDEBLOKKEERDEMELDING_OBJECTSLEUTEL(6219),
    /**
     * Waarde voor: GedeblokkeerdeMelding - RegelCode.
     */
    GEDEBLOKKEERDEMELDING_REGELCODE(6220),
    /**
     * Waarde voor: GedeblokkeerdeMelding - Melding.
     */
    GEDEBLOKKEERDEMELDING_MELDING(6254),
    /**
     * Waarde voor: AdministratieveHandelingGedeblokkeerdeMelding - Identiteit.
     */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_IDENTITEIT(6223),
    /**
     * Waarde voor: AdministratieveHandelingGedeblokkeerdeMelding - ObjectSleutel.
     */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_OBJECTSLEUTEL(6225),
    /**
     * Waarde voor: AdministratieveHandelingGedeblokkeerdeMelding - AdministratieveHandeling.
     */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_ADMINISTRATIEVEHANDELING(6226),
    /**
     * Waarde voor: AdministratieveHandelingGedeblokkeerdeMelding - GedeblokkeerdeMelding.
     */
    ADMINISTRATIEVEHANDELINGGEDEBLOKKEERDEMELDING_GEDEBLOKKEERDEMELDING(6227),
    /**
     * Waarde voor: ActieBron - Identiteit.
     */
    ACTIEBRON_IDENTITEIT(8119),
    /**
     * Waarde voor: ActieBron - ObjectSleutel.
     */
    ACTIEBRON_OBJECTSLEUTEL(8121),
    /**
     * Waarde voor: ActieBron - Actie.
     */
    ACTIEBRON_ACTIE(8122),
    /**
     * Waarde voor: ActieBron - Document.
     */
    ACTIEBRON_DOCUMENT(8123),
    /**
     * Waarde voor: ActieBron - RechtsgrondCode.
     */
    ACTIEBRON_RECHTSGRONDCODE(8124),
    /**
     * Waarde voor: ActieBron - Rechtsgrondomschrijving.
     */
    ACTIEBRON_RECHTSGRONDOMSCHRIJVING(10914),
    /**
     * Waarde voor: Rechtsgrond - Identiteit.
     */
    RECHTSGROND_IDENTITEIT(8126),
    /**
     * Waarde voor: Rechtsgrond - ObjectSleutel.
     */
    RECHTSGROND_OBJECTSLEUTEL(8128),
    /**
     * Waarde voor: Rechtsgrond - Omschrijving.
     */
    RECHTSGROND_OMSCHRIJVING(8129),
    /**
     * Waarde voor: Rechtsgrond - Code.
     */
    RECHTSGROND_CODE(8131),
    /**
     * Waarde voor: Rechtsgrond - SoortNaam.
     */
    RECHTSGROND_SOORTNAAM(8141),
    /**
     * Waarde voor: Rechtsgrond - DatumAanvangGeldigheid.
     */
    RECHTSGROND_DATUMAANVANGGELDIGHEID(9173),
    /**
     * Waarde voor: Rechtsgrond - DatumEindeGeldigheid.
     */
    RECHTSGROND_DATUMEINDEGELDIGHEID(9174),
    /**
     * Waarde voor: Rechtsgrond - IndicatieLeidtTotStrijdigheid.
     */
    RECHTSGROND_INDICATIELEIDTTOTSTRIJDIGHEID(11105),
    /**
     * Waarde voor: SoortRechtsgrond - Identiteit.
     */
    SOORTRECHTSGROND_IDENTITEIT(8133),
    /**
     * Waarde voor: SoortRechtsgrond - ObjectSleutel.
     */
    SOORTRECHTSGROND_OBJECTSLEUTEL(8135),
    /**
     * Waarde voor: SoortRechtsgrond - Naam.
     */
    SOORTRECHTSGROND_NAAM(8136),
    /**
     * Waarde voor: AdministratieveHandeling - Identiteit.
     */
    ADMINISTRATIEVEHANDELING_IDENTITEIT(9019),
    /**
     * Waarde voor: AdministratieveHandeling - Levering.
     */
    ADMINISTRATIEVEHANDELING_LEVERING(10051),
    /**
     * Waarde voor: AdministratieveHandeling - Standaard.
     */
    ADMINISTRATIEVEHANDELING_STANDAARD(21582),
    /**
     * Waarde voor: AdministratieveHandeling - ToelichtingOntlening.
     */
    ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING(6174),
    /**
     * Waarde voor: AdministratieveHandeling - ObjectSleutel.
     */
    ADMINISTRATIEVEHANDELING_OBJECTSLEUTEL(9021),
    /**
     * Waarde voor: AdministratieveHandeling - PartijCode.
     */
    ADMINISTRATIEVEHANDELING_PARTIJCODE(9172),
    /**
     * Waarde voor: AdministratieveHandeling - SoortNaam.
     */
    ADMINISTRATIEVEHANDELING_SOORTNAAM(9208),
    /**
     * Waarde voor: AdministratieveHandeling - TijdstipRegistratie.
     */
    ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE(9505),
    /**
     * Waarde voor: AdministratieveHandeling - Levering - Tijdstip.
     */
    ADMINISTRATIEVEHANDELING_LEVERING_TIJDSTIP(10052),
    /**
     * Waarde voor: AdministratieveHandeling - Bijhoudingsplan.
     */
    ADMINISTRATIEVEHANDELING_BIJHOUDINGSPLAN(21549),
    /**
     * Waarde voor: SoortAdministratieveHandeling - Identiteit.
     */
    SOORTADMINISTRATIEVEHANDELING_IDENTITEIT(9197),
    /**
     * Waarde voor: SoortAdministratieveHandeling - ObjectSleutel.
     */
    SOORTADMINISTRATIEVEHANDELING_OBJECTSLEUTEL(9199),
    /**
     * Waarde voor: SoortAdministratieveHandeling - Naam.
     */
    SOORTADMINISTRATIEVEHANDELING_NAAM(9200),
    /**
     * Waarde voor: SoortAdministratieveHandeling - Code.
     */
    SOORTADMINISTRATIEVEHANDELING_CODE(9202),
    /**
     * Waarde voor: SoortAdministratieveHandeling - ModuleNaam.
     */
    SOORTADMINISTRATIEVEHANDELING_MODULENAAM(9532),
    /**
     * Waarde voor: SoortAdministratieveHandeling - Categorie.
     */
    SOORTADMINISTRATIEVEHANDELING_CATEGORIE(9874),
    /**
     * Waarde voor: SoortAdministratieveHandeling - Alias.
     */
    SOORTADMINISTRATIEVEHANDELING_ALIAS(11438),
    /**
     * Waarde voor: SoortAdministratieveHandeling - KoppelvlakNaam.
     */
    SOORTADMINISTRATIEVEHANDELING_KOPPELVLAKNAAM(21420),
    /**
     * Waarde voor: Voorvoegsel - Identiteit.
     */
    VOORVOEGSEL_IDENTITEIT(9263),
    /**
     * Waarde voor: Voorvoegsel - ObjectSleutel.
     */
    VOORVOEGSEL_OBJECTSLEUTEL(9265),
    /**
     * Waarde voor: Voorvoegsel - Voorvoegsel.
     */
    VOORVOEGSEL_VOORVOEGSEL(9268),
    /**
     * Waarde voor: Voorvoegsel - Scheidingsteken.
     */
    VOORVOEGSEL_SCHEIDINGSTEKEN(9269),
    /**
     * Waarde voor: Kind - Identiteit.
     */
    KIND_IDENTITEIT(12677),
    /**
     * Waarde voor: Kind - Relatie.
     */
    KIND_RELATIE(13534),
    /**
     * Waarde voor: Kind - RolCode.
     */
    KIND_ROLCODE(13535),
    /**
     * Waarde voor: Kind - Persoon.
     */
    KIND_PERSOON(13536),
    /**
     * Waarde voor: Kind - ObjectSleutel.
     */
    KIND_OBJECTSLEUTEL(13788),
    /**
     * Waarde voor: Kind - VoorkomenSleutel.
     */
    KIND_VOORKOMENSLEUTEL(14279),
    /**
     * Waarde voor: Kind - Betrokkenheid.
     */
    KIND_BETROKKENHEID(14280),
    /**
     * Waarde voor: Kind - TijdstipRegistratie.
     */
    KIND_TIJDSTIPREGISTRATIE(14281),
    /**
     * Waarde voor: Kind - TijdstipVerval.
     */
    KIND_TIJDSTIPVERVAL(14282),
    /**
     * Waarde voor: Kind - NadereAanduidingVerval.
     */
    KIND_NADEREAANDUIDINGVERVAL(14283),
    /**
     * Waarde voor: Kind - ActieInhoud.
     */
    KIND_ACTIEINHOUD(14284),
    /**
     * Waarde voor: Kind - ActieVerval.
     */
    KIND_ACTIEVERVAL(14285),
    /**
     * Waarde voor: Kind - ActieVervalTbvLeveringMutaties.
     */
    KIND_ACTIEVERVALTBVLEVERINGMUTATIES(18902),
    /**
     * Waarde voor: Kind - IndicatieVoorkomenTbvLeveringMutaties.
     */
    KIND_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18903),
    /**
     * Waarde voor: Ouder - OuderlijkGezag.
     */
    OUDER_OUDERLIJKGEZAG(3211),
    /**
     * Waarde voor: Ouder - Ouderschap.
     */
    OUDER_OUDERSCHAP(3858),
    /**
     * Waarde voor: Ouder - Identiteit.
     */
    OUDER_IDENTITEIT(12682),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - IndicatieOuderHeeftGezag.
     */
    OUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG(3208),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - Betrokkenheid.
     */
    OUDER_OUDERLIJKGEZAG_BETROKKENHEID(4033),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - DatumAanvangGeldigheid.
     */
    OUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID(4034),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - DatumEindeGeldigheid.
     */
    OUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID(4035),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - TijdstipRegistratie.
     */
    OUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE(4036),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - TijdstipVerval.
     */
    OUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL(4037),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - ActieInhoud.
     */
    OUDER_OUDERLIJKGEZAG_ACTIEINHOUD(4038),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - ActieVerval.
     */
    OUDER_OUDERLIJKGEZAG_ACTIEVERVAL(4039),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - ActieAanpassingGeldigheid.
     */
    OUDER_OUDERLIJKGEZAG_ACTIEAANPASSINGGELDIGHEID(4040),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - VoorkomenSleutel.
     */
    OUDER_OUDERLIJKGEZAG_VOORKOMENSLEUTEL(4512),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - NadereAanduidingVerval.
     */
    OUDER_OUDERLIJKGEZAG_NADEREAANDUIDINGVERVAL(11114),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - ActieVervalTbvLeveringMutaties.
     */
    OUDER_OUDERLIJKGEZAG_ACTIEVERVALTBVLEVERINGMUTATIES(18818),
    /**
     * Waarde voor: Ouder - OuderlijkGezag - IndicatieVoorkomenTbvLeveringMutaties.
     */
    OUDER_OUDERLIJKGEZAG_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18819),
    /**
     * Waarde voor: Ouder - Ouderschap - Betrokkenheid.
     */
    OUDER_OUDERSCHAP_BETROKKENHEID(4025),
    /**
     * Waarde voor: Ouder - Ouderschap - TijdstipRegistratie.
     */
    OUDER_OUDERSCHAP_TIJDSTIPREGISTRATIE(4026),
    /**
     * Waarde voor: Ouder - Ouderschap - TijdstipVerval.
     */
    OUDER_OUDERSCHAP_TIJDSTIPVERVAL(4027),
    /**
     * Waarde voor: Ouder - Ouderschap - ActieInhoud.
     */
    OUDER_OUDERSCHAP_ACTIEINHOUD(4028),
    /**
     * Waarde voor: Ouder - Ouderschap - ActieVerval.
     */
    OUDER_OUDERSCHAP_ACTIEVERVAL(4029),
    /**
     * Waarde voor: Ouder - Ouderschap - VoorkomenSleutel.
     */
    OUDER_OUDERSCHAP_VOORKOMENSLEUTEL(4509),
    /**
     * Waarde voor: Ouder - Ouderschap - IndicatieOuder.
     */
    OUDER_OUDERSCHAP_INDICATIEOUDER(6088),
    /**
     * Waarde voor: Ouder - Ouderschap - DatumAanvangGeldigheid.
     */
    OUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID(6089),
    /**
     * Waarde voor: Ouder - Ouderschap - DatumEindeGeldigheid.
     */
    OUDER_OUDERSCHAP_DATUMEINDEGELDIGHEID(6090),
    /**
     * Waarde voor: Ouder - Ouderschap - ActieAanpassingGeldigheid.
     */
    OUDER_OUDERSCHAP_ACTIEAANPASSINGGELDIGHEID(6091),
    /**
     * Waarde voor: Ouder - Ouderschap - IndicatieOuderUitWieKindIsGeboren.
     */
    OUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN(6176),
    /**
     * Waarde voor: Ouder - Ouderschap - NadereAanduidingVerval.
     */
    OUDER_OUDERSCHAP_NADEREAANDUIDINGVERVAL(11113),
    /**
     * Waarde voor: Ouder - Ouderschap - ActieVervalTbvLeveringMutaties.
     */
    OUDER_OUDERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES(18816),
    /**
     * Waarde voor: Ouder - Ouderschap - IndicatieVoorkomenTbvLeveringMutaties.
     */
    OUDER_OUDERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18817),
    /**
     * Waarde voor: Ouder - Relatie.
     */
    OUDER_RELATIE(13538),
    /**
     * Waarde voor: Ouder - RolCode.
     */
    OUDER_ROLCODE(13539),
    /**
     * Waarde voor: Ouder - Persoon.
     */
    OUDER_PERSOON(13540),
    /**
     * Waarde voor: Ouder - ObjectSleutel.
     */
    OUDER_OBJECTSLEUTEL(13789),
    /**
     * Waarde voor: Ouder - VoorkomenSleutel.
     */
    OUDER_VOORKOMENSLEUTEL(14286),
    /**
     * Waarde voor: Ouder - Betrokkenheid.
     */
    OUDER_BETROKKENHEID(14287),
    /**
     * Waarde voor: Ouder - TijdstipRegistratie.
     */
    OUDER_TIJDSTIPREGISTRATIE(14288),
    /**
     * Waarde voor: Ouder - TijdstipVerval.
     */
    OUDER_TIJDSTIPVERVAL(14289),
    /**
     * Waarde voor: Ouder - NadereAanduidingVerval.
     */
    OUDER_NADEREAANDUIDINGVERVAL(14290),
    /**
     * Waarde voor: Ouder - ActieInhoud.
     */
    OUDER_ACTIEINHOUD(14291),
    /**
     * Waarde voor: Ouder - ActieVerval.
     */
    OUDER_ACTIEVERVAL(14292),
    /**
     * Waarde voor: Ouder - ActieVervalTbvLeveringMutaties.
     */
    OUDER_ACTIEVERVALTBVLEVERINGMUTATIES(18904),
    /**
     * Waarde voor: Ouder - IndicatieVoorkomenTbvLeveringMutaties.
     */
    OUDER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18905),
    /**
     * Waarde voor: Partner - Identiteit.
     */
    PARTNER_IDENTITEIT(12687),
    /**
     * Waarde voor: Partner - Relatie.
     */
    PARTNER_RELATIE(13542),
    /**
     * Waarde voor: Partner - RolCode.
     */
    PARTNER_ROLCODE(13543),
    /**
     * Waarde voor: Partner - Persoon.
     */
    PARTNER_PERSOON(13544),
    /**
     * Waarde voor: Partner - ObjectSleutel.
     */
    PARTNER_OBJECTSLEUTEL(13790),
    /**
     * Waarde voor: Partner - VoorkomenSleutel.
     */
    PARTNER_VOORKOMENSLEUTEL(14293),
    /**
     * Waarde voor: Partner - Betrokkenheid.
     */
    PARTNER_BETROKKENHEID(14294),
    /**
     * Waarde voor: Partner - TijdstipRegistratie.
     */
    PARTNER_TIJDSTIPREGISTRATIE(14295),
    /**
     * Waarde voor: Partner - TijdstipVerval.
     */
    PARTNER_TIJDSTIPVERVAL(14296),
    /**
     * Waarde voor: Partner - NadereAanduidingVerval.
     */
    PARTNER_NADEREAANDUIDINGVERVAL(14297),
    /**
     * Waarde voor: Partner - ActieInhoud.
     */
    PARTNER_ACTIEINHOUD(14298),
    /**
     * Waarde voor: Partner - ActieVerval.
     */
    PARTNER_ACTIEVERVAL(14299),
    /**
     * Waarde voor: Partner - ActieVervalTbvLeveringMutaties.
     */
    PARTNER_ACTIEVERVALTBVLEVERINGMUTATIES(18906),
    /**
     * Waarde voor: Partner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18907),
    /**
     * Waarde voor: Huwelijk - Identiteit.
     */
    HUWELIJK_IDENTITEIT(12692),
    /**
     * Waarde voor: Huwelijk - Standaard.
     */
    HUWELIJK_STANDAARD(13848),
    /**
     * Waarde voor: Huwelijk - SoortCode.
     */
    HUWELIJK_SOORTCODE(13546),
    /**
     * Waarde voor: Huwelijk - ObjectSleutel.
     */
    HUWELIJK_OBJECTSLEUTEL(13791),
    /**
     * Waarde voor: Huwelijk - VoorkomenSleutel.
     */
    HUWELIJK_VOORKOMENSLEUTEL(13849),
    /**
     * Waarde voor: Huwelijk - Relatie.
     */
    HUWELIJK_RELATIE(13850),
    /**
     * Waarde voor: Huwelijk - TijdstipRegistratie.
     */
    HUWELIJK_TIJDSTIPREGISTRATIE(13851),
    /**
     * Waarde voor: Huwelijk - TijdstipVerval.
     */
    HUWELIJK_TIJDSTIPVERVAL(13852),
    /**
     * Waarde voor: Huwelijk - NadereAanduidingVerval.
     */
    HUWELIJK_NADEREAANDUIDINGVERVAL(13853),
    /**
     * Waarde voor: Huwelijk - DatumAanvang.
     */
    HUWELIJK_DATUMAANVANG(13856),
    /**
     * Waarde voor: Huwelijk - GemeenteAanvangCode.
     */
    HUWELIJK_GEMEENTEAANVANGCODE(13857),
    /**
     * Waarde voor: Huwelijk - WoonplaatsnaamAanvang.
     */
    HUWELIJK_WOONPLAATSNAAMAANVANG(13858),
    /**
     * Waarde voor: Huwelijk - BuitenlandsePlaatsAanvang.
     */
    HUWELIJK_BUITENLANDSEPLAATSAANVANG(13859),
    /**
     * Waarde voor: Huwelijk - BuitenlandseRegioAanvang.
     */
    HUWELIJK_BUITENLANDSEREGIOAANVANG(13860),
    /**
     * Waarde voor: Huwelijk - OmschrijvingLocatieAanvang.
     */
    HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG(13861),
    /**
     * Waarde voor: Huwelijk - LandGebiedAanvangCode.
     */
    HUWELIJK_LANDGEBIEDAANVANGCODE(13862),
    /**
     * Waarde voor: Huwelijk - RedenEindeCode.
     */
    HUWELIJK_REDENEINDECODE(13863),
    /**
     * Waarde voor: Huwelijk - DatumEinde.
     */
    HUWELIJK_DATUMEINDE(13864),
    /**
     * Waarde voor: Huwelijk - GemeenteEindeCode.
     */
    HUWELIJK_GEMEENTEEINDECODE(13865),
    /**
     * Waarde voor: Huwelijk - WoonplaatsnaamEinde.
     */
    HUWELIJK_WOONPLAATSNAAMEINDE(13866),
    /**
     * Waarde voor: Huwelijk - BuitenlandsePlaatsEinde.
     */
    HUWELIJK_BUITENLANDSEPLAATSEINDE(13867),
    /**
     * Waarde voor: Huwelijk - BuitenlandseRegioEinde.
     */
    HUWELIJK_BUITENLANDSEREGIOEINDE(13868),
    /**
     * Waarde voor: Huwelijk - OmschrijvingLocatieEinde.
     */
    HUWELIJK_OMSCHRIJVINGLOCATIEEINDE(13869),
    /**
     * Waarde voor: Huwelijk - LandGebiedEindeCode.
     */
    HUWELIJK_LANDGEBIEDEINDECODE(13870),
    /**
     * Waarde voor: Huwelijk - ActieInhoud.
     */
    HUWELIJK_ACTIEINHOUD(14019),
    /**
     * Waarde voor: Huwelijk - ActieVerval.
     */
    HUWELIJK_ACTIEVERVAL(14020),
    /**
     * Waarde voor: Huwelijk - ActieVervalTbvLeveringMutaties.
     */
    HUWELIJK_ACTIEVERVALTBVLEVERINGMUTATIES(18922),
    /**
     * Waarde voor: Huwelijk - IndicatieVoorkomenTbvLeveringMutaties.
     */
    HUWELIJK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18923),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - Identiteit.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_IDENTITEIT(12704),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - Standaard.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_STANDAARD(12716),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - SoortCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_SOORTCODE(13549),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - ObjectSleutel.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OBJECTSLEUTEL(13792),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - Relatie.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_RELATIE(13551),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - TijdstipRegistratie.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE(13552),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - TijdstipVerval.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL(13553),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - NadereAanduidingVerval.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_NADEREAANDUIDINGVERVAL(13554),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - DatumAanvang.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMAANVANG(13557),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - GemeenteAanvangCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE(13558),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - WoonplaatsnaamAanvang.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG(13559),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - BuitenlandsePlaatsAanvang.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG(13560),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - BuitenlandseRegioAanvang.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG(13561),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - OmschrijvingLocatieAanvang.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG(13562),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - LandGebiedAanvangCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE(13563),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - RedenEindeCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_REDENEINDECODE(13564),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - DatumEinde.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_DATUMEINDE(13565),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - GemeenteEindeCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE(13566),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - WoonplaatsnaamEinde.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE(13567),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - BuitenlandsePlaatsEinde.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE(13568),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - BuitenlandseRegioEinde.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE(13569),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - OmschrijvingLocatieEinde.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE(13570),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - LandGebiedEindeCode.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE(13571),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - VoorkomenSleutel.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_VOORKOMENSLEUTEL(13793),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - ActieInhoud.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEINHOUD(14322),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - ActieVerval.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEVERVAL(14323),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - ActieVervalTbvLeveringMutaties.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES(18908),
    /**
     * Waarde voor: HuwelijkGeregistreerdPartnerschap - IndicatieVoorkomenTbvLeveringMutaties.
     */
    HUWELIJKGEREGISTREERDPARTNERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18909),
    /**
     * Waarde voor: GeregistreerdPartnerschap - Identiteit.
     */
    GEREGISTREERDPARTNERSCHAP_IDENTITEIT(12739),
    /**
     * Waarde voor: GeregistreerdPartnerschap - Standaard.
     */
    GEREGISTREERDPARTNERSCHAP_STANDAARD(12751),
    /**
     * Waarde voor: GeregistreerdPartnerschap - SoortCode.
     */
    GEREGISTREERDPARTNERSCHAP_SOORTCODE(13573),
    /**
     * Waarde voor: GeregistreerdPartnerschap - ObjectSleutel.
     */
    GEREGISTREERDPARTNERSCHAP_OBJECTSLEUTEL(13794),
    /**
     * Waarde voor: GeregistreerdPartnerschap - Relatie.
     */
    GEREGISTREERDPARTNERSCHAP_RELATIE(13575),
    /**
     * Waarde voor: GeregistreerdPartnerschap - TijdstipRegistratie.
     */
    GEREGISTREERDPARTNERSCHAP_TIJDSTIPREGISTRATIE(13576),
    /**
     * Waarde voor: GeregistreerdPartnerschap - TijdstipVerval.
     */
    GEREGISTREERDPARTNERSCHAP_TIJDSTIPVERVAL(13577),
    /**
     * Waarde voor: GeregistreerdPartnerschap - NadereAanduidingVerval.
     */
    GEREGISTREERDPARTNERSCHAP_NADEREAANDUIDINGVERVAL(13578),
    /**
     * Waarde voor: GeregistreerdPartnerschap - DatumAanvang.
     */
    GEREGISTREERDPARTNERSCHAP_DATUMAANVANG(13581),
    /**
     * Waarde voor: GeregistreerdPartnerschap - GemeenteAanvangCode.
     */
    GEREGISTREERDPARTNERSCHAP_GEMEENTEAANVANGCODE(13582),
    /**
     * Waarde voor: GeregistreerdPartnerschap - WoonplaatsnaamAanvang.
     */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMAANVANG(13583),
    /**
     * Waarde voor: GeregistreerdPartnerschap - BuitenlandsePlaatsAanvang.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSAANVANG(13584),
    /**
     * Waarde voor: GeregistreerdPartnerschap - BuitenlandseRegioAanvang.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOAANVANG(13585),
    /**
     * Waarde voor: GeregistreerdPartnerschap - OmschrijvingLocatieAanvang.
     */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEAANVANG(13586),
    /**
     * Waarde voor: GeregistreerdPartnerschap - LandGebiedAanvangCode.
     */
    GEREGISTREERDPARTNERSCHAP_LANDGEBIEDAANVANGCODE(13587),
    /**
     * Waarde voor: GeregistreerdPartnerschap - RedenEindeCode.
     */
    GEREGISTREERDPARTNERSCHAP_REDENEINDECODE(13588),
    /**
     * Waarde voor: GeregistreerdPartnerschap - DatumEinde.
     */
    GEREGISTREERDPARTNERSCHAP_DATUMEINDE(13589),
    /**
     * Waarde voor: GeregistreerdPartnerschap - GemeenteEindeCode.
     */
    GEREGISTREERDPARTNERSCHAP_GEMEENTEEINDECODE(13590),
    /**
     * Waarde voor: GeregistreerdPartnerschap - WoonplaatsnaamEinde.
     */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAMEINDE(13591),
    /**
     * Waarde voor: GeregistreerdPartnerschap - BuitenlandsePlaatsEinde.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEPLAATSEINDE(13592),
    /**
     * Waarde voor: GeregistreerdPartnerschap - BuitenlandseRegioEinde.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSEREGIOEINDE(13593),
    /**
     * Waarde voor: GeregistreerdPartnerschap - OmschrijvingLocatieEinde.
     */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVINGLOCATIEEINDE(13594),
    /**
     * Waarde voor: GeregistreerdPartnerschap - LandGebiedEindeCode.
     */
    GEREGISTREERDPARTNERSCHAP_LANDGEBIEDEINDECODE(13595),
    /**
     * Waarde voor: GeregistreerdPartnerschap - VoorkomenSleutel.
     */
    GEREGISTREERDPARTNERSCHAP_VOORKOMENSLEUTEL(13795),
    /**
     * Waarde voor: GeregistreerdPartnerschap - ActieInhoud.
     */
    GEREGISTREERDPARTNERSCHAP_ACTIEINHOUD(14023),
    /**
     * Waarde voor: GeregistreerdPartnerschap - ActieVerval.
     */
    GEREGISTREERDPARTNERSCHAP_ACTIEVERVAL(14024),
    /**
     * Waarde voor: GeregistreerdPartnerschap - ActieVervalTbvLeveringMutaties.
     */
    GEREGISTREERDPARTNERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES(18924),
    /**
     * Waarde voor: GeregistreerdPartnerschap - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GEREGISTREERDPARTNERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18925),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - Identiteit.
     */
    FAMILIERECHTELIJKEBETREKKING_IDENTITEIT(12774),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - Standaard.
     */
    FAMILIERECHTELIJKEBETREKKING_STANDAARD(14324),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - SoortCode.
     */
    FAMILIERECHTELIJKEBETREKKING_SOORTCODE(13597),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - ObjectSleutel.
     */
    FAMILIERECHTELIJKEBETREKKING_OBJECTSLEUTEL(13796),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - VoorkomenSleutel.
     */
    FAMILIERECHTELIJKEBETREKKING_VOORKOMENSLEUTEL(14325),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - Relatie.
     */
    FAMILIERECHTELIJKEBETREKKING_RELATIE(14326),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - TijdstipRegistratie.
     */
    FAMILIERECHTELIJKEBETREKKING_TIJDSTIPREGISTRATIE(14327),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - TijdstipVerval.
     */
    FAMILIERECHTELIJKEBETREKKING_TIJDSTIPVERVAL(14328),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - NadereAanduidingVerval.
     */
    FAMILIERECHTELIJKEBETREKKING_NADEREAANDUIDINGVERVAL(14329),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - ActieInhoud.
     */
    FAMILIERECHTELIJKEBETREKKING_ACTIEINHOUD(14330),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - ActieVerval.
     */
    FAMILIERECHTELIJKEBETREKKING_ACTIEVERVAL(14331),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - DatumAanvang.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUMAANVANG(14332),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - GemeenteAanvangCode.
     */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTEAANVANGCODE(14333),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - WoonplaatsnaamAanvang.
     */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMAANVANG(14334),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - BuitenlandsePlaatsAanvang.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSAANVANG(14335),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - BuitenlandseRegioAanvang.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOAANVANG(14336),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - OmschrijvingLocatieAanvang.
     */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEAANVANG(14337),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - LandGebiedAanvangCode.
     */
    FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDAANVANGCODE(14338),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - RedenEindeCode.
     */
    FAMILIERECHTELIJKEBETREKKING_REDENEINDECODE(14339),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - DatumEinde.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUMEINDE(14340),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - GemeenteEindeCode.
     */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTEEINDECODE(14341),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - WoonplaatsnaamEinde.
     */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAMEINDE(14342),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - BuitenlandsePlaatsEinde.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEPLAATSEINDE(14343),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - BuitenlandseRegioEinde.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSEREGIOEINDE(14344),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - OmschrijvingLocatieEinde.
     */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVINGLOCATIEEINDE(14345),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - LandGebiedEindeCode.
     */
    FAMILIERECHTELIJKEBETREKKING_LANDGEBIEDEINDECODE(14346),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - ActieVervalTbvLeveringMutaties.
     */
    FAMILIERECHTELIJKEBETREKKING_ACTIEVERVALTBVLEVERINGMUTATIES(18910),
    /**
     * Waarde voor: FamilierechtelijkeBetrekking - IndicatieVoorkomenTbvLeveringMutaties.
     */
    FAMILIERECHTELIJKEBETREKKING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18911),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - Identiteit.
     */
    ERKENNINGONGEBORENVRUCHT_IDENTITEIT(12786),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - Standaard.
     */
    ERKENNINGONGEBORENVRUCHT_STANDAARD(14347),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - SoortCode.
     */
    ERKENNINGONGEBORENVRUCHT_SOORTCODE(13600),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - ObjectSleutel.
     */
    ERKENNINGONGEBORENVRUCHT_OBJECTSLEUTEL(13797),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - VoorkomenSleutel.
     */
    ERKENNINGONGEBORENVRUCHT_VOORKOMENSLEUTEL(14348),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - Relatie.
     */
    ERKENNINGONGEBORENVRUCHT_RELATIE(14349),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - TijdstipRegistratie.
     */
    ERKENNINGONGEBORENVRUCHT_TIJDSTIPREGISTRATIE(14350),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - TijdstipVerval.
     */
    ERKENNINGONGEBORENVRUCHT_TIJDSTIPVERVAL(14351),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - NadereAanduidingVerval.
     */
    ERKENNINGONGEBORENVRUCHT_NADEREAANDUIDINGVERVAL(14352),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - ActieInhoud.
     */
    ERKENNINGONGEBORENVRUCHT_ACTIEINHOUD(14353),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - ActieVerval.
     */
    ERKENNINGONGEBORENVRUCHT_ACTIEVERVAL(14354),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - DatumAanvang.
     */
    ERKENNINGONGEBORENVRUCHT_DATUMAANVANG(14355),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - GemeenteAanvangCode.
     */
    ERKENNINGONGEBORENVRUCHT_GEMEENTEAANVANGCODE(14356),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - WoonplaatsnaamAanvang.
     */
    ERKENNINGONGEBORENVRUCHT_WOONPLAATSNAAMAANVANG(14357),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - BuitenlandsePlaatsAanvang.
     */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEPLAATSAANVANG(14358),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - BuitenlandseRegioAanvang.
     */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEREGIOAANVANG(14359),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - OmschrijvingLocatieAanvang.
     */
    ERKENNINGONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEAANVANG(14360),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - LandGebiedAanvangCode.
     */
    ERKENNINGONGEBORENVRUCHT_LANDGEBIEDAANVANGCODE(14361),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - RedenEindeCode.
     */
    ERKENNINGONGEBORENVRUCHT_REDENEINDECODE(14362),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - DatumEinde.
     */
    ERKENNINGONGEBORENVRUCHT_DATUMEINDE(14363),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - GemeenteEindeCode.
     */
    ERKENNINGONGEBORENVRUCHT_GEMEENTEEINDECODE(14364),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - WoonplaatsnaamEinde.
     */
    ERKENNINGONGEBORENVRUCHT_WOONPLAATSNAAMEINDE(14365),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - BuitenlandsePlaatsEinde.
     */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEPLAATSEINDE(14366),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - BuitenlandseRegioEinde.
     */
    ERKENNINGONGEBORENVRUCHT_BUITENLANDSEREGIOEINDE(14367),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - OmschrijvingLocatieEinde.
     */
    ERKENNINGONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEEINDE(14368),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - LandGebiedEindeCode.
     */
    ERKENNINGONGEBORENVRUCHT_LANDGEBIEDEINDECODE(14369),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - ActieVervalTbvLeveringMutaties.
     */
    ERKENNINGONGEBORENVRUCHT_ACTIEVERVALTBVLEVERINGMUTATIES(18912),
    /**
     * Waarde voor: ErkenningOngeborenVrucht - IndicatieVoorkomenTbvLeveringMutaties.
     */
    ERKENNINGONGEBORENVRUCHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18913),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - Identiteit.
     */
    NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT(12798),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - Standaard.
     */
    NAAMSKEUZEONGEBORENVRUCHT_STANDAARD(14370),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - SoortCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_SOORTCODE(13603),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - ObjectSleutel.
     */
    NAAMSKEUZEONGEBORENVRUCHT_OBJECTSLEUTEL(13798),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - VoorkomenSleutel.
     */
    NAAMSKEUZEONGEBORENVRUCHT_VOORKOMENSLEUTEL(14371),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - Relatie.
     */
    NAAMSKEUZEONGEBORENVRUCHT_RELATIE(14372),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - TijdstipRegistratie.
     */
    NAAMSKEUZEONGEBORENVRUCHT_TIJDSTIPREGISTRATIE(14373),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - TijdstipVerval.
     */
    NAAMSKEUZEONGEBORENVRUCHT_TIJDSTIPVERVAL(14374),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - NadereAanduidingVerval.
     */
    NAAMSKEUZEONGEBORENVRUCHT_NADEREAANDUIDINGVERVAL(14375),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - ActieInhoud.
     */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEINHOUD(14376),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - ActieVerval.
     */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEVERVAL(14377),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - DatumAanvang.
     */
    NAAMSKEUZEONGEBORENVRUCHT_DATUMAANVANG(14378),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - GemeenteAanvangCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_GEMEENTEAANVANGCODE(14379),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - WoonplaatsnaamAanvang.
     */
    NAAMSKEUZEONGEBORENVRUCHT_WOONPLAATSNAAMAANVANG(14380),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - BuitenlandsePlaatsAanvang.
     */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEPLAATSAANVANG(14381),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - BuitenlandseRegioAanvang.
     */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEREGIOAANVANG(14382),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - OmschrijvingLocatieAanvang.
     */
    NAAMSKEUZEONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEAANVANG(14383),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - LandGebiedAanvangCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_LANDGEBIEDAANVANGCODE(14384),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - RedenEindeCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_REDENEINDECODE(14385),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - DatumEinde.
     */
    NAAMSKEUZEONGEBORENVRUCHT_DATUMEINDE(14386),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - GemeenteEindeCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_GEMEENTEEINDECODE(14387),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - WoonplaatsnaamEinde.
     */
    NAAMSKEUZEONGEBORENVRUCHT_WOONPLAATSNAAMEINDE(14388),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - BuitenlandsePlaatsEinde.
     */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEPLAATSEINDE(14389),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - BuitenlandseRegioEinde.
     */
    NAAMSKEUZEONGEBORENVRUCHT_BUITENLANDSEREGIOEINDE(14390),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - OmschrijvingLocatieEinde.
     */
    NAAMSKEUZEONGEBORENVRUCHT_OMSCHRIJVINGLOCATIEEINDE(14391),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - LandGebiedEindeCode.
     */
    NAAMSKEUZEONGEBORENVRUCHT_LANDGEBIEDEINDECODE(14392),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - ActieVervalTbvLeveringMutaties.
     */
    NAAMSKEUZEONGEBORENVRUCHT_ACTIEVERVALTBVLEVERINGMUTATIES(18914),
    /**
     * Waarde voor: NaamskeuzeOngeborenVrucht - IndicatieVoorkomenTbvLeveringMutaties.
     */
    NAAMSKEUZEONGEBORENVRUCHT_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18915),
    /**
     * Waarde voor: Erkenner - Identiteit.
     */
    ERKENNER_IDENTITEIT(12810),
    /**
     * Waarde voor: Erkenner - Relatie.
     */
    ERKENNER_RELATIE(13606),
    /**
     * Waarde voor: Erkenner - RolCode.
     */
    ERKENNER_ROLCODE(13607),
    /**
     * Waarde voor: Erkenner - Persoon.
     */
    ERKENNER_PERSOON(13608),
    /**
     * Waarde voor: Erkenner - ObjectSleutel.
     */
    ERKENNER_OBJECTSLEUTEL(13799),
    /**
     * Waarde voor: Erkenner - VoorkomenSleutel.
     */
    ERKENNER_VOORKOMENSLEUTEL(14300),
    /**
     * Waarde voor: Erkenner - Betrokkenheid.
     */
    ERKENNER_BETROKKENHEID(14301),
    /**
     * Waarde voor: Erkenner - TijdstipRegistratie.
     */
    ERKENNER_TIJDSTIPREGISTRATIE(14302),
    /**
     * Waarde voor: Erkenner - TijdstipVerval.
     */
    ERKENNER_TIJDSTIPVERVAL(14303),
    /**
     * Waarde voor: Erkenner - NadereAanduidingVerval.
     */
    ERKENNER_NADEREAANDUIDINGVERVAL(14304),
    /**
     * Waarde voor: Erkenner - ActieInhoud.
     */
    ERKENNER_ACTIEINHOUD(14305),
    /**
     * Waarde voor: Erkenner - ActieVerval.
     */
    ERKENNER_ACTIEVERVAL(14306),
    /**
     * Waarde voor: Erkenner - ActieVervalTbvLeveringMutaties.
     */
    ERKENNER_ACTIEVERVALTBVLEVERINGMUTATIES(18916),
    /**
     * Waarde voor: Erkenner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    ERKENNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18917),
    /**
     * Waarde voor: Instemmer - Identiteit.
     */
    INSTEMMER_IDENTITEIT(12815),
    /**
     * Waarde voor: Instemmer - Relatie.
     */
    INSTEMMER_RELATIE(13610),
    /**
     * Waarde voor: Instemmer - RolCode.
     */
    INSTEMMER_ROLCODE(13611),
    /**
     * Waarde voor: Instemmer - Persoon.
     */
    INSTEMMER_PERSOON(13612),
    /**
     * Waarde voor: Instemmer - ObjectSleutel.
     */
    INSTEMMER_OBJECTSLEUTEL(13800),
    /**
     * Waarde voor: Instemmer - VoorkomenSleutel.
     */
    INSTEMMER_VOORKOMENSLEUTEL(14307),
    /**
     * Waarde voor: Instemmer - Betrokkenheid.
     */
    INSTEMMER_BETROKKENHEID(14308),
    /**
     * Waarde voor: Instemmer - TijdstipRegistratie.
     */
    INSTEMMER_TIJDSTIPREGISTRATIE(14309),
    /**
     * Waarde voor: Instemmer - TijdstipVerval.
     */
    INSTEMMER_TIJDSTIPVERVAL(14310),
    /**
     * Waarde voor: Instemmer - NadereAanduidingVerval.
     */
    INSTEMMER_NADEREAANDUIDINGVERVAL(14311),
    /**
     * Waarde voor: Instemmer - ActieInhoud.
     */
    INSTEMMER_ACTIEINHOUD(14312),
    /**
     * Waarde voor: Instemmer - ActieVerval.
     */
    INSTEMMER_ACTIEVERVAL(14313),
    /**
     * Waarde voor: Instemmer - ActieVervalTbvLeveringMutaties.
     */
    INSTEMMER_ACTIEVERVALTBVLEVERINGMUTATIES(18918),
    /**
     * Waarde voor: Instemmer - IndicatieVoorkomenTbvLeveringMutaties.
     */
    INSTEMMER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18919),
    /**
     * Waarde voor: Naamgever - Identiteit.
     */
    NAAMGEVER_IDENTITEIT(12820),
    /**
     * Waarde voor: Naamgever - Relatie.
     */
    NAAMGEVER_RELATIE(13614),
    /**
     * Waarde voor: Naamgever - RolCode.
     */
    NAAMGEVER_ROLCODE(13615),
    /**
     * Waarde voor: Naamgever - Persoon.
     */
    NAAMGEVER_PERSOON(13616),
    /**
     * Waarde voor: Naamgever - ObjectSleutel.
     */
    NAAMGEVER_OBJECTSLEUTEL(13801),
    /**
     * Waarde voor: Naamgever - VoorkomenSleutel.
     */
    NAAMGEVER_VOORKOMENSLEUTEL(14314),
    /**
     * Waarde voor: Naamgever - Betrokkenheid.
     */
    NAAMGEVER_BETROKKENHEID(14315),
    /**
     * Waarde voor: Naamgever - TijdstipRegistratie.
     */
    NAAMGEVER_TIJDSTIPREGISTRATIE(14316),
    /**
     * Waarde voor: Naamgever - TijdstipVerval.
     */
    NAAMGEVER_TIJDSTIPVERVAL(14317),
    /**
     * Waarde voor: Naamgever - NadereAanduidingVerval.
     */
    NAAMGEVER_NADEREAANDUIDINGVERVAL(14318),
    /**
     * Waarde voor: Naamgever - ActieInhoud.
     */
    NAAMGEVER_ACTIEINHOUD(14319),
    /**
     * Waarde voor: Naamgever - ActieVerval.
     */
    NAAMGEVER_ACTIEVERVAL(14320),
    /**
     * Waarde voor: Naamgever - ActieVervalTbvLeveringMutaties.
     */
    NAAMGEVER_ACTIEVERVALTBVLEVERINGMUTATIES(18920),
    /**
     * Waarde voor: Naamgever - IndicatieVoorkomenTbvLeveringMutaties.
     */
    NAAMGEVER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18921),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - Identiteit.
     */
    PERSOON_VERSTREKKINGSBEPERKING_IDENTITEIT(9347),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - ObjectSleutel.
     */
    PERSOON_VERSTREKKINGSBEPERKING_OBJECTSLEUTEL(9349),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - Persoon.
     */
    PERSOON_VERSTREKKINGSBEPERKING_PERSOON(9351),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - PartijCode.
     */
    PERSOON_VERSTREKKINGSBEPERKING_PARTIJCODE(9352),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - TijdstipRegistratie.
     */
    PERSOON_VERSTREKKINGSBEPERKING_TIJDSTIPREGISTRATIE(9365),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - TijdstipVerval.
     */
    PERSOON_VERSTREKKINGSBEPERKING_TIJDSTIPVERVAL(9366),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - ActieInhoud.
     */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEINHOUD(9367),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - ActieVerval.
     */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEVERVAL(9368),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - VoorkomenSleutel.
     */
    PERSOON_VERSTREKKINGSBEPERKING_VOORKOMENSLEUTEL(9376),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - OmschrijvingDerde.
     */
    PERSOON_VERSTREKKINGSBEPERKING_OMSCHRIJVINGDERDE(10912),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - GemeenteVerordeningPartijCode.
     */
    PERSOON_VERSTREKKINGSBEPERKING_GEMEENTEVERORDENINGPARTIJCODE(10913),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - PersoonVerstrekkingsbeperking.
     */
    PERSOON_VERSTREKKINGSBEPERKING_PERSOONVERSTREKKINGSBEPERKING(10954),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - NadereAanduidingVerval.
     */
    PERSOON_VERSTREKKINGSBEPERKING_NADEREAANDUIDINGVERVAL(11149),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - ActieVervalTbvLeveringMutaties.
     */
    PERSOON_VERSTREKKINGSBEPERKING_ACTIEVERVALTBVLEVERINGMUTATIES(18886),
    /**
     * Waarde voor: Persoon - Verstrekkingsbeperking - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PERSOON_VERSTREKKINGSBEPERKING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18887),
    /**
     * Waarde voor: BurgerzakenModule - Identiteit.
     */
    BURGERZAKENMODULE_IDENTITEIT(9511),
    /**
     * Waarde voor: BurgerzakenModule - ObjectSleutel.
     */
    BURGERZAKENMODULE_OBJECTSLEUTEL(9513),
    /**
     * Waarde voor: BurgerzakenModule - Naam.
     */
    BURGERZAKENMODULE_NAAM(9514),
    /**
     * Waarde voor: BurgerzakenModule - Omschrijving.
     */
    BURGERZAKENMODULE_OMSCHRIJVING(9535),
    /**
     * Waarde voor: Gemeente - Identiteit.
     */
    GEMEENTE_IDENTITEIT(9559),
    /**
     * Waarde voor: Gemeente - VoortzettendeGemeenteCode.
     */
    GEMEENTE_VOORTZETTENDEGEMEENTECODE(2005),
    /**
     * Waarde voor: Gemeente - ObjectSleutel.
     */
    GEMEENTE_OBJECTSLEUTEL(9561),
    /**
     * Waarde voor: Gemeente - Naam.
     */
    GEMEENTE_NAAM(9562),
    /**
     * Waarde voor: Gemeente - Code.
     */
    GEMEENTE_CODE(9563),
    /**
     * Waarde voor: Gemeente - PartijCode.
     */
    GEMEENTE_PARTIJCODE(9564),
    /**
     * Waarde voor: Gemeente - DatumAanvangGeldigheid.
     */
    GEMEENTE_DATUMAANVANGGELDIGHEID(9566),
    /**
     * Waarde voor: Gemeente - DatumEindeGeldigheid.
     */
    GEMEENTE_DATUMEINDEGELDIGHEID(9567),
    /**
     * Waarde voor: CategorieAdministratieveHandeling - Identiteit.
     */
    CATEGORIEADMINISTRATIEVEHANDELING_IDENTITEIT(9867),
    /**
     * Waarde voor: CategorieAdministratieveHandeling - Naam.
     */
    CATEGORIEADMINISTRATIEVEHANDELING_NAAM(9868),
    /**
     * Waarde voor: CategorieAdministratieveHandeling - ObjectSleutel.
     */
    CATEGORIEADMINISTRATIEVEHANDELING_OBJECTSLEUTEL(9878),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - Identiteit.
     */
    PERSOON_AFNEMERINDICATIE_IDENTITEIT(10318),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - Standaard.
     */
    PERSOON_AFNEMERINDICATIE_STANDAARD(10319),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - ObjectSleutel.
     */
    PERSOON_AFNEMERINDICATIE_OBJECTSLEUTEL(10321),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - LeveringsAutorisatieIdentificatie.
     */
    PERSOON_AFNEMERINDICATIE_LEVERINGSAUTORISATIEIDENTIFICATIE(10323),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - Persoon.
     */
    PERSOON_AFNEMERINDICATIE_PERSOON(10324),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - AfnemerCode.
     */
    PERSOON_AFNEMERINDICATIE_AFNEMERCODE(10343),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - DatumAanvangMaterielePeriode.
     */
    PERSOON_AFNEMERINDICATIE_DATUMAANVANGMATERIELEPERIODE(10327),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - DatumEindeVolgen.
     */
    PERSOON_AFNEMERINDICATIE_DATUMEINDEVOLGEN(10328),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - TijdstipRegistratie.
     */
    PERSOON_AFNEMERINDICATIE_TIJDSTIPREGISTRATIE(10331),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - TijdstipVerval.
     */
    PERSOON_AFNEMERINDICATIE_TIJDSTIPVERVAL(10332),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - VoorkomenSleutel.
     */
    PERSOON_AFNEMERINDICATIE_VOORKOMENSLEUTEL(10341),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - PersoonAfnemerindicatie.
     */
    PERSOON_AFNEMERINDICATIE_PERSOONAFNEMERINDICATIE(10348),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - NadereAanduidingVerval.
     */
    PERSOON_AFNEMERINDICATIE_NADEREAANDUIDINGVERVAL(11142),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - DienstInhoud.
     */
    PERSOON_AFNEMERINDICATIE_DIENSTINHOUD(11418),
    /**
     * Waarde voor: Persoon - Afnemerindicatie - DienstVerval.
     */
    PERSOON_AFNEMERINDICATIE_DIENSTVERVAL(11419),
    /**
     * Waarde voor: Persoon - Cache - Identiteit.
     */
    PERSOON_CACHE_IDENTITEIT(10386),
    /**
     * Waarde voor: Persoon - Cache - Standaard.
     */
    PERSOON_CACHE_STANDAARD(10403),
    /**
     * Waarde voor: Persoon - Cache - ObjectSleutel.
     */
    PERSOON_CACHE_OBJECTSLEUTEL(10387),
    /**
     * Waarde voor: Persoon - Cache - Persoon.
     */
    PERSOON_CACHE_PERSOON(10401),
    /**
     * Waarde voor: Persoon - Cache - Versienummer.
     */
    PERSOON_CACHE_VERSIENUMMER(10388),
    /**
     * Waarde voor: Persoon - Cache - PersoonHistorieVolledigChecksum.
     */
    PERSOON_CACHE_PERSOONHISTORIEVOLLEDIGCHECKSUM(10392),
    /**
     * Waarde voor: Persoon - Cache - PersoonHistorieVolledigGegevens.
     */
    PERSOON_CACHE_PERSOONHISTORIEVOLLEDIGGEGEVENS(10393),
    /**
     * Waarde voor: Persoon - Cache - AfnemerindicatieGegevens.
     */
    PERSOON_CACHE_AFNEMERINDICATIEGEGEVENS(11422),
    /**
     * Waarde voor: Persoon - Cache - AfnemerindicatieChecksum.
     */
    PERSOON_CACHE_AFNEMERINDICATIECHECKSUM(11423),
    /**
     * Waarde voor: Terugmelding - Identiteit.
     */
    TERUGMELDING_IDENTITEIT(10717),
    /**
     * Waarde voor: Terugmelding - Standaard.
     */
    TERUGMELDING_STANDAARD(10739),
    /**
     * Waarde voor: Terugmelding - Contactpersoon.
     */
    TERUGMELDING_CONTACTPERSOON(11095),
    /**
     * Waarde voor: Terugmelding - ObjectSleutel.
     */
    TERUGMELDING_OBJECTSLEUTEL(10719),
    /**
     * Waarde voor: Terugmelding - TerugmeldendePartijCode.
     */
    TERUGMELDING_TERUGMELDENDEPARTIJCODE(10736),
    /**
     * Waarde voor: Terugmelding - Persoon.
     */
    TERUGMELDING_PERSOON(10737),
    /**
     * Waarde voor: Terugmelding - BijhoudingsgemeentePartijCode.
     */
    TERUGMELDING_BIJHOUDINGSGEMEENTEPARTIJCODE(10738),
    /**
     * Waarde voor: Terugmelding - Identiteit - TijdstipRegistratie.
     */
    TERUGMELDING_IDENTITEIT_TIJDSTIPREGISTRATIE(10839),
    /**
     * Waarde voor: Terugmelding - Onderzoek.
     */
    TERUGMELDING_ONDERZOEK(10740),
    /**
     * Waarde voor: Terugmelding - KenmerkMeldendePartij.
     */
    TERUGMELDING_KENMERKMELDENDEPARTIJ(10741),
    /**
     * Waarde voor: Terugmelding - StatusNaam.
     */
    TERUGMELDING_STATUSNAAM(10752),
    /**
     * Waarde voor: Terugmelding - Terugmelding.
     */
    TERUGMELDING_TERUGMELDING(10811),
    /**
     * Waarde voor: Terugmelding - Standaard - TijdstipRegistratie.
     */
    TERUGMELDING_STANDAARD_TIJDSTIPREGISTRATIE(10812),
    /**
     * Waarde voor: Terugmelding - TijdstipVerval.
     */
    TERUGMELDING_TIJDSTIPVERVAL(10813),
    /**
     * Waarde voor: Terugmelding - ActieInhoud.
     */
    TERUGMELDING_ACTIEINHOUD(10814),
    /**
     * Waarde voor: Terugmelding - ActieVerval.
     */
    TERUGMELDING_ACTIEVERVAL(10815),
    /**
     * Waarde voor: Terugmelding - VoorkomenSleutel.
     */
    TERUGMELDING_VOORKOMENSLEUTEL(10836),
    /**
     * Waarde voor: Terugmelding - Toelichting.
     */
    TERUGMELDING_TOELICHTING(11092),
    /**
     * Waarde voor: Terugmelding - NadereAanduidingVerval.
     */
    TERUGMELDING_NADEREAANDUIDINGVERVAL(11156),
    /**
     * Waarde voor: Terugmelding - ActieVervalTbvLeveringMutaties.
     */
    TERUGMELDING_ACTIEVERVALTBVLEVERINGMUTATIES(18894),
    /**
     * Waarde voor: Terugmelding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    TERUGMELDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18895),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Email.
     */
    TERUGMELDING_CONTACTPERSOON_EMAIL(11096),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Telefoonnummer.
     */
    TERUGMELDING_CONTACTPERSOON_TELEFOONNUMMER(11097),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Voornamen.
     */
    TERUGMELDING_CONTACTPERSOON_VOORNAMEN(11101),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Voorvoegsel.
     */
    TERUGMELDING_CONTACTPERSOON_VOORVOEGSEL(11102),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Scheidingsteken.
     */
    TERUGMELDING_CONTACTPERSOON_SCHEIDINGSTEKEN(11103),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Geslachtsnaamstam.
     */
    TERUGMELDING_CONTACTPERSOON_GESLACHTSNAAMSTAM(11104),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - Terugmelding.
     */
    TERUGMELDING_CONTACTPERSOON_TERUGMELDING(11158),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - TijdstipRegistratie.
     */
    TERUGMELDING_CONTACTPERSOON_TIJDSTIPREGISTRATIE(11159),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - TijdstipVerval.
     */
    TERUGMELDING_CONTACTPERSOON_TIJDSTIPVERVAL(11160),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - ActieInhoud.
     */
    TERUGMELDING_CONTACTPERSOON_ACTIEINHOUD(11161),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - ActieVerval.
     */
    TERUGMELDING_CONTACTPERSOON_ACTIEVERVAL(11162),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - NadereAanduidingVerval.
     */
    TERUGMELDING_CONTACTPERSOON_NADEREAANDUIDINGVERVAL(11163),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - VoorkomenSleutel.
     */
    TERUGMELDING_CONTACTPERSOON_VOORKOMENSLEUTEL(11174),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - ActieVervalTbvLeveringMutaties.
     */
    TERUGMELDING_CONTACTPERSOON_ACTIEVERVALTBVLEVERINGMUTATIES(18896),
    /**
     * Waarde voor: Terugmelding - Contactpersoon - IndicatieVoorkomenTbvLeveringMutaties.
     */
    TERUGMELDING_CONTACTPERSOON_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18897),
    /**
     * Waarde voor: StatusTerugmelding - Identiteit.
     */
    STATUSTERUGMELDING_IDENTITEIT(10743),
    /**
     * Waarde voor: StatusTerugmelding - ObjectSleutel.
     */
    STATUSTERUGMELDING_OBJECTSLEUTEL(10745),
    /**
     * Waarde voor: StatusTerugmelding - Naam.
     */
    STATUSTERUGMELDING_NAAM(10747),
    /**
     * Waarde voor: StatusTerugmelding - Omschrijving.
     */
    STATUSTERUGMELDING_OMSCHRIJVING(10748),
    /**
     * Waarde voor: GegevenInTerugmelding - Identiteit.
     */
    GEGEVENINTERUGMELDING_IDENTITEIT(10754),
    /**
     * Waarde voor: GegevenInTerugmelding - ObjectSleutel.
     */
    GEGEVENINTERUGMELDING_OBJECTSLEUTEL(10756),
    /**
     * Waarde voor: GegevenInTerugmelding - Terugmelding.
     */
    GEGEVENINTERUGMELDING_TERUGMELDING(10758),
    /**
     * Waarde voor: GegevenInTerugmelding - ElementNaam.
     */
    GEGEVENINTERUGMELDING_ELEMENTNAAM(10759),
    /**
     * Waarde voor: GegevenInTerugmelding - BetwijfeldeWaarde.
     */
    GEGEVENINTERUGMELDING_BETWIJFELDEWAARDE(10761),
    /**
     * Waarde voor: GegevenInTerugmelding - VerwachteWaarde.
     */
    GEGEVENINTERUGMELDING_VERWACHTEWAARDE(10762),
    /**
     * Waarde voor: SoortPersoonOnderzoek - Identiteit.
     */
    SOORTPERSOONONDERZOEK_IDENTITEIT(10765),
    /**
     * Waarde voor: SoortPersoonOnderzoek - ObjectSleutel.
     */
    SOORTPERSOONONDERZOEK_OBJECTSLEUTEL(10767),
    /**
     * Waarde voor: SoortPersoonOnderzoek - Naam.
     */
    SOORTPERSOONONDERZOEK_NAAM(10769),
    /**
     * Waarde voor: SoortPersoonOnderzoek - Omschrijving.
     */
    SOORTPERSOONONDERZOEK_OMSCHRIJVING(10774),
    /**
     * Waarde voor: PartijenInOnderzoek - Identiteit.
     */
    PARTIJENINONDERZOEK_IDENTITEIT(10776),
    /**
     * Waarde voor: PartijenInOnderzoek - Standaard.
     */
    PARTIJENINONDERZOEK_STANDAARD(10785),
    /**
     * Waarde voor: PartijenInOnderzoek - ObjectSleutel.
     */
    PARTIJENINONDERZOEK_OBJECTSLEUTEL(10778),
    /**
     * Waarde voor: PartijenInOnderzoek - PartijCode.
     */
    PARTIJENINONDERZOEK_PARTIJCODE(10780),
    /**
     * Waarde voor: PartijenInOnderzoek - Onderzoek.
     */
    PARTIJENINONDERZOEK_ONDERZOEK(10782),
    /**
     * Waarde voor: PartijenInOnderzoek - RolNaam.
     */
    PARTIJENINONDERZOEK_ROLNAAM(10786),
    /**
     * Waarde voor: PartijenInOnderzoek - PartijOnderzoek.
     */
    PARTIJENINONDERZOEK_PARTIJONDERZOEK(10797),
    /**
     * Waarde voor: PartijenInOnderzoek - TijdstipRegistratie.
     */
    PARTIJENINONDERZOEK_TIJDSTIPREGISTRATIE(10798),
    /**
     * Waarde voor: PartijenInOnderzoek - TijdstipVerval.
     */
    PARTIJENINONDERZOEK_TIJDSTIPVERVAL(10799),
    /**
     * Waarde voor: PartijenInOnderzoek - ActieInhoud.
     */
    PARTIJENINONDERZOEK_ACTIEINHOUD(10800),
    /**
     * Waarde voor: PartijenInOnderzoek - ActieVerval.
     */
    PARTIJENINONDERZOEK_ACTIEVERVAL(10801),
    /**
     * Waarde voor: PartijenInOnderzoek - VoorkomenSleutel.
     */
    PARTIJENINONDERZOEK_VOORKOMENSLEUTEL(10830),
    /**
     * Waarde voor: PartijenInOnderzoek - NadereAanduidingVerval.
     */
    PARTIJENINONDERZOEK_NADEREAANDUIDINGVERVAL(11125),
    /**
     * Waarde voor: PartijenInOnderzoek - ActieVervalTbvLeveringMutaties.
     */
    PARTIJENINONDERZOEK_ACTIEVERVALTBVLEVERINGMUTATIES(18838),
    /**
     * Waarde voor: PartijenInOnderzoek - IndicatieVoorkomenTbvLeveringMutaties.
     */
    PARTIJENINONDERZOEK_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18839),
    /**
     * Waarde voor: SoortPartijOnderzoek - Identiteit.
     */
    SOORTPARTIJONDERZOEK_IDENTITEIT(10787),
    /**
     * Waarde voor: SoortPartijOnderzoek - ObjectSleutel.
     */
    SOORTPARTIJONDERZOEK_OBJECTSLEUTEL(10789),
    /**
     * Waarde voor: SoortPartijOnderzoek - Naam.
     */
    SOORTPARTIJONDERZOEK_NAAM(10790),
    /**
     * Waarde voor: SoortPartijOnderzoek - Omschrijving.
     */
    SOORTPARTIJONDERZOEK_OMSCHRIJVING(10791),
    /**
     * Waarde voor: StatusOnderzoek - Identiteit.
     */
    STATUSONDERZOEK_IDENTITEIT(10854),
    /**
     * Waarde voor: StatusOnderzoek - ObjectSleutel.
     */
    STATUSONDERZOEK_OBJECTSLEUTEL(10856),
    /**
     * Waarde voor: StatusOnderzoek - Naam.
     */
    STATUSONDERZOEK_NAAM(10858),
    /**
     * Waarde voor: StatusOnderzoek - Omschrijving.
     */
    STATUSONDERZOEK_OMSCHRIJVING(10859),
    /**
     * Waarde voor: NadereBijhoudingsaard - Identiteit.
     */
    NADEREBIJHOUDINGSAARD_IDENTITEIT(10866),
    /**
     * Waarde voor: NadereBijhoudingsaard - ObjectSleutel.
     */
    NADEREBIJHOUDINGSAARD_OBJECTSLEUTEL(10868),
    /**
     * Waarde voor: NadereBijhoudingsaard - Naam.
     */
    NADEREBIJHOUDINGSAARD_NAAM(10869),
    /**
     * Waarde voor: NadereBijhoudingsaard - Code.
     */
    NADEREBIJHOUDINGSAARD_CODE(10894),
    /**
     * Waarde voor: NadereBijhoudingsaard - Omschrijving.
     */
    NADEREBIJHOUDINGSAARD_OMSCHRIJVING(10896),
    /**
     * Waarde voor: SoortMigratie - Identiteit.
     */
    SOORTMIGRATIE_IDENTITEIT(10873),
    /**
     * Waarde voor: SoortMigratie - ObjectSleutel.
     */
    SOORTMIGRATIE_OBJECTSLEUTEL(10875),
    /**
     * Waarde voor: SoortMigratie - Naam.
     */
    SOORTMIGRATIE_NAAM(10877),
    /**
     * Waarde voor: SoortMigratie - Code.
     */
    SOORTMIGRATIE_CODE(11182),
    /**
     * Waarde voor: GerelateerdeOuder - Identiteit.
     */
    GERELATEERDEOUDER_IDENTITEIT(12826),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap.
     */
    GERELATEERDEOUDER_OUDERSCHAP(12827),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG(12828),
    /**
     * Waarde voor: GerelateerdeOuder - RolCode.
     */
    GERELATEERDEOUDER_ROLCODE(21080),
    /**
     * Waarde voor: GerelateerdeOuder - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_VOORKOMENSLEUTEL(21140),
    /**
     * Waarde voor: GerelateerdeOuder - Betrokkenheid.
     */
    GERELATEERDEOUDER_BETROKKENHEID(21141),
    /**
     * Waarde voor: GerelateerdeOuder - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_TIJDSTIPREGISTRATIE(21142),
    /**
     * Waarde voor: GerelateerdeOuder - TijdstipVerval.
     */
    GERELATEERDEOUDER_TIJDSTIPVERVAL(21143),
    /**
     * Waarde voor: GerelateerdeOuder - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_NADEREAANDUIDINGVERVAL(21144),
    /**
     * Waarde voor: GerelateerdeOuder - ActieInhoud.
     */
    GERELATEERDEOUDER_ACTIEINHOUD(21145),
    /**
     * Waarde voor: GerelateerdeOuder - ActieVerval.
     */
    GERELATEERDEOUDER_ACTIEVERVAL(21146),
    /**
     * Waarde voor: GerelateerdeOuder - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_ACTIEVERVALTBVLEVERINGMUTATIES(21147),
    /**
     * Waarde voor: GerelateerdeOuder - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21148),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_OUDERSCHAP_VOORKOMENSLEUTEL(13871),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - Betrokkenheid.
     */
    GERELATEERDEOUDER_OUDERSCHAP_BETROKKENHEID(13872),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - DatumAanvangGeldigheid.
     */
    GERELATEERDEOUDER_OUDERSCHAP_DATUMAANVANGGELDIGHEID(13873),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - DatumEindeGeldigheid.
     */
    GERELATEERDEOUDER_OUDERSCHAP_DATUMEINDEGELDIGHEID(13874),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_OUDERSCHAP_TIJDSTIPREGISTRATIE(13875),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - TijdstipVerval.
     */
    GERELATEERDEOUDER_OUDERSCHAP_TIJDSTIPVERVAL(13876),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_OUDERSCHAP_NADEREAANDUIDINGVERVAL(13877),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - ActieInhoud.
     */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEINHOUD(14025),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - ActieVerval.
     */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEVERVAL(14026),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - ActieAanpassingGeldigheid.
     */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEAANPASSINGGELDIGHEID(14027),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_OUDERSCHAP_ACTIEVERVALTBVLEVERINGMUTATIES(18926),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_OUDERSCHAP_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18927),
    /**
     * Waarde voor: GerelateerdeOuder - Ouderschap - IndicatieOuderUitWieKindIsGeboren.
     */
    GERELATEERDEOUDER_OUDERSCHAP_INDICATIEOUDERUITWIEKINDISGEBOREN(21253),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - Betrokkenheid.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_BETROKKENHEID(12829),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - DatumAanvangGeldigheid.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID(12830),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - DatumEindeGeldigheid.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID(12831),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE(12832),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_NADEREAANDUIDINGVERVAL(12838),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - IndicatieOuderHeeftGezag.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG(12839),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - TijdstipVerval.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL(13617),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_VOORKOMENSLEUTEL(13808),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - ActieInhoud.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEINHOUD(14028),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - ActieVerval.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEVERVAL(14029),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - ActieAanpassingGeldigheid.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEAANPASSINGGELDIGHEID(14030),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_ACTIEVERVALTBVLEVERINGMUTATIES(18928),
    /**
     * Waarde voor: GerelateerdeOuder - OuderlijkGezag - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18929),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identiteit.
     */
    GERELATEERDEOUDER_PERSOON_IDENTITEIT(12841),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS(12843),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM(12856),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE(12874),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING(12889),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SoortCode.
     */
    GERELATEERDEOUDER_PERSOON_SOORTCODE(14212),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(12844),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(12845),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(12846),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(12847),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(12853),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(12854),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(12855),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13618),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13809),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14031),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14032),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14033),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18930),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18931),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PERSOON(12857),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(12858),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(12859),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(12860),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(12866),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(12867),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(12868),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(12869),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(12870),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(12871),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(12872),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(12873),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13619),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13810),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14034),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14035),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14036),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14254),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18932),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18933),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_PERSOON(12875),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(12876),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(12881),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(12882),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE(12883),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - Datum.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM(12884),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE(12885),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM(12886),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(12887),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(12888),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13620),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13811),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEINHOUD(14037),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEVERVAL(14038),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18934),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18935),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_PERSOON(12890),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(12891),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(12892),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(12893),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(12899),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE(12900),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13621),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13812),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14039),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14040),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14041),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18936),
    /**
     * Waarde voor: GerelateerdeOuder - Persoon - Geslachtsaanduiding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18937),
    /**
     * Waarde voor: GerelateerdeKind - Identiteit.
     */
    GERELATEERDEKIND_IDENTITEIT(12921),
    /**
     * Waarde voor: GerelateerdeKind - RolCode.
     */
    GERELATEERDEKIND_ROLCODE(21081),
    /**
     * Waarde voor: GerelateerdeKind - VoorkomenSleutel.
     */
    GERELATEERDEKIND_VOORKOMENSLEUTEL(21149),
    /**
     * Waarde voor: GerelateerdeKind - Betrokkenheid.
     */
    GERELATEERDEKIND_BETROKKENHEID(21150),
    /**
     * Waarde voor: GerelateerdeKind - TijdstipRegistratie.
     */
    GERELATEERDEKIND_TIJDSTIPREGISTRATIE(21151),
    /**
     * Waarde voor: GerelateerdeKind - TijdstipVerval.
     */
    GERELATEERDEKIND_TIJDSTIPVERVAL(21152),
    /**
     * Waarde voor: GerelateerdeKind - NadereAanduidingVerval.
     */
    GERELATEERDEKIND_NADEREAANDUIDINGVERVAL(21153),
    /**
     * Waarde voor: GerelateerdeKind - ActieInhoud.
     */
    GERELATEERDEKIND_ACTIEINHOUD(21154),
    /**
     * Waarde voor: GerelateerdeKind - ActieVerval.
     */
    GERELATEERDEKIND_ACTIEVERVAL(21155),
    /**
     * Waarde voor: GerelateerdeKind - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEKIND_ACTIEVERVALTBVLEVERINGMUTATIES(21156),
    /**
     * Waarde voor: GerelateerdeKind - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEKIND_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21157),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identiteit.
     */
    GERELATEERDEKIND_PERSOON_IDENTITEIT(12923),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS(12925),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM(12938),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE(12956),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SoortCode.
     */
    GERELATEERDEKIND_PERSOON_SOORTCODE(14213),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_PERSOON(12926),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(12927),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(12928),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(12929),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(12935),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(12936),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(12937),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13636),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13814),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14042),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14043),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14044),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18938),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18939),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PERSOON(12939),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(12940),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(12941),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(12942),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(12948),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(12949),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(12950),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(12951),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(12952),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(12953),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(12954),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(12955),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13637),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13815),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14045),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14046),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14047),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14255),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18940),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18941),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_PERSOON(12957),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(12958),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(12963),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO(12964),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE(12965),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - Datum.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM(12966),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE(12967),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM(12968),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(12969),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(12970),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13638),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13816),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEINHOUD(14048),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEVERVAL(14049),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18942),
    /**
     * Waarde voor: GerelateerdeKind - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEKIND_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18943),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Identiteit.
     */
    GERELATEERDEHUWELIJKSPARTNER_IDENTITEIT(12992),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - RolCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_ROLCODE(21082),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - VoorkomenSleutel.
     */
    GERELATEERDEHUWELIJKSPARTNER_VOORKOMENSLEUTEL(21158),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Betrokkenheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_BETROKKENHEID(21159),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - TijdstipRegistratie.
     */
    GERELATEERDEHUWELIJKSPARTNER_TIJDSTIPREGISTRATIE(21160),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - TijdstipVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_TIJDSTIPVERVAL(21161),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - NadereAanduidingVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_NADEREAANDUIDINGVERVAL(21162),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - ActieInhoud.
     */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEINHOUD(21163),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - ActieVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEVERVAL(21164),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES(21165),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21166),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identiteit.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT(12994),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS(12996),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM(13009),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE(13027),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING(13042),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SoortCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SOORTCODE(14214),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(12997),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(12998),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(12999),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13000),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13006),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13007),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13008),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13653),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13818),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14050),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14051),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14052),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18944),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Identificatienummers -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18945),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON(13010),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13011),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13012),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13013),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13019),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13020),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13021),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13022),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13023),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13024),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13025),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13026),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13654),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13819),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14053),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14054),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14055),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14256),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18946),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18947),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_PERSOON(13028),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13029),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13034),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13035),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13036),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - Datum.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM(13037),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE(13038),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13039),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13040),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13041),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13655),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13820),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD(14056),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL(14057),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18948),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18949),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13043),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13044),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13045),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13046),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13052),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE(13053),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13656),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13821),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14058),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14059),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14060),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18950),
    /**
     * Waarde voor: GerelateerdeHuwelijkspartner - Persoon - Geslachtsaanduiding -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEHUWELIJKSPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18951),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Identiteit.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_IDENTITEIT(13074),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - RolCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_ROLCODE(21083),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - VoorkomenSleutel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_VOORKOMENSLEUTEL(21167),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Betrokkenheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_BETROKKENHEID(21168),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - TijdstipRegistratie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_TIJDSTIPREGISTRATIE(21169),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - TijdstipVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_TIJDSTIPVERVAL(21170),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - NadereAanduidingVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_NADEREAANDUIDINGVERVAL(21171),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - ActieInhoud.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEINHOUD(21172),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - ActieVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEVERVAL(21173),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES(21174),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21175),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identiteit.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTITEIT(13076),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS(13078),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM(13091),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE(13109),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING(13124),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SoortCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SOORTCODE(14215),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(13079),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(13080),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(13081),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13082),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13088),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13089),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13090),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13671),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13823),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14061),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14062),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14063),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18952),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Identificatienummers -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18953),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON(13092),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13093),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13094),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13095),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13101),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13102),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13103),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13104),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13105),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13106),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13107),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13108),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13672),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13824),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14064),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14065),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14066),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14257),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18954),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - SamengesteldeNaam -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18955),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_PERSOON(13110),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13111),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13116),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13117),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13118),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - Datum.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM(13119),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE(13120),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13121),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13122),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13123),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13673),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13825),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD(14067),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL(14068),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18956),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18957),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13125),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13126),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13127),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13128),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13134),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE(13135),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13674),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13826),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14069),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14070),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14071),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18958),
    /**
     * Waarde voor: GerelateerdeGeregistreerdePartner - Persoon - Geslachtsaanduiding -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18959),
    /**
     * Waarde voor: GerelateerdeInstemmer - Identiteit.
     */
    GERELATEERDEINSTEMMER_IDENTITEIT(13156),
    /**
     * Waarde voor: GerelateerdeInstemmer - RolCode.
     */
    GERELATEERDEINSTEMMER_ROLCODE(21084),
    /**
     * Waarde voor: GerelateerdeInstemmer - VoorkomenSleutel.
     */
    GERELATEERDEINSTEMMER_VOORKOMENSLEUTEL(21176),
    /**
     * Waarde voor: GerelateerdeInstemmer - Betrokkenheid.
     */
    GERELATEERDEINSTEMMER_BETROKKENHEID(21177),
    /**
     * Waarde voor: GerelateerdeInstemmer - TijdstipRegistratie.
     */
    GERELATEERDEINSTEMMER_TIJDSTIPREGISTRATIE(21178),
    /**
     * Waarde voor: GerelateerdeInstemmer - TijdstipVerval.
     */
    GERELATEERDEINSTEMMER_TIJDSTIPVERVAL(21179),
    /**
     * Waarde voor: GerelateerdeInstemmer - NadereAanduidingVerval.
     */
    GERELATEERDEINSTEMMER_NADEREAANDUIDINGVERVAL(21180),
    /**
     * Waarde voor: GerelateerdeInstemmer - ActieInhoud.
     */
    GERELATEERDEINSTEMMER_ACTIEINHOUD(21181),
    /**
     * Waarde voor: GerelateerdeInstemmer - ActieVerval.
     */
    GERELATEERDEINSTEMMER_ACTIEVERVAL(21182),
    /**
     * Waarde voor: GerelateerdeInstemmer - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_ACTIEVERVALTBVLEVERINGMUTATIES(21183),
    /**
     * Waarde voor: GerelateerdeInstemmer - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21184),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identiteit.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTITEIT(13158),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS(13160),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM(13173),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE(13191),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING(13206),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SoortCode.
     */
    GERELATEERDEINSTEMMER_PERSOON_SOORTCODE(14216),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(13161),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(13162),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(13163),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13164),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13170),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13171),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13172),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13689),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13828),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14072),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14073),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14074),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18960),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18961),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_PERSOON(13174),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13175),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13176),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13177),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13183),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13184),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13185),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13186),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13187),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13188),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13189),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13190),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13690),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13829),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14075),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14076),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14077),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14258),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18962),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18963),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_PERSOON(13192),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13193),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13198),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13199),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13200),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - Datum.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_DATUM(13201),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_GEMEENTECODE(13202),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13203),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13204),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13205),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13691),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13830),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEINHOUD(14078),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEVERVAL(14079),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18964),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18965),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13207),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13208),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13209),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13210),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13216),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_CODE(13217),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13692),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13831),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14080),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14081),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14082),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18966),
    /**
     * Waarde voor: GerelateerdeInstemmer - Persoon - Geslachtsaanduiding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEINSTEMMER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18967),
    /**
     * Waarde voor: GerelateerdeErkenner - Identiteit.
     */
    GERELATEERDEERKENNER_IDENTITEIT(13238),
    /**
     * Waarde voor: GerelateerdeErkenner - RolCode.
     */
    GERELATEERDEERKENNER_ROLCODE(21085),
    /**
     * Waarde voor: GerelateerdeErkenner - VoorkomenSleutel.
     */
    GERELATEERDEERKENNER_VOORKOMENSLEUTEL(21185),
    /**
     * Waarde voor: GerelateerdeErkenner - Betrokkenheid.
     */
    GERELATEERDEERKENNER_BETROKKENHEID(21186),
    /**
     * Waarde voor: GerelateerdeErkenner - TijdstipRegistratie.
     */
    GERELATEERDEERKENNER_TIJDSTIPREGISTRATIE(21187),
    /**
     * Waarde voor: GerelateerdeErkenner - TijdstipVerval.
     */
    GERELATEERDEERKENNER_TIJDSTIPVERVAL(21188),
    /**
     * Waarde voor: GerelateerdeErkenner - NadereAanduidingVerval.
     */
    GERELATEERDEERKENNER_NADEREAANDUIDINGVERVAL(21189),
    /**
     * Waarde voor: GerelateerdeErkenner - ActieInhoud.
     */
    GERELATEERDEERKENNER_ACTIEINHOUD(21190),
    /**
     * Waarde voor: GerelateerdeErkenner - ActieVerval.
     */
    GERELATEERDEERKENNER_ACTIEVERVAL(21191),
    /**
     * Waarde voor: GerelateerdeErkenner - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_ACTIEVERVALTBVLEVERINGMUTATIES(21192),
    /**
     * Waarde voor: GerelateerdeErkenner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21193),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identiteit.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTITEIT(13240),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS(13242),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM(13255),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE(13273),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING(13288),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SoortCode.
     */
    GERELATEERDEERKENNER_PERSOON_SOORTCODE(14217),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(13243),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(13244),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(13245),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13246),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13252),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13253),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13254),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13707),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13833),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14083),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14084),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14085),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18968),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18969),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_PERSOON(13256),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13257),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13258),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13259),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13265),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13266),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13267),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13268),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13269),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13270),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13271),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13272),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13708),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13834),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14086),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14087),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14088),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14259),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18970),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18971),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - Persoon.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_PERSOON(13274),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13275),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13280),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13281),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13282),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - Datum.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_DATUM(13283),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_GEMEENTECODE(13284),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13285),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13286),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13287),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13709),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13835),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEINHOUD(14089),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEVERVAL(14090),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18972),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18973),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13289),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13290),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13291),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13292),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13298),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_CODE(13299),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13710),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13836),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14091),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14092),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14093),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18974),
    /**
     * Waarde voor: GerelateerdeErkenner - Persoon - Geslachtsaanduiding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDEERKENNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18975),
    /**
     * Waarde voor: GerelateerdeNaamgever - Identiteit.
     */
    GERELATEERDENAAMGEVER_IDENTITEIT(13320),
    /**
     * Waarde voor: GerelateerdeNaamgever - RolCode.
     */
    GERELATEERDENAAMGEVER_ROLCODE(21086),
    /**
     * Waarde voor: GerelateerdeNaamgever - VoorkomenSleutel.
     */
    GERELATEERDENAAMGEVER_VOORKOMENSLEUTEL(21194),
    /**
     * Waarde voor: GerelateerdeNaamgever - Betrokkenheid.
     */
    GERELATEERDENAAMGEVER_BETROKKENHEID(21195),
    /**
     * Waarde voor: GerelateerdeNaamgever - TijdstipRegistratie.
     */
    GERELATEERDENAAMGEVER_TIJDSTIPREGISTRATIE(21196),
    /**
     * Waarde voor: GerelateerdeNaamgever - TijdstipVerval.
     */
    GERELATEERDENAAMGEVER_TIJDSTIPVERVAL(21197),
    /**
     * Waarde voor: GerelateerdeNaamgever - NadereAanduidingVerval.
     */
    GERELATEERDENAAMGEVER_NADEREAANDUIDINGVERVAL(21198),
    /**
     * Waarde voor: GerelateerdeNaamgever - ActieInhoud.
     */
    GERELATEERDENAAMGEVER_ACTIEINHOUD(21199),
    /**
     * Waarde voor: GerelateerdeNaamgever - ActieVerval.
     */
    GERELATEERDENAAMGEVER_ACTIEVERVAL(21200),
    /**
     * Waarde voor: GerelateerdeNaamgever - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_ACTIEVERVALTBVLEVERINGMUTATIES(21201),
    /**
     * Waarde voor: GerelateerdeNaamgever - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21202),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identiteit.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTITEIT(13322),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS(13324),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM(13337),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE(13355),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING(13370),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SoortCode.
     */
    GERELATEERDENAAMGEVER_PERSOON_SOORTCODE(14218),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(13325),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(13326),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(13327),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13328),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13334),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13335),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13336),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13725),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13838),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14094),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14095),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14096),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18976),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Identificatienummers - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18977),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_PERSOON(13338),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13339),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13340),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13341),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13347),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13348),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13349),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13350),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13351),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13352),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13353),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13354),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13726),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13839),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14097),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14098),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14099),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14260),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18978),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18979),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - Persoon.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_PERSOON(13356),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13357),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13362),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13363),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13364),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - Datum.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_DATUM(13365),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_GEMEENTECODE(13366),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13367),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13368),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13369),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13727),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13840),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEINHOUD(14100),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEVERVAL(14101),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18980),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18981),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13371),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13372),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13373),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13374),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13380),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_CODE(13381),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13728),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13841),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14102),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14103),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14104),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18982),
    /**
     * Waarde voor: GerelateerdeNaamgever - Persoon - Geslachtsaanduiding - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMGEVER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18983),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Identiteit.
     */
    GERELATEERDENAAMSKEUZEPARTNER_IDENTITEIT(13402),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - RolCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_ROLCODE(21087),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - VoorkomenSleutel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_VOORKOMENSLEUTEL(21203),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Betrokkenheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_BETROKKENHEID(21204),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - TijdstipRegistratie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_TIJDSTIPREGISTRATIE(21205),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - TijdstipVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_TIJDSTIPVERVAL(21206),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - NadereAanduidingVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_NADEREAANDUIDINGVERVAL(21207),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - ActieInhoud.
     */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEINHOUD(21208),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - ActieVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEVERVAL(21209),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_ACTIEVERVALTBVLEVERINGMUTATIES(21210),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(21211),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identiteit.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTITEIT(13404),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS(13406),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM(13419),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE(13437),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING(13452),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SoortCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SOORTCODE(14219),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - Persoon.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_PERSOON(13407),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMAANVANGGELDIGHEID(13408),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_DATUMEINDEGELDIGHEID(13409),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - TijdstipRegistratie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE(13410),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - NadereAanduidingVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_NADEREAANDUIDINGVERVAL(13416),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - Administratienummer.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER(13417),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - Burgerservicenummer.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER(13418),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - TijdstipVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPVERVAL(13743),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - VoorkomenSleutel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_VOORKOMENSLEUTEL(13843),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - ActieInhoud.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEINHOUD(14105),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - ActieVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVAL(14106),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEAANPASSINGGELDIGHEID(14107),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_ACTIEVERVALTBVLEVERINGMUTATIES(18984),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Identificatienummers -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_IDENTIFICATIENUMMERS_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18985),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - Persoon.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_PERSOON(13420),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID(13421),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID(13422),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - TijdstipRegistratie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE(13423),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - NadereAanduidingVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_NADEREAANDUIDINGVERVAL(13429),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - AdellijkeTitelCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE(13430),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - PredicaatCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_PREDICAATCODE(13431),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - Voornamen.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORNAMEN(13432),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - Geslachtsnaamstam.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM(13433),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - Scheidingsteken.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN(13434),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - Voorvoegsel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL(13435),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - IndicatieNamenreeks.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS(13436),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - TijdstipVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL(13744),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - VoorkomenSleutel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_VOORKOMENSLEUTEL(13844),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - ActieInhoud.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEINHOUD(14108),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - ActieVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVAL(14109),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEAANPASSINGGELDIGHEID(14110),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - IndicatieAfgeleid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID(14261),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_ACTIEVERVALTBVLEVERINGMUTATIES(18986),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - SamengesteldeNaam - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_SAMENGESTELDENAAM_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18987),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - Persoon.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_PERSOON(13438),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - TijdstipRegistratie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE(13439),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - NadereAanduidingVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_NADEREAANDUIDINGVERVAL(13444),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - BuitenlandseRegio.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO(13445),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - LandGebiedCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE(13446),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - Datum.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_DATUM(13447),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - GemeenteCode.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE(13448),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - Woonplaatsnaam.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM(13449),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - BuitenlandsePlaats.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS(13450),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - OmschrijvingLocatie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE(13451),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - TijdstipVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL(13745),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - VoorkomenSleutel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_VOORKOMENSLEUTEL(13845),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - ActieInhoud.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEINHOUD(14111),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - ActieVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEVERVAL(14112),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_ACTIEVERVALTBVLEVERINGMUTATIES(18988),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geboorte - IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GEBOORTE_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18989),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - Persoon.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_PERSOON(13453),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - DatumAanvangGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID(13454),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - DatumEindeGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID(13455),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - TijdstipRegistratie.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE(13456),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - NadereAanduidingVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_NADEREAANDUIDINGVERVAL(13462),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - Code.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_CODE(13463),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - TijdstipVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL(13746),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - VoorkomenSleutel.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_VOORKOMENSLEUTEL(13846),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - ActieInhoud.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEINHOUD(14113),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - ActieVerval.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVAL(14114),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - ActieAanpassingGeldigheid.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEAANPASSINGGELDIGHEID(14115),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding - ActieVervalTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_ACTIEVERVALTBVLEVERINGMUTATIES(18990),
    /**
     * Waarde voor: GerelateerdeNaamskeuzePartner - Persoon - Geslachtsaanduiding -
     * IndicatieVoorkomenTbvLeveringMutaties.
     */
    GERELATEERDENAAMSKEUZEPARTNER_PERSOON_GESLACHTSAANDUIDING_INDICATIEVOORKOMENTBVLEVERINGMUTATIES(18991),
    /**
     * Waarde voor: SoortElementAutorisatie - Identiteit.
     */
    SOORTELEMENTAUTORISATIE_IDENTITEIT(13516),
    /**
     * Waarde voor: SoortElementAutorisatie - ObjectSleutel.
     */
    SOORTELEMENTAUTORISATIE_OBJECTSLEUTEL(13518),
    /**
     * Waarde voor: SoortElementAutorisatie - Naam.
     */
    SOORTELEMENTAUTORISATIE_NAAM(13519),
    /**
     * Waarde voor: SoortElementAutorisatie - Omschrijving.
     */
    SOORTELEMENTAUTORISATIE_OMSCHRIJVING(14007),
    /**
     * Waarde voor: Stelsel - Identiteit.
     */
    STELSEL_IDENTITEIT(21399),
    /**
     * Waarde voor: Stelsel - ObjectSleutel.
     */
    STELSEL_OBJECTSLEUTEL(21401),
    /**
     * Waarde voor: Stelsel - Naam.
     */
    STELSEL_NAAM(21402),
    /**
     * Waarde voor: Koppelvlak - Identiteit.
     */
    KOPPELVLAK_IDENTITEIT(21408),
    /**
     * Waarde voor: Koppelvlak - ObjectSleutel.
     */
    KOPPELVLAK_OBJECTSLEUTEL(21410),
    /**
     * Waarde voor: Koppelvlak - Naam.
     */
    KOPPELVLAK_NAAM(21411),
    /**
     * Waarde voor: Koppelvlak - StelselNaam.
     */
    KOPPELVLAK_STELSELNAAM(21412);

    private final Integer id;
    private final String naam;
    private final String elementNaam;
    private final SoortElement soort;
    private final Integer groepId;
    private final Integer objectTypeId;
    private final Integer hisDbObjectId;
    private final Integer volgnummer;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param id Id voor ElementEnum
     */
    private ElementEnum(final Integer id) {
        this.id = id;
        naam = Helper.HELPER.geefNaamVoorElementId(id);
        elementNaam = Helper.HELPER.geefElementNaamVoorElementId(id);
        soort = Helper.HELPER.geefSoortVoorElementId(id);
        groepId = Helper.HELPER.geefGroepIdVoorElementId(id);
        objectTypeId = Helper.HELPER.geefObjectTypeIdVoorElementId(id);
        hisDbObjectId = Helper.HELPER.geefHisDbObjectIdVoorElementId(id);
        volgnummer = Helper.HELPER.geefVolgnummerVoorElementId(id);
    }

    /**
     * Retourneert id van ElementEnum.
     *
     * @return id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retourneert Naam van Element.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert Element naam van Element.
     *
     * @return Element naam.
     */
    public String getElementNaam() {
        return elementNaam;
    }

    /**
     * Retourneert Soort van Element.
     *
     * @return Soort.
     */
    public SoortElement getSoort() {
        return soort;
    }

    /**
     * Retourneert Groep van Element.
     *
     * @return Groep.
     */
    public Integer getGroepId() {
        return groepId;
    }

    /**
     * Retourneert Objecttype van Element.
     *
     * @return Objecttype.
     */
    public Integer getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Retourneert Historie database object van Element.
     *
     * @return Historie database object.
     */
    public Integer getHisDbObjectId() {
        return hisDbObjectId;
    }

    /**
     * Retourneert Volgnummer van Element.
     *
     * @return Volgnummer.
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Geef de groep waar dit element toe behoort.
     *
     * @return de groep element enum, null indien dit element niet tot een groep behoort
     */
    public ElementEnum getGroep() {
        if (groepId == null) {
            return null;
        }
        for (final ElementEnum element : ElementEnum.values()) {
            if (element.id.equals(groepId)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Groep niet gevonden.");
    }

    /**
     * Geef het objecttype waar dit element toe behoort.
     *
     * @return objecttype element enum, null indien dit element niet tot een objecttype behoort
     */
    public ElementEnum getObjectType() {
        if (objectTypeId == null) {
            return null;
        }
        for (final ElementEnum element : ElementEnum.values()) {
            if (element.id.equals(objectTypeId)) {
                return element;
            }
        }
        throw new IllegalArgumentException("Objecttype niet gevonden.");
    }

    /** Helper voor de element enum om waarden op te halen uit de gegenereerde properties bestanden. */
    private final static class Helper {
        private final static Helper HELPER = new Helper();
        private final Properties naamProperties = new Properties();
        private final Properties elementNaamProperties = new Properties();
        private final Properties soortProperties = new Properties();
        private final Properties groepProperties = new Properties();
        private final Properties objectProperties = new Properties();
        private final Properties hisDbObjectProperties = new Properties();
        private final Properties volgnummerProperties = new Properties();
        {
            try (final InputStream isNaam = ElementEnum.class.getResourceAsStream("/gegevens/element.naam.properties");
                final InputStream isElementNaam = ElementEnum.class.getResourceAsStream("/gegevens/element.elementnaam.properties");
                final InputStream isSoort = ElementEnum.class.getResourceAsStream("/gegevens/element.soort.properties");
                final InputStream isGroep = ElementEnum.class.getResourceAsStream("/gegevens/element.groep.properties");
                final InputStream isObject = ElementEnum.class.getResourceAsStream("/gegevens/element.object.properties");
                final InputStream isHisDbObject = ElementEnum.class.getResourceAsStream("/gegevens/element.hisdbobject.properties");
                final InputStream isVolgnummer = ElementEnum.class.getResourceAsStream("/gegevens/element.volgnummer.properties")) {
                naamProperties.load(isNaam);
                elementNaamProperties.load(isElementNaam);
                soortProperties.load(isSoort);
                groepProperties.load(isGroep);
                objectProperties.load(isObject);
                hisDbObjectProperties.load(isHisDbObject);
                volgnummerProperties.load(isVolgnummer);
            } catch (final IOException e) {
                throw new IllegalArgumentException("Kan element properties niet laden.", e);
            }
        }

        /**
         * Geef de naam voor het element.
         *
         * @param id id voor het element
         * @return naam voor het element
         */
        public String geefNaamVoorElementId(final Integer id) {
            return naamProperties.getProperty(id.toString());
        }

        /**
         * Geef de elementnaam voor het element.
         *
         * @param id id voor het element
         * @return elementnaam voor het element
         */
        public String geefElementNaamVoorElementId(final Integer id) {
            return elementNaamProperties.getProperty(id.toString());
        }

        /**
         * Geef de soort voor het element.
         *
         * @param id id voor het element
         * @return soort voor het element
         */
        public SoortElement geefSoortVoorElementId(final Integer id) {
            final String value = soortProperties.getProperty(id.toString());
            return value == null ? null : SoortElement.valueOf(value);
        }

        /**
         * Geef de groep voor het element.
         *
         * @param id id voor het element
         * @return groep voor het element
         */
        public Integer geefGroepIdVoorElementId(final Integer id) {
            final String value = groepProperties.getProperty(id.toString());
            return value == null ? null : Integer.valueOf(value);
        }

        /**
         * Geef de object voor het element.
         *
         * @param id id voor het element
         * @return object voor het element
         */
        public Integer geefObjectTypeIdVoorElementId(final Integer id) {
            final String value = objectProperties.getProperty(id.toString());
            return value == null ? null : Integer.valueOf(value);
        }

        /**
         * Geef de his db object voor het element.
         *
         * @param id id voor het element
         * @return his db object voor het element
         */
        public Integer geefHisDbObjectIdVoorElementId(final Integer id) {
            final String value = hisDbObjectProperties.getProperty(id.toString());
            return value == null ? null : Integer.valueOf(value);
        }

        /**
         * Geef het volgnummer voor het element.
         *
         * @param id id voor het element
         * @return volgnummer voor het element
         */
        public Integer geefVolgnummerVoorElementId(final Integer id) {
            final String value = volgnummerProperties.getProperty(id.toString());
            return value == null ? null : Integer.valueOf(value);
        }
    }

}
