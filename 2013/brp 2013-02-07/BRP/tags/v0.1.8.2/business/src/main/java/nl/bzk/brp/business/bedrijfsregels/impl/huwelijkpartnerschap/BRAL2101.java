/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.huwelijkpartnerschap;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Land;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementatie van bedrijfsregel BRAL2101.
 * <p/>
 * Als land aanvang huwelijk (of geregistreerd partnerschap) Nederland is dan is gemeente aanvang verplicht.
 *
 * @brp.bedrijfsregel BRAL2101
 */
public class BRAL2101 implements BedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRAL2101.class);

    @Override
    public String getCode() {
        return "BRAL2101";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie,
        final Datum datumAanvangGeldigheid)
    {
        final List<Melding> meldingen;

        if (nieuweSituatie == null || nieuweSituatie.getGegevens() == null) {
            meldingen = Collections.emptyList();
            LOGGER.warn("Bedrijfsregel BRAL2101 aangeroepen met null waarde voor nieuwe situatie (of gegevens). "
                + "Bedrijfsregel daarom niet verder uitgevoerd (geen meldingen naar gebruiker).");
        } else {
            if (isHuwelijkOfPartnerschapRelatie(nieuweSituatie) && isRelatieVolbrachtInNederland(nieuweSituatie)
                && nieuweSituatie.getGegevens().getGemeenteAanvang() == null)
            {
                meldingen = Collections.singletonList(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR,
                    MeldingCode.BRAL2101, (Identificeerbaar) nieuweSituatie.getGegevens(), "gemeenteAanvang"));
            } else {
                meldingen = Collections.emptyList();
            }
        }
        return meldingen;
    }

    /**
     * Retourneert of de opgegeven relatie van het soort huwelijk of geregistreerd partnerschap is.
     *
     * @param relatie de relatie die moet worden gecontroleerd.
     * @return of de opgegeven relatie van het soort huwelijk of geregistreerd partnerschap is.
     */
    private boolean isHuwelijkOfPartnerschapRelatie(final Relatie relatie) {
        boolean relatieIsHuwelijkofPartnerschap = true;
        relatieIsHuwelijkofPartnerschap &= relatie != null;
        relatieIsHuwelijkofPartnerschap &= relatie.getSoort() != null;
        relatieIsHuwelijkofPartnerschap &= (relatie.getSoort() == SoortRelatie.HUWELIJK
            || relatie.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        return relatieIsHuwelijkofPartnerschap;
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

}
