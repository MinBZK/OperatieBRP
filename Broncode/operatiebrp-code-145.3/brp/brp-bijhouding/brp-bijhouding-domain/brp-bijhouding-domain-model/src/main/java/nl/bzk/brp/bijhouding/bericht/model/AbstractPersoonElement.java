/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlTransient;

/**
 * Abstracte persoon element.
 */
public abstract class AbstractPersoonElement extends AbstractBmrGroepReferentie<PersoonElement> implements PersoonElement {

    private final AfgeleidAdministratiefElement afgeleidAdministratief;
    private final IdentificatienummersElement identificatienummers;
    private final SamengesteldeNaamElement samengesteldeNaam;
    private final GeboorteElement geboorte;
    private final GeslachtsaanduidingElement geslachtsaanduiding;
    private final BijhoudingElement bijhouding;
    private final VerblijfsrechtElement verblijfsrecht;
    @XmlChildList(listElementType = VoornaamElement.class)
    private final List<VoornaamElement> voornamen;
    @XmlChildList(listElementType = GeslachtsnaamcomponentElement.class)
    private final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten;
    private final NaamgebruikElement naamgebruik;
    @XmlChildList(listElementType = AdresElement.class)
    private final List<AdresElement> adressen;
    @XmlChildList(listElementType = IndicatieElement.class)
    private final List<IndicatieElement> indicaties;
    @XmlChildList(listElementType = VerstrekkingsbeperkingElement.class)
    private final List<VerstrekkingsbeperkingElement> verstrekkingsbeperkingen;
    private final MigratieElement migratie;
    @XmlChildList(listElementType = NationaliteitElement.class)
    private final List<NationaliteitElement> nationaliteiten;
    @XmlChildList(listElementType = OnderzoekElement.class)
    private final List<OnderzoekElement> onderzoeken;
    @XmlTransient
    private BijhoudingPersoon nieuwIngeschrevenBijhoudingPersoon;

    /**
     * Maakt een AbstractBmrGroepReferentie object.
     * @param basisAttribuutGroep de lijst met attributen waarin objecttype moet voorkomen
     */
    AbstractPersoonElement(
            final Map<String, String> basisAttribuutGroep,
            final AfgeleidAdministratiefElement afgeleidAdministratief,
            final IdentificatienummersElement identificatienummers,
            final SamengesteldeNaamElement samengesteldeNaam,
            final GeboorteElement geboorte,
            final GeslachtsaanduidingElement geslachtsaanduiding,
            final BijhoudingElement bijhouding,
            final VerblijfsrechtElement verblijfsrecht,
            final List<VoornaamElement> voornamen,
            final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten,
            final NaamgebruikElement naamgebruik,
            final List<AdresElement> adressen,
            final List<IndicatieElement> indicaties,
            final List<VerstrekkingsbeperkingElement> verstrekkingsbeperkingen,
            final MigratieElement migratie,
            final List<NationaliteitElement> nationaliteiten,
            final List<OnderzoekElement> onderzoeken) {
        super(basisAttribuutGroep);
        this.adressen = initArrayList(adressen);
        this.afgeleidAdministratief = afgeleidAdministratief;
        this.bijhouding = bijhouding;
        this.geboorte = geboorte;
        this.geslachtsaanduiding = geslachtsaanduiding;
        this.geslachtsnaamcomponenten = initArrayList(geslachtsnaamcomponenten);
        this.identificatienummers = identificatienummers;
        this.indicaties = initArrayList(indicaties);
        this.migratie = migratie;
        this.naamgebruik = naamgebruik;
        this.nationaliteiten = initArrayList(nationaliteiten);
        this.samengesteldeNaam = samengesteldeNaam;
        this.voornamen = initArrayList(voornamen);
        this.verblijfsrecht = verblijfsrecht;
        this.verstrekkingsbeperkingen = initArrayList(verstrekkingsbeperkingen);
        this.onderzoeken = initArrayList(onderzoeken);
    }

