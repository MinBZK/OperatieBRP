/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;

/**
 * VR00015c : Afgeleide registratie Bijhouding door Verhuizing.
 *
 * Als voor een Persoon een actueel Adres wordt geregistreerd waarbij Land/gebied="Nederland", terwijl geen actuele
 * Bijhouding staat geregistreerd met een Bijhoudingspartij die gelijk is aan de Partij behorend bij de Gemeente van het
 * Adres dan wordt voor de Persoon de volgende Bijhouding afgeleid geregistreerd:
 * Bijhoudingsaard := "Ingezetene"
 * NadereBijhoudingsaard := "Actueel"
 * Bijhoudingspartij := Adres.Gemeente.Partij,
 * OnverwerktDocumentAanwezig := "Nee"
 * DatumAanvangGeldigheid := Adres.DatumAanvangGeldigheid
 * DatumEindeGeldigheid := Adres.DatumEindeGeldigheid (=Null omdat Adres actueel!)
 * ActieInhoud := Adres.ActieInhoud
 *
 * Opmerking:
 * Een Persoon met NadereBijhoudingsaard "Ministerieel besluit" kan als niet-ingezetene toch een Adres in Nederland
 * krijgen. Omdat de Minister verantwoordelijk is voor zo'n bijhouding, is deze regel niet van toepassing:
 * de Bijhouding wordt niet geactualiseerd.
 */
public class BijhoudingAfleidingDoorVerhuizing extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     */
    public BijhoudingAfleidingDoorVerhuizing(final PersoonHisVolledig model,
                                             final ActieModel actie)
    {
        super(model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00015c;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final PersoonHisVolledig persoonHisVolledig = getModel();
        final HisPersoonAdresModel actueleAdres =
                persoonHisVolledig.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();

        if (actueleAdres.getLandGebied().getWaarde().getCode().equals(LandGebiedCodeAttribuut.NEDERLAND)
                && actueleAdresGemeenteIsNietGelijkAanBijhoudingPartij(persoonHisVolledig, actueleAdres))
        {
            final HisPersoonBijhoudingModel bijhouding = new HisPersoonBijhoudingModel(
                    getModel(),
                    new PartijAttribuut(actueleAdres.getGemeente().getWaarde().getPartij()),
                    new BijhoudingsaardAttribuut(Bijhoudingsaard.INGEZETENE),
                    new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.ACTUEEL),
                    JaNeeAttribuut.NEE,
                    actueleAdres.getVerantwoordingInhoud(),
                    actueleAdres.getMaterieleHistorie()
            );

            persoonHisVolledig.getPersoonBijhoudingHistorie().voegToe(bijhouding);
        }

        return GEEN_VERDERE_AFLEIDINGEN;
    }

    /**
     * Controleert of de persoon een bijhouding groep heeft met een bijhoudingpartij, zo ja dan controleert de regel ook
     * of de bijhouding partij overeenkomt met de gemeente van het adres.
     * @param persoonHisVolledig de persoon
     * @param actueleAdres het actuele adres
     * @return true indien de bijhouding partij niet synchroon is met de gemeente van het adres.
     */
    private boolean actueleAdresGemeenteIsNietGelijkAanBijhoudingPartij(final PersoonHisVolledig persoonHisVolledig,
                                                                        final HisPersoonAdresModel actueleAdres)
    {
        boolean resultaat;
        final HisPersoonBijhoudingModel actueleBijhouding =
                persoonHisVolledig.getPersoonBijhoudingHistorie().getActueleRecord();
        if (actueleBijhouding == null || actueleBijhouding.getBijhoudingspartij() == null) {
            resultaat = true;
        } else {
            final Partij bijhoudingspartij = actueleBijhouding.getBijhoudingspartij().getWaarde();
            final Gemeente gemeenteAdres = actueleAdres.getGemeente().getWaarde();
            resultaat = !gemeenteAdres.getPartij().getID().equals(bijhoudingspartij.getID());
        }
        return resultaat;
    }

}
