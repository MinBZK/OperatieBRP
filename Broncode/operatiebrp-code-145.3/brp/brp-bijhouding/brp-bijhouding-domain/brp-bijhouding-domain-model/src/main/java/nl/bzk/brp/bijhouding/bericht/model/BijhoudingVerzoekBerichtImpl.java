/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AbstractDelegatePersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RootEntiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementen;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;
import nl.bzk.brp.bijhouding.bericht.util.ValidatieHelper;

/**
 * Een bericht dat wordt afgehandeld door de bijhouding module.
 */
@XmlElementen(enumType = BijhoudingBerichtSoort.class, methode = "getSoort")
public final class BijhoudingVerzoekBerichtImpl extends AbstractBmrGroep implements BijhoudingVerzoekBericht {

    private final BijhoudingBerichtSoort soort;
    private final StuurgegevensElement stuurgegevens;
    private final ParametersElement parameters;
    private final AdministratieveHandelingElement administratieveHandeling;

    private String oinWaardeOndertekenaar;
    private String oinWaardeTransporteur;
    private String xml;
    @XmlTransient
    private final DatumTijdElement tijdstipOntvangst;
    @XmlTransient
    private final DatumElement datumOntvangst;
    private ObjectSleutelIndex objectSleutelIndex;
    @XmlTransient
    private List<BmrGroepReferentie> referenties;
    @XmlTransient
    private List<Element> postConstructElements;

    /**
     * Maakt een BijhoudingVerzoekBericht object.
     * @param attributen de attributen
     * @param soort het soort bijhoudingsbericht
     * @param stuurgegevens de stuurgegevens
     * @param parameters de parameters
     * @param administratieveHandeling de administratieve handeling
     */
    public BijhoudingVerzoekBerichtImpl(
            final Map<String, String> attributen,
            final BijhoudingBerichtSoort soort,
            final StuurgegevensElement stuurgegevens,
            final ParametersElement parameters,
            final AdministratieveHandelingElement administratieveHandeling) {
        super(attributen);
        postConstructElements = new ArrayList<>();
        ValidatieHelper.controleerOpNullWaarde(soort, "soort");
        ValidatieHelper.controleerOpNullWaarde(stuurgegevens, "stuurgegevens");
        ValidatieHelper.controleerOpNullWaarde(parameters, "parameters");
        ValidatieHelper.controleerOpNullWaarde(administratieveHandeling, "administratieveHandeling");
        this.soort = soort;
        this.stuurgegevens = stuurgegevens;
        this.parameters = parameters;
        this.administratieveHandeling = administratieveHandeling;
        this.tijdstipOntvangst = new DatumTijdElement(ZonedDateTime.now());
        this.datumOntvangst = new DatumElement(DatumUtil.vandaag());
    }

    @Override
    public BijhoudingBerichtSoort getSoort() {
        return soort;
    }

    @Override
    public StuurgegevensElement getStuurgegevens() {
        return stuurgegevens;
    }

    @Override
    public ParametersElement getParameters() {
        return parameters;
    }

    @Override
    public AdministratieveHandelingElement getAdministratieveHandeling() {
        return administratieveHandeling;
    }

    @Override
    public String getOinWaardeOndertekenaar() {
        return oinWaardeOndertekenaar;
    }

    @Override
    public void setOinWaardeOndertekenaar(final String oinWaardeOndertekenaar) {
        this.oinWaardeOndertekenaar = oinWaardeOndertekenaar;
    }

    @Override
    public String getOinWaardeTransporteur() {
        return oinWaardeTransporteur;
    }

    @Override
    public void setOinWaardeTransporteur(final String oinWaardeTransporteur) {
        this.oinWaardeTransporteur = oinWaardeTransporteur;
    }

    @Override
    public String getXml() {
        return xml;
    }

    @Override
    public void setXml(final String xml) {
        this.xml = xml;
    }

