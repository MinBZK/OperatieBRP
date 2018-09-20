/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ExpressietekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Door de BRP in te zetten dienst ten behoeve van een specifiek abonnement.
 *
 * TODO: opmerking voorheen catalodus optie, was menukaart. Nu niet alle combinaties mogelijk. Afvangen in beheer.
 *
 *
 * Gevolg daarvan is dat we met de afnemer moeten communiceren over welke dienst we het hebben. Daarvoor gebruiken we de
 * Dienst-ID (het toevoegen van een 'leesbare' Dienst-Code heeft niet de voorkeur omdat er dan weer allerlei
 * betekenisvolle semantiek in de sleutel kan sluipen, zoals bijvoorbeeld bij de huidige codes voor de
 * autorisatietabelregels)
 *
 *
 * Een abonnement wordt geregeld doordat ��n of verschillende soorten diensten worden ingezet. Het inzetten van ��n
 * soort dienst ten behoeve van ��n abonnement, is ��n dienst. Het kan hierbij overigens zijn dat ��n soort dienst
 * meerdere keren wordt ingezet ten behoeve van ��n en hetzelfde abonnement. In dat geval leidt dit tot meerdere
 * diensten.
 *
 *
 *
 */
@Table(schema = "AutAut", name = "Dienst")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Dienst extends AbstractDienst {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Dienst() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param dienstbundel dienstbundel van Dienst.
     * @param soort soort van Dienst.
     * @param effectAfnemerindicaties effectAfnemerindicaties van Dienst.
     * @param datumIngang datumIngang van Dienst.
     * @param datumEinde datumEinde van Dienst.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Dienst.
     * @param attenderingscriterium attenderingscriterium van Dienst.
     * @param eersteSelectiedatum eersteSelectiedatum van Dienst.
     * @param selectieperiodeInMaanden selectieperiodeInMaanden van Dienst.
     */
    protected Dienst(
        final Dienstbundel dienstbundel,
        final SoortDienst soort,
        final EffectAfnemerindicaties effectAfnemerindicaties,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final AttenderingscriteriumAttribuut attenderingscriterium,
        final DatumAttribuut eersteSelectiedatum,
        final SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(dienstbundel,
            soort,
            effectAfnemerindicaties,
            datumIngang,
            datumEinde,
            indicatieGeblokkeerd,
            attenderingscriterium,
            eersteSelectiedatum,
            selectieperiodeInMaanden);
    }


    @Override
    public Integer getID() {
        return super.getID();
    }

    /**
     * Controleert of de dienst ongeldig is op basis van datum ingang en datum einde.
     *
     * @return true als dienst ongeldig is, anders false.
     */
    public boolean isDienstGeldig() {
        final DatumAttribuut datumIngang = getDatumIngang();
        final DatumAttribuut datumEinde = getDatumEinde();

        return !(erIsGeenGeldigRecord(datumIngang) || isVandaagNietGeldig(datumIngang, datumEinde));
    }

    /**
     * Controleert of er een geldig record is voor dit a-laag record.
     *
     * @param datumIngang De datum ingang.
     * @return True als er geen geldig record is, anders false.
     */
    private boolean erIsGeenGeldigRecord(final DatumAttribuut datumIngang) {
        return datumIngang == null || datumIngang.getWaarde() == null;
    }

    /**
     * Controleert of de dienst vandaag geldig is.
     *
     * @param datumIngang De datum ingang.
     * @param datumEinde De datum einde.
     * @return True als het abonnement vandaag niet geldig is, anders false.
     */
    private boolean isVandaagNietGeldig(final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde)
    {
        final DatumAttribuut vandaag = DatumAttribuut.vandaag();
        return !((vandaag.equals(datumEinde) || vandaag.naOfOp(datumIngang)) && (datumEinde == null || vandaag
            .voor(datumEinde)));
    }

    public boolean isGeblokkeerd() {
        return getIndicatieGeblokkeerd() != null && getIndicatieGeblokkeerd().getWaarde() == Ja.J;
    }

    public boolean isDienstGeldigOp(final DatumAttribuut peilDatum) {
        return getDatumIngang().voorOfOp(peilDatum)
            && (getDatumEinde() == null || getDatumEinde().na(peilDatum));
    }

    @Regels(Regel.VR00052)
    public final List<String> geefAttributenFilterExpressieLijst(final Rol rol) {
        final List<String> abonnementExpressieStrings = new ArrayList<>();
        final List<ExpressietekstAttribuut> abonnementExpressies = getDienstbundel().geefGeldigeExpressies(rol);
        for (final ExpressietekstAttribuut abonementExpressieAttribuut : abonnementExpressies) {
            abonnementExpressieStrings.add(abonementExpressieAttribuut.getWaarde());
        }
        return abonnementExpressieStrings;
    }
}
