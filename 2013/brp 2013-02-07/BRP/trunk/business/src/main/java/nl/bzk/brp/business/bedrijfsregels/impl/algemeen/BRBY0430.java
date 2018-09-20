/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.util.DatumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van bedrijfsregel BRBY0430.
 * <p/>
 * Land aanvang huwelijk (of geregistreerd partnerschap) moet op de datum aanvang huwelijk (of geregistreerd
 * partnerschap) een geldige landcode zijn in stamgegeven "Land" voor de corresponderende periode.
 * Als de datum aanvang huwelijk  (of geregistreerd partnerschap) in de toekomst ligt
 * (datum aanvang huwelijk  > systeemdatum) dan is deze controle op basis van systeemdatum.
 * <p/>
 *
 * @brp.bedrijfsregel BRBY0430
 */
public class BRBY0430 implements ActieBedrijfsRegel<HuwelijkGeregistreerdPartnerschap> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0430.class);

    @Override
    public String getCode() {
        return "BRBY0430";
    }

    @Override
    public List<Melding> executeer(final HuwelijkGeregistreerdPartnerschap huidigeSituatie,
            final HuwelijkGeregistreerdPartnerschap nieuweSituatie, final Actie actie)
    {
        final List<Melding> meldingen;

        if (nieuweSituatie == null || nieuweSituatie.getStandaard() == null) {
            meldingen = Collections.emptyList();
            LOGGER.warn("Bedrijfsregel " + getCode() + "BRBY0430 aangeroepen met null waarde voor nieuwe situatie"
                + " (of gegevens). Bedrijfsregel daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            Land landAanvang = nieuweSituatie.getStandaard().getLandAanvang();
            boolean ongeldigeLand = false;

            if (landAanvang != null) {
                Datum controleDatum = bepaalControleDatum(nieuweSituatie);

                if (!DatumUtil.isGeldigOp(landAanvang.getDatumAanvangGeldigheid(),
                    landAanvang.getDatumEindeGeldigheid(), controleDatum))
                {
                    ongeldigeLand = true;
                }
            }

            if (ongeldigeLand) {
                String tekst = String.format(MeldingCode.BRBY0430.getOmschrijving(), landAanvang.getCode());
                meldingen =
                    Collections.singletonList(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0430,
                        tekst, (Identificeerbaar) nieuweSituatie.getStandaard(), "landAanvang"));
            } else {
                meldingen = Collections.emptyList();
            }
        }
        return meldingen;
    }

    /**
     * Bepaald de datum die gebruikt dient te worden voor de controle van de geldigheid. Dit is de datum aanvang
     * geldigheid van de relatie, of indien deze in de toekomst ligt, dan de huidige (systeem) datum.
     *
     * @param relatie de relatie met daarin de datum van aanvang.
     * @return de datum die gebruikt dient te worden voor controle van geldigheid.
     */
    private Datum bepaalControleDatum(final HuwelijkGeregistreerdPartnerschap relatie) {
        Datum controleDatum = relatie.getStandaard().getDatumAanvang();
        Datum vandaag = DatumUtil.vandaag();

        if (controleDatum == null || controleDatum.getWaarde() == null) {
            LOGGER.warn("Relatie bevat geen datum aanvang. Bedrijfsregel "
                + getCode()
                + " gebruikt daarom systeem datum.");
            controleDatum = vandaag;
        } else if (controleDatum.na(vandaag)) {
            controleDatum = vandaag;
        }

        return controleDatum;
    }
}
