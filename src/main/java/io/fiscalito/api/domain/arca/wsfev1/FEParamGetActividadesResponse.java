
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
 *         &lt;element name="FEParamGetActividadesResult" type="{http://ar.gov.afip.dif.FEV1/}FEActividadesResponse" minOccurs="0"/>
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
    "feParamGetActividadesResult"
})
@XmlRootElement(name = "FEParamGetActividadesResponse")
public class FEParamGetActividadesResponse {

    @XmlElement(name = "FEParamGetActividadesResult")
    protected FEActividadesResponse feParamGetActividadesResult;

    /**
     * Gets the value of the feParamGetActividadesResult property.
     * 
     * @return
     *     possible object is
     *     {@link FEActividadesResponse }
     *     
     */
    public FEActividadesResponse getFEParamGetActividadesResult() {
        return feParamGetActividadesResult;
    }

    /**
     * Sets the value of the feParamGetActividadesResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FEActividadesResponse }
     *     
     */
    public void setFEParamGetActividadesResult(FEActividadesResponse value) {
        this.feParamGetActividadesResult = value;
    }

}
