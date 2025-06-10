
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
 *         &lt;element name="FEParamGetTiposTributosResult" type="{http://ar.gov.afip.dif.FEV1/}FETributoResponse" minOccurs="0"/>
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
    "feParamGetTiposTributosResult"
})
@XmlRootElement(name = "FEParamGetTiposTributosResponse")
public class FEParamGetTiposTributosResponse {

    @XmlElement(name = "FEParamGetTiposTributosResult")
    protected FETributoResponse feParamGetTiposTributosResult;

    /**
     * Gets the value of the feParamGetTiposTributosResult property.
     * 
     * @return
     *     possible object is
     *     {@link FETributoResponse }
     *     
     */
    public FETributoResponse getFEParamGetTiposTributosResult() {
        return feParamGetTiposTributosResult;
    }

    /**
     * Sets the value of the feParamGetTiposTributosResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FETributoResponse }
     *     
     */
    public void setFEParamGetTiposTributosResult(FETributoResponse value) {
        this.feParamGetTiposTributosResult = value;
    }

}
