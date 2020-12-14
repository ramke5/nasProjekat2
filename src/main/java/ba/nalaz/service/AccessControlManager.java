package ba.nalaz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.nalaz.dao.LabDao;
import ba.nalaz.dao.SampleDao;
import ba.nalaz.model.core.User;


@Service
public class AccessControlManager {
    @Autowired
    LabDao labDao;
    @Autowired
    SampleDao sampleDao;

    public boolean checkEditSampleAccess(User user,Long sampleId){
        boolean grantAccess = false;
        if(user.getLab() != null){
           grantAccess = sampleDao.isLabSampleExist(sampleId,user.getLab().getId());
        }
        return grantAccess;
    }
    public boolean checkLabNonEmpty(User user){
        boolean grantAccess = true;
        if(user.getLab() == null){
            grantAccess = false;
        }
        return grantAccess;
    }
    public boolean checkUserLabVsLab(User user,Long labId){
        boolean grantAccess = false;
        if(user.getLab() != null && user.getLab().getId().equals(labId)){
            grantAccess =true;
        }
        return grantAccess;
    }
    public boolean checkUserVsSampleCreated(User user,Long sampleUserCreatedId){
        boolean grantAccess = true;
        if(user.getUserType().getId()==3 && (user.getId()!=sampleUserCreatedId)){
            grantAccess = false;
        }
        return grantAccess;
    }
}