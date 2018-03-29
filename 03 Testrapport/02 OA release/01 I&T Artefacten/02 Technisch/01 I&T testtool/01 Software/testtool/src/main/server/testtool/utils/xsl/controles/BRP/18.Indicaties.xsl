<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:import href="../../import/plat.xsl"/>

	<xsl:template match="lvg_synVerwerkPersoon">
		<Resultaat>
			<Bericht>
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/derdeHeeftGezag" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/onderCuratele" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/volledigeVerstrekkingsbeperking" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/vastgesteldNietNederlander" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/behandeldAlsNederlander" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/signaleringMetBetrekkingTotVerstrekkenReisdocument" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/staatloos" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/bijzondereVerblijfsrechtelijkePositie" />
				<xsl:apply-templates select="synchronisatie/bijgehoudenPersonen/persoon/indicaties/onverwerktDocumentAanwezig" />
			</Bericht>
		</Resultaat>
	</xsl:template>

</xsl:stylesheet>
