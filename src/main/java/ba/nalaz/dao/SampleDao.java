package ba.nalaz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ba.nalaz.model.core.Sample;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;

@Repository
public class SampleDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveSample(Sample sample) {
        sessionFactory.getCurrentSession().saveOrUpdate(sample);
    }

    public void removeSample(Long id) {
        Sample sample = (Sample) sessionFactory.getCurrentSession().load(Sample.class, id);
        sessionFactory.getCurrentSession().delete(sample);
    }

    public Sample getSample(Long id) {
        Sample sample = (Sample) sessionFactory.getCurrentSession().get(Sample.class, id);
    	return sample;
    }

    @SuppressWarnings("unchecked")
	public PartialList getSampleList(Pagination pagination,Long labId) {
        List<Sample> partialList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.createAlias("modifiedUser", "modifiedUser");
        criteria.add(Restrictions.eq("lab.id", labId));
        criteria.add(Restrictions.eq("overallStatus", 0));
        criteria.add(Restrictions.ne("deleted", true));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("patientName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("patientSurname", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("sampleCode", pagination.getQuery() + '%',MatchMode.ANYWHERE)
            ));
        }
        String sortField = pagination.getSortField();
        if (sortField != null) {
            if ("ASC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.asc(sortField));
            }
            if ("DESC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.desc(sortField));
            }
        }
        criteria.addOrder(Order.desc("createdDate"));
        criteria.setMaxResults(pagination.getLimit());
        criteria.setFirstResult(pagination.getStart());
        partialList = criteria.list();
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        return new PartialList(partialList, total);
    }
    
    @SuppressWarnings("unchecked")
   	public PartialList getSampleListAsCompanyUser(Pagination pagination,Long labId, Long userId) {
           List<Sample> partialList = null;
           Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
           criteria.createAlias("modifiedUser", "modifiedUser");
           criteria.add(Restrictions.eq("lab.id", labId));
           criteria.add(Restrictions.eq("overallStatus", 0));
           criteria.add(Restrictions.eq("createdUser.id", userId));
           criteria.add(Restrictions.ne("deleted", true));
           if (pagination.getQuery() != null) {
               criteria.add(Restrictions.or(

                       Restrictions.ilike("patientName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                       Restrictions.ilike("patientSurname", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                       Restrictions.ilike("sampleCode", pagination.getQuery() + '%',MatchMode.ANYWHERE)

               ));
           }
           String sortField = pagination.getSortField();
           if (sortField != null) {
               if ("ASC".equals(pagination.getSortDirection())) {
               	criteria.addOrder(Order.asc(sortField));
               }
               if ("DESC".equals(pagination.getSortDirection())) {
               	criteria.addOrder(Order.desc(sortField));
               }
           }
           criteria.addOrder(Order.desc("createdDate"));
           criteria.setMaxResults(pagination.getLimit());
           criteria.setFirstResult(pagination.getStart());
           partialList = criteria.list();
           criteria.setFirstResult(0);
           criteria.setProjection(Projections.rowCount());
           Long total = (Long) criteria.uniqueResult();
           return new PartialList(partialList, total);
       }
    
    @SuppressWarnings("unchecked")
	public PartialList getSampleProcessedList(Pagination pagination,Long labId) {
        List<Sample> partialList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.createAlias("modifiedUser", "modifiedUser");
        criteria.add(Restrictions.eq("lab.id", labId));
        criteria.add(Restrictions.eq("overallStatus", 1));
        criteria.add(Restrictions.ne("deleted", true));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("patientName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("patientSurname", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("sampleCode", pagination.getQuery() + '%',MatchMode.ANYWHERE)
            ));
        }
        String sortField = pagination.getSortField();
        if (sortField != null) {
            if ("ASC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.asc(sortField));
            }
            if ("DESC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.desc(sortField));
            }
        }
        criteria.addOrder(Order.desc("createdDate"));
        criteria.setMaxResults(pagination.getLimit());
        criteria.setFirstResult(pagination.getStart());
        partialList = criteria.list();
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        return new PartialList(partialList, total);
    }
    
    @SuppressWarnings("unchecked")
   	public PartialList getSampleProcessedListAsCompanyUser(Pagination pagination,Long labId, Long userId) {
           List<Sample> partialList = null;
           Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
           criteria.createAlias("modifiedUser", "modifiedUser");
           criteria.add(Restrictions.eq("lab.id", labId));
           criteria.add(Restrictions.eq("overallStatus", 1));
           criteria.add(Restrictions.eq("createdUser.id", userId));
           criteria.add(Restrictions.ne("deleted", true));
           if (pagination.getQuery() != null) {
               criteria.add(Restrictions.or(

                       Restrictions.ilike("patientName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                       Restrictions.ilike("patientSurname", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                       Restrictions.ilike("sampleCode", pagination.getQuery() + '%',MatchMode.ANYWHERE)

               ));
           }
           String sortField = pagination.getSortField();
           if (sortField != null) {
               if ("ASC".equals(pagination.getSortDirection())) {
               	criteria.addOrder(Order.asc(sortField));
               }
               if ("DESC".equals(pagination.getSortDirection())) {
               	criteria.addOrder(Order.desc(sortField));
               }
           }
           criteria.addOrder(Order.desc("createdDate"));
           criteria.setMaxResults(pagination.getLimit());
           criteria.setFirstResult(pagination.getStart());
           partialList = criteria.list();
           criteria.setFirstResult(0);
           criteria.setProjection(Projections.rowCount());
           Long total = (Long) criteria.uniqueResult();
           return new PartialList(partialList, total);
       }

    @SuppressWarnings("unchecked")
	public List<Sample> getSampleList(Long labId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.add(Restrictions.eq("lab.id", labId));
        criteria.createAlias("lab", "labObj");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.ne("deleted", true));
        criteria.addOrder(Order.asc("patientName"));
        return criteria.list();
    }
    
    @SuppressWarnings("unchecked")
	public List<Sample> getSampleListByStripeUser(Long labId, Long userId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.add(Restrictions.eq("lab.id", labId));
        criteria.add(Restrictions.eq("createdUser.id", userId));
        criteria.createAlias("lab", "labObj");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.ne("deleted", true));
        criteria.addOrder(Order.asc("patientName"));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
	public List<Sample> searchSample(String searchParam, Long labId) {
        Session session = sessionFactory.getCurrentSession();
        List<Sample>  propertyList= null;
        Criteria criteria = session.createCriteria(Sample.class);
        criteria.createAlias("modifiedUser","modifiedUser");
        criteria.add(Restrictions.eq("lab.id",labId));
        criteria.add(Restrictions.ne("deleted", true));
        criteria.add(Restrictions.ilike("patientName", searchParam, MatchMode.START));
        criteria.addOrder(Order.asc("patientName"));
        propertyList = criteria.list();
        return propertyList;
    }

    public boolean isSampleExist(String patientName, Long labId) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("patientName", patientName));
        if(labId != null) {
            criteria.add(Restrictions.eq("lab.id", labId));
        }
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;
    }

    public boolean isLabSampleExist(Long propertyId, Long labId) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("id", propertyId));
        if(labId != null) {
            criteria.add(Restrictions.eq("lab.id", labId));
        }
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;
    }
    
    public Sample getSampleByFips(String fips) {
        Session session = sessionFactory.getCurrentSession();
        List<Sample>  propertyList= null;
        Criteria criteria = session.createCriteria(Sample.class);
        criteria.add(Restrictions.eq("countyFIPS", fips));
        propertyList = (List<Sample>) criteria.list();
        return propertyList.get(0);
	}
    
    public Sample getSampleByName(String name){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.add(Restrictions.eq("patientName", name));
        criteria.setMaxResults(1);
        return (Sample) criteria.uniqueResult();
    }
    
    public Sample getSampleByNameAndZip(String name, String zip){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Sample.class);
        criteria.add(Restrictions.eq("patientName", name));
        criteria.add(Restrictions.eq("zipCode", zip));
        criteria.setMaxResults(1);
        return (Sample) criteria.uniqueResult();
    }
}