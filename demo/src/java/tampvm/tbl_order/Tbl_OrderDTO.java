/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tampvm.tbl_order;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author minht
 */
public class Tbl_OrderDTO implements Serializable {
    private String id;
    private Date date;
    private float total;

    public Tbl_OrderDTO() {
    }

    public Tbl_OrderDTO(String id, Date date, float total) {
        this.id = id;
        this.date = date;
        this.total = total;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(float total) {
        this.total = total;
    }    
}
