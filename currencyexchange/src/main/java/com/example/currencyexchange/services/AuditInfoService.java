package com.example.currencyexchange.services;

import com.example.currencyexchange.models.AuditInfo;

import java.util.List;

public interface AuditInfoService {
    AuditInfo createData(AuditInfo auditInfo);

    AuditInfo updateData(AuditInfo auditInfo);

    List<AuditInfo> getAllData();

    AuditInfo getDataById(Integer requestId);

    void deleteData(Integer requestId);

    Boolean dataExist(Integer requestId);


}
