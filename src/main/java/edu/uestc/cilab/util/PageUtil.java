package edu.uestc.cilab.util;

import java.util.List;

/**
 * Copyright ©2016 创联工作室@电子科技大学计算机科学与工程学院 iustu.com(http://www.iustu.com)
 * <p>
 * Create with marxism in com.iustu.marxism.util
 * Class: PageUtil.java
 * User: joe-mac
 * Email: zhangshuzhou.hi@163.com
 * Time: 2016-08-12 20:20
 * Description:
 */
public class PageUtil<T> {
    public int pageNo;
    public int pageSize;
    public int totalPages;
    public long totalElements;
    public List<T> content;


    public PageUtil(){
        super();
    }

//    public PageUtil(int pageNo, int pageSize, int totalPage, long totalElements, List<T> content) {
//        this.pageNo = pageNo;
//        this.pageSize = pageSize;
//        this.totalPages = totalPage;
//        this.totalElements = totalElements;
//        this.content = content;
//    }


    public PageUtil(Integer pageNo, Integer pageSize, List<T> workList) {

        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = workList.size();
        this.totalPages = (int) (totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1);

        long start = pageNo * pageSize < totalElements ? pageNo * pageSize : totalElements;
        long end = (pageNo + 1) * pageSize < totalElements ? (pageNo + 1) * pageSize : totalElements;

        this.content = workList.subList((int)start, (int)end);
    }

    public PageUtil(Integer pageNo, Integer pageSize, long totalElements, List<T> workList) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = (int) (totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1);
        this.content = workList;
    }


    @Override
    public String toString() {
        return "PageEntity{" + "pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalPages=" + totalPages + ", totalElements="
                + totalElements + ", content=" + content + '}';
    }
}
