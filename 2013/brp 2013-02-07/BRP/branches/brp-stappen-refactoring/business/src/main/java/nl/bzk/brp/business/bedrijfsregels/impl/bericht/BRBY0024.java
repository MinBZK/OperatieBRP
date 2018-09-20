/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.bericht;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.bzk.brp.business.bedrijfsregels.BerichtBedrijfsRegel;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;

/**
 * Implementatie van bedrijfsregel BRBY0024: Acties mogen niet in de tijd overlappen.
 * <p/>
 * Indien in het bericht meer dan één actie voorkomt (met betrekking tot een objecttype met een enkelvoudige materiele
 * historie), mag geen enkele actie in het bericht een overlap in geldigheid hebben met een andere actie.
 *
 * @brp.bedrijfsregel BRBY0024
 */
public class BRBY0024 implements BerichtBedrijfsRegel<AbstractBijhoudingsBericht> {

    @Override
    public String getCode() {
        return "BRBY0024";
    }

    /**
     * Bedrijfsregel controleert of er overlappende acties zijn en indien dat zo is, wordt er een melding geretourneerd
     * die dit vermeldt. Indien er geen overlappende acties zijn, zal de lijst met meldingen leeg zijn.
     *
     * @param bericht het bericht waarvoor de bedrijfsregel moet worden uitgevoerd.
     * @return een lijst met eventuele meldingen indien het bericht niet voldoet aan de bedrijfsregel.
     */
    @Override
    public List<Melding> executeer(final AbstractBijhoudingsBericht bericht) {
        List<Melding> meldingen;
        if (bericht != null && bevatLijstOverlappendeActies(bericht.getAdministratieveHandeling().getActies())) {
            meldingen =
                Arrays.asList(new Melding(SoortMelding.FOUT, MeldingCode.BRBY0024, bericht, null));
        } else {
            meldingen = Collections.EMPTY_LIST;
        }
        return meldingen;
    }

    /**
     * Controleert of een lijst met acties eventueel overlappende acties bevat (qua geldigheid).
     *
     * @param acties de lijst met acties die gecontroleerd worden.
     * @return <code>true</code> indien er een overlap zit tussen twee (of meer) van de acties;
     *         anders <code>false</code>.
     */
    private boolean bevatLijstOverlappendeActies(final List<ActieBericht> acties) {
        boolean overlappendeActies = false;

        if (acties != null && acties.size() > 1) {
            for (int i = 0; i < acties.size(); i++) {
                for (int j = i + 1; j < acties.size(); j++) {
                    if (overlappendeActies(acties.get(i), acties.get(j))) {
                        overlappendeActies = true;
                        break;
                    }
                }
                if (overlappendeActies) {
                    break;
                }
            }
        }
        return overlappendeActies;
    }

    /**
     * Controleert of twee acties elkaar (deels) overlappen in geldigheid.
     * <p/>
     * Er is sprake van overlap in geldigheid tussen twee Acties A en B als geldt dat:
     * - datum aanvang geldigheid van A < datum einde geldigheid van B
     * EN
     * - datum einde geldigheid van A > datum aanvang geldigheid van B
     *
     * @param actie1 de eerste actie.
     * @param actie2 de tweede actie.
     * @return <code>true</code> indien twee (of meer) acties elkaar (deels) overlappen in geldigheid; anders
     *         <code>false</code>, dus als de acties elkaar niet (ook niet deels) overlappen.
     */
    private boolean overlappendeActies(final Actie actie1, final Actie actie2) {
        return (actie2.getDatumEindeGeldigheid() == null || actie1.getDatumAanvangGeldigheid() == null
            || (actie1.getDatumAanvangGeldigheid().voor(actie2.getDatumEindeGeldigheid())))
            && (actie1.getDatumEindeGeldigheid() == null || actie2.getDatumAanvangGeldigheid() == null
            || actie1.getDatumEindeGeldigheid().na(actie2.getDatumAanvangGeldigheid()));
    }

}
