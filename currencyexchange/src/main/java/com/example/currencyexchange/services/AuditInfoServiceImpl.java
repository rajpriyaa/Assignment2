package com.example.currencyexchange.services;

import com.example.currencyexchange.models.AuditInfo;
import com.example.currencyexchange.repository.AuditInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditInfoServiceImpl implements AuditInfoService{

    @Autowired
    private AuditInfoRepository auditInfoRepository;

    @Override
    public AuditInfo createData(AuditInfo auditInfo) {
        return auditInfoRepository.save(auditInfo);
    }

    @Override
    public AuditInfo updateData(AuditInfo auditInfo) {
        Optional<AuditInfo> auditObj = this.auditInfoRepository.findById(auditInfo.getRequestId());

        if(auditObj.isPresent()){
            AuditInfo auditUpdate = auditObj.get();
            auditUpdate.setRequest(auditInfo.getRequest());
            auditUpdate.setResponse(auditInfo.getResponse());
            auditUpdate.setStatus(auditInfo.getStatus());
            return this.auditInfoRepository.save(auditUpdate);
        } else {
            throw new RuntimeException("No information is present in database about this id " + auditInfo.getRequestId());
        }
    }

    @Override
    public List<AuditInfo> getAllData() {
        return this.auditInfoRepository.findAll();
    }

    @Override
    public AuditInfo getDataById(Integer requestId) {
        Optional<AuditInfo> auditObj = this.auditInfoRepository.findById(requestId);

        if(auditObj.isPresent()){
            return auditObj.get();
        } else {
            throw new RuntimeException("Nothing related to this id " + requestId);
        }
    }

    @Override
    public void deleteData(Integer requestId) {
        Optional<AuditInfo> auditObj = this.auditInfoRepository.findById(requestId);
        if(auditObj.isPresent()){
            this.auditInfoRepository.deleteById(requestId);
        } else {
            throw new RuntimeException("Nothing related to this id " + requestId);
        }
        
    }

    @Override
    public Boolean dataExist(Integer requestId) {
        Optional<AuditInfo> auditObj = this.auditInfoRepository.findById(requestId);

        if(auditObj.isPresent()){
            return true;
        } else {
            return false;
        }
    }

    

   
}
