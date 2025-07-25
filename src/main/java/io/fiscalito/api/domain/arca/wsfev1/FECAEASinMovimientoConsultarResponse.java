
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
 *         &lt;element name="FECAEASinMovimientoConsultarResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEASinMovConsResponse" minOccurs="0"/>
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
    "fecaeaSinMovimientoConsultarResult"
})
@XmlRootElement(name = "FECAEASinMovimientoConsultarResponse")
public class FECAEASinMovimientoConsultarResponse {

    @XmlElement(name = "FECAEASinMovimientoConsultarResult")
    protected FECAEASinMovConsResponse fecaeaSinMovimientoConsultarResult;

    /**
     * Gets the value of the fecaeaSinMovimientoConsultarResult property.
     * 
     * @return
     *     possible object is
     *     {@link FECAEASinMovConsResponse }
     *     
     */
    public FECAEASinMovConsResponse getFECAEASinMovimientoConsultarResult() {
        return fecaeaSinMovimientoConsultarResult;
    }

    /**
     * Sets the value of the fecaeaSinMovimientoConsultarResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEASinMovConsResponse }
     *     
     */
    public void setFECAEASinMovimientoConsultarResult(FECAEASinMovConsResponse value) {
        this.fecaeaSinMovimientoConsultarResult = value;
    }

}
