/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * De wijze waarop een partij toegang krijgt tot (de gegevens geleverd via) een leveringsautorisatie.
 * <p/>
 * Een partij (lees: afnemer) kan via ��n of meer abonnementen gegevens uit de BRP ontvangen. Het is hierbij mogelijk dat een specifiek autorisatiemiddel
 * wordt ingezet, en eventueel dat er sprake is van een intermediair.
 */
@Table(schema = "AutAut", name = "ToegangLevsautorisatie")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ToegangLeveringsautorisatie extends AbstractToegangLeveringsautorisatie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected ToegangLeveringsautorisatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param geautoriseerde           geautoriseerde van ToegangLeveringsautorisatie.
     * @param leveringsautorisatie     leveringsautorisatie van ToegangLeveringsautorisatie.
     * @param ondertekenaar            ondertekenaar van ToegangLeveringsautorisatie.
     * @param transporteur             transporteur van ToegangLeveringsautorisatie.
     * @param datumIngang              datumIngang van ToegangLeveringsautorisatie.
     * @param datumEinde               datumEinde van ToegangLeveringsautorisatie.
     * @param naderePopulatiebeperking naderePopulatiebeperking van ToegangLeveringsautorisatie.
     * @param afleverpunt              afleverpunt van ToegangLeveringsautorisatie.
     * @param indicatieGeblokkeerd     indicatieGeblokkeerd van ToegangLeveringsautorisatie.
     */
    protected ToegangLeveringsautorisatie(
        final PartijRol geautoriseerde,
        final Leveringsautorisatie leveringsautorisatie,
        final Partij ondertekenaar,
        final Partij transporteur,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final UriAttribuut afleverpunt,
        final JaAttribuut indicatieGeblokkeerd)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(geautoriseerde,
            leveringsautorisatie,
            ondertekenaar,
            transporteur,
            datumIngang,
            datumEinde,
            naderePopulatiebeperking,
            afleverpunt,
            indicatieGeblokkeerd);
    }


    @Override
    public Integer getID() {
        return super.getID();
    }

    /**
     * Geeft aan of dit toegangabonnement "geldig" is op gegeven peilDatum. Dat wil zeggen {@code peilDatum} is op of na de datumIngang van het
     * toegangabonnement en datumEinde van het toegangabonnement is niet ingevuld _of_ {@code peilDatum} is voor datumEinde.
     *
     * @param peilDatum de peil datum
     * @return {@code true} als het toegangabonnement geldig is op peildatum, anders {@code false}
     */
    public boolean isGeldigOp(final DatumAttribuut peilDatum) {
        return getDatumIngang() != null && getDatumIngang().voorOfOp(peilDatum)
            && (getDatumEinde() == null || getDatumEinde().na(peilDatum));
    }


    public boolean isGeblokkeerd() {
        return getIndicatieGeblokkeerd() != null && getIndicatieGeblokkeerd().getWaarde() == Ja.J;
    }


    /**
     * Bepaalt of leveringautorisatie bijhouderrol heeft.
     *
     * @return heeft de leveringautorisatie de bijhouder rol
     */
    public final boolean heeftBijHouderRol() {
        final Rol rol = getGeautoriseerde().getRol();
        return rol != null && (rol == Rol.BIJHOUDINGSORGAAN_COLLEGE || rol == Rol.BIJHOUDINGSORGAAN_MINISTER);
    }

    /**
     * Geeft de nadere populatiebeperking expressie. Voor null waarden in de database wordt WAAR geretourneert, in alle andere gevallen wordt de expressie
     * zelf geretourneert
     *
     * @return een expressie String
     */
    public final String getNaderePopulatiebeperkingExpressieString() {
        final PopulatiebeperkingAttribuut populatiebeperkingAttribuut = super.getNaderePopulatiebeperking();
        String expressieWaarde = "WAAR";
        if (populatiebeperkingAttribuut != null && !StringUtils.isBlank(populatiebeperkingAttribuut.getWaarde())) {
            expressieWaarde = populatiebeperkingAttribuut.getWaarde();
        }
        return expressieWaarde;
    }
}
