
package io.fiscalito.api.domain.arca.wsfev1;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCbteAsoc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCbteAsoc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CbteAsoc" type="{http://ar.gov.afip.dif.FEV1/}CbteAsoc" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCbteAsoc", propOrder = {
    "cbteAsoc"
})
public class ArrayOfCbteAsoc {

    @XmlElement(name = "CbteAsoc", nillable = true)
    protected List<CbteAsoc> cbteAsoc;

    /**
     * Gets the value of the cbteAsoc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cbteAsoc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCbteAsoc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CbteAsoc }
     * 
     * 
     */
    public List<CbteAsoc> getCbteAsoc() {
        if (cbteAsoc == null) {
            cbteAsoc = new ArrayList<CbteAsoc>();
        }
        return this.cbteAsoc;
    }

}
