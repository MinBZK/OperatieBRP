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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het registreren van een adres.
 */
@XmlElement("registratieGeslachtsnaamVoornaam")
public final class RegistratieGeslachtsnaamVoornaamActieElement extends AbstractPersoonWijzigingActieElement {


    /**
     * Maakt een registratieGeslachtsnaamVoornaamElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieGeslachtsnaamVoornaamActieElement(final Map<String, String> basisAttribuutGroep,
                                                        final DatumElement datumAanvangGeldigheid,
                                                        final DatumElement datumEindeGeldigheid,
                                                        final List<BronReferentieElement> bronnen,
                                                        final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen, persoon);
        ValidatieHelper.controleerOpNullWaarde(persoon, "persoon");
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        if (getVerzoekBericht().getAdministratieveHandeling().isErkenningHandeling()) {
            controleerDatumAanvangGeldigheidGelijkAanDatumErkenning(meldingen);
            controleerVoornaamAanwezigAlsIndicatieNamenReeksWijzigt(meldingen);
            controleerDagGeslachtsnaamComponent(meldingen);
            controleerDagVoornaamSamengesteldenaam(meldingen);
            controleerDagMetIndicatieNamenReeks(meldingen);
        }
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2475)
    private void controleerDatumAanvangGeldigheidGelijkAanDatumErkenning(final List<MeldingElement> meldingen) {
        final ActieElement registratieOuder = getVerzoekBericht().getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_OUDER);
        if (registratieOuder != null && !Objects.equals(registratieOuder.getDatumAanvangGeldigheid().getWaarde(), getDatumAanvangGeldigheid().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2475, this));
        }
    }

    @Bedrijfsregel(Regel.R2471)
    private void controleerVoornaamAanwezigAlsIndicatieNamenReeksWijzigt(final List<MeldingElement> meldingen) {
        final Boolean oudeWaardeIndicatieNamenreeks;
        if (getPersoon().heeftReferentie()) {
            oudeWaardeIndicatieNamenreeks = getPersoon().getReferentie().getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde();
        } else {
            oudeWaardeIndicatieNamenreeks = getPersoon().getPersoonEntiteit().getActueleIndicatieNamenreeks();
        }

        final boolean nieuweWaardeIndicatieNamenreeks = getPersoon().getSamengesteldeNaam() != null &&
                getPersoon().getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde();

        if (!(oudeWaardeIndicatieNamenreeks && !nieuweWaardeIndicatieNamenreeks) && !getPersoon().getVoornamen().isEmpty()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2471, this));
        }
    }

    @Bedrijfsregel(Regel.R2746)
    private void controleerDagGeslachtsnaamComponent(List<MeldingElement> meldingen) {
        if (!getPersoon().getGeslachtsnaamcomponenten().isEmpty()) {
            final Integer geldendeDatumGeslachtsnaamComponent;
            if (getVerzoekBericht().getAdministratieveHandeling().isEersteInschrijving()) {
                geldendeDatumGeslachtsnaamComponent = getVerzoekBericht().getAdministratieveHandeling().getHoofdActie().getPeilDatum().getWaarde();
            } else {
                final PersoonGeslachtsnaamcomponentHistorie
                        actueleGeslachtsnaamComponent =
                        getPersoon().getPersoonEntiteit().getActueleGeslachtsnaamComponent(DatumUtil.vandaag());
                geldendeDatumGeslachtsnaamComponent = actueleGeslachtsnaamComponent != null ? actueleGeslachtsnaamComponent.getDatumAanvangGeldigheid() : null;
            }
            if (geldendeDatumGeslachtsnaamComponent != null && geldendeDatumGeslachtsnaamComponent > getPeilDatum().getWaarde()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2746, this));
            }
        }
    }

    @Bedrijfsregel(Regel.R2747)
    private void controleerDagVoornaamSamengesteldenaam(List<MeldingElement> meldingen) {
        if (!getPersoon().getVoornamen().isEmpty()) {
            final List<PersoonVoornaamHistorie>
                    actueleVoornamen =
                    getPersoon().getPersoonEntiteit().getActueleVoornamen(DatumUtil.vandaag());
            final boolean dagVoornaamHistoryNaPeildatum = !actueleVoornamen.isEmpty()
                    && actueleVoornamen.stream().filter(historie -> historie.getDatumAanvangGeldigheid() > getPeilDatum().getWaarde()).count() > 0;

            if (dagVoornaamHistoryNaPeildatum || isDagActueleSamenGesteldeNaamNaPeildatum()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2747, this));
            }
        }
    }

    private boolean isDagActueleSamenGesteldeNaamNaPeildatum() {
        if (getVerzoekBericht().getAdministratieveHandeling().isEersteInschrijving()) {
            return getVerzoekBericht().getAdministratieveHandeling().getHoofdActie().getPeilDatum().getWaarde() > getPeilDatum().getWaarde();
        } else {
            final PersoonSamengesteldeNaamHistorie
                    actueleSamengesteldeNaam =
                    getPersoon().getPersoonEntiteit().getActuelePersoonSamengesteldeNaamHistorie();
            return actueleSamengesteldeNaam != null && actueleSamengesteldeNaam.getDatumAanvangGeldigheid() > getPeilDatum().getWaarde();
        }
    }

    @Bedrijfsregel(Regel.R2748)
    private void controleerDagMetIndicatieNamenReeks(final List<MeldingElement> meldingen) {
        if (getPersoon().getSamengesteldeNaam() != null && isDagActueleSamenGesteldeNaamNaPeildatum()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2748, this));
        }
    }


    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_GESLACHTSNAAMVOORNAAM;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        BRPActie actie = null;
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        if (bijhoudingPersoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            PersoonElement persoon = getPersoon();
            if (!persoon.getVoornamen().isEmpty() || getPersoon().getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde()) {
                for (PersoonVoornaam voornaam : bijhoudingPersoon.getPersoonVoornaamSet()) {
                    MaterieleHistorie.beeindigActueelVoorkomen(voornaam.getPersoonVoornaamHistorieSet(), actie, actie.getDatumTijdRegistratie(),
                            getDatumAanvangGeldigheid().getWaarde());
                }
                bijhoudingPersoon.voegPersoonVoornamenHistorieToe(persoon.getVoornamen(), actie, getDatumAanvangGeldigheid().getWaarde());
            }
            if (!persoon.getGeslachtsnaamcomponenten().isEmpty()) {
                bijhoudingPersoon.voegPersoonGeslachtsnaamComponentHistorieToe(persoon.getGeslachtsnaamcomponenten().get(0), actie,
                        getDatumAanvangGeldigheid().getWaarde());
            }
            bijhoudingPersoon.leidAf(actie, getDatumAanvangGeldigheid().getWaarde(), true, persoon.getSamengesteldeNaam().getIndicatieNamenreeks().getWaarde());
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Override
    public boolean heeftInvloedOpGerelateerden() {
        return true;
    }
}
