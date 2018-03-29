/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle dat bijhoudingspartij op aangeboden en gevonden persoonslijst gelijk is.
 */
public final class PlControleGevondenBlokkeringssituatieIsJuist implements PlControle {

    private static final String RNI_PARTIJ_CODE = "199901";
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
    private final BrpDalService brpDalService;

    /**
     * Constructor voor deze implementatie van {@link PlControle}.
     * @param brpDalService de {@link BrpDalService}
     */
    public PlControleGevondenBlokkeringssituatieIsJuist(final BrpDalService brpDalService) {
        this.brpDalService = brpDalService;
    }

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_GEVONDEN_PERSOONSLIJST_BEVAT_JUISTE_BLOKKERINGSSITUATIE);

        // Haal blokkeringsinformatie op
        final Blokkering gevondenBlokkeringsInformatie = brpDalService.vraagOpBlokkering(dbPersoonslijst.getActueelAdministratienummer());
        boolean result = true;

        if (gevondenBlokkeringsInformatie != null) {
            // Gemeente van bijhouding is GBA dan wel RNI
            final Partij bijhoudingsPartijAangebodenPersoonslijst = brpDalService.geefPartij(getBijhoudingsPartij(context.getBrpPersoonslijst()));
            result = verzendendeGemeenteIsGelijkAanBlokkeringGemeenteNaar(gevondenBlokkeringsInformatie, context.getVerzoek());
            result = result && isPartijGBAGemeenteOfRni(bijhoudingsPartijAangebodenPersoonslijst);

            // Gevonden waarde
            final Partij bijhoudingsPartijBrpPersoonslijst = brpDalService.geefPartij(getBijhoudingsPartij(dbPersoonslijst));
            result = result && isPartijBRPGemeente(bijhoudingsPartijBrpPersoonslijst);
        }

        logging.logResultaat(result);
        return result;
    }

    private BrpPartijCode getBijhoudingsPartij(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpBijhoudingInhoud> stapel = persoonslijst.getBijhoudingStapel();
        if (stapel == null) {
            return null;
        }

        return stapel.getActueel().getInhoud().getBijhoudingspartijCode();
    }

    private boolean isPartijGBAGemeenteOfRni(final Partij partij) {
        boolean result = false;
        if (partij != null) {
            final Integer datumOvergangNaarBrp = partij.getDatumOvergangNaarBrp();
            result = Objects.equals(RNI_PARTIJ_CODE, partij.getCode());
            if (!result) {
                result = datumOvergangNaarBrp == null || datumOvergangNaarBrp > Integer.valueOf(sdf.format(new Date()));
            }
        }
        return result;
    }

    private boolean isPartijBRPGemeente(final Partij partij) {
        boolean result = false;
        if (partij != null) {
            final Integer datumOvergangNaarBrp = partij.getDatumOvergangNaarBrp();
            result = datumOvergangNaarBrp != null && datumOvergangNaarBrp < Integer.valueOf(sdf.format(new Date()));
        }
        return result;
    }

    private boolean verzendendeGemeenteIsGelijkAanBlokkeringGemeenteNaar(final Blokkering blokkering, final SynchroniseerNaarBrpVerzoekBericht bericht) {
        return bericht != null && blokkering.getGemeenteCodeNaar().equals(bericht.getVerzendendeGemeente());
    }
}
