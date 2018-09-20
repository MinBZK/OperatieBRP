/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementatie van bedrijfsregel BRBY0429.
 * <p/>
 * Gemeente aanvang huwelijk (of geregistreerd partnerschap) moet op de datum aanvang huwelijk (of geregistreerd
 * partnerschap) een geldige gemeentecode zijn in stamgegeven "Partij" voor de corresponderende periode. Als de datum
 * aanvang huwelijk (of geregistreerd partnerschap) in de toekomst ligt (datum aanvang huwelijk  > systeemdatum) dan is
 * deze controle op basis van systeemdatum.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0429
 */
public class BRBY0429 implements BedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0429.class);

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public String getCode() {
        return "BRBY0429";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        final List<Melding> meldingen;

        if (nieuweSituatie == null || nieuweSituatie.getGegevens() == null) {
            meldingen = Collections.emptyList();
            LOGGER.warn("Bedrijfsregel BRBY0429 aangeroepen met null waarde voor nieuwe situatie (of gegevens). "
                + "Bedrijfsregel daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            Partij gemeenteAanvang = nieuweSituatie.getGegevens().getGemeenteAanvang();
            boolean ongeldigeGemeente = false;

            if (gemeenteAanvang != null && isRelatieVolbrachtInNederland(nieuweSituatie)) {
                Datum controleDatum = bepaalControleDatum(nieuweSituatie);

                if (!geldigeGemeenteOpDatum(gemeenteAanvang, controleDatum)) {
                    ongeldigeGemeente = true;
                }
            }

            if (ongeldigeGemeente) {
                String meldingTekst = String.format("Gemeente %s is niet correct.", gemeenteAanvang.getGemeentecode());
                meldingen = Collections.singletonList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR,
                    MeldingCode.BRBY0429, meldingTekst, (Identificeerbaar) nieuweSituatie.getGegevens(),
                    "gemeenteAanvang"));
            } else {
                meldingen = Collections.emptyList();
            }
        }
        return meldingen;
    }

    /**
     * Retourneert of de relatie is aangevangen/voltrokken in Nederland.
     *
     * @param relatie de relatie.
     * @return of de relatie is aangevangen/voltrokken in Nederland.
     */
    private boolean isRelatieVolbrachtInNederland(final Relatie relatie) {
        Land landAanvangRelatie = relatie.getGegevens().getLandAanvang();

        boolean resultaat;
        if (landAanvangRelatie != null && landAanvangRelatie.getCode() != null) {
            resultaat = landAanvangRelatie.getCode().equals(BrpConstanten.NL_LAND_CODE);
        } else {
            resultaat = false;
        }
        return resultaat;
    }

    /**
     * Bepaald de datum die gebruikt dient te worden voor de controle van de geldigheid. Dit is de datum aanvang
     * geldigheid van de relatie, of indien deze in de toekomst ligt, dan de huidige (systeem) datum.
     *
     * @param relatie de relatie met daarin de datum van aanvang.
     * @return de datum die gebruikt dient te worden voor controle van geldigheid.
     */
    private Datum bepaalControleDatum(final Relatie relatie) {
        Datum controleDatum = relatie.getGegevens().getDatumAanvang();
        Datum vandaag = DatumUtil.vandaag();

        if (controleDatum == null || controleDatum.getWaarde() == null) {
            LOGGER.warn("Relatie bevat geen datum aanvang. Bedrijfsregel BRBY0429 gebruikt daarom systeem datum.");
            controleDatum = vandaag;
        } else if (controleDatum.na(vandaag)) {
            controleDatum = vandaag;
        }

        return controleDatum;
    }

    /**
     * Controleert of de opgegeven gemeente een geldige gemeente was op de opgegeven datum.
     *
     * @param gemeente de gemeente die gecontroleerd dient te worden.
     * @param controleDatum de datum waarop de gemeente geldig dient te zijn.
     * @return of de gemeente geldig was op opgegeven datum.
     */
    private boolean geldigeGemeenteOpDatum(final Partij gemeente, final Datum controleDatum) {
        boolean resultaat;

        try {
            Partij volledigOpgehaaldeGemeente;

            if (gemeente.getDatumAanvang() == null) {
                volledigOpgehaaldeGemeente = referentieDataRepository.vindGemeenteOpCode(gemeente.getGemeentecode());
            } else {
                volledigOpgehaaldeGemeente = gemeente;
            }

            resultaat = !controleDatum.voor(volledigOpgehaaldeGemeente.getDatumAanvang())
                && (volledigOpgehaaldeGemeente.getDatumEinde() == null || controleDatum
                .voor(volledigOpgehaaldeGemeente.getDatumEinde()));
        } catch (OnbekendeReferentieExceptie e) {
            resultaat = false;
        }
        return resultaat;
    }

}
