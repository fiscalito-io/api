
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
 *         &lt;element name="FEParamGetTiposMonedasResult" type="{http://ar.gov.afip.dif.FEV1/}MonedaResponse" minOccurs="0"/>
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
    "feParamGetTiposMonedasResult"
})
@XmlRootElement(name = "FEParamGetTiposMonedasResponse")
public class FEParamGetTiposMonedasResponse {

    @XmlElement(name = "FEParamGetTiposMonedasResult")
    protected MonedaResponse feParamGetTiposMonedasResult;

    /**
     * Gets the value of the feParamGetTiposMonedasResult property.
     * 
     * @return
     *     possible object is
     *     {@link MonedaResponse }
     *     
     */
    public MonedaResponse getFEParamGetTiposMonedasResult() {
        return feParamGetTiposMonedasResult;
    }

    /**
     * Sets the value of the feParamGetTiposMonedasResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link MonedaResponse }
     *     
     */
    public void setFEParamGetTiposMonedasResult(MonedaResponse value) {
        this.feParamGetTiposMonedasResult = value;
    }

}
