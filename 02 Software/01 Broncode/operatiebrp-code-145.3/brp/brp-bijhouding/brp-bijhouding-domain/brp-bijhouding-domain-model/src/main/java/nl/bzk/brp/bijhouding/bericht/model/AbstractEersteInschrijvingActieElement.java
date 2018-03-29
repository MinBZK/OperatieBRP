/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Parent class voor {@link RegistratieGeboreneActieElement}, RegistratieIngezetene en RegistratieGeboorte
 */
public abstract class AbstractEersteInschrijvingActieElement extends AbstractActieElement {
    /**
     * Maakt een AbstractActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     */
    public AbstractEersteInschrijvingActieElement(final Map<String, String> basisAttribuutGroep,
                                                  final DatumElement datumAanvangGeldigheid,
                                                  final DatumElement datumEindeGeldigheid,
                                                  final List<BronReferentieElement> bronReferenties) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties);
    }

    @Override
    protected final List<MeldingElement> valideerSpecifiekeInhoud() {
        List<MeldingElement> meldingen = new ArrayList<>();
        valideerRelatieGemeenteMetDocument(meldingen);
        controleerDerdeGeneratieNationalteit(meldingen);
        meldingen.addAll(valideerImplementerendeActies());
        return meldingen;
    }

    protected abstract List<MeldingElement> valideerImplementerendeActies();

    abstract PersoonElement getInTeSchrijvenPersoon();

    @Bedrijfsregel(Regel.R2435)
    private void valideerRelatieGemeenteMetDocument(final List<MeldingElement> meldingen) {
        controleerGeenRegisterAkteOfGemeenteKomenOvereen(this.getBronReferenties(), getInTeSchrijvenPersoon().getGeboorte().getGemeenteCode(), meldingen,
                Regel.R2435);
    }

    @Bedrijfsregel(Regel.R1690)
    private void controleerDerdeGeneratieNationalteit(final List<MeldingElement> meldingen) {
        final Integer geboorteDatum = getInTeSchrijvenPersoon().getGeboorte().getDatum().getWaarde();
        if (!getInTeSchrijvenPersoon().getPersoonEntiteit().heeftNederlandseNationaliteit(geboorteDatum)) {
            final Map<BijhoudingPersoon, Integer> ouders =
                    getVerzoekBericht().getAdministratieveHandeling().getOuders(getInTeSchrijvenPersoon(), getPeilDatum().getWaarde());
            if (zijnOudersEnGrootoudersIngezetene(ouders, geboorteDatum)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1690, this));
            }
        }
    }


    private boolean zijnOudersEnGrootoudersIngezetene(final Map<BijhoudingPersoon, Integer> ouders, final Integer geboorteDatum) {

        for (final Map.Entry<BijhoudingPersoon, Integer> ouder : ouders.entrySet()) {
            if (Objects.equals(ouder.getValue(), geboorteDatum)
                    && ouder.getKey().isIngezeteneOpPeildatum(geboorteDatum)
                    && isGrootOuderIngezetene(geboorteDatum, ouder.getKey())) {
                return true;
            }
        }
        return false;
    }

    private boolean isGrootOuderIngezetene(final Integer geboorteDatum, final BijhoudingPersoon ouder) {
        for (Persoon grootOuder : ouder.getOuders(geboorteDatum)) {
            BijhoudingPersoon bijhoudingGrootouder = new BijhoudingPersoon(grootOuder);
            if (bijhoudingGrootouder.isIngezeteneOpPeildatum(geboorteDatum)) {
                return true;
            }
        }
        return false;
    }
}
