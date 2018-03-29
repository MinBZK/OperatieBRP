/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een adres.
 */
//
@XmlElement("registratieOuder")
public final class RegistratieOuderActieElement extends AbstractActieElement implements ActieMetOuderGegevens {
    private static final int ZEVEN_JAAR = 7;
    private static final int MAX_DAGEN_ERKENNING_NA_GEBOORTE = 8;
    private PersoonRelatieElement persoonRelatieElement;

    /**
     * Maakt een RegistratieAdresActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronnen bron referenties
     * @param persoon de persoon
     */
    public RegistratieOuderActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronnen,
            final PersoonRelatieElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronnen);
        this.persoonRelatieElement = persoon;

        final BetrokkenheidElement ouder =
                persoonRelatieElement.getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking().getBetrokkenheden().get(0);
        ouder.maakOuderNouwkig();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        final List<MeldingElement> meldingen = new LinkedList<>();
        final Integer geboorteDatum;
        if (!persoonRelatieElement.heeftReferentie()) {
            geboorteDatum = persoonRelatieElement.getPersoonEntiteit().getActueleDatumGeboorte();
        } else {
            geboorteDatum = persoonRelatieElement.getReferentie().getGeboorte().getDatum().getWaarde();
        }

        controleerDatumAanvangGeldigheidNaGeboorteDatum(meldingen, geboorteDatum);
        controleerVerkrijgingNederlandseNationaliteit(meldingen, geboorteDatum);
        controleerOuderOverleden(meldingen);
        controleerActueleOuder(meldingen);
        controleerDatumErkenningAchtDagenNaGeboortedatum(meldingen);
        return meldingen;
    }

    /**
     * Deze regel mag alleen af gaan indien erkenning na de geboorteDatum.
     * immers de leeftijd is meer als 7 jaar
     * dit betekend dat we altijd te maken hebben met een persoonentiteit.
     */
    @Bedrijfsregel(Regel.R1748)
    private void controleerVerkrijgingNederlandseNationaliteit(final List<MeldingElement> meldingen, final Integer geboorteDatum) {
        if (getVerzoekBericht().getAdministratieveHandeling().isRegistratieOudersNaGeboorteHandeling() && isNederlanseEnJongerDanZevenJaarEnNederlandseOuder(
                geboorteDatum)) {
            meldingen.add(MeldingElement.getInstance(Regel.R1748, this));
        }
    }

    private boolean isNederlanseEnJongerDanZevenJaarEnNederlandseOuder(final Integer geboorteDatum) {
        return !persoonRelatieElement.getPersoonEntiteit().heeftNederlandseNationaliteit(getDatumAanvangGeldigheid().getWaarde())
                && DatumUtil.bepaalJarenTussenDatumsSoepel(geboorteDatum, getDatumAanvangGeldigheid().getWaarde()) < ZEVEN_JAAR
                && heeftEenOuderDeNederlandseNationaliteit();
    }

    private boolean heeftEenOuderDeNederlandseNationaliteit() {
        return getOuders().stream().filter(ouder -> ouder.getPersoonEntiteit().heeftNederlandseNationaliteit(getDatumAanvangGeldigheid().getWaarde())).count()
                > 0;
    }

    @Bedrijfsregel(Regel.R2638)
    private void controleerActueleOuder(List<MeldingElement> meldingen) {
        final BijhoudingPersoon kind = persoonRelatieElement.getPersoonEntiteit();
        if (kind.getActueleOuders().size() > 1) {
            meldingen.add(MeldingElement.getInstance(Regel.R2638, this));
        }
    }


    @Bedrijfsregel(Regel.R2450)
    private void controleerDatumAanvangGeldigheidNaGeboorteDatum(final List<MeldingElement> meldingen, final Integer geboorteDatum) {
        if (getVerzoekBericht().getAdministratieveHandeling().isErkenningHandeling() && getDatumAanvangGeldigheid().getWaarde() <= geboorteDatum) {
            meldingen.add(MeldingElement.getInstance(Regel.R2450, this));
        }
    }

    @Bedrijfsregel(Regel.R1734)
    private void controleerOuderOverleden(final List<MeldingElement> meldingen) {
        final PersoonElement ouder = getOuders().get(0);
        if (ouder.heeftPersoonEntiteit() && ouder.getPersoonEntiteit()
                .isPersoonOverledenOpDatum(getPeilDatum().getWaarde())) {
            meldingen.add(MeldingElement.getInstance(Regel.R1734, this));
        }
    }

    @Bedrijfsregel(Regel.R2783)
    private void controleerDatumErkenningAchtDagenNaGeboortedatum(final List<MeldingElement> meldingen) {
        if (getVerzoekBericht().getAdministratieveHandeling().isErkenningNaGeboorteHandeling()) {
            final int geboorteDatumKind = getKind().getPersoonEntiteit().getActueleDatumGeboorte();
            if (DatumUtil.bepaalDagenTussenDatumsSoepel(geboorteDatumKind, getPeilDatum().getWaarde()) > MAX_DAGEN_ERKENNING_NA_GEBOORTE) {
                meldingen.add(MeldingElement.getInstance(Regel.R2783, this));
            }
        }
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_OUDER;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BRPActie actie;
        if (persoonRelatieElement.getPersoonEntiteit().isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);
            final BijhoudingRelatie bijhoudingRelatie =
                    persoonRelatieElement.getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking().getBijhoudingRelatie();

            final BetrokkenheidElement ouderElement = persoonRelatieElement.getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking()
                    .getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER).iterator().next();
            ouderElement.maakBetrokkenheidEntiteit(bijhoudingRelatie, actie, getDatumAanvangGeldigheid().getWaarde());
            if (!getHoofdPersonen().get(0).heeftNederlandseNationaliteit(getPeilDatum().getWaarde())) {
                verwerkBVP(actie, persoonRelatieElement.getPersoonEntiteit(), getOuders(), getVerzoekBericht());
            }
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public List<BijhoudingPersoon> getHoofdPersonen() {
        return Collections.singletonList(getPersoonRelatieElement().getPersoonEntiteit());
    }

    @Override
    public List<PersoonElement> getPersoonElementen() {
        return Collections.emptyList();
    }

    @Override
    public DatumElement getPeilDatum() {
        return getDatumAanvangGeldigheid();
    }

    @Override
    public PersoonElement getKind() {
        return persoonRelatieElement;
    }

    /**
     * geeft persoon relatie element terug.
     */
    public PersoonRelatieElement getPersoonRelatieElement() {
        return persoonRelatieElement;
    }

    @Override
    public List<PersoonElement> getOuders() {
        return persoonRelatieElement.getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking().getOuders();
    }

    @Override
    public List<PersoonElement> getNouwkigs() {
        return persoonRelatieElement.getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking().getOuders();
    }
}
