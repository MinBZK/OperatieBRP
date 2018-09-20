/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetHistorischBesef;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.DatumBasisAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonHisVolledigUtil;
import nl.bzk.brp.model.hisvolledig.util.RelatieHisVolledigUtil;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;


/**
 * Indiener naturalisatieverzoek moet voldoen aan termijn, dwz:
 * òf Minstens 5 jaar ingezetene zijn (in willekeurige periodes bij elkaar opgeteld),
 * òf Minstens 3 jaar onafgebroken een verbintenis hebben met een Nederlander.
 *
 * @brp.bedrijfsregel BRBY0158
 */
@Named("BRBY0158")
public class BRBY0158 implements VoorActieRegelMetHistorischBesef<PersoonHisVolledig, PersoonBericht> {

    // Als benadering nemen we hier een aantal van 365 dagen per jaar, aangezien voor een optelling
    // van periodes anders niet te checken is wat samen 1 jaar is.
    private static final int AANTAL_DAGEN_PER_JAAR = 365;

    private static final int MINIMAAL_AANTAL_JAREN_INGEZETENE = 5;
    private static final int MINIMAAL_AANTAL_JAREN_ONAFGEBROKEN_VERBINTENIS_MET_NEDERLANDER = 3;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0158;
    }

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonHisVolledig huidigeSituatie,
            final PersoonBericht nieuweSituatie, final Actie actie, final Map<String, PersoonView> bestaandeBetrokkenen)
    {
        final List<BerichtEntiteit> overtreders = new ArrayList<>();

        if (isNaturalisatieverzoek(nieuweSituatie, actie) && !voldoetAanTermijn(huidigeSituatie)) {
            overtreders.add(nieuweSituatie);
        }

        return overtreders;
    }

    private boolean isNaturalisatieverzoek(final PersoonBericht nieuweSituatie, final Actie actie) {
        return NationaliteitRegelUtil.verkrijgtNederlandseNationaliteit(nieuweSituatie)
            && NationaliteitRegelUtil.heeftKoninklijkbesluitBron(actie);
    }

    private boolean voldoetAanTermijn(final PersoonHisVolledig persoon) {
        return persoon == null
            || isMinstensVijfJaarIngezetene(persoon)
            || heeftMinstensDrieJaarOnafgebrokenEenVerbintenisMetNederlander(persoon);
    }

    /**
     * Bekijkt of een persoon in zijn gehele leven minstens 5 jaar in Nederland heeft gewoont.
     * Dit wordt gecheckt door van alle historie records met bijhouding ingezetene
     * de geldigheid periodes bij elkaar op te tellen.
     *
     * @param persoon de persoon
     * @return of de persoon minstens 5 jaar ingezetene is
     */
    private boolean isMinstensVijfJaarIngezetene(final PersoonHisVolledig persoon) {
        int totaalAantalDagenIngezetene = 0;
        // Blader door alle niet vervallen records.

        for (final HisPersoonBijhoudingModel bijhouding : persoon.getPersoonBijhoudingHistorie().getNietVervallenHistorie()) {
            // Als voor dit record de bijhouding ingezetene is.
            if (bijhouding.getBijhoudingsaard().getWaarde() == Bijhoudingsaard.INGEZETENE) {
                final DatumBasisAttribuut eindeGeldigheid;
                if (bijhouding.getDatumEindeGeldigheid() != null) {
                    eindeGeldigheid = bijhouding.getDatumEindeGeldigheid();
                } else {
                    eindeGeldigheid = DatumAttribuut.vandaag();
                }
                final int aantalDagen = bijhouding.getDatumAanvangGeldigheid().aantalDagenVerschil(eindeGeldigheid);
                // Tel het aantal geldige dagen op bij het totaal.
                totaalAantalDagenIngezetene += aantalDagen;
            }
        }
        return totaalAantalDagenIngezetene >= MINIMAAL_AANTAL_JAREN_INGEZETENE * AANTAL_DAGEN_PER_JAAR;
    }

    /**
     * Bekijkt of een persoon minstens 3 jaar onafgebroken een verbintenis heeft met een Nederlander.
     *
     * @param persoon de persoon
     * @return of een persoon minstens 3 jaar onafgebroken een verbintenis heeft met een Nederlander
     */
    private boolean heeftMinstensDrieJaarOnafgebrokenEenVerbintenisMetNederlander(
            final PersoonHisVolledig persoon)
    {
        boolean voldoet = false;
        final PersoonHisVolledig partner = getOnafgebrokenPartnerVoorDrieJaar(persoon);
        if (partner != null) {
            voldoet = isNederlander(partner);
        }
        return voldoet;
    }

    /**
     * Geef de persoon terug die de afgelopen 3 jaar onafgebroken partner is geweest van de meegegeven persoon.
     *
     * @param persoon de persoon
     * @return de partner (of null, indien geen geschikte partner gevonden)
     */
    private PersoonHisVolledig getOnafgebrokenPartnerVoorDrieJaar(final PersoonHisVolledig persoon) {
        PersoonHisVolledig partner = null;
        // Bekijk alleen de actuele (niet beeindigde) hgp's.
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig hgp : PersoonHisVolledigUtil.getActueleHGPs(persoon)) {
            // Optie 1: huwelijk of partnerschap begon minstens 3 jaar geleden.
            if (hgp.getRelatieHistorie().getActueleRecord().getDatumAanvang().aantalDagenVerschil(DatumAttribuut.vandaag())
                    >= MINIMAAL_AANTAL_JAREN_ONAFGEBROKEN_VERBINTENIS_MET_NEDERLANDER * AANTAL_DAGEN_PER_JAAR)
            {
                partner = hgp.geefPartnerVan(persoon).getPersoon();
            } else if (hgp.getSoort().getWaarde() == SoortRelatie.HUWELIJK) {
                // Optie 2: huwelijk is minder dan 3 jaar geleden, maar is omgezet vanuit een partnerschap,
                // dat al wel minstens 3 jaar geleden begon.
                final GeregistreerdPartnerschapHisVolledig omgezetPartnerschap = RelatieHisVolledigUtil
                        .getVoorgaandPartnerschap((HuwelijkHisVolledig) hgp);
                if (omgezetPartnerschap != null && omgezetPartnerschap.getRelatieHistorie()
                        .getActueleRecord().getDatumAanvang().aantalDagenVerschil(DatumAttribuut.vandaag())
                        >= MINIMAAL_AANTAL_JAREN_ONAFGEBROKEN_VERBINTENIS_MET_NEDERLANDER * AANTAL_DAGEN_PER_JAAR)
                {
                    partner = hgp.geefPartnerVan(persoon).getPersoon();
                }
            }
        }
        return partner;
    }

    /**
     * Checkt of de meegegeven persoon op dit moment Nederlander is.
     *
     * @param persoon de persoon
     * @return of de meegegeven persoon Nederlander is
     */
    private boolean isNederlander(final PersoonHisVolledig persoon) {
        for (final PersoonNationaliteit persoonNationaliteit : new PersoonView(persoon).getNationaliteiten()) {
            if (persoonNationaliteit.getNationaliteit().getWaarde().getCode().equals(
                    NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE))
            {
                return true;
            }
        }
        return false;
    }

}