    private void koppelEntiteitenAanElementenMetObjectSleutel() {
        if (getEntiteit() != null) {
            for (final NationaliteitElement nationaliteit : nationaliteiten) {
                nationaliteit.bepaalBijhoudingPersoonNationaliteitEntiteit(getEntiteit());
            }
        }
    }

    @Override
    public final PersoonElement getReferentie() {
        return (PersoonElement) getGroep();
    }

    @Override
    public boolean inObjectSleutelIndex() {
        return true;
    }

    @Override
    public final boolean verwijstNaarBestaandEnJuisteType() {
        return getReferentieId() == null || getGroep() instanceof PersoonElement;
    }

    final void setNieuwIngeschrevenBijhoudingPersoon(final BijhoudingPersoon nieuwIngeschrevenBijhoudingPersoon) {
        this.nieuwIngeschrevenBijhoudingPersoon = nieuwIngeschrevenBijhoudingPersoon;
    }

    @Override
    public void postConstruct() {
        koppelEntiteitenAanElementenMetObjectSleutel();
    }

    public final BijhoudingPersoon getPersoonEntiteit() {
        final BijhoudingPersoon result;
        if (getReferentieId() != null) {
            result = getReferentie().getPersoonEntiteit();
        } else if (heeftObjectSleutel()) {
            result = getEntiteit();
        } else if (nieuwIngeschrevenBijhoudingPersoon != null) {
            result = nieuwIngeschrevenBijhoudingPersoon;
        } else {
            result = null;
        }
        return result;
    }

    @Override
    public final boolean heeftPersoonEntiteit() {
        if (getReferentieId() != null) {
            return getReferentie().heeftPersoonEntiteit();
        } else {
            return heeftObjectSleutel() || nieuwIngeschrevenBijhoudingPersoon != null;
        }
    }

    @Override
    public final Class<BijhoudingPersoon> getEntiteitType() {
        return BijhoudingPersoon.class;
    }

    @Override
    public final IdentificatienummersElement getIdentificatienummers() {
        return identificatienummers;
    }

    @Override
    public final SamengesteldeNaamElement getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public final GeboorteElement getGeboorte() {
        return geboorte;
    }

    @Override
    public final GeslachtsaanduidingElement getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }

    @Override
    public final List<VoornaamElement> getVoornamen() {
        return Collections.unmodifiableList(voornamen);
    }

    @Override
    public final List<GeslachtsnaamcomponentElement> getGeslachtsnaamcomponenten() {
        return Collections.unmodifiableList(geslachtsnaamcomponenten);
    }

    @Override
    public final List<AdresElement> getAdressen() {
        return Collections.unmodifiableList(adressen);
    }

    @Override
    public final AdresElement getAdres() {
        if (adressen.isEmpty()) {
            return null;
        }
        return adressen.get(0);
    }

    @Override
    public final MigratieElement getMigratie() {
        return migratie;
    }

    @Override
    public final List<IndicatieElement> getIndicaties() {
        return Collections.unmodifiableList(indicaties);
    }

    @Override
    public final List<VerstrekkingsbeperkingElement> getVerstrekkingsbeperkingen() {
        return Collections.unmodifiableList(verstrekkingsbeperkingen);
    }

    /**
     * Geeft lijst met onderzoeken terug.
     * @return lijst met onderzoeken
     */
    public final List<OnderzoekElement> getOnderzoeken() {
        return Collections.unmodifiableList(onderzoeken);
    }

    @Override
    public final NaamgebruikElement getNaamgebruik() {
        return naamgebruik;
    }

    @Override
    public final AfgeleidAdministratiefElement getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    @Override
    public final List<NationaliteitElement> getNationaliteiten() {
        return nationaliteiten;
    }

    @Override
    public final boolean heeftNationaliteiten() {
        return !nationaliteiten.isEmpty();
    }

    @Override
    public final NationaliteitElement getNationaliteit() {
        if (nationaliteiten.isEmpty()) {
            return null;
        }
        return nationaliteiten.get(0);
    }

    @Override
    public final BijhoudingElement getBijhouding() {
        return bijhouding;
    }

    @Override
    public final VerblijfsrechtElement getVerblijfsrecht() {
        return verblijfsrecht;
    }
}
