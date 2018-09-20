/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols;

import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.symbols.solvers.AttributeGetter;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Opsomming van alle attributen zoals die in het BMR voorkomen en zoals die gebruikt kunnen worden in expressies.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public enum ExpressieAttribuut {

    /**
     * Attribuut PERSOON_SOORT. BMR-attribuut 'Soort' van objecttype 'Persoon'.
     */
    PERSOON_SOORT("soort", false, ExpressieType.STRING, ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSoortGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING. BMR-attribuut 'Tijdstip laatste wijziging' van
     * objecttype 'Persoon'.
     */
    PERSOON_ADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING("administratief.tijdstip_laatste_wijziging", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefTijdstipLaatsteWijzigingGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_SORTEERVOLGORDE. BMR-attribuut 'Sorteervolgorde' van objecttype 'Persoon'.
     */
    PERSOON_ADMINISTRATIEF_SORTEERVOLGORDE("administratief.sorteervolgorde", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefSorteervolgordeGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_ONVERWERKT_BIJHOUDINGSVOORSTEL_NIETINGEZETENE_AANWEZIG. BMR-attribuut
     * 'Onverwerkt bijhoudingsvoorstel niet-ingezetene aanwezig?' van objecttype 'Persoon'.
     */
    PERSOON_ADMINISTRATIEF_ONVERWERKT_BIJHOUDINGSVOORSTEL_NIETINGEZETENE_AANWEZIG("administratief.onverwerkt_bijhoudingsvoorstel_nietingezetene_aanwezig",
            false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefOnverwerktBijhoudingsvoorstelNietingezeteneAanwezigGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING_GBASYSTEMATIEK. BMR-attribuut 'Tijdstip laatste
     * wijziging GBA-systematiek' van objecttype 'Persoon'.
     */
    PERSOON_ADMINISTRATIEF_TIJDSTIP_LAATSTE_WIJZIGING_GBASYSTEMATIEK("administratief.tijdstip_laatste_wijziging_gbasystematiek", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefTijdstipLaatsteWijzigingGbasystematiekGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Persoon Afgeleid administratief'.
     */
    PERSOON_ADMINISTRATIEF_DATUM_TIJD_REGISTRATIE("administratief.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Afgeleid administratief'.
     */
    PERSOON_ADMINISTRATIEF_DATUM_TIJD_VERVAL("administratief.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_SOORT("administratief.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_PARTIJ("administratief.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("administratief.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGINHOUD_DATUM_ONTLENING("administratief.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_SOORT("administratief.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_PARTIJ("administratief.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("administratief.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_ADMINISTRATIEF_VERANTWOORDINGVERVAL_DATUM_ONTLENING("administratief.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdministratiefVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER. BMR-attribuut 'Burgerservicenummer' van objecttype
     * 'Persoon'.
     */
    PERSOON_IDENTIFICATIENUMMERS_BURGERSERVICENUMMER("identificatienummers.burgerservicenummer", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersBurgerservicenummerGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER. BMR-attribuut 'Administratienummer' van objecttype
     * 'Persoon'.
     */
    PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER("identificatienummers.administratienummer", false, ExpressieType.GROOT_GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersAdministratienummerGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van
     * objecttype 'His Persoon Identificatienummers'.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUM_AANVANG_GELDIGHEID("identificatienummers.datum_aanvang_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van
     * objecttype 'His Persoon Identificatienummers'.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUM_EINDE_GELDIGHEID("identificatienummers.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon Identificatienummers'.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUM_TIJD_REGISTRATIE("identificatienummers.datum_tijd_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon Identificatienummers'.
     */
    PERSOON_IDENTIFICATIENUMMERS_DATUM_TIJD_VERVAL("identificatienummers.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_SOORT("identificatienummers.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_PARTIJ("identificatienummers.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("identificatienummers.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGINHOUD_DATUM_ONTLENING("identificatienummers.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_SOORT("identificatienummers.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_PARTIJ("identificatienummers.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("identificatienummers.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGVERVAL_DATUM_ONTLENING("identificatienummers.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van
     * objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("identificatienummers.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van
     * objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("identificatienummers.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut
     * 'Tijdstip registratie' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "identificatienummers.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOON_IDENTIFICATIENUMMERS_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "identificatienummers.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIdentificatienummersVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_AFGELEID. BMR-attribuut 'Afgeleid?' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_AFGELEID("samengestelde_naam.afgeleid", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamAfgeleidGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_NAMENREEKS. BMR-attribuut 'Namenreeks?' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_NAMENREEKS("samengestelde_naam.namenreeks", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamNamenreeksGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_PREDICAAT. BMR-attribuut 'Predicaat' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_PREDICAAT("samengestelde_naam.predicaat", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamPredicaatGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VOORNAMEN. BMR-attribuut 'Voornamen' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VOORNAMEN("samengestelde_naam.voornamen", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVoornamenGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_ADELLIJKE_TITEL. BMR-attribuut 'Adellijke titel' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_ADELLIJKE_TITEL("samengestelde_naam.adellijke_titel", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VOORVOEGSEL. BMR-attribuut 'Voorvoegsel' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VOORVOEGSEL("samengestelde_naam.voorvoegsel", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVoorvoegselGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_SCHEIDINGSTEKEN. BMR-attribuut 'Scheidingsteken' van objecttype 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_SCHEIDINGSTEKEN("samengestelde_naam.scheidingsteken", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamScheidingstekenGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_GESLACHTSNAAMSTAM. BMR-attribuut 'Geslachtsnaamstam' van objecttype
     * 'Persoon'.
     */
    PERSOON_SAMENGESTELDE_NAAM_GESLACHTSNAAMSTAM("samengestelde_naam.geslachtsnaamstam", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamGeslachtsnaamstamGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van
     * objecttype 'His Persoon Samengestelde naam'.
     */
    PERSOON_SAMENGESTELDE_NAAM_DATUM_AANVANG_GELDIGHEID("samengestelde_naam.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van
     * objecttype 'His Persoon Samengestelde naam'.
     */
    PERSOON_SAMENGESTELDE_NAAM_DATUM_EINDE_GELDIGHEID("samengestelde_naam.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon Samengestelde naam'.
     */
    PERSOON_SAMENGESTELDE_NAAM_DATUM_TIJD_REGISTRATIE("samengestelde_naam.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon Samengestelde naam'.
     */
    PERSOON_SAMENGESTELDE_NAAM_DATUM_TIJD_VERVAL("samengestelde_naam.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_SOORT("samengestelde_naam.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_PARTIJ("samengestelde_naam.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("samengestelde_naam.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGINHOUD_DATUM_ONTLENING("samengestelde_naam.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_SOORT("samengestelde_naam.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_PARTIJ("samengestelde_naam.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("samengestelde_naam.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGVERVAL_DATUM_ONTLENING("samengestelde_naam.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van
     * objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("samengestelde_naam.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van
     * objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("samengestelde_naam.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut
     * 'Tijdstip registratie' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "samengestelde_naam.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOON_SAMENGESTELDE_NAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("samengestelde_naam.verantwoordingAanpassingGeldigheid.datum_ontlening",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonSamengesteldeNaamVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_DATUM. BMR-attribuut 'Datum geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_DATUM("geboorte.datum", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteDatumGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_GEMEENTE. BMR-attribuut 'Gemeente geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_GEMEENTE("geboorte.gemeente", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteGemeenteGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_WOONPLAATSNAAM. BMR-attribuut 'Woonplaatsnaam geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_WOONPLAATSNAAM("geboorte.woonplaatsnaam", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteWoonplaatsnaamGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_BUITENLANDSE_PLAATS. BMR-attribuut 'Buitenlandse plaats geboorte' van objecttype
     * 'Persoon'.
     */
    PERSOON_GEBOORTE_BUITENLANDSE_PLAATS("geboorte.buitenlandse_plaats", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteBuitenlandsePlaatsGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_BUITENLANDSE_REGIO. BMR-attribuut 'Buitenlandse regio geboorte' van objecttype
     * 'Persoon'.
     */
    PERSOON_GEBOORTE_BUITENLANDSE_REGIO("geboorte.buitenlandse_regio", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteBuitenlandseRegioGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_OMSCHRIJVING_LOCATIE. BMR-attribuut 'Omschrijving locatie geboorte' van objecttype
     * 'Persoon'.
     */
    PERSOON_GEBOORTE_OMSCHRIJVING_LOCATIE("geboorte.omschrijving_locatie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteOmschrijvingLocatieGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_LAND_GEBIED. BMR-attribuut 'Land/gebied geboorte' van objecttype 'Persoon'.
     */
    PERSOON_GEBOORTE_LAND_GEBIED("geboorte.land_gebied", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteLandGebiedGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Geboorte'.
     */
    PERSOON_GEBOORTE_DATUM_TIJD_REGISTRATIE("geboorte.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Geboorte'.
     */
    PERSOON_GEBOORTE_DATUM_TIJD_VERVAL("geboorte.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_SOORT("geboorte.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_PARTIJ("geboorte.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("geboorte.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGINHOUD_DATUM_ONTLENING("geboorte.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_SOORT("geboorte.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_PARTIJ("geboorte.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("geboorte.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_GEBOORTE_VERANTWOORDINGVERVAL_DATUM_ONTLENING("geboorte.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeboorteVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_GESLACHTSAANDUIDING. BMR-attribuut 'Geslachtsaanduiding' van objecttype
     * 'Persoon'.
     */
    PERSOON_GESLACHTSAANDUIDING_GESLACHTSAANDUIDING("geslachtsaanduiding.geslachtsaanduiding", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingGeslachtsaanduidingGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van
     * objecttype 'His Persoon Geslachtsaanduiding'.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUM_AANVANG_GELDIGHEID("geslachtsaanduiding.datum_aanvang_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van
     * objecttype 'His Persoon Geslachtsaanduiding'.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUM_EINDE_GELDIGHEID("geslachtsaanduiding.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon Geslachtsaanduiding'.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUM_TIJD_REGISTRATIE("geslachtsaanduiding.datum_tijd_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon Geslachtsaanduiding'.
     */
    PERSOON_GESLACHTSAANDUIDING_DATUM_TIJD_VERVAL("geslachtsaanduiding.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_SOORT("geslachtsaanduiding.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_PARTIJ("geslachtsaanduiding.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("geslachtsaanduiding.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("geslachtsaanduiding.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_SOORT("geslachtsaanduiding.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_PARTIJ("geslachtsaanduiding.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("geslachtsaanduiding.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("geslachtsaanduiding.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van
     * objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("geslachtsaanduiding.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van
     * objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("geslachtsaanduiding.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut
     * 'Tijdstip registratie' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "geslachtsaanduiding.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOON_GESLACHTSAANDUIDING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "geslachtsaanduiding.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsaanduidingVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_DATUM. BMR-attribuut 'Datum inschrijving' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING_DATUM("inschrijving.datum", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingDatumGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERSIENUMMER. BMR-attribuut 'Versienummer' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING_VERSIENUMMER("inschrijving.versienummer", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVersienummerGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL. BMR-attribuut 'Datumtijdstempel' van objecttype 'Persoon'.
     */
    PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL("inschrijving.datumtijdstempel", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingDatumtijdstempelGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Inschrijving'.
     */
    PERSOON_INSCHRIJVING_DATUM_TIJD_REGISTRATIE("inschrijving.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Inschrijving'.
     */
    PERSOON_INSCHRIJVING_DATUM_TIJD_VERVAL("inschrijving.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_SOORT("inschrijving.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_PARTIJ("inschrijving.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("inschrijving.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("inschrijving.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_SOORT("inschrijving.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_PARTIJ("inschrijving.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("inschrijving.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_INSCHRIJVING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("inschrijving.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonInschrijvingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VORIGE_BURGERSERVICENUMMER. BMR-attribuut 'Vorige burgerservicenummer' van
     * objecttype 'Persoon'.
     */
    PERSOON_NUMMERVERWIJZING_VORIGE_BURGERSERVICENUMMER("nummerverwijzing.vorige_burgerservicenummer", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVorigeBurgerservicenummerGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VOLGENDE_BURGERSERVICENUMMER. BMR-attribuut 'Volgende burgerservicenummer' van
     * objecttype 'Persoon'.
     */
    PERSOON_NUMMERVERWIJZING_VOLGENDE_BURGERSERVICENUMMER("nummerverwijzing.volgende_burgerservicenummer", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVolgendeBurgerservicenummerGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VORIGE_ADMINISTRATIENUMMER. BMR-attribuut 'Vorige administratienummer' van
     * objecttype 'Persoon'.
     */
    PERSOON_NUMMERVERWIJZING_VORIGE_ADMINISTRATIENUMMER("nummerverwijzing.vorige_administratienummer", false, ExpressieType.GROOT_GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVorigeAdministratienummerGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VOLGENDE_ADMINISTRATIENUMMER. BMR-attribuut 'Volgende administratienummer' van
     * objecttype 'Persoon'.
     */
    PERSOON_NUMMERVERWIJZING_VOLGENDE_ADMINISTRATIENUMMER("nummerverwijzing.volgende_administratienummer", false, ExpressieType.GROOT_GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVolgendeAdministratienummerGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van
     * objecttype 'His Persoon Nummerverwijzing'.
     */
    PERSOON_NUMMERVERWIJZING_DATUM_AANVANG_GELDIGHEID("nummerverwijzing.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype
     * 'His Persoon Nummerverwijzing'.
     */
    PERSOON_NUMMERVERWIJZING_DATUM_EINDE_GELDIGHEID("nummerverwijzing.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Persoon Nummerverwijzing'.
     */
    PERSOON_NUMMERVERWIJZING_DATUM_TIJD_REGISTRATIE("nummerverwijzing.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon Nummerverwijzing'.
     */
    PERSOON_NUMMERVERWIJZING_DATUM_TIJD_VERVAL("nummerverwijzing.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_SOORT("nummerverwijzing.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_PARTIJ("nummerverwijzing.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("nummerverwijzing.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("nummerverwijzing.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_SOORT("nummerverwijzing.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_PARTIJ("nummerverwijzing.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("nummerverwijzing.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("nummerverwijzing.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("nummerverwijzing.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van
     * objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("nummerverwijzing.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut
     * 'Tijdstip registratie' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "nummerverwijzing.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOON_NUMMERVERWIJZING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("nummerverwijzing.verantwoordingAanpassingGeldigheid.datum_ontlening",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNummerverwijzingVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_BIJHOUDINGSPARTIJ. BMR-attribuut 'Bijhoudingspartij' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDING_BIJHOUDINGSPARTIJ("bijhouding.bijhoudingspartij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingBijhoudingspartijGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_BIJHOUDINGSAARD. BMR-attribuut 'Bijhoudingsaard' van objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDING_BIJHOUDINGSAARD("bijhouding.bijhoudingsaard", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingBijhoudingsaardGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_NADERE_BIJHOUDINGSAARD. BMR-attribuut 'Nadere bijhoudingsaard' van objecttype
     * 'Persoon'.
     */
    PERSOON_BIJHOUDING_NADERE_BIJHOUDINGSAARD("bijhouding.nadere_bijhoudingsaard", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingNadereBijhoudingsaardGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_ONVERWERKT_DOCUMENT_AANWEZIG. BMR-attribuut 'Onverwerkt document aanwezig?' van
     * objecttype 'Persoon'.
     */
    PERSOON_BIJHOUDING_ONVERWERKT_DOCUMENT_AANWEZIG("bijhouding.onverwerkt_document_aanwezig", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingOnverwerktDocumentAanwezigGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype
     * 'His Persoon Bijhouding'.
     */
    PERSOON_BIJHOUDING_DATUM_AANVANG_GELDIGHEID("bijhouding.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His
     * Persoon Bijhouding'.
     */
    PERSOON_BIJHOUDING_DATUM_EINDE_GELDIGHEID("bijhouding.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Bijhouding'.
     */
    PERSOON_BIJHOUDING_DATUM_TIJD_REGISTRATIE("bijhouding.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Bijhouding'.
     */
    PERSOON_BIJHOUDING_DATUM_TIJD_VERVAL("bijhouding.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_SOORT("bijhouding.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_PARTIJ("bijhouding.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("bijhouding.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("bijhouding.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_SOORT("bijhouding.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_PARTIJ("bijhouding.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("bijhouding.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("bijhouding.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("bijhouding.verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("bijhouding.verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("bijhouding.verantwoordingAanpassingGeldigheid.tijdstip_registratie",
            false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening'
     * van objecttype 'Actie'.
     */
    PERSOON_BIJHOUDING_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("bijhouding.verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBijhoudingVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_DATUM. BMR-attribuut 'Datum overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_DATUM("overlijden.datum", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenDatumGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_GEMEENTE. BMR-attribuut 'Gemeente overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_GEMEENTE("overlijden.gemeente", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenGemeenteGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_WOONPLAATSNAAM. BMR-attribuut 'Woonplaatsnaam overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_WOONPLAATSNAAM("overlijden.woonplaatsnaam", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenWoonplaatsnaamGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_BUITENLANDSE_PLAATS. BMR-attribuut 'Buitenlandse plaats overlijden' van objecttype
     * 'Persoon'.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSE_PLAATS("overlijden.buitenlandse_plaats", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenBuitenlandsePlaatsGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_BUITENLANDSE_REGIO. BMR-attribuut 'Buitenlandse regio overlijden' van objecttype
     * 'Persoon'.
     */
    PERSOON_OVERLIJDEN_BUITENLANDSE_REGIO("overlijden.buitenlandse_regio", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenBuitenlandseRegioGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_OMSCHRIJVING_LOCATIE. BMR-attribuut 'Omschrijving locatie overlijden' van objecttype
     * 'Persoon'.
     */
    PERSOON_OVERLIJDEN_OMSCHRIJVING_LOCATIE("overlijden.omschrijving_locatie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenOmschrijvingLocatieGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_LAND_GEBIED. BMR-attribuut 'Land/gebied overlijden' van objecttype 'Persoon'.
     */
    PERSOON_OVERLIJDEN_LAND_GEBIED("overlijden.land_gebied", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenLandGebiedGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Overlijden'.
     */
    PERSOON_OVERLIJDEN_DATUM_TIJD_REGISTRATIE("overlijden.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Overlijden'.
     */
    PERSOON_OVERLIJDEN_DATUM_TIJD_VERVAL("overlijden.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_SOORT("overlijden.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_PARTIJ("overlijden.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("overlijden.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGINHOUD_DATUM_ONTLENING("overlijden.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_SOORT("overlijden.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_PARTIJ("overlijden.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("overlijden.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_OVERLIJDEN_VERANTWOORDINGVERVAL_DATUM_ONTLENING("overlijden.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonOverlijdenVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_NAAMGEBRUIK. BMR-attribuut 'Naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_NAAMGEBRUIK("naamgebruik.naamgebruik", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikNaamgebruikGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_AFGELEID. BMR-attribuut 'Naamgebruik afgeleid?' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_AFGELEID("naamgebruik.afgeleid", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikAfgeleidGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_PREDICAAT. BMR-attribuut 'Predicaat naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_PREDICAAT("naamgebruik.predicaat", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikPredicaatGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VOORNAMEN. BMR-attribuut 'Voornamen naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_VOORNAMEN("naamgebruik.voornamen", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVoornamenGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_ADELLIJKE_TITEL. BMR-attribuut 'Adellijke titel naamgebruik' van objecttype
     * 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_ADELLIJKE_TITEL("naamgebruik.adellijke_titel", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VOORVOEGSEL. BMR-attribuut 'Voorvoegsel naamgebruik' van objecttype 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_VOORVOEGSEL("naamgebruik.voorvoegsel", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVoorvoegselGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN. BMR-attribuut 'Scheidingsteken naamgebruik' van objecttype
     * 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN("naamgebruik.scheidingsteken", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikScheidingstekenGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM. BMR-attribuut 'Geslachtsnaamstam naamgebruik' van objecttype
     * 'Persoon'.
     */
    PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM("naamgebruik.geslachtsnaamstam", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikGeslachtsnaamstamGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Naamgebruik'.
     */
    PERSOON_NAAMGEBRUIK_DATUM_TIJD_REGISTRATIE("naamgebruik.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Naamgebruik'.
     */
    PERSOON_NAAMGEBRUIK_DATUM_TIJD_VERVAL("naamgebruik.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_SOORT("naamgebruik.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_PARTIJ("naamgebruik.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("naamgebruik.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGINHOUD_DATUM_ONTLENING("naamgebruik.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_SOORT("naamgebruik.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_PARTIJ("naamgebruik.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("naamgebruik.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_NAAMGEBRUIK_VERANTWOORDINGVERVAL_DATUM_ONTLENING("naamgebruik.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNaamgebruikVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_SOORT. BMR-attribuut 'Soort migratie' van objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_SOORT("migratie.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieSoortGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_REDEN_WIJZIGING. BMR-attribuut 'Reden wijziging migratie' van objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_REDEN_WIJZIGING("migratie.reden_wijziging", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieRedenWijzigingGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_AANGEVER. BMR-attribuut 'Aangever migratie' van objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_AANGEVER("migratie.aangever", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieAangeverGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_LAND_GEBIED. BMR-attribuut 'Land/gebied migratie' van objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_LAND_GEBIED("migratie.land_gebied", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieLandGebiedGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_1. BMR-attribuut 'Buitenlands adres regel 1 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_1("migratie.buitenlands_adres_regel_1", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel1Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_2. BMR-attribuut 'Buitenlands adres regel 2 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_2("migratie.buitenlands_adres_regel_2", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel2Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_3. BMR-attribuut 'Buitenlands adres regel 3 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_3("migratie.buitenlands_adres_regel_3", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel3Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_4. BMR-attribuut 'Buitenlands adres regel 4 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_4("migratie.buitenlands_adres_regel_4", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel4Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_5. BMR-attribuut 'Buitenlands adres regel 5 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_5("migratie.buitenlands_adres_regel_5", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel5Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_6. BMR-attribuut 'Buitenlands adres regel 6 migratie' van
     * objecttype 'Persoon'.
     */
    PERSOON_MIGRATIE_BUITENLANDS_ADRES_REGEL_6("migratie.buitenlands_adres_regel_6", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieBuitenlandsAdresRegel6Getter()),
    /**
     * Attribuut PERSOON_MIGRATIE_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype 'His
     * Persoon Migratie'.
     */
    PERSOON_MIGRATIE_DATUM_AANVANG_GELDIGHEID("migratie.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His
     * Persoon Migratie'.
     */
    PERSOON_MIGRATIE_DATUM_EINDE_GELDIGHEID("migratie.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon Migratie'.
     */
    PERSOON_MIGRATIE_DATUM_TIJD_REGISTRATIE("migratie.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Migratie'.
     */
    PERSOON_MIGRATIE_DATUM_TIJD_VERVAL("migratie.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_SOORT("migratie.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_PARTIJ("migratie.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("migratie.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING("migratie.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_SOORT("migratie.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_PARTIJ("migratie.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("migratie.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING("migratie.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("migratie.verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("migratie.verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("migratie.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening'
     * van objecttype 'Actie'.
     */
    PERSOON_MIGRATIE_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("migratie.verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonMigratieVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_AANDUIDING. BMR-attribuut 'Aanduiding verblijfsrecht' van objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSRECHT_AANDUIDING("verblijfsrecht.aanduiding", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtAanduidingGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_DATUM_AANVANG. BMR-attribuut 'Datum aanvang verblijfsrecht' van objecttype
     * 'Persoon'.
     */
    PERSOON_VERBLIJFSRECHT_DATUM_AANVANG("verblijfsrecht.datum_aanvang", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtDatumAanvangGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_DATUM_MEDEDELING. BMR-attribuut 'Datum mededeling verblijfsrecht' van objecttype
     * 'Persoon'.
     */
    PERSOON_VERBLIJFSRECHT_DATUM_MEDEDELING("verblijfsrecht.datum_mededeling", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtDatumMededelingGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_DATUM_VOORZIEN_EINDE. BMR-attribuut 'Datum voorzien einde verblijfsrecht' van
     * objecttype 'Persoon'.
     */
    PERSOON_VERBLIJFSRECHT_DATUM_VOORZIEN_EINDE("verblijfsrecht.datum_voorzien_einde", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtDatumVoorzienEindeGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Persoon Verblijfsrecht'.
     */
    PERSOON_VERBLIJFSRECHT_DATUM_TIJD_REGISTRATIE("verblijfsrecht.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Verblijfsrecht'.
     */
    PERSOON_VERBLIJFSRECHT_DATUM_TIJD_VERVAL("verblijfsrecht.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_SOORT("verblijfsrecht.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_PARTIJ("verblijfsrecht.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verblijfsrecht.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verblijfsrecht.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_SOORT("verblijfsrecht.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_PARTIJ("verblijfsrecht.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verblijfsrecht.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_VERBLIJFSRECHT_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verblijfsrecht.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerblijfsrechtVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_UITSLUITING_KIESRECHT. BMR-attribuut 'Uitsluiting kiesrecht?' van
     * objecttype 'Persoon'.
     */
    PERSOON_UITSLUITING_KIESRECHT_UITSLUITING_KIESRECHT("uitsluiting_kiesrecht.uitsluiting_kiesrecht", false, ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtUitsluitingKiesrechtGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT. BMR-attribuut 'Datum voorzien
     * einde uitsluiting kiesrecht' van objecttype 'Persoon'.
     */
    PERSOON_UITSLUITING_KIESRECHT_DATUM_VOORZIEN_EINDE_UITSLUITING_KIESRECHT("uitsluiting_kiesrecht.datum_voorzien_einde_uitsluiting_kiesrecht", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtDatumVoorzienEindeUitsluitingKiesrechtGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon Uitsluiting kiesrecht'.
     */
    PERSOON_UITSLUITING_KIESRECHT_DATUM_TIJD_REGISTRATIE("uitsluiting_kiesrecht.datum_tijd_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon Uitsluiting kiesrecht'.
     */
    PERSOON_UITSLUITING_KIESRECHT_DATUM_TIJD_VERVAL("uitsluiting_kiesrecht.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_SOORT("uitsluiting_kiesrecht.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_PARTIJ("uitsluiting_kiesrecht.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("uitsluiting_kiesrecht.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGINHOUD_DATUM_ONTLENING("uitsluiting_kiesrecht.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_SOORT("uitsluiting_kiesrecht.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_PARTIJ("uitsluiting_kiesrecht.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("uitsluiting_kiesrecht.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_UITSLUITING_KIESRECHT_VERANTWOORDINGVERVAL_DATUM_ONTLENING("uitsluiting_kiesrecht.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonUitsluitingKiesrechtVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_DEELNAME_EU_VERKIEZINGEN. BMR-attribuut 'Deelname EU verkiezingen?'
     * van objecttype 'Persoon'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_DEELNAME_EU_VERKIEZINGEN("deelname_eu_verkiezingen.deelname_eu_verkiezingen", false, ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenDeelnameEuVerkiezingenGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN. BMR-attribuut
     * 'Datum aanleiding aanpassing deelname EU verkiezingen' van objecttype 'Persoon'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_AANLEIDING_AANPASSING_DEELNAME_EU_VERKIEZINGEN(
            "deelname_eu_verkiezingen.datum_aanleiding_aanpassing_deelname_eu_verkiezingen", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenDatumAanleidingAanpassingDeelnameEuVerkiezingenGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN. BMR-attribuut 'Datum
     * voorzien einde uitsluiting EU verkiezingen' van objecttype 'Persoon'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_VOORZIEN_EINDE_UITSLUITING_EU_VERKIEZINGEN(
            "deelname_eu_verkiezingen.datum_voorzien_einde_uitsluiting_eu_verkiezingen", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenDatumVoorzienEindeUitsluitingEuVerkiezingenGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon Deelname EU verkiezingen'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_TIJD_REGISTRATIE("deelname_eu_verkiezingen.datum_tijd_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype
     * 'His Persoon Deelname EU verkiezingen'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_DATUM_TIJD_VERVAL("deelname_eu_verkiezingen.datum_tijd_verval", false, ExpressieType.DATUMTIJD,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_SOORT("deelname_eu_verkiezingen.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_PARTIJ("deelname_eu_verkiezingen.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("deelname_eu_verkiezingen.verantwoordingInhoud.tijdstip_registratie",
            false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening'
     * van objecttype 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGINHOUD_DATUM_ONTLENING("deelname_eu_verkiezingen.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_SOORT("deelname_eu_verkiezingen.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_PARTIJ("deelname_eu_verkiezingen.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("deelname_eu_verkiezingen.verantwoordingVerval.tijdstip_registratie",
            false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening'
     * van objecttype 'Actie'.
     */
    PERSOON_DEELNAME_EU_VERKIEZINGEN_VERANTWOORDINGVERVAL_DATUM_ONTLENING("deelname_eu_verkiezingen.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonDeelnameEuVerkiezingenVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_GEMEENTE. BMR-attribuut 'Gemeente persoonskaart' van objecttype 'Persoon'.
     */
    PERSOON_PERSOONSKAART_GEMEENTE("persoonskaart.gemeente", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartGemeenteGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VOLLEDIG_GECONVERTEERD. BMR-attribuut 'Persoonskaart volledig geconverteerd?' van
     * objecttype 'Persoon'.
     */
    PERSOON_PERSOONSKAART_VOLLEDIG_GECONVERTEERD("persoonskaart.volledig_geconverteerd", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVolledigGeconverteerdGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Persoon Persoonskaart'.
     */
    PERSOON_PERSOONSKAART_DATUM_TIJD_REGISTRATIE("persoonskaart.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon
     * Persoonskaart'.
     */
    PERSOON_PERSOONSKAART_DATUM_TIJD_VERVAL("persoonskaart.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_SOORT("persoonskaart.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_PARTIJ("persoonskaart.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("persoonskaart.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGINHOUD_DATUM_ONTLENING("persoonskaart.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_SOORT("persoonskaart.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_PARTIJ("persoonskaart.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("persoonskaart.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOON_PERSOONSKAART_VERANTWOORDINGVERVAL_DATUM_ONTLENING("persoonskaart.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonPersoonskaartVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_VOORNAMEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOON_VOORNAMEN("voornamen", true, ExpressieType.VOORNAAM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVoornamenGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VOLGNUMMER. BMR-attribuut 'Volgnummer' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_VOLGNUMMER("volgnummer", false, ExpressieType.GETAL, ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVolgnummerGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_NAAM. BMR-attribuut 'Naam' van objecttype 'Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_NAAM("naam", false, ExpressieType.STRING, ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamNaamGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype 'His
     * Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_DATUM_AANVANG_GELDIGHEID("datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His
     * Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_DATUM_EINDE_GELDIGHEID("datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon \ Voornaam'.
     */
    PERSOONVOORNAAM_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \
     * Voornaam'.
     */
    PERSOONVOORNAAM_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.VOORNAAM,
            ExpressieAttribuut.PERSOON_VOORNAMEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("verantwoordingAanpassingGeldigheid.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONVOORNAAM_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.VOORNAAM, ExpressieAttribuut.PERSOON_VOORNAMEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonvoornaamVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_GESLACHTSNAAMCOMPONENTEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \
     * Geslachtsnaamcomponent'.
     */
    PERSOON_GESLACHTSNAAMCOMPONENTEN("geslachtsnaamcomponenten", true, ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonGeslachtsnaamcomponentenGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VOLGNUMMER. BMR-attribuut 'Volgnummer' van objecttype 'Persoon \
     * Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VOLGNUMMER("volgnummer", false, ExpressieType.GETAL, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVolgnummerGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_PREDICAAT. BMR-attribuut 'Predicaat' van objecttype 'Persoon \
     * Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_PREDICAAT("predicaat", false, ExpressieType.STRING, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentPredicaatGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_ADELLIJKE_TITEL. BMR-attribuut 'Adellijke titel' van objecttype 'Persoon
     * \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_ADELLIJKE_TITEL("adellijke_titel", false, ExpressieType.STRING, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentAdellijkeTitelGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VOORVOEGSEL. BMR-attribuut 'Voorvoegsel' van objecttype 'Persoon \
     * Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VOORVOEGSEL("voorvoegsel", false, ExpressieType.STRING, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVoorvoegselGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN. BMR-attribuut 'Scheidingsteken' van objecttype 'Persoon
     * \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN("scheidingsteken", false, ExpressieType.STRING, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentScheidingstekenGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_STAM. BMR-attribuut 'Stam' van objecttype 'Persoon \
     * Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_STAM("stam", false, ExpressieType.STRING, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentStamGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van
     * objecttype 'His Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_DATUM_AANVANG_GELDIGHEID("datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van
     * objecttype 'His Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_DATUM_EINDE_GELDIGHEID("datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon \ Geslachtsnaamcomponent'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.GESLACHTSNAAMCOMPONENT,
            ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van
     * objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van
     * objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut
     * 'Tijdstip registratie' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("verantwoordingAanpassingGeldigheid.tijdstip_registratie",
            false, ExpressieType.DATUMTIJD, ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOONGESLACHTSNAAMCOMPONENT_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.GESLACHTSNAAMCOMPONENT, ExpressieAttribuut.PERSOON_GESLACHTSNAAMCOMPONENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoongeslachtsnaamcomponentVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_VERIFICATIES. BMR-attribuut 'Geverifieerde' van objecttype 'Persoon \ Verificatie'.
     */
    PERSOON_VERIFICATIES("verificaties", true, ExpressieType.VERIFICATIE, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerificatiesGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Persoon \ Verificatie'.
     */
    PERSOONVERIFICATIE_PARTIJ("partij", false, ExpressieType.GETAL, ExpressieType.VERIFICATIE, ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatiePartijGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_SOORT. BMR-attribuut 'Soort' van objecttype 'Persoon \ Verificatie'.
     */
    PERSOONVERIFICATIE_SOORT("soort", false, ExpressieType.STRING, ExpressieType.VERIFICATIE, ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieSoortGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_DATUM. BMR-attribuut 'Datum' van objecttype 'Persoon \ Verificatie'.
     */
    PERSOONVERIFICATIE_DATUM("datum", false, ExpressieType.DATUM, ExpressieType.VERIFICATIE, ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieDatumGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon \ Verificatie'.
     */
    PERSOONVERIFICATIE_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \
     * Verificatie'.
     */
    PERSOONVERIFICATIE_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VERIFICATIE, ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VERIFICATIE, ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONVERIFICATIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.VERIFICATIE,
            ExpressieAttribuut.PERSOON_VERIFICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverificatieVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_NATIONALITEITEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOON_NATIONALITEITEN("nationaliteiten", true, ExpressieType.NATIONALITEIT, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonNationaliteitenGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_NATIONALITEIT. BMR-attribuut 'Nationaliteit' van objecttype 'Persoon \
     * Nationaliteit'.
     */
    PERSOONNATIONALITEIT_NATIONALITEIT("nationaliteit", false, ExpressieType.GETAL, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitNationaliteitGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_REDEN_VERKRIJGING. BMR-attribuut 'Reden verkrijging' van objecttype 'Persoon \
     * Nationaliteit'.
     */
    PERSOONNATIONALITEIT_REDEN_VERKRIJGING("reden_verkrijging", false, ExpressieType.GETAL, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitRedenVerkrijgingGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_REDEN_VERLIES. BMR-attribuut 'Reden verlies' van objecttype 'Persoon \
     * Nationaliteit'.
     */
    PERSOONNATIONALITEIT_REDEN_VERLIES("reden_verlies", false, ExpressieType.GETAL, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitRedenVerliesGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_BIJHOUDING_BEEINDIGD. BMR-attribuut 'Bijhouding beeindigd?' van objecttype
     * 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_BIJHOUDING_BEEINDIGD("bijhouding_beeindigd", false, ExpressieType.BOOLEAN, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitBijhoudingBeeindigdGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_MIGRATIE_REDEN_OPNAME_NATIONALITEIT. BMR-attribuut 'Migratie Reden opname
     * nationaliteit' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_MIGRATIE_REDEN_OPNAME_NATIONALITEIT("migratie_reden_opname_nationaliteit", false, ExpressieType.STRING,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitMigratieRedenOpnameNationaliteitGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_MIGRATIE_REDEN_BEEINDIGEN_NATIONALITEIT. BMR-attribuut 'Migratie Reden beeindigen
     * nationaliteit' van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_MIGRATIE_REDEN_BEEINDIGEN_NATIONALITEIT("migratie_reden_beeindigen_nationaliteit", false, ExpressieType.STRING,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitMigratieRedenBeeindigenNationaliteitGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_MIGRATIE_DATUM_EINDE_BIJHOUDING. BMR-attribuut 'Migratie Datum einde bijhouding'
     * van objecttype 'Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_MIGRATIE_DATUM_EINDE_BIJHOUDING("migratie_datum_einde_bijhouding", false, ExpressieType.DATUM, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitMigratieDatumEindeBijhoudingGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype
     * 'His Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_DATUM_AANVANG_GELDIGHEID("datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His
     * Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_DATUM_EINDE_GELDIGHEID("datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon \ Nationaliteit'.
     */
    PERSOONNATIONALITEIT_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \
     * Nationaliteit'.
     */
    PERSOONNATIONALITEIT_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.NATIONALITEIT,
            ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("verantwoordingAanpassingGeldigheid.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    PERSOONNATIONALITEIT_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.NATIONALITEIT, ExpressieAttribuut.PERSOON_NATIONALITEITEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonnationaliteitVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_ADRESSEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Adres'.
     */
    PERSOON_ADRESSEN("adressen", true, ExpressieType.ADRES, ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAdressenGetter()),
    /**
     * Attribuut PERSOONADRES_SOORT. BMR-attribuut 'Soort' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_SOORT("soort", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresSoortGetter()),
    /**
     * Attribuut PERSOONADRES_REDEN_WIJZIGING. BMR-attribuut 'Reden wijziging' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_REDEN_WIJZIGING("reden_wijziging", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresRedenWijzigingGetter()),
    /**
     * Attribuut PERSOONADRES_AANGEVER_ADRESHOUDING. BMR-attribuut 'Aangever adreshouding' van objecttype 'Persoon \
     * Adres'.
     */
    PERSOONADRES_AANGEVER_ADRESHOUDING("aangever_adreshouding", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresAangeverAdreshoudingGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_AANVANG_ADRESHOUDING. BMR-attribuut 'Datum aanvang adreshouding' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_DATUM_AANVANG_ADRESHOUDING("datum_aanvang_adreshouding", false, ExpressieType.DATUM, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumAanvangAdreshoudingGetter()),
    /**
     * Attribuut PERSOONADRES_IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT. BMR-attribuut 'Identificatiecode adresseerbaar
     * object' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT("identificatiecode_adresseerbaar_object", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresIdentificatiecodeAdresseerbaarObjectGetter()),
    /**
     * Attribuut PERSOONADRES_IDENTIFICATIECODE_NUMMERAANDUIDING. BMR-attribuut 'Identificatiecode nummeraanduiding' van
     * objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_IDENTIFICATIECODE_NUMMERAANDUIDING("identificatiecode_nummeraanduiding", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresIdentificatiecodeNummeraanduidingGetter()),
    /**
     * Attribuut PERSOONADRES_GEMEENTE. BMR-attribuut 'Gemeente' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_GEMEENTE("gemeente", false, ExpressieType.GETAL, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresGemeenteGetter()),
    /**
     * Attribuut PERSOONADRES_NAAM_OPENBARE_RUIMTE. BMR-attribuut 'Naam openbare ruimte' van objecttype 'Persoon \
     * Adres'.
     */
    PERSOONADRES_NAAM_OPENBARE_RUIMTE("naam_openbare_ruimte", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresNaamOpenbareRuimteGetter()),
    /**
     * Attribuut PERSOONADRES_AFGEKORTE_NAAM_OPENBARE_RUIMTE. BMR-attribuut 'Afgekorte naam openbare ruimte' van
     * objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_AFGEKORTE_NAAM_OPENBARE_RUIMTE("afgekorte_naam_openbare_ruimte", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresAfgekorteNaamOpenbareRuimteGetter()),
    /**
     * Attribuut PERSOONADRES_GEMEENTEDEEL. BMR-attribuut 'Gemeentedeel' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_GEMEENTEDEEL("gemeentedeel", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresGemeentedeelGetter()),
    /**
     * Attribuut PERSOONADRES_HUISNUMMER. BMR-attribuut 'Huisnummer' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_HUISNUMMER("huisnummer", false, ExpressieType.GETAL, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisnummerGetter()),
    /**
     * Attribuut PERSOONADRES_HUISLETTER. BMR-attribuut 'Huisletter' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_HUISLETTER("huisletter", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisletterGetter()),
    /**
     * Attribuut PERSOONADRES_HUISNUMMERTOEVOEGING. BMR-attribuut 'Huisnummertoevoeging' van objecttype 'Persoon \
     * Adres'.
     */
    PERSOONADRES_HUISNUMMERTOEVOEGING("huisnummertoevoeging", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresHuisnummertoevoegingGetter()),
    /**
     * Attribuut PERSOONADRES_POSTCODE. BMR-attribuut 'Postcode' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_POSTCODE("postcode", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresPostcodeGetter()),
    /**
     * Attribuut PERSOONADRES_WOONPLAATSNAAM. BMR-attribuut 'Woonplaatsnaam' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_WOONPLAATSNAAM("woonplaatsnaam", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresWoonplaatsnaamGetter()),
    /**
     * Attribuut PERSOONADRES_LOCATIE_TEN_OPZICHTE_VAN_ADRES. BMR-attribuut 'Locatie ten opzichte van adres' van
     * objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LOCATIE_TEN_OPZICHTE_VAN_ADRES("locatie_ten_opzichte_van_adres", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLocatieTenOpzichteVanAdresGetter()),
    /**
     * Attribuut PERSOONADRES_LOCATIEOMSCHRIJVING. BMR-attribuut 'Locatieomschrijving' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LOCATIEOMSCHRIJVING("locatieomschrijving", false, ExpressieType.STRING, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLocatieomschrijvingGetter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_1. BMR-attribuut 'Buitenlands adres regel 1' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_1("buitenlands_adres_regel_1", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel1Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_2. BMR-attribuut 'Buitenlands adres regel 2' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_2("buitenlands_adres_regel_2", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel2Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_3. BMR-attribuut 'Buitenlands adres regel 3' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_3("buitenlands_adres_regel_3", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel3Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_4. BMR-attribuut 'Buitenlands adres regel 4' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_4("buitenlands_adres_regel_4", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel4Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_5. BMR-attribuut 'Buitenlands adres regel 5' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_5("buitenlands_adres_regel_5", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel5Getter()),
    /**
     * Attribuut PERSOONADRES_BUITENLANDS_ADRES_REGEL_6. BMR-attribuut 'Buitenlands adres regel 6' van objecttype
     * 'Persoon \ Adres'.
     */
    PERSOONADRES_BUITENLANDS_ADRES_REGEL_6("buitenlands_adres_regel_6", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresBuitenlandsAdresRegel6Getter()),
    /**
     * Attribuut PERSOONADRES_LAND_GEBIED. BMR-attribuut 'Land/gebied' van objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_LAND_GEBIED("land_gebied", false, ExpressieType.GETAL, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresLandGebiedGetter()),
    /**
     * Attribuut PERSOONADRES_PERSOON_AANGETROFFEN_OP_ADRES. BMR-attribuut 'Persoon aangetroffen op adres?' van
     * objecttype 'Persoon \ Adres'.
     */
    PERSOONADRES_PERSOON_AANGETROFFEN_OP_ADRES("persoon_aangetroffen_op_adres", false, ExpressieType.BOOLEAN, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresPersoonAangetroffenOpAdresGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype 'His
     * Persoon \ Adres'.
     */
    PERSOONADRES_DATUM_AANVANG_GELDIGHEID("datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His Persoon
     * \ Adres'.
     */
    PERSOONADRES_DATUM_EINDE_GELDIGHEID("datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumEindeGeldigheidGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His Persoon
     * \ Adres'.
     */
    PERSOONADRES_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONADRES_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \ Adres'.
     */
    PERSOONADRES_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL, ExpressieType.ADRES,
            ExpressieAttribuut.PERSOON_ADRESSEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("verantwoordingAanpassingGeldigheid.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONADRES_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.ADRES, ExpressieAttribuut.PERSOON_ADRESSEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonadresVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_INDICATIES. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOON_INDICATIES("indicaties", true, ExpressieType.INDICATIE, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonIndicatiesGetter()),
    /**
     * Attribuut PERSOONINDICATIE_SOORT. BMR-attribuut 'Soort' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_SOORT("soort", false, ExpressieType.STRING, ExpressieType.INDICATIE, ExpressieAttribuut.PERSOON_INDICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieSoortGetter()),
    /**
     * Attribuut PERSOONINDICATIE_WAARDE. BMR-attribuut 'Waarde' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_WAARDE("waarde", false, ExpressieType.BOOLEAN, ExpressieType.INDICATIE, ExpressieAttribuut.PERSOON_INDICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieWaardeGetter()),
    /**
     * Attribuut PERSOONINDICATIE_MIGRATIE_REDEN_OPNAME_NATIONALITEIT. BMR-attribuut 'Migratie Reden opname
     * nationaliteit' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_MIGRATIE_REDEN_OPNAME_NATIONALITEIT("migratie_reden_opname_nationaliteit", false, ExpressieType.STRING, ExpressieType.INDICATIE,
            ExpressieAttribuut.PERSOON_INDICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieMigratieRedenOpnameNationaliteitGetter()),
    /**
     * Attribuut PERSOONINDICATIE_MIGRATIE_REDEN_BEEINDIGEN_NATIONALITEIT. BMR-attribuut 'Migratie Reden beeindigen
     * nationaliteit' van objecttype 'Persoon \ Indicatie'.
     */
    PERSOONINDICATIE_MIGRATIE_REDEN_BEEINDIGEN_NATIONALITEIT("migratie_reden_beeindigen_nationaliteit", false, ExpressieType.STRING,
            ExpressieType.INDICATIE, ExpressieAttribuut.PERSOON_INDICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonindicatieMigratieRedenBeeindigenNationaliteitGetter()),
    /**
     * Indicatie INDICATIE_DERDE_HEEFT_GEZAG.
     */
    INDICATIE_DERDE_HEEFT_GEZAG("indicatie.derde_heeft_gezag", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagGetter()),
    /**
     * Datum aanvang geldigheid van indicatie INDICATIE_DERDE_HEEFT_GEZAG_DATUM_AANVANG_GELDIGHEID.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_DATUM_AANVANG_GELDIGHEID("indicatie.derde_heeft_gezag.datum_aanvang_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagDatumAanvangGeldigheidGetter()),
    /**
     * Datum einde geldigheid van indicatie INDICATIE_DERDE_HEEFT_GEZAG_DATUM_EINDE_GELDIGHEID.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_DATUM_EINDE_GELDIGHEID("indicatie.derde_heeft_gezag.datum_einde_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagDatumEindeGeldigheidGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "indicatie.derde_heeft_gezag.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_DERDE_HEEFT_GEZAG_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_DATUM_TIJD_REGISTRATIE("indicatie.derde_heeft_gezag.datum_tijd_registratie", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_DERDE_HEEFT_GEZAG_DATUM_TIJD_VERVAL.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_DATUM_TIJD_VERVAL("indicatie.derde_heeft_gezag.datum_tijd_verval", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_SOORT("indicatie.derde_heeft_gezag.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_PARTIJ("indicatie.derde_heeft_gezag.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("indicatie.derde_heeft_gezag.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGINHOUD_DATUM_ONTLENING("indicatie.derde_heeft_gezag.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_SOORT("indicatie.derde_heeft_gezag.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_PARTIJ("indicatie.derde_heeft_gezag.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("indicatie.derde_heeft_gezag.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_DERDE_HEEFT_GEZAG_VERANTWOORDINGVERVAL_DATUM_ONTLENING("indicatie.derde_heeft_gezag.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieDerdeHeeftGezagVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_ONDER_CURATELE.
     */
    INDICATIE_ONDER_CURATELE("indicatie.onder_curatele", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleGetter()),
    /**
     * Datum aanvang geldigheid van indicatie INDICATIE_ONDER_CURATELE_DATUM_AANVANG_GELDIGHEID.
     */
    INDICATIE_ONDER_CURATELE_DATUM_AANVANG_GELDIGHEID("indicatie.onder_curatele.datum_aanvang_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleDatumAanvangGeldigheidGetter()),
    /**
     * Datum einde geldigheid van indicatie INDICATIE_ONDER_CURATELE_DATUM_EINDE_GELDIGHEID.
     */
    INDICATIE_ONDER_CURATELE_DATUM_EINDE_GELDIGHEID("indicatie.onder_curatele.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleDatumEindeGeldigheidGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "indicatie.onder_curatele.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_ONDER_CURATELE_DATUM_TIJD_REGISTRATIE("indicatie.onder_curatele.datum_tijd_registratie", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_ONDER_CURATELE_DATUM_TIJD_VERVAL.
     */
    INDICATIE_ONDER_CURATELE_DATUM_TIJD_VERVAL("indicatie.onder_curatele.datum_tijd_verval", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_SOORT("indicatie.onder_curatele.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_PARTIJ("indicatie.onder_curatele.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("indicatie.onder_curatele.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGINHOUD_DATUM_ONTLENING("indicatie.onder_curatele.verantwoordingInhoud.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_SOORT("indicatie.onder_curatele.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_PARTIJ("indicatie.onder_curatele.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("indicatie.onder_curatele.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_ONDER_CURATELE_VERANTWOORDINGVERVAL_DATUM_ONTLENING("indicatie.onder_curatele.verantwoordingVerval.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieOnderCurateleVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING("indicatie.volledige_verstrekkingsbeperking", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_DATUM_TIJD_REGISTRATIE("indicatie.volledige_verstrekkingsbeperking.datum_tijd_registratie", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_DATUM_TIJD_VERVAL.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_DATUM_TIJD_VERVAL("indicatie.volledige_verstrekkingsbeperking.datum_tijd_verval", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_SOORT("indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_PARTIJ("indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.partij",
            false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE(
            "indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING(
            "indicatie.volledige_verstrekkingsbeperking.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_SOORT("indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_PARTIJ("indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.partij",
            false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE(
            "indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_VOLLEDIGE_VERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING(
            "indicatie.volledige_verstrekkingsbeperking.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVolledigeVerstrekkingsbeperkingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER("indicatie.vastgesteld_niet_nederlander", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderGetter()),
    /**
     * Datum aanvang geldigheid van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_AANVANG_GELDIGHEID.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_AANVANG_GELDIGHEID("indicatie.vastgesteld_niet_nederlander.datum_aanvang_geldigheid", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderDatumAanvangGeldigheidGetter()),
    /**
     * Datum einde geldigheid van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_EINDE_GELDIGHEID.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_EINDE_GELDIGHEID("indicatie.vastgesteld_niet_nederlander.datum_einde_geldigheid", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderDatumEindeGeldigheidGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_TIJD_REGISTRATIE("indicatie.vastgesteld_niet_nederlander.datum_tijd_registratie", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_TIJD_VERVAL.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_DATUM_TIJD_VERVAL("indicatie.vastgesteld_niet_nederlander.datum_tijd_verval", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_SOORT("indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_PARTIJ("indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGINHOUD_DATUM_ONTLENING(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_SOORT("indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_PARTIJ("indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_VASTGESTELD_NIET_NEDERLANDER_VERANTWOORDINGVERVAL_DATUM_ONTLENING(
            "indicatie.vastgesteld_niet_nederlander.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieVastgesteldNietNederlanderVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER("indicatie.behandeld_als_nederlander", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderGetter()),
    /**
     * Datum aanvang geldigheid van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_AANVANG_GELDIGHEID.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_AANVANG_GELDIGHEID("indicatie.behandeld_als_nederlander.datum_aanvang_geldigheid", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderDatumAanvangGeldigheidGetter()),
    /**
     * Datum einde geldigheid van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_EINDE_GELDIGHEID.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_EINDE_GELDIGHEID("indicatie.behandeld_als_nederlander.datum_einde_geldigheid", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderDatumEindeGeldigheidGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT(
            "indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ(
            "indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING(
            "indicatie.behandeld_als_nederlander.verantwoordingAanpassingGeldigheid.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_TIJD_REGISTRATIE("indicatie.behandeld_als_nederlander.datum_tijd_registratie", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_TIJD_VERVAL.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_DATUM_TIJD_VERVAL("indicatie.behandeld_als_nederlander.datum_tijd_verval", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_SOORT("indicatie.behandeld_als_nederlander.verantwoordingInhoud.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_PARTIJ("indicatie.behandeld_als_nederlander.verantwoordingInhoud.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE(
            "indicatie.behandeld_als_nederlander.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGINHOUD_DATUM_ONTLENING("indicatie.behandeld_als_nederlander.verantwoordingInhoud.datum_ontlening",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_SOORT("indicatie.behandeld_als_nederlander.verantwoordingVerval.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_PARTIJ("indicatie.behandeld_als_nederlander.verantwoordingVerval.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE(
            "indicatie.behandeld_als_nederlander.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_BEHANDELD_ALS_NEDERLANDER_VERANTWOORDINGVERVAL_DATUM_ONTLENING("indicatie.behandeld_als_nederlander.verantwoordingVerval.datum_ontlening",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBehandeldAlsNederlanderVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT("indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument", false,
            ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentGetter()),
    /**
     * Tijdstip registratie van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_DATUM_TIJD_REGISTRATIE(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.datum_tijd_registratie", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_DATUM_TIJD_VERVAL.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_DATUM_TIJD_VERVAL(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.datum_tijd_verval", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_SOORT(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_PARTIJ(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.tijdstip_registratie",
            false,
            ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingInhoud.datum_ontlening",
            false,
            ExpressieType.DATUM,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_SOORT(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_PARTIJ(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.tijdstip_registratie",
            false,
            ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING(
            "indicatie.signalering_met_betrekking_tot_verstrekken_reisdocument.verantwoordingVerval.datum_ontlening",
            false,
            ExpressieType.DATUM,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_STAATLOOS.
     */
    INDICATIE_STAATLOOS("indicatie.staatloos", false, ExpressieType.BOOLEAN, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosGetter()),
    /**
     * Datum aanvang geldigheid van indicatie INDICATIE_STAATLOOS_DATUM_AANVANG_GELDIGHEID.
     */
    INDICATIE_STAATLOOS_DATUM_AANVANG_GELDIGHEID("indicatie.staatloos.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosDatumAanvangGeldigheidGetter()),
    /**
     * Datum einde geldigheid van indicatie INDICATIE_STAATLOOS_DATUM_EINDE_GELDIGHEID.
     */
    INDICATIE_STAATLOOS_DATUM_EINDE_GELDIGHEID("indicatie.staatloos.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosDatumEindeGeldigheidGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("indicatie.staatloos.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("indicatie.staatloos.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "indicatie.staatloos.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("indicatie.staatloos.verantwoordingAanpassingGeldigheid.datum_ontlening",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_STAATLOOS_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_STAATLOOS_DATUM_TIJD_REGISTRATIE("indicatie.staatloos.datum_tijd_registratie", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_STAATLOOS_DATUM_TIJD_VERVAL.
     */
    INDICATIE_STAATLOOS_DATUM_TIJD_VERVAL("indicatie.staatloos.datum_tijd_verval", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_SOORT("indicatie.staatloos.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_PARTIJ("indicatie.staatloos.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("indicatie.staatloos.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGINHOUD_DATUM_ONTLENING("indicatie.staatloos.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_SOORT("indicatie.staatloos.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_PARTIJ("indicatie.staatloos.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("indicatie.staatloos.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_STAATLOOS_VERANTWOORDINGVERVAL_DATUM_ONTLENING("indicatie.staatloos.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieStaatloosVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Indicatie INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE("indicatie.bijzondere_verblijfsrechtelijke_positie", false, ExpressieType.BOOLEAN,
            ExpressieType.PERSOON, new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieGetter()),
    /**
     * Tijdstip registratie van indicatie INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_DATUM_TIJD_REGISTRATIE.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_DATUM_TIJD_REGISTRATIE("indicatie.bijzondere_verblijfsrechtelijke_positie.datum_tijd_registratie",
            false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieDatumTijdRegistratieGetter()),
    /**
     * Tijdstip verval van indicatie INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_DATUM_TIJD_VERVAL.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_DATUM_TIJD_VERVAL("indicatie.bijzondere_verblijfsrechtelijke_positie.datum_tijd_verval", false,
            ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieDatumTijdVervalGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_SOORT.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_SOORT(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordinginhoudSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_PARTIJ.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_PARTIJ(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordinginhoudPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGINHOUD_DATUM_ONTLENING(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Soort van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_SOORT.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_SOORT(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordingvervalSoortGetter()),
    /**
     * Partij van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_PARTIJ.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_PARTIJ(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordingvervalPartijGetter()),
    /**
     * TSReg van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.STRING,
            ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Datum ontlening van de verantwoording van indicatie
     * INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING.
     */
    INDICATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE_VERANTWOORDINGVERVAL_DATUM_ONTLENING(
            "indicatie.bijzondere_verblijfsrechtelijke_positie.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.IndicatieBijzondereVerblijfsrechtelijkePositieVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_REISDOCUMENTEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOON_REISDOCUMENTEN("reisdocumenten", true, ExpressieType.REISDOCUMENT, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonReisdocumentenGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_SOORT. BMR-attribuut 'Soort' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_SOORT("soort", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentSoortGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_NUMMER. BMR-attribuut 'Nummer' van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_NUMMER("nummer", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentNummerGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_AUTORITEIT_VAN_AFGIFTE. BMR-attribuut 'Autoriteit van afgifte' van objecttype
     * 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_AUTORITEIT_VAN_AFGIFTE("autoriteit_van_afgifte", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentAutoriteitVanAfgifteGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_INGANG_DOCUMENT. BMR-attribuut 'Datum ingang document' van objecttype
     * 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_INGANG_DOCUMENT("datum_ingang_document", false, ExpressieType.DATUM, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumIngangDocumentGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_EINDE_DOCUMENT. BMR-attribuut 'Datum einde document' van objecttype 'Persoon
     * \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_EINDE_DOCUMENT("datum_einde_document", false, ExpressieType.DATUM, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumEindeDocumentGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_UITGIFTE. BMR-attribuut 'Datum uitgifte' van objecttype 'Persoon \
     * Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_UITGIFTE("datum_uitgifte", false, ExpressieType.DATUM, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumUitgifteGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_INHOUDING_VERMISSING. BMR-attribuut 'Datum inhouding/vermissing' van
     * objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_INHOUDING_VERMISSING("datum_inhouding_vermissing", false, ExpressieType.DATUM, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumInhoudingVermissingGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_AANDUIDING_INHOUDING_VERMISSING. BMR-attribuut 'Aanduiding inhouding/vermissing'
     * van objecttype 'Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_AANDUIDING_INHOUDING_VERMISSING("aanduiding_inhouding_vermissing", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentAanduidingInhoudingVermissingGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon \ Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \
     * Reisdocument'.
     */
    PERSOONREISDOCUMENT_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.REISDOCUMENT,
            ExpressieAttribuut.PERSOON_REISDOCUMENTEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONREISDOCUMENT_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.REISDOCUMENT, ExpressieAttribuut.PERSOON_REISDOCUMENTEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonreisdocumentVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_BETROKKENHEDEN. BMR-attribuut 'Persoon' van objecttype 'Betrokkenheid'.
     */
    PERSOON_BETROKKENHEDEN("betrokkenheden", true, ExpressieType.BETROKKENHEID, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonBetrokkenhedenGetter()),
    /**
     * Attribuut BETROKKENHEID_ROL. BMR-attribuut 'Rol' van objecttype 'Betrokkenheid'.
     */
    BETROKKENHEID_ROL("rol", false, ExpressieType.STRING, ExpressieType.BETROKKENHEID, ExpressieAttribuut.PERSOON_BETROKKENHEDEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidRolGetter()),
    /**
     * Attribuut BETROKKENHEID_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Betrokkenheid'.
     */
    BETROKKENHEID_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidDatumTijdRegistratieGetter()),
    /**
     * Attribuut BETROKKENHEID_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Betrokkenheid'.
     */
    BETROKKENHEID_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidDatumTijdVervalGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.BETROKKENHEID, ExpressieAttribuut.PERSOON_BETROKKENHEDEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN, new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.BETROKKENHEID, ExpressieAttribuut.PERSOON_BETROKKENHEDEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut BETROKKENHEID_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    BETROKKENHEID_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            ExpressieAttribuut.PERSOON_BETROKKENHEDEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.BetrokkenheidVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_VERSTREKKINGSBEPERKINGEN. BMR-attribuut 'Persoon' van objecttype 'Persoon \
     * Verstrekkingsbeperking'.
     */
    PERSOON_VERSTREKKINGSBEPERKINGEN("verstrekkingsbeperkingen", true, ExpressieType.VERSTREKKINGSBEPERKING, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonVerstrekkingsbeperkingenGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Persoon \
     * Verstrekkingsbeperking'.
     */
    PERSOONVERSTREKKINGSBEPERKING_PARTIJ("partij", false, ExpressieType.GETAL, ExpressieType.VERSTREKKINGSBEPERKING,
            ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingPartijGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_OMSCHRIJVING_DERDE. BMR-attribuut 'Omschrijving derde' van objecttype
     * 'Persoon \ Verstrekkingsbeperking'.
     */
    PERSOONVERSTREKKINGSBEPERKING_OMSCHRIJVING_DERDE("omschrijving_derde", false, ExpressieType.STRING, ExpressieType.VERSTREKKINGSBEPERKING,
            ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingOmschrijvingDerdeGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_GEMEENTE_VERORDENING. BMR-attribuut 'Gemeente verordening' van objecttype
     * 'Persoon \ Verstrekkingsbeperking'.
     */
    PERSOONVERSTREKKINGSBEPERKING_GEMEENTE_VERORDENING("gemeente_verordening", false, ExpressieType.GETAL, ExpressieType.VERSTREKKINGSBEPERKING,
            ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingGemeenteVerordeningGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Persoon \ Verstrekkingsbeperking'.
     */
    PERSOONVERSTREKKINGSBEPERKING_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.VERSTREKKINGSBEPERKING,
            ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon \ Verstrekkingsbeperking'.
     */
    PERSOONVERSTREKKINGSBEPERKING_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.VERSTREKKINGSBEPERKING,
            ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    PERSOONVERSTREKKINGSBEPERKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.VERSTREKKINGSBEPERKING, ExpressieAttribuut.PERSOON_VERSTREKKINGSBEPERKINGEN,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonverstrekkingsbeperkingVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOON_AFNEMERINDICATIES. BMR-attribuut 'Persoon' van objecttype 'Persoon \ Afnemerindicatie'.
     */
    PERSOON_AFNEMERINDICATIES("afnemerindicaties", true, ExpressieType.AFNEMERINDICATIE, ExpressieType.PERSOON,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonAfnemerindicatiesGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_AFNEMER. BMR-attribuut 'Afnemer' van objecttype 'Persoon \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_AFNEMER("afnemer", false, ExpressieType.GETAL, ExpressieType.AFNEMERINDICATIE, ExpressieAttribuut.PERSOON_AFNEMERINDICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieAfnemerGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_LEVERINGSAUTORISATIE. BMR-attribuut 'Leveringsautorisatie' van objecttype
     * 'Persoon \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_LEVERINGSAUTORISATIE("leveringsautorisatie", false, ExpressieType.GETAL, ExpressieType.AFNEMERINDICATIE,
            ExpressieAttribuut.PERSOON_AFNEMERINDICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieLeveringsautorisatieGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_DATUM_AANVANG_MATERIELE_PERIODE. BMR-attribuut 'Datum aanvang materiële
     * periode' van objecttype 'Persoon \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_DATUM_AANVANG_MATERIELE_PERIODE("datum_aanvang_materiele_periode", false, ExpressieType.DATUM, ExpressieType.AFNEMERINDICATIE,
            ExpressieAttribuut.PERSOON_AFNEMERINDICATIES,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieDatumAanvangMaterielePeriodeGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_DATUM_EINDE_VOLGEN. BMR-attribuut 'Datum einde volgen' van objecttype 'Persoon
     * \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_DATUM_EINDE_VOLGEN("datum_einde_volgen", false, ExpressieType.DATUM, ExpressieType.AFNEMERINDICATIE,
            ExpressieAttribuut.PERSOON_AFNEMERINDICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieDatumEindeVolgenGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Persoon \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.AFNEMERINDICATIE,
            ExpressieAttribuut.PERSOON_AFNEMERINDICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONAFNEMERINDICATIE_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Persoon \ Afnemerindicatie'.
     */
    PERSOONAFNEMERINDICATIE_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.AFNEMERINDICATIE,
            ExpressieAttribuut.PERSOON_AFNEMERINDICATIES, new nl.bzk.brp.expressietaal.symbols.solvers.PersoonafnemerindicatieDatumTijdVervalGetter()),
    /**
     * Attribuut ONDERZOEK_DATUM_AANVANG. BMR-attribuut 'Datum aanvang' van objecttype 'Onderzoek'.
     */
    ONDERZOEK_DATUM_AANVANG("datum_aanvang", false, ExpressieType.DATUM, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekDatumAanvangGetter()),
    /**
     * Attribuut ONDERZOEK_VERWACHTE_AFHANDELDATUM. BMR-attribuut 'Verwachte afhandeldatum' van objecttype 'Onderzoek'.
     */
    ONDERZOEK_VERWACHTE_AFHANDELDATUM("verwachte_afhandeldatum", false, ExpressieType.DATUM, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerwachteAfhandeldatumGetter()),
    /**
     * Attribuut ONDERZOEK_DATUM_EINDE. BMR-attribuut 'Datum einde' van objecttype 'Onderzoek'.
     */
    ONDERZOEK_DATUM_EINDE("datum_einde", false, ExpressieType.DATUM, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekDatumEindeGetter()),
    /**
     * Attribuut ONDERZOEK_OMSCHRIJVING. BMR-attribuut 'Omschrijving' van objecttype 'Onderzoek'.
     */
    ONDERZOEK_OMSCHRIJVING("omschrijving", false, ExpressieType.STRING, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekOmschrijvingGetter()),
    /**
     * Attribuut ONDERZOEK_STATUS. BMR-attribuut 'Status' van objecttype 'Onderzoek'.
     */
    ONDERZOEK_STATUS("status", false, ExpressieType.STRING, ExpressieType.ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekStatusGetter()),
    /**
     * Attribuut ONDERZOEK_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Onderzoek'.
     */
    ONDERZOEK_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekDatumTijdRegistratieGetter()),
    /**
     * Attribuut ONDERZOEK_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Onderzoek'.
     */
    ONDERZOEK_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekDatumTijdVervalGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut ONDERZOEK_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype 'Actie'.
     */
    ONDERZOEK_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.OnderzoekVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_ROL. BMR-attribuut 'Rol' van objecttype 'Persoon \ Onderzoek'.
     */
    PERSOONONDERZOEK_ROL("persoononderzoek.rol", false, ExpressieType.STRING, ExpressieType.PERSOON_ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekRolGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Persoon \ Onderzoek'.
     */
    PERSOONONDERZOEK_DATUM_TIJD_REGISTRATIE("persoononderzoek.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON_ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekDatumTijdRegistratieGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Persoon \
     * Onderzoek'.
     */
    PERSOONONDERZOEK_DATUM_TIJD_VERVAL("persoononderzoek.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.PERSOON_ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekDatumTijdVervalGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGINHOUD_SOORT("persoononderzoek.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGINHOUD_PARTIJ("persoononderzoek.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("persoononderzoek.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON_ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGINHOUD_DATUM_ONTLENING("persoononderzoek.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGVERVAL_SOORT("persoononderzoek.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGVERVAL_PARTIJ("persoononderzoek.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("persoononderzoek.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.PERSOON_ONDERZOEK,
            new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut PERSOONONDERZOEK_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    PERSOONONDERZOEK_VERANTWOORDINGVERVAL_DATUM_ONTLENING("persoononderzoek.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.PERSOON_ONDERZOEK, new nl.bzk.brp.expressietaal.symbols.solvers.PersoononderzoekVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_OUDER. BMR-attribuut 'Ouder?' van objecttype 'Ouder'.
     */
    OUDER_OUDERSCHAP_OUDER("ouderschap.ouder", false, ExpressieType.BOOLEAN, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapOuderGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_OUDER_UIT_WIE_HET_KIND_IS_GEBOREN. BMR-attribuut 'Ouder uit wie het kind is geboren?'
     * van objecttype 'Ouder'.
     */
    OUDER_OUDERSCHAP_OUDER_UIT_WIE_HET_KIND_IS_GEBOREN("ouderschap.ouder_uit_wie_het_kind_is_geboren", false, ExpressieType.BOOLEAN,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapOuderUitWieHetKindIsGeborenGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype 'His
     * Ouder Ouderschap'.
     */
    OUDER_OUDERSCHAP_DATUM_AANVANG_GELDIGHEID("ouderschap.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype 'His
     * Ouder Ouderschap'.
     */
    OUDER_OUDERSCHAP_DATUM_EINDE_GELDIGHEID("ouderschap.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapDatumEindeGeldigheidGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His
     * Ouder Ouderschap'.
     */
    OUDER_OUDERSCHAP_DATUM_TIJD_REGISTRATIE("ouderschap.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapDatumTijdRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Ouder
     * Ouderschap'.
     */
    OUDER_OUDERSCHAP_DATUM_TIJD_VERVAL("ouderschap.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapDatumTijdVervalGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_SOORT("ouderschap.verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_PARTIJ("ouderschap.verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("ouderschap.verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGINHOUD_DATUM_ONTLENING("ouderschap.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_SOORT("ouderschap.verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_PARTIJ("ouderschap.verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van
     * objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("ouderschap.verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGVERVAL_DATUM_ONTLENING("ouderschap.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("ouderschap.verantwoordingAanpassingGeldigheid.soort", false, ExpressieType.STRING,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("ouderschap.verantwoordingAanpassingGeldigheid.partij", false, ExpressieType.GETAL,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE("ouderschap.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening'
     * van objecttype 'Actie'.
     */
    OUDER_OUDERSCHAP_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("ouderschap.verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderschapVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_OUDER_HEEFT_GEZAG. BMR-attribuut 'Ouder heeft gezag?' van objecttype 'Ouder'.
     */
    OUDER_OUDERLIJK_GEZAG_OUDER_HEEFT_GEZAG("ouderlijk_gezag.ouder_heeft_gezag", false, ExpressieType.BOOLEAN, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagOuderHeeftGezagGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_DATUM_AANVANG_GELDIGHEID. BMR-attribuut 'Datum aanvang geldigheid' van objecttype
     * 'His Ouder Ouderlijk gezag'.
     */
    OUDER_OUDERLIJK_GEZAG_DATUM_AANVANG_GELDIGHEID("ouderlijk_gezag.datum_aanvang_geldigheid", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagDatumAanvangGeldigheidGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_DATUM_EINDE_GELDIGHEID. BMR-attribuut 'Datum einde geldigheid' van objecttype
     * 'His Ouder Ouderlijk gezag'.
     */
    OUDER_OUDERLIJK_GEZAG_DATUM_EINDE_GELDIGHEID("ouderlijk_gezag.datum_einde_geldigheid", false, ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagDatumEindeGeldigheidGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Ouder Ouderlijk gezag'.
     */
    OUDER_OUDERLIJK_GEZAG_DATUM_TIJD_REGISTRATIE("ouderlijk_gezag.datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagDatumTijdRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Ouder
     * Ouderlijk gezag'.
     */
    OUDER_OUDERLIJK_GEZAG_DATUM_TIJD_VERVAL("ouderlijk_gezag.datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagDatumTijdVervalGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_SOORT("ouderlijk_gezag.verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_PARTIJ("ouderlijk_gezag.verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("ouderlijk_gezag.verantwoordingInhoud.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGINHOUD_DATUM_ONTLENING("ouderlijk_gezag.verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_SOORT("ouderlijk_gezag.verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_PARTIJ("ouderlijk_gezag.verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie'
     * van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("ouderlijk_gezag.verantwoordingVerval.tijdstip_registratie", false,
            ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGVERVAL_DATUM_ONTLENING("ouderlijk_gezag.verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.BETROKKENHEID, new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT. BMR-attribuut 'Soort' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_SOORT("ouderlijk_gezag.verantwoordingAanpassingGeldigheid.soort", false,
            ExpressieType.STRING, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingaanpassinggeldigheidSoortGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_PARTIJ("ouderlijk_gezag.verantwoordingAanpassingGeldigheid.partij", false,
            ExpressieType.GETAL, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingaanpassinggeldigheidPartijGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_TIJDSTIP_REGISTRATIE(
            "ouderlijk_gezag.verantwoordingAanpassingGeldigheid.tijdstip_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingaanpassinggeldigheidTijdstipRegistratieGetter()),
    /**
     * Attribuut OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING. BMR-attribuut 'Datum
     * ontlening' van objecttype 'Actie'.
     */
    OUDER_OUDERLIJK_GEZAG_VERANTWOORDINGAANPASSINGGELDIGHEID_DATUM_ONTLENING("ouderlijk_gezag.verantwoordingAanpassingGeldigheid.datum_ontlening", false,
            ExpressieType.DATUM, ExpressieType.BETROKKENHEID,
            new nl.bzk.brp.expressietaal.symbols.solvers.OuderOuderlijkGezagVerantwoordingaanpassinggeldigheidDatumOntleningGetter()),
    /**
     * Attribuut HUWELIJK_SOORT. BMR-attribuut 'Soort' van objecttype 'Relatie'.
     */
    HUWELIJK_SOORT("soort", false, ExpressieType.STRING, ExpressieType.HUWELIJK, new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkSoortGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_AANVANG. BMR-attribuut 'Datum aanvang' van objecttype 'Relatie'.
     */
    HUWELIJK_DATUM_AANVANG("datum_aanvang", false, ExpressieType.DATUM, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumAanvangGetter()),
    /**
     * Attribuut HUWELIJK_GEMEENTE_AANVANG. BMR-attribuut 'Gemeente aanvang' van objecttype 'Relatie'.
     */
    HUWELIJK_GEMEENTE_AANVANG("gemeente_aanvang", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkGemeenteAanvangGetter()),
    /**
     * Attribuut HUWELIJK_WOONPLAATSNAAM_AANVANG. BMR-attribuut 'Woonplaatsnaam aanvang' van objecttype 'Relatie'.
     */
    HUWELIJK_WOONPLAATSNAAM_AANVANG("woonplaatsnaam_aanvang", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkWoonplaatsnaamAanvangGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_PLAATS_AANVANG. BMR-attribuut 'Buitenlandse plaats aanvang' van objecttype
     * 'Relatie'.
     */
    HUWELIJK_BUITENLANDSE_PLAATS_AANVANG("buitenlandse_plaats_aanvang", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandsePlaatsAanvangGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_REGIO_AANVANG. BMR-attribuut 'Buitenlandse regio aanvang' van objecttype
     * 'Relatie'.
     */
    HUWELIJK_BUITENLANDSE_REGIO_AANVANG("buitenlandse_regio_aanvang", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandseRegioAanvangGetter()),
    /**
     * Attribuut HUWELIJK_OMSCHRIJVING_LOCATIE_AANVANG. BMR-attribuut 'Omschrijving locatie aanvang' van objecttype
     * 'Relatie'.
     */
    HUWELIJK_OMSCHRIJVING_LOCATIE_AANVANG("omschrijving_locatie_aanvang", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkOmschrijvingLocatieAanvangGetter()),
    /**
     * Attribuut HUWELIJK_LAND_GEBIED_AANVANG. BMR-attribuut 'Land/gebied aanvang' van objecttype 'Relatie'.
     */
    HUWELIJK_LAND_GEBIED_AANVANG("land_gebied_aanvang", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkLandGebiedAanvangGetter()),
    /**
     * Attribuut HUWELIJK_REDEN_EINDE. BMR-attribuut 'Reden einde' van objecttype 'Relatie'.
     */
    HUWELIJK_REDEN_EINDE("reden_einde", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkRedenEindeGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_EINDE. BMR-attribuut 'Datum einde' van objecttype 'Relatie'.
     */
    HUWELIJK_DATUM_EINDE("datum_einde", false, ExpressieType.DATUM, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumEindeGetter()),
    /**
     * Attribuut HUWELIJK_GEMEENTE_EINDE. BMR-attribuut 'Gemeente einde' van objecttype 'Relatie'.
     */
    HUWELIJK_GEMEENTE_EINDE("gemeente_einde", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkGemeenteEindeGetter()),
    /**
     * Attribuut HUWELIJK_WOONPLAATSNAAM_EINDE. BMR-attribuut 'Woonplaatsnaam einde' van objecttype 'Relatie'.
     */
    HUWELIJK_WOONPLAATSNAAM_EINDE("woonplaatsnaam_einde", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkWoonplaatsnaamEindeGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_PLAATS_EINDE. BMR-attribuut 'Buitenlandse plaats einde' van objecttype 'Relatie'.
     */
    HUWELIJK_BUITENLANDSE_PLAATS_EINDE("buitenlandse_plaats_einde", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandsePlaatsEindeGetter()),
    /**
     * Attribuut HUWELIJK_BUITENLANDSE_REGIO_EINDE. BMR-attribuut 'Buitenlandse regio einde' van objecttype 'Relatie'.
     */
    HUWELIJK_BUITENLANDSE_REGIO_EINDE("buitenlandse_regio_einde", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkBuitenlandseRegioEindeGetter()),
    /**
     * Attribuut HUWELIJK_OMSCHRIJVING_LOCATIE_EINDE. BMR-attribuut 'Omschrijving locatie einde' van objecttype
     * 'Relatie'.
     */
    HUWELIJK_OMSCHRIJVING_LOCATIE_EINDE("omschrijving_locatie_einde", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkOmschrijvingLocatieEindeGetter()),
    /**
     * Attribuut HUWELIJK_LAND_GEBIED_EINDE. BMR-attribuut 'Land/gebied einde' van objecttype 'Relatie'.
     */
    HUWELIJK_LAND_GEBIED_EINDE("land_gebied_einde", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkLandGebiedEindeGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype 'His Relatie'.
     */
    HUWELIJK_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumTijdRegistratieGetter()),
    /**
     * Attribuut HUWELIJK_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His Relatie'.
     */
    HUWELIJK_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkDatumTijdVervalGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van objecttype
     * 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.HUWELIJK, new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip registratie' van objecttype
     * 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.HUWELIJK, new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut HUWELIJK_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van objecttype 'Actie'.
     */
    HUWELIJK_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM, ExpressieType.HUWELIJK,
            new nl.bzk.brp.expressietaal.symbols.solvers.HuwelijkVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_SOORT. BMR-attribuut 'Soort' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_SOORT("soort", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapSoortGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_DATUM_AANVANG. BMR-attribuut 'Datum aanvang' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_DATUM_AANVANG("datum_aanvang", false, ExpressieType.DATUM, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapDatumAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_GEMEENTE_AANVANG. BMR-attribuut 'Gemeente aanvang' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_GEMEENTE_AANVANG("gemeente_aanvang", false, ExpressieType.GETAL, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapGemeenteAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAM_AANVANG. BMR-attribuut 'Woonplaatsnaam aanvang' van objecttype
     * 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAM_AANVANG("woonplaatsnaam_aanvang", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapWoonplaatsnaamAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_PLAATS_AANVANG. BMR-attribuut 'Buitenlandse plaats aanvang' van
     * objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_PLAATS_AANVANG("buitenlandse_plaats_aanvang", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapBuitenlandsePlaatsAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_REGIO_AANVANG. BMR-attribuut 'Buitenlandse regio aanvang' van
     * objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_REGIO_AANVANG("buitenlandse_regio_aanvang", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapBuitenlandseRegioAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_OMSCHRIJVING_LOCATIE_AANVANG. BMR-attribuut 'Omschrijving locatie aanvang'
     * van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVING_LOCATIE_AANVANG("omschrijving_locatie_aanvang", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapOmschrijvingLocatieAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_LAND_GEBIED_AANVANG. BMR-attribuut 'Land/gebied aanvang' van objecttype
     * 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_LAND_GEBIED_AANVANG("land_gebied_aanvang", false, ExpressieType.GETAL, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapLandGebiedAanvangGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_REDEN_EINDE. BMR-attribuut 'Reden einde' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_REDEN_EINDE("reden_einde", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapRedenEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_DATUM_EINDE. BMR-attribuut 'Datum einde' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_DATUM_EINDE("datum_einde", false, ExpressieType.DATUM, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapDatumEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_GEMEENTE_EINDE. BMR-attribuut 'Gemeente einde' van objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_GEMEENTE_EINDE("gemeente_einde", false, ExpressieType.GETAL, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapGemeenteEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAM_EINDE. BMR-attribuut 'Woonplaatsnaam einde' van objecttype
     * 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_WOONPLAATSNAAM_EINDE("woonplaatsnaam_einde", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapWoonplaatsnaamEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_PLAATS_EINDE. BMR-attribuut 'Buitenlandse plaats einde' van
     * objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_PLAATS_EINDE("buitenlandse_plaats_einde", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapBuitenlandsePlaatsEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_REGIO_EINDE. BMR-attribuut 'Buitenlandse regio einde' van
     * objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_BUITENLANDSE_REGIO_EINDE("buitenlandse_regio_einde", false, ExpressieType.STRING, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapBuitenlandseRegioEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_OMSCHRIJVING_LOCATIE_EINDE. BMR-attribuut 'Omschrijving locatie einde' van
     * objecttype 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_OMSCHRIJVING_LOCATIE_EINDE("omschrijving_locatie_einde", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapOmschrijvingLocatieEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_LAND_GEBIED_EINDE. BMR-attribuut 'Land/gebied einde' van objecttype
     * 'Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_LAND_GEBIED_EINDE("land_gebied_einde", false, ExpressieType.GETAL, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapLandGebiedEindeGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van objecttype
     * 'His Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapDatumTijdRegistratieGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Relatie'.
     */
    GEREGISTREERDPARTNERSCHAP_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapDatumTijdVervalGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    GEREGISTREERDPARTNERSCHAP_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.GEREGISTREERDPARTNERSCHAP,
            new nl.bzk.brp.expressietaal.symbols.solvers.GeregistreerdpartnerschapVerantwoordingvervalDatumOntleningGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_SOORT. BMR-attribuut 'Soort' van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_SOORT("soort", false, ExpressieType.STRING, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingSoortGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_DATUM_AANVANG. BMR-attribuut 'Datum aanvang' van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUM_AANVANG("datum_aanvang", false, ExpressieType.DATUM, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingDatumAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_GEMEENTE_AANVANG. BMR-attribuut 'Gemeente aanvang' van objecttype
     * 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTE_AANVANG("gemeente_aanvang", false, ExpressieType.GETAL, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingGemeenteAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAM_AANVANG. BMR-attribuut 'Woonplaatsnaam aanvang' van
     * objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAM_AANVANG("woonplaatsnaam_aanvang", false, ExpressieType.STRING, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingWoonplaatsnaamAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_PLAATS_AANVANG. BMR-attribuut 'Buitenlandse plaats aanvang'
     * van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_PLAATS_AANVANG("buitenlandse_plaats_aanvang", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingBuitenlandsePlaatsAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_REGIO_AANVANG. BMR-attribuut 'Buitenlandse regio aanvang' van
     * objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_REGIO_AANVANG("buitenlandse_regio_aanvang", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingBuitenlandseRegioAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVING_LOCATIE_AANVANG. BMR-attribuut 'Omschrijving locatie aanvang'
     * van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVING_LOCATIE_AANVANG("omschrijving_locatie_aanvang", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingOmschrijvingLocatieAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_LAND_GEBIED_AANVANG. BMR-attribuut 'Land/gebied aanvang' van objecttype
     * 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_LAND_GEBIED_AANVANG("land_gebied_aanvang", false, ExpressieType.GETAL, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingLandGebiedAanvangGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_REDEN_EINDE. BMR-attribuut 'Reden einde' van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_REDEN_EINDE("reden_einde", false, ExpressieType.STRING, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingRedenEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_DATUM_EINDE. BMR-attribuut 'Datum einde' van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUM_EINDE("datum_einde", false, ExpressieType.DATUM, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingDatumEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_GEMEENTE_EINDE. BMR-attribuut 'Gemeente einde' van objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_GEMEENTE_EINDE("gemeente_einde", false, ExpressieType.GETAL, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingGemeenteEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAM_EINDE. BMR-attribuut 'Woonplaatsnaam einde' van objecttype
     * 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_WOONPLAATSNAAM_EINDE("woonplaatsnaam_einde", false, ExpressieType.STRING, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingWoonplaatsnaamEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_PLAATS_EINDE. BMR-attribuut 'Buitenlandse plaats einde' van
     * objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_PLAATS_EINDE("buitenlandse_plaats_einde", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingBuitenlandsePlaatsEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_REGIO_EINDE. BMR-attribuut 'Buitenlandse regio einde' van
     * objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_BUITENLANDSE_REGIO_EINDE("buitenlandse_regio_einde", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingBuitenlandseRegioEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVING_LOCATIE_EINDE. BMR-attribuut 'Omschrijving locatie einde' van
     * objecttype 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_OMSCHRIJVING_LOCATIE_EINDE("omschrijving_locatie_einde", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingOmschrijvingLocatieEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_LAND_GEBIED_EINDE. BMR-attribuut 'Land/gebied einde' van objecttype
     * 'Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_LAND_GEBIED_EINDE("land_gebied_einde", false, ExpressieType.GETAL, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingLandGebiedEindeGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_DATUM_TIJD_REGISTRATIE. BMR-attribuut 'Datum/tijd registratie' van
     * objecttype 'His Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUM_TIJD_REGISTRATIE("datum_tijd_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingDatumTijdRegistratieGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_DATUM_TIJD_VERVAL. BMR-attribuut 'Datum/tijd verval' van objecttype 'His
     * Relatie'.
     */
    FAMILIERECHTELIJKEBETREKKING_DATUM_TIJD_VERVAL("datum_tijd_verval", false, ExpressieType.DATUMTIJD, ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingDatumTijdVervalGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_SOORT("verantwoordingInhoud.soort", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordinginhoudSoortGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_PARTIJ("verantwoordingInhoud.partij", false, ExpressieType.GETAL,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordinginhoudPartijGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_TIJDSTIP_REGISTRATIE("verantwoordingInhoud.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordinginhoudTijdstipRegistratieGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGINHOUD_DATUM_ONTLENING("verantwoordingInhoud.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordinginhoudDatumOntleningGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_SOORT. BMR-attribuut 'Soort' van objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_SOORT("verantwoordingVerval.soort", false, ExpressieType.STRING,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordingvervalSoortGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_PARTIJ. BMR-attribuut 'Partij' van objecttype
     * 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_PARTIJ("verantwoordingVerval.partij", false, ExpressieType.GETAL,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordingvervalPartijGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE. BMR-attribuut 'Tijdstip
     * registratie' van objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_TIJDSTIP_REGISTRATIE("verantwoordingVerval.tijdstip_registratie", false, ExpressieType.DATUMTIJD,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordingvervalTijdstipRegistratieGetter()),
    /**
     * Attribuut FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING. BMR-attribuut 'Datum ontlening' van
     * objecttype 'Actie'.
     */
    FAMILIERECHTELIJKEBETREKKING_VERANTWOORDINGVERVAL_DATUM_ONTLENING("verantwoordingVerval.datum_ontlening", false, ExpressieType.DATUM,
            ExpressieType.FAMILIERECHTELIJKEBETREKKING,
            new nl.bzk.brp.expressietaal.symbols.solvers.FamilierechtelijkebetrekkingVerantwoordingvervalDatumOntleningGetter());

    private final String syntax;
    private final boolean isLijst;
    private final ExpressieType type;
    private final ExpressieType parentType;
    private final ExpressieAttribuut parent;
    private final AttributeGetter getter;

    /**
     * Constructor.
     *
     * @param syntax Naam (syntax) van het attribuut.
     * @param isLijst TRUE als het een lijst-attribuut is (bijv. adressen en nationaliteiten).
     * @param type Type van de attribuutwaarde.
     * @param parentType Type waartoe het attribuut behoort.
     * @param parent Lijst-attribuut (inverse associatie) waartoe het attribute behoort.
     * @param getter Getter voor het attribuut.
     */
    private ExpressieAttribuut(
        final String syntax,
        final boolean isLijst,
        final ExpressieType type,
        final ExpressieType parentType,
        final ExpressieAttribuut parent,
        final AttributeGetter getter)
    {
        this.syntax = syntax.toLowerCase();
        this.isLijst = isLijst;
        this.type = type;
        this.parentType = parentType;
        this.parent = parent;
        this.getter = getter;
    }

    /**
     * Constructor.
     *
     * @param syntax Naam (syntax) van het attribuut.
     * @param isLijst TRUE als het een lijst-attribuut is, anders FALSE.
     * @param type Type van de attribuutwaarde.
     * @param parentType Type waartoe het attribuut behoort.
     * @param getter Getter voor het attribuut.
     */
    private ExpressieAttribuut(
        final String syntax,
        final boolean isLijst,
        final ExpressieType type,
        final ExpressieType parentType,
        final AttributeGetter getter)
    {
        this(syntax, isLijst, type, parentType, null, getter);
    }

    public String getSyntax() {
        return syntax;
    }

    public boolean isLijst() {
        return isLijst;
    }

    public ExpressieType getType() {
        return type;
    }

    public ExpressieType getParentType() {
        return parentType;
    }

    public ExpressieAttribuut getParent() {
        return parent;
    }

    /**
     * Geeft de waarde van het attribuut voor het betreffende BRP-object terug.
     *
     * @param brpObject Object waarvan de attribuutwaarde bepaald moet worden.
     * @return Waarde van het attribuut of NULL indien niet gevonden.
     */
    public Expressie getAttribuutWaarde(final BrpObject brpObject) {
        return getter.getAttribuutWaarde(brpObject);
    }

    /**
     * Geeft het attribuut voor het betreffende BRP-object terug.
     *
     * @param brpObject Object waarvan het attribuut bepaald moet worden.
     * @return Het attribuut of NULL indien niet gevonden.
     */
    public Attribuut getAttribuut(final BrpObject brpObject) {
        return getter.getAttribuut(brpObject);
    }

    /**
     * Geeft de historische attributen voor het betreffende BRP-object terug.
     *
     * @param brpObject Object waarvan de attributen bepaald moeten worden.
     * @return List met attributen of NULL indien niet gevonden.
     */
    public List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        return getter.getHistorischeAttributen(brpObject);
    }

    @Override
    public String toString() {
        return getParentType().toString() + "." + getSyntax() + " (" + getType().toString() + ")";
    }
}
