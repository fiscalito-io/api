
package io.fiscalito.api.domain.arca.wsfev1;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfFECAEADetRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfFECAEADetRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FECAEADetRequest" type="{http://ar.gov.afip.dif.FEV1/}FECAEADetRequest" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFECAEADetRequest", propOrder = {
    "fecaeaDetRequest"
})
public class ArrayOfFECAEADetRequest {

    @XmlElement(name = "FECAEADetRequest", nillable = true)
    protected List<FECAEADetRequest> fecaeaDetRequest;

    /**
     * Gets the value of the fecaeaDetRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fecaeaDetRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFECAEADetRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FECAEADetRequest }
     * 
     * 
     */
    public List<FECAEADetRequest> getFECAEADetRequest() {
        if (fecaeaDetRequest == null) {
            fecaeaDetRequest = new ArrayList<FECAEADetRequest>();
        }
        return this.fecaeaDetRequest;
    }

}
