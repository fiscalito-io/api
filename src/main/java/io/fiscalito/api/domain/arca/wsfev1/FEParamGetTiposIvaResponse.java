
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
 *         &lt;element name="FEParamGetTiposIvaResult" type="{http://ar.gov.afip.dif.FEV1/}IvaTipoResponse" minOccurs="0"/>
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
    "feParamGetTiposIvaResult"
})
@XmlRootElement(name = "FEParamGetTiposIvaResponse")
public class FEParamGetTiposIvaResponse {

    @XmlElement(name = "FEParamGetTiposIvaResult")
    protected IvaTipoResponse feParamGetTiposIvaResult;

    /**
     * Gets the value of the feParamGetTiposIvaResult property.
     * 
     * @return
     *     possible object is
     *     {@link IvaTipoResponse }
     *     
     */
    public IvaTipoResponse getFEParamGetTiposIvaResult() {
        return feParamGetTiposIvaResult;
    }

    /**
     * Sets the value of the feParamGetTiposIvaResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IvaTipoResponse }
     *     
     */
    public void setFEParamGetTiposIvaResult(IvaTipoResponse value) {
        this.feParamGetTiposIvaResult = value;
    }

}
