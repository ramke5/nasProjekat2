package ba.nalaz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import ba.nalaz.model.core.Role;
import ba.nalaz.model.core.User;
import ba.nalaz.model.core.UserLog;
import ba.nalaz.model.core.UserType;
import ba.nalaz.web.helper.Pagination;
import ba.nalaz.web.helper.PartialList;


@Repository
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public User getUser(Long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    public PartialList getUsers(Pagination pagination) {
        List resultList = null;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.createAlias("userType", "userTypeObj");
        criteria.add(Restrictions.ne("userTypeObj.name","Admin kompanije"));
        criteria.add(Restrictions.ne("userTypeObj.name","Korisnik kompanije"));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(
                    Restrictions.ilike("username", pagination.getQuery() + '%', MatchMode.ANYWHERE),
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
        resultList = criteria.list();
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        Long total=(Long)criteria.uniqueResult();
        return  new PartialList(resultList,total);

    }

    public List<User> getUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("userType.id",1L));
        return criteria.list();
    }

    public List getLabUser(Pagination pagination,Long labId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("userType.id",3L));
        criteria.add(Restrictions.eq("lab.id",labId));
        if (pagination.getQuery() != null) {
            criteria.add(Restrictions.or(

                    Restrictions.ilike("username", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("firstName", pagination.getQuery() + '%',MatchMode.ANYWHERE),
                    Restrictions.ilike("lastName", pagination.getQuery() + '%',MatchMode.ANYWHERE)

            ));
        }
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
    public PartialList getStripeUsers(Pagination pagination) {
	    List resultList = null;
	    Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
	    criteria.createAlias("userType", "userTypeObj");
        criteria.add(Restrictions.or(Restrictions.eq("userType.id",5L), Restrictions.eq("userType.id",7L)));
	    if (pagination.getQuery() != null) {
	        criteria.add(Restrictions.or(
	                Restrictions.ilike("username", pagination.getQuery() + '%', MatchMode.ANYWHERE),
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
	    resultList = criteria.list();
	    criteria.setFirstResult(0);
	    criteria.setProjection(Projections.rowCount());
	    Long total=(Long)criteria.uniqueResult();
	    return  new PartialList(resultList,total);
    }

    public List getAllStripeUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("userType.id",5L));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public List getLabUserBasic(Long labId) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("userType.id",4L));
        criteria.add(Restrictions.eq("lab.id",labId));
        //criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

    public User saveUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        sessionFactory.getCurrentSession().flush();
        return user;
    }


    public User mergeUser(User user) {
    	sessionFactory.getCurrentSession().merge(user);
        sessionFactory.getCurrentSession().flush();
        return user;
    }

    public Boolean canDeleteUser(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from LRTenant where createdUser=:user or modifiedUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;
    }

    public Boolean isUserExistInProperty(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Sample where createdUser=:user or modifiedUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;

    }
    public Boolean isUserExistInLRTenant(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from LRTenant where createdUser=:user or modifiedUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;

    }
    public Boolean isUserExistInLRRecord(User user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from LRRecord where createdUser=:user");
        query.setParameter("user", user);
        List <User> userList = query.list();
        return userList.isEmpty() ? false : true;

    }
    public void removeUser(Long id) {
        User user =  (User) sessionFactory.getCurrentSession().load(User.class, id);
        sessionFactory.getCurrentSession().delete(user);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where username=:username");
        query.setParameter("username", username);
        List users = query.list();
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    public String getUserPassword(Long userId) {
        User user = (User) sessionFactory.getCurrentSession().get(User.class, userId);
        return user.getPassword();

    }

    // Role

    public Role getRole(Long id) {
        return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
    }

    public UserType getUserType(Long id) {
        return (UserType) sessionFactory.getCurrentSession().get(UserType.class, id);
    }

    public List<Role> getRoles() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role r order by upper(r.name)");
        return query.list();
    }

    public List<Role> getUserRoles() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role r where systemRole=false order by upper(r.name)");
        return query.list();
    }

    public Role getRoleByName(String rolename) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role where name=:rolename");
        query.setParameter("rolename", rolename);
        List roles = query.list();
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }

    public Role saveRole(Role role) {
        sessionFactory.getCurrentSession().saveOrUpdate(role);
        return role;
    }

    public void removeRole(String rolename) {
        Role role = getRoleByName(rolename);
        sessionFactory.getCurrentSession().delete(role);
    }

    public void removeRole(Role role) {
        sessionFactory.getCurrentSession().delete(role);
    }

    public void removeRole(Long id) {
        Role role =  (Role) sessionFactory.getCurrentSession().load(Role.class, id);
        sessionFactory.getCurrentSession().delete(role);
    }

    // UserType

    public List<UserType> getUserTypes() {
        Query query = sessionFactory.getCurrentSession().createQuery("from UserType r order by upper(r.name)");
        return query.list();
    }




    public List<User> getUsersByUserTypeUser(String userTypeName){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.createAlias("userType", "userTypeObj");
        criteria.add(Restrictions.ne("accountExpired",true));
        criteria.add(Restrictions.eq("userTypeObj.name", userTypeName));
        return criteria.list();
    }

    public boolean isUserEmailExists(String email) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("email", email));
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }
    public boolean isUserEmailExistsEdit(Long id,String email) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("email", email));
        if(id != null) {
            criteria.add(Restrictions.ne("id", id));
        }
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }
    public boolean isUserEmailExistsEditLab(Long id,String email) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("email", email));
        if(id != null) {
            criteria.add(Restrictions.ne("id", id));
        }
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }


    public boolean isUserEmailExistsLab(String email) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("email", email));

        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }

    public boolean isUserExists(String username) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("username", username));
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }

    public boolean isUserExistsEdit(Long id,String username) {
        int count = 0;
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());
        criteria.add(Restrictions.eq("username", username));
        if(id != null) {
            criteria.add(Restrictions.ne("id", id));
        }
        count = ((Number) criteria.uniqueResult()).intValue();
        return (count > 0) ? true : false;

    }

    public User getUserByEmail(String email){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        criteria.add(Restrictions.ne("accountExpired",true));
        criteria.setMaxResults(1);
        return (User) criteria.uniqueResult();
    }

    public User getUserByUsername(String username){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        criteria.add(Restrictions.ne("accountExpired",true));
        criteria.setMaxResults(1);
        return (User) criteria.uniqueResult();
    }

    public void saveUserLog(UserLog userLog) {
        sessionFactory.getCurrentSession().saveOrUpdate(userLog);
    }

    public UserLog getUserLog(Long userId) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserLog.class);
        criteria.add(Restrictions.eq("user.id",userId));
        criteria.addOrder(Order.desc("loggedInTime"));
        criteria.setMaxResults(1);
        return (UserLog)criteria.uniqueResult();
    }

    public List getUserList(String searchParam, Long labId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.createAlias("lab","lab");
        criteria.createAlias("userType","userType");
        criteria.add(Restrictions.eq("lab.id",labId));
        criteria.add(Restrictions.ne("userType.mainRole.id",2L));
        criteria.add(Restrictions.eq("enabled",true));
        criteria.add(Restrictions.ilike("username", searchParam, MatchMode.START));
        criteria.addOrder(Order.asc("username"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();

    }



}
