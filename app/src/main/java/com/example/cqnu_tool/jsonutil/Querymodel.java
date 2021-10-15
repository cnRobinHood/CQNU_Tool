package com.example.cqnu_tool.jsonutil;

import java.util.List;

public class Querymodel {

    private int currentpage;
    private int currentresult;
    private boolean entityorfield;
    private int limit;
    private int offset;
    private int pageno;
    private int pagesize;
    private int showcount;
    private List<String> sorts;
    private int totalcount;
    private int totalpage;
    private int totalresult;

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentresult(int currentresult) {
        this.currentresult = currentresult;
    }

    public int getCurrentresult() {
        return currentresult;
    }

    public void setEntityorfield(boolean entityorfield) {
        this.entityorfield = entityorfield;
    }

    public boolean getEntityorfield() {
        return entityorfield;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPageno() {
        return pageno;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setShowcount(int showcount) {
        this.showcount = showcount;
    }

    public int getShowcount() {
        return showcount;
    }

    public void setSorts(List<String> sorts) {
        this.sorts = sorts;
    }

    public List<String> getSorts() {
        return sorts;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalresult(int totalresult) {
        this.totalresult = totalresult;
    }

    public int getTotalresult() {
        return totalresult;
    }

}