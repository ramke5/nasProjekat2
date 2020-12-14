package ba.nalaz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ba.nalaz.model.core.Contact;
import ba.nalaz.model.core.Lab;
import ba.nalaz.model.core.User;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;

@Repository
public class LabDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Lab saveLab(Lab lab) {
        sessionFactory.getCurrentSession().saveOrUpdate(lab);
        return lab;
    }

    public Contact saveContact(Contact contact) {
        sessionFactory.getCurrentSession().saveOrUpdate(contact);
        return contact;
    }

    public List<Lab> getAllLabs() { 
        Query query = sessionFactory.getCurrentSession().createQuery("select o from Lab o order by o.name ASC");
        return (List<Lab>) query.list();
    }
    
    public List<Lab> getAllStripeLabs() { 
        Query query = sessionFactory.getCurrentSession().createQuery("select o from Lab o where o.stripeLab is true and o.deleted is false and o.enabled is true order by o.name ASC");
        return (List<Lab>) query.list();
    }
    
    public List<Lab> getAllLabsExceptStripe() { 
        Query query = sessionFactory.getCurrentSession().createQuery("select o from Lab o where o.stripeLab is false or o.stripeLab is null order by o.name ASC");
        return (List<Lab>) query.list();
    }
        
    public PartialList getLabList(Pagination pagination) {
        List partialList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);

        criteria.createAlias("contact", "contact");
        criteria.setFetchMode("productAccessList", FetchMode.SELECT);
        criteria.add(Restrictions.ne("deleted", true));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("name", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("contact.firstName", pagination.getQuery() + '%', MatchMode.ANYWHERE),
                    Restrictions.ilike("contact.lastName", pagination.getQuery() + '%',MatchMode.ANYWHERE)
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

        criteria.setMaxResults(pagination.getLimit());
        criteria.setFirstResult(pagination.getStart());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        partialList = criteria.list();
       criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        return new PartialList(partialList, total);

    }
    
    public PartialList getStripeLabList(Pagination pagination) {
        List partialList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Lab.class);
        criteria.add(Restrictions.ne("deleted", true));
        criteria.add(Restrictions.eq("stripeLab", true));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.ilike("name", pagination.getQuery() + '%',MatchMode.ANYWHERE)
            );
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

        criteria.setMaxResults(pagination.getLimit());
        criteria.setFirstResult(pagination.getStart());
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        partialList = criteria.list();
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        return new PartialList(partialList, total);
    }

    public Lab getLab(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Lab.class);
        criteria.add(Restrictions.eq("id", id));

        return (Lab) criteria.uniqueResult();
    }
    
    public List<Lab> getStripeLabs() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Lab.class);
        criteria.add(Restrictions.eq("stripeLab", true));

        return (List<Lab>) criteria.list();
    }

    public Boolean canDeleteLab(Lab lab) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("lab", lab));
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;
    }

    public void removeLab(Long id) {
        Lab lab = (Lab) sessionFactory.getCurrentSession().load(Lab.class, id);
        sessionFactory.getCurrentSession().delete(lab);
    }

    public boolean isLabExists(String labName) {

        int count = 0;
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Lab.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("name", labName));
        criteria.add(Restrictions.ne("deleted", true));
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;
    }

    public boolean isLabExists1(String labName, Long labId) {

        int count = 0;
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Lab.class);
        criteria.setProjection(Projections.rowCount());
        if (labId != null) {
            criteria.add(Restrictions.ne("id", labId));
        }
        criteria.add(Restrictions.eq("name", labName));
        criteria.add(Restrictions.ne("deleted", true));
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;
    }
    @SuppressWarnings("unchecked")
	public PartialList getLabAdministratorList(Pagination pagination, Long labId) {
        List<User> partialList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.createAlias("roles", "roleObj");
        criteria.createAlias("lab", "labObj");
        criteria.add(Restrictions.eq("roleObj.name", "ROLE_ADMIN_KOMPANIJE"));
        criteria.add(Restrictions.ne("accountExpired", true));
        criteria.add(Restrictions.eq("labObj.id", labId));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("username", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("firstName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("lastName", pagination.getQuery() + '%',MatchMode.ANYWHERE)
            ));
        }
        String sortField =  pagination.getSortField();
        if (sortField != null){
            if ("ASC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.asc(sortField));
            }
            if ("DESC".equals(pagination.getSortDirection())) {
            	criteria.addOrder(Order.desc(sortField));
            }
        }
        criteria.setMaxResults(pagination.getLimit());
        criteria.setFirstResult(pagination.getStart());

        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property("id").as("id"));
        projList.add(Projections.property("firstName").as("firstName"));
        projList.add(Projections.property("lastName").as("lastName"));
        projList.add(Projections.property("username").as("username"));
        projList.add(Projections.property("enabled").as("enabled"));
        criteria.setProjection(projList);
        criteria.setResultTransformer(Transformers.aliasToBean(User.class));
        
        //criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        partialList = criteria.list();
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total = (Long) criteria.uniqueResult();
        return  new PartialList(partialList,total);
    }
    public Boolean canDeleteLabAdmin(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from LRTenant where createdUser=:user or modifiedUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;
    }

    public Boolean canDeleteLabAdm(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Sample where createdUser=:user or modifiedUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;
    }

    public void removeLabAdministrator(Long id) {
        User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
        sessionFactory.getCurrentSession().delete(user);
    }

}
