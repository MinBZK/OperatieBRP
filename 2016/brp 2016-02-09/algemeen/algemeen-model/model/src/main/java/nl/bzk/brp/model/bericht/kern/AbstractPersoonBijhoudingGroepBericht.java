/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroepBasis;

/**
 * Groep gegevens over de bijhouding
 *
 * De Wet BRP kent twee afdelingen voor bijhouden, afdeling I ('Ingezetenen') en afdeling II ('Niet ingezetenen'). Deze
 * afdelingen bepalen de wijze waarop de bijhouding plaats vindt. Hierbij is het van belang of iemand thans een
 * ingezetene is (en valt onder afdeling I), dat iemand geëmigreerd is, of dat iemand van begin af aan onder afdeling II
 * werd bijgehouden. Dit onderscheid wordt aangegeven met bijhoudingsaard.
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. In geval van een opgeschorte bijhouding, is 'ingezetene' en 'niet-ingezetene' minder goed gedefinieerd. In het LO
 * BRP wordt dit punt uitgewerkt. 2. Vorm van historie: Beiden. De bijhoudingsverantwoordelijkheid kan verschuiven door
 * bijvoorbeeld een verhuizing die om technische redenen later wordt geregistreerd (maar wellicht op tijd is
 * doorgegeven). De datum ingang geldigheid van de (nieuwe) bijhoudingsverantwoordelijkheid ligt dus vaak vóór de
 * datumtijd registratie. Een formele tijdslijn alleen is dus onvoldoende. Omdat, in geval van verschuivingen van
 * verantwoordelijkheid tussen Minister en College B&W, ook van belang kan zijn wie over een periode in het verleden 'de
 * verantwoordelijke' was (voor het wijzigen van historische gegevens), is de materiële tijdslijn ook relevant, en dus
 * wordt deze vastgelegd. 3. De naam is aangepast naar bijhoudingsaard, tegen de term bijhoudingsverantwoordelijkheid
 * was teveel weerstand.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonBijhoudingGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep, PersoonBijhoudingGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3664;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3573, 3568, 10864, 3578);
    private String bijhoudingspartijCode;
    private PartijAttribuut bijhoudingspartij;
    private BijhoudingsaardAttribuut bijhoudingsaard;
    private NadereBijhoudingsaardAttribuut nadereBijhoudingsaard;
    private JaNeeAttribuut indicatieOnverwerktDocumentAanwezig;

    /**
     * Retourneert Bijhoudingspartij van Bijhouding.
     *
     * @return Bijhoudingspartij.
     */
    public String getBijhoudingspartijCode() {
        return bijhoudingspartijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsaardAttribuut getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NadereBijhoudingsaardAttribuut getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Zet Bijhoudingspartij van Bijhouding.
     *
     * @param bijhoudingspartijCode Bijhoudingspartij.
     */
    public void setBijhoudingspartijCode(final String bijhoudingspartijCode) {
        this.bijhoudingspartijCode = bijhoudingspartijCode;
    }

    /**
     * Zet Bijhoudingspartij van Bijhouding.
     *
     * @param bijhoudingspartij Bijhoudingspartij.
     */
    public void setBijhoudingspartij(final PartijAttribuut bijhoudingspartij) {
        this.bijhoudingspartij = bijhoudingspartij;
    }

    /**
     * Zet Bijhoudingsaard van Bijhouding.
     *
     * @param bijhoudingsaard Bijhoudingsaard.
     */
    public void setBijhoudingsaard(final BijhoudingsaardAttribuut bijhoudingsaard) {
        this.bijhoudingsaard = bijhoudingsaard;
    }

    /**
     * Zet Nadere bijhoudingsaard van Bijhouding.
     *
     * @param nadereBijhoudingsaard Nadere bijhoudingsaard.
     */
    public void setNadereBijhoudingsaard(final NadereBijhoudingsaardAttribuut nadereBijhoudingsaard) {
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
    }

    /**
     * Zet Onverwerkt document aanwezig? van Bijhouding.
     *
     * @param indicatieOnverwerktDocumentAanwezig Onverwerkt document aanwezig?.
     */
    public void setIndicatieOnverwerktDocumentAanwezig(final JaNeeAttribuut indicatieOnverwerktDocumentAanwezig) {
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (bijhoudingspartij != null) {
            attributen.add(bijhoudingspartij);
        }
        if (bijhoudingsaard != null) {
            attributen.add(bijhoudingsaard);
        }
        if (nadereBijhoudingsaard != null) {
            attributen.add(nadereBijhoudingsaard);
        }
        if (indicatieOnverwerktDocumentAanwezig != null) {
            attributen.add(indicatieOnverwerktDocumentAanwezig);
        }
        return attributen;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
