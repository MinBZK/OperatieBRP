/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorVerhuizing;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonAdresView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;

/**
 * Afgeleide registratie Adres van kind bij geboorte.
 *
 * Adres voor kind wordt overgenomen van de ouder uit wie kind is voortgekomen.
 *
 * @brp.bedrijfsregel VR00013a
 */
public class AdresAfleidingDoorGeboorte extends AbstractAfleidingsregel<PersoonHisVolledigImpl> {

    private final PersoonHisVolledig adresgevendeOuder;

    /**
     * Forwarding constructor.
     *
     * @param persoon de persoon
     * @param adresgevendeOuder de persoon uit wie het kind is voortgekomen
     * @param actie actie
     */
    public AdresAfleidingDoorGeboorte(final PersoonHisVolledigImpl persoon, final PersoonHisVolledig adresgevendeOuder,
            final ActieModel actie)
    {
        super(persoon, actie);
        this.adresgevendeOuder = adresgevendeOuder;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00013a;
    }

    @Override
    public final AfleidingResultaat leidAf() {
        final AfleidingResultaat afleidingsregels;

        if (adresgevendeOuder == null) {
            afleidingsregels = GEEN_VERDERE_AFLEIDINGEN;
        } else {
            final PersoonAdresView adresMoederCLaag = haalWoonadresOp(adresgevendeOuder);
            kopieerAdresMoederNaarKind(adresMoederCLaag);

            // Let op: Bij registratie buitenlands adres moeten we de bijhouding afleiden, bij Nederlands adres
            // de bijhoudingsgemeente. Zie verwerking groep adres VR00013.
            final HisPersoonAdresModel adresKind =
                getModel().getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();
            if (LandGebiedCodeAttribuut.NEDERLAND.equals(adresKind.getLandGebied().getWaarde().getCode())) {
                afleidingsregels = vervolgAfleidingen(new BijhoudingAfleidingDoorVerhuizing(getModel(), getActie()));
            } else {
                afleidingsregels =
                    vervolgAfleidingen(new BijhoudingAfleidingDoorEmigratie(getModel(), getActie()));
            }
        }

        return afleidingsregels;
    }

    /**
     * Haalt het huidige woonadres op van de persoon.
     *
     * @param persoon de persoon
     * @return de persoon adres his volledig view
     */
    private PersoonAdresView haalWoonadresOp(final PersoonHisVolledig persoon) {
        PersoonAdresView resultaat = null;
        // TODO spec zegt adres op datumgeboorte, op dit moment hoeft er alleen gekeken worden naar de huidige situatie.
        for (final PersoonAdresHisVolledig persoonAdres : persoon.getAdressen()) {
            final PersoonAdresView adresOpMoment = new PersoonAdresView(persoonAdres);
            resultaat = adresOpMoment;
        }

        return resultaat;
    }

    /**
     * Kopieert het adres van de moeder naar het kind.
     *
     * @param adresMoeder het adres van de moeder uit de c laag
     */
    private void kopieerAdresMoederNaarKind(final PersoonAdresView adresMoeder) {
        final RedenWijzigingVerblijf redenWijzigingVerblijf =
            getReferentieDataRepository().vindRedenWijzingVerblijfOpCode(
                    RedenWijzigingVerblijfCodeAttribuut.PERSOON_REDEN_WIJZIGING_ADRES_CODE_AMBTSHALVE_CODE);

        final PersoonAdresHisVolledigImpl adresKind = new PersoonAdresHisVolledigImpl(getModel());
        final HisPersoonAdresModel hisPersoonAdresModelMoeder = adresMoeder.getStandaard();
        final HisPersoonAdresModel adresKindCLaag =
            new HisPersoonAdresModel(adresKind, hisPersoonAdresModelMoeder.getSoort(),
                    new RedenWijzigingVerblijfAttribuut(redenWijzigingVerblijf), null, getModel()
                            .getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte(),
                    hisPersoonAdresModelMoeder.getIdentificatiecodeAdresseerbaarObject(),
                    hisPersoonAdresModelMoeder.getIdentificatiecodeNummeraanduiding(),
                    hisPersoonAdresModelMoeder.getGemeente(), hisPersoonAdresModelMoeder.getNaamOpenbareRuimte(),
                    hisPersoonAdresModelMoeder.getAfgekorteNaamOpenbareRuimte(),
                    hisPersoonAdresModelMoeder.getGemeentedeel(), hisPersoonAdresModelMoeder.getHuisnummer(),
                    hisPersoonAdresModelMoeder.getHuisletter(), hisPersoonAdresModelMoeder.getHuisnummertoevoeging(),
                    hisPersoonAdresModelMoeder.getPostcode(), hisPersoonAdresModelMoeder.getWoonplaatsnaam(),
                    hisPersoonAdresModelMoeder.getLocatieTenOpzichteVanAdres(),
                    hisPersoonAdresModelMoeder.getLocatieomschrijving(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel1(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel2(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel3(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel4(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel5(),
                    hisPersoonAdresModelMoeder.getBuitenlandsAdresRegel6(), hisPersoonAdresModelMoeder.getLandGebied(),
                    null, getActie(), getActie());

        // DatumAanvangGeldigheid := Geboorte.DatumGeboorte
        adresKindCLaag.getMaterieleHistorie().setDatumAanvangGeldigheid(
                getModel().getPersoonGeboorteHistorie().getActueleRecord().getDatumGeboorte());

        adresKind.getPersoonAdresHistorie().voegToe(adresKindCLaag);
        getModel().getAdressen().add(adresKind);
    }
}
