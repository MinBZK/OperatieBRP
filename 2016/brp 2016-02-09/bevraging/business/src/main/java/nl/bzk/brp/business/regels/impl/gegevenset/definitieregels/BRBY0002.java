/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.gegevenset.definitieregels;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.hisvolledig.momentview.kern.BetrokkenheidView;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.hisvolledig.util.PersoonUtil;
import nl.bzk.brp.model.logisch.kern.HuwelijkGeregistreerdPartnerschap;

/**
 * Deze klasse bevat definities voor kandidaat vader.
 * <p/>
 * Als in de BRP een mannelijk Persoon staat geregistreerd (als Ingeschrevene of NietIngeschrevene), die voldoet aan
 * één van onderstaande voorwaarden, dan en slechts dan is deze man een kandidaat om als (juridisch) vader aangemerkt
 * te worden van het Kind van de moeder.
 * <p/>
 * 1. De man was echtgenoot van de moeder echter dit Huwelijk is vóór de geboortedatum ontbonden vanwege het
 * overlijden (van de man) terwijl het Kind is geboren binnen 306 dagen na de ontbinding van het Huwelijk (met andere
 * woorden: datum geboorte - 306 dagen <= datum einde relatie < datum geboorte). Indien een man ("streng") aan deze
 * voorwaarde voldoet, zijn overige kandidaten uitgesloten.
 * <p/>
 * 2. De man is op de geboortedatum echtgenoot van de moeder én er is geen andere man geregistreerd die ("streng") aan
 * voorwaarde 3 voldoet met een datum erkenning ongeboren vrucht < datum aanvang relatie (Huwelijk).
 * <p/>
 * 3. De man staat op de geboortedatum als erkenner ongeboren vrucht van moeder geregistreerd terwijl het Kind is
 * geboren binnen 306 dagen na erkenning (met andere woorden: datum geboorte - 306 dagen <= datum erkenning ongeboren
 * vrucht < datum geboorte).
 * <p/>
 * Opmerkingen:
 * 1. Moeder is de Persoon die in de bijhouding is aangeduid als de Ouder uit wie het kind is voortgekomen.
 * 2. Het Huwelijk tussen moeder en man is wegens overlijden ontbonden als in betreffende Relatie staat vermeld dat
 * Reden einde is "Overlijden"
 * 3. De 306 dagen termijn in geldt alleen als in de BRP kan worden vastgesteld dat de man de Nederlandse Nationaliteit
 * had op datum einde relatie, in alle andere gevallen zoals geen Nederlandse Nationaliteit, man is NietIngeschrevene
 * of datum einde relatie (gedeeltelijk) onbekend - moet 365 dagen worden genomen.
 * 4. Indien de vader een NietIngeschrevene is, dan moet de man een NietIngeschrevene  zijn bij de moeder met exact
 * dezelfde gegevens cf BRBY0007: Vergelijken Niet-Ingeschreven Personen.
 * 5. De regel kan meer dan 1 kandidaat opleveren vanwege de verschillende voorwaarden, maar ook vanwege de vorige
 * opmerking.
 * <p/>
 * Hernoemd van BRPUC50110 naar BRBY0002.
 *
 * @brp.bedrijfsregel BRBY0002
 */
@Named("BRBY0002")
public class BRBY0002 {

    private static final int DAGEN_NA_OVERLIJDEN_NL_NATIONALITEIT = 306;
    private static final int DAGEN_NA_OVERLIJDEN_NIET_NL_NATIONALITEIT = 365;

    /**
     * Bepaalt de kandidaten vader op basis van de moeder en de geboortedatum van het kind.
     *
     * @param ouwkiv            de ouwkiv, ouder uit wie kind is voortgekomen
     * @param geboorteDatumKind de geboorte datum kind
     * @return de lijst id's van kandidaten vader
     */
    public List<Integer> bepaalKandidatenVader(final PersoonView ouwkiv,
                                               final DatumEvtDeelsOnbekendAttribuut geboorteDatumKind)
    {
        final List<Integer> kandidatenVader = new ArrayList<>();
        // Zoek in de relaties, welke personen hebben een 'huwelijk' relatie met de ouwkiv, rekening houdend
        // met het geslacht van de partner (== man) en dat de relatie geldig is op het moment van de
        // peilDatum (aka. geboorteDatumKind).
        final List<BetrokkenheidView> partners = vindPartners(ouwkiv);

        for (final BetrokkenheidView partner : partners) {
            final HuwelijkGeregistreerdPartnerschap relatieKandidaatVader =
                    (HuwelijkGeregistreerdPartnerschap) partner.getRelatie();
            if (isRedenEindeOverlijden(relatieKandidaatVader)
                    && valtGeboorteDatumBinnenRangeHuwelijkVader(new DatumAttribuut(geboorteDatumKind), partner))
            {
                // BRBY0002
                // 1. De man was echtgenoot van de moeder echter dit Huwelijk is vóór de geboortedatum ontbonden
                // vanwege het overlijden (van de man) terwijl het Kind is geboren binnen 306 dagen na de ontbinding
                // van het Huwelijk (met andere woorden: datum geboorte - 306 dagen <= datum einde relatie < datum
                // geboorte). Indien een man ("streng") aan deze voorwaarde voldoet, zijn overige kandidaten
                // uitgesloten.
                //
                // De 306 dagen termijn geldt alleen als in de BRP kan worden vastgesteld dat de man de
                // Nederlandse nationaliteit had op datum overlijden, in alle andere gevallen zoals geen Nederlandse
                // nationaliteit, man is Niet-ingeschrevene of datum overlijden (gedeeltelijk) onbekend - moet 365
                // dagen worden genomen.
                kandidatenVader.add(partner.getPersoon().getID());
            }
        }

        if (kandidatenVader.size() == 0) {
            for (final BetrokkenheidView partner : partners) {
                final HuwelijkGeregistreerdPartnerschap relatieKandidaatVader =
                        (HuwelijkGeregistreerdPartnerschap) partner.getRelatie();
                final DatumEvtDeelsOnbekendAttribuut
                        huwelijkAanvang = relatieKandidaatVader.getStandaard().getDatumAanvang();
                final DatumEvtDeelsOnbekendAttribuut
                        huwelijkEinde = relatieKandidaatVader.getStandaard().getDatumEinde();

                if (geboorteDatumKind.isGeldigTussen(huwelijkAanvang, huwelijkEinde)) {
                    kandidatenVader.add(partner.getPersoon().getID());
                }
            }
        }

        return kandidatenVader;
    }

