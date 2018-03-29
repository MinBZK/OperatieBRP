/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.regressie.it.local;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IT_Intake_Compleet.class,
        IT_SA1SP_SynchroniseerPersoon.class,
        IT_SA1VA_VerwijderAfnemerindicatie.class,
        IT_SA1PA_PlaatsAfnemerindicatie.class,
        IT_LV1AL_AutorisatieLevering.class,
        IT_AL1AU_Authenticatie.class,
        IT_SA0AA_AttenderingMetPlaatsenAfnemerindicatie.class,
        IT_SA0MA_MutatieleveringObvAfnemerindicatie.class,
        IT_SA1LM_BepaalLevering.class,
        IT_SA0AT_Attendering.class,
        IT_SA0MD_MutatieleveringObvDoelbinding.class,
        IT_SA1LM_MarkeerVerwerkt.class,
        IT_LV1CP_ControleerPersoonsselectie.class,
        IT_LV1MB_MaakBericht.class,
        IT_LV1MB_MaakBerichtDefinitieregels.class,
        IT_BV0ZP_Zoek_Persoon.class,
        IT_BV0GD_GeefDetailsPersoon.class,
        IT_LV1PB_Protocolleer_bericht.class,
        IT_BRP_Bijhouding.class,
        IT_Geconverteerde_data_test.class,
        IT_AL1AV_AfhandelenVerzoek.class,
        IT_BV0ZA_ZoekPersoonAdres.class,
        IT_BV0GM_GeefMedeBewoners.class,
        IT_ExpressieTaal.class,
        IT_VolledigPersoonsBeeld.class,
        IT_SA1LM_MarkeerVerwerkt.class
})
public class IT_UsecaseSuite {

}