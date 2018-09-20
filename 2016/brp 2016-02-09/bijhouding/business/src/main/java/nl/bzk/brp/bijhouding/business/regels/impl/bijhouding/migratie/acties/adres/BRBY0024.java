/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import nl.bzk.brp.business.regels.VoorBerichtRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Implementatie van bedrijfsregel BRBY0024: Acties mogen niet in de tijd overlappen.
 * <p/>
 * Indien in het bericht meer dan één actie voorkomt (met betrekking tot een objecttype met een enkelvoudige materiele
 * historie), mag geen enkele actie in het bericht een overlap in geldigheid hebben met een andere actie.
 * <p/>
 *
 * Let op (opmerking uit EA): (JDY: ) Deze regel is - zeker in zijn algemeenheid - niet juist: bijvoorbeeld bij
 * gelijktijdige erkenning bij geboorte is overlap wel degelijk toegestaan. Ook bij corrigeren adres - waar de regel
 * zijn oorsprong vindt - is inmiddels geen reden meer overlap te verbieden. Naar alle waarschijnlijkheid komt deze
 * regel te vervallen.
 *
 * @brp.bedrijfsregel BRBY0024
 */
@Named("BRBY0024")
public class BRBY0024 implements VoorBerichtRegel<BijhoudingsBericht> {

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0024;
    }

    /*
     * Deze bedrijfsregel controleert of er overlappende acties zijn en indien dat zo is,
     * wordt er een melding geretourneerd die dit vermeldt.
     * Indien er geen overlappende acties zijn, zal de lijst met meldingen leeg zijn.
     */
    @Override
    public List<BerichtIdentificeerbaar> voerRegelUit(final BijhoudingsBericht bericht) {
        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (bevatLijstOverlappendeActies(bericht.getAdministratieveHandeling().getActies())) {
            objectenDieDeRegelOvertreden.add(bericht.getAdministratieveHandeling());
        }
        return objectenDieDeRegelOvertreden;
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

        return overlappendeActies;
    }

    /**
     * Controleert of twee acties elkaar (deels) overlappen in geldigheid.
     * <p/>
     * Er is sprake van overlap in geldigheid tussen twee Acties A en B als geldt dat: - datum aanvang geldigheid van A
     * < datum einde geldigheid van B EN - datum einde geldigheid van A > datum aanvang geldigheid van B
     *
     * @param actie1 de eerste actie.
     * @param actie2 de tweede actie.
     * @return <code>true</code> indien twee (of meer) acties elkaar (deels) overlappen in geldigheid; anders
     *         <code>false</code>, dus als de acties elkaar niet (ook niet deels) overlappen.
     */
    private boolean overlappendeActies(final Actie actie1, final Actie actie2) {
        return (actie2.getDatumEindeGeldigheid() == null || actie1.getDatumAanvangGeldigheid().voor(
                actie2.getDatumEindeGeldigheid()))
            && (actie1.getDatumEindeGeldigheid() == null || actie1.getDatumEindeGeldigheid().na(
                    actie2.getDatumAanvangGeldigheid()));
    }

}
