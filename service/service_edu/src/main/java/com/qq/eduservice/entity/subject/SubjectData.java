package com.qq.eduservice.entity.subject;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 读取excel表中的数据字段
 */
@Data
public class SubjectData {

    //设置列对应的属性(用于读)，如果设置表头名称表示用于写
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //设置列对应的属性(用于读)，如果设置表头名称表示用于写
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
