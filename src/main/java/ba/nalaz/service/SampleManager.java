package ba.nalaz.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.nalaz.dao.SampleDao;
import ba.nalaz.model.core.Sample;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;

@Service
public class SampleManager {

    @Autowired
    SampleDao sampleDao;

    public void saveSample(Sample sample) {
        sampleDao.saveSample(sample);
    }

    public void removeSample(Long id) {
        sampleDao.removeSample(id);
    }

    public Sample getSample(Long id) {
        return sampleDao.getSample(id);
    }

    public PartialList getSampleList(Pagination pagination,Long labId) {
        return sampleDao.getSampleList(pagination, labId);
    }
    public PartialList getSampleListAsCompanyUser(Pagination pagination,Long labId, Long userId) {
        return sampleDao.getSampleListAsCompanyUser(pagination, labId, userId);
    }
    public PartialList getSampleProcessedList(Pagination pagination,Long labId) {
        return sampleDao.getSampleProcessedList(pagination, labId);
    }
    public PartialList getSampleProcessedListAsCompanyUser(Pagination pagination,Long labId, Long userId) {
        return sampleDao.getSampleProcessedListAsCompanyUser(pagination, labId, userId);
    }

    public List<Sample> getSampleList(Long labId) {
        return sampleDao.getSampleList(labId);
    }
    
    public List<Sample> getSampleListByStripeUser(Long labId, Long userId) {
        return sampleDao.getSampleListByStripeUser(labId,userId);
    }

    public Boolean isSampleExist(String propName,Long labId){
    	return sampleDao.isSampleExist(propName, labId);
    }

    public List<Sample> searchSample(String searchParam, Long labId){
        return sampleDao.searchSample(searchParam, labId);
    }
    
    public Sample getSampleByName(String name) {
        return sampleDao.getSampleByName(name);
    }
    
    public Sample getSampleByNameAndZip(String name, String zip) {
        return sampleDao.getSampleByNameAndZip(name, zip);
    }
}