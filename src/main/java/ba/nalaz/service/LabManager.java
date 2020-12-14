package ba.nalaz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ba.nalaz.dao.LabDao;
import ba.nalaz.model.core.Contact;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.User;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;

@Service
public class LabManager {

    LabDao labDao;

    @Autowired
    public void setLabDao(LabDao labDao) {
        this.labDao = labDao;
    }

    public Lab saveLab(Lab org) {
        labDao.saveLab(org);
        return org;
    }

    public Contact saveContact(Contact contact) {
        labDao.saveContact(contact);
        return contact;
    }
    public PartialList getLabList(Pagination pagination) {
        return labDao.getLabList(pagination);
    }    
    public PartialList getStripeLabList(Pagination pagination) {
        return labDao.getStripeLabList(pagination);
    }
    public List<Lab> getAllLabs() {
        return labDao.getAllLabs();
    }
    public List<Lab> getAllStripeLabs() {
        return labDao.getAllStripeLabs();
    }
    public List<Lab> getAllLabsExceptStripe() {
        return labDao.getAllLabsExceptStripe();
    }
    public List<Lab> getStripeLabs() {
        return labDao.getStripeLabs();
    }

    public Lab getLab(Long id) {
        return labDao.getLab(id);
    }

    public Boolean canDeleteLab(Lab lab) {
        Boolean status = false;
        status = labDao.canDeleteLab(lab);
        return status;
    }

    public void removeLab(Long id) {
        labDao.removeLab(id);
    }

    public boolean isLabExists(String labName) {
        return labDao.isLabExists(labName);
    }

    public boolean isLabExists1(String labName, Long labId) {
        return labDao.isLabExists1(labName, labId);
    }

    public PartialList getLabAdministratorList(Pagination pagination,Long labId) {
        return labDao.getLabAdministratorList(pagination,labId);
    }

    public Boolean canDeleteLabAdmin(User user) {
        Boolean status = false;
        status = labDao.canDeleteLabAdmin(user);
        return status;
    }

    public Boolean canDeleteLabAdm(User user) {
        Boolean status = false;
        status = labDao.canDeleteLabAdm(user);
        return status;
    }

    public void removeLabAdministrator(Long id) {
        labDao.removeLabAdministrator(id);
    }
}