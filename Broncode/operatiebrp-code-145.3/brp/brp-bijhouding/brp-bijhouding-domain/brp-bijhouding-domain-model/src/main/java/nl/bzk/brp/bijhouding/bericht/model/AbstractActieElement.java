/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;

/**
 * Een actie element uit het bijhouding berichtenmodel.
 */
public abstract class AbstractActieElement extends AbstractBmrObjecttype implements ActieElement {

    @XmlTransient
    private static final CharacterElement STRIJDIG_MET_OPENBARE_ORDE = new CharacterElement('S');

    private static final List<SoortActieHandeling> ACTIES_VOOR_UITSLUITEND_INGESCHREVEN_PERSONEN =
            Arrays.asList(new SoortActieHandeling(SoortActie.REGISTRATIE_AANVANG_GEREGISTREERD_PARTNERSCHAP),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_AANVANG_HUWELIJK),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_GESLACHTSNAAM),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_NAAMGEBRUIK),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE,
                            AdministratieveHandelingElementSoort.REGISTRATIE_VESTIGING_NIET_INGEZETENE,
                            AdministratieveHandelingElementSoort.WIJZIGING_BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_VERBLIJFSRECHT),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_OUDER),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_GEBORENE),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_VERSTREKKINGSBEPERKING,
                            AdministratieveHandelingElementSoort.REGISTRATIE_VESTIGING_NIET_INGEZETENE),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_ADRES, AdministratieveHandelingElementSoort.REGISTRATIE_VESTIGING_NIET_INGEZETENE),
                    new SoortActieHandeling(SoortActie.REGISTRATIE_MIGRATIE, AdministratieveHandelingElementSoort.REGISTRATIE_VESTIGING_NIET_INGEZETENE));


    private static final Set<SoortActie>
            ACTIES_VOOR_REGISTRATIE_RELATIE =
            EnumSet.of(SoortActie.REGISTRATIE_AANVANG_GEREGISTREERD_PARTNERSCHAP, SoortActie.REGISTRATIE_AANVANG_HUWELIJK, SoortActie.REGISTRATIE_GEBORENE,
                    SoortActie.REGISTRATIE_OUDER, SoortActie.REGISTRATIE_INGEZETENE);

    private static final Set<SoortActie>
            CORRECTIE_ACTIES_ZONDER_BRON =
            EnumSet.of(SoortActie.CORRECTIEVERVAL_ADRES, SoortActie.CORRECTIEREGISTRATIE_ADRES, SoortActie.CORRECTIEVERVAL_BIJHOUDING,
                    SoortActie.CORRECTIEREGISTRATIE_BIJHOUDING);

    private final DatumElement datumAanvangGeldigheid;
    private final DatumElement datumEindeGeldigheid;
    @XmlChildList(naam = "bronnen", listElementType = BronReferentieElement.class)
    private final List<BronReferentieElement> bronReferenties;

    /**
     * Maakt een AbstractActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid de datum einde geldigheid
     * @param bronReferenties bron referenties
     */
    public AbstractActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties) {
        super(basisAttribuutGroep);
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
        this.datumEindeGeldigheid = datumEindeGeldigheid;
        this.bronReferenties = initArrayList(bronReferenties);
    }

    @Override
    public final DatumElement getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    @Override
    public final DatumElement getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    @Override
    public final List<BronReferentieElement> getBronReferenties() {
        return Collections.unmodifiableList(bronReferenties);
    }

    @Override
    /*rechtsgrond wordt nog niet gevuld bij het maken van ActieBron*/
    public final BRPActie maakActieEntiteit(final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie =
                new BRPActie(getSoortActie(), administratieveHandeling, administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());

        for (final BronReferentieElement bronReferentieElement : getBronReferenties()) {
            final Document document =
                    bronReferentieElement.getReferentie().getDocument() == null ? null
                            : bronReferentieElement.getReferentie().getDocument().maakDocumentEntiteit();
            if (bronReferentieElement.getReferentie().getRechtsgrondCode() != null) {
                actie.koppelDocumentViaActieBron(document, bronReferentieElement.getReferentie().getRechtsgrond());
            } else if (document != null) {
                actie.koppelDocumentViaActieBron(document);
            }
        }
        return actie;
    }

    @Override
    protected final List<MeldingElement> valideerInhoud() {
        final List<MeldingElement> meldingen = new ArrayList<>();

        controleerObjectSleutelsVoorPersonen(meldingen);
        controleerDocumentGeldigVoorActieEnAdministratieveHandeling(meldingen);
        controleerActieToegestaanBijOverledenHoofdPersoon(meldingen);
        meldingen.addAll(valideerSpecifiekeInhoud());
        controleerRechtsgrondVanBronnen(meldingen);
        controleerBronnenBijCorrectie(meldingen);
        controleerDatumAanvangEnDatumEindeGeldigheid(meldingen);
        return meldingen;
    }

    @Bedrijfsregel(Regel.R2354)
    @Bedrijfsregel(Regel.R2527)
    private void controleerDatumAanvangEnDatumEindeGeldigheid(final List<MeldingElement> meldingen) {
        if (getDatumAanvangGeldigheid() != null && getDatumAanvangGeldigheid().getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2354, this));
        }

        if (getDatumEindeGeldigheid() != null && getDatumEindeGeldigheid().getWaarde() > DatumUtil.vandaag()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2527, this));
        }
    }

    /**
     * Bepaalt of alle hoofdpersonen van deze actie ook verwerkbaar zijn.
     * @return true als alle hoofdpersonen verwerkbaar zijn, anders false
     */
    final boolean zijnAlleHoofdPersonenVerwerkbaar() {
        final List<BijhoudingPersoon> hoofdPersonen = getHoofdPersonen();
        boolean result = !hoofdPersonen.isEmpty();
        for (final BijhoudingPersoon bijhoudingPersoon : hoofdPersonen) {
            if (!bijhoudingPersoon.isVerwerkbaar()) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Bepaalt of de bronnen waarnaar deze actie verwijst een geldige rechtsgrond bevatten voor de gegeven nadere aanduiding verval.
     * @param nadereAanduidingVerval de nadere aanduiding verval
     * @return true als er voor de gegeven nadere aanduiding verval een geldige rechtsgrond gevonden is, anders false
     */
    final boolean heeftOngeldigeRechtsgrondVoorNadereAanduidingVerval(final CharacterElement nadereAanduidingVerval) {
        if (!STRIJDIG_MET_OPENBARE_ORDE.equals(nadereAanduidingVerval)) {
            return false;
        }
        for (final BronReferentieElement bronReferentieElement : getBronReferenties()) {
            if (bronReferentieElement.getReferentie().getRechtsgrondCode() != null) {
                final Rechtsgrond
                        rechtsgrond =
                        getDynamischeStamtabelRepository().getRechtsgrondByCode(bronReferentieElement.getReferentie().getRechtsgrondCode().getWaarde());
                if (rechtsgrond != null && Boolean.TRUE.equals(rechtsgrond.getIndicatieLeidtTotStrijdigheid())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Valideert de specifieke inhoud van de implementerende acties.
     * @return lijst van meldingen of een lege lijst als er geen meldingen zijn
     */
    protected abstract List<MeldingElement> valideerSpecifiekeInhoud();

    private void controleerObjectSleutelsVoorPersonen(final List<MeldingElement> meldingen) {

        for (final PersoonElement persoonElement : getPersoonElementen()) {
            if (voerRegelUit(ACTIES_VOOR_UITSLUITEND_INGESCHREVEN_PERSONEN, getVerzoekBericht().getAdministratieveHandeling().getSoort(), getSoortActie())) {
                controleerOfPersoonIngeschreveneIs(meldingen, persoonElement);
            }
            if (ACTIES_VOOR_REGISTRATIE_RELATIE.contains(getSoortActie())) {
                controleerStructuur(meldingen, persoonElement);
            }
        }
    }

    @Bedrijfsregel(Regel.R2181)
    private void controleerStructuur(final List<MeldingElement> meldingen, final PersoonElement persoonElement) {
        final boolean gerelateerdeGegevensLeeg = !persoonElement.bevatGerelateerdeGegevens();
        final boolean heeftObjectSleutel = persoonElement.heeftPersoonEntiteit() && !persoonElement.getPersoonEntiteit().isEersteInschrijving();

        if (heeftObjectSleutel && !gerelateerdeGegevensLeeg || !heeftObjectSleutel && gerelateerdeGegevensLeeg) {
            meldingen.add(MeldingElement.getInstance(Regel.R2181, persoonElement));
        }
    }

    @Bedrijfsregel(Regel.R2117)
    private void controleerOfPersoonIngeschreveneIs(final List<MeldingElement> meldingen, final PersoonElement persoonElement) {
        if (persoonElement.heeftPersoonEntiteit() && !persoonElement.getPersoonEntiteit().isPersoonIngeschrevene()) {
            meldingen.add(MeldingElement.getInstance(Regel.R2117, persoonElement));
        }
    }

    @Bedrijfsregel(Regel.R1606)
    private void controleerDocumentGeldigVoorActieEnAdministratieveHandeling(
            final List<MeldingElement> meldingen) {
        final SoortActie soortActie = getSoortActie();
        if (SoortActie.REGISTRATIE_ADRES.equals(soortActie)
                || getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie()
                || getVerzoekBericht().getAdministratieveHandeling().isOnderzoekHandeling()) {
            return;
        }
        final SoortAdministratieveHandeling soortAdministratieveHandeling =
                SoortAdministratieveHandeling.valueOf(getVerzoekBericht().getAdministratieveHandeling().getSoort().name());

        for (final BronReferentieElement bronReferentieElement : getBronReferenties()) {
            final SoortDocument soortDocument = bronReferentieElement.getReferentie().getDocument().getSoortDocument();
            if (soortDocument == null || !heeftSoortActieBrongebruik(soortActie, soortAdministratieveHandeling, soortDocument)) {
                meldingen.add(MeldingElement.getInstance(Regel.R1606, bronReferentieElement));
            }
        }
    }

    private boolean heeftSoortActieBrongebruik(
            final SoortActie soortActie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final SoortDocument soortDocument) {
        final SoortActieBrongebruikSleutel sleutel = new SoortActieBrongebruikSleutel(soortActie, soortAdministratieveHandeling, soortDocument);
        return getDynamischeStamtabelRepository().getSoortActieBrongebruikBySoortActieBrongebruikSleutel(sleutel) != null;
    }

    @Bedrijfsregel(Regel.R1579)
    private void controleerActieToegestaanBijOverledenHoofdPersoon(final List<MeldingElement> meldingen) {
        final List<BijhoudingPersoon> hoofdpersonen = getHoofdPersonen();
        if (!getVerzoekBericht().getAdministratieveHandeling().isOnderzoekHandeling()) {
            for (final BijhoudingPersoon hoofdPersoon : hoofdpersonen) {
                if (hoofdPersoon != null && hoofdPersoon
                        .isPersoonOverledenOpDatum(getVerzoekBericht().getAdministratieveHandeling().getPeilDatum().getWaarde())) {
                    meldingen.add(MeldingElement.getInstance(Regel.R1579, this));
                }
            }
        }
    }

    @Bedrijfsregel(Regel.R2431)
    @Bedrijfsregel(Regel.R2668)
    private void controleerRechtsgrondVanBronnen(final List<MeldingElement> meldingen) {
        boolean isBronOpgenomen = !getBronReferenties().isEmpty();
        boolean bronBevatDocumentOfRechtsgrond = false;
        for (final BronReferentieElement bronReferentieElement : getBronReferenties()) {
            bronBevatDocumentOfRechtsgrond = bronBevatDocumentOfRechtsgrond ||
                    bronReferentieElement.getReferentie().getDocument() != null
                    || bronReferentieElement.getReferentie().getRechtsgrondCode() != null;
            if (bronReferentieElement.getReferentie().isRechtsgrondOngeldigOpPeildatum(getPeilDatum().getWaarde())) {
                meldingen.add(MeldingElement.getInstance(Regel.R2431, bronReferentieElement));
            }
        }
        if (getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie() && (!isBronOpgenomen || !bronBevatDocumentOfRechtsgrond)) {
            meldingen.add(MeldingElement.getInstance(Regel.R2668, this));
        }
    }

    @Bedrijfsregel(Regel.R2448)
    private void controleerBronnenBijCorrectie(final List<MeldingElement> meldingen) {
        if (getVerzoekBericht().getAdministratieveHandeling().getSoort().isCorrectie() && getBronReferenties().isEmpty()
                && !CORRECTIE_ACTIES_ZONDER_BRON.contains(getSoortActie())) {
            meldingen.add(MeldingElement.getInstance(Regel.R2448, this));
        }
    }

    /**
     * Loopt over de bronnen en controleert of de meegegeven gemeenteCode gelijk is aan de partijCode van die bron indien het een Nederlandse registerakte is.
     * @param bronnen de bronnen van de desbetreffende actie
     * @param gemeenteCode de gemeentecode
     * @param meldingen lijst van gelogde meldingen
     * @param regel de regel die gelogd moet worden al niet aan de voorwaarde wordt voldaanl
     */
    void controleerGeenRegisterAkteOfGemeenteKomenOvereen(final List<BronReferentieElement> bronnen, final StringElement gemeenteCode,
                                                          final List<MeldingElement> meldingen, final Regel regel) {
        for (final BronReferentieElement bronReferentieElement : bronnen) {
            final SoortDocument soortDocument = bronReferentieElement.getReferentie().getDocument().getSoortDocument();
            final boolean isRegisterAkte = soortDocument != null && soortDocument.getRegistersoort() != null;
            if (isRegisterAkte) {
                final Gemeente documentGemeente = getDynamischeStamtabelRepository()
                        .getGemeenteByPartijcode(bronReferentieElement.getReferentie().getDocument().getPartijCode().getWaarde());
                if (documentGemeente == null
                        || (gemeenteCode != null && !documentGemeente.getCode().equals(gemeenteCode.getWaarde()))) {
                    meldingen.add(MeldingElement.getInstance(regel, bronReferentieElement));
                }
            }
        }
    }

    boolean voerRegelUit(List<SoortActieHandeling> actiesHandelingen, AdministratieveHandelingElementSoort soortHandeling, SoortActie soortActie) {
        for (SoortActieHandeling actie : actiesHandelingen) {
            if (actie.soortActie.equals(soortActie)
                    && (actie.administratieveHandelingElementSoort.isEmpty()
                    || actie.administratieveHandelingElementSoort.contains(soortHandeling))) {
                return true;
            }
        }
        return false;
    }


}
