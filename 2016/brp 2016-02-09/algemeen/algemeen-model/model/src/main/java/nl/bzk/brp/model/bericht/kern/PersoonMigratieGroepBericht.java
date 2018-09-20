/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonMigratieGroep;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVeld;
import nl.bzk.brp.model.validatie.constraint.ConditioneelVelden;
import nl.bzk.brp.model.validatie.constraint.Lengte;


/**
 * Validatie voor persoon migratie groep: BRAL1118: Aangever adreshouding moet gevuld worden indien reden wijziging adres is "Aangifte door persoon".
 * BRAL2038: Binnen de buitenlandse adresregels is regel twee verplicht. BRAL2039: Buitenlandse adresregel 1 is verplicht als regel 3 is ingevuld.
 * BRAL9027: Buitenlandse adresregel mag niet uit meer dan 35 karakters bestaan.
 */
@ConditioneelVelden({
    @ConditioneelVeld(wanneerInhoudVanVeld = "redenWijzigingMigratie.waarde.code",
        isGelijkAan = RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_STRING,
        danVoldoetRegelInInhoudVanVeld = "aangeverMigratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON, code = Regel.BRAL1118,
        message = "BRAL1118", dbObject = DatabaseObjectKern.PERSOON__AANGEVER_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: Alleen als regel 2 is ingevuld, mogen andere regels ingevuld zijn.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel1Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel2Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2038, message = "BRAL2038_1",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: Alleen als regel 2 is ingevuld, mogen andere regels ingevuld zijn.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel3Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel2Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2038, message = "BRAL2038_3",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: Alleen als regel 2 is ingevuld, mogen andere regels ingevuld zijn.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel4Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel2Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2038, message = "BRAL2038_4",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: Alleen als regel 2 is ingevuld, mogen andere regels ingevuld zijn.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel5Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel2Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2038, message = "BRAL2038_5",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: Alleen als regel 2 is ingevuld, mogen andere regels ingevuld zijn.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel6Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel2Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2038, message = "BRAL2038_6",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE),
    @ConditioneelVeld(
        // Voor buitenlandse adres: regel3 mag alleen gevuld zijn als 1 ook gevuld is.
        wanneerInhoudVanVeld = "buitenlandsAdresRegel3Migratie",
        isGelijkAan = ConditioneelVeld.WILDCARD_WAARDE_CODE,
        danVoldoetRegelInInhoudVanVeld = "buitenlandsAdresRegel1Migratie",
        aanConditieRegel = ConditioneelVeld.ConditieRegel.SYNCHROON_IF_NULL_DONT_CARE,
        code = Regel.BRAL2039, message = "BRAL2039",
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL1_MIGRATIE) })
public final class PersoonMigratieGroepBericht extends AbstractPersoonMigratieGroepBericht implements Groep,
    PersoonMigratieGroep, MetaIdentificeerbaar
{

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL1_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel1Migratie() {
        return super.getBuitenlandsAdresRegel1Migratie();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL2_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel2Migratie() {
        return super.getBuitenlandsAdresRegel2Migratie();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL3_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel3Migratie() {
        return super.getBuitenlandsAdresRegel3Migratie();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL4_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel4Migratie() {
        return super.getBuitenlandsAdresRegel4Migratie();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL5_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel5Migratie() {
        return super.getBuitenlandsAdresRegel5Migratie();
    }

    @Lengte(min = 0, max = 35, code = Regel.BRAL9027,
        dbObject = DatabaseObjectKern.PERSOON__BUITENLANDS_ADRES_REGEL6_MIGRATIE)
    @Override
    public AdresregelAttribuut getBuitenlandsAdresRegel6Migratie() {
        return super.getBuitenlandsAdresRegel6Migratie();
    }

}