    @Override
    public boolean isPrevalidatie() {
        return Verwerkingswijze.PREVALIDATIE.equals(Verwerkingswijze.parseNaam(getParameters().getVerwerkingswijze().getWaarde()));
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    @Override
    public DatumTijdElement getTijdstipOntvangst() {
        return tijdstipOntvangst;
    }

    @Override
    public DatumElement getDatumOntvangst() {
        return datumOntvangst;
    }

    @Override
    public void setObjectSleutelIndex(final ObjectSleutelIndex objectSleutelIndex) {
        this.objectSleutelIndex = objectSleutelIndex;
    }

    @Override
    public List<MeldingElement> laadEntiteitenVoorObjectSleutels() {
        return objectSleutelIndex.initialize();
    }

    @Override
    public <T extends RootEntiteit> T getEntiteitVoorObjectSleutel(final Class<T> entiteitType, final String objectSleutel) {
        return objectSleutelIndex.getEntiteitVoorObjectSleutel(entiteitType, objectSleutel);
    }

    private <T extends RootEntiteit> List<T> getEntiteiten(final Class<T> entiteitType) {
        return objectSleutelIndex.getEntiteiten(entiteitType);
    }

    @Override
    public <T extends RootEntiteit> T getEntiteitVoorId(final Class<T> entiteitType, final Number databaseId) {
        return objectSleutelIndex.getEntiteitVoorId(entiteitType, databaseId);
    }

    @Override
    public void voegToeAanObjectSleutelIndex(final RootEntiteit rootEntiteit) {
        objectSleutelIndex.voegToe(rootEntiteit);
    }

    @Override
    public Partij getZendendePartij() {
        return getDynamischeStamtabelRepository().getPartijByCode(getStuurgegevens().getZendendePartij().getWaarde());
    }

    @Override
    public <T extends RootEntiteit> void vervangEntiteitMetId(final Class<T> entiteitType, final Number databaseId, final T nieuweRootEntiteit) {
        objectSleutelIndex.vervangEntiteitMetId(entiteitType, databaseId, nieuweRootEntiteit);
    }

    @Override
    public <T extends RootEntiteit> void voegObjectSleutelToe(final String objectSleutel, final Class<T> entiteitType, final Number databaseId) {
        objectSleutelIndex.voegObjectSleutelToe(objectSleutel, entiteitType, databaseId);
    }

    @Override
    public void setReferenties(final List<BmrGroepReferentie> referenties) {
        this.referenties = referenties;
    }

    @Override
    @Bedrijfsregel(Regel.R1838)
    /* referentie.getReferentie kan een class cast exception opleveren indien niet null.
     * referentie.verwijst
     */
    public List<MeldingElement> controleerReferentiesInBericht() {
        final List<MeldingElement> result = new ArrayList<>();
        for (final BmrGroepReferentie referentie : referenties) {
            if (!referentie.verwijstNaarBestaandEnJuisteType()) {
                result.add(MeldingElement.getInstance(Regel.R1838, referentie));
            }
        }
        return result;
    }

    @Override
    public void registreerPersoonElementenBijBijhoudingPersonen() {
        for (final ActieElement actie : getAdministratieveHandeling().getActies()) {
            for (final PersoonElement persoonElement : actie.getPersoonElementen()) {
                if (persoonElement.heeftPersoonEntiteit()) {
                    persoonElement.getPersoonEntiteit().registreerPersoonElement(persoonElement);
                }
            }
        }
    }

    @Override
    public Set<BijhoudingPersoon> getTeArchiverenPersonen() {
        Set<BijhoudingPersoon> mogelijkTeArchiverenPersonen = new HashSet<>();
        mogelijkTeArchiverenPersonen.addAll(getEntiteiten(BijhoudingPersoon.class));

        final List<BijhoudingRelatie> bijhoudingRelaties = getEntiteiten(BijhoudingRelatie.class);
        for (final BijhoudingRelatie bijhoudingRelatie : bijhoudingRelaties) {
            mogelijkTeArchiverenPersonen.addAll(bijhoudingRelatie.getHoofdPersonen(this));
        }

        return mogelijkTeArchiverenPersonen.stream().filter(AbstractDelegatePersoon::isPersoonIngeschrevene).collect(Collectors.toSet());
    }

    @Override
    public void postConstruct() {
        final Iterator<Element> elementIterator = postConstructElements.iterator();
        while (elementIterator.hasNext()) {
            elementIterator.next().postConstruct();
            elementIterator.remove();
        }
    }

    @Override
    public void registreerPostConstructAanroep(final Element element) {
        postConstructElements.add(element);
    }
}
