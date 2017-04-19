package cn.gotoil.znl.config.define;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wh on 2017/4/19.
 */
@Getter
@Setter
public class PageInfo<T> {

    public static  final  int DefaultPageSize = 10;

    private int pageNum;

    private int totalPages;

    private List<T> list ;



}
