
package io.fiscalito.api.domain.arca.wsfev1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FECAEARegInformativoResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEAResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fecaeaRegInformativoResult"
})
@XmlRootElement(name = "FECAEARegInformativoResponse")
public class FECAEARegInformativoResponse {

    @XmlElement(name = "FECAEARegInformativoResult")
    protected FECAEAResponse fecaeaRegInformativoResult;

    /**
     * Gets the value of the fecaeaRegInformativoResult property.
     * 
     * @return
     *     possible object is
     *     {@link FECAEAResponse }
     *     
     */
    public FECAEAResponse getFECAEARegInformativoResult() {
        return fecaeaRegInformativoResult;
    }

    /**
     * Sets the value of the fecaeaRegInformativoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEAResponse }
     *     
     */
    public void setFECAEARegInformativoResult(FECAEAResponse value) {
        this.fecaeaRegInformativoResult = value;
    }

}