    /**
     * Controleert of de geboortedatum valt binnen de range van datum aanvang en datum einde + marge van de huwelijk.
     *
     * @param geboorteDatum  de geboorte datum
     * @param kandidaatVader de huwelijk van de vader wanneer gekeken moet worden
     * @return true als het binnen de range valt.
     */
    private boolean valtGeboorteDatumBinnenRangeHuwelijkVader(final DatumAttribuut geboorteDatum,
                                                              final BetrokkenheidView kandidaatVader)
    {
        final DatumEvtDeelsOnbekendAttribuut datumAanvang =
                ((HuwelijkGeregistreerdPartnerschap) kandidaatVader.getRelatie()).getStandaard().getDatumAanvang();
        final DatumEvtDeelsOnbekendAttribuut overlijdenDatum =
                ((HuwelijkGeregistreerdPartnerschap) kandidaatVader.getRelatie()).getStandaard().getDatumEinde();

        final DatumAttribuut overlijdenDatumPlusMarge = new DatumAttribuut(overlijdenDatum);
        // TODO onbekende datum nog niet in scope
        if (PersoonUtil.heeftActueleNederlandseNationaliteit(kandidaatVader.getPersoon())) {
            overlijdenDatumPlusMarge.voegDagToe(DAGEN_NA_OVERLIJDEN_NL_NATIONALITEIT);
        } else {
            overlijdenDatumPlusMarge.voegDagToe(DAGEN_NA_OVERLIJDEN_NIET_NL_NATIONALITEIT);
        }

        return geboorteDatum.na(datumAanvang) && (geboorteDatum.getWaarde()
                .equals(overlijdenDatumPlusMarge.getWaarde()) || geboorteDatum.voor(overlijdenDatumPlusMarge));
    }

    /**
     * Controleert of de reden beëindiging om een overlijden gaat.
     *
     * @param relatieKandidaatVader de huwelijk van de vader waar naar gekeken moet worden.
     * @return true als het om overlijden gaat.
     */
    private boolean isRedenEindeOverlijden(final HuwelijkGeregistreerdPartnerschap relatieKandidaatVader) {
        return relatieKandidaatVader.getStandaard().getRedenEinde() != null
                && relatieKandidaatVader.getStandaard().getRedenEinde().getWaarde().getCode().getWaarde()
                .equals(RedenEindeRelatieCodeAttribuut.REDEN_EINDE_RELATIE_OVERLIJDEN_CODE.getWaarde());
    }

    /**
     * Vind alle mannen die een huwelijk partnerschap relatie hebben met de moeder.
     * TODO: niet ouwkiv hoeft niet altijd man te zijn (nieuwe wetgeving)
     *
     * @param ouwkiv de ouder uit wie het kind is voortgekomen
     * @return lijst van kandidaten vader
     */
    private List<BetrokkenheidView> vindPartners(final PersoonView ouwkiv) {
        final List<BetrokkenheidView> resultaat = new ArrayList<>();

        for (final BetrokkenheidView betrokkenheid : ouwkiv.getBetrokkenheden()) {
            if (betrokkenheid.getRelatie().getSoort().getWaarde() == SoortRelatie.HUWELIJK) {
                for (final BetrokkenheidView kandidaatBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (kandidaatBetrokkenheid.getPersoon().getGeslachtsaanduiding()
                            .getGeslachtsaanduiding().getWaarde() == Geslachtsaanduiding.MAN)
                    {
                        resultaat.add(kandidaatBetrokkenheid);
                    }
                }
            }
        }

        return resultaat;
    }
}
