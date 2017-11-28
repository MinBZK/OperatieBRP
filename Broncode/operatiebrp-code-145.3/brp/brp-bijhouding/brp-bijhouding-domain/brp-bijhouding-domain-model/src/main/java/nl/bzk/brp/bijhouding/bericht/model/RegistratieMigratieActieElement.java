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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * De actie voor het registreren van een adres.
 */
//
@XmlElement("registratieMigratie")
public final class RegistratieMigratieActieElement extends AbstractPersoonWijzigingActieElement {

    private static final int LEEFTIJD_16 = 16;

    /**
     * Maakt een RegistratieAdresActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieMigratieActieElement(
            final Map<String, String> basisAttribuutGroep,
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
        controleerAanvangGeldigheidTegenBijhouding(meldingen);
        controleerLandVoorPeilDatum(meldingen);
        controleerPersoon(meldingen);
        return meldingen;
    }


    @Bedrijfsregel(Regel.R2388)
    private void controleerPersoon(final List<MeldingElement> meldingen) {
        if (heeftIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2388, getPersoon().getMigratie()));
        }
        final boolean staatOnderCuratele = heeftIndicatie(SoortIndicatie.ONDER_CURATELE);
        if (getPersoon().getMigratie().getAangeverCode() != null) {
            controleerAangeverIngeschrevene(meldingen, staatOnderCuratele);
            controleerAangeverOuder(meldingen);
            controleerAangeverGezaghouder(meldingen, staatOnderCuratele);
            controleerAangeverMeerderjarigInwonendKind(meldingen);
            controleerAangeverPartner(meldingen);
        }
    }

    @Bedrijfsregel(Regel.R2371)
    private void controleerAangeverPartner(final List<MeldingElement> meldingen) {
        if (Aangever.PARTNER == getPersoon().getMigratie().getAangeverCode().getWaarde()) {
            final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
            if (bijhoudingPersoon.getActueleHgpRelaties(getPeilDatum().getWaarde()).isEmpty()) {
                meldingen.add(MeldingElement.getInstance(Regel.R2371, getPersoon().getMigratie()));
            }
        }
    }


    @Bedrijfsregel(Regel.R2370)
    private void controleerAangeverMeerderjarigInwonendKind(final List<MeldingElement> meldingen) {
        if (Aangever.MEERDERJARIG_INWONEND_KIND == getPersoon().getMigratie().getAangeverCode().getWaarde()) {
            final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
            boolean isOuderVanMeerderjarigKind = false;
            for (Betrokkenheid kind : bijhoudingPersoon.getActueleKinderen()) {
                if (!isPersoonMinderJarig(kind.getPersoon())) {
                    isOuderVanMeerderjarigKind = true;
                }
            }
            if (!isOuderVanMeerderjarigKind) {
                meldingen.add(MeldingElement.getInstance(Regel.R2370, getPersoon().getMigratie()));
            }
        }
    }

    @Bedrijfsregel(Regel.R2367)
    private void controleerAangeverIngeschrevene(final List<MeldingElement> meldingen, final boolean staatOnderCuratele) {
        final BijhoudingPersoon bijhoudingPersoon = getPersoon().getPersoonEntiteit();
        if (Aangever.INGESCHREVENE == getPersoon().getMigratie().getAangeverCode().getWaarde()) {
            final int leeftijd = bijhoudingPersoon.bepaalLeeftijd(getVerzoekBericht().getDatumOntvangst().getWaarde());
            if (leeftijd < LEEFTIJD_16 || staatOnderCuratele) {
                meldingen.add(MeldingElement.getInstance(Regel.R2367, getPersoon().getMigratie()));
            }
        }
    }

    @Bedrijfsregel(Regel.R2368)
    private void controleerAangeverOuder(final List<MeldingElement> meldingen) {
        if (Aangever.OUDER == getPersoon().getMigratie().getAangeverCode().getWaarde() && (isPersoonMinderJarig(getPersoon().getPersoonEntiteit())
                || !hasActueelOuderschap(getPersoon().getPersoonEntiteit()))) {
            meldingen.add(MeldingElement.getInstance(Regel.R2368, getPersoon().getMigratie()));
        }
    }

    @Bedrijfsregel(Regel.R2369)
    private void controleerAangeverGezaghouder(final List<MeldingElement> meldingen, final boolean staatOnderCuratele) {
        if (Aangever.GEZAGHOUDER == getPersoon().getMigratie().getAangeverCode().getWaarde()) {
            final int leeftijd = getPersoon().getPersoonEntiteit().bepaalLeeftijd(getVerzoekBericht().getDatumOntvangst().getWaarde());
            if (!(leeftijd < LEEFTIJD_16 || staatOnderCuratele)) {
                meldingen.add(MeldingElement.getInstance(Regel.R2369, getPersoon().getMigratie()));
            }
        }
    }

    private boolean heeftIndicatie(final SoortIndicatie soort) {
        final PersoonIndicatie indicatie = getPersoon().getPersoonEntiteit().getPersoonIndicatie(soort);
        return indicatie != null && FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(indicatie.getPersoonIndicatieHistorieSet()) != null;
    }


    @Bedrijfsregel(Regel.R1663)
    private void controleerLandVoorPeilDatum(final List<MeldingElement> meldingen) {
        if (getMigratieElement().getLandGebiedCode() != null) {
            final LandOfGebied landOfGebied = getDynamischeStamtabelRepository().getLandOfGebiedByCode(getMigratieElement().getLandGebiedCode().getWaarde());
            if (landOfGebied != null && !DatumUtil
                    .valtDatumBinnenPeriode(getPeilDatum().getWaarde(), landOfGebied.getDatumAanvangGeldigheid(), landOfGebied.getDatumEindeGeldigheid())) {
                meldingen.add(MeldingElement.getInstance(Regel.R1663, getMigratieElement()));
            }
        }
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_MIGRATIE;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        BRPActie actie = null;
        final BijhoudingPersoon persoon = getPersoon().getPersoonEntiteit();
        if (persoon.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            // Migratie
            persoon.voegPersoonMigratieHistorieToe(getPersoon().getMigratie(), getDatumAanvangGeldigheid().getWaarde(), actie);
            // Deelname EU verkiezingen
            persoon.laatPersoonDeelnameEuVerkiezingenVervallen(actie);
            // Bijhouding
            final PersoonBijhoudingHistorie
                    actueleBijhoudingHistorie =
                    FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonBijhoudingHistorieSet());

            if (actueleBijhoudingHistorie != null && actueleBijhoudingHistorie.getBijhoudingsaard() == Bijhoudingsaard.INGEZETENE) {
                final NadereBijhoudingsaard nadereBijhoudingsaard;
                final MigratieElement migratieElement = getMigratieElement();
                final StringElement landGebiedCode = migratieElement.getLandGebiedCode();

                if (landGebiedCode == null || LandOfGebied.CODE_ONBEKEND.equals(landGebiedCode.getWaarde())) {
                    nadereBijhoudingsaard = NadereBijhoudingsaard.VERTROKKEN_ONBEKEND_WAARHEEN;
                } else if ('M' == migratieElement.getRedenWijzigingCode().getWaarde()) {
                    nadereBijhoudingsaard = NadereBijhoudingsaard.BIJZONDERE_STATUS;
                } else {
                    nadereBijhoudingsaard = NadereBijhoudingsaard.EMIGRATIE;
                }

                final Partij ministerPartij = getDynamischeStamtabelRepository().getPartijByCode(Partij.PARTIJCODE_MINISTER);
                persoon.voegPersoonBijhoudingHistorieToe(ministerPartij, Bijhoudingsaard.NIET_INGEZETENE, nadereBijhoudingsaard,
                        getDatumAanvangGeldigheid().getWaarde(), actie);
            }
            persoon.beeindigAdres(getDatumAanvangGeldigheid().getWaarde(), actie);
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    private MigratieElement getMigratieElement() {
        return getPersoon().getMigratie();
    }


}
