/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.element;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;

/**
 * GroepElement.
 */
public final class GroepElement implements ElementObject {

    /**
     * Naamgeving voor identiteit groepen.
     */
    public static final String IDENTITEIT_GROEP_ELEMENT_NAAM = "identiteit";
    /**
     * Naamgeving voor standaard groepen.
     */
    public static final String STANDAARD_GROEP_ELEMENT_NAAM = "standaard";

    private final HistoriePatroon historiePatroon;
    private final VerantwoordingCategorie verantwoordingCategorie;

    private AttribuutElement dienstInhoudAttribuut;
    private AttribuutElement dienstVervalAttribuut;
    private AttribuutElement actieInhoudAttribuut;
    private AttribuutElement actieVervalAttribuut;
    private AttribuutElement actieAanpassingGeldigheidAttribuut;
    private AttribuutElement nadereAanduidingVervalAttribuut;
    private AttribuutElement datumAanvangGeldigheidAttribuut;
    private AttribuutElement datumEindeGeldigheidAttribuut;
    private AttribuutElement indicatieTbvLeveringMutatiesAttribuut;
    private AttribuutElement actieVervalTbvLeveringMutatiesAttribuut;
    private AttribuutElement datumTijdRegistratieAttribuut;
    private AttribuutElement datumTijdVervalAttribuut;
    private boolean isIdentiteitGroep;
    private boolean isStandaardGroep;
    private boolean isIdentificerend;
    private boolean isIndicatie;
    private boolean isMaterieel;
    private boolean isFormeel;
    private GroepElement aliasVan;
    private ObjectElement objectElement;
    private List<AttribuutElement> attributenInGroep;
    private Map<String, AttribuutElement> attribuutInGroepMap;
    private ElementObject typeObject;
    private Element element;

    /**
     * Constructor.
     * @param element record het record
     */
    GroepElement(final Element element) {
        this.element = element;
        historiePatroon = element.getHistoriePatroon();
        if (element.getElementWaarde().getVerantwoordingcategorie() != null) {
            verantwoordingCategorie = element.getElementWaarde().getVerantwoordingcategorie();
        } else {
            verantwoordingCategorie = VerantwoordingCategorie.G;
        }

        isIdentiteitGroep = IDENTITEIT_GROEP_ELEMENT_NAAM.equalsIgnoreCase(getElementNaam());
        isStandaardGroep = STANDAARD_GROEP_ELEMENT_NAAM.equalsIgnoreCase(getElementNaam());
        isIndicatie = getNaam().startsWith("Persoon.Indicatie");
        isMaterieel = historiePatroon == HistoriePatroon.F_M || historiePatroon == HistoriePatroon.F_M1;
        isFormeel = historiePatroon == HistoriePatroon.F || historiePatroon == HistoriePatroon.F1;
    }

