/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.validatie.constraint.BRAL0102;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVelden;
import nl.bzk.brp.model.validatie.constraint.CustomSpEL;
import nl.bzk.brp.model.validatie.constraint.CustomSpELs;
import nl.bzk.brp.model.validatie.constraint.GeldigheidBestaansPeriodeStamgegeven;
import nl.bzk.brp.model.validatie.constraint.GeldigheidBestaansPeriodeStamgegevens;
import nl.bzk.brp.model.validatie.constraint.Lengte;
import org.jibx.runtime.IUnmarshallingContext;


/**
 * Voor de modellering van buitenlands adres waren enkele opties: - Adres in een attribuut met 'regelovergang' tekens Nadeel: Regelovergangtekens zijn niet
 * platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen. - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht) RNI
 * heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet lukken. (Voorlopig) nog geen optie. -
 * Adres per regel opnemen. - Adresregels in een aparte tabel. Is ook mogelijk mits aantal regels beperkt wordt. Uiteindelijk is gekozen voor opname per
 * regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over
 * het maximale aantal regels en het maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen. RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 */
@ConditioneelVelden({
    // Verplicht zijn wanneer land NL (landcode 6030) is
    // Note: de message attribute is hier nodig anders kan de validatie framework geen onderscheidt maken tussen de
    // validatie errors en ontstaat er maar 1 error uitkomst.
    @ConditioneelVeld(wanneerInhoudVanVeld = "landGebied.waarde.code",
        isGelijkAan = LandGebiedCodeAttribuut.NL_LAND_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "soort",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2033, message = "BRAL2033", dbObject = DatabaseObjectKern.PERSOON_ADRES__SOORT),
    @ConditioneelVeld(wanneerInhoudVanVeld = "landGebied.waarde.code",
        isGelijkAan = LandGebiedCodeAttribuut.NL_LAND_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "datumAanvangAdreshouding",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2033, message = "BRAL2033",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING),
    @ConditioneelVeld(wanneerInhoudVanVeld = "landGebied.waarde.code",
        isGelijkAan = LandGebiedCodeAttribuut.NL_LAND_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "gemeente.waarde.code",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2033, message = "BRAL2033",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__GEMEENTE),
    @ConditioneelVeld(
        // vs 1.1: andersom (verboden) toegevoegd (aka.SYNSCHROON_IF_NULL_DONT_CARE naar SYNCHROON)
        wanneerInhoudVanVeld = "redenWijziging.waarde.code",
        isGelijkAan = RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "aangeverAdreshouding",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL1118,
        message = "BRAL1118", dbObject = DatabaseObjectKern.PERSOON_ADRES__AANGEVER_ADRESHOUDING),
    @ConditioneelVeld(wanneerInhoudVanVeld = "woonplaatsnaam", isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "gemeente.waarde.code",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL0209, message = "BRAL0209",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__GEMEENTE),
    @ConditioneelVeld(wanneerInhoudVanVeld = "huisnummertoevoeging.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "huisnummer.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2085, message = "BRAL2085",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__HUISNUMMER),
    @ConditioneelVeld(wanneerInhoudVanVeld = "huisletter.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "huisnummer.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2084, message = "BRAL2084",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__HUISNUMMER),
    @ConditioneelVeld(wanneerInhoudVanVeld = "afgekorteNaamOpenbareRuimte.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "huisnummer.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2083, message = "BRAL2083",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__HUISNUMMER),
    @ConditioneelVeld(wanneerInhoudVanVeld = "gemeentedeel.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "gemeente.waarde.code",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2035, message = "BRAL2035",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__GEMEENTE),
    @ConditioneelVeld(wanneerInhoudVanVeld = "landGebied.waarde.code",
        isGelijkAan = LandGebiedCodeAttribuut.NL_LAND_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "identificatiecodeAdresseerbaarObject.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2032, message = "BRAL2032", soortMelding = SoortMelding.DEBLOKKEERBAAR,
        dbObject = DatabaseObjectKern.PERSOON_ADRES__IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT),
    @ConditioneelVeld(wanneerInhoudVanVeld = "landGebied.waarde.code", isGelijkAan = ConditioneelVeld.OPERATOR_NOT
        + LandGebiedCodeAttribuut.NL_LAND_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "identificatiecodeAdresseerbaarObject.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL2031, message = "BRAL2031",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__IDENTIFICATIECODE_ADRESSEERBAAR_OBJECT),
    @ConditioneelVeld(wanneerInhoudVanVeld = "locatieTenOpzichteVanAdres.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "huisnummertoevoeging.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL2086, message = "BRAL2086",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__HUISNUMMERTOEVOEGING),
    @ConditioneelVeld(wanneerInhoudVanVeld = "postcode.waarde", isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "huisnummer.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2094, message = "BRAL2094",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__HUISNUMMER),
    @ConditioneelVeld(wanneerInhoudVanVeld = "postcode.waarde", isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "afgekorteNaamOpenbareRuimte.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2094, message = "BRAL2094",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__AFGEKORTE_NAAM_OPENBARE_RUIMTE),
    @ConditioneelVeld(wanneerInhoudVanVeld = "locatieomschrijving.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "afgekorteNaamOpenbareRuimte.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.EXCLUSIEF_IF_NULL_DONT_CARE,
        code = Regel.BRAL2025, message = "BRAL2025",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__AFGEKORTE_NAAM_OPENBARE_RUIMTE),
    @ConditioneelVeld(wanneerInhoudVanVeld = "identificatiecodeAdresseerbaarObject.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "identificatiecodeNummeraanduiding.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL2095,
        message = "BRAL2095",
        dbObject = DatabaseObjectKern.PERSOON_ADRES__IDENTIFICATIECODE_NUMMERAANDUIDING),
    @ConditioneelVeld(wanneerInhoudVanVeld = "identificatiecodeAdresseerbaarObject.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "naamOpenbareRuimte.waarde",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL2095,
        message = "BRAL2095", dbObject = DatabaseObjectKern.PERSOON_ADRES__NAAM_OPENBARE_RUIMTE),
    @ConditioneelVeld(wanneerInhoudVanVeld = "identificatiecodeAdresseerbaarObject.waarde",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "woonplaatsnaam",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL2095,
        message = "BRAL2095", dbObject = DatabaseObjectKern.PERSOON_ADRES__WOONPLAATSNAAM) })
@CustomSpELs({ @CustomSpEL(
    wanneerVeldVoldoetAanRegel = "landGebied?.waarde?.code?.waarde == #LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT and "
        + "redenWijziging?.waarde?.code?.waarde == #RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING",
    danMoetVeldVoldoenAanRegel = "!#isBlank(afgekorteNaamOpenbareRuimte?.waarde)",
    code = Regel.BRAL2027, message = "BRAL2027", soortMelding = SoortMelding.DEBLOKKEERBAAR) })
@GeldigheidBestaansPeriodeStamgegevens({ @GeldigheidBestaansPeriodeStamgegeven(
    peilDatumVeld = "datumAanvangAdreshouding",
    bestaansPeriodeStamgegevenVeld = "landGebied",
    code = Regel.BRBY0172) })
public final class PersoonAdresStandaardGroepBericht extends AbstractPersoonAdresStandaardGroepBericht implements
    PersoonAdresStandaardGroep
{

    /**
     * default constructor.
     */
    public PersoonAdresStandaardGroepBericht() {
        super();
        // de default waarde die in de database hoort te staan
        setIndicatiePersoonAangetroffenOpAdres(new NeeAttribuut(Nee.N));
    }

    @BRAL0102(dbObject = DatabaseObjectKern.PERSOON_ADRES__DATUM_AANVANG_ADRESHOUDING)
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangAdreshouding() {
        return super.getDatumAanvangAdreshouding();
    }

    @nl.bzk.brp.model.validatie.constraint.Postcode
    @Override
    public PostcodeAttribuut getPostcode() {
        return super.getPostcode();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9025, dbObject = DatabaseObjectKern.PERSOON_ADRES__LOCATIEOMSCHRIJVING)
    @Override
    public LocatieomschrijvingAttribuut getLocatieomschrijving() {
        return super.getLocatieomschrijving();
    }

    /**
     * Hook voor Jibx om de communicatieId van de encapsulerende object te zetten. De standaard groep wordt niet geregistreerd in de CommunicatieIdMap.
     *
     * @param ctx de jibx context
     */
    public void jibxPostSetCommunicatieId(final IUnmarshallingContext ctx) {
        setCommunicatieID(((BerichtIdentificeerbaar) ctx.getStackObject(1)).getCommunicatieID());
    }
}
