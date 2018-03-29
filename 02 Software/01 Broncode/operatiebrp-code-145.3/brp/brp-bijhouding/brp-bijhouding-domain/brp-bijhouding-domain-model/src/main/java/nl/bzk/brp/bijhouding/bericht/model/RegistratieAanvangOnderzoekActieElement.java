/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * De actie voor het registreren van een aanvang van een huwelijk.
 */
@XmlElement("registratieAanvangOnderzoek")
public final class RegistratieAanvangOnderzoekActieElement extends AbstractPersoonWijzigingActieElement {

    /**
     * Maakt een RegistratieAanvangOnderzoekActieElement object.
     * @param basisAttribuutGroep de basis attribuutgroep
     * @param datumAanvangGeldigheid datum aanvang geldigheid
     * @param datumEindeGeldigheid datum einde geldigheid
     * @param bronReferenties bron referenties
     * @param persoon de persoon
     */
    public RegistratieAanvangOnderzoekActieElement(
            final Map<String, String> basisAttribuutGroep,
            final DatumElement datumAanvangGeldigheid,
            final DatumElement datumEindeGeldigheid,
            final List<BronReferentieElement> bronReferenties,
            final PersoonGegevensElement persoon) {
        super(basisAttribuutGroep, datumAanvangGeldigheid, datumEindeGeldigheid, bronReferenties, persoon);
    }

    @Override
    public SoortActie getSoortActie() {
        return SoortActie.REGISTRATIE_AANVANG_ONDERZOEK;
    }

    @Override
    public BRPActie verwerk(final BijhoudingVerzoekBericht bericht, final AdministratieveHandeling administratieveHandeling) {
        final BijhoudingPersoon persoonEntiteit = getHoofdPersonen().get(0);
        final BRPActie actie;
        if (persoonEntiteit.isVerwerkbaar()) {
            actie = maakActieEntiteit(administratieveHandeling);

            //partij hoeft niet te worden meegegeven maar is nu nog verplicht in de database, de partij parameter kan weg na de BMR wijziging
            final Onderzoek onderzoek = new Onderzoek(actie.getPartij(), persoonEntiteit);
            final OnderzoekHistorie
                    onderzoekHistorie =
                    new OnderzoekHistorie(BmrAttribuut.getWaardeOfNull(getOnderzoek().getOnderzoekGroep().getDatumAanvang()), StatusOnderzoek.IN_UITVOERING,
                            onderzoek);
            onderzoekHistorie.setOmschrijving(BmrAttribuut.getWaardeOfNull(getOnderzoek().getOnderzoekGroep().getOmschrijving()));
            BijhoudingEntiteit.voegFormeleHistorieToe(onderzoekHistorie, actie, onderzoek.getOnderzoekHistorieSet());

            for (final GegevenInOnderzoekElement gegevenInOnderzoekElement : getOnderzoek().getGegevensInOnderzoek()) {
                final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(onderzoek, gegevenInOnderzoekElement.bepaalElement());
                final Entiteit entiteitInOnderzoek = gegevenInOnderzoekElement.bepaalObjectOfVoorkomen(persoonEntiteit);
                if (entiteitInOnderzoek != null) {
                    gegevenInOnderzoek.setEntiteitOfVoorkomen(entiteitInOnderzoek);
                }
                final GegevenInOnderzoekHistorie gegevenInOnderzoekHistorie = new GegevenInOnderzoekHistorie(gegevenInOnderzoek);
                BijhoudingEntiteit.voegFormeleHistorieToe(gegevenInOnderzoekHistorie, actie, gegevenInOnderzoek.getGegevenInOnderzoekHistorieSet());
                onderzoek.addGegevenInOnderzoek(gegevenInOnderzoek);
            }
            persoonEntiteit.addOnderzoek(onderzoek);
        } else {
            actie = null;
        }
        return actie;
    }

    @Override
    public DatumElement getPeilDatum() {
        return getOnderzoek().getOnderzoekGroep().getDatumAanvang();
    }

    @Override
    protected List<MeldingElement> valideerSpecifiekeInhoud() {
        return VALIDATIE_OK;
    }

    private OnderzoekElement getOnderzoek() {
        return getPersoon().getOnderzoeken().iterator().next();
    }
}
