package ba.nalaz.web.helper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Pagination {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pagination.class);
    private Integer page;
    private Integer start;
    private Integer limit;
    private String query;
    private List<SortItem> sortList;
    private String sort;

    public Pagination(){

    }

    public Pagination(HttpServletRequest request){
        Integer sortOrder = 0;
        Map paramMap = WebUtils.getParametersStartingWith(request, "d-");
        String queryVariable = request.getParameter("q");
        if(queryVariable!=null){
            this.query = queryVariable;
        }
        SortItem sortItem = new SortItem();
        List<SortItem> sortItemList = new ArrayList<SortItem>();

      if (!paramMap.isEmpty()) {
          if(paramMap.containsKey("16544-o")){
              String order = paramMap.get("16544-o").toString();
              if(order!=null){
                  sortOrder = Integer.parseInt(order);
              }
              if(sortOrder==1){
                  sortItem.setDirection("ASC");
              } else{
                  sortItem.setDirection("DESC");
              }


          }
          if(paramMap.containsKey("16544-s")){

              String sortBy = paramMap.get("16544-s").toString();
              sortItem.setProperty(sortBy);
              sortItemList.add(sortItem);
              this.sortList=sortItemList;

          }

          if(paramMap.containsKey("16544-p")){
          String pageVariable = paramMap.get("16544-p").toString();
              if(pageVariable!=null){
                  this.page = Integer.parseInt(pageVariable);
                  this.limit= 10;
                  Integer startPos = (Integer.parseInt(pageVariable) * this.limit)-this.limit;
                  this.start = startPos;
          }


        }
      }
    }

    public void setSort(String value) {
        List<SortItem> sortVariable = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            sortVariable = mapper.readValue(value, new TypeReference<List<SortItem>>(){
            	
            });
        } catch (Exception e) {
        	LOGGER.error("Error converting JSON collection to List<SortItem>.", e);
        }
        this.sort = value;
        this.sortList = sortVariable;
    }

    public String getSortField(){
        if (sortList == null){
            return null;
        }else{
            return sortList.get(0).property;
        }

    }

    public String getSortDirection(){
        if (sortList == null){
            return null;
        }else{
            return sortList.get(0).direction;
        }

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getStart() {
        if (this.start == null){
            return 0;
        }else{
            return this.start;
        }
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        if (this.limit == null){
            return 10;
        }else{
            return this.limit;
        }
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setQ(String query) {
        this.query = query;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pagination: ");
        if (page != null) {
            sb.append(" page=").append(page);
        }
        if (start != null) {
            sb.append(" start=").append(start);
        }
        if (limit != null) {
            sb.append(" limit=").append(limit);
        }
        if (query != null) {
            sb.append(" query=").append(query);
        }
        if (sortList != null) {
            sb.append(" sortList=").append(sortList);
        }
        return sb.toString();
    }

}


class SortItem {
    String property;
    String direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (property != null) {
            sb.append("property=").append(property);
        }
        if (direction != null) {
            sb.append(" direction=").append(direction);
        }
        return sb.toString();
    }
}


