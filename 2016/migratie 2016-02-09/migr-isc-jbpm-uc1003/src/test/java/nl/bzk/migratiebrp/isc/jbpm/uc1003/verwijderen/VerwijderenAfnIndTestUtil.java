/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003.verwijderen;

import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AfnemersindicatieFoutcodeType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.VerwerkAfnemersindicatieAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesAutorisatieRegisterAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.VerwerkAfnemersindicatieAntwoordBericht;

/**
 * Utils voor tests voor uc1003.
 */
public final class VerwijderenAfnIndTestUtil {

    private VerwijderenAfnIndTestUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    public static Av01Bericht maakAv01Bericht(final String afnemerCode) {
        return maakAv01Bericht(afnemerCode, null);
    }

    public static Av01Bericht maakAv01Bericht(final String afnemerCode, final String aNummer) {
        final Av01Bericht av01Bericht = new Av01Bericht();
        av01Bericht.setBronGemeente(afnemerCode);
        av01Bericht.setDoelGemeente("3000200");
        if (aNummer != null) {
            av01Bericht.setANummer(aNummer);
        }
        return av01Bericht;
    }

    public static VerwerkAfnemersindicatieAntwoordBericht maakVerwerkAfnemersindicatieAntwoordBericht(final String foutCode) {
        final VerwerkAfnemersindicatieAntwoordType antwoordType = new VerwerkAfnemersindicatieAntwoordType();
        antwoordType.setStatus(StatusType.OK);

        if (foutCode != null) {
            antwoordType.setFoutcode(AfnemersindicatieFoutcodeType.fromValue(foutCode));
        }

        return new VerwerkAfnemersindicatieAntwoordBericht(antwoordType);
    }

    public static LeesAutorisatieRegisterAntwoordBericht maakLeesAfnemerRegisterAntwoordBericht() {
        final LeesAutorisatieRegisterAntwoordType type = new LeesAutorisatieRegisterAntwoordType();
        type.setStatus(StatusType.OK);
        type.setAutorisatieRegister(new AutorisatieRegisterType());
        type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType("580001", 100034, 200001, 200002, null, null));
        type.getAutorisatieRegister().getAutorisatie().add(maakAutorisatieRegisterVoorkomenType("580002", 100035, null, null, 200003, null));

        return new LeesAutorisatieRegisterAntwoordBericht(type);
    }

    private static AutorisatieRegisterVoorkomenType maakAutorisatieRegisterVoorkomenType(
        final String partijCode,
        final int leveringsautorisatieId,
        final Integer plaatsenAfnIndDienstId,
        final Integer verwijderenAfnIndDienstId,
        final Integer bevragenPersoonDienstId,
        final Integer bevragenAdresDienstId)
    {
        final AutorisatieRegisterVoorkomenType resultaat = new AutorisatieRegisterVoorkomenType();
        resultaat.setPartijCode(partijCode);
        resultaat.setToegangLeveringsautorisatieId(leveringsautorisatieId);
        resultaat.setPlaatsenAfnemersindicatiesDienstId(plaatsenAfnIndDienstId);
        resultaat.setVerwijderenAfnemersindicatiesDienstId(verwijderenAfnIndDienstId);
        resultaat.setBevragenPersoonDienstId(bevragenPersoonDienstId);
        resultaat.setBevragenAdresDienstId(bevragenAdresDienstId);
        return resultaat;
    }
}
