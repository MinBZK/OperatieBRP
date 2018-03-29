/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De base class voor het registreren van een aanvang van een huwelijk of geregistreerd partnerschap.
 */
public abstract class AbstractRegistratieAanvangHuwelijkOfGpActieElement extends AbstractActieElement {

    @XmlTransient
    private final HuwelijkOfGpElement huwelijkOfGp;

    /**
     * Maakt een AbstractRegistratieHuwelijkOfGpActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param huwelijkOfGp huwelijkOfGp
     */
    public AbstractRegistratieAanvangHuwelijkOfGpActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final AbstractHuwelijkOfGpElement huwelijkOfGp) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
        ValidatieHelper.controleerOpNullWaarde(huwelijkOfGp, "huwelijkOfGp");
        this.huwelijkOfGp = huwelijkOfGp;
    }

    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();
        controleerGeenVerwantschapBetrokkenheden(meldingen);
        valideerRelatieGemeenteMetDocument(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R1868)
    private void controleerGeenVerwantschapBetrokkenheden(final List<MeldingElement> meldingen) {
        final List<BijhoudingPersoon> hoofdPersonen = getHoofdPersonen();
        if (!hoofdPersonen.isEmpty()) {
            final BijhoudingPersoon bijhoudingPersoonA = hoofdPersonen.get(0);
            final BijhoudingPersoon bijhoudingPersoonB = hoofdPersonen.size() == 2 ? hoofdPersonen.get(1) : null;

            if (Persoon.bestaatVerwantschap(bijhoudingPersoonA, bijhoudingPersoonB)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1868, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R1862)
    private void valideerRelatieGemeenteMetDocument(final List<MeldingElement> meldingen) {
        final StringElement gemeenteAanvangCode = this.getHuwelijkOfGp().getRelatieGroep()
                .getGemeenteAanvangCode();
        controleerGeenRegisterAkteOfGemeenteKomenOvereen(this.getBronReferenties(), gemeenteAanvangCode, meldingen, Regel.R1862);
    }

    @Override
    public final BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        maakPseudoPersoonAanAlsNodig(bericht);
        final BRPActie actie;
        if (zijnAlleHoofdPersonenVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            huwelijkOfGp.maakRelatieEntiteitEnBetrokkenen(actie, getDatumAanvangGeldigheid().getWaarde());
            for (final Persoon hoofdPersoon : getHoofdPersonen()) {
                hoofdPersoon.leidtNaamgebruikAf(actie, false);
            }
        } else {
            actie = null;
        }
        return actie;
    }

    private void maakPseudoPersoonAanAlsNodig(final BijhoudingVerzoekBericht bericht) {
        final List<BijhoudingPersoon> hoofdPersonen = getHoofdPersonen();
        if (hoofdPersonen.size() > 1) {
            final List<BijhoudingPersoon> verwerkbareHoofdPersonen = new ArrayList<>();
            final List<BijhoudingPersoon> nietVerwerkbareHoofdPersonen = new ArrayList<>();
            for (BijhoudingPersoon hoofdPersoon : hoofdPersonen) {
                if (hoofdPersoon.isVerwerkbaar()) {
                    verwerkbareHoofdPersonen.add(hoofdPersoon);
                } else {
                    nietVerwerkbareHoofdPersonen.add(hoofdPersoon);
                }
            }
            if (!verwerkbareHoofdPersonen.isEmpty() && !nietVerwerkbareHoofdPersonen.isEmpty()) {
                for (final BijhoudingPersoon nietVerwerkbarePersoon : nietVerwerkbareHoofdPersonen) {
                    final BijhoudingPersoon kopiePseudoPersoon = nietVerwerkbarePersoon.kopieer(getDatumAanvangGeldigheid().getWaarde(),
                            false);
                    bericht.vervangEntiteitMetId(BijhoudingPersoon.class, nietVerwerkbarePersoon.getId(), kopiePseudoPersoon);
                }
            }
        }
    }

    @Override
    public final List<BijhoudingPersoon> getHoofdPersonen() {
        return huwelijkOfGp.getHoofdPersonen();
    }

    @Override
    public final List<PersoonElement> getPersoonElementen() {
        return huwelijkOfGp.getPersoonElementen();
    }

    @Override
    public final DatumElement getPeilDatum() {
        return huwelijkOfGp.getRelatieGroep().getDatumAanvang();
    }

    /**
     * Geeft de waarde van huwelijkOfGp.
     * @return huwelijkOfGp
     */
    public final HuwelijkOfGpElement getHuwelijkOfGp() {
        return huwelijkOfGp;
    }
}
