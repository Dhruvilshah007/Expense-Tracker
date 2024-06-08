package com.ds.expensetracker.authentication.model;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;


//@MappedSup`erclass ensures that the fields in BaseEntity are mapped to the database columns of the subclass table but BaseEntity itself is not a separate table.

@MappedSuperclass
@Data
public abstract class BaseEntity {


    //added Proctected because were not able to access outside package
    public BaseEntity(){
        this.activeFlag=1;
    }

    @Column(name = "active_flag", nullable = false, columnDefinition = "int default 1")
    private int activeFlag;

    @CreationTimestamp  //By default will add currentTimestamp
    @Temporal(TemporalType.TIMESTAMP)   //Will store Date and time both
    private Date createdDate;
    private String createdByIpaddress;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    private String updatedByIpaddress;

}
