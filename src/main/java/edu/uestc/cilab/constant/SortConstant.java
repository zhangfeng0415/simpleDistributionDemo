package edu.uestc.cilab.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright ©2017 章峰(https://github.com/zhangfeng)@电子科技大学计算机科学与工程学院
 * <p>
 * Create with education-demo in edu.uestc.cilab.config
 * Class: SortConstant.java
 * User: zhangfeng
 * Email: 2498711309@qq.com
 * Time: 2017-11-21 16:43
 * Description:
 */
public class SortConstant {
    public static final String DEFAULT_SORT_NAME = "id";

    public static final String DEFAULT_SORT_ORDER = "DESC";

    public static final List<String> SORT_ORDERS = new ArrayList<>(Arrays.asList(new String[]{"ASC", "DESC"}));

    public static boolean isValidateSortOrder(String sortOrder) {
        return SORT_ORDERS.contains(sortOrder.toUpperCase());
    }
}
