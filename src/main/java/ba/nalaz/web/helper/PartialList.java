package ba.nalaz.web.helper;

import java.util.List;

public class PartialList {

    private List list;
    private long totalSize;

    private PartialList(){

    }

    public PartialList(List list, Long totalSize){
        this.list = list;
        this.totalSize = totalSize;

    }

    public List getList() {
        return list;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public int getSize(){
        return  (int) totalSize;
    }


}
