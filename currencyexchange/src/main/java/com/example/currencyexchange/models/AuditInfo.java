package com.example.currencyexchange.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Data
@Table(name = "audit_info")
public class AuditInfo {

    @Id
    @Column(name = "requestId")
    private Integer requestId;

    public enum RequestStatus {
        //2 possible values for status col in database
        SENT_REQUEST,
        RECIEVED_RESPONSE
    }


    @Column(name = "status")
    private RequestStatus status;

    @Column(name = "request")
    private String request;

    @Column(name = "response")
    private String response;

    @CreationTimestamp
    @Column(name = "createdTS", updatable = false)
    private Date createdTimestamp;

    @UpdateTimestamp
    @Column(name = "updatedTS", updatable = true)
    private Date updatedTimestamp;



}