    @Override
    public void postCreate(final ElementLocator locator) {

        if (!(getElement().getElementWaarde().getTypeAlias() == null || getElement().getElementWaarde().getTypeAlias() < 1)) {
            aliasVan = locator.getGroep(getElement().getElementWaarde().getTypeAlias());
        }

        objectElement = locator.getObject(getElement().getElementWaarde().getObjecttype());

        isIdentificerend = bepaalIdentificerend();

        attributenInGroep = locator.getAttributenInGroep(getId());

        attribuutInGroepMap = Maps.newHashMap();
        for (AttribuutElement attribuutElement : attributenInGroep) {
            attribuutInGroepMap.put(attribuutElement.getElementNaam(), attribuutElement);
        }

        dienstInhoudAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_DIENST_INHOUD);
        dienstVervalAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_DIENST_VERVAL);
        actieInhoudAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_ACTIE_INHOUD);
        actieVervalAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_ACTIE_VERVAL);
        actieAanpassingGeldigheidAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_ACTIE_AANPASSING_GELDIGHEID);
        nadereAanduidingVervalAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.NADERE_AANDUIDING_VERVAL);
        datumAanvangGeldigheidAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.DATUM_AANVANG_GELDIGHEID);
        datumEindeGeldigheidAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.DATUM_EINDE_GELDIGHEID);
        indicatieTbvLeveringMutatiesAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.VOORKOMEN_TBV_LEVERING_MUTATIES);
        actieVervalTbvLeveringMutatiesAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.BRP_ACTIE_VERVAL_TBV_LEVERING_MUTATIES);
        datumTijdRegistratieAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.DATUM_TIJD_REGISTRATIE);
        datumTijdVervalAttribuut = getAttribuutMetElementNaamOfNull(AttribuutElement.DATUM_TIJD_VERVAL);
        typeObject = getTypeObject(locator);
    }

    @Override
    public <T extends ElementObject> T getTypeObject() {
        return (T) typeObject;
    }

    @Override
    public Element getElement() {
        return element;
    }


    @Override
    public int getVolgnummer() {
        return element.getVolgnummer();
    }

    /**
     * @return de groep alias, of null indien het attribuut geen alias heeft.
     */
    public GroepElement getAliasVan() {
        return aliasVan;
    }

    public ObjectElement getObjectElement() {
        return objectElement;
    }

    /**
     * @return het type verantwoording van de groep
     */
    public VerantwoordingCategorie getVerantwoordingCategorie() {
        return verantwoordingCategorie;
    }

    /**
     * @return het type historie van de groep
     */
    public HistoriePatroon getHistoriePatroon() {
        return historiePatroon;
    }

    /**
     * @return indicatie of het een 'identiteit' groep betreft
     */
    public boolean isIdentiteitGroep() {
        return isIdentiteitGroep;
    }

    /**
     * @return indicatie of het een 'standaard' groep betreft
     */
    public boolean isStandaardGroep() {
        return isStandaardGroep;
    }

    /**
     * @return het dienst inhoud attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDienstInhoudAttribuut() {
        return dienstInhoudAttribuut;
    }

    /**
     * @return het dienst verval attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDienstVervalAttribuut() {
        return dienstVervalAttribuut;
    }

    /**
     * @return het actie inhoud attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getActieInhoudAttribuut() {
        return actieInhoudAttribuut;
    }

    /**
     * @return het actie verval attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getActieVervalAttribuut() {
        return actieVervalAttribuut;
    }

    /**
     * @return het actie aanpassing geldigheid attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getActieAanpassingGeldigheidAttribuut() {
        return actieAanpassingGeldigheidAttribuut;
    }

    /**
     * @return het nadere aanduiding verval attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getNadereAanduidingVervalAttribuut() {
        return nadereAanduidingVervalAttribuut;
    }

    /**
     * @return het datum aanvang geldigheid, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDatumAanvangGeldigheidAttribuut() {
        return datumAanvangGeldigheidAttribuut;
    }

    /**
     * @return het datum einde geldigheid attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDatumEindeGeldigheidAttribuut() {
        return datumEindeGeldigheidAttribuut;
    }

    /**
     * @return het indicatie tbv leverning mutatie attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getIndicatieTbvLeveringMutatiesAttribuut() {
        return indicatieTbvLeveringMutatiesAttribuut;
    }

    /**
     * @return het actie verval tbv levering mutaties  attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getActieVervalTbvLeveringMutatiesAttribuut() {
        return actieVervalTbvLeveringMutatiesAttribuut;
    }

    /**
     * @return het datum tijd registratie attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDatumTijdRegistratieAttribuut() {
        return datumTijdRegistratieAttribuut;
    }

    /**
     * @return het datum tijd verval attribuut, null als de groep dit attribuut niet kent
     */
    public AttribuutElement getDatumTijdVervalAttribuut() {
        return datumTijdVervalAttribuut;
    }

    /**
     * Een Identificerende groep is een voorkomen van een groep dat altijd geleverd wordt zodat de Afnemer in staat is om vast te stellen welke Persoon het
     * betreft.
     * @return isIdentificerend
     */
    @Bedrijfsregel(Regel.R1542)
    public boolean isIdentificerend() {
        return isIdentificerend;
    }

    /**
     * @param elementNaam naam van element
     * @return het element
     */
    public AttribuutElement getAttribuutMetElementNaam(final String elementNaam) {
        final AttribuutElement attribuutElement = attribuutInGroepMap.get(elementNaam);
        if (attribuutElement == null) {
            throw new IllegalStateException(String.format("Groep %s[%d] kent element %s niet ", getNaam(), getId(), elementNaam));
        }
        return attribuutElement;
    }

    /**
     * @param elementNaam naam van element
     * @return het element
     */
    public AttribuutElement getAttribuutMetElementNaamOfNull(final String elementNaam) {
        return attribuutInGroepMap.get(elementNaam);
    }

    /**
     * @param elementNaam naam van element
     * @return indicatie of de groep een attribuut bevat met de gegeven elementnaam
     */
    public boolean bevatAttribuut(final String elementNaam) {
        return attribuutInGroepMap.containsKey(elementNaam);
    }

    /**
     * @return de attributen in deze groep
     */
    public Iterable<AttribuutElement> getAttributenInGroep() {
        return attributenInGroep;
    }

    /**
     * Bepaalt of het {@link HistoriePatroon} voor deze groep een formele aard heeft, en geen materiele.
     * @return true als formeel anders false
     */
    public boolean isFormeel() {
        return isFormeel;
    }

    /**
     * Bepaalt of het {@link HistoriePatroon} voor deze groep een materiele (en impliciet formele) aard heeft.
     * @return true als materieel anders false
     */
    public boolean isMaterieel() {
        return isMaterieel;
    }

    /**
     * @return indicatie of het een 'indicatie-groep' betreft
     */
    public boolean isIndicatie() {
        return isIndicatie;
    }

    private boolean bepaalIdentificerend() {
        return isGeboorteGroep() || isGeslachtsaanduidingGroep() || isIdentificatienummersGroep() || isSamengesteldeNaamGroep();
    }

    private boolean isGeboorteGroep() {
        return getId().equals(Element.PERSOON_GEBOORTE.getId()) || getAliasVan() != null && getAliasVan().getId().equals(Element.PERSOON_GEBOORTE.getId());
    }

    private boolean isGeslachtsaanduidingGroep() {
        return getId().equals(Element.PERSOON_GESLACHTSAANDUIDING.getId()) || getAliasVan() != null && getAliasVan().getId()
                .equals(Element.PERSOON_GESLACHTSAANDUIDING.getId());
    }

    private boolean isIdentificatienummersGroep() {
        return getId().equals(Element.PERSOON_IDENTIFICATIENUMMERS.getId())
                || getAliasVan() != null && getAliasVan().getId().equals(Element.PERSOON_IDENTIFICATIENUMMERS.getId());
    }

    private boolean isSamengesteldeNaamGroep() {
        return getId().equals(Element.PERSOON_SAMENGESTELDENAAM.getId()) || getAliasVan() != null && getAliasVan().getId()
                .equals(Element.PERSOON_SAMENGESTELDENAAM.getId());
    }

    /**
     * Bepaalt of een groep een onderzoeksgroep is, volgens definitie R1543.
     * @return true als groep een onderzoeksgroep is
     */
    @Bedrijfsregel(Regel.R1543)
    public boolean isOnderzoeksgroep() {
        return Lists.newArrayList(
                ElementHelper.getObjectElement(Element.ONDERZOEK),
                ElementHelper.getObjectElement(Element.GEGEVENINONDERZOEK)).contains(this.getObjectElement());
    }

    /**
     * Bepaalt of een groep een verantwoordingsgroep is, volgens definitie R1541.
     * @return true als groep een verantwoordingsgroep is
     */
    @Bedrijfsregel(Regel.R1541)
    public boolean isVerantwoordingsgroep() {
        return Lists.newArrayList(
                ElementHelper.getObjectElement(Element.ADMINISTRATIEVEHANDELING),
                ElementHelper.getObjectElement(Element.ACTIE),
                ElementHelper.getObjectElement(Element.ACTIEBRON),
                ElementHelper.getObjectElement(Element.DOCUMENT),
                ElementHelper.getObjectElement(Element.GEDEBLOKKEERDEMELDING)).contains(this.getObjectElement());
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(element.getId());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {

            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GroepElement elem = (GroepElement) o;
        return this.element.getId() == elem.getElement().getId();

    }

    @Override
    public String toString() {
        return String.format("%s [%d]", element.getNaam(), element.getId());
    }
}
