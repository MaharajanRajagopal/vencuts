<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" omit-xml-declaration="yes" indent="yes"
		standalone="yes" />

	<xsl:template match="/">
		<xsl:for-each select="records/record">
			<xsl:variable name="reference" select="@reference"/>
			
			<xsl:variable name="dups" select="../record[generate-id() = generate-id(key('references', @reference)[2])]" />
			<xsl:variable name="duplicateref">
				<xsl:for-each select="//records/record[@reference = $reference]">
					<xsl:value-of select="$reference" />
				</xsl:for-each>
			</xsl:variable>
			<xsl:decimal-format name="coerce" NaN="0" />
			<xsl:variable name="a" select="format-number(startBalance, '#.00', 'coerce')"/>
			<xsl:variable name="b" select="format-number(mutation, '#.00', 'coerce')"/>
			<xsl:choose>
			<xsl:when test="format-number($a + $b,'#.##','coerce') != number(endBalance)">
				<inValidRecords>
			        <reference><xsl:value-of select="$reference"/></reference>
			        <mutation><xsl:value-of select="mutation"/></mutation>
			        <endBalance><xsl:value-of select="endBalance"/></endBalance>
			        <description><xsl:value-of select="description"/></description>
			        <accountNumber><xsl:value-of select="accountNumber"/></accountNumber>
			        <startBalance><xsl:value-of select="startBalance"/></startBalance>
			    </inValidRecords>
			</xsl:when>
			<xsl:otherwise>
				<ValidRecords>
			        <reference><xsl:value-of select="$reference"/></reference>
			        <mutation><xsl:value-of select="mutation"/></mutation>
			        <endBalance><xsl:value-of select="endBalance"/></endBalance>
			        <description><xsl:value-of select="description"/></description>
			        <accountNumber><xsl:value-of select="accountNumber"/></accountNumber>
			        <startBalance><xsl:value-of select="startBalance"/></startBalance>
			    </ValidRecords>
			</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
