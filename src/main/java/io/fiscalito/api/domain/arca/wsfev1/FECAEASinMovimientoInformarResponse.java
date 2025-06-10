
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
 *         &lt;element name="FECAEASinMovimientoInformarResult" type="{http://ar.gov.afip.dif.FEV1/}FECAEASinMovResponse" minOccurs="0"/>
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
    "fecaeaSinMovimientoInformarResult"
})
@XmlRootElement(name = "FECAEASinMovimientoInformarResponse")
public class FECAEASinMovimientoInformarResponse {

    @XmlElement(name = "FECAEASinMovimientoInformarResult")
    protected FECAEASinMovResponse fecaeaSinMovimientoInformarResult;

    /**
     * Gets the value of the fecaeaSinMovimientoInformarResult property.
     * 
     * @return
     *     possible object is
     *     {@link FECAEASinMovResponse }
     *     
     */
    public FECAEASinMovResponse getFECAEASinMovimientoInformarResult() {
        return fecaeaSinMovimientoInformarResult;
    }

    /**
     * Sets the value of the fecaeaSinMovimientoInformarResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link FECAEASinMovResponse }
     *     
     */
    public void setFECAEASinMovimientoInformarResult(FECAEASinMovResponse value) {
        this.fecaeaSinMovimientoInformarResult = value;
    }

}
