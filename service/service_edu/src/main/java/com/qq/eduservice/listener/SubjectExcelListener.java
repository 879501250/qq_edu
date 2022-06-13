package com.qq.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.eduservice.entity.EduSubject;
import com.qq.eduservice.entity.subject.SubjectData;
import com.qq.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.qq.servicebase.exceptionhandler.QqException;

@Component
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    // 注入service层
    @Autowired
    private EduSubjectService subjectService;

    // 从除了第一行表头的数据开始读取一行一行读取excel数据
    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
        if (data == null)
            throw new QqException(20001, "文件数据为空");

        // 插入一级分类
        EduSubject oneSubject = existOneSubject(data.getOneSubjectName());
        if (oneSubject == null) { // 如果一级分类为空
            EduSubject subject = new EduSubject();
            subject.setTitle(data.getOneSubjectName());
            subject.setParentId("0");
            subjectService.save(subject);
            oneSubject = existOneSubject(data.getOneSubjectName());
        }

        // 查询二级分类的pid
        String id = oneSubject.getId();

        // 插入二级分类
        EduSubject twoSubject = existTwoSubject(data.getTwoSubjectName(), id);
        if (twoSubject == null) { // 如果二级分类为空
            EduSubject subject = new EduSubject();
            subject.setTitle(data.getTwoSubjectName());
            subject.setParentId(id);
            subjectService.save(subject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }

    // 判断一级分类是否存在
    public EduSubject existOneSubject(String name) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name); // 判断名称是否存在
        queryWrapper.eq("parent_id", 0); // 判断是否是一级分类
        return subjectService.getOne(queryWrapper); // 返回查询结果
    }

    // 判断二级分类是否存在
    public EduSubject existTwoSubject(String name, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", name); // 判断名称是否存在
        queryWrapper.eq("parent_id", pid); // 判断是否是同一类二级分类
        return subjectService.getOne(queryWrapper); // 返回查询结果
    }
}
